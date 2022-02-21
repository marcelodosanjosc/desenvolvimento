package desenvolvimento.aula.tela;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import desenvolvimento.aula.dao.DaoAlunoRepository;
import desenvolvimento.aula.entity.Aluno;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

public class AlunoTela {

	DaoAlunoRepository repository = new DaoAlunoRepository();
	
	private JFrame frame;
	private final JLabel lblNewLabel = new JLabel("Codigo");
	private JTextField textFieldCod;
	private JTextField textFieldNome;
	private JTable table;
	private JButton btnSalvar;
	private JButton btnEditar;
	private JButton btnListar;
	private DefaultTableModel modelo = new DefaultTableModel();

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlunoTela window = new AlunoTela();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public AlunoTela() {
		initialize();
	}
	
	String [] colunas = {"Nome"};
	private JScrollPane scrollPane_1;

	
	private void initialize()  {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBounds(100, 100, 800, 300);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[117.00px][][grow]", "[grow][][][][][][][]"));
		
		
		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny center");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		
		
		textFieldCod = new JTextField();
		textFieldCod.setEditable(false);
		textFieldCod.setColumns(10);
		panel.add(textFieldCod, "cell 0 1,alignx left,aligny top");
		
		JLabel lblNewLabel_1 = new JLabel("Nome");
		panel.add(lblNewLabel_1, "cell 0 2,alignx left,aligny center");
		
		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
		panel.add(textFieldNome, "cell 0 3,alignx left,aligny top");
		
		btnSalvar = new JButton("Salvar");
		panel.add(btnSalvar, "cell 0 4");
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnEditar, "cell 0 5");
		
		btnListar = new JButton("Listar");
		panel.add(btnListar, "cell 0 6");
		
		scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, "cell 1 6,grow");
		table = new JTable(modelo);
		scrollPane_1.setViewportView(table);
		
		modelo.addColumn("ID");
		modelo.addColumn("Nome");
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(40);
		criarEventos();
		try {
			salvarAluno();
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		editarAluno();
	}
	

	
	
	private void criarEventos() {
		btnListar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)  {
				try {
					listaAlunos();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
	}
	
	private void listaAlunos() {
		List<Aluno> list;
		try {
			modelo.setNumRows(0);
			list = repository.findAll();
			for (Aluno aluno : list) {
				modelo.addRow(new Object[] {aluno.getId(), aluno.getNome()});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void salvarAluno() throws Exception {
		btnSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Aluno aluno;
				if(!textFieldNome.getText().isEmpty() && textFieldNome.getText() != null) {
					aluno = new Aluno();
					aluno.setNome(textFieldNome.getText());
					try {
						repository.salvarAluno(aluno);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					listaAlunos();
					
					textFieldNome.setText("");
				}else {
					JOptionPane.showMessageDialog(null,
							"Preencha o campo de Nome");
				}
				
			}
		});
	}
	
	private void editarAluno() {
		btnEditar.addActionListener(new ActionListener() {
			Aluno aluno;
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
				
				int linhaSelecionada = -1;
				linhaSelecionada = table.getSelectedRow();
				if(linhaSelecionada >= 0) {
					int idAluno = (int) table.getValueAt(linhaSelecionada, 0);
					 aluno = new Aluno();
					 aluno = repository.findId(idAluno);
					 textFieldCod.setText(Integer.toString(aluno.getId()));
					 textFieldNome.setText(aluno.getNome());
					 
					 btnSalvar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							aluno = new Aluno();
							aluno.setId(Integer.parseInt(textFieldCod.getText()));
							aluno.setNome(textFieldNome.getText());
							
							try {
								repository.atualizarAluno(aluno, idAluno);
								listaAlunos();
							} catch (Exception e1) {
								
								e1.printStackTrace();
							}
							
						}
					});
					System.out.println(idAluno);
				}else {
					JOptionPane.showMessageDialog(null,
							"É necesário selecionar uma linha.");
				}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
	}

}

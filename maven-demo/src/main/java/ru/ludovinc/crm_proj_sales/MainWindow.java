package ru.ludovinc.crm_proj_sales;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

import net.miginfocom.swing.MigLayout;
import javax.swing.JDesktopPane;
import javax.swing.JSpinner;
//import ru.ldinc.main33.Main;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private JTabbedPane tabbedPane;
	private JPanel panel1;
	private JButton btnGetImage;
	private Choice choice;
	private JPanel panel2;
	private JPanel panel3;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmSaveFile;
	private JMenu mnOptions;
	private JMenuItem mntmExit;
	private JTable table;
	private JScrollPane scrollPane_2;
	private ClientModel clModel;
	private JPanel panel_1;
	private JLabel lblCompanyLogo;
	private JScrollPane scrollPane_1;
	private JButton btnAddClient;
	private DefaultComboBoxModel<String> model;
	private JComboBox<String> comboBox;
	private String[] select = new String[] {"�� �����", "�� ������"};
	private JTextPane searchTextPane;
	private JButton btnChooseFile;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JTextPane textPaneHints;
	private ImageIcon imageIcon;
	private JButton btnNewButton_base_add;
	private JComboBox comboBox_type_of_journal;
	private JSpinner spinner;
	

	public MainWindow(int width, int height) {
	
		imageIcon = new ImageIcon("DE_logo.png");
		setIconImage(imageIcon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(468, 428);
		getContentPane().setLayout(new MigLayout("", "[432px]", "[329px]"));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		setLocationRelativeTo(null);
		
		getContentPane().add(tabbedPane, "cell 0 0,grow");
		
		panel1 = new JPanel(null);
		tabbedPane.addTab("������ � ���������", panel1);
		
		btnChooseFile = new JButton("�������� ����");
		btnChooseFile.setForeground(new Color(47, 79, 79));
		btnChooseFile.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearFolders(new File(Main.absolut.getAbsolutePath()+"/VBA_PDF"));
				clearFolders(new File(Main.absolut.getAbsolutePath()+"/dep_gateway/"));
				File requestDownloadDirectory = new File("C:\\Users\\805268\\Downloads");
				JFileChooser chooser = new JFileChooser(requestDownloadDirectory);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Adobe Acrobat file", "pdf");
				
				chooser.setFileFilter(filter);
				int reply = chooser.showOpenDialog(null);
				if(reply == JFileChooser.APPROVE_OPTION){
					File ahu_data = chooser.getSelectedFile();
					try {
						FileUtils.copyFile(ahu_data, new File(Main.absolut.getAbsolutePath()+"/dep_gateway/"+ahu_data.getName()));
						FileUtils.copyFile(ahu_data, new File(Main.absolut.getAbsolutePath()+"/VBA_PDF/zencha_request.pdf"));
						//�������� � �����
						SideApp.runAcrobat();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
				try{Thread.sleep(5000);} catch (InterruptedException e){}
				panel1.add(btnNewButton);
				panel1.updateUI();;
				//
			}	
		});
		btnChooseFile.setBounds(10, 11, 186, 41);
		panel1.add(btnChooseFile);
		
		btnNewButton = new JButton("����������");
		btnNewButton.setForeground(new Color(47, 79, 79));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPaneHints.setText("����� ����������� ������� �� ������ �������, ����������� ����� ����� �"
						+ " ���������. ����� ��� �������� ���������� Excel � �� ������� �������� ������� ������������ �� "
						+ "�� ����� ����������. ����� ���� ��� �� ��� ������������ ���� ����� ��������� � ������� � �� �����,"
						+ " ����� ������ ���������");
				panel1.updateUI();
				if (!new File(Main.absolut.getAbsolutePath()+"\\VBA_PDF\\zencha_request_portable.xlsx").exists()){
					JOptionPane.showMessageDialog(rootPane, "�� ������ ���� ��� �����������, ��������� ������ � Acrobat. �����������/Pdf_to_Xls/������");
					return;
				}
//				VBAMaros.callExcelMacro(new File("batch_calculator.xlsm"), new String[] {"!PickTheDataFileAuto","!Main"});
				SideApp.runExcel();
			}
		});
		btnNewButton.setBounds(10, 63, 186, 41);
		panel1.add(btnNewButton);
		panel1.updateUI();;
		
		
		final JButton btnSave = new JButton("��������� ������");
		btnSave.setForeground(new Color(47, 79, 79));
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					if (!new File(Main.absolut.getAbsolutePath()+"/dep_gateway/offer.xls").exists())
					{
						JOptionPane.showMessageDialog(null, "�� �� �������.");
						return ;
					}	
					int selection = JOptionPane.showConfirmDialog(btnSave, "������� ����� ������?");
					
					if (selection ==0){NewProjectPlasementToBase();}
					if (selection ==1){ProjectModificationPlasementToBase();}
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void ProjectModificationPlasementToBase() throws IOException, Exception {
				
				String projectId = JOptionPane.showInputDialog("������� ����� ����������� �������.");
				if (projectId == null){return;}
				TxtWriter.writeOfferComment(JOptionPane.showInputDialog("����������� � �����������"));
//				VBAMaros.callExcelMacro(new File("batch_calculator.xlsm"), new String[] {"SaveToAuto"});
				SpreadSheets.journalInsertChange(ExcelAPI.getOfferData(), projectId);
				JOptionPane.showMessageDialog(null, "����� ��������.");
				textPaneHints.setText("��� ������ id " + projectId + " ��������.");
				panel1.updateUI();
				FileUtils.cleanDirectory(new File(Main.absolut.getAbsolutePath()+"/dep_gateway/"));
			}

			private void NewProjectPlasementToBase() throws IOException, Exception {
			
				TxtWriter.writeOfferComment(JOptionPane.showInputDialog("����������� � �����������"));
				String projectName = JOptionPane.showInputDialog("������������ �������");
//				VBAMaros.callExcelMacro(new File("batch_calculator.xlsm"), new String[] {"SaveToAuto"});
				SpreadSheets.journalInsert(ExcelAPI.getOfferData(), projectName);
				JOptionPane.showMessageDialog(null, "������ ��������.");
				textPaneHints.setText("� ������ ��������� ������ � ������� ����� � �������� � ������������. �� ������ ������ ����� ������.");
				panel1.updateUI();
				FileUtils.cleanDirectory(new File(Main.absolut.getAbsolutePath()+"/dep_gateway/"));
			}
		});
		btnSave.setBounds(10, 115, 186, 41);
		panel1.add(btnSave);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(206, 11, 213, 282);
		panel1.add(scrollPane);
		
		textPaneHints = new JTextPane();
		textPaneHints.setFont(new Font("Tahoma", Font.PLAIN, 13));
		scrollPane.setViewportView(textPaneHints);
		
		textPaneHints.setText("������ ����� �������� ����, ����������� ������ � ������� "
				+ "  \".pdf\". ��������� �������� �������, � ������� ����������� ��������� ������ Xls_to_Pdf. "
				+ "�� ��������� ��������� ����� ������� ������� � ������ ������ \"����������\".");
		comboBox_type_of_journal = new JComboBox<String>(); // � ������ ����� ���������� �������� �������, �� ������ ������ �� ������, � ������ �� ����
		comboBox_type_of_journal.addItem(WorkJournal.selectionName);
		comboBox_type_of_journal.addItem(TestJournal.selectionName);
		comboBox_type_of_journal.setBounds(32, 273, 164, 20);
		panel1.add(comboBox_type_of_journal);
		panel1.updateUI();		
		
		panel2 = new JPanel(null);
		tabbedPane.addTab("Tab 2", panel2);
		
		panel3 = new JPanel(null);
		tabbedPane.addTab("�������� �������", panel3);
		panel3.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(273, 11, 146, 107);
		panel3.add(scrollPane_1);
		
		panel_1 = new JPanel();
		scrollPane_1.setViewportView(panel_1);
		lblCompanyLogo = new JLabel("");//CompanyLogo
		panel_1.add(lblCompanyLogo, BorderLayout.CENTER);;
		setResizable(false);
		
		btnGetImage = new JButton("��� �������");
		btnGetImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Input the URL address","Choose the file"};
				UploadTheLogo(JOptionPane.showInputDialog(null, null, "Choose upload method", JOptionPane.QUESTION_MESSAGE, null, options, null), options);	
			}
		});
		btnGetImage.setBounds(273, 160, 130, 23);
		panel3.add(btnGetImage);
		btnAddClient = new JButton("��� ������");
		btnAddClient.setBounds(273, 129, 130, 23);
		btnAddClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CollectingClientData();
				panel3.updateUI();
			}
		});
		
		panel3.add(btnAddClient);
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 253, 114);
		panel3.add(scrollPane_2);
		clModel = new ClientModel();
		table = new JTable(clModel);
		scrollPane_2.setViewportView(table);
		
		
		//���� ������, ����� ��� �� ����.
		comboBox = new JComboBox<String>();
		model = new DefaultComboBoxModel<String>();
		for(int i = 0; i< select.length; i++){
			model.addElement(select[i]);		
		}
		
		comboBox.setModel(model);
		comboBox.setBounds(273, 248, 130, 20);
		
		panel3.add(comboBox);
		
		searchTextPane = new JTextPane();
		searchTextPane.setBounds(273, 273, 130, 20);
		
		searchTextPane.setText("������... ");
		
		searchTextPane.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				
				if (searchTextPane.getText().length()<2){return;}
				searchTextPane.getText();
				try {
					Sql_communication.roughSearch(searchTextPane.getText());
					panel3.updateUI();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			public void keyPressed(KeyEvent e) {	
			}
			public void keyReleased(KeyEvent e) {	
			}
		});
		searchTextPane.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				String a = "";
				if (a.equals(searchTextPane.getText())){
					searchTextPane.setText("������...");
				}				
			}
			
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				searchTextPane.setText("");
			}
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				String a = "";
				if (a.equals(searchTextPane.getText())){
					searchTextPane.setText("������... ");
				}				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				searchTextPane.setText("");
			}
		});
		
		panel3.add(searchTextPane);
		
		btnNewButton_base_add = new JButton("���. � ����");
		btnNewButton_base_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int clientid = Sql_communication.post();
					BufferedImage input = Main.getImage();
					if (input == null) {return;}
					File outputFile = new File(Main.absolut.getAbsolutePath()+"/logos/id" + clientid + "_company_logo.png");
		        	ImageIO.write(input, "PNG", outputFile);
		        	JOptionPane.showMessageDialog(btnSave, "������ �" + clientid + " �������� � ����.");
		        	
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			
			}
		});
		btnNewButton_base_add.setBounds(273, 194, 130, 23);
		panel3.add(btnNewButton_base_add);
		
		spinner = new JSpinner();
		//��������� �������� ��������������
		spinner.setBounds(10, 130, 253, 20);
		
		spinner.setModel(new SpinnerModel() {
			
			@Override
			public void setValue(Object arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeChangeListener(ChangeListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getValue() {
				// TODO Auto-generated method stub
				return 1;
			}
			
			@Override
			public Object getPreviousValue() {
				// TODO Auto-generated method stub
				return 2;
			}
			
			@Override
			public Object getNextValue() {
				// TODO Auto-generated method stub
				return 3;
			}
			
			@Override
			public void addChangeListener(ChangeListener arg0) {
				// TODO Auto-generated method stub
				arg0.stateChanged(null);
			}
		});
		
		panel3.add(spinner);
		
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		mntmSaveFile = new JMenuItem(" Save file");
		mntmSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int reply = chooser.showSaveDialog(null);
				if (reply == JFileChooser.APPROVE_OPTION){
					Main.saveImage(chooser.getSelectedFile(), choice.getSelectedItem());
				}
			}
		});
		mnFile.add(mntmSaveFile);
		mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnOptions.add(mntmExit);
		setVisible(true);				
	}
	protected void CollectingClientData() {
		// collection of customer data
		String naming = JOptionPane.showInputDialog(Main.client.properties[0]);
		String address = JOptionPane.showInputDialog(Main.client.properties[1]);
		Object region = JOptionPane.showInputDialog(null, Main.client.properties[2], null, JOptionPane.QUESTION_MESSAGE, null, new String []{"����","���","���","������-���������� ��", "����������� ��", "��������� ��", "��������� ��", "����"}, null);
		String phone = JOptionPane.showInputDialog(Main.client.properties[3]);
		String email = JOptionPane.showInputDialog(Main.client.properties[4]);
		Object direction = JOptionPane.showInputDialog(null, Main.client.properties[5], null, JOptionPane.QUESTION_MESSAGE, null, new String []{"���������","�������������","��������/����������/���������","�������������", "�����", "���"}, null);
		
		Main.client.setNaming(naming);
		Main.client.setAddress(address);
		Main.client.setRegion(region.toString());
		Main.client.setPhone(phone);
		Main.client.seteMail(email);
		Main.client.setDirection(direction.toString());
//		try {
//			Sql_communication.post();
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		
	}

	
	
	protected void UploadTheLogo(Object selection, Object options[]) {
		// different ways of setting logo
		if (selection == options[0]){
			try {
				System.out.println(Main.getImage());
				Main.setImage(new URL(JOptionPane.showInputDialog("Place the Url of picture;")));
				if(Main.getImage()==null){return;}
				
				ImageIcon rawLogo=new ImageIcon(Main.getImage());  
	            Image image= rawLogo.getImage().getScaledInstance(100, 90, Image.SCALE_SMOOTH);
	            lblCompanyLogo.setIcon(new ImageIcon(image));
	            lblCompanyLogo.updateUI();	
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "invalid URL or no Internet connection.");	
			}
		}
		if (selection == options[1]){
			JFileChooser chooser = new JFileChooser();
			int reply = chooser.showOpenDialog(null);
			if(reply == JFileChooser.APPROVE_OPTION){
			Main.setImage(chooser.getSelectedFile());	
			if(Main.getImage()==null){return;}
			lblCompanyLogo.setIcon(new ImageIcon(Main.getImage()));
			lblCompanyLogo.updateUI();	
			}
		}
	}

	 public BufferedImage cropImage(BufferedImage src, Rectangle rect) {
	 BufferedImage dest = src.getSubimage(0, 0, rect.width, rect.height);
	      return dest; 
	 }
	 
	 public String getComboBox_type_of_journal(){
		
		 return String.valueOf(comboBox_type_of_journal.getSelectedItem());
	 }
	 public void clearFolders(File f){
		try {		
			FileUtils.cleanDirectory(f);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(rootPane, "�������� ����� � ������� ������");
		}
	 }
}

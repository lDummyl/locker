package SandCourt.main33;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Choice;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

import com.google.gdata.*;

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
	private String[] select = new String[] {"По имени", "По адресу"};
	private JTextPane searchTextPane;

	

	private static final String SPREADSHEET_SERVICE_URL
    = "https://spreadsheets.google.com/feeds/spreadsheets/private/full":
	
	public MainWindow(int width, int height) {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(468, 428);
		getContentPane().setLayout(new MigLayout("", "[432px]", "[329px]"));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, "cell 0 0,grow");
		
		panel1 = new JPanel(null);
		tabbedPane.addTab("Tab 1", panel1);
		
		panel2 = new JPanel(null);
		tabbedPane.addTab("Tab 2", panel2);
		
		panel3 = new JPanel(null);
		tabbedPane.addTab("Карточка клиента", panel3);
		panel3.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(273, 11, 146, 107);
		panel3.add(scrollPane_1);
		
		panel_1 = new JPanel();
		scrollPane_1.setViewportView(panel_1);
		lblCompanyLogo = new JLabel("");//CompanyLogo
		panel_1.add(lblCompanyLogo, BorderLayout.CENTER);;
		setResizable(false);
		
		btnGetImage = new JButton("Get Image");
		btnGetImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Input the URL address","Choose the file"};
				UploadTheLogo(JOptionPane.showInputDialog(null, null, "Choose upload method", JOptionPane.QUESTION_MESSAGE, null, options, null), options);	
			}
		});
		btnGetImage.setBounds(273, 160, 130, 23);
		panel3.add(btnGetImage);
		btnAddClient = new JButton("Add Client");
		btnAddClient.setBounds(273, 129, 130, 23);
		btnAddClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CollectingClientData();
				panel3.updateUI();
			}
		});
		panel3.add(btnAddClient);
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 253, 282);
		panel3.add(scrollPane_2);
		clModel = new ClientModel();
		table = new JTable(clModel);
		scrollPane_2.setViewportView(table);
		
		
		
		//поле поиска, через что мы ищем.
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
		
		searchTextPane.setText("Искать... ");
		
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
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				String a = "";
				if (a.equals(searchTextPane.getText())){
					searchTextPane.setText("Искать...");
				}				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				searchTextPane.setText("");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				String a = "";
				if (a.equals(searchTextPane.getText())){
					searchTextPane.setText("Искать... ");
				}				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				searchTextPane.setText("");
			}
		});
		
		panel3.add(searchTextPane);
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
		String naming = JOptionPane.showInputDialog("Company name:");
		String address = JOptionPane.showInputDialog("Add the adress:");
		Main.client.setNaming(naming);
		Main.client.setAddress(address);
		try {
			Sql_communication.post(naming, address);
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	protected void UploadTheLogo(Object selection, Object options[]) {
		// different ways of setting logo
		if (selection == options[0]){
			try {
				System.out.println(Main.getImage());
				Main.setImage(new URL(JOptionPane.showInputDialog("Place the Url of picture;")));
				if(Main.getImage()==null){return;}
				lblCompanyLogo.setIcon(new ImageIcon(Main.getImage()));
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
}

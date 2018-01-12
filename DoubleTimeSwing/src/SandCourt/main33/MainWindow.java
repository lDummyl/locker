package SandCourt.main33;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Choice;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainWindow extends JFrame {
	
	private JTabbedPane tabbedPane;
	private JPanel panel1;
	private JTextArea txtrEnterUrlHere;
	private JButton btnGetImage;
	private JButton btnGetFile;
	private Choice choice;
	private JPanel panel2;
	private JPanel panel3;
	private JButton btnView;
	private JLabel lblFormat;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmSaveFile;
	private JMenu mnOptions;
	private JMenuItem mntmExit;
	private JPanel panel;
	private JLabel imageLabel;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTable table;
	private JScrollPane scrollPane_2;
	private ClientModel clModel;

	public MainWindow(int width, int height) {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(468, 428);
		getContentPane().setLayout(new MigLayout("", "[432px]", "[329px]"));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, "cell 0 0,grow");
		
		panel1 = new JPanel(null);
		
		tabbedPane.addTab("Tab 1", panel1);
		
		txtrEnterUrlHere = new JTextArea();
		txtrEnterUrlHere.setText("Enter URL here...");
		txtrEnterUrlHere.setBounds(10, 52, 407, 84);
		panel1.add(txtrEnterUrlHere);
		
		btnGetImage = new JButton("Get Image");
		btnGetImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(Main.getImage());
					Main.setImage(new URL(txtrEnterUrlHere.getText()));
					System.out.println(Main.getImage());
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "invalid URL or no Internet connection.");
				}				}
		});
		btnGetImage.setBounds(10, 11, 130, 23);
		panel1.add(btnGetImage);
		
		btnGetFile = new JButton("Get File");
		btnGetFile.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int reply = chooser.showOpenDialog(null);
				if(reply == JFileChooser.APPROVE_OPTION){
					Main.setImage(chooser.getSelectedFile());
					
					//Main.setImage(chooser.getSelectedFile());
				};			}
		} );
		
		btnGetFile.setBounds(287, 11, 130, 23);
		panel1.add(btnGetFile);
		
		panel2 = new JPanel(null);
		tabbedPane.addTab("Tab 2", panel2);
		
		choice = new Choice();
		choice.setBounds(350, 10, 67, 20);
		panel2.add(choice);
		
		choice.add("png");
		choice.add("jpg");
		
		btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(Main.getImage()==null){return;}
				imageLabel.setIcon(new ImageIcon(Main.getImage()));
				imageLabel.updateUI();
			}
		});
		btnView.setBounds(10, 10, 89, 23);
		panel2.add(btnView);
		
		lblFormat = new JLabel("Format");
		lblFormat.setBounds(309, 14, 46, 14);
		panel2.add(lblFormat);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 43, 409, 250);
		panel2.add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		imageLabel = new JLabel("");
		panel.add(imageLabel, BorderLayout.CENTER);
		setResizable(false);
		
		////////////////////////////////////////////////////
		
		

		panel3 = new JPanel(null);
		tabbedPane.addTab("Карточка клиента", panel3);
		panel3.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(273, 11, 146, 118);
		panel3.add(panel_1);
		
		scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1);
		
		JLabel lblPicture = new JLabel("Picture");
		scrollPane_1.setViewportView(lblPicture);
		
		JButton btnAddClient = new JButton("Add Client");
		btnAddClient.setBounds(283, 140, 89, 23);
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
		
		
		//
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
		Main.client.setNaming(JOptionPane.showInputDialog("Company name:"));
		Main.client.setAddress(JOptionPane.showInputDialog("Add the adress:"));


	}
}

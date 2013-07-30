import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.ArrayList;


import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

import com.google.gson.Gson;


import java.awt.Font;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


public class MainWindow {

	private JFrame frame;
	JFileChooser choose;
	static BufferedReader in;
	static PrintWriter out;
	static JTextArea txtpnLog;
	public static ClientSender clientSender;
	public static ClientListener clientListener;
	private static Gson gson = new Gson();
	private static DefaultListModel<String> model;
	public static JList<String> list;
	public static JList<String> list_1;
	public static String currentSelectedDeviceSong;
	public static ArrayList<String> devicePlaylist;
	public static JScrollPane scrollPane;
	public static void main(String hostname, int port) {
		
		try {
			MainWindow window = new MainWindow();
			window.frame.setVisible(true);
			startClient(hostname, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void startClient(String ipAddress, int port)
	{
		try {
			// Connect to Server
			addChat("Attempting connection to server " + ipAddress + ":" + port);
			Socket socket = new Socket(ipAddress, port);
			in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter( new OutputStreamWriter(socket.getOutputStream()));
			addChat("Connected to server " + ipAddress + ":" + port);
		} catch (IOException ioe) {
			addChat("Can not establish connection to " + ipAddress + ":" + port);
			ioe.printStackTrace();
		}

		// Create and start Sender thread
		clientSender = new ClientSender(out);
		clientSender.setDaemon(true);
		clientSender.start();
		
		clientListener = new ClientListener(in);
		clientListener.setDaemon(true);
		clientListener.start();
		
		
	}
	public static void login(String username, String password)
	{
		Command output = new Command(301, password, username);
		String json = gson.toJson(output);
		clientSender.sendMessage(json);
		
	}
	public static void addChat(String message)
	{
		txtpnLog.setText(txtpnLog.getText()+message+"\n");
		txtpnLog.setCaretPosition(txtpnLog.getDocument().getLength());
	}
	public MainWindow() throws MalformedURLException {
		initialize();

		
		
	}

	private void initialize() {
		devicePlaylist = new ArrayList<String>();
		frame = new JFrame();
		frame.setBounds(100, 100, 1167, 877);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JList list = new JList();
		list.setBounds(10, 393, 307, 413);
		frame.getContentPane().add(list);
		
		JButton btnNewButton = new JButton("<<<");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Command playCommand = new Command(103, "previous", "previous");
				clientSender.sendCommand(playCommand);
			}
		});
		btnNewButton.setBounds(356, 581, 97, 67);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton(">>>");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Command playCommand = new Command(102, "next", "next");
				clientSender.sendCommand(playCommand);
			}
		});
		btnNewButton_1.setBounds(562, 581, 103, 67);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblOnComputer = new JLabel("On Computer");
		lblOnComputer.setBounds(96, 368, 82, 14);
		frame.getContentPane().add(lblOnComputer);
		
		JLabel lblOnAnroid = new JLabel("On Android");
		lblOnAnroid.setBounds(937, 17, 89, 14);
		frame.getContentPane().add(lblOnAnroid);
		
		JButton btnNewButton_2 = new JButton("Play");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(devicePlaylist.contains(currentSelectedDeviceSong)){
					Command playCommand = new Command(101, currentSelectedDeviceSong, "play it now");
					clientSender.sendCommand(playCommand);
				} else {
					Command playCommand = new Command(101, "play", "play it now");
					clientSender.sendCommand(playCommand);
				}
				
			}
		});
		btnNewButton_2.setBounds(463, 538, 89, 67);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Pause");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Command playCommand = new Command(104, "pause", "pause");
				clientSender.sendCommand(playCommand);
			}
		});
		btnNewButton_3.setBounds(463, 629, 89, 62);
		frame.getContentPane().add(btnNewButton_3);
		
		txtpnLog = new JTextArea();
		txtpnLog.setText("Log");
		txtpnLog.setBounds(234, 17, 532, 87);
		txtpnLog.setEditable(false);
		txtpnLog.setLineWrap(true);
		txtpnLog.setWrapStyleWord(true);
		txtpnLog.setAutoscrolls(true);
		JScrollPane sp = new JScrollPane(txtpnLog);
		sp.setSize(575, 171);
		sp.setLocation(10, 42);
		frame.getContentPane().add(sp);
		
		JLabel lblSongTitle = new JLabel("Song Title");
		lblSongTitle.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblSongTitle.setBounds(463, 337, 89, 39);
		frame.getContentPane().add(lblSongTitle);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Load");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmLoadDirectory = new JMenuItem("Load Directory");
		mnNewMenu.add(mntmLoadDirectory);
		
		JMenuItem mntmLoadFile = new JMenuItem("Load File");
		mnNewMenu.add(mntmLoadFile);
		
		model = new DefaultListModel<String>();
		list_1 = new JList<String>();
		list_1.setModel(model);
		list_1.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				Song temp = Song.songFactory(list_1.getSelectedValue().toString()," ");
				MainWindow.currentSelectedDeviceSong = list_1.getSelectedValue().toString() ;
			}
		});
		list_1.setBounds(1,1,200,200);
		scrollPane = new JScrollPane(list_1);
		scrollPane.setSize(356, 716);
		scrollPane.setLocation(763, 73);
		frame.getContentPane().add(scrollPane);


	}
	public static void updateClientList(ArrayList<String> playlist)
	{
		model = new DefaultListModel<String>();
		for(int i=0; i<playlist.size(); i++){
			model.addElement(new String(playlist.get(i)));
			devicePlaylist.add(new String(playlist.get(i)));
			
		}
		list_1.setModel(model);
		scrollPane.revalidate();
		list = new JList<String>(model);
		
	}
}

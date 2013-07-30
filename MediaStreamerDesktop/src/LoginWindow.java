import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField_2;
	private JTextField textField_3;
	private static LoginWindow frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new LoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public LoginWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 304, 380);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.main( textField_3.getText(), Integer.parseInt(textField_2.getText()));
				LoginWindow.frame.setVisible(false);
			}
		});
		btnLogin.setBounds(106, 299, 89, 23);
		contentPane.add(btnLogin);
		
		JLabel lblServerLogin = new JLabel("Server Login");
		lblServerLogin.setBounds(123, 11, 72, 14);
		contentPane.add(lblServerLogin);
		
		textField_2 = new JTextField();
		textField_2.setText("12345");
		textField_2.setBounds(72, 138, 157, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(138, 113, 46, 14);
		contentPane.add(lblPort);
		
		textField_3 = new JTextField();
		textField_3.setText("192.168.1.83");
		textField_3.setBounds(72, 81, 157, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblHostnameIp = new JLabel("Hostname / IP");
		lblHostnameIp.setBounds(110, 56, 89, 14);
		contentPane.add(lblHostnameIp);
	}

}

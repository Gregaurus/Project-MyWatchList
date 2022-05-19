import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginPage extends JFrame implements ActionListener{

	private JPanel northPanel, centerPanel, southPanel;
	private JLabel login, username, password;
	private JButton btnLogin, btnRegister;
	private JTextField tUsername;
	private JPasswordField tPassword;

	Connect con;

	private void init() {
		con = new Connect();

		//NORTH
		northPanel = new JPanel();
		login = new JLabel("LOGIN");
		login.setFont(new Font("Franklin Gothic", Font.BOLD, 24));
		login.setForeground(Color.BLUE);

		northPanel.add(login);

		//CENTER
		centerPanel = new JPanel(new GridLayout(2,2,20,30));
		centerPanel.setBorder(new EmptyBorder(50,35,50,35));
		username = new JLabel("Username");
		tUsername = new JTextField();
		password = new JLabel("Password");
		tPassword = new JPasswordField();

		centerPanel.add(username);
		centerPanel.add(tUsername);
		centerPanel.add(password);
		centerPanel.add(tPassword);

		//SOUTH
		southPanel = new JPanel();
		southPanel.setBorder(new EmptyBorder(0,35,10,35));
		btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(this);
		btnRegister = new JButton("I don't have an account");
		btnRegister.addActionListener(this);

		southPanel.add(btnLogin);
		southPanel.add(btnRegister);

		//ADD
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);

	}

	public LoginPage() {
		init();
		setSize(500,300);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("MyWatchList");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			String userTemp = tUsername.getText();
			String passwordTemp = String.valueOf(tPassword.getPassword());
			ResultSet rs;
			if (userTemp.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please input email!", "Alert", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (passwordTemp.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please input password!", "Alert", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				//validate if email and password are correct according to the database
				try {
					boolean userCorrect = false;
					boolean passCorrect = false;
					String queryUser = "SELECT name FROM users";
					rs = con.executeQuery(queryUser);
					while (rs.next()) {
						String name = rs.getString("name");
						if (userTemp.equals(name)) {
							userCorrect = true;
							break;
						}
					}
					if (userCorrect) {
						String queryPassword = "SELECT password FROM users WHERE name='%s'";
						queryPassword = String.format(queryPassword, userTemp);
						rs = con.executeQuery(queryPassword);
						rs.next();
						String password = rs.getString("password");
						if (passwordTemp.equals(password)) {
							passCorrect = true;
						}
					}
					if (userCorrect && passCorrect) {
						String queryUsername = "SELECT name, id FROM users WHERE name='%s'";
						queryUsername = String.format(queryUsername, userTemp);
						rs = con.executeQuery(queryUsername);
						rs.next();
						int userid = rs.getInt("id");
						JOptionPane.showMessageDialog(this, "Welcome, " + userTemp, "Message", JOptionPane.INFORMATION_MESSAGE);
						
						//CHANGE WINDOW
						new MainForm(userTemp, userid);
						dispose();

					} else {
						JOptionPane.showMessageDialog(this, "Incorrect Email or Password", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					}			
				} catch (Exception e2) {
					e2.printStackTrace();
				}		
			}
		} else if (e.getSource() == btnRegister) {
			new RegisterPage().setVisible(true);
			dispose();
		}
	}
}

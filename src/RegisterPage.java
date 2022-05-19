import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RegisterPage extends JFrame implements ActionListener{
	
	private JPanel northPanel, centerPanel, southPanel, genderPanel;
	private JLabel lblRegister, lblName, lblGender, lblBio, lblAge, lblPassword;
	private JTextField txtName, txtBio, txtAge;
	private JPasswordField txtPassword;
	private JRadioButton btnMale, btnFemale;
	private JButton btnRegister, btnLogin;
	private ButtonGroup bgGender;
	
	Connect con;
	
	private void init() {
		con = new Connect();
		
		//NORTH
		northPanel = new JPanel();
		lblRegister = new JLabel("REGISTER");
		lblRegister.setFont(new Font("Franklin Gothic", Font.BOLD, 24));
		lblRegister.setForeground(Color.RED);
		
		northPanel.add(lblRegister);
		
		//CENTER
		centerPanel = new JPanel(new GridLayout(5,2,20,30));
		centerPanel.setBorder(new EmptyBorder(50,35,50,35));
		lblName = new JLabel("Username*");
		txtName = new JTextField();
		lblPassword = new JLabel("Password*");
		txtPassword = new JPasswordField();
		lblAge = new JLabel("Age*");
		txtAge = new JTextField();
		lblBio = new JLabel("Bio");
		txtBio = new JTextField();
		lblGender = new JLabel("Gender*");
		
		//GENDER PANEL
		genderPanel = new JPanel(new GridLayout(1, 2));
		btnMale = new JRadioButton("Male");
		btnMale.setActionCommand("Male");
		btnFemale = new JRadioButton("Female");
		btnFemale.setActionCommand("Female");
		bgGender = new ButtonGroup();
		bgGender.add(btnMale);
		bgGender.add(btnFemale);
		
		genderPanel.add(btnMale);
		genderPanel.add(btnFemale);
		
		centerPanel.add(lblName);
		centerPanel.add(txtName);
		centerPanel.add(lblPassword);
		centerPanel.add(txtPassword);
		centerPanel.add(lblAge);
		centerPanel.add(txtAge);
		centerPanel.add(lblBio);
		centerPanel.add(txtBio);
		centerPanel.add(lblGender);
		centerPanel.add(genderPanel);
		
		//SOUTH
		southPanel = new JPanel();
		southPanel.setBorder(new EmptyBorder(0,35,10,35));
		btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(this);
		btnLogin = new JButton("I have an account");
		btnLogin.addActionListener(this);
		
		southPanel.add(btnRegister);
		southPanel.add(btnLogin);
		
		
		//ADD
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
	}

	public RegisterPage() {
		init();
		setSize(500,600);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("MyWatchList");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void resetData( ) {
		txtName.setText("");
		txtPassword.setText("");
		txtAge.setText("");
		txtBio.setText("");
		bgGender.clearSelection();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegister) {
			
			String username = txtName.getText();
			String password = String.valueOf(txtPassword.getPassword());
			String age = txtAge.getText();
			String bio = txtBio.getText();
			String gender = "";
			
			if (username.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Username cannot be empty!", "Alert", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (password.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Password cannot be empty!", "Alert", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (age.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Age cannot be empty!", "Alert", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (!btnMale.isSelected() && !btnFemale.isSelected()) {
				JOptionPane.showMessageDialog(null, "Gender must be selected!", "Alert", JOptionPane.ERROR_MESSAGE);
				return;
			} else 
				gender = bgGender.getSelection().getActionCommand();
			
			
			//INSERT NEW USER
			if (bio.isEmpty()) {
				bio = "-";
			}
			String query = String.format("INSERT INTO users (name, password, gender, bio, age) VALUES('" + username + "', '" +password + "', '" + gender + "', '" + bio + "', '" + age + "')");
			boolean success = con.executeUpdate(query);
			
			if (success) {
				JOptionPane.showMessageDialog(null, "Registration Successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
				new LoginPage().setVisible(true);
				dispose();
			}else {
				JOptionPane.showMessageDialog(null, "Registration Failed!", "Alert", JOptionPane.ERROR_MESSAGE);
				resetData();
				return;
			}
			
		}else if (e.getSource() == btnLogin) {
			new LoginPage().setVisible(true);
			dispose();
		}
		
	}

}

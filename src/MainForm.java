import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainForm extends JFrame implements ActionListener{
	
	private JDesktopPane dPane;
	private JMenuBar mb;
	private JMenu userMenu, myListMenu;
	private JMenuItem miLogout, miAddlist, miMylist;
	
	private String username;
	private AddWatchList awl;
	private MyWatchList mwl;
	private int userid;
	
	
	private void init() {
		dPane = new JDesktopPane();
		setContentPane(dPane);
		
		mb = new JMenuBar();
		
		userMenu = new JMenu("User");
		myListMenu = new JMenu("MyWatchList");
		
		miLogout = new JMenuItem("Log Out");
		miLogout.addActionListener(this);
		miAddlist = new JMenuItem("Add Movies/Series");
		miAddlist.addActionListener(this);
		miMylist = new JMenuItem("View My List");
		miMylist.addActionListener(this);
		
		//USERMENU
		userMenu.add(miLogout);
		
		//MYLISTMENU
		myListMenu.add(miAddlist);
		myListMenu.add(miMylist);
		
		//ADD
		mb.add(userMenu);
		mb.add(myListMenu);
		setJMenuBar(mb);
	}

	public MainForm(String username, int userid) {
		this.userid = userid;
		this.username = username;
		init();
		setVisible(true);
		setSize(1366,768);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle(this.username + "'s MyWatchList");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == miLogout) {
			new LoginPage().setVisible(true);
			dispose();
		} else if (e.getSource() == miAddlist) {
			awl = new AddWatchList(userid);
			awl.setVisible(true);
			dPane.add(awl);
		} else if (e.getSource() == miMylist) {
			mwl = new MyWatchList(userid);
			mwl.setVisible(true);
			dPane.add(mwl);
		}
		
	}

}

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AddWatchList extends JInternalFrame implements ActionListener, MouseListener{

	private JTable tableM, tableS;
	private JLabel lblMovies, lblSeries;
	private JPanel northPanel, centerPanel, southPanel;
	private DefaultTableModel dtmM, dtmS;
	private JScrollPane spM, spS;
	private JButton btnAdd;
	private boolean clickedM = false, clickedS = false;
	private String type, tempName;
	private int episodes, userid;

	Connect con;
	ResultSet rs;

	private void init() {
		con = new Connect();
		tableM = new JTable();
		tableM.addMouseListener(this);
		tableS = new JTable();
		tableS.addMouseListener(this);

		refreshTable();

		//NORTH
		northPanel = new JPanel(new GridLayout(1,2));
		lblMovies = new JLabel("Movies");
		lblSeries = new JLabel("Series");

		northPanel.add(lblMovies);
		northPanel.add(lblSeries);

		//CENTER
		centerPanel = new JPanel(new GridLayout(1,2));
		spM = new JScrollPane(tableM);
		spS = new JScrollPane(tableS);

		centerPanel.add(spM);
		centerPanel.add(spS);

		//SOUTH
		southPanel = new JPanel();
		btnAdd = new JButton("SELECT");
		btnAdd.addActionListener(this);
		
		southPanel.add(btnAdd);

		//ADD
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}

	private void refreshTable() {
		//TABLE 1
		Object[] columnNamesM = {"Title", "Producer", "Studios", "Genres", "Runtime", "Rating", "Score"};

		dtmM = new DefaultTableModel(columnNamesM, 0);

		rs = con.executeQuery("SELECT * FROM movies");

		try {
			while(rs.next()) {
				String title = rs.getString("title");
				String producer = rs.getString("producer");
				String studio = rs.getString("studio");
				String genre = rs.getString("genre");
				String runtime = rs.getString("runtime");
				String rating = rs.getString("rating");
				String score = rs.getString("score");


				Vector<Object> rowData = new Vector<>();
				rowData.add(title);
				rowData.add(producer);
				rowData.add(studio);
				rowData.add(genre);
				rowData.add(runtime);
				rowData.add(rating);
				rowData.add(score);

				dtmM.addRow(rowData);
			}
			tableM.setModel(dtmM);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//TABLE 2
		Object[] columnNamesS = {"Title", "Producer", "Studios", "Genres", "Runtime", "Rating", "Score", "Episodes"};

		dtmS = new DefaultTableModel(columnNamesS, 0);

		rs = con.executeQuery("SELECT * FROM series");

		try {
			while(rs.next()) {
				String title = rs.getString("title");
				String producer = rs.getString("producer");
				String studio = rs.getString("studio");
				String genre = rs.getString("genre");
				String runtime = rs.getString("runtime");
				String rating = rs.getString("rating");
				String score = rs.getString("score");
				String episode = rs.getString("episode");


				Vector<Object> rowData = new Vector<>();
				rowData.add(title);
				rowData.add(producer);
				rowData.add(studio);
				rowData.add(genre);
				rowData.add(runtime);
				rowData.add(rating);
				rowData.add(score);
				rowData.add(episode);

				dtmS.addRow(rowData);
			}
			tableS.setModel(dtmS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public AddWatchList(int userid) {
		this.userid = userid;
		init();
		setSize(1300, 700);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setTitle("Add to MyWatchList");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {
			if (clickedM == true) {
				type = "Movies";
				tempName = tableM.getValueAt(tableM.getSelectedRow(), 0).toString();
			} else if (clickedS == true) {
				type = "Series";
				tempName = tableS.getValueAt(tableS.getSelectedRow(), 0).toString();
			} else {
				JOptionPane.showMessageDialog(this, "Please Select Any Movies/Series!", "Message", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			new AddList(type,tempName,episodes, userid).setVisible(true);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == tableM) {
			tableS.clearSelection();
			episodes = 1;
			clickedS = false;
			clickedM = true;
		} 
		if (e.getSource() == tableS) {
			tableM.clearSelection();
			episodes = Integer.parseInt(tableS.getValueAt(tableS.getSelectedRow(), 7).toString());
			clickedM = false;
			clickedS = true;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

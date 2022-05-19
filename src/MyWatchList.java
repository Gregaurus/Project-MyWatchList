import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

public class MyWatchList extends JInternalFrame implements MouseListener, ChangeListener, ActionListener{

	private JPanel centerPanel, rightPanel, rightBtmPanel, northPanel, southPanel;
	private JLabel status, score, comment, episode, uName, uAge, uGender, uBio, iAge, iGender, iBio, iName, title;
	private JComboBox<Object> cbStatus, cbScore;
	private JTextField txtComment;
	private JSpinner sEpisode;
	private SpinnerModel spmEpisode;
	private JTable table;
	private DefaultTableModel dtm;
	private JScrollPane sp;
	private JButton btnUpdate, btnDelete;

	private boolean dataselected;
	private int totalepisodes, userid;
	private String showName;

	Connect con;
	ResultSet rs;

	private void init() {
		con = new Connect();
		table = new JTable();
		table.addMouseListener(this);

		refreshTable();
		
		
		//NORTH PANEL
		//NAMA Age Gender BIO
		northPanel = new JPanel(new GridLayout(4,2));
		northPanel.setBorder(new EmptyBorder(20, 35, 10, 35));
		userInit();
		northPanel.add(iName);
		northPanel.add(uName);
		northPanel.add(iAge);
		northPanel.add(uAge);
		northPanel.add(iGender);
		northPanel.add(uGender);
		northPanel.add(iBio);
		northPanel.add(uBio);
		

		//RIGHT BTM PANEL
		rightBtmPanel = new JPanel(new GridLayout(1,2));
		btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(this);
		btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(this);
		
		rightBtmPanel.add(btnUpdate);
		rightBtmPanel.add(btnDelete);
		

		//RIGHT PANEL
		//status score comment episodes + button
		rightPanel = new JPanel(new GridLayout(9,1));
		status = new JLabel("Status");
		Object[] cbArraySt = {"Watching", "Completed", "On-Hold", "Dropped", "Plan to Watch"};
		cbStatus = new JComboBox<>(cbArraySt);
		cbStatus.addActionListener(this);
		score = new JLabel("Score(1-10)");
		Object[] cbArraySc = {"(10) Masterpiece", "(9) Great", "(8) Very Good", "(7) Good", "(6) Fine", "(5) Average", "(4) Bad", "(3) Very Bad", "(2) Horrible", "(1) Appalling"};
		cbScore = new JComboBox<>(cbArraySc);
		comment = new JLabel("Comment");
		txtComment = new JTextField();
		episode = new JLabel("Episodes Watched");
		sEpisode = new JSpinner();
		sEpisode.addChangeListener(this);
		spmEpisode = new SpinnerNumberModel(1, 1, 100, 1);
		sEpisode.setModel(spmEpisode);

		rightPanel.add(status);
		rightPanel.add(cbStatus);
		rightPanel.add(score);
		rightPanel.add(cbScore);
		rightPanel.add(comment);
		rightPanel.add(txtComment);
		rightPanel.add(episode);
		rightPanel.add(sEpisode);
		rightPanel.add(rightBtmPanel);

		//CENTER
		centerPanel = new JPanel(new GridLayout(1, 2));
		centerPanel.setBorder(new EmptyBorder(0, 35, 10, 35));
		sp = new JScrollPane(table);

		centerPanel.add(sp);
		centerPanel.add(rightPanel);
		
		//SOUTH
		southPanel = new JPanel(new GridBagLayout());
		title = new JLabel(showName);
		title.setFont(new Font("Comic Sans", Font.BOLD, 24));
		title.setForeground(Color.MAGENTA);
		
		southPanel.add(title);

		//ADD
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);

	}

	private void userInit() {
		String name="", gender="", bio="";
		int age=0;
		rs = con.executeQuery("SELECT * FROM users WHERE id = '" + userid + "'");
		
		try {
			rs.next();
			name = rs.getString("name");
			age = rs.getInt("age");
			gender = rs.getString("gender");
			bio = rs.getString("bio");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String showAge = String.valueOf(age) + " years old";
		showName = name + "'s MyWatchList";
		
		iName = new JLabel("Name: ");
		uName = new JLabel(name);
		iAge = new JLabel("Age: ");
		uAge = new JLabel(showAge);
		iGender = new JLabel("Gender: ");
		uGender = new JLabel(gender);
		iBio = new JLabel("Bio: ");
		uBio = new JLabel(bio);
	}

	private void refreshTable() {
		//TABLE 1
		Object[] columnNames = {"Title", "Status", "Score", "Comments", "type", "episodes"};

		dtm = new DefaultTableModel(columnNames, 0);

		rs = con.executeQuery("SELECT * FROM movielist WHERE users_id = '" + userid + "'");

		try {
			while(rs.next()) {
				String title = rs.getString("title");
				String status = rs.getString("status");
				int score = rs.getInt("score");
				String comment = rs.getString("comments");
				String type = "Movie";
				String episodes = "1/1";

				//fix
				String showScore = String.valueOf(score) + "/10"; 

				//find episodes

				Vector<Object> rowData = new Vector<>();
				rowData.add(title);
				rowData.add(status);
				rowData.add(showScore);
				rowData.add(comment);
				rowData.add(type);
				rowData.add(episodes);


				dtm.addRow(rowData);
			}
			table.setModel(dtm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rs = con.executeQuery("SELECT * FROM serieslist JOIN series ON serieslist.series_id = series.id WHERE users_id = '" + userid + "'");

		try {
			while(rs.next()) {
				String title = rs.getString("title");
				String status = rs.getString("status");
				int score = rs.getInt("score");
				String comment = "-";
				String type = "Series";
				int episodes = rs.getInt("episode");
				totalepisodes = rs.getInt(16);

				//fix
				String showScore = String.valueOf(score) + "/10"; 
				String showEpisode = String.valueOf(episodes) + "/" + totalepisodes;

				Vector<Object> rowData = new Vector<>();
				rowData.add(title);
				rowData.add(status);
				rowData.add(showScore);
				rowData.add(comment);
				rowData.add(type);
				rowData.add(showEpisode);

				dtm.addRow(rowData);
			}
			table.setModel(dtm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public MyWatchList(int userid) {
		this.userid = userid;
		init();
		setSize(1300, 700);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setTitle("MyWatchList");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		dataselected = true;
		int episodes=0, score =0;
		String tempTitle = table.getValueAt(table.getSelectedRow(), 0).toString();
		String type = table.getValueAt(table.getSelectedRow(), 4).toString();
		if (type.equals("Movie")) {
			spmEpisode = new SpinnerNumberModel(1, 1, 1, 1);
			rs = con.executeQuery("SELECT * FROM movielist JOIN movies ON movielist.movies_id = movies.id WHERE movies.title = '" + tempTitle + "'");
			try {
				rs.next();
				score = rs.getInt(4);
			} catch (SQLException x) {
				x.printStackTrace();
			}
		} else {
			rs = con.executeQuery("SELECT * FROM serieslist JOIN series ON serieslist.series_id = series.id WHERE series.title = '" + tempTitle + "' AND users_id = " + userid);
			try {
				rs.next();
				//EPISODE
				episodes = rs.getInt(5);
				score = rs.getInt(4);
				totalepisodes = rs.getInt(16);
			} catch (SQLException x) {
				x.printStackTrace();
			}
			spmEpisode = new SpinnerNumberModel(episodes, 1, totalepisodes, 1);
		}
		sEpisode.setModel(spmEpisode);

		//score
		int showScore = 10 - score;
		//STATUS
		String status = table.getValueAt(table.getSelectedRow(), 1).toString();
		//COMMENT
		String comment = table.getValueAt(table.getSelectedRow(), 3).toString();

		//set STATUS SCORE COMMENT
		cbStatus.setSelectedItem(status);
		cbScore.setSelectedIndex(showScore);
		txtComment.setText(comment);


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

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == sEpisode) {
			if (Integer.parseInt(sEpisode.getValue().toString()) != totalepisodes) {
				cbStatus.setSelectedIndex(0);
			}else
				cbStatus.setSelectedIndex(1);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnUpdate) {
			if (dataselected == false) {
				JOptionPane.showMessageDialog(this, "Please select One", "Message", JOptionPane.INFORMATION_MESSAGE);
			} else {
				//Update Movie
				//status score comment episode
				int score = 10 - cbScore.getSelectedIndex();
				String status = cbStatus.getSelectedItem().toString();
				String comment = txtComment.getText();
				int episode = Integer.parseInt(sEpisode.getValue().toString());
				//check movie or series
				String type = table.getValueAt(table.getSelectedRow(), 4).toString();
				if (type.equals("Movie")) {
					//grab movielistid 
					int movielistid=0;
					String tempTitle = table.getValueAt(table.getSelectedRow(), 0).toString();
					String queryMovieId = "SELECT * FROM movielist WHERE title = '" + tempTitle + "' AND users_id = " + userid;
					rs = con.executeQuery(queryMovieId);
					try {
						rs.next();
						movielistid = rs.getInt("id");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					//UPDATE QUERY
					String query = "UPDATE movielist SET status = '" + status + "', score = '" + score + "', comments = '" + comment + "' WHERE movielist.id = '" + movielistid + "' AND users_id = " + userid;
					boolean update = con.executeUpdate(query);
					
					if (update) {
						JOptionPane.showMessageDialog(this, "Update Successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
					}else
						JOptionPane.showMessageDialog(this, "Update Failed!", "Error", JOptionPane.ERROR_MESSAGE);
					refreshTable();
					
				} else {
					//check klo dia coba comment
					if (!comment.equals("-")) {
						JOptionPane.showMessageDialog(this, "You Cannot Comment on a TV Series!", "Message", JOptionPane.INFORMATION_MESSAGE);
					} else {
						//grab movielistid 
						int serieslistid=0;
						String tempTitle = table.getValueAt(table.getSelectedRow(), 0).toString();
						String queryseriesId = "SELECT * FROM serieslist WHERE title = '" + tempTitle + "' AND users_id = " + userid;
						rs = con.executeQuery(queryseriesId);
						try {
							rs.next();
							serieslistid = rs.getInt("id");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						//UPDATE QUERY
						String query = "UPDATE serieslist SET status = '" + status + "', score = '" + score + "', episode = '" + episode + "' WHERE serieslist.id = '" + serieslistid + "'";
						boolean update = con.executeUpdate(query);
						
						if (update) {
							JOptionPane.showMessageDialog(this, "Update Successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
						}else
							JOptionPane.showMessageDialog(this, "Update Failed!", "Error", JOptionPane.ERROR_MESSAGE);
						refreshTable();
					}
				}
						
				reset();
			}
		}
		
		if (e.getSource() == cbStatus) {
			String type = table.getValueAt(table.getSelectedRow(), 4).toString();
			if (type.equals("Movie")) {
				
			} else {
				if (cbStatus.getSelectedItem().toString().equals("Completed")) {
					sEpisode.setValue(totalepisodes);
				}
			}
		}
		if (e.getSource() == btnDelete) {
			if (dataselected == false) {
				JOptionPane.showMessageDialog(this, "Please select One", "Message", JOptionPane.INFORMATION_MESSAGE);
			} else {
				
				//delete data
				//check
				String type = table.getValueAt(table.getSelectedRow(), 4).toString();
				String tempTitle = table.getValueAt(table.getSelectedRow(), 0).toString();
				boolean deleteMovie = false, deleteSeries = false, check;
				if (type.equals("Movie")) {
					String query = "DELETE FROM movielist WHERE title = '" + tempTitle + "'";
					deleteMovie = con.executeUpdate(query);
				} else {
					String query = "DELETE FROM serieslist WHERE title = '" + tempTitle + "'";
					deleteSeries = con.executeUpdate(query);
				}
				if (JOptionPane.showConfirmDialog(this, "Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					check = true;
				} else {
					check = false;
				}
				if (deleteSeries && check|| deleteMovie && check) {
					JOptionPane.showMessageDialog(this, "Delete Successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
					reset();
					refreshTable();
				}else
					JOptionPane.showMessageDialog(this, "Delete Failed!", "Message", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void reset() {
		dataselected = false;
		table.getSelectionModel().clearSelection();
	}
}

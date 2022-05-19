import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AddList extends JFrame implements ActionListener, ChangeListener{
	
	private JPanel northPanel, centerPanel, southPanel;
	private JLabel mTitle, status, score, comment, episode;
	private JTextField txtComment;
	private JComboBox<Object> cbStatus, cbScore;
	private JSpinner sEpisode;
	private SpinnerModel spmEpisode;
	private JButton btnSubmit;
	
	
	private int episodes, userid;
	private String tempName, type;
	
	Connect con;
	ResultSet rs;
	
	private void init() {
		con = new Connect();
		
		//NORTH
		northPanel = new JPanel();
		
		mTitle = new JLabel(tempName);
		mTitle.setFont(new Font("Didot", Font.BOLD, 24));
		northPanel.add(mTitle);
		
		//CENTER
		centerPanel = new JPanel(new GridLayout(3, 2,20,20));
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
		
		centerPanel.add(status);
		centerPanel.add(cbStatus);
		centerPanel.add(score);
		centerPanel.add(cbScore);
		if (type.equals("Movies")) {
			centerPanel.add(comment);
			centerPanel.add(txtComment);
		} else {
			sEpisode = new JSpinner();
			sEpisode.addChangeListener(this);
			spmEpisode = new SpinnerNumberModel(1, 1, episodes, 1);
			sEpisode.setModel(spmEpisode);
			centerPanel.add(episode);
			centerPanel.add(sEpisode);
		}
		
		
		//SOUTH
		southPanel = new JPanel();
		btnSubmit = new JButton("SAVE");
		btnSubmit.addActionListener(this);
		
		southPanel.add(btnSubmit);
		
		//ADD
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}

	public AddList(String type, String tempName, int episodes, int userid) {
		this.userid = userid;
		this.episodes = episodes;
		this.type = type;
		this.tempName = tempName;
		init();
		setSize(500,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Add " + type);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSubmit) {
			//title status score comment episode movieid seriesid
			String status = cbStatus.getSelectedItem().toString();
			int score = 0;
			score = 10 - cbScore.getSelectedIndex();
			
			if (type.equals("Movies")) {
				//movie title and movieid
				String querySearch = "SELECT * FROM movies WHERE title = '" + tempName + "'";
				rs = con.executeQuery(querySearch);
				String title="";
				int movieid = 0;
				try {
					while (rs.next()) {
						title = rs.getString("title");
						movieid = rs.getInt("id");
					}
				} catch (SQLException x) {
					x.printStackTrace();
				}
				//comment
				String comment = txtComment.getText();
				
				//insert
				String queryInsert = String.format("INSERT INTO movielist(title, status, score, comments, movies_id, users_id) VALUES('%s', '%s', '%d', '%s', '%d', '%d')", title, status, score, comment, movieid, userid);
				boolean insert = con.executeUpdate(queryInsert);
				
				if (insert) {
					JOptionPane.showMessageDialog(this, "Movie Successfully Added!", "Message", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Movie Failed to be Added!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
			} else {
				//series title and seriesid
				String querySearch = "SELECT * FROM series WHERE title = '" + tempName + "'";
				rs = con.executeQuery(querySearch);
				String title="";
				int seriesid = 0;
				try {
					while (rs.next()) {
						title = rs.getString("title");
						seriesid = rs.getInt("id");
					}
				} catch (SQLException x) {
					x.printStackTrace();
				}
				
				//get episodes
				int eps = Integer.parseInt(sEpisode.getValue().toString());
				
				//insert
				String queryInsert = String.format("INSERT INTO serieslist(title, status, score, episode, series_id, users_id) VALUES('%s', '%s', '%d', '%s', '%d', '%d')", title, status, score, eps, seriesid, userid);
				boolean insert = con.executeUpdate(queryInsert);
				
				if (insert) {
					JOptionPane.showMessageDialog(this, "Series Successfully Added!", "Message", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Series Failed to be Added!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
			}
			
		}
		if (e.getSource() == cbStatus) {
			if (type.equals("Movies")) {
				
			} else {
				if (cbStatus.getSelectedItem().toString().equals("Completed")) {
					sEpisode.setValue(episodes);
				}
			}
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == sEpisode) {
			if (Integer.parseInt(sEpisode.getValue().toString()) != episodes) {
				cbStatus.setSelectedIndex(0);
			}else
				cbStatus.setSelectedIndex(1);
		}
	}

}

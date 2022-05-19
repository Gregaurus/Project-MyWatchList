import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	private Connection con;
	private Statement stat, tempStat;
	ResultSet rs = null;
	
	
	public Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mywatchlist", "root", "");

			tempStat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);
			stat = con.createStatement();
		} catch (Exception e) {
			System.out.println("Connect gagal");
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery2(String query) {
		try {
			rs = tempStat.executeQuery(query); //pake scroll insens sama concur read
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet executeQuery(String query) {
		try {
			rs = stat.executeQuery(query); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public boolean executeUpdate(String query) {
		try {
			stat.executeUpdate(query); 
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public PreparedStatement preparedStatement(String query) {
		  PreparedStatement ps = null;
		  
		  try {
		   ps = con.prepareStatement(query);
		  } catch (SQLException e) {
		   e.printStackTrace();
		  }
		  return ps;
		 }
}

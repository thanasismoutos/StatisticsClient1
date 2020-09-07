import java.sql.*;

public class StatisticsClient {

	private static final String connURL = "jdbc:mysql://localhost:3306/page_visits";
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String connUser = "root";
	private static final String connPasswd = "root";

	public static void main(String[] args) {

		if (args.length == 0 || args[0].equals("-help")) {
			help();
		} else if (args[0].equals("-list")) {
			try {
				String connectionURL = "jdbc:mysql://localhost:3306/page_visits?serverTimezone=UTC"; 
				Connection connection = StatisticsDB; 
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); 
				Connection conn = DriverManager.getConnection(connURL, connUser, connPasswd);

				/*
				 * generate statistics
				 */
				Statement stmt1 = conn.createStatement();
				// calculate number of visits per shape
				ResultSet rs1 = stmt1
						.executeQuery("SELECT SHAPE, COUNT(*) NUM FROM PAGE_VISITS GROUP BY SHAPE ASC ORDER BY SHAPE");

				Statement stmt2 = conn.createStatement();
				// calculate visit timestamps per shape
				ResultSet rs2 = stmt2.executeQuery("SELECT SHAPE, TS FROM PAGE_VISITS ORDER BY SHAPE ASC, TS ASC");

				// for each shape print its name and number of visits, followed by the list of
				// visit timestamps
				while (rs1.next()) {
					int num = rs1.getInt("NUM");
					System.out.println(rs1.getString("SHAPE") + ": " + num + " visit(s).");
					for (int idx = 0; idx < num; idx++) {
						rs2.next();
						System.out.println("        " + rs2.getTimestamp("TS"));
					}
				}

				rs1.close();
				stmt1.close();
				rs2.close();
				stmt2.close();
				conn.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (args[0].equals("-clear")) {
			try {
				// obtain a connection to the DB, use DB driver, URL, credentials
				String connectionURL = "jdbc:mysql://localhost:3306/page_visits?serverTimezone=UTC"; 
				Connection connection = StatisticsDB; 
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); 
				Connection conn = DriverManager.getConnection(connURL, "root", "root");

				Statement stmt = conn.createStatement();
				stmt.executeUpdate("TRUNCATE PAGE_VISITS");
				stmt.close();
				conn.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else
			help();
	}

	private static void help() {
		System.out.println("Usage:");
		System.out.println("    java StatisticsClient -help");
		System.out.println("    java StatisticsClient -show");
		System.out.println("    java StatisticsClient -clear");
	}
}

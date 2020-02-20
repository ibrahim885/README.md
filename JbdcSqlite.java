/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ms3codechallenge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author abdul363
 */
public class JbdcSqlite {
    
static Connection con = null;

	public static Connection getLocalConnection() {
		try {
			//Sqlite URL along with database name 
		
			String url = "jdbc:sqlite:Ms3customer.db";
			
			// create a connection to the database
			con = DriverManager.getConnection(url);
			
			Statement st = con.createStatement();
			String sql = "CREATE TABLE Records(\n" + 
					"   id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" + 
					"   fname NVARCHAR(20) NOT NULL, lname  NVARCHAR(20) NOT NULL,website NVARCHAR(60) NOT NULL,\n" + 
					"   gender NVARCHAR(20) NOT NULL,img NVARCHAR(120) NOT NULL,company NVARCHAR(20) NOT NULL,\n" + 
					"   charges REAL ,h_column Boolean ,i_column Boolean  ,city NVARCHAR(30) NOT NULL\n" + 
					")";
			st.executeQuery(sql);
			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {//if any issue arise then exception will be thrown
			System.out.println(e.getMessage());
		}

		return con;//return connection object being created

	}

	/*
	 *  close out the connection after the work is over
	 */
	public static void close() {
		try {
			if (con != null) {//check if connection is actually established
				//if yes then close connection now
				con.close();
			}

		} catch (SQLException e) {//if exception arises
			System.out.println(e.getMessage());
		}
	}
}


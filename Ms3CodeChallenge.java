/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ms3codechallenge;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author abdul363
 */
public class Ms3CodeChallenge {

   static int lineCount = 0;

	public static void main(String[] s) {
		String fileName = "ms3Interview.csv";
		//read the csv file
		ArrayList<Ms3Person> personsList = readCSV(fileName);
		//conncet will sqliteDb & insert records in Persons table
		connectWithSqlDB(personsList);// personslist
	}

	private static void connectWithSqlDB(ArrayList<Ms3Person> personsList) {
		try {
			Connection con = JbdcSqlite.getLocalConnection();
			System.out.println(con);
			if (con != null) {
				System.out.println("connection established");
				
				//insert query which will compile once
				//then we will execute the statement in Batch
				String sqlQuery = "INSERT OR REPLACE INTO Records(id,fname,lname,website,gender,img,company,charges,h_column,i_column,city) "
						+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement pst = con.prepareStatement(sqlQuery);
				insertDataInSqlite(pst, personsList);
			} else {
				System.out.println("failed..");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			JbdcSqlite.close();
		}
	}

	private static void insertDataInSqlite(PreparedStatement pst, ArrayList<Ms3Person> personList) {
		
		System.out.println("\n\n\n");
		try {
			for (int i = 0; i < personList.size(); i++) {
				//for every Person Object we set value in preparedstatement
				//then we add batch when 500 Persons record is added
				//we use execute batch in Sqlite3
				Ms3Person person = personList.get(i);
				//index in prepared statement starts with index 1
				//set all columns details for PErson Table 
				pst.setInt(1, i+1);
				pst.setString(2, person.getFirstName());
				pst.setString(3, person.getLastName());
				pst.setString(4, person.getEmail());
				pst.setString(5, person.getGender());
				pst.setString(6, person.getUrlLink());
				pst.setString(7, person.getCard());
				
				pst.setFloat(8, person.getCharges());
				
				pst.setBoolean(9, person.getH_column());
				pst.setBoolean(10, person.getI_column());
				pst.setString(11, person.getCity());
				//one person record is set so add that to a Batch
				//so that we can execute large statement in 1 go
				pst.addBatch();
				if(i%500 ==0) {
					pst.executeBatch();//execute the batch for 500 PErsons record
					System.out.println("INSERTING 500 records in SQLITE now...");
				}
			}
		} catch (Exception e) {//if any exception arises
			e.printStackTrace();
		} finally {// close the preparedStatement object
			try {
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} // finally block end
	}

	// read CSV file NOW
	private static ArrayList<Ms3Person> readCSV(String fileName) {
		ArrayList<Ms3Person> personsList = new ArrayList<>();
		try (Scanner sc = new Scanner(new File(fileName))) {
			// skip the first line which is having column headers name
			sc.nextLine();
			int index = 1;
			// loop will go till end of file is reached ie.
			while (sc.hasNextLine()) {
				// read single line at a time
				String personData = sc.nextLine();
				if(index >=5999)
					System.out.println(index+" index"+personData);
				//System.out.println(personData);
				//split the string by delimiter ,
				if(personData.length()>2) {
					String singlePersonRecord[] = personData.split(",");
					//the data we get is in form of sTring we need to convert
					//it to required data type for usage
					Ms3Person person = convertDataToRequiredDataType(singlePersonRecord,index);
					if (person != null) {
						// add the person object in ArrayList
						personsList.add(person);
					} else {
						// bad data so create new file & write data
                                            // writing to the logfile
             
  
					}
					System.out.println("index: " + index);
				}else {
					if(personData.length()<1) {//if no more line is there or only empty lines left
						System.out.println("finished reading the file...");
						break;
					}
						
				}
				index++;
			}
		} catch (Exception e) {
			// if any exception arises while reading the file data that exception
			// will be caught in this block
			System.out.println("EXCEPTION: " + e.getMessage());
			e.printStackTrace();
		}
		return personsList;
	}

	//data read from csv file need to parsed to appropriate Data type
	private static Ms3Person convertDataToRequiredDataType(String[] personArray,int index) {
		
		String firstName = "", lastName = "", email = "", gender = "", urlLink = "", card = "", city = "";
		float charges = 0f;
		boolean h_column = false, i_column = false;
		
		boolean flag = false;
		int i = 0;
		//ignore the bad data if read
		if(personArray.length<2 || personArray.length >11)
			return null;
		//iterate the person details & set details in PErson object
		for (; i < personArray.length; i++) {
			String temp = personArray[i];
			if(temp.length()<=1)
				return null;
			// if current string is empty or null
			if (temp == null || temp.length() <=1) {
				// contains bad data
				// so new file is to be created & append
				return null;
			} else {
				if (i == 0) {
					firstName = personArray[0];
				} else if (i == 1) {
					lastName = personArray[1];
				} else if (i == 2) {
					email = personArray[2];
				} else if (i == 3) {
					gender = personArray[3];
				} else if (i == 4) {//urllink contains comma in it we need to be careful for this
					urlLink = personArray[4] + personArray[i + 1];
					i = 5;
				} else if (i == 6) {//card name
					card = personArray[6];
				} else if (i == 7) {//// $0.02 we need to trim $ from it
					//few ccolumns contains empty data so we need to check that as well
					String stringCharge = personArray[7];
					stringCharge = stringCharge.substring(1);
					//parse String to double to take care of 
					//exception
					//System.out.println("Cost:  "+stringCharge);
					charges = (float)Double.parseDouble(stringCharge);
				} else if (i == 8) {//parse String to  Boolean 
					h_column = Boolean.parseBoolean(personArray[8]);
				} else if (i == 9) {
					i_column = Boolean.parseBoolean(personArray[9]);
				} else {
					if (i == 10)
						city = personArray[10];
                                     
				}
			}
			i++;
		} // while loop ends

		//create object & initialise all data members
		Ms3Person person = new Ms3Person(firstName, lastName, email, gender, urlLink, card, charges, h_column, i_column,
				city);
		lineCount++;
		//know how many lines are being read
		System.out.println(person + " lineNo: " + lineCount);
		return person;//return the PErson object
                
	}
}
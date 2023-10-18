import java.sql.*;
import java.util.*;

public class Driver {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		
		try {
			String database = "jdbc:ucanaccess://C://Users//Usama//OneDrive//Documents//JDBC_Practice//CarTracker.accdb";
			Connection con = DriverManager.getConnection(database);
			Statement statement = con.createStatement();
			
			
			System.out.println("Part one");//1
			
			String retrieveQuery = "SELECT * FROM Cars";
			ResultSet sqlResult = statement.executeQuery(retrieveQuery);//creates query
			
			while((sqlResult!=null) && (sqlResult.next())) { //output
				if("Mitsubishi".equals(sqlResult.getString(1))) {
					System.out.println("Model: " + sqlResult.getString(2) + "\tType: " + sqlResult.getString(3) + "\tColor: " + sqlResult.getString(5));
				}
			}
			
			
			System.out.println("\nPart Two\n");//2
			
			retrieveQuery = "SELECT DISTINCT Make, Model, Body_Type, Color FROM Cars";
			sqlResult = statement.executeQuery(retrieveQuery);//creates query
			
			while((sqlResult!=null) && (sqlResult.next())) { //output
				if("Mitsubishi".equals(sqlResult.getString(1))) {
					System.out.println("Model: " + sqlResult.getString(2) + "\tType: " + sqlResult.getString(3) + "\tColor: " + sqlResult.getString(4));
				}
			}
			
			
			System.out.println("\nPart Three\n");//3
			
			retrieveQuery = "SELECT Last_Name, Salary, Make, Model FROM Cars, Owners WHERE Cars.VIN = Owners.VIN";
			sqlResult = statement.executeQuery(retrieveQuery);//creates query
			
			while((sqlResult!=null) && (sqlResult.next())) {//outputs
				System.out.println("LName: " + sqlResult.getString(1) + "\tSalary: " + sqlResult.getString(2) + "\tMake: " + sqlResult.getString(3) + "\tModel: " + sqlResult.getString(4));
			}
			
			
			System.out.println("\nPart Four\n");//4
			
			retrieveQuery = "SELECT Last_Name, Salary, Make, Model FROM Cars, Owners WHERE Cars.VIN = Owners.VIN AND Owners.Age BETWEEN 25 AND 35";
			sqlResult = statement.executeQuery(retrieveQuery);//creates query
			
			while((sqlResult!=null) && (sqlResult.next())) {//outputs
				System.out.println("LName: " + sqlResult.getString(1) + "\tSalary: " + sqlResult.getString(2) + "\tMake: " + sqlResult.getString(3) + "\tModel: " + sqlResult.getString(4));
			}
			
			
			System.out.println("\nPart Five\n");//5
			
			retrieveQuery = "SELECT * FROM Cars WHERE Cars.VIN NOT IN (SELECT VIN FROM Owners)";
			sqlResult = statement.executeQuery(retrieveQuery);//creates query
			
			while((sqlResult!=null) && (sqlResult.next())) {//outputs	
				System.out.println("Make: " + sqlResult.getString(1) + "\tModel: " + sqlResult.getString(2) + "\tColor: " + sqlResult.getString(5));
			}
			
			
			System.out.println("\nPart Six\n");//6
			
			//get user input
			System.out.print("Enter color 1: "); 
			String col1 = sc.nextLine();
			System.out.print("Enter color 2: ");
			String col2 = sc.nextLine();
			
			retrieveQuery = "UPDATE Cars SET Color = '" + col2 + "' WHERE Color = '" + col1 + "'";
			int sqlResult2 = statement.executeUpdate(retrieveQuery);//updates
			
			System.out.println("Number of rows updated: " + sqlResult2);//num of rows updates
			
			
			System.out.println("\nPart Seven\n");//7
			
			System.out.print("Enter VIN: "); //gets user input
			String vin = sc.nextLine();
			
			retrieveQuery = "SELECT VIN FROM Owners";
			sqlResult = statement.executeQuery(retrieveQuery);//creates query
			boolean valid = false;
			
			while((sqlResult!=null) && (sqlResult.next())) {//checks if VIN matches Owners VIN
				if(vin.equals(sqlResult.getString(1))) {
					valid = true;
					break;
				}
			}
			
			if(valid) {//if VIN matches
				System.out.println("Exists in Owners Table");
			}else {
				retrieveQuery = "DELETE FROM Cars WHERE VIN = " + vin;
				sqlResult2 = statement.executeUpdate(retrieveQuery);
				
				if(sqlResult2 == 0) { //if 0, then no rows were deleted
					System.out.println("No such record exists");
				}else {
					System.out.println("Deleted successfully");
				}
			}
		
			
			System.out.println("\nPart Eight\n");//8
			
			//gets user inputs
			System.out.print("Enter Make: "); 
			String make = sc.nextLine();
			System.out.print("Enter Model: ");
			String model = sc.nextLine();
			System.out.print("Enter Body_Type: ");
			String body = sc.nextLine();
			System.out.print("Enter Num_Doors: ");
			String doors = sc.nextLine();
			System.out.print("Enter Color: ");
			String color = sc.nextLine();
			
			retrieveQuery = "SELECT Make, VIN FROM Cars";
			sqlResult = statement.executeQuery(retrieveQuery);//creates query
			valid = false;
			int val = 0;
			int val2 = 0;
			//gets highest VIN value
			while((sqlResult!=null) && (sqlResult.next())) {
				if(make.equals(sqlResult.getString(1))) { //if make exists
					valid = true;
					if(Integer.valueOf(sqlResult.getString(2)) > val) {
						val = Integer.valueOf(sqlResult.getString(2));
					}
				}
				if(Integer.valueOf(sqlResult.getString(2)) > val2) {
					val2 = Integer.valueOf(sqlResult.getString(2));
				}
			}
			
			
			if(valid) {//if existing make
				
				int length = String.valueOf(val).length(); //length of number
				int firstDigit = Integer.parseInt(Integer.toString(val2).substring(0, length-2)); //first digits(could be 2 or 23)
				
				if((val+1) < ((firstDigit*100)+100)) {//if val is not in the next 100s category 
					retrieveQuery = "INSERT INTO Cars (VIN, Make, Model, Body_Type, Num_Doors, Color) VALUES (" + (val+1) +
									", '" + make + "', '" + model + "', '" + body + "', '" + doors + "', '" + color + "')";
					sqlResult2 = statement.executeUpdate(retrieveQuery);//update
					System.out.println("Added successfully: " + sqlResult2);
				}else {
					System.out.println("Cannot add record because next 100s category");
				}
			}else {//if make does not exist (new make)
				
				int length = String.valueOf(val2).length();//length of number
				int firstDigit = Integer.parseInt(Integer.toString(val2).substring(0, length-2));// first digits(could be 2 or 3)
				val2 =  ((firstDigit*100) + 100);//starts at next 100s category
				
				retrieveQuery = "INSERT INTO Cars (VIN, Make, Model, Body_Type, Num_Doors, Color) VALUES (" + (val2) +
						", '" + make + "', '" + model + "', '" + body + "', '" + doors + "', '" + color + "')";
				sqlResult2 = statement.executeUpdate(retrieveQuery);//update
				System.out.println("Added successfully");
			}
			
		}catch(Exception e) {
			System.out.println("Error");
		}
	}
}

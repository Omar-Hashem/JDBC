import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;


public class UnitTest {

	
	public void test1() throws Exception {
		DriverIM e = new DriverIM();
		DriverManager.registerDriver(e);
		assertEquals(true,e.acceptsURL("JDBC:DBMS:default"));
		assertEquals(false,e.acceptsURL("JDBC:DBMS:nope"));
		Properties info = new Properties();
		info.put("username", "ahmed");	
		info.put("password", "123");
		ConnectionIM con=null;
		con = (ConnectionIM) DriverManager.getConnection("JDBC:DBMS:default",info);
		StatementIm stmt = (StatementIm) con.createStatement();
		assertEquals(false,stmt.execute("CREATE TABLE Persons(PersonID int,LastName varchar(255),FirstName varchar(255),Address varchar(255),City varchar(255));"));
		try {
			stmt.execute("SELECT * FROM TEST;");
		} catch (SQLException e1) {
			assertEquals(dbms.TABLE_NOT_FOUND,e1.getMessage());
		}
		try {
			stmt.execute("SELECTT * FROM TEST;");
		} catch (SQLException e1) {
			assertEquals(dbms.PARSING_ERROR,e1.getMessage());
		}
		try {
			stmt.execute("CREATE TABLE cutomers(PersonID int,LastName varchar(255),FirstName varchar(255),Address varchar(255),City varchar(255));");
		} catch (SQLException e1) {
			assertEquals(dbms.TABLE_ALREADY_EXISTS,e1.getMessage());
		}
		
		assertEquals(0, stmt.getQueryTimeout());
		stmt.setQueryTimeout(10);
		assertEquals(10, stmt.getQueryTimeout());
		
		stmt.addBatch("INSERT INTO customers (PersonID,LastName,FirstName,Address,City) VALUES (49,'Mahmoud','Amr','11 street','Alexandria');");
		stmt.addBatch("INSERT INTO customsers (PersonID,LastName,FirstName,Address,City) VALUES (2,'B','A','12 street','Alexandria');");
		stmt.addBatch("INSERT INTO customers (PersonID,LastName,FirstName,Address,City) VALUES (17,'D','X','13 street','Tanta');");
		stmt.addBatch("INSERT INTO customers (PersonID,LastName,FirstName,Address,City) VALUES (14,'Y','z','14 street','Tanta');");
		int[] temp = stmt.executeBatch();
		for(int i=0;i<temp.length;i++)
			assertEquals(1,temp[i]);
		ResultSetIM ResSet = (ResultSetIM) stmt.executeQuery("SELECT * FROM Persons;");
		
		stmt.clearBatch();
		stmt.close();
		try {
			stmt.execute("SELECT * FROM Persons;");
		} catch (SQLException e1) {
			assertEquals("No Defined Current Database",e1.getMessage());
		}
		
		con = (ConnectionIM) DriverManager.getConnection("JDBC:DBMS:default",info);
		stmt = (StatementIm) con.createStatement();
		stmt.addBatch("UPDATE Persons SET Firstname='Amr' WHERE City = 'Alexandria';");
		stmt.addBatch("DELETE FROM Persons WHERE Firstname = 'Amr';");
		stmt.addBatch("DELETE * FROM Persons;");
		temp = stmt.executeBatch();
		for(int i=0;i<temp.length;i++)
			assertEquals(2,temp[i]);
		
		stmt.setQueryTimeout(1);
		stmt.executeUpdate("INSERT INTO Persons (PersonID,LastName,FirstName,Address,City) VALUES (49,'Mahmoud','Amr','11 street','Alexandria');");
		stmt.executeUpdate("INSERT INTO Persons (PersonID,LastName,FirstName,Address,City) VALUES (49,'Mahmoud','Amr','11 street','Alexandria');");
		stmt.execute("CREATE TABLE ArrayTest(arr Integer ARRAY[10]);");
		stmt.execute("INSERT INTO ArrayTest (arr) VALUES(2, 3, 5, 7, 11);");
		stmt.execute("INSERT INTO ArrayTest (arr) VALUES(1, 1, 2, 3, 5;");
		stmt.execute("INSERT INTO ArrayTest (arr) VALUES(2, 4, 8, 16, 32);");
		
		stmt.execute("DELETE FROM ArrayTest WHERE arr = '{2, 3, 5, 7, 11}';");
		stmt.execute("INSERT INTO ArrayTest (arr) VALUES(2, 3, 5, 7, 11);");
		stmt.execute("INSERT INTO ArrayTest (arr) VALUES(2, 3, 5, 7, 11);");
		ResSet = (ResultSetIM) stmt.executeQuery("SELECT * FROM ArrayTest;");

	}
	@Test 
	public void test2(){
		try {
			String url = "jdbc:DBMS:Default";
			DriverIM e = new DriverIM();
			DriverManager.registerDriver(e);
			Properties p = new Properties();
			p.put("username", "youssef");
			p.put("password", "123456");
			Connection con = DriverManager.getConnection(url, p);
			StatementIm stmt = (StatementIm) con.createStatement(); 
			ResultSetIM res = (ResultSetIM) stmt.executeQuery("select * from Persons;");
			assertEquals (true, res.absolute(1)) ;
			assertEquals (false , res.absolute(0));
			assertEquals (true,res.absolute(-1));
			assertEquals (true ,res.absolute(-3));
			assertEquals (false,res.absolute(-5));
			res.absolute(1) ;
			assertEquals (1,res.getInt(0));
			res.next(); 
			assertEquals ("11 street",res.getString(3));
			assertEquals (1234656512 ,res.getLong(7));
			assertEquals (1234656512,res.getLong("long"));
			res.absolute(4);
			assertEquals (4,res.getInt(0));
			assertEquals ("Yasser",res.getString("FirstName"));
			assertEquals (12313812,res.getLong("long"));
			res.next(); 
			assertEquals (false ,res.isBeforeFirst());
			assertEquals (true,res.isAfterLast());
			res.next();
			assertEquals (true,res.isAfterLast());
			res.absolute (0) ;
			assertEquals (true,res.isBeforeFirst());
			res.absolute (3);
			assertEquals ("Ahmed",res.getString("LastName"));
			assertEquals ("12 street",res.getString(3));
			res.next(); 
			res.previous() ;
			assertEquals ("Cairo",res.getString("city"));
			res.previous();
			res.previous();
			assertEquals (true, res.isFirst());
			res.previous();
			assertEquals (true, res.isBeforeFirst());
			res.absolute(2);
			assertEquals ("Alexandria", res.getString("city"));
			assertEquals (100.621165, res.getDouble(6),.0000999);
			res.next();
			assertEquals (100.6213, res.getFloat("weight"),.0000999);
			res.absolute(-2);
			assertEquals (100.6213, res.getFloat("weight"),.0000999);
			res.next();
			assertEquals (72.236 ,res.getFloat("weight"),.0000999);
			res.previous() ;
			assertEquals (1234812, res.getLong(7));
			assertEquals (3, res.findColumn("address"));
		
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testValidator() {
		
		Validator v = new Validator();
		
		assertEquals(true,v.Validate_Expression("SELECT columnname,columnname FROM tablename;"));
		assertEquals(true,v.Validate_Expression("SELECT * FROM table_name;"));
		assertEquals(true,v.Validate_Expression("SELECT DISTINCT column_name,column_name FROM table_name;"));
		assertEquals(true,v.Validate_Expression("SELECT column_name,column_name FROM table_name WHERE column_name < value;"));
		assertEquals(true,v.Validate_Expression("SELECT * FROM Customers WHERE Country='Mexico';"));
		assertEquals(true,v.Validate_Expression("SELECT * FROM Customers WHERE CustomerID=1;"));
		assertEquals(true,v.Validate_Expression("SELECT column_name,column_name FROM table_name ORDER BY column_name,column_name ASC;"));
		assertEquals(true,v.Validate_Expression("INSERT INTO Customers (CustomerName,ContactName,Address,City,PostalCode,Country) VALUES ('Cardinal','{Tom b,'2'}','Skagen','Stavanger','4006','Norway');"));
		assertEquals(true,v.Validate_Expression("UPDATE Customers SET ContactName='Alfred Schmidt', City='Hamburg' WHERE CustomerName=Alfreds Futterkiste;"));
		assertEquals(true,v.Validate_Expression("DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste' ;"));
		assertEquals(true,v.Validate_Expression("DELETE * FROM table_name;"));
		
		assertEquals(false,v.Validate_Expression(" columnname,columnname FROM tablename;"));
		assertEquals(false,v.Validate_Expression("SELECT  FROM table_name;"));
		assertEquals(true,v.Validate_Expression("SELECT DISTINCT column_name,column_name FROM table_name;"));
		assertEquals(true,v.Validate_Expression("SELECT column_name,column_name FROM table_name WHERE column_name < value;"));
		assertEquals(false,v.Validate_Expression("SELECT *  Customers WHERE Country='Mexico';"));
		assertEquals(false,v.Validate_Expression("SELECT * FROM  CustomerID=1;"));
		assertEquals(false,v.Validate_Expression("SCT column_name,column_name FROM table_name ORDER BY column_name,column_name ASC;"));
		assertEquals(false,v.Validate_Expression("INSERT  Customers (CustomerName,ContactName,Address,City,PostalCode,Country) VALUES ('Cardinal','{Tom b,'2'}','Skagen','Stavanger','4006','Norway');"));
		assertEquals(false,v.Validate_Expression("UTE Customers SET ContactName='Alfred Schmidt', City='Hamburg' WHERE CustomerName='{Alfreds ,Futterkiste}';"));
		assertEquals(true,v.Validate_Expression("DELETE * FROM table_name"));
	}
	
	
	
	

}

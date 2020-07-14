package cs5530;

import java.sql.*;

public class Register {
	
	public Register() {}
	
	public boolean createNewCar(String table, int vin, String category, String login, Statement stmt)
	{
		String sql = "INSERT INTO " + table + " (login,vin,category) values ('"+login+"',"+vin+",'"+category+"')";
//		System.out.println("Executing " + sql);
		try
		{
			int result = stmt.executeUpdate(sql);
//			System.out.println("Result from insert: " + result);
			return true;
		}
		catch(Exception e) {
			System.out.println("Error executing Query");
		}
		return false;
		
	}
	
	public boolean removeCar(String table, int vin, Statement stmt)
	{
		
		String sql = "DELETE FROM " + table + " WHERE vin=" + vin;
		try
		{
			int result = stmt.executeUpdate(sql);
//			System.out.println("Result from insert: " + result);
			return true;
		}
		catch(Exception e) {
			System.out.println("Error executing delete Query");
		}
		return false;

		
	}
	
	public boolean vinExists(String table, int vin, Statement stmt)
	{
		String sql = "SELECT * FROM " + table + " WHERE vin=" + vin;
		ResultSet rs = null;
//		System.out.println("Executing " + sql);
		try
		{
			rs = stmt.executeQuery(sql);
			int size = 0;
			while(rs.next())
			{
				size ++;
			}
			rs.close();
			if(size == 1)
			{
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
		 		System.out.println("cannot execute the query");
		 	}
		finally
		 	{
		 		try{
   		 		if (rs!=null && !rs.isClosed())
   		 			rs.close();
		 		}
		 		catch(Exception e)
		 		{
		 			System.out.println("cannot close resultset");
		 		}
		 	}
	    return false;

		
	}
	
	public boolean isLoggedInVin(String table, String login, int vin, Statement stmt)
	{
		String sql = "SELECT * FROM " + table + " WHERE vin=" + vin + " AND login='" + login+"'";
		ResultSet rs = null;
//		System.out.println("Executing " + sql);
		try
		{
			rs = stmt.executeQuery(sql);
			int size = 0;
			while(rs.next())
			{
				size++;
			}
			rs.close();
			if(size == 1)
			{
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
	 		System.out.println("cannot execute the query");
	 	}
	finally
	 	{
	 		try{
		 		if (rs!=null && !rs.isClosed())
		 			rs.close();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 		
		}
 		return false;
	}
	
	public boolean typeExists(String table, String make, String model, Statement stmt)
	{
		String sql = "SELECT * FROM " + table + " WHERE make='" + make.toUpperCase() +"' AND model='" + model.toUpperCase()+"'";
		ResultSet rs = null;
//		System.out.println(sql);
		try
		{
			rs = stmt.executeQuery(sql);
			int size = 0;
			while(rs.next())
			{
				size++;
			}
			rs.close();
			if(size == 1)
			{
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
	 		System.out.println("cannot execute the query");
	 	}
	finally
	 	{
	 		try{
		 		if (rs!=null && !rs.isClosed())
		 			rs.close();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		return false;
		
	}
	
	public void addType(String table, String make, String model, Statement stmt)
	{
		if(!typeExists(table, make, model, stmt))
		{
			try
			{
				String sql = "INSERT INTO " + table + " (make, model) values ('"+make.toUpperCase()+"','"+model.toUpperCase()+"')";
				int result = stmt.executeUpdate(sql);
//				System.out.println("Result from insert: " + result);
				return;
			}
			catch(Exception e)
			{
				System.out.println("Error adding car type");
			}
		}
	}
	
	public boolean connectCarType(String table, int vin, String make, String model, Statement stmt)
	{
		String sql = "SELECT tid FROM Ctypes WHERE make='" + make.toUpperCase() +"' AND model='" + model.toUpperCase()+"'";
		ResultSet rs = null;
		int tid = 0;
		try
		{
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				tid = rs.getInt("tid");
			}
		}
		catch(Exception e) {
			System.out.println("Error adding car type");
			return false;
		}
		String insertSql = "INSERT INTO " + table + " (vin, tid) values ("+vin+","+tid+")";
		try
		{
			int result = stmt.executeUpdate(insertSql);
//			System.out.println("Result from insert: " + result);
			return true;
		}catch(Exception e) {
			System.out.println("Error adding car type");
			return false;
		}
		

	}
	
	public boolean deleteCarType(String table, int vin, Statement stmt)
	{
		String sql = "DELETE FROM " + table + " WHERE vin=" + vin;
		try
		{
			int result = stmt.executeUpdate(sql);
			System.out.println("Result from insert: " + result);
			return true;
		}
		catch(Exception e) {
			System.out.println("Error executing delete type Query");
		}
		return false;

		
	}
}
package cs5530;

import java.sql.*;
import java.util.ArrayList;


public class Statistics
{
	public ArrayList<String> mostPopularUCs(int m, String category, Statement stmt)
	{
		ArrayList<String> list = new ArrayList<String>();
		String sql = "SELECT R.vin, count(*) as count FROM Ride R, UC C WHERE R.vin=C.vin AND C.category='"+category+"' GROUP BY R.vin DESC LIMIT " + m;
		ResultSet rs=null;
//		System.out.println("executing "+sql);
		try{

			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add("VIN: " + rs.getString("vin") + ", COUNT: " + rs.getString("count"));
			}

			rs.close();
			return list;
		}
		catch(Exception e)
		{
			System.out.println("cannot execute query");
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
		return new ArrayList<String>();
		
	}
	
	public ArrayList<String> mostExpensiveUCs(int m, String category, Statement stmt)
	{
		ArrayList<String> list = new ArrayList<String>();
		String sql = "SELECT R.vin, avg(cost) as average FROM Ride R, UC C WHERE R.vin=C.vin AND C.category='"+category+"' GROUP BY R.vin DESC LIMIT " + m;
		ResultSet rs=null;
//		System.out.println("executing "+sql);
		try{

			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add("VIN: " + rs.getString("vin") + ", AVG COST: " + rs.getString("average"));
			}

			rs.close();
			return list;
		}
		catch(Exception e)
		{
			System.out.println("cannot execute query");
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
		return new ArrayList<String>();
		
	}
	
	public ArrayList<String> highlyRatedUDs(int m, String category, Statement stmt)
	{
		ArrayList<String> list = new ArrayList<String>();
		String sql = "SELECT C.login, avg(score) as average FROM Feedback F, UC C WHERE F.vin=C.vin AND C.category='"+category+"' GROUP BY C.login DESC LIMIT " + m;
		ResultSet rs=null;
//		System.out.println("executing "+sql);
		try{

			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add("DRIVER: " + rs.getString("login") + ", AVG SCORE: " + rs.getString("average"));
			}

			rs.close();
			return list;
		}
		catch(Exception e)
		{
			System.out.println("cannot execute query");
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
		return new ArrayList<String>();
		
	}
}
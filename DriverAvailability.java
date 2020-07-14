package cs5530;

import java.sql.*;
import java.util.ArrayList;

public class DriverAvailability {

	public DriverAvailability()
	{}

	public boolean logRide(String login, int vin, int cost, int from, int to, Statement stmt) {

		// Check that the time frame is valid for the ride
		String sql="select * from Available A, Period P, UC C, UD D, IsCtypes I, Ctypes T where C.vin="+vin+" and T.tid=I.tid and I.vin=C.vin and A.pid=P.pid and D.login=A.login and C.login=D.login and P.fromHour<="+from+" and P.toHour>="+to;
		String output="";
		ResultSet rs=null;
//		System.out.println("executing "+sql);
		try{

			rs=stmt.executeQuery(sql);
			int size = 0;
			while (rs.next())
			{
				size++;
			}

			rs.close();
			
			if(size == 0) {
				System.out.println("That vehicle is not available during that time frame.");
				return false;
			}
		}
		catch(Exception e)
		{
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

		sql="insert into Ride (login,vin,cost,fromHour,toHour) values ('"+login+"',"+vin+","+cost+","+from+","+to+")";
		output="";
//		System.out.println("executing "+sql);
		try{
			int result=stmt.executeUpdate(sql);

			return true;
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the update");
		}
		finally
		{

		}
		return false;
	}

	public boolean reserveCar(String login, int pid, int vin, int cost, Statement stmt) {

		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());

		String sql="insert into Reserve (login,vin,pid,cost,date) values ('"+login+"',"+vin+","+pid+","+cost+",'"+date.toString()+"')";
		String output="";
//		System.out.println("executing "+sql);
		try{
			int result=stmt.executeUpdate(sql);

			return true;
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query");
		}
		finally
		{

		}
		return false;
	}

	public ArrayList<String> getAllAvailibities(int start, int end, Statement stmt) {

		ArrayList<String> list = new ArrayList<String>();

		String sql="select * from Available A, Period P, UC C, UD D, IsCtypes I, Ctypes T where T.tid=I.tid and I.vin=C.vin and A.pid=P.pid and D.login=A.login and C.login=D.login and P.fromHour<="+start+" and P.toHour>="+end;
		String output="";
		ResultSet rs=null;
//		System.out.println("executing "+sql);
		try{

			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add(rs.getString("make") + " " + rs.getString("model") + " , " + rs.getString("login") + " " + rs.getInt("pid") + " " + rs.getInt("vin"));
			}

			rs.close();
			return list;
		}
		catch(Exception e)
		{
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
		return new ArrayList<String>();
	}

	public boolean deleteAvailability(String login, int pid, Statement stmt) {
		String sql="delete from Available where login='"+login+"' and pid="+pid+"";
		String output="";
//		System.out.println("executing "+sql);
		try{
			int result=stmt.executeUpdate(sql);
			int size=0;

			System.out.println("Result from insert: " + result);

			return true;
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query");
		}
		finally
		{

		}
		return false;
	}

	public ArrayList<String> getCurrentAvailibities(String login, Statement stmt) {

		ArrayList<String> list = new ArrayList<String>();

		String sql="select * from Available A, Period P where login='"+login+"' and A.pid=P.pid";
		String output="";
		ResultSet rs=null;
//		System.out.println("executing "+sql);
		try{

			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add("From " + rs.getInt("fromHour") + " - " + rs.getInt("toHour") + ". PeriodID: " + rs.getInt("pid"));
			}

			rs.close();
			return list;
		}
		catch(Exception e)
		{
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
		return new ArrayList<String>();
	}

	public int insertPeriod(int starting, int ending, Statement stmt) {

		int periodID = -1;

		String sql = "SELECT pid FROM Period WHERE fromHour=" + starting + " and toHour=" + ending;
		ResultSet rs = null;
//		System.out.println("Executing " + sql);
		try
		{
			rs = stmt.executeQuery(sql);
			int size = 0;
			while(rs.next())
			{
				periodID = rs.getInt("pid");
				size++;
			}
			rs.close();

			if(size == 1) {
				return periodID;
			}else {
				sql="insert into Period (fromHour,toHour) values ("+starting+","+ending+")";
				String output="";
//				System.out.println("executing "+sql);
				int result=stmt.executeUpdate(sql);

				sql="SELECT pid FROM Period WHERE fromHour=" + starting + " and toHour=" + ending;
				output="";
				System.out.println("executing "+sql);

				rs=stmt.executeQuery(sql);

				while(rs.next())
				{
					periodID = rs.getInt("pid");
				}
				rs.close();

				return periodID;
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
		periodID = -1;
		return periodID;
	}

	public boolean setDriverAvailability(String login, int startingHour, int endingHour, Statement stmt)
	{
		int periodID = insertPeriod(startingHour, endingHour, stmt);

		if(periodID == -1) {
			System.out.println("Error: couldn't create new pid");
			return false;
		}

		String sql="insert into Available (login,pid) values ('"+login+"',"+periodID+")";
		String output="";
//		System.out.println("executing "+sql);
		try{
			int result=stmt.executeUpdate(sql);

			System.out.println("Result from insert: " + result);

			return true;
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query");
		}
		finally
		{

		}
		return false;
	}

}

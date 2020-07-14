package cs5530;

import java.sql.*;
import java.util.ArrayList;

public class Separation {
		public Separation()
		{}
		
		public boolean oneDegreeOfSeparation(String login1, String login2, Statement stmt) {
			String sql="select F.vin from Favorites F, Favorites F2 where F.login='"+login1+"' and F2.login='"+login2+"' and F.vin=F2.vin";
			ResultSet rs=null;
//   		 	System.out.println("executing "+sql);
   		 	try{
	   		 	rs=stmt.executeQuery(sql);
	   		 	int size=0;
	   		 	while (rs.next())
				 {
					size++;
				 }
	   		 	
			     rs.close();
			     
			     if(size > 0) {
			    	 return true;
			     }else {
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
		    return false;
		}
		
		public boolean twoDegreesOfSeparation(String login1, String login2, Statement stmt) {
			String sql="select F3.login from Favorites F, Favorites F2, Favorites F3, Favorites F4 where F.login='"+login1+"' and F2.login='"+login2+"' and not F.vin=F2.vin and not F3.login=F.login and not F3.login=F2.login and F3.login=F4.login and F3.vin=F.vin and F4.vin=F2.vin";
			ResultSet rs=null;
//   		 	System.out.println("executing "+sql);
   		 	try{
	   		 	rs=stmt.executeQuery(sql);
	   		 	int size=0;
	   		 	while (rs.next())
				 {
					size++;
				 }
	   		 	
			     rs.close();
			     
			     if(size > 0) {
			    	 return true;
			     }else {
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
		    return false;
		}
		
		public boolean checkVehicleExists(int vin, Statement stmt) {
			String sql="select * from UC where vin="+vin;
			ResultSet rs=null;
//   		 	System.out.println("executing "+sql);
   		 	try{
	   		 	rs=stmt.executeQuery(sql);
	   		 	int size=0;
	   		 	while (rs.next())
				 {
					size++;
				 }
			     
			     rs.close();
			     
			     if(size == 1) {
			    	 return true;
			     }else {
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
		    return false;
		}
		
		public boolean favoriteVehicle(String login, int vin, Statement stmt) {
			
			// Check to make sure the vin is valid
			boolean vinValid = checkVehicleExists(vin, stmt);
			
			if(!vinValid) {
				System.out.println("Error: not a valid vin number.");
				return false;
			}
			
			// Delete any existing favorites
			String sql="delete from Favorites where login='"+login+"'";
			ResultSet rs=null;
//   		 	System.out.println("executing "+sql);
   		 	try{
	   		 	int result =stmt.executeUpdate(sql);
	   		 	
	   		 	java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
	   		 	
	   		 	sql = "insert into Favorites (login,vin,fvdate) values ('"+login+"',"+vin+",'"+date.toString()+"')";
	   		 	result = stmt.executeUpdate(sql);
	   		 	
			     return true;
   		 	}
   		 	catch(Exception e)
   		 	{
   		 		System.out.println("cannot execute the query");
   		 	}

		    return false;
			
		}
		
		public boolean markTrust(String login1, String login2, int trust, Statement stmt)
		{
			try
			{
				String sql = "INSERT INTO Trust (login1, isTrusted, login2) values ('"+login1+"',"+trust+",'"+login2+"')";
				int result = stmt.executeUpdate(sql);
				return true;
			}catch(Exception e)
			{
				return false;
			}
		}
		
		public ArrayList<String> UCSuggestion(String login, int vin, Statement stmt) {
			String sql="select R4.vin, count(*) as count from Ride R4 where R4.login in (select distinct R3.login from Ride R, Ride R2, Ride R3 where R.login='"+login+"' and not R2.login=R.login and R2.login=R3.login and R2.vin="+vin+" and not R3.vin=R2.vin) and not R4.vin="+vin+" group by R4.vin";
			ResultSet rs=null;
			ArrayList<String> list = new ArrayList<String>();
//			System.out.println("executing "+sql);
			try{

				rs=stmt.executeQuery(sql);
				while (rs.next())
				{
					list.add("VIN: " + rs.getString("vin") + ", RIDES COUNT: " + rs.getString("count"));
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

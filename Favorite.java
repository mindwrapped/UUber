package cs5530;

import java.sql.*;

public class Favorite {
		public Favorite()
		{}
		
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
			
			String sql="";
			ResultSet rs=null;
//   		 	System.out.println("executing "+sql);
   		 	try{
	   		 	//int result =stmt.executeUpdate(sql);
	   		 	
	   		 	java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
	   		 	
	   		 	sql = "insert into Favorites (login,vin,fvdate) values ('"+login+"',"+vin+",'"+date.toString()+"')";
	   		 	int result = stmt.executeUpdate(sql);
	   		 	
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
}

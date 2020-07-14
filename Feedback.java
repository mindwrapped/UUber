package cs5530;

import java.sql.*;
import java.util.ArrayList;

public class Feedback {
		public Feedback()
		{}
				
		public ArrayList<String> getUsefulFeedback(String login, int numOfFeedbacks, Statement stmt){
			
			ArrayList<String> list = new ArrayList<String>();

			String sql="select distinct F.fid, score, text, fbdate, C.vin, C.login, ave from Feedback F, UC C, Rates R2, (select R.fid,sum(rating)/count(*) as ave from Rates R group by R.fid) as J where F.fid=J.fid and C.login='"+login+"' and C.vin=F.vin and F.fid=R2.fid order by ave desc limit " + numOfFeedbacks;
			String output="";
			ResultSet rs=null;
//			System.out.println("executing "+sql);
			try{

				rs=stmt.executeQuery(sql);
				while (rs.next())
				{
					list.add("FID: "+ rs.getInt("fid") + ", VIN: "+ rs.getInt("vin")+ " Score: " + rs.getInt("score") + ", Text: " + rs.getString("text") + ", Average Usefulness: " + rs.getDouble("ave"));
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
		
		public boolean checkFeedbackExists(int fid, Statement stmt) {
			String sql="select * from Feedback where fid="+fid;
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
		
		public boolean rateFeedback(String login, int fid, String rating, Statement stmt) {
			// Check to make sure the fid is valid
			boolean fidValid = checkFeedbackExists(fid, stmt);
			
			if(!fidValid) {
				System.out.println("Error: not a valid fid number.");
				return false;
			}
			
			int ratingNum = 0;
			
			if(rating.equals("VERY USEFUL")) {
				ratingNum = 3;
			}else if(rating.equals("USEFUL")) {
				ratingNum = 2;
			}else {
				ratingNum = 1;
			}
			
			// Delete any existing Feedback for the same login and vehicle
			String sql="delete from Rates where login='"+login+"' and fid="+fid+"";
			ResultSet rs=null;
//   		 	System.out.println("executing "+sql);
   		 	
   		 	try{
	   		 	int result =stmt.executeUpdate(sql);
	   		 	
	   		 	java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
	   		 	
	   		 	sql = "insert into Rates (login,fid,rating) values ('"+login+"',"+fid+","+ratingNum+")";
	   		 	result = stmt.executeUpdate(sql);
	   		 	
			    return true;
   		 	}
   		 	catch(Exception e)
   		 	{
   		 		System.out.println("cannot execute the query");
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
		
		public boolean recordFeedbackForVehicle(String login, int vin, int score, String text, Statement stmt) {
			
			// Check to make sure the vin is valid
			boolean vinValid = checkVehicleExists(vin, stmt);
			
			if(!vinValid) {
				System.out.println("Error: not a valid vin number.");
				return false;
			}
			
			// Delete any existing Feedback for the same login and vehicle
			String sql="delete from Feedback where login='"+login+"' and vin="+vin+"";
			ResultSet rs=null;
//   		 	System.out.println("executing "+sql);
   		 	
   		 	try{
	   		 	int result =stmt.executeUpdate(sql);
	   		 	
	   		 	java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
	   		 	
	   		 	if(text.length() == 0) {
		   		 	sql = "insert into Feedback (login,vin,score,fbdate) values ('"+login+"',"+vin+","+score+",'"+date.toString()+"')";
		   		 	result = stmt.executeUpdate(sql);
	   		 	}else {
	   			 	sql = "insert into Feedback (login,vin,score,text,fbdate) values ('"+login+"',"+vin+","+score+",'"+text+"','"+date.toString()+"')";
		   		 	result = stmt.executeUpdate(sql);
	   		 	}
	   		 	
	   		 	
	   		 	
			     return true;
   		 	}
   		 	catch(Exception e)
   		 	{
   		 		System.out.println("cannot execute the query");
   		 	}

		    return false;
			
		}
		
		public boolean CheckLogin(String login, int fid, Statement stmt)
		{
			String sql="SELECT * FROM Feedback where login='"+login+"' and fid=" + fid;
			ResultSet rs = null;
//			System.out.println("Executing " + sql);
			
			try
			{
				rs = stmt.executeQuery(sql);
				int size = 0;
				while(rs.next())
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
		    return true;
		}
}

package cs5530;

import java.sql.*;

public class Login {
		public Login()
		{}
		
		public boolean createNewUser(String table, String login, String password, String name, String street, String city, String state, String phone, Statement stmt)
		{
			String sql="insert into "+table+" (login,password,name,street,city,state,phone) values ('"+login+"','"+password+"','"+name+"','"+street+"','"+city+"','"+state+"','"+phone+"')";
			String output="";
//   		 	System.out.println("executing "+sql);
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
		
		public boolean checkLogin(String table, String login, String password, Statement stmt)
		{
			String sql="select * from "+table+" where login='"+login+"' and password='"+password+"'";
			String output="";
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
		
		public boolean checkIfUserExists(String table, String login, Statement stmt)
		{
			String sql="select * from "+table+" where login='"+login+"'";
			String output="";
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
}

package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Browsing {
	Browsing(){

	}

	public ArrayList<String> browseUserCars(String login, String category, String city, String state, String model, int cityStateChoice, int cityOrStateChoice, int modelChoice, int orderNum, Statement stmt){

		String sql="select C.vin, avg(F.score) as AvgScore from UC C, IsCtypes I, Ctypes T, UD D, Trust Tr, Feedback F where C.category='"+category+"' and F.vin=C.vin and C.login=D.login and I.tid=T.tid and I.vin=C.vin ";
		
		if(orderNum == 1) {
			
			if(cityStateChoice == 1) {
				if(cityOrStateChoice == 1) {
					sql += "and D.city like '"+city+"' ";
				}else {
					sql += "and D.state like '"+state+"' ";
				}
			}else if(cityStateChoice == 2) {
				if(cityOrStateChoice == 1) {
					sql += "or D.city like '"+city+"' ";
				}else {
					sql += "or D.state like '"+state+"' ";
				}
			}
			
			if(modelChoice == 1) {
				sql += "and T.model like '"+model+"' ";
			}else if(modelChoice == 2) {
				sql += "or T.model like '"+model+"' ";
			}
			
			sql += "group by C.vin order by AvgScore desc";
			
		}else {
			
			if(cityStateChoice == 1) {
				if(cityOrStateChoice == 1) {
					sql += "and D.city like '"+city+"' ";
				}else {
					sql += "and D.state like '"+state+"' ";
				}
			}else if(cityStateChoice == 2) {
				if(cityOrStateChoice == 1) {
					sql += "or D.city like '"+city+"' ";
				}else {
					sql += "or D.state like '"+state+"' ";
				}
			}
			
			if(modelChoice == 1) {
				sql += "and T.model like '"+model+"' ";
			}else if(modelChoice == 2) {
				sql += "or T.model like '"+model+"' ";
			}
			
			sql += "and F.login=Tr.login2 and Tr.isTrusted=1 and Tr.login1='"+login+"' ";
			
			sql += "group by C.vin order by AvgScore desc";
			
		}


		ArrayList<String> list = new ArrayList<String>();

		String output="";
		ResultSet rs=null;
//		System.out.println("executing "+sql);
		try{

			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add("VIN: " + rs.getString("vin") + ", Average Feedback Score: " + rs.getString("AvgScore"));
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
}

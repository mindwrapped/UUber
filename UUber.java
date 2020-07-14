package cs5530;


import java.lang.*;
import java.sql.*;
import java.util.ArrayList;
import java.io.*;

public class UUber {

	/**
	 * @param args
	 */
	public static void displayUUMenu() {
		System.out.println("        Welcome UUber User     ");
		System.out.println("1. Reserve an Uber Car:");
		System.out.println("2. Record ride with Uber Car:");
		System.out.println("3. Favorite Uber Car:");
		System.out.println("4. Record feedback for Uber Car:");
		System.out.println("5. Rate feedback:");
		System.out.println("6. Mark User as Trusted");
		System.out.println("7. Browse Uber Cars:");
		System.out.println("8. Find useful feedback for Uber Driver:");
		System.out.println("9. Statistics");
		System.out.println("10. Degrees Of Separation");
		System.out.println("11. Exit:");
		System.out.println("please enter your choice:");
	}

	public static void displayUDMenu()
	{
		System.out.println("        Welcome UUber Driver     ");
		System.out.println("1. Reserve an Uber Car:");
		System.out.println("2. Manage Your Uber Cars:");
		System.out.println("3. Record ride with Uber Car:");
		System.out.println("4. Favorite Uber Car:");
		System.out.println("5. Record feedback for Uber Car:");
		System.out.println("6. Rate feedback:");
		System.out.println("7. Browse Uber Cars:");
		System.out.println("8. Find useful feedback for Uber Driver:");
		System.out.println("9. Adjust available times to drive:");
		System.out.println("10. Mark User as Trusted");
		System.out.println("11. Statistics");
		System.out.println("12. Degrees Of Separation");
		System.out.println("13. Exit:");
		System.out.println("please enter your choice:");
	}

	public static void displayLoginMenu()
	{
		System.out.println("        Welcome to UUber System     ");
		System.out.println("1. Login:");
		System.out.println("2. Create a new user:");
		System.out.println("3. Exit:");
		System.out.println("please enter your choice:");
	}

	public static void ManageCarMenu() {
		System.out.println("        Manage Your Cars        ");
		System.out.println("1. Create New UUber Car");
		System.out.println("2. Manage an UUber Car");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Example for cs5530");
		Connector2 con=null;
		String choice;

		String loginName = "";
		String loginPassword;
		String name;
		String loginAddress;
		String loginPhone;
		boolean loggedIn = false;
		boolean uberDriver = false;

		String cname;
		String dname;
		String sql=null;
		int c=0;
		try
		{
			//remember to replace the password
			con= new Connector2();
			System.out.println ("Database connection established");

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while(true)
			{
				if(!loggedIn) {
					// Login menu
					displayLoginMenu();
					while ((choice = in.readLine()) == null && choice.length() == 0);
					try{
						c = Integer.parseInt(choice);
					}catch (Exception e)
					{
						continue;
					}
					if (c<1 | c>3)
						continue;
					if(c==1) {
						// Get login
						System.out.println("please enter a login:");
						while ((loginName = in.readLine()) == null && loginName.length() == 0);

						// Get password
						System.out.println("please enter a password:");
						while ((loginPassword = in.readLine()) == null && loginPassword.length() == 0);

						// Check UU and UD
						Login login = new Login();
						boolean tempUU = login.checkLogin("UU", loginName, loginPassword, con.stmt);
						boolean tempUD = login.checkLogin("UD", loginName, loginPassword, con.stmt);

						// Check if they are logged in
						if(tempUU || tempUD) {

							if(tempUD) {
								uberDriver = true;
							}else {
								uberDriver = false;
							}

							loggedIn = true;

							System.out.println("Successfully logged in.");
						}else {
							System.out.println("Error logging in. Try again.");
							continue;
						}
					}else if(c==2) {
						// Get login
						System.out.println("please enter a login:");
						while ((loginName = in.readLine()) == null && loginName.length() == 0);

						// Get password
						System.out.println("please enter a password:");
						while ((loginPassword = in.readLine()) == null && loginPassword.length() == 0);

						// Get name
						System.out.println("please enter a name:");
						while ((name = in.readLine()) == null && name.length() == 0);

						// Get street
						String loginStreet;
						System.out.println("please enter your street:");
						while ((loginStreet = in.readLine()) == null && loginStreet.length() == 0);

						// Get city
						String loginCity;
						System.out.println("please enter your city:");
						while ((loginCity = in.readLine()) == null && loginCity.length() == 0);

						// Get state
						String loginState;
						System.out.println("please enter a state:");
						while ((loginState = in.readLine()) == null && loginState.length() == 0);

						// Get phone
						System.out.println("please enter a phone:");
						while ((loginPhone = in.readLine()) == null && loginPhone.length() == 0);

						// Get whether they are a driver or not
						System.out.println("are you signing up to be an uber driver[y/n]:");
						while ((choice = in.readLine()) == null && choice.length() == 0 && !choice.equals("y") && !choice.equals("n") && !choice.equals("Y") && !choice.equals("N"));
						if(choice.equals("y") || choice.equals("Y")) {
							uberDriver = true;
						}else {
							uberDriver = false;
						}

						// Lowercase all entered info
						loginName = loginName.toLowerCase();
						loginPassword = loginPassword.toLowerCase();
						name = name.toLowerCase();
						loginStreet = loginStreet.toLowerCase();
						loginCity = loginCity.toLowerCase();
						loginState = loginState.toLowerCase();
						loginPhone = loginPhone.toLowerCase();

						// Check whether the user already exists
						Login login = new Login();
						boolean alreadyCreated;
						if(uberDriver) {
							alreadyCreated = login.checkIfUserExists("UD", loginName, con.stmt);
						}else {
							alreadyCreated = login.checkIfUserExists("UU", loginName, con.stmt);
						}

						// If login is already used then throw and error
						if(alreadyCreated) {
							System.out.println("Error: Login name already exists. Use a different login.");
							continue;
						}

						// Insert the new user in their table
						if(uberDriver) {
							loggedIn = login.createNewUser("UD", loginName, loginPassword, name, loginStreet, loginCity, loginState, loginPhone, con.stmt);
							login.createNewUser("UU", loginName, loginPassword, name, loginStreet, loginCity, loginState, loginPhone, con.stmt);
						}else {
							loggedIn = login.createNewUser("UU", loginName, loginPassword, name, loginStreet, loginCity, loginState, loginPhone, con.stmt);
						}

						// Check if the new user was created successfully
						if(loggedIn) {
							System.out.println("Successfully created account and logged in.");
						}else {
							System.out.println("Error could not make account.");
							continue;
						}
					}else if(c == 3) {
						// Exit and close db connection
						System.out.println("Exiting UUber System");
						con.stmt.close();
						break;
					}
				}

				System.out.println();

				// Display menu to user depending on if they are UD or UU
				if(uberDriver) {

































































					// UD Menu
					displayUDMenu();

					while ((choice = in.readLine()) == null && choice.length() == 0);
					try{
						c = Integer.parseInt(choice);
					}catch (Exception e)
					{
						continue;
					}
					if (c<1 | c>12)
						continue;
					if (c==1)
					{
						// Get starting reservation hour
						System.out.println("Enter the starting hour for your vehicle reservation:");
						String startStr = "";
						while ((startStr = in.readLine()) == null && startStr.length() == 0);
						int startingHour = 0;
						try{
							startingHour = Integer.parseInt(startStr);
						}catch (Exception e)
						{
							System.out.println("Error: Starting hour must be integer.");
							continue;
						}
						if (c<1 | c>24) {
							System.out.println("Error: Invalid starting hour time.");
							continue;
						}

						// Get ending reservation
						System.out.println("Enter the ending hour for your vehicle reservation:");
						String endStr;
						while ((endStr = in.readLine()) == null && endStr.length() == 0);
						int endingHour = 0;
						try{
							endingHour = Integer.parseInt(endStr);
						}catch (Exception e)
						{
							System.out.println("Error: Ending hour must be integer.");
							continue;
						}
						if (c<1 | c>24) {
							System.out.println("Error: Invalid ending hour time.");
							continue;
						}


						// Display available UC's during that period. 
						DriverAvailability driverAvailability = new DriverAvailability();
						ArrayList<String> list = driverAvailability.getAllAvailibities(startingHour, endingHour, con.stmt);

						if(list.size()>0) {
							System.out.println("Available vehicles during that time:");
							for(int i = 0; i < list.size(); i++) {
								System.out.println(i+1 + ". " + list.get(i).split(",")[0]);
							}
							System.out.println("please make a choice...");
						}else {
							System.out.println("No vehicles available during that time.");
							continue;
						}


						while ((choice = in.readLine()) == null && choice.length() == 0);
						try{
							c = Integer.parseInt(choice);
						}catch (Exception e)
						{
							System.out.println("Not a valid choice.");
							continue;
						}
						if (c<1 | c>list.size())
							continue;

						int vin = Integer.parseInt(list.get(c-1).split(" ")[5]);
						int periodID = Integer.parseInt(list.get(c-1).split(" ")[4]);
						String login = list.get(c-1).split(" ")[3];
						String make = list.get(c-1).split(" ")[0];
						String model = list.get(c-1).split(" ")[1];

						System.out.println("Are you sure you want to reserve " + make + " " + model + " for " + startingHour + " to " + endingHour + "? [y/n]");					
						while ((choice = in.readLine()) == null && choice.length() == 0 && !choice.equals("y") && !choice.equals("n") && !choice.equals("Y") && !choice.equals("N"));
						if(choice.equals("y") || choice.equals("Y")) {
							boolean result = driverAvailability.reserveCar(loginName, periodID, vin, (endingHour-startingHour)*5, con.stmt);

							if(result) {

								//UC SUGGESTIONS
								System.out.println("would you like to see some car suggestions? y/n");
								while ((choice = in.readLine()) == null && choice.length() == 0 && !choice.equals("y") && !choice.equals("n") && !choice.equals("Y") && !choice.equals("N"));
								if(choice.equals("y") || choice.equals("Y")) {
									Separation sep = new Separation();
									ArrayList<String> reccList = sep.UCSuggestion(loginName, vin, con.stmt);
									if (reccList.size() > 0) {
										System.out.println("Top " + 10 + "in Luxury");
										for (int i = 0; i < reccList.size(); i++) {
											System.out.println(i + 1 + ". " + reccList.get(i));
										}
									} else {
										System.out.println("No Reccomended Cars");
									}

								}


								System.out.println("Successfully reserved vehicle.");
							}else {
								System.out.println("Error reserving vehicles.");
							}
						}else {
							System.out.println("Cancelled.");
						}
					}
					else if (c==2)
					{	 
						ManageCarMenu();
						Register reg = new Register();

						while ((choice = in.readLine()) == null && choice.length() == 0);

						try {
							c = Integer.parseInt(choice);
						} catch (Exception e) {

							continue;
						}
						if (c == 1) {

							System.out.println("Enter your vehicle VIN #:");
							String StrVin;
							int vin;
							while ((StrVin = in.readLine()) == null && StrVin.length() == 0)
								;
							try {
								vin = Integer.parseInt(StrVin);

							} catch (Exception e) {
								System.out.println("Error: invalid vin, must be integer");
								continue;
							}

							// check if vin already exists in DB
							boolean vinExists = reg.vinExists("UC", vin, con.stmt);
							if (vinExists) {
								System.out.println("Error: vin # already exists");
								continue;
							}
							System.out.println("Enter vehicle make");
							String make;
							while ((make = in.readLine()) == null && make.length() == 0)
								;
							System.out.println("Enter vehicle model");
							String model;
							while ((model = in.readLine()) == null && model.length() == 0)
								;

							System.out.println("Enter the number of your new vehicle category:");
							System.out.println("1. Economy");
							System.out.println("2. Comfort");
							System.out.println("3. Luxury");

							String cat;
							while ((cat = in.readLine()) == null && cat.length() == 0)
								;
							try {
								c = Integer.parseInt(cat);
							} catch (Exception e) {

								continue;
							}
							if (c == 1) {
								boolean registerCar = reg.createNewCar("UC", vin, "Economy", loginName, con.stmt);
								reg.addType("Ctypes", make, model, con.stmt);
								boolean connectType = reg.connectCarType("IsCtypes", vin, make, model, con.stmt);
								// Check if the new car was registered successfully
								if (registerCar && connectType) {
									System.out.println("Successfully updated car");
								} else {
									System.out.println("Error could not register car");
									continue;
								}
							}
							else if (c == 2) {
								boolean registerCar = reg.createNewCar("UC", vin, "Comfort", loginName, con.stmt);
								reg.addType("Ctypes", make, model, con.stmt);
								boolean connectType = reg.connectCarType("IsCtypes", vin, make, model, con.stmt);
								// Check if the new car was registered successfully
								if (registerCar && connectType) {
									System.out.println("Successfully updated car");
								} else {
									System.out.println("Error could not register car");
									continue;
								}
							}else if (c == 3) {
								boolean registerCar = reg.createNewCar("UC", vin, "Luxury", loginName, con.stmt);
								reg.addType("Ctypes", make, model, con.stmt);
								boolean connectType = reg.connectCarType("IsCtypes", vin, make, model, con.stmt);
								// Check if the new car was registered successfully
								if (registerCar && connectType) {
									System.out.println("Successfully updated car");
								} else {
									System.out.println("Error could not register car");
									continue;
								}
							}
						} else if (c == 2) {

							System.out.println("Enter your vehicle VIN #:");
							String StrVin;
							int vin;
							while ((StrVin = in.readLine()) == null && StrVin.length() == 0)
								;
							try {
								vin = Integer.parseInt(StrVin);

							} catch (Exception e) {
								System.out.println("Error: invalid vin, must be integer");
								continue;
							}

							// check if vin already exists in DB
							boolean loginVin = reg.isLoggedInVin("UC", loginName, vin, con.stmt);
							if (loginVin) {
								reg.deleteCarType("IsCtypes", vin, con.stmt);
								reg.removeCar("UC", vin, con.stmt);
								System.out.println("Enter new make");
								String make;
								while ((make = in.readLine()) == null && make.length() == 0)
									;
								System.out.println("Enter new model");
								String model;
								while ((model = in.readLine()) == null && model.length() == 0)
									;

								System.out.println("Enter the number of your new vehicle category:");
								System.out.println("1. Economy");
								System.out.println("2. Comfort");
								System.out.println("3. Luxury");

								String cat;
								while ((cat = in.readLine()) == null && cat.length() == 0)
									;
								try {
									c = Integer.parseInt(cat);
								} catch (Exception e) {

									continue;
								}
								if (c == 1) {
									boolean registerCar = reg.createNewCar("UC", vin, "Economy", loginName, con.stmt);
									reg.addType("Ctypes", make, model, con.stmt);
									boolean connectType = reg.connectCarType("IsCtypes", vin, make, model, con.stmt);
									// Check if the new car was registered successfully
									if (registerCar && connectType) {
										System.out.println("Successfully updated car");
									} else {
										System.out.println("Error could not register car");
										continue;
									}
								}
								else if (c == 2) {
									boolean registerCar = reg.createNewCar("UC", vin, "Comfort", loginName, con.stmt);
									reg.addType("Ctypes", make, model, con.stmt);
									boolean connectType = reg.connectCarType("IsCtypes", vin, make, model, con.stmt);
									// Check if the new car was registered successfully
									if (registerCar && connectType) {
										System.out.println("Successfully updated car");
									} else {
										System.out.println("Error could not register car");
										continue;
									}
								}else if (c == 3) {
									boolean registerCar = reg.createNewCar("UC", vin, "Luxury", loginName, con.stmt);
									reg.addType("Ctypes", make, model, con.stmt);
									boolean connectType = reg.connectCarType("IsCtypes", vin, make, model, con.stmt);
									// Check if the new car was registered successfully
									if (registerCar && connectType) {
										System.out.println("Successfully updated car");
									} else {
										System.out.println("Error could not register car");
										continue;
									}
								}

							} else {
								System.out.println("Invalid vin #");
								continue;
							}

						} else {
							continue;
						}


					}else if (c==3){
						// Get starting reservation hour
						System.out.println("Enter the starting hour for your ride:");
						String startStr = "";
						while ((startStr = in.readLine()) == null && startStr.length() == 0);
						int startingHour = 0;
						try{
							startingHour = Integer.parseInt(startStr);
						}catch (Exception e)
						{
							System.out.println("Error: Starting hour must be integer.");
							continue;
						}
						if (c<1 | c>24) {
							System.out.println("Error: Invalid starting hour time.");
							continue;
						}

						// Get ending reservation
						System.out.println("Enter the ending hour for your ride:");
						String endStr;
						while ((endStr = in.readLine()) == null && endStr.length() == 0);
						int endingHour = 0;
						try{
							endingHour = Integer.parseInt(endStr);
						}catch (Exception e)
						{
							System.out.println("Error: Ending hour must be integer.");
							continue;
						}
						if (c<1 | c>24) {
							System.out.println("Error: Invalid ending hour time.");
							continue;
						}

						// Get vin number for ride
						System.out.println("Enter the vin number for your ride:");
						String vinStr;
						while ((vinStr = in.readLine()) == null && vinStr.length() == 0);
						int vin = 0;
						try{
							vin = Integer.parseInt(vinStr);
						}catch (Exception e)
						{
							System.out.println("Error: Vin must be integer.");
							continue;
						}

						// Get cost for ride
						System.out.println("Enter the cost for your ride:");
						String costStr;
						while ((costStr = in.readLine()) == null && costStr.length() == 0);
						int cost = 0;
						try{
							cost = Integer.parseInt(costStr);
						}catch (Exception e)
						{
							System.out.println("Error: Cost must be integer.");
							continue;
						}

						DriverAvailability driverAvailability = new DriverAvailability();

						System.out.println("Are you sure you want to log a ride for vin: " + vin + ", cost: " + cost + " from " + startingHour + " to " + endingHour + "? [y/n]");					
						while ((choice = in.readLine()) == null && choice.length() == 0 && !choice.equals("y") && !choice.equals("n") && !choice.equals("Y") && !choice.equals("N"));
						if(choice.equals("y") || choice.equals("Y")) {
							boolean result = driverAvailability.logRide(loginName, vin, cost, startingHour, endingHour, con.stmt);

							if(result) {
								System.out.println("Successfully logged ride.");
							}else {
								System.out.println("Error logging ride.");
							}
						}else {
							System.out.println("Cancelled.");
						}

					}else if (c==4){


						System.out.println("Enter the vin of the vehicle you would like to favorite:");
						String vinStr;
						int vin;
						while((vinStr = in.readLine()) == null && vinStr.length() == 0);
						try 
						{
							vin = Integer.parseInt(vinStr);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid vin, must be integer"); 
							continue;
						}

						// Set as favorite
						Favorite favorite = new Favorite();
						boolean result = favorite.favoriteVehicle(loginName, vin, con.stmt);

						if(result) {
							System.out.println("Successfully favorited vehicle.");
						}else {
							System.out.println("Error favoriting vehicle.");
						}


					}else if (c==5){
						// Get the vin number
						System.out.println("Enter the vin of the vehicle you would like to record feedback for:");
						String vinStr;
						int vin;
						while((vinStr = in.readLine()) == null && vinStr.length() == 0);
						try 
						{
							vin = Integer.parseInt(vinStr);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid vin, must be integer"); 
							continue;
						}

						// Get the score
						System.out.println("Enter the score you would like to give this vehicle (0-10):");
						String scoreStr;
						int score;
						while((scoreStr = in.readLine()) == null && scoreStr.length() == 0);
						try 
						{
							score = Integer.parseInt(scoreStr);
							if(score < 0 || score > 10) {
								System.out.println("Error: invalid score, must be between 0 and 10.");
								continue;
							}
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid score, must be integer"); 
							continue;
						}

						// Optionally get text for the feedback
						System.out.println("Optionally, explain why you gave this score or else just enter nothing:");
						String text;
						while((text = in.readLine()) == null);

						Feedback feedback = new Feedback();

						boolean result = feedback.recordFeedbackForVehicle(loginName, vin, score, text, con.stmt);

						if(result) {
							System.out.println("Successfully recorded feedback for vehicle.");
						}else {
							System.out.println("Error recording feedback for vehicle.");
						}


					}else if (c==6){
						// Get fid
						System.out.println("Enter the fid of the Feedback you would like to rate:");
						int fid = 0;
						while ((choice = in.readLine()) == null && choice.length() == 0)
							;
						Feedback feedback = new Feedback();
						try {
							fid = Integer.parseInt(choice);
							boolean sameLogin = feedback.CheckLogin(loginName, fid, con.stmt);
							if(sameLogin)
							{
								System.out.println("Error: Cannot rate own feedback.");
								continue;
							}
						} catch (Exception e) {
							System.out.println("Error: Fid must be an integer.");
							continue;
						}

						// Get the rating
						System.out.println("Select the rating you would like to give this Feedback:");
						System.out.println("1. Very Useful.");
						System.out.println("2. Useful.");
						System.out.println("3. Useless.");
						System.out.println("4. Cancel.");
						System.out.println("please make a choice...");
						while ((choice = in.readLine()) == null && choice.length() == 0)
							;
						try {
							c = Integer.parseInt(choice);
						} catch (Exception e) {
							System.out.println("Error invalid choice.");
							continue;
						}
						if (c < 1 | c > 4) {
							System.out.println("Error invalid choice.");
							continue;
						}


						if (c == 1) {
							String rating = "VERY USEFUL";
							boolean result = feedback.rateFeedback(loginName, fid, rating, con.stmt);

							if (result) {
								System.out.println("Successfully rated feedback.");
							} else {
								System.out.println("Error rating feedback.");
							}
						} else if (c == 2) {
							String rating = "USEFUL";
							boolean result = feedback.rateFeedback(loginName, fid, rating, con.stmt);

							if (result) {
								System.out.println("Successfully rated feedback.");
							} else {
								System.out.println("Error rating feedback.");
							}
						} else if (c == 3) {
							String rating = "USELESS";
							boolean result = feedback.rateFeedback(loginName, fid, rating, con.stmt);

							if (result) {
								System.out.println("Successfully rated feedback.");
							} else {
								System.out.println("Error rating feedback.");
							}
						} else {
							continue;
						}

					}else if (c == 7){
						// Get category
						System.out.println("Which category do you want to query by:");
						System.out.println("1. Economy");
						System.out.println("2. Comfort");
						System.out.println("3. Luxury");
						String categoryStr;
						while((categoryStr = in.readLine()) == null && categoryStr.length() == 0);
						try 
						{
							c = Integer.parseInt(categoryStr);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid choice, must be integer"); 
							continue;
						}
						if(c<1 || c>3) {
							System.out.println("Invalid choice.");
							continue;
						}

						String category;

						if(c==1) {
							category="Economy";
						}else if(c==2) {
							category="Comfort";
						}else {
							category="Luxury";
						}

						// Get category
						System.out.println("Enter how you want to query by city or state:");
						System.out.println("1. AND city or state");
						System.out.println("2. OR city or state");
						System.out.println("3. Do not use city or state in query");
						while((choice = in.readLine()) == null && choice.length() == 0);
						try 
						{
							c = Integer.parseInt(choice);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid choice, must be integer"); 
							continue;
						}
						if(c<1 || c>3) {
							System.out.println("Invalid choice.");
							continue;
						}

						int choiceNum = c;
						int cityOrStateNum = 0;
						String city = "";
						String state = "";

						if(choiceNum != 3) {
							// Get city or state
							System.out.println("Do you want to use city or state:");
							System.out.println("1. Use city");
							System.out.println("2. Use state");
							while((choice = in.readLine()) == null && choice.length() == 0);
							try 
							{
								c = Integer.parseInt(choice);
							}
							catch(Exception e)
							{
								System.out.println("Error: invalid choice, must be integer"); 
								continue;
							}
							if(c<1 || c>2) {
								System.out.println("Invalid choice.");
								continue;
							}

							cityOrStateNum = c;

							if(c==1) {
								System.out.println("Enter the city:");
								while((city = in.readLine()) == null && city.length() == 0);
							}else {
								System.out.println("Enter the state:");
								while((state = in.readLine()) == null && state.length() == 0);
							}
						}

						// Get model query
						System.out.println("Enter how you want to query by model:");
						System.out.println("1. AND model");
						System.out.println("2. OR model");
						System.out.println("3. Do not use model in query");
						while((choice = in.readLine()) == null && choice.length() == 0);
						try 
						{
							c = Integer.parseInt(choice);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid choice, must be integer"); 
							continue;
						}
						if(c<1 || c>3) {
							System.out.println("Invalid choice.");
							continue;
						}

						int modelNum = c;
						String model = "";

						if(c != 3) {
							System.out.println("Enter the model:");
							while((model = in.readLine()) == null && model.length() == 0);
						}

						// Get how they want the results ordered
						System.out.println("How do you want the results ordered:");
						System.out.println("1. By average numerical score of feedbacks.");
						System.out.println("2. By average numerical score of trusted user feedbacks.");
						while((choice = in.readLine()) == null && choice.length() == 0);
						try 
						{
							c = Integer.parseInt(choice);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid choice, must be integer"); 
							continue;
						}
						if(c<1 || c>2) {
							System.out.println("Invalid choice.");
							continue;
						}

						int orderingNum = c;


						Browsing browsing = new Browsing();

						ArrayList<String> list = browsing.browseUserCars(loginName, category, city, state, model, choiceNum, cityOrStateNum, modelNum, orderingNum, con.stmt);

						if(list.size() == 0) {
							System.out.println("No Uber Cars found from this query.");
							continue;
						}else {
							for(int i = 0; i < list.size(); i++) {
								System.out.println(i+1 + ". " + list.get(i));
							}
						}



					}else if (c==8){
						// Get the login of the UD
						System.out.println("Enter the login of the driver you would like to see feedback for:");
						String driverStr;
						while((driverStr = in.readLine()) == null && driverStr.length() == 0);

						Login login = new Login();
						if(!login.checkIfUserExists("UD", driverStr, con.stmt)) {
							System.out.println("Error: nonexistant driver login.");
							continue;
						}

						// Get the number of useful feedbacks to display
						System.out.println("Up to how many useful feedbacks would you like to display?");
						String numStr;
						int numberOfFeedbacks;
						while((numStr = in.readLine()) == null && numStr.length() == 0);
						try 
						{
							numberOfFeedbacks = Integer.parseInt(numStr);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid number of feedbacks, must be integer"); 
							continue;
						}

						// Get and display feedbacks
						Feedback feedback = new Feedback();
						ArrayList<String> list = feedback.getUsefulFeedback(driverStr, numberOfFeedbacks, con.stmt);

						if(list.size() == 0) {
							System.out.println("No useful feedback found for the driver.");
							continue;
						}

						for(int i = 0; i < list.size(); i++) {
							System.out.println(i+1 + ". " + list.get(i));
						}

					}else if (c==9){
						DriverAvailability driverAvailability = new DriverAvailability(); 
						ArrayList<String> currentAvailabilities = driverAvailability.getCurrentAvailibities(loginName, con.stmt);

						System.out.println();

						if(currentAvailabilities.size() == 0) {
							System.out.println("No current availabilities.");
						}else {
							System.out.println("Current Availibilities:");
						}

						for(String item: currentAvailabilities) {
							System.out.println(item.split("PeriodID")[0]);
						}

						System.out.println();

						System.out.println("1. Add new availability.");
						System.out.println("2. Delete availability.");
						System.out.println("3. Exit.");

						System.out.println("please make a choice...");

						while ((choice = in.readLine()) == null && choice.length() == 0);
						try{
							c = Integer.parseInt(choice);
						}catch (Exception e)
						{
							continue;
						}
						if (c<1 | c>3)
							continue;

						if(c == 1) {
							// Get starting hour and make sure it is valid
							System.out.println("please enter a starting hour (1-24):");
							while ((choice = in.readLine()) == null && choice.length() == 0);

							int startingHour = 0;
							try{
								startingHour = Integer.parseInt(choice);
								if (startingHour<1 | startingHour>24) {
									System.out.println("Error: Not a valid starting hour.");
									continue;
								}
							}catch (Exception e)
							{
								System.out.println("Error: valid starting time not entered.");
								continue;
							}

							// Get ending hour and make sure it is valid
							System.out.println("please enter an ending hour (1-24):");
							while ((choice = in.readLine()) == null && choice.length() == 0);

							int endingHour = 0;
							try{
								endingHour = Integer.parseInt(choice);
								if (endingHour<1 | endingHour>24) {
									System.out.println("Error: Not a valid ending hour.");
									continue;
								}
							}catch (Exception e)
							{
								System.out.println("Error: valid ending time not entered.");
								continue;
							}

							if(startingHour >= endingHour) {
								System.out.println("Error: Starting hour can't be equal to or greater than ending hour.");
								continue;
							}

							boolean result = driverAvailability.setDriverAvailability(loginName, startingHour, endingHour, con.stmt);

							if(result) {
								System.out.println("Successfully added new availability.");
							}else {
								System.out.println("Error: could not add new availability.");
							}
						}else if (c == 2) {

							if(currentAvailabilities.size() == 0) {
								System.out.println("No availabilities to delete.");
								continue;
							}else {
								System.out.println("Which availability would you like to delete?");

								for(int i = 0; i < currentAvailabilities.size(); i++) {
									System.out.println(i+1 + ". " + currentAvailabilities.get(i));
								}

								while ((choice = in.readLine()) == null && choice.length() == 0);
								try{
									c = Integer.parseInt(choice);
								}catch (Exception e)
								{
									continue;
								}
								if (c<1 | c>currentAvailabilities.size())
									continue;

								int periodID = Integer.parseInt(currentAvailabilities.get(c-1).split(" ")[5]);

								boolean result = driverAvailability.deleteAvailability(loginName, periodID, con.stmt);

								if(result) {
									System.out.println("Successfully deleted availability.");
								}else {
									System.out.println("Error deleting availability.");
								}
							}
						}else {
							continue;
						}



					}else if(c==10) {
						System.out.println("Please Enter user login to mark user as trusted/untrusted");
						String log2;
						while ((log2 = in.readLine()) == null && log2.length() == 0);
						Login login = new Login();
						Favorite favorite = new Favorite();
						boolean exists = login.checkIfUserExists("UU", log2, con.stmt);
						if(exists)
						{
							System.out.println("Choose how to mark this user");
							System.out.println("1. Not Trusted");
							System.out.println("2. Trusted");
							String t;
							while ((t = in.readLine()) == null && t.length() == 0);
							int trust = Integer.parseInt(t);

							try {
								if (trust < 1 || trust > 2) {
									System.out.println("Error: invalid selection, must be between 1 and 2.");
									continue;
								}
							} catch (Exception e) {
								System.out.println("Error: invalid score, must be integer");
								continue;
							}
							boolean result = favorite.markTrust(loginName, log2, trust-1, con.stmt);
							if(result)
							{
								System.out.println("Successfully marked user");
							}
							else {
								System.out.println("Error Marking user");
								continue;

							}
						}
					}else if(c==11){
						System.out.println("Available Statistics:");
						System.out.println("1. Most Popular UCs");
						System.out.println("2. Most Expensive UCs");
						System.out.println("3. Most Highly Rated UDs");
						System.out.println("Please Choose One:");

						String sChoose;
						while ((sChoose = in.readLine()) == null && sChoose.length() == 0);

						try {
							c = Integer.parseInt(sChoose);
						} catch (Exception e) {
							System.out.println("Error invalid choice.");
							continue;
						}
						if (c < 1 | c > 3) {
							System.out.println("Error invalid choice.");
							continue;
						}

						Statistics stat = new Statistics();

						if(c == 1)
						{
							System.out.println("How many cars do you want to show?");

							while ((sChoose = in.readLine()) == null && sChoose.length() == 0);
							int m = 0;
							try {
								m = Integer.parseInt(sChoose);
							} catch (Exception e) {
								System.out.println("Error invalid choice.");
								continue;
							}

							ArrayList<String> list = stat.mostPopularUCs(m, "Economy", con.stmt);
							if (list.size() > 0) {
								System.out.println("Top " + m + " in Economy");
								for (int i = 0; i < list.size(); i++) {
									System.out.println(i + 1 + ". " + list.get(i));
								}
							} else {
								System.out.println("No Stats to show for Economy category");
							}

							ArrayList<String> list2 = stat.mostPopularUCs(m, "Luxury", con.stmt);
							if (list2.size() > 0) {
								System.out.println("Top " + m + " in Luxury");
								for (int i = 0; i < list2.size(); i++) {
									System.out.println(i + 1 + ". " + list2.get(i));
								}
							} else {
								System.out.println("No Stats to show for Luxury Category");
							}

							ArrayList<String> list3 = stat.mostPopularUCs(m, "Comfort", con.stmt);
							if (list3.size() > 0) {
								System.out.println("Top " + m + " in Comfort");
								for (int i = 0; i < list3.size(); i++) {
									System.out.println(i + 1 + ". " + list3.get(i));
								}
							} else {
								System.out.println("No Stats to show Luxury Category");
							}


						}
						if(c == 2)
						{

							System.out.println("How many cars do you want to show?");

							while ((sChoose = in.readLine()) == null && sChoose.length() == 0);
							int m = 0;
							try {
								m = Integer.parseInt(sChoose);
							} catch (Exception e) {
								System.out.println("Error invalid choice.");
								continue;
							}

							ArrayList<String> list = stat.mostExpensiveUCs(m, "Economy", con.stmt);
							if (list.size() > 0) {
								System.out.println("Most Expensive " + m + " in Economy");
								for (int i = 0; i < list.size(); i++) {
									System.out.println(i + 1 + ". " + list.get(i));
								}
							} else {
								System.out.println("No Stats to show for Economy category");
							}

							ArrayList<String> list2 = stat.mostExpensiveUCs(m, "Luxury", con.stmt);
							if (list2.size() > 0) {
								System.out.println("Most Expensive " + m + " in Luxury");
								for (int i = 0; i < list2.size(); i++) {
									System.out.println(i + 1 + ". " + list2.get(i));
								}
							} else {
								System.out.println("No Stats to show for Luxury Category");
							}

							ArrayList<String> list3 = stat.mostExpensiveUCs(m, "Comfort", con.stmt);
							if (list3.size() > 0) {
								System.out.println("Most Expensive " + m + " in Comfort");
								for (int i = 0; i < list3.size(); i++) {
									System.out.println(i + 1 + ". " + list3.get(i));
								}
							} else {
								System.out.println("No Stats to show Luxury Category");
							}

						}
						if(c == 3)
						{
							System.out.println("How many drivers do you want to show?");

							while ((sChoose = in.readLine()) == null && sChoose.length() == 0);
							int m = 0;
							try {
								m = Integer.parseInt(sChoose);
							} catch (Exception e) {
								System.out.println("Error invalid choice.");
								continue;
							}

							ArrayList<String> list = stat.highlyRatedUDs(m, "Economy", con.stmt);
							if (list.size() > 0) {
								System.out.println(m + " Highest Scores in Economy");
								for (int i = 0; i < list.size(); i++) {
									System.out.println(i + 1 + ". " + list.get(i));
								}
							} else {
								System.out.println("No Stats to show for Economy category");
							}

							ArrayList<String> list2 = stat.highlyRatedUDs(m, "Luxury", con.stmt);
							if (list2.size() > 0) {
								System.out.println(m + " Highest Scores in Luxury");
								for (int i = 0; i < list2.size(); i++) {
									System.out.println(i + 1 + ". " + list2.get(i));
								}
							} else {
								System.out.println("No Stats to show for Luxury Category");
							}

							ArrayList<String> list3 = stat.highlyRatedUDs(m, "Comfort", con.stmt);
							if (list3.size() > 0) {
								System.out.println(m + " Highest Scores in Comfort");
								for (int i = 0; i < list3.size(); i++) {
									System.out.println(i + 1 + ". " + list3.get(i));
								}
							} else {
								System.out.println("No Stats to show for Comfort category");
							}

						}
					}else if(c==12) {
						// Get the first login
						System.out.println("Enter the first login:");
						String firstLogStr;
						while ((firstLogStr = in.readLine()) == null && firstLogStr.length() == 0);

						// Get the second login
						System.out.println("Enter the second login:");
						String secLogStr;
						while ((secLogStr = in.readLine()) == null && secLogStr.length() == 0);

						// Check for one degree of separation
						Separation separation = new Separation();
						boolean result = separation.oneDegreeOfSeparation(firstLogStr, secLogStr, con.stmt);

						if(result) {
							System.out.println("Users are 1-degree away.");
							continue;
						}

						result = separation.twoDegreesOfSeparation(firstLogStr, secLogStr, con.stmt);
						if(result) {
							System.out.println("Users are 2-degrees away.");
							continue;
						}else {
							System.out.println("Users are not 1 or 2 degrees away.");
						}

					}else{
						System.out.println("Exiting UUber System");
						con.stmt.close();
						break;
					}
				}else {






























































					// UU Menu
					displayUUMenu();

					while ((choice = in.readLine()) == null && choice.length() == 0);
					try{
						c = Integer.parseInt(choice);
					}catch (Exception e)
					{
						continue;
					}
					if (c<1 | c>11)
						continue;
					if (c==1)
					{
						// Get starting reservation hour
						System.out.println("Enter the starting hour for your vehicle reservation:");
						String startStr = "";
						while ((startStr = in.readLine()) == null && startStr.length() == 0);
						int startingHour = 0;
						try{
							startingHour = Integer.parseInt(startStr);
						}catch (Exception e)
						{
							System.out.println("Error: Starting hour must be integer.");
							continue;
						}
						if (c<1 | c>24) {
							System.out.println("Error: Invalid starting hour time.");
							continue;
						}

						// Get ending reservation
						System.out.println("Enter the ending hour for your vehicle reservation:");
						String endStr;
						while ((endStr = in.readLine()) == null && endStr.length() == 0);
						int endingHour = 0;
						try{
							endingHour = Integer.parseInt(endStr);
						}catch (Exception e)
						{
							System.out.println("Error: Ending hour must be integer.");
							continue;
						}
						if (c<1 | c>24) {
							System.out.println("Error: Invalid ending hour time.");
							continue;
						}


						// Display available UC's during that period. 
						DriverAvailability driverAvailability = new DriverAvailability();
						ArrayList<String> list = driverAvailability.getAllAvailibities(startingHour, endingHour, con.stmt);

						if(list.size()>0) {
							System.out.println("Available vehicles during that time:");
							for(int i = 0; i < list.size(); i++) {
								System.out.println(i+1 + ". " + list.get(i).split(",")[0]);
							}
							System.out.println("please make a choice...");
						}else {
							System.out.println("No vehicles available during that time.");
							continue;
						}


						while ((choice = in.readLine()) == null && choice.length() == 0);
						try{
							c = Integer.parseInt(choice);
						}catch (Exception e)
						{
							System.out.println("Not a valid choice.");
							continue;
						}
						if (c<1 | c>list.size())
							continue;

						int vin = Integer.parseInt(list.get(c-1).split(" ")[5]);
						int periodID = Integer.parseInt(list.get(c-1).split(" ")[4]);
						String login = list.get(c-1).split(" ")[3];
						String make = list.get(c-1).split(" ")[0];
						String model = list.get(c-1).split(" ")[1];

						System.out.println("Are you sure you want to reserve " + make + " " + model + " for " + startingHour + " to " + endingHour + "? [y/n]");					
						while ((choice = in.readLine()) == null && choice.length() == 0 && !choice.equals("y") && !choice.equals("n") && !choice.equals("Y") && !choice.equals("N"));
						if(choice.equals("y") || choice.equals("Y")) {
							boolean result = driverAvailability.reserveCar(loginName, periodID, vin, (endingHour-startingHour)*5, con.stmt);

							if(result) {

								//UC SUGGESTIONS
								System.out.println("would you like to see some car suggestions? y/n");
								while ((choice = in.readLine()) == null && choice.length() == 0 && !choice.equals("y") && !choice.equals("n") && !choice.equals("Y") && !choice.equals("N"));
								if(choice.equals("y") || choice.equals("Y")) {
									Separation sep = new Separation();
									ArrayList<String> reccList = sep.UCSuggestion(loginName, vin, con.stmt);
									if (reccList.size() > 0) {
										System.out.println("Top " + 10 + "in Luxury");
										for (int i = 0; i < reccList.size(); i++) {
											System.out.println(i + 1 + ". " + reccList.get(i));
										}
									} else {
										System.out.println("No Reccomended Cars");
									}

								}


								System.out.println("Successfully reserved vehicle.");
							}else {
								System.out.println("Error reserving vehicles.");
							}
						}else {
							System.out.println("Cancelled.");
						}
					}
					else if (c==2)
					{	 
						// Get starting reservation hour
						System.out.println("Enter the starting hour for your ride:");
						String startStr = "";
						while ((startStr = in.readLine()) == null && startStr.length() == 0);
						int startingHour = 0;
						try{
							startingHour = Integer.parseInt(startStr);
						}catch (Exception e)
						{
							System.out.println("Error: Starting hour must be integer.");
							continue;
						}
						if (c<1 | c>24) {
							System.out.println("Error: Invalid starting hour time.");
							continue;
						}

						// Get ending reservation
						System.out.println("Enter the ending hour for your ride:");
						String endStr;
						while ((endStr = in.readLine()) == null && endStr.length() == 0);
						int endingHour = 0;
						try{
							endingHour = Integer.parseInt(endStr);
						}catch (Exception e)
						{
							System.out.println("Error: Ending hour must be integer.");
							continue;
						}
						if (c<1 | c>24) {
							System.out.println("Error: Invalid ending hour time.");
							continue;
						}

						// Get vin number for ride
						System.out.println("Enter the vin number for your ride:");
						String vinStr;
						while ((vinStr = in.readLine()) == null && vinStr.length() == 0);
						int vin = 0;
						try{
							vin = Integer.parseInt(vinStr);
						}catch (Exception e)
						{
							System.out.println("Error: Vin must be integer.");
							continue;
						}

						// Get cost for ride
						System.out.println("Enter the cost for your ride:");
						String costStr;
						while ((costStr = in.readLine()) == null && costStr.length() == 0);
						int cost = 0;
						try{
							cost = Integer.parseInt(costStr);
						}catch (Exception e)
						{
							System.out.println("Error: Cost must be integer.");
							continue;
						}

						DriverAvailability driverAvailability = new DriverAvailability();

						System.out.println("Are you sure you want to log a ride for vin: " + vin + ", cost: " + cost + " from " + startingHour + " to " + endingHour + "? [y/n]");					
						while ((choice = in.readLine()) == null && choice.length() == 0 && !choice.equals("y") && !choice.equals("n") && !choice.equals("Y") && !choice.equals("N"));
						if(choice.equals("y") || choice.equals("Y")) {
							boolean result = driverAvailability.logRide(loginName, vin, cost, startingHour, endingHour, con.stmt);

							if(result) {
								System.out.println("Successfully logged ride.");
							}else {
								System.out.println("Error logging ride.");
							}
						}else {
							System.out.println("Cancelled.");
						}
					}else if (c==3){
						System.out.println("Enter the vin of the vehicle you would like to favorite:");
						String vinStr;
						int vin;
						while((vinStr = in.readLine()) == null && vinStr.length() == 0);
						try 
						{
							vin = Integer.parseInt(vinStr);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid vin, must be integer"); 
							continue;
						}

						// Set as favorite
						Favorite favorite = new Favorite();
						boolean result = favorite.favoriteVehicle(loginName, vin, con.stmt);

						if(result) {
							System.out.println("Successfully favorited vehicle.");
						}else {
							System.out.println("Error favoriting vehicle.");
						}


					}else if (c==4){
						// Get the vin number
						System.out.println("Enter the vin of the vehicle you would like to record feedback for:");
						String vinStr;
						int vin;
						while((vinStr = in.readLine()) == null && vinStr.length() == 0);
						try 
						{
							vin = Integer.parseInt(vinStr);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid vin, must be integer"); 
							continue;
						}

						// Get the score
						System.out.println("Enter the score you would like to give this vehicle (0-10):");
						String scoreStr;
						int score;
						while((scoreStr = in.readLine()) == null && scoreStr.length() == 0);
						try 
						{
							score = Integer.parseInt(scoreStr);
							if(score < 0 || score > 10) {
								System.out.println("Error: invalid score, must be between 0 and 10.");
								continue;
							}
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid score, must be integer"); 
							continue;
						}

						// Optionally get text for the feedback
						System.out.println("Optionally, explain why you gave this score or else just enter nothing:");
						String text;
						while((text = in.readLine()) == null);

						Feedback feedback = new Feedback();

						boolean result = feedback.recordFeedbackForVehicle(loginName, vin, score, text, con.stmt);

						if(result) {
							System.out.println("Successfully recorded feedback for vehicle.");
						}else {
							System.out.println("Error recording feedback for vehicle.");
						}
					}else if (c==5){
						// Get fid
						System.out.println("Enter the fid of the Feedback you would like to rate:");
						int fid = 0;
						while ((choice = in.readLine()) == null && choice.length() == 0)
							;
						Feedback feedback = new Feedback();
						try {
							fid = Integer.parseInt(choice);
							boolean sameLogin = feedback.CheckLogin(loginName, fid, con.stmt);
							if(sameLogin)
							{
								System.out.println("Error: Cannot rate own feedback.");
								continue;
							}
						} catch (Exception e) {
							System.out.println("Error: Fid must be an integer.");
							continue;
						}

						// Get the rating
						System.out.println("Select the rating you would like to give this Feedback:");
						System.out.println("1. Very Useful.");
						System.out.println("2. Useful.");
						System.out.println("3. Useless.");
						System.out.println("4. Cancel.");
						System.out.println("please make a choice...");
						while ((choice = in.readLine()) == null && choice.length() == 0)
							;
						try {
							c = Integer.parseInt(choice);
						} catch (Exception e) {
							System.out.println("Error invalid choice.");
							continue;
						}
						if (c < 1 | c > 4) {
							System.out.println("Error invalid choice.");
							continue;
						}


						if (c == 1) {
							String rating = "VERY USEFUL";
							boolean result = feedback.rateFeedback(loginName, fid, rating, con.stmt);

							if (result) {
								System.out.println("Successfully rated feedback.");
							} else {
								System.out.println("Error rating feedback.");
							}
						} else if (c == 2) {
							String rating = "USEFUL";
							boolean result = feedback.rateFeedback(loginName, fid, rating, con.stmt);

							if (result) {
								System.out.println("Successfully rated feedback.");
							} else {
								System.out.println("Error rating feedback.");
							}
						} else if (c == 3) {
							String rating = "USELESS";
							boolean result = feedback.rateFeedback(loginName, fid, rating, con.stmt);

							if (result) {
								System.out.println("Successfully rated feedback.");
							} else {
								System.out.println("Error rating feedback.");
							}
						} else {
							continue;
						}
					}else if (c==6){
						System.out.println("Please Enter user login to mark user as trusted/untrusted");
						String log2;
						while ((log2 = in.readLine()) == null && log2.length() == 0);
						Login login = new Login();
						Favorite favorite = new Favorite();
						boolean exists = login.checkIfUserExists("UU", log2, con.stmt);
						if(exists)
						{
							System.out.println("Choose how to mark this user");
							System.out.println("1. Not Trusted");
							System.out.println("2. Trusted");
							String t;
							while ((t = in.readLine()) == null && t.length() == 0);
							int trust = Integer.parseInt(t);

							try {
								if (trust < 1 || trust > 2) {
									System.out.println("Error: invalid selection, must be between 1 and 2.");
									continue;
								}
							} catch (Exception e) {
								System.out.println("Error: invalid score, must be integer");
								continue;
							}
							boolean result = favorite.markTrust(loginName, log2, trust-1, con.stmt);
							if(result)
							{
								System.out.println("Successfully marked user");
							}
							else {
								System.out.println("Error Marking user");
								continue;

							}
						}
						
					}else if (c==7){
						// Get category
						System.out.println("Which category do you want to query by:");
						System.out.println("1. Economy");
						System.out.println("2. Comfort");
						System.out.println("3. Luxury");
						String categoryStr;
						while((categoryStr = in.readLine()) == null && categoryStr.length() == 0);
						try 
						{
							c = Integer.parseInt(categoryStr);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid choice, must be integer"); 
							continue;
						}
						if(c<1 || c>3) {
							System.out.println("Invalid choice.");
							continue;
						}

						String category;

						if(c==1) {
							category="Economy";
						}else if(c==2) {
							category="Comfort";
						}else {
							category="Luxury";
						}

						// Get category
						System.out.println("Enter how you want to query by city or state:");
						System.out.println("1. AND city or state");
						System.out.println("2. OR city or state");
						System.out.println("3. Do not use city or state in query");
						while((choice = in.readLine()) == null && choice.length() == 0);
						try 
						{
							c = Integer.parseInt(choice);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid choice, must be integer"); 
							continue;
						}
						if(c<1 || c>3) {
							System.out.println("Invalid choice.");
							continue;
						}

						int choiceNum = c;
						int cityOrStateNum = 0;
						String city = "";
						String state = "";

						if(choiceNum != 3) {
							// Get city or state
							System.out.println("Do you want to use city or state:");
							System.out.println("1. Use city");
							System.out.println("2. Use state");
							while((choice = in.readLine()) == null && choice.length() == 0);
							try 
							{
								c = Integer.parseInt(choice);
							}
							catch(Exception e)
							{
								System.out.println("Error: invalid choice, must be integer"); 
								continue;
							}
							if(c<1 || c>2) {
								System.out.println("Invalid choice.");
								continue;
							}

							cityOrStateNum = c;

							if(c==1) {
								System.out.println("Enter the city:");
								while((city = in.readLine()) == null && city.length() == 0);
							}else {
								System.out.println("Enter the state:");
								while((state = in.readLine()) == null && state.length() == 0);
							}
						}

						// Get model query
						System.out.println("Enter how you want to query by model:");
						System.out.println("1. AND model");
						System.out.println("2. OR model");
						System.out.println("3. Do not use model in query");
						while((choice = in.readLine()) == null && choice.length() == 0);
						try 
						{
							c = Integer.parseInt(choice);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid choice, must be integer"); 
							continue;
						}
						if(c<1 || c>3) {
							System.out.println("Invalid choice.");
							continue;
						}

						int modelNum = c;
						String model = "";

						if(c != 3) {
							System.out.println("Enter the model:");
							while((model = in.readLine()) == null && model.length() == 0);
						}

						// Get how they want the results ordered
						System.out.println("How do you want the results ordered:");
						System.out.println("1. By average numerical score of feedbacks.");
						System.out.println("2. By average numerical score of trusted user feedbacks.");
						while((choice = in.readLine()) == null && choice.length() == 0);
						try 
						{
							c = Integer.parseInt(choice);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid choice, must be integer"); 
							continue;
						}
						if(c<1 || c>2) {
							System.out.println("Invalid choice.");
							continue;
						}

						int orderingNum = c;


						Browsing browsing = new Browsing();

						ArrayList<String> list = browsing.browseUserCars(loginName, category, city, state, model, choiceNum, cityOrStateNum, modelNum, orderingNum, con.stmt);

						if(list.size() == 0) {
							System.out.println("No Uber Cars found from this query.");
							continue;
						}else {
							for(int i = 0; i < list.size(); i++) {
								System.out.println(i+1 + ". " + list.get(i));
							}
						}
					}else if (c==8){
						// Get the login of the UD
						System.out.println("Enter the login of the driver you would like to see feedback for:");
						String driverStr;
						while((driverStr = in.readLine()) == null && driverStr.length() == 0);

						Login login = new Login();
						if(!login.checkIfUserExists("UD", driverStr, con.stmt)) {
							System.out.println("Error: nonexistant driver login.");
							continue;
						}

						// Get the number of useful feedbacks to display
						System.out.println("Up to how many useful feedbacks would you like to display?");
						String numStr;
						int numberOfFeedbacks;
						while((numStr = in.readLine()) == null && numStr.length() == 0);
						try 
						{
							numberOfFeedbacks = Integer.parseInt(numStr);
						}
						catch(Exception e)
						{
							System.out.println("Error: invalid number of feedbacks, must be integer"); 
							continue;
						}

						// Get and display feedbacks
						Feedback feedback = new Feedback();
						ArrayList<String> list = feedback.getUsefulFeedback(driverStr, numberOfFeedbacks, con.stmt);

						if(list.size() == 0) {
							System.out.println("No useful feedback found for the driver.");
							continue;
						}

						for(int i = 0; i < list.size(); i++) {
							System.out.println(i+1 + ". " + list.get(i));
						}
					}else if (c==9){
						System.out.println("Available Statistics:");
						System.out.println("1. Most Popular UCs");
						System.out.println("2. Most Expensive UCs");
						System.out.println("3. Most Highly Rated UDs");
						System.out.println("Please Choose One:");

						String sChoose;
						while ((sChoose = in.readLine()) == null && sChoose.length() == 0);

						try {
							c = Integer.parseInt(sChoose);
						} catch (Exception e) {
							System.out.println("Error invalid choice.");
							continue;
						}
						if (c < 1 | c > 3) {
							System.out.println("Error invalid choice.");
							continue;
						}

						Statistics stat = new Statistics();

						if(c == 1)
						{
							System.out.println("How many cars do you want to show?");

							while ((sChoose = in.readLine()) == null && sChoose.length() == 0);
							int m = 0;
							try {
								m = Integer.parseInt(sChoose);
							} catch (Exception e) {
								System.out.println("Error invalid choice.");
								continue;
							}

							ArrayList<String> list = stat.mostPopularUCs(m, "Economy", con.stmt);
							if (list.size() > 0) {
								System.out.println("Top " + m + " in Economy");
								for (int i = 0; i < list.size(); i++) {
									System.out.println(i + 1 + ". " + list.get(i));
								}
							} else {
								System.out.println("No Stats to show for Economy category");
							}

							ArrayList<String> list2 = stat.mostPopularUCs(m, "Luxury", con.stmt);
							if (list2.size() > 0) {
								System.out.println("Top " + m + " in Luxury");
								for (int i = 0; i < list2.size(); i++) {
									System.out.println(i + 1 + ". " + list2.get(i));
								}
							} else {
								System.out.println("No Stats to show for Luxury Category");
							}

							ArrayList<String> list3 = stat.mostPopularUCs(m, "Comfort", con.stmt);
							if (list3.size() > 0) {
								System.out.println("Top " + m + " in Comfort");
								for (int i = 0; i < list3.size(); i++) {
									System.out.println(i + 1 + ". " + list3.get(i));
								}
							} else {
								System.out.println("No Stats to show Luxury Category");
							}


						}
						if(c == 2)
						{

							System.out.println("How many cars do you want to show?");

							while ((sChoose = in.readLine()) == null && sChoose.length() == 0);
							int m = 0;
							try {
								m = Integer.parseInt(sChoose);
							} catch (Exception e) {
								System.out.println("Error invalid choice.");
								continue;
							}

							ArrayList<String> list = stat.mostExpensiveUCs(m, "Economy", con.stmt);
							if (list.size() > 0) {
								System.out.println("Most Expensive " + m + " in Economy");
								for (int i = 0; i < list.size(); i++) {
									System.out.println(i + 1 + ". " + list.get(i));
								}
							} else {
								System.out.println("No Stats to show for Economy category");
							}

							ArrayList<String> list2 = stat.mostExpensiveUCs(m, "Luxury", con.stmt);
							if (list2.size() > 0) {
								System.out.println("Most Expensive " + m + " in Luxury");
								for (int i = 0; i < list2.size(); i++) {
									System.out.println(i + 1 + ". " + list2.get(i));
								}
							} else {
								System.out.println("No Stats to show for Luxury Category");
							}

							ArrayList<String> list3 = stat.mostExpensiveUCs(m, "Comfort", con.stmt);
							if (list3.size() > 0) {
								System.out.println("Most Expensive " + m + " in Comfort");
								for (int i = 0; i < list3.size(); i++) {
									System.out.println(i + 1 + ". " + list3.get(i));
								}
							} else {
								System.out.println("No Stats to show Luxury Category");
							}

						}
						if(c == 3)
						{
							System.out.println("How many drivers do you want to show?");

							while ((sChoose = in.readLine()) == null && sChoose.length() == 0);
							int m = 0;
							try {
								m = Integer.parseInt(sChoose);
							} catch (Exception e) {
								System.out.println("Error invalid choice.");
								continue;
							}

							ArrayList<String> list = stat.highlyRatedUDs(m, "Economy", con.stmt);
							if (list.size() > 0) {
								System.out.println(m + " Highest Scores in Economy");
								for (int i = 0; i < list.size(); i++) {
									System.out.println(i + 1 + ". " + list.get(i));
								}
							} else {
								System.out.println("No Stats to show for Economy category");
							}

							ArrayList<String> list2 = stat.highlyRatedUDs(m, "Luxury", con.stmt);
							if (list2.size() > 0) {
								System.out.println(m + " Highest Scores in Luxury");
								for (int i = 0; i < list2.size(); i++) {
									System.out.println(i + 1 + ". " + list2.get(i));
								}
							} else {
								System.out.println("No Stats to show for Luxury Category");
							}

							ArrayList<String> list3 = stat.highlyRatedUDs(m, "Comfort", con.stmt);
							if (list3.size() > 0) {
								System.out.println(m + " Highest Scores in Comfort");
								for (int i = 0; i < list3.size(); i++) {
									System.out.println(i + 1 + ". " + list3.get(i));
								}
							} else {
								System.out.println("No Stats to show for Comfort category");
							}

						}
					}else if (c==10){
						// Get the first login
						System.out.println("Enter the first login:");
						String firstLogStr;
						while ((firstLogStr = in.readLine()) == null && firstLogStr.length() == 0);

						// Get the second login
						System.out.println("Enter the second login:");
						String secLogStr;
						while ((secLogStr = in.readLine()) == null && secLogStr.length() == 0);

						// Check for one degree of separation
						Separation separation = new Separation();
						boolean result = separation.oneDegreeOfSeparation(firstLogStr, secLogStr, con.stmt);

						if(result) {
							System.out.println("Users are 1-degree away.");
							continue;
						}

						result = separation.twoDegreesOfSeparation(firstLogStr, secLogStr, con.stmt);
						if(result) {
							System.out.println("Users are 2-degrees away.");
							continue;
						}else {
							System.out.println("Users are not 1 or 2 degrees away.");
						}

					}else{   
						System.out.println("Exiting UUber System");
						con.stmt.close(); 
						break;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println ("Either connection error or query execution error!");
		}
		finally
		{
			if (con != null)
			{
				try
				{
					con.closeConnection();
					System.out.println ("Database connection terminated");
				}

				catch (Exception e) { /* ignore close errors */ }
			}	 
		}
	}
}

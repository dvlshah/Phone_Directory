package DSA_FINAL_PROJECT;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import DSA_FINAL_PROJECT.SubClasses.Contact.Contact;
import DSA_FINAL_PROJECT.SubClasses.Features;
import DSA_FINAL_PROJECT.SubClasses.Link;
import DSA_FINAL_PROJECT.SubClasses.Search;

public class PhoneBook{
	public static void main(String []args) throws IOException{

		int no_of_contact_details = 1000;
		Contact []obj = new Contact[no_of_contact_details];
		int i = -1;
		Features featuresObject = new Features();
		Search sObj =new Search();
		ArrayList contact_profile = null;

		for(;;){
			System.out.println("------Menu----- ");
			System.out.println("1).Add Contact");
			System.out.println("2).Search");
			System.out.println("3).Modify");
			System.out.println("4).Recents");
			System.out.println("5).Delete");
			System.out.println("6).Display Contacts");
			System.out.println("7).Exit");
			int option = new Scanner(System.in).nextInt();
		
			switch(option){
				case 1:
					try{
					i++;
					
					obj[i] = new Contact();
					obj[i].set_lastName();
					obj[i].set_phone_numbers();
					obj[i].set_address();
					obj[i].set_gender();	
					obj[i].set_email_Id();
					obj[i].set_group();
					obj[i].upperCase();
					
					featuresObject.recentContacts(obj[i]);
					featuresObject.insertHash(obj[i]);
					
					File fileDatabase = new File("database.bin");
					if(fileDatabase.exists()){
						contact_profile = (ArrayList)((new ObjectInputStream(new FileInputStream(fileDatabase))).readObject());
						fileDatabase.delete();
					}
					else {
						contact_profile = new ArrayList(0);
					}
					contact_profile.add(obj[i]);

					ObjectOutputStream database = new ObjectOutputStream(new FileOutputStream(fileDatabase));
					database.writeObject(contact_profile);
					database.close();
					featuresObject.sortByName();
					sObj.buffer_check();
				}
				catch(Exception e){
					System.out.println("Error in input/output:"+e);
				}		
				break;
				
				case 2:
					System.out.println("1.Search By Number");
					System.out.println("2.Search By Name");
					int ch = new Scanner(System.in).nextInt();
					switch(ch){
						case 1:
							System.out.println("Enter no u want to search");
							String s = new Scanner(System.in).nextLine();
							featuresObject.searchByNo(s);
							break;
						case 2:
							System.out.println("Search By First Name");
							String name_search = new Scanner(System.in).nextLine();
							sObj.searchByName(name_search);
							break;
					}
		   		break;

		   		case 3:
		   			featuresObject.modifyContactProfile();
		   		break;
		   		
		   		case 4:
		   			featuresObject.displayRecents();
		   		break;

		   		case 5:
					System.out.println("1).Delete a particular contact detail");
					System.out.println("2).Delete all contact details");
					int choice = new Scanner(System.in).nextInt();
					switch(choice){
		   			
		   			case 1:
		   				featuresObject.delete(1,0);
		   			break;

		   			case 2:
		   				featuresObject.delete(0,1);
		   				System.out.println("All records deleted successfully");
		   			break;

		   			default:
					System.out.println("Enter a valid option!");
		   			}
		   		break;


		   		case 6:
		   			featuresObject.display_bin();
		   		break;

				case 7:
				System.exit(0);

				default:
				System.out.println("Enter a valid option!");
			}
		}
	}
}

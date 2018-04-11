package DSA_FINAL_PROJECT.SubClasses;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import DSA_FINAL_PROJECT.SubClasses.Contact.Contact;

public class Features implements Serializable{

	Search sObj = new Search();
	Contact Contact1 = null;	
	Contact Contact2 = null;
	int recent_queue_size = 0;
	Contact[] hasharray = new Contact[1000];
	double a = 0.6180339887; 
    public int length;

    public void sortByName(){
		
		try{
			File f = new File("database.bin");
			ArrayList databaseObject = (ArrayList)(new ObjectInputStream(new FileInputStream(f)).readObject());
			Object temp = null;
			int no_of_iterations = 0;
			boolean flag = true;

			if(f.exists()){
				f.delete();
			}

			while(flag) {
				flag = false;
				for(int j = 0;j < databaseObject.size()-1;j++){
				Contact1 = (Contact)(databaseObject.get(j));
				Contact2 = (Contact)(databaseObject.get(j+1));

				if(Contact1.ffirstName().compareTo(Contact2.ffirstName()) > 0){
						temp  = databaseObject.get(j);
						databaseObject.set(j,databaseObject.get(j+1));
						databaseObject.set(j+1,temp);
						flag = true;
						no_of_iterations++; 	
					}
			  	}
			}
			File fileDatabase = new File("database.bin");
			ObjectOutputStream database = new ObjectOutputStream(new FileOutputStream(fileDatabase));
			database.writeObject(databaseObject);
			database.close();

		}
		catch(FileNotFoundException e){
				System.out.println("No database found!");
		}
		catch(NullPointerException e){
				System.out.println("No Contact records found!");
		}
		catch(Exception e){
				System.out.println("Error"+e);
		}
	}

/*
	Determines the index from the Contact no to get index of hasharray used
	for searching contact details by number.
*/
	int getHashIndex(String s){
		int index;
		int temp = s.hashCode();
		int temp1;
		if(temp < 0){
			temp1 = -temp;
		}else
		{
			temp1 = temp;
		}
		double temp2 = temp1 * a;
		double indtemp;
		/*
			1000 represents the size of the Hasharray
			The Function used to determine the index is the best function to occur minimal collision 
		*/
		indtemp = Math.floor(1000*(temp2 - Math.floor(temp2)));
		index = (int)indtemp;
		return index;
	}

//---------------------------------------------------------------------------------------------------------------
/*
	Method to store a Contact into a hash array
	Method first determine the index on which the Contact needs to be stored
	If the collision occurs, it stores the Contact to the next available index
	@param
		Contact s
*/
	public void insertHash(Contact s){
		try{
			File fileHash = new File("hash.bin");
			if(fileHash.exists()){
				ArrayList newobj = (ArrayList)(new ObjectInputStream(new FileInputStream(fileHash)).readObject());
				Contact []obj = (Contact[])newobj.get(0);
				hasharray = obj;
			}
			ArrayList Contact_profile = null;
			Link current = s.object_phone_number.start;
			Link next;

		while(current != null){
			int ind = getHashIndex(current.data);
			System.out.println(current.data);
			if(hasharray[ind] == null){
				hasharray[ind] = s;
			}
			else{
				while(hasharray[ind] != null){
					ind++;
				}
				hasharray[ind] = s;
			}
			current = current.next;
		}
		if(fileHash.exists())
		fileHash.delete();
				
		Contact_profile = new ArrayList(0);
		Contact_profile.add(hasharray);
		ObjectOutputStream database = new ObjectOutputStream(new FileOutputStream(fileHash));
		database.writeObject(Contact_profile);
		database.close();		
		}

		catch(Exception e){
			System.out.println("Error in insertHash"+e);
		}
	}

//--------------------------------------------------------------------------------------------------------------------------
/*
	Method to delete a Contact from Hasharray
	It searches for the Contact and when it found the Contact, it changes the values to a particuler value
	@param
		Contact s
*/
	void deleteHash(Contact s){
		try{
			File fileHash = new File("hash.bin");
			if(fileHash.exists()){
				ArrayList newobj = (ArrayList)(new ObjectInputStream(new FileInputStream(fileHash)).readObject());
				Contact []obj = (Contact [])newobj.get(0);
				hasharray = obj;
			}
		}
		catch(Exception e){
			System.out.println("Error in deleteHash"+e);
		}

		ArrayList Contact_profile = null;
			
		Link current = s.object_phone_number.start;
		Link next;

		int ind;
		System.out.println("Before delete");
		while(current != null){
			ind = getHashIndex(current.data);
			if(hasharray[ind].firstName.equals(s.firstName)){
				System.out.println("Inside delete!");
				hasharray[ind].firstName = "null";
			}
			else{
				while(hasharray[ind] != null){
					if(hasharray[ind].firstName.equals(s.firstName)){
						hasharray[ind].firstName = "null";
					}
					ind++;
				}
			}

			current = current.next;
		}
		
		try{
			File fileHash = new File("hash.bin");
			if(fileHash.exists())
				fileHash.delete();
			
			Contact_profile = new ArrayList(0);
			Contact_profile.add(hasharray);
			ObjectOutputStream database = new ObjectOutputStream(new FileOutputStream(fileHash));
			database.writeObject(Contact_profile);
			database.close();		

		}
		catch(Exception e){
			System.out.println("Error in deleteHash"+e);
		}
	}

//--------------------------------------------------------------------------------------------------------------------------------
/*
	Method to Modify a Contact which is already stored in the Hasharray

	@param
		Contact newContact
		Contact oldContact
*/
	public void modifyHash(Contact newContact, Contact oldContact){
		try{
			File fileHash = new File("hash.bin");
			if(fileHash.exists()){
				ArrayList newobj = (ArrayList)(new ObjectInputStream(new FileInputStream(fileHash)).readObject());
				Contact []obj = (Contact [])newobj.get(0);
				hasharray = obj;
			}
		}
		catch(Exception e){
			System.out.println("Error in modifyHash"+e);
		}
			
		ArrayList Contact_profile = null;
		Link current = oldContact.object_phone_number.start;
		Link current1 = newContact.object_phone_number.start;
		Link next;

		while(current != null){
			int ind = getHashIndex(current.data);
			if(current.data.equals(current1.data)){
				while(hasharray[ind] != null){
					if(hasharray[ind].firstName.equals(oldContact.firstName)){
						hasharray[ind] = newContact;
						break;
					}
					else{
						if(current.next == null){
							ind++;
						}
						else{
							break;
						}
					}
				}
			}
			else{
				System.out.println("Inside else of Modify");
				deleteHash(oldContact);
				insertHash(newContact);
			}
			current = current.next;
			current1 = current1.next;
		}

		try{
			File fileHash = new File("hash.bin");
			if(fileHash.exists())
				fileHash.delete();
		
			Contact_profile = new ArrayList(0);
			Contact_profile.add(hasharray);
			ObjectOutputStream database = new ObjectOutputStream(new FileOutputStream(fileHash));
			database.writeObject(Contact_profile);
			database.close();		

		}
		catch(Exception e){
			System.out.println("Error in modifyHash"+e);
		}
	}

//--------------------------------------------------------------------------------------------------------------------------
/*
	Takes a Contact no as a parameter and then searches for the Contact after getting the index
	@param
		String S 
*/
	public void searchByNo(String s){
		try{
			File f = new File("hash.bin");
			if(f.exists()){
				ArrayList newobj = (ArrayList)(new ObjectInputStream(new FileInputStream(f)).readObject());
				Contact []obj = (Contact [])newobj.get(0);
				hasharray = obj;
			}
		}
		catch(Exception e){
			System.out.println(e);
		}


		int ind = getHashIndex(s);
		if(hasharray[ind] == null){
			System.out.println("Number not found");
		}
		else{
			Link current;
			Link next;

			while(hasharray[ind] != null){
				current = hasharray[ind].object_phone_number.start;
				if(hasharray[ind].firstName.equals("null")){

				}
				else{
					while(current != null){
						if(current.data.equals(s)){
							try{
								hasharray[ind].get_firstName();
								hasharray[ind].get_lastName();
								hasharray[ind].get_phone_numbers();
								hasharray[ind].get_address();
								hasharray[ind].get_gender();
								hasharray[ind].get_email_Id();
								hasharray[ind].get_group();
								break;
							}
							catch(Exception e){
								System.out.println(e);
							}
						}
						else{
							current = current.next;
						}
					}
				}	
				ind++;
			}
			
		}	
	}
//---------------------------------------------------------------------------------------------------------------
	
	public void delete(int index,int userChoice){
		try{
			int deleteIndex = 0;
			int deleteStatus = 0;

			File f = new File("database.bin");
			ArrayList databaseObject = (ArrayList)(new ObjectInputStream(new FileInputStream(f)).readObject());			
			Search sObj = new Search();
			int []buffer_update = sObj.arr_buffer();
			if(userChoice==0){
				System.out.println("1).Delete By Name");
				System.out.println("2).Exit");
				System.out.println("Enter your option");
				int option = new Scanner(System.in).nextInt();
				switch(option){
				
				case 1:
				String delete_name = null;
				System.out.println("Enter the name of Contact you want to delete!:");
				delete_name = new Scanner(System.in).nextLine();
				delete_name = delete_name.toUpperCase(); 
				sObj.searchByName(delete_name);
				if(!sObj.noRecords){
					for(int i = 0;i<sObj.deleteMemory.size();i++){
						Contact delete_Contact = (Contact)(databaseObject.get((int)sObj.deleteMemory.get(i)));
							
							if(delete_name.equals(delete_Contact.ffirstName())){
								deleteIndex = (int)sObj.deleteMemory.get(i);
								databaseObject.remove(deleteIndex);
								buffer_update[delete_name.charAt(0)-65]--;
								databaseObject.trimToSize();
								deleteStatus = 1;
								deleteHash(delete_Contact);
								System.out.println("Contact deleted!");
								}
						else{
							System.out.println("Name not found!");
							deleteStatus = -1;
						}	
					}
				}

				FileWriter fobj = new FileWriter("buffer.dat",false);
				for(int i=0;i<buffer_update.length;i++){
					fobj.append((char)buffer_update[i]);
				}
				fobj.close();

				File fnew = new File("database.bin");
				ObjectOutputStream database = new ObjectOutputStream(new FileOutputStream(fnew));
				database.writeObject(databaseObject);
				database.close();	
				break;
				
				case 2:
				return;

				default:
				System.out.println("Enter a valid option!");

				}
			}
			else{
				databaseObject = null;
				if(databaseObject==null)
					System.out.println("All contacts deleted successfully");
			}
		}

		catch(Exception e){
			System.out.println("Error in delete:"+e);
		}	
	}

	public void modifyContactProfile(){
		
		try{
			int index_update = 0;
			Link next;
			File fileDatabase = new File("database.bin");
			ArrayList databaseObject = (ArrayList)(new ObjectInputStream(new FileInputStream(fileDatabase)).readObject());
			
			System.out.println("Enter the first name for updating the Contact information:");
			String name_search = new Scanner(System.in).nextLine();
			sObj.searchByName(name_search);
			if(!sObj.noRecords){
			System.out.println("Select index from above displayed Contacts");
			index_update = new Scanner(System.in).nextInt();
			Contact object = (Contact)databaseObject.get(index_update);
			Contact old = new Contact(object);	
			m:

			for(;;){
					System.out.println("Which Contact details you want to update?!");
					System.out.println("1).FirstName");
					System.out.println("2).LastName");
					System.out.println("3).Phone Number");
					System.out.println("4).Address");
					System.out.println("5).Email-Id");
					System.out.println("6).Gender");
					System.out.println("7).Group");
					System.out.println("8).Exit");
					int option = new Scanner(System.in).nextInt();
			
					switch(option){
						case 1:
							object.set_firstName();			
						break;

						case 2:
							object.set_lastName();
						break;

						case 3:
							Link current_number = object.object_phone_number.start;
							int number_count = 1;
							while(current_number.next!=null){
								current_number = current_number.next;
								number_count++;
							}
							current_number = object.object_phone_number.start;
							
							for(int i = 0;i<number_count;i++){
								System.out.println("Number "+(i+1)+" :" +current_number.data);
								current_number = current_number.next;
							}
							for(;;){
							current_number = object.object_phone_number.start;
							System.out.println("Which of the following numbers you want to update?");
							int no = new Scanner(System.in).nextInt();
							if(no>number_count||no<1){
								System.out.println("There are only "+ number_count +" numbers!");
							}
							else
							{
							if(no==1){
								String number_change = new Scanner(System.in).nextLine();
								current_number.data = number_change;
							}
							else {
								current_number = object.object_phone_number.start;
								for(int i=0;i<no-1;i++){
									current_number = current_number.next;
								}
								String number_change = new Scanner(System.in).nextLine();
								current_number.data = number_change;
							}
							}
							current_number = object.object_phone_number.start;
							for(int i = 0;i<number_count;i++){
								System.out.println("Number "+(i+1)+":"+current_number.data);
								current_number = current_number.next;
							}
							System.out.println("Do you want to update another record!");
							String choice = new Scanner(System.in).nextLine();
							if(choice.compareToIgnoreCase("yes")!=0)
								break;
							}
						break;

						case 4:
							Link current_address = object.object_address.start;
							
							int address_count = 1;
							while(current_address.next!=null){
								current_address = current_address.next;
								address_count++;
							}
							System.out.println("Address count 1 "+address_count);
							current_address = object.object_address.start;
							
							for(int i = 0;i<address_count;i++){
								System.out.println("Address "+(i+1)+":"+current_address.data);
								current_address = current_address.next;
							}
							for(;;){
							current_address = object.object_address.start;
							System.out.println("Which of the following address you want to update?");
							int no = new Scanner(System.in).nextInt();
							if(no>address_count||no<1){
								System.out.println("There are only"+ address_count +"address!");
							}
							else
							{
							if(no==1){
								String address_change = new Scanner(System.in).nextLine();
								current_address.data = address_change;
							}
							else {
								current_address = object.object_address.start;
								for(int i=0;i<no-1;i++){
									current_address = current_address.next;
								}
								String address_change = new Scanner(System.in).nextLine();
								current_address.data = address_change;
							}
							}
							current_address = object.object_address.start;
							for(int i = 0;i<address_count;i++){
								System.out.println("Address "+(i+1)+":"+current_address.data);
								current_address = current_address.next;
							}
							System.out.println("Address count 2 "+address_count);
							System.out.println("Do you want to update another record!");
							String choice = new Scanner(System.in).nextLine();
							if(choice.compareToIgnoreCase("yes")!=0)
								break;
							}
						break;

						case 5:
							object.set_email_Id();
						break;

						case 6:
							object.set_gender();
						break;

						case 7:
							object.set_group();
						break;

						case 8:

						break m;

						default:
							System.out.println("Enter a valid option!");
						break;
					}
				}
			object.upperCase();
			modifyHash(object,old);
			databaseObject.remove(index_update);
			databaseObject.add(index_update,object);

			ObjectOutputStream database = new ObjectOutputStream(new FileOutputStream(fileDatabase));
			database.writeObject(databaseObject);
			database.close();
		 	}
		}
		
		catch(Exception e){
			System.out.println("Error:"+e);
		}
	}	

	public void recentContacts(Contact obj){

		try{

		Link recent;
		File f = new File("recents.bin");

		if(f.exists()){
			recent = (Link)((new ObjectInputStream(new FileInputStream(f)).readObject()));
			Link current = recent.start;
			while(current.next!=null){
				current = current.next;
				recent_queue_size++;
			}
			f.delete();
		}
		else {
			recent = new Link();
			recent_queue_size = 0;
		}

		recent.enqueue(obj,recent_queue_size);
		recent_queue_size++;

		File fnew = new File("recents.bin");
		ObjectOutputStream database = new ObjectOutputStream(new FileOutputStream(fnew));
		database.writeObject(recent);
		database.close();
		}

		catch (Exception e){
			System.out.println("Error:"+e);
		}		
	}

	public void displayRecents(){
		try{
			
			File fileRecent = new File("recents.bin");
			Link recent = (Link)((new ObjectInputStream(new FileInputStream(fileRecent)).readObject()));
			
			if(recent.start.data_Contact==null) 
				System.out.println("No recent records found!");
			
			Link current = recent.start;
			while(current!=null){
		   		System.out.println("----------------");
		   		current.data_Contact.get_firstName();
				current.data_Contact.get_lastName();
				current.data_Contact.get_phone_numbers();
				current.data_Contact.get_address();
				current.data_Contact.get_gender();
				current.data_Contact.get_email_Id();
				current.data_Contact.get_group();
				System.out.println("----------------");
				current = current.next;
			}
		}
		catch(NullPointerException e){
			System.out.println("No recent records found!");
		}
		catch(Exception e){
			System.out.println("Error:"+e);
		}
	}

	public void display_bin(){
		
		sortByName();
		Contact tempContact = null;

		try{
			File f = new File("database.bin");
			ArrayList databaseObject = (ArrayList)(new ObjectInputStream(new FileInputStream(f)).readObject());
			if(databaseObject.size()>0){
				for(int k = 0;k<databaseObject.size();k++){
					tempContact = (Contact)(databaseObject.get(k));
					System.out.println("Index :"+k);
					System.out.println("----------------");
					tempContact.get_firstName();
					tempContact.get_lastName();
					tempContact.get_phone_numbers();
					tempContact.get_address();
					tempContact.get_gender();
					tempContact.get_email_Id();
					tempContact.get_group();
					System.out.println("----------------");
				}
			}
			else 
				System.out.println("No Contacts records found!");
		}
		catch(FileNotFoundException e){
			System.out.println("No database found!");
		}
		catch(NullPointerException e){
			System.out.println("No Contacts records found!");
		}
		catch(Exception e){
			System.out.println("Error in displaying binary file:"+e);
		}
	}	
}
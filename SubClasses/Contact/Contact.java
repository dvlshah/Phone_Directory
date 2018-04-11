package DSA_FINAL_PROJECT.SubClasses.Contact;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import  DSA_FINAL_PROJECT.SubClasses.*;

public class Contact implements Serializable,Cloneable{

	public String firstName;
	public String lastName;
	public String numbers;
	public String email_Id;
	public String gender;
	public String address;
	public boolean friends;
	public boolean work;
	public boolean other;
	public boolean family;
	public String group;
	static public  int access_index;
	public static Pattern emailNamePtrn = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	//"
	public static Pattern phonePtrn = Pattern.compile("[*#+[0-9]]{1,}");

	public Link object_phone_number = new Link();
	public Link object_address = new Link();


	public Contact()throws IOException{
		System.out.print("First Name:");
		firstName = new Scanner(System.in).nextLine();
		lastName = null;
		email_Id = null;
		gender = null;
	}

	public Contact(Contact obj){
		try{
			this.firstName = obj.firstName;
			this.lastName = obj.lastName;
			this.numbers = obj.numbers;
			this.email_Id = obj.email_Id;
			this.gender = obj.gender;
			this.address = obj.address;
			this.friends = obj.friends;
			this.work = obj.work;
			this.other = obj.other;
			this.family = obj.family;
			this.access_index = obj.access_index;
			this.object_phone_number = (Link) obj.object_phone_number.clone();
			this.object_address = (Link) obj.object_address.clone();
		}	
		catch(Exception e){
			System.out.println("Error in Contact"+e);
		}
	}

	public void set_firstName()throws IOException{
		try{
			System.out.print("First Name: ");
			firstName = new Scanner(System.in).nextLine();
		}
		catch(Exception e){
			System.out.println("Error:"+e);
		}	

	}

	public void get_firstName(){
		
		if(firstName!=null){
			System.out.println("Firstname: "+firstName);
		}
	}

	public String ffirstName(){
		return firstName;
	}

	public String flastName(){
		return lastName;
	}

	public void set_lastName()throws IOException{
		try{
			System.out.print("Last Name: ");
			lastName = new Scanner(System.in).nextLine();
		}
		catch(Exception e){
			System.out.println("Error:"+e);
		}	

	}

	public void get_lastName(){
		
		if(lastName!=null){
			System.out.println("Lastname: "+lastName);
		}
	}


	public boolean ValidatePhoneNumber(String number)
	{
		
		Matcher mtch1 = phonePtrn.matcher(number);
		if(mtch1.matches())
		{
			return true;
		}
		return false;
	
	}

	int check_validity_number(String number){
	if(ValidatePhoneNumber(number))
			return 1;
		else
			return 0;
	}

	public void set_phone_numbers()throws IOException{
		String choice; 
		int number_count = 1;

		try{
			while(true){
			System.out.print("Number "+number_count+":");
			numbers = new Scanner(System.in).nextLine();
			
			if(check_validity_number(numbers)==1){
				object_phone_number.insertAtLast(numbers);
				number_count++;
				}
			else
				System.out.println("Phone Number Invalid");
				
				System.out.println("Do you want to enter another number??YES or NO");
				choice = new Scanner(System.in).nextLine();
			
			if(choice.compareToIgnoreCase("yes")!=0)
				break;
			}

		}
		catch(Exception e){
			System.out.println("Error:"+e);
		}
	}

	public void get_phone_numbers(){

		Link current = object_phone_number.start;
		Link next;
		int number_count = 1;
		while(current!=null){
			System.out.println("Number "+number_count+":"+current.data);
			current = current.next;
			number_count++;
		}
		
	}

	public void set_address()throws IOException{
		
		String choice;
		int address_count = 1;

		try{
			while(true){
			
			System.out.println("Address "+address_count+":");
			address = new Scanner(System.in).nextLine();
			object_address.insertAtLast(address.toUpperCase());

			System.out.println("Do you want to enter another address??YES or NO");
			choice = new Scanner(System.in).nextLine();
			address_count++;

			if(choice.compareToIgnoreCase("yes")!=0)
				break;

			}
		}	
		catch(Exception e){
			System.out.println("Error:"+e);
		}
	}
	
	public void get_address(){		
		
		Link current = object_address.start;
		Link next;
		int address_count = 1;

		while(current!=null){
			System.out.println("Address "+address_count+" :"+current.data);
			current = current.next;
			address_count++;
		}
	}

	public boolean validateEmailAddress(String userName)
	{
		
		Matcher mtch = emailNamePtrn.matcher(userName);
		if(mtch.matches())
		{
			return true;
		}
		return false;
	
	}

	int check_validity_emailID(String email_Id){
	if(validateEmailAddress(email_Id))
			return 1;
		else
			return 0;
	}

	public void set_email_Id()throws IOException{
		try{
			System.out.print("Email_Id: ");
			email_Id = new Scanner(System.in).nextLine();	
			if(check_validity_emailID(email_Id)!=1){
				System.out.println("Invalid Email-Id");
				email_Id=String.valueOf((char)13);
			}	
		}
		catch(Exception e){
			System.out.println("Error:"+e);
		}	
	}	

	public void get_email_Id(){
		if(email_Id!=null){
			System.out.println("Email-Id: "+email_Id);
		}
	}

	public void set_gender()throws IOException{
		try{
			System.out.println("Gender:");
			gender = new Scanner(System.in).nextLine();
		}
		catch(Exception e){
			System.out.println("Error"+e);
		}
	}

	public void get_gender()throws IOException{
		if(gender!=null){
			System.out.println("Gender: "+gender);
		}
	}


	public void set_group() throws IOException{
		try{
			System.out.println("Group: ");
			System.out.println("1).Family");
			System.out.println("2).Friends");
			System.out.println("3).Work");
			System.out.println("4).Other");
			int choice = new Scanner(System.in).nextInt();
				
			if(choice == 1){
				family = true;
				group = "Family";
			}
			if(choice == 2){
				friends = true;
				group = "Friends";	
			}		
			if(choice == 3){
				work = true;
				group = "Work";
			}
			if(choice == 4){
				other = true;
				group = "Other";
			}
			else {
				other = false;
				family = false;
				friends = false;
				work = false;
			}
		}
		catch(Exception e){
			System.out.println("Error:"+e);
		}
	}

	public void get_group(){
		if(group!=null){
			System.out.println("Group: "+group);
		}
	}

	public void upperCase(){

		firstName = firstName.toUpperCase();
		lastName = lastName.toUpperCase();
		email_Id = email_Id.toLowerCase();
		gender = gender.toUpperCase();
		group = group.toUpperCase();
	}

	@Override
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}

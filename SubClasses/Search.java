package DSA_FINAL_PROJECT.SubClasses;
import DSA_FINAL_PROJECT.SubClasses.Contact.Contact;
import java.io.*;
import java.util.*;

public class Search{
	
	int []temp_buffer;
	public boolean noRecords;
	ArrayList deleteMemory = new ArrayList(); 

	public void display_buffer(){
		try{
		File fdat = new File("buffer.dat");
			if(fdat.exists())
			{
				FileReader robj = new FileReader(fdat);

				int val;
				int value=0;
				int m = 0;
			
				while((val = robj.read()) != -1) {
					value = val;
				}

				robj.close();
			}
		}
		catch(FileNotFoundException e){
			System.out.println("Buffer.dat file missing!Reinitialize the program..Terminating");
		}
		catch(Exception e){
			System.out.println("Error in display Buffer:"+e);
		}	
	}
	
	public int offset_buffer(char a){
		
		try{
		FileReader robj = new FileReader("buffer.dat");

			int val;
			int value=0;
			temp_buffer = new int[26];
			int m = 0;

			while((val = robj.read()) != -1) {
				value = val;
				temp_buffer[m] = value;
				m++;
			}
			robj.close();

			return temp_buffer[a-65];
		}
		catch(FileNotFoundException e){
			System.out.println("Buffer.dat file missing!Reinitialize the program..Terminating");
		}
		catch(Exception e){
			System.out.println("Error in offset_buffer:"+e);
		}
		return temp_buffer[a-65];	
	}

	public int []arr_buffer(){
		
		try{
		FileReader robj = new FileReader("buffer.dat");

			int val;
			int value=0;
			temp_buffer = new int[26];
			int m = 0;

			while((val = robj.read()) != -1) {
				value = val;
				temp_buffer[m] = value;
				m++;
			}
			robj.close();

			return temp_buffer;
		}
		catch(FileNotFoundException e){
			System.out.println("Buffer.dat file missing!Reinitialize the program..Terminating");
		}
		catch(Exception e){
			System.out.println("Error in array buffer:"+e);
		}
		return temp_buffer;	
	}

	public void buffer_check(){
		try{
			int []buffer = new int[26];
				
			File f = new File("database.bin");
			ArrayList databaseObject = (ArrayList)(new ObjectInputStream(new FileInputStream(f)).readObject());
			int buffer_int = 65;

			for(int k = 0;k<databaseObject.size();k++){
				
				Contact c = (Contact)(databaseObject.get(k));
				for(buffer_int = 65;buffer_int<91;buffer_int++){
					if((int)c.ffirstName().charAt(0)==buffer_int){
						buffer[buffer_int-65] = buffer[buffer_int-65] + 1;
						break;
					}
				}
			}

			FileWriter fobj = new FileWriter("buffer.dat",false);

			for(int i=0;i<buffer.length;i++){
				fobj.append((char)buffer[i]);
			}

			fobj.close();
			System.out.println();
		}
		catch(Exception e){
			System.out.println("Error in buffer check:"+e);
		}
	}

	public void searchByName(String name_search){

		try{
			File fileDatabase = new File("database.bin");
			ArrayList databaseObject = (ArrayList)(new ObjectInputStream(new FileInputStream(fileDatabase)).readObject());
			Contact cSearch = null;
			name_search = name_search.toUpperCase();

			int search_count = 0;
			int temp = 0;
			int availablity = 0;
			int index = 0;
			int offset = offset_buffer(name_search.charAt(0));
			int []buffer = new int[26];
			buffer = arr_buffer();
			int sum = 0;

			for(int i=0;i<name_search.charAt(0)-65;i++){
				sum = buffer[i] + sum;
			}
			offset = sum;
			display_buffer();	

			for(int k = offset;k<offset + buffer[name_search.charAt(0)-65];k++){
				cSearch = (Contact)(databaseObject.get(k));
				if(name_search.length()>cSearch.ffirstName().length())
						temp = cSearch.ffirstName().length();
				else
						temp = name_search.length();
				
					for(int i = 0;i<temp;i++){
						if(name_search.charAt(i)==cSearch.ffirstName().charAt(i)){
							search_count++;
					}
				}
				index = k;	

			if(search_count==name_search.length()){
				System.out.println("\n"+"--------------");
				System.out.println("Index:"+index);
				System.out.println("--------------");
				cSearch.get_firstName();
				cSearch.get_lastName();
				cSearch.get_phone_numbers();
				cSearch.get_address();
				cSearch.get_gender();
				cSearch.get_email_Id();
				cSearch.get_group();
				System.out.println("--------------"+"\n");
				search_count = 0;
				noRecords = false;
				deleteMemory.add(index);
			}
			else{
				search_count = 0;
				availablity++;
				noRecords = true;
			}
				
		if(availablity==databaseObject.size()){
			System.out.println("Name not found in PhoneBook!");	
			noRecords = true;
			}
	    }
	}	  	
	catch(Exception e){
			System.out.println("Error in searchByName:"+e);
	}

	}

	public String smaller_name(String str1,String str2){
		if(str1.length()>str2.length())
			return str2;
		if(str1.length()<str2.length())
			return str1;
		if(str1.length() == str2.length())
			return str1;
		else
			return null;
	}
}
package DSA_FINAL_PROJECT.SubClasses;
import java.util.Scanner;
import java.io.*;
import DSA_FINAL_PROJECT.SubClasses.Contact.Contact;

public class Link implements Serializable,Cloneable {										
	public String data;	
	public int data2;
	Contact data_Contact;						
	public Link start;							
	public  Link end;
	public Link next;
    static int listCount = 0;					//listCount is static which is incremented 
    static int queueSize = 0;
    											//whenever a new link is created
	public Link(){								//Default constructor 
		
	}
	
	public Link(Contact d){							//Parameterized constructor 
		
		data_Contact = d;
	}

	public Link(String d){							//Parameterized constructor 
		
		data = d;
		listCount++;
	}

	public Link(String name,int d){							//Parameterized constructor 
		
		data2 = d;
		data = name;
		listCount++;
	}

	public void displayLink(){					//Method displayLink to display data of that Link

		System.out.println(data);
	}

	public boolean isEmpty(){					//Method isEmpty to check whether link is empty or not	 

		return (start == null);
	}

	public void enqueue(Contact object,int max_size){

		Link recent = new Link(object);
		Link current = start;

		if(max_size<2){
			if(start!=null){
				while(current.next!=null){
					current = current.next;
				}	
			current.next = recent;
			}
			else
				start = recent;
		}
		else{
			
			Link temp = start;
			start = temp.next;

			if(start!=null){
				while(current.next!=null){
					current = current.next;
				}	
			current.next = recent;
			}
			else
				start = recent;
		}
	}

	public void insertAtLast(String d){			//Method insertAtLast
		
		Link newLink = new Link(d);
		
		if(start==null){						//If there is no link then as there is
			start = newLink;					//no link first and last link are same
			end = newLink;}
		else{
		end.next = newLink;						//Else it will move ahead till last link
		end = newLink;							//is observed and then new link is created there
		}
	}

	public void insertAtLast(String name,int d){	//Method insertAtLast
		
		Link newLink = new Link(name,d);
		
		if(start==null){						//If there is no link then as there is
			start = newLink;					//no link first and last link are same
			end = newLink;}
		else{
		end.next = newLink;						//Else it will move ahead till last link
		end = newLink;							//is observed and then new link is created there
		}
	}

	public void deleteAtPosition(int position){	//Method to delete link at any particular position
	
		Link current = start;
		
		if(position>0 && position<listCount){	//Condition if link we want to delete is first
		if(position == 1){
			start = current.next;
		}
 
		if(position>1 && position<listCount){	//Condition if element we want to delete is present in this 
												//range.
			for(int i=0;i<position - 2;i++){
					current = current.next;
			}
		current.next = current.next.next;		//As soon as we encounter the element we want to delete
		}										//we break that link and connect previous of that link
		if(position == listCount){				//to next of the link we deleted
			while(current.next.next!=null){
			current = current.next;
			}
		current.next = null;					//If the link we want to delete is last then we simply 
												//assign it null.
		}
		listCount--;							//Length of list is decremented by 1
	}	
	else	
			System.out.println("Invalid Position!");
	}
	public void display(){
		if(start==null)  {                                      
			System.out.println("No Info Entered");	
			return;
			}						//Method display to display data at each link
		Link current = start;
		while(current!=null){
			System.out.println(current.data);
			current = current.next;
		}

		
	}
	@Override
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}	
package client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import server.WebInterface;

public class DemoTest{
	
	static WebInterface ServerInterface;

   public static void main(String args[])  {
	    	   
		Runnable task1 = () -> {
			try {
				run("COMPS1111",args,"COMP6111","COMP6999");
			} 
			catch (Exception e) {
				
				System.out.println("Exception occured"+e);
			}
		};
		Runnable task2 = () -> {
			try {
				run("COMPS1113",args,"COMP6111","COMP6999");
			} 
			catch (Exception e) {
				
				System.out.println("Exception occured"+e);
			}
		};
		Runnable task3 = () -> {
			try {
				run("COMPS1114",args,"COMP6111","COMP6999");
			} 
			catch (Exception e) {
				
				System.out.println("Exception occured"+e);
			}
		};
		Thread t1 = new Thread(task1);
		Thread t2 = new Thread(task2);
		Thread t3 = new Thread(task3);
		
		t1.start();
		t2.start();
		t3.start();	   
		
	   
   }
   
     
   public static void run(String inputvalue,String args[],String oldcourseID, String newcourseID) {
	   checkserver(inputvalue,args);
	   
	   String enrolresult=ServerInterface.enrolCourse(inputvalue, oldcourseID, "FALL");
	   System.out.println("enrolresult for "+inputvalue+" is :"+enrolresult);
	   String swapresult=ServerInterface.swapCourse(inputvalue, oldcourseID, newcourseID);
	   System.out.println("swapresult for "+inputvalue+" is :"+swapresult);
	   
	   
   }
	public static void checkserver(String inputvalue,String []args) {
	   String servervalue=inputvalue.substring(0,4).trim(); 
	   	        
	      try {
	    	  
	     	if (servervalue.equals("COMP")){

	    		URL compURL = new URL("http://localhost:8080/CompServer?wsdl");
	    		QName compQName = new QName("http://server/", "ImplementationRmiService");
	    		Service compService = Service.create(compURL, compQName);	
	    		ServerInterface = compService.getPort(WebInterface.class);
	     	}
	     	
	        else if(servervalue.equals("SOEN")){
   
	    		URL soenURL = new URL("http://localhost:8080/SoenServer?wsdl");
	    		QName soenQName = new QName("http://server/", "ImplementationRmiService");
	    		Service soenService = Service.create(soenURL, soenQName);
	    		ServerInterface = soenService.getPort(WebInterface.class);	
	        }
	        else if(servervalue.equals("INSE")){	
	        	
	    		URL inseURL = new URL("http://localhost:8080/InseServer?wsdl");
	    		QName inseQName = new QName("http://server/", "ImplementationRmiService");
	    		Service inseService = Service.create(inseURL, inseQName);
	    		ServerInterface = inseService.getPort(WebInterface.class);	        	
	        }	        
	     	
	        else
	        {
	        	System.out.println("Please enter the correct client id");
	    		
	        }
	     	
	      }
			catch (Exception e) {  
				System.out.println("Exception occured"+e);
			}

}
}
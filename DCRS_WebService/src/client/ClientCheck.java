package client;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import server.WebInterface;

public class ClientCheck {
	
   static String[] studentid  ={"COMPS1111","COMPS1112","COMPS1113","COMPS1114","COMPS1115","SOENS1111","SOENS1112","SOENS1113","SOENS1114","SOENS1115","INSES1111","INSES1112","INSES1113","INSES1114","INSES1115"};
   
   String[] advisorid  ={"COMPA1111","COMPA1112","COMPA1113","COMPA1114","COMPA1115",
           				"SOENA1111","SOENA1112","SOENA1113","SOENA1114","SOENA1115",
           				"INSEA1111","INSEA1112","INSEA1113","INSEA1114","INSEA1115"};
   
   String[] sems ={"FALL","WINTER","SUMMER"};
   
   String registryURL;
   int RMIPort;
   //static server.InterfaceRmi inter;
   Scanner scan=new Scanner(System.in);
   String courseid;
   String semester;
   HashMap<String, Integer> hash = new HashMap<String,Integer>();
   boolean flags=false;
   boolean flaga=false;
   boolean flagsem=false;
   
   Logger logger = Logger.getLogger(ClientCheck.class.getName());
   static private FileHandler fh;
   static private SimpleFormatter sf;
   static WebInterface ServerInterface;
    
   
   public ClientCheck(){	   
   }
   
   public void inputValue(String []args) {
	   
	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the id:");
	String input =sc.nextLine().toUpperCase().trim();	
	if (input.length()==9){
	    try{

	        // This block configure the logger with handler and formatter  
	    	String path="C:\\Users\\iknoor\\workspace\\DCRS_1\\clientlogs\\"+input+".log";
	    	
	        fh= new FileHandler(path);
	        sf = new SimpleFormatter();
	            fh.setFormatter(sf);
	            logger.addHandler(fh);
	 
	          

	        // the following statement is used to log any messages  
	        //logger.info("My first log");  

	    } catch (SecurityException e) {  
	    	System.out.println(e);

	    } catch (IOException e) {  
	    	System.out.println(e); 
	    }	
	    logger.info("Id entered is :"+input);    
	    checkserver(input,args);
    }
	else{
		System.out.println("Please enter the correct id");
		logger.info("Please enter the correct id");
		inputValue(args);
	}
	
   }
   
   
   public void rmiAdvisor(String idvalue,String []args){
	   
	   System.out.println("Please enter the operation the advisor performs:");
	   System.out.println("1. for adding course");
	   System.out.println("2. for removing course");
	   System.out.println("3. for listing course availability");
	   System.out.println("4. for operating on behalf of student");
	   String oper="NO";
	   String choice =scan.next().trim();

	   try{
	   switch(choice){
	   
	   case "1":
		   logger.info("1. for adding course");
		   System.out.println("Please enter the course id to add:");
		   courseid=scan.next().toUpperCase().trim();
		   int courselength=courseid.length();

		   System.out.println("Please enter the semester:");
		   semester=scan.next().toUpperCase().trim();
		   
		   System.out.println("Please enter the seat capacity:");
		   String capacity=scan.next().toUpperCase().trim();
		   for (String s :sems){
			   if (s.equals(semester)){
				   flagsem=true;
				   break;
			   }
			   
		   }
		   
		   if (flagsem){
			   
		   
		   String advisordepart=idvalue.substring(0,4).toUpperCase().trim(); 
		   String coursedepart=courseid.substring(0,4).toUpperCase().trim();
		   if (advisordepart.equals(coursedepart)){
			   boolean addresult=ServerInterface.addCourse(courseid,semester,capacity);
			   System.out.println("Output for advisor add result "+addresult);
			   logger.info("Output for advisor add result "+addresult);
		   }
		   else{
			System.out.println("Please enter the course that belongs to the department of advisor " +idvalue); 
			logger.info("Please enter the course that belongs to the department of advisor " +idvalue);
		   }
		   
		   
		   }
		   else
		   {   
			   System.out.println("Please enter the semesters as FALL,WINTER,SUMMER only");
			   logger.info("Please enter the semesters as FALL,WINTER,SUMMER only");
			   
		   }
		   System.out.println("Do you wish to perform anymore operations for this user? YES OR NO");
		   logger.info("Do you wish to perform anymore operations for this user? YES OR NO");
		   oper=scan.next().toUpperCase().trim();
		   if (oper.equals("YES")){
			   logger.info("YES");
			   rmiAdvisor(idvalue,args);
		   }
		   logger.info(oper);
		   break;
		   
		   
	   case "2":
		   logger.info("2. for removing course");
		   System.out.println("Please enter the course id to remove:");
		   courseid=scan.next().toUpperCase().trim();
		   System.out.println("Please enter the semester:");
		   semester=scan.next().toUpperCase().trim();
		   for (String s :sems){
			   if (s.equals(semester)){
				   flagsem=true;
				   break;
			   }
			   
		   }
		   
		   if (flagsem){
		   String advisordeprt =idvalue.substring(0,4).toUpperCase().trim(); 
		   String coursedeprt  =courseid.substring(0,4).toUpperCase().trim();
		   if (advisordeprt.equals(coursedeprt)){
			   boolean removeresult =ServerInterface.removeCourse(courseid,semester); 
			   
			   System.out.println("Output for advisor remove result "+removeresult);
			   logger.info("Output for advisor remove result "+removeresult);
		   }
		   else{
			   
			
			System.out.println("Please enter the course that belongs to the department of advisor " +idvalue); 
			logger.info("Please enter the course that belongs to the department of advisor " +idvalue);
		   }
		   }
		   else
		   {   
			   System.out.println("Please enter the semesters as FALL,WINTER,SUMMER only"); 
			   logger.info("Please enter the semesters as FALL,WINTER,SUMMER only");
		   }
		   
		   System.out.println("Do you wish to perform anymore operations for this user? YES OR NO");
		   logger.info("Do you wish to perform anymore operations for this user? YES OR NO");
		   oper=scan.next().toUpperCase().trim();
		   if (oper.equals("YES")){
			   logger.info("YES");
			   rmiAdvisor(idvalue,args);
		   }
		   logger.info(oper);
		   break;
		   
		   
	   case "3":
		   logger.info("3. for listing course availability");
		   System.out.println("Please enter the semester:");
		   semester=scan.next().toUpperCase().trim();
		   for (String s :sems){
			   if (s.equals(semester)){
				   flagsem=true;
				   break;
			   }
			   
		   }
		   
		   if (flagsem){		   
		   String advisdepart=idvalue.substring(0,4).toUpperCase().trim();
		   if (advisdepart.equals("COMP")){
			   semester+='C';
			   String result=ServerInterface.listCourseAvailability(semester.toUpperCase().trim());
			   
			   System.out.println("Courses available: "+result);
			   logger.info("Courses available: "+result);
		   }
		   else if(advisdepart.equals("SOEN")){
			   semester+='S';
			   String result=ServerInterface.listCourseAvailability(semester.toUpperCase().trim());
			   
			   System.out.println("Courses available: "+result); 
			   logger.info("Courses available: "+result);
		   }
		   else if (advisdepart.equals("INSE")){
			   semester+='I';
			   String result=ServerInterface.listCourseAvailability(semester.toUpperCase().trim());
			   
			   System.out.println("Courses available: "+result); 
			   logger.info("Courses available: "+result);
		   }
		   }
		   else
		   {   
			   System.out.println("Please enter the semesters as FALL,WINTER,SUMMER only");
			   logger.info("Please enter the semesters as FALL,WINTER,SUMMER only");
		   }
		   System.out.println("Do you wish to perform anymore operations for this user? YES OR NO");
		   logger.info("Do you wish to perform anymore operations for this user? YES OR NO");
		   oper=scan.next().toUpperCase().trim();
		   if (oper.equals("YES")){
			   logger.info("YES");
			   rmiAdvisor(idvalue,args);
		   }
		   logger.info(oper);
		   break;
		   
		   
	   case "4":
		   logger.info("4. for operating on behalf of student");
		   while(true){
		   System.out.println("Please enter the student id:");
		   idvalue=scan.next().toUpperCase().trim();
		   
		   if (checkAS(idvalue).equals("S")){
		   checkClient(idvalue,args);
		   System.out.println("Do you wish to perform anymore operations for this user? YES OR NO");
		   logger.info("Do you wish to perform anymore operations for this user? YES OR NO");
		   oper=scan.next().toUpperCase().trim();
		   if (oper.equals("YES")){
			   logger.info("YES");
			   rmiAdvisor(idvalue,args);
		   }
		   logger.info(oper);
		   break;
		   }
		   else{
			   
			   System.out.println("Incorrect student id:"); 
			   logger.info("Incorrect student id:");
			   continue;
		   }
		   }
	       break;
   	   default:
   		    
	       System.out.println("Please enter the correct option");
	       logger.info("Please enter the correct option");
	       rmiAdvisor(idvalue,args);
	   }   
	   }
       catch (Exception e) {
    	   	  
	          System.out.println("Exception in rmiadvisor: " + e);
	          logger.info("Exception in rmiadvisor: "+e);
	          inputValue(args);
	       } 
	   
   }
   
   public void rmiStudent(String idvalue,String []args)   {

	   System.out.println("Please enter the operation the student performs:");
	   System.out.println("1. for enrolling in the course");
	   System.out.println("2. for dropping the course");
	   System.out.println("3. for getting the course schedule");
	   System.out.println("4. for swapping course");
	   
	   String choice =scan.next().trim();
	   String oper="NO";
      
	   try{
	   switch(choice){
	   
	   case "1":
		   logger.info("1. for enrolling in the course");
		   System.out.println("Please enter the course id to add:");
		   courseid=scan.next().toUpperCase().trim();
		   System.out.println("Please enter the semester:");
		   semester=scan.next().toUpperCase().trim();
		   		   
		   for (String s :sems){
			   if (s.equals(semester.toUpperCase().trim())){
				   flagsem=true;
				   break;
			   }
			   
		   }
		   
		   if (flagsem){
		   String result=ServerInterface.enrolCourse(idvalue,courseid,semester);
		   
		   System.out.println("Enrol Course Result: "+result);
		   logger.info("Enrol Course Result: "+result);
		   }
	   
		   else
		   {
			      
		   System.out.println("Please enter the semesters as FALL,WINTER,SUMMER only");
		   logger.info("Please enter the semesters as FALL,WINTER,SUMMER only");
		   }
		   
		   System.out.println("Do you wish to perform anymore operations for this user? YES OR NO");
		   logger.info("Do you wish to perform anymore operations for this user? YES OR NO");
		   oper=scan.next().toUpperCase().trim();
		   if (oper.equals("YES")){
			   logger.info("YES");
			   rmiStudent(idvalue,args);
		   }
		   logger.info(oper);
		   break;
		   
		   
	   case "2":
		   logger.info("2. for dropping the course");
		   System.out.println("Please enter the course id to drop:");
		   courseid=scan.next().toUpperCase().trim();
		   boolean resultdrop=ServerInterface.dropCourse(idvalue,courseid);
		   
		   System.out.println("Drop Course Result: "+resultdrop);
		   logger.info("Drop Course Result: "+resultdrop);
		   System.out.println("Do you wish to perform anymore operations for this user? YES OR NO");
		   logger.info("Do you wish to perform anymore operations for this user? YES OR NO");
		   oper=scan.next().toUpperCase().trim();
		   if (oper.equals("YES")){
			   logger.info("YES");
			   rmiStudent(idvalue,args);
		   }
		   logger.info(oper);
		   break;
		   
		   
	   case "3":
		   logger.info("3. for getting the course schedule");
		   String resultschedule=ServerInterface.getClassSchedule(idvalue);
		   
		   System.out.println("Students Class Schedule: "+resultschedule);
		   logger.info("Students Class Schedule: "+resultschedule);
		   System.out.println("Do you wish to perform anymore operations for this user? YES OR NO");
		   logger.info("Do you wish to perform anymore operations for this user? YES OR NO");
		   oper=scan.next().toUpperCase().trim();
		   if (oper.equals("YES")){
			   logger.info("YES");
			   rmiStudent(idvalue,args);
		   }
		   logger.info(oper);
		   break;
		   
	   case "4":
		   
		   String courseidold=null;
		   String courseidnew=null;
		   logger.info("4. for swapping course");
		   System.out.println("Please enter the old course id:");		   		   
		   courseidold=scan.next().toUpperCase().trim();	
		   System.out.println("Please enter the new course id:");		   		   
		   courseidnew=scan.next().toUpperCase().trim();		   
		   
		   String resultswap=ServerInterface.swapCourse(idvalue,courseidold,courseidnew);
		   System.out.println("Swap Result :"+resultswap);
		   System.out.println("Do you wish to perform anymore operations for this user? YES OR NO");
		   logger.info("Do you wish to perform anymore operations for this user? YES OR NO");
		   oper=scan.next().toUpperCase().trim();
		   if (oper.equals("YES")){
			   logger.info("YES");
			   rmiStudent(idvalue,args);
		   }
		   logger.info(oper);
		   break;
	   default:
		   System.out.println("Please enter the correct option");
		   logger.info("Please enter the correct option");
		   rmiStudent(idvalue,args);
	   }
	   }
       catch (Exception e) {
	          System.out.println("Exception in rmistudent: " + e);
	          logger.info("Exception in rmistudent :" +e);
	          inputValue(args);
	       } 
	      
	   
   }
   
   
   public String checkAS(String inputvalue){
	   String letter;
	   return letter=inputvalue.substring(4,5).trim(); 
   }
   
   public void checkClient(String inputvalue,String []args) {
 	   
	String letter;
    
	letter=checkAS(inputvalue);
	
	if (letter.equals("A")){
		
		
		System.out.println("Checking if the advisor id is valid...");
		logger.info("Checking if the advisor id is valid...");
		
		for(int i=0;i<advisorid.length;i++){
			
			if (advisorid[i].equalsIgnoreCase(inputvalue)){
				flaga=true;
				break;
			}
		}
		
		    if(flaga) {
				System.out.println("valid advisor");
				logger.info("valid advisor");
				rmiAdvisor(inputvalue,args);
		    }
			else{
				System.out.println("invalid advisor");
				logger.info("invalid advisor");
				System.out.println("Please enter the correct id");
				logger.info("Please enter the correct id");
				inputValue(args);
			}
		}
		
    
	else if(letter.equals("S")){
		System.out.println("Checking if the student id is valid...");
		logger.info("Checking if the student id is valid..");
		
		for(int i=0;i<studentid.length;i++){
			
			if (studentid[i].equalsIgnoreCase(inputvalue)){
				flags=true;
				break;
			}
		}
		
		    if(flags) {
		    	
				System.out.println("valid student");
				logger.info("valid student");
				rmiStudent(inputvalue,args);
		    }
			else{
				
				System.out.println("invalid student");
				logger.info("invalid student");
				
				System.out.println("Please enter the correct id");
				logger.info("Please enter the correct id");
				inputValue(args);
			}
		}
   }
   
   public void checkserver(String inputvalue,String []args) {
	   String servervalue=null;	   
	   servervalue=inputvalue.substring(0,4).trim(); 
	   logger.info("Server is :"+servervalue); 	   
	        
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
	        	logger.info("Please enter the correct client id");
	    		inputValue(args);
	    		
	        }
	         
	     	  
	          checkClient(inputvalue,args);

	          
	       } // end try 
	       catch (Exception e) {
	          System.out.println("Exception while checking client server: " + e);
	          logger.info("Exception while checking client server: " + e);
	          inputValue(args);
	       } 
	    
   }
   
   
   public static void main(String args[]){
	   ClientCheck c = new ClientCheck(); 
	   c.inputValue(args);
      
	      
	   
 //end main
}//end class
}
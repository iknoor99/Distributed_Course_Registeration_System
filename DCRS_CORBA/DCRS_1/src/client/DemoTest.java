package client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;


import DCRS_1Corba.InterfaceCorbaHelper;

import DCRS_1Corba.InterfaceCorba;


public class DemoTest{
	
   static InterfaceCorba intercorba;

   public static void main(String args[]) throws RemoteException,SecurityException,IOException, InputMismatchException, InvalidName {
	    	   
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
   
     
   public static void run(String inputvalue,String args[],String oldcourseID, String newcourseID) throws MalformedURLException, RemoteException, SecurityException, InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NotBoundException, IOException {
	   checkserver(inputvalue,args);
	   
	   String enrolresult=intercorba.enrolCourse(inputvalue, oldcourseID, "FALL");
	   System.out.println("enrolresult for "+inputvalue+" is :"+enrolresult);
	   String swapresult=intercorba.swapCourse(inputvalue, oldcourseID, newcourseID);
	   System.out.println("swapresult for "+inputvalue+" is :"+swapresult);
	   
	   
   }
	public static void checkserver(String inputvalue,String []args) throws InputMismatchException, SecurityException, IOException, InvalidName{
	   String servervalue=inputvalue.substring(0,4).trim(); 
	   
		ORB orb = ORB.init(args, null);
		//System.out.println("orb" + orb);
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		//System.out.println("objRef" + objRef);
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		//System.out.println("ncRef" + ncRef);
	        
	      try {
	    	  
	     	if (servervalue.equals("COMP")){

	     		intercorba=(InterfaceCorba) InterfaceCorbaHelper.narrow(ncRef.resolve_str("ServerComp"));	
	     	}
	     	
	        else if(servervalue.equals("SOEN")){
   
	        	intercorba=(InterfaceCorba) InterfaceCorbaHelper.narrow(ncRef.resolve_str("ServerSoen"));
	        }
	        else if(servervalue.equals("INSE")){	
	        	intercorba=(InterfaceCorba) InterfaceCorbaHelper.narrow(ncRef.resolve_str("ServerInse"));
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
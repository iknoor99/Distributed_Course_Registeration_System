package server;

import java.rmi.*;
import java.rmi.server.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;

import DCRS_1Corba.InterfaceCorba;
import DCRS_1Corba.InterfaceCorbaHelper;

/**
 * This class represents the object server for a distributed
 * ServerComp.
 * @author Iknoor 
 */

public class ServerInse  {
	static HashMap<String, Integer> hash;
	static HashMap<String, HashMap<String, Integer>> hashDataInse=new HashMap<String, HashMap<String, Integer>>();
	static HashMap<String, Integer> hashDataInseinner =new HashMap<String, Integer>();	
	
	static HashMap<String, HashMap<String, Integer>> hashDatasem=new HashMap<String, HashMap<String, Integer>>();
	static HashMap<String, Integer>hashDataseminner =new HashMap<String, Integer>();
	static Logger logger = Logger.getLogger(ServerInse.class.getName());
	static private FileHandler fh;
	static private SimpleFormatter sf;
		
   public static void main(String args[]) {
         
   }

public static void startInseserver()  throws RemoteException{
	// TODO Auto-generated method stub
    try{

        // This block configure the logger with handler and formatter  
    	String path="C:\\Users\\iknoor\\workspace\\DCRS_1\\clientlogs\\ServerInse.log";
    	
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
    try{
    
    	
		Runnable task = () -> {

			receive();

		};

		Thread thread = new Thread(task);		
		thread.start();
		 
   	    //runServers.startRegistry(portNum);         
        //ImplementationRmi implmtobj = new ImplementationRmi();
        //String registryURL = "rmi://localhost:" + portNum + "/forServerinse";
        //Naming.rebind(registryURL, implmtobj);
        System.out.println("ServerInse Server ready.");
        logger.info("ServerInse Server ready.");
        inputValues("FALL");
        inputValues("WINTER");
        inputValues("SUMMER");
        
        
    	List<HashMap<String,HashMap<String,Integer>>> d = Arrays.asList(hashDataInse);
    	System.out.println(hashDataInse);
    	logger.info(hashDataInse.toString());
    	
     }// end try
     catch (Exception re) {
        System.out.println("Exception in ServerInse: " + re);
        logger.info("Exception in ServerInse: " + re);
     } // end catch
	
}

public static String listCourseAvailability(String semester){
	   
	   System.out.println("Inse listCourseAvailability function");
	   logger.info("Inse listCourseAvailability function");
	   String desc="list,"+semester;
	   String resultComp=sendMessage(7777,desc);
	   String resultSoen=sendMessage(8888,desc);
	   
	   String resultInse = hashDataInse.get(semester).toString();
	   String resultcourses="Data for semester " +semester+" is COMPSERVER"+" "+resultComp.trim()+" SOENSERVER "+" "+resultSoen.trim()+" INSESERVER "+" "+resultInse.trim();
	   	
	   
	   return resultcourses;

}

public static String drop(String studentID, String courseID,String semester){
	   System.out.println("Inside Inseserver drop function");
	   logger.info("Inside Inseserver drop function");
	   String course=courseID.substring(0,4).toUpperCase();
	   String studid=studentID.substring(0,4).toUpperCase();
	   String checkresult=null;
	   if (course.equals(studid)){
		   System.out.println("Same Inse serever drop"); 
		   logger.info("Same Inse serever drop");
		   synchronized(hashDataInse) {
			  hashDataInseinner=hashDataInse.get(semester); 
			  int capacity=hashDataInseinner.get(courseID);
			  capacity=capacity+1;
			  hashDataInseinner.put(courseID, capacity);
			  hashDataInse.put(semester,hashDataInseinner);
			  				  
			  List<HashMap<String, HashMap<String, Integer>>> d = Arrays.asList(ServerComp.hashDataComp);
			  System.out.println("Inse server hash map after droping:- "+d);	
			  logger.info("Inse server hash map after droping:- "+d);
			  return "success";
		   }//synchronization
	   }
	   
	   else{
	   
	   String desc="drop"+","+studentID+","+courseID+","+semester;
	   
	   
		if (course.equals("COMP")){
			   checkresult=sendMessage(7777,desc);  
		   }
		   else if(course.equals("SOEN")){
			   checkresult=sendMessage(8888,desc);
		   }
		
		   checkresult=checkresult.trim();
		   if (checkresult.equals("pass")){
			return "success";  
		   }
		return "fail";
	   }
}

public static String enrol(String studentID, String courseID, String semester){
	   
	   System.out.println("Inside Inseserver Enrol function");
	   logger.info("Inside Inseserver Enrol function");
	   String desc="enrol"+","+studentID+","+courseID+","+semester;
	   String course=courseID.substring(0,4).toUpperCase().trim();
	   String studid=studentID.substring(0,4).toUpperCase().trim();
	   String checkresult=null;
	   if (course.equals(studid)){
		   synchronized(hashDataInse) {
		if (hashDataInse.containsKey(semester)){
			//System.out.println(semester);
			hashDataseminner=hashDataInse.get(semester);
			//System.out.println(hashDataseminner);
		}
		
		
		if (hashDataseminner.containsKey(courseID)){
			//System.out.println(courseID);
			int capacity=hashDataseminner.get(courseID);
			if (capacity>0){
				//System.out.println(capacity);
				String result=ImplementationRmi.enrolcrosserver(studentID,courseID,semester);
				if (result.equals("success")){
					capacity=capacity-1;
					hashDataseminner.put(courseID,capacity );
					hashDataInse.put(semester, hashDataseminner);
					
					List<HashMap<String,HashMap<String,Integer>>> display = Arrays.asList(hashDataInse);
					System.out.println("Inse hash map after enroll "+display);							
					logger.info("Inse hash map after enroll "+display);
					return "success";
				}
			}
	
		}	   
		   }//synchronized
	   }
	   
   else{
	   
   
	if (course.equals("COMP")){
		   checkresult=sendMessage(7777,desc);  
	   }
	   else if(course.equals("SOEN")){
		   checkresult=sendMessage(8888,desc);
	   }
	   
	   checkresult=checkresult.trim();
	   if (checkresult.equals("pass")){
		return "success";  
	   }
	return "fail";
}
	return "fail";
}

private static String sendMessage(int serverPort,String desc) {

	DatagramSocket aSocket = null;

	try {

		aSocket = new DatagramSocket();
		
		String mess="Please send me the hash map for your server for :" +desc;
		
		byte[] message = mess.getBytes();

		InetAddress aHost = InetAddress.getByName("localhost");

		DatagramPacket request = new DatagramPacket(message, message.length, aHost, serverPort);

		aSocket.send(request);

		System.out.println("Request message sent from the client to server with port number " + serverPort + " is: "

				+ new String(request.getData()));
		logger.info("Request message sent from the client to server with port number " + serverPort + " is: "

				+ new String(request.getData()));

		byte[] buffer = new byte[1000];

		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);



		aSocket.receive(reply);

		//System.out.println("Reply received from the server with port number " + serverPort + " is: "

			//	+ new String(reply.getData()));
		
		String checkresult = new String(reply.getData());
		return checkresult;		

	} catch (SocketException e) {

		System.out.println("Socket: " + e.getMessage());

	} catch (IOException e) {

		e.printStackTrace();

		System.out.println("IO: " + e.getMessage());

	} finally {

		if (aSocket != null)

			aSocket.close();

	}
	return null;

}

private static String enrolserver(String studentid,String courseid,String semester){
	
	System.out.println("Inside enrolserver");
	logger.info("Inside enrolserver");
	String retresult="notpass";
	synchronized(hashDataInse) {
	if (hashDataInse.containsKey(semester)){
		//System.out.println(semester);
		hashDataseminner=hashDataInse.get(semester);
		//System.out.println(hashDataseminner);
	}
	
	
	if (hashDataseminner.containsKey(courseid)){
		//System.out.println(courseid);
		int capacity=hashDataseminner.get(courseid);
		if (capacity>0){
			//System.out.println(capacity);
			String result=ImplementationRmi.enrolcrosserver(studentid,courseid,semester);
			if (result.equals("success")){
				capacity=capacity-1;
				hashDataseminner.put(courseid,capacity );
				hashDataInse.put(semester, hashDataseminner);
				
				List<HashMap<String,HashMap<String,Integer>>> display = Arrays.asList(hashDataInse);
				System.out.println("Inse hash map after enrol "+display);							
				logger.info("Inse hash map after enrol "+display);
				retresult="pass";
			}
		}
	}
	}//synchronized
	return retresult;
}


private static String dropserver(String studentid,String courseid,String semester){
	   String retresult="notpass";
	   logger.info("Inside dropserver");
	   synchronized(hashDataInse) {
		if (hashDataInse.containsKey(semester)){
			//System.out.println(semester);
			hashDataseminner=hashDataInse.get(semester);
			//System.out.println(hashDataseminner);
		}
		
		if (hashDataseminner.containsKey(courseid)){
			//System.out.println(courseid);
			int capacity=hashDataseminner.get(courseid);
			capacity+=1;
			hashDataseminner.put(courseid,capacity );
			hashDataInse.put(semester, hashDataseminner);
			
			List<HashMap<String,HashMap<String,Integer>>> display = Arrays.asList(hashDataInse);
			System.out.println("Comp hash map after drop "+display);							
			logger.info("Comp hash map after drop "+display);
			retresult="pass";						
		}
	   }//synchronized
		return retresult;
		
	   
}

private static void receive() {

	DatagramSocket aSocket = null;

	try {

		aSocket = new DatagramSocket(9999);
        
		System.out.println("Server 9999 Started............");
		logger.info("Server 9999 Started............");
		while (true) {
			
			byte[] buffer = new byte[1000];

			DatagramPacket request = new DatagramPacket(buffer, buffer.length);

			aSocket.receive(request);
			String eld=null;
			
			//Please send me the hash map for your server for :enrol,COMPS1111,SOEN6111,Fall
			
			String stringdata=new String (request.getData());
			
			//System.out.println(stringdata);
			
			String func=stringdata.split(":")[1].split(",")[0];
			func=func.trim();
			
			if (func.equals("enrol")){
				
				String []param=stringdata.split(":")[1].split(",",4);
				
				//System.out.println(param.length);
				
				String studentid=param[1];
				studentid=studentid.trim();
				
				 
			    String courseid =param[2];
			    courseid=courseid.trim();
			   
			    
				String semester =param[3];
				semester=semester.trim();
				
				eld=enrolserver(studentid,courseid,semester);

		    
			}
			
			else if (func.equals("list")){
				String semester=stringdata.split(":")[1].split(",")[1];
				semester=semester.trim();
				hashDataseminner=hashDataInse.get(semester);
				
				List<HashMap<String, Integer>> d = Arrays.asList(hashDataseminner);
				eld=hashDataseminner.toString();
				
			}
			
			else if(func.equals("drop")){
				
				
				String []param=stringdata.split(":")[1].split(",",4);
				
				//System.out.println(param.length);
				
				String studentid=param[1];
				studentid=studentid.trim();
				
				 
			    String courseid =param[2];
			    courseid=courseid.trim();
			   
			    
				String semester =param[3];
				semester=semester.trim();
				
				eld=dropserver(studentid,courseid,semester);
				
				
			}
		
			String rep=eld.trim();
			buffer = rep.getBytes();

			DatagramPacket reply = new DatagramPacket(buffer, buffer.length, request.getAddress(),

					request.getPort());

			aSocket.send(reply);

		}	

	} catch (SocketException e) {

		System.out.println("Socket: " + e.getMessage());

	} catch (IOException e) {

		System.out.println("IO: " + e.getMessage());

	} finally {

		if (aSocket != null)

			aSocket.close();

	}

}

public static void inputValues(String term) {
	
	
	if(term == "FALL") {
		hash = new HashMap<String, Integer>();
		hash.put("INSE6111", 10);
		hash.put("INSE6112", 10);
		hash.put("INSE6113", 10);
		hash.put("INSE6114", 10);
//		hash.put("INSE6211", 10);
		
		hashDataInse.put(term, hash);
	}
	
	if(term == "SUMMER") {
		hash = new HashMap<String, Integer>();
		hash.put("INSE6121", 10);
		hash.put("INSE6122", 10);
		hash.put("INSE6123", 10);
		hash.put("INSE6124", 10);
		
		hashDataInse.put(term, hash);
	}
	
	if(term == "WINTER") {
		hash = new HashMap<String, Integer>();
		hash.put("INSE6211", 10);
		hash.put("INSE6311", 10);
		hash.put("INSE6411", 10);
		hash.put("INSE6123", 10);
//		hash.put("INSE6124", 10);
																															
		hashDataInse.put(term, hash);
		
	}

	
}
} 

package server;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


import javax.xml.ws.Endpoint;

import server.ImplementationRmi;

/**
 * This class represents the object server for a distributed
 * ServerComp.
 * @author Iknoor 
 */

public class ServerComp  {
	

   static HashMap<String, Integer> hash;   
   static HashMap<String, HashMap<String, Integer>> hashDataComp=new HashMap<String, HashMap<String, Integer>>();
   static HashMap<String, Integer> hashDataCompinner=new HashMap<String, Integer>();
   
   static HashMap<String, HashMap<String, Integer>> hashDatasem=new HashMap<String, HashMap<String, Integer>>();
   static HashMap<String, Integer>hashDataseminner =new HashMap<String, Integer>();
   static Logger logger = Logger.getLogger(ServerComp.class.getName());
   static private FileHandler fh;
   static private SimpleFormatter sf;
   public static ImplementationRmi implementationRmiweb;

	
   public static void main(String args[]) {
         
   }

   public static void inputValues(String term) {
	
	   
		if(term == "FALL") {
			hash = new HashMap<String, Integer>();
			hash.put("COMP6111", 10);
			hash.put("COMP6112", 10);
			hash.put("COMP6113", 10);
			hash.put("COMP6114", 10);
//			hash.put("COMP6211", 10);
			
			hashDataComp.put(term, hash);
			
		}
		if(term == "SUMMER") {
			hash = new HashMap<String, Integer>();
			hash.put("COMP6121", 10);
			hash.put("COMP6122", 10);
			hash.put("COMP6123", 10);
			hash.put("COMP6124", 10);
			
			hashDataComp.put(term, hash);
		}
		
		if(term == "WINTER") {
			hash = new HashMap<String, Integer>();
			hash.put("COMP6211", 10);
			hash.put("COMP6311", 10);
			hash.put("COMP6411", 10);
			hash.put("COMP6123", 10);
//			hash.put("COMP6124", 10);
//			hash.put("COMP6621", 10);
			
			hashDataComp.put(term, hash);
		}

	}
   
   
   
   public static String listCourseAvailability(String semester){
	   
	   System.out.println("Inside Compserver listCourseAvailability function");
	   logger.info("Inside Compserver listCourseAvailability function");
	   String desc="list,"+semester;
	   String resultSoen=sendMessage(8888,desc);
	   String resultInse=sendMessage(9999,desc);
	   
	   //System.out.println("inse" +resultInse);
	  
	   String resultComp = hashDataComp.get(semester).toString();
	   String resultcourses="Data for semester :" +semester+" is COMPSERVER"+" "+resultComp.trim()+" SOENSERVER  "+" "+resultSoen.trim()+"INSESERVER "+" "+resultInse.trim();
	   
	   //System.out.println(resultcourses);
	return resultcourses;
	    		   
   }
   
   
   public static String drop(String studentID, String courseID,String semester){
	   System.out.println("Inside Compserver drop function");
	   logger.info("Inside Compserver drop function");
	   String course=courseID.substring(0,4).toUpperCase();
	   String studid=studentID.substring(0,4).toUpperCase();
	   String checkresult=null;
	   if (course.equals(studid)){
		   System.out.println("Same Compserver drop"); 
		   	  synchronized(hashDataComp) {
			  hashDataCompinner=hashDataComp.get(semester); 
			  int capacity=hashDataCompinner.get(courseID);
			  capacity=capacity+1;
			  hashDataCompinner.put(courseID, capacity);
			  hashDataComp.put(semester,hashDataCompinner);
			  				  
			  List<HashMap<String, HashMap<String, Integer>>> d = Arrays.asList(hashDataComp);
			  System.out.println("COMP server hash map after drop :- "+d);	
			  logger.info("COMP server hash map after drop :- "+d);
			  return "success";
	   }//synchornization
	   }
	   else{
		   
	   
	   String desc="drop"+","+studentID+","+courseID+","+semester;
	   
		if (course.equals("SOEN")){
			   checkresult=sendMessage(8888,desc);  
		   }
		   else if(course.equals("INSE")){
			   checkresult=sendMessage(9999,desc);
		   }
		
		   checkresult=checkresult.trim();
		   if (checkresult.equals("pass")){
			return "success";  
		   }
		return "fail";	
	   }
   
   }
   
   public static String enrol(String studentID, String courseID, String semester){
	   
	   
	   System.out.println("Inside Compserver Enrol function");
	   logger.info("Inside Compserver Enrol function");
	   String desc="enrol"+","+studentID+","+courseID+","+semester;
	   String course=courseID.substring(0,4).toUpperCase().trim();
	   String studid=studentID.substring(0,4).toUpperCase().trim();
	   String checkresult=null;
	   System.out.println("1");
	   if (course.equals(studid)){
		   System.out.println("2");
		synchronized(hashDataComp) {   
		if (hashDataComp.containsKey(semester)){
			System.out.println(semester);
			hashDataseminner=hashDataComp.get(semester);
			System.out.println(hashDataseminner);
		}
		
		
		if (hashDataseminner.containsKey(courseID)){
			System.out.println("course check:"+courseID);
			int capacity=hashDataseminner.get(courseID);
			if (capacity>0){
				System.out.println(capacity);
				String result=ImplementationRmi.enrolcrosserver(studentID,courseID,semester);
				if (result.equals("success")){
					capacity=capacity-1;
					hashDataseminner.put(courseID,capacity );
					hashDataComp.put(semester, hashDataseminner);
					
					List<HashMap<String,HashMap<String,Integer>>> display = Arrays.asList(hashDataComp);
					System.out.println("Comp hash map after enroll "+display);							
					logger.info("Comp hash map after enroll "+display);
					
					return "success";
				}//success
			}//capacity
		
		}//courseid	   
		}//synchronization
	   }//course & studid
	   
	   
   else{
	   
   
	if (course.equals("SOEN")){
		   checkresult=sendMessage(8888,desc);  
	   }
	   else if(course.equals("INSE")){
		   checkresult=sendMessage(9999,desc);
	   }
	   
	   checkresult=checkresult.trim();
	   if (checkresult.equals("pass")){
		return "success";  
	   }
	return "fail";
   
   }
	return "fail";   
   
   }
   
   public static String sendMessage(int serverPort,String desc) {

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

				//	 );
			

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
		
	   logger.info("Inside enrolserver");
	   String retresult="notpass";
	   synchronized(hashDataComp) {
		if (hashDataComp.containsKey(semester)){
			//System.out.println(semester);
			hashDataseminner=hashDataComp.get(semester);
			//System.out.println(hashDataseminner);
		}
		
		
		if (hashDataseminner.containsKey(courseid)){
			//System.out.println(courseid);
			int capacity=hashDataseminner.get(courseid);
			if (capacity>0){
				//System.out.println(capacity);
				String result=ImplementationRmi.enrolcrosserver(studentid,courseid,semester);
				if (result.equals("success")){
					capacity-=1;
					hashDataseminner.put(courseid,capacity );
					hashDataComp.put(semester, hashDataseminner);
					
					List<HashMap<String,HashMap<String,Integer>>> display = Arrays.asList(hashDataComp);
					System.out.println("Comp hash map after enroll "+display);	
					logger.info("Comp hash map after enroll "+display);
					
					retresult="pass";
				}
			}
		}
	   }//synchronization
		return retresult;
	}
   
   private static String dropserver(String studentid,String courseid,String semester){
	   String retresult="notpass";
	    System.out.println("Inside dropserver");
	    logger.info("Inside dropserver");
	    synchronized(hashDataComp) {
		if (hashDataComp.containsKey(semester)){
			//System.out.println(semester);
			hashDataseminner=hashDataComp.get(semester);
			//System.out.println(hashDataseminner);
		}
		
		if (hashDataseminner.containsKey(courseid)){
			//System.out.println(courseid);
			int capacity=hashDataseminner.get(courseid);
			capacity+=1;
			hashDataseminner.put(courseid,capacity );
			hashDataComp.put(semester, hashDataseminner);
			
			List<HashMap<String,HashMap<String,Integer>>> display = Arrays.asList(hashDataComp);
			System.out.println("Comp hash map after drop "+display);							
			logger.info("Comp hash map after drop "+display);
			retresult="pass";						
		}
	    }//synchronization
		return retresult;
		
	   
   }
   

	private static void receive() {
		
		DatagramSocket aSocket = null;

		try {

			aSocket = new DatagramSocket(7777);

			System.out.println("Server 7777 Started............");
			logger.info("Server 7777 Started............");
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
					hashDataseminner=hashDataComp.get(semester);
					
					List<HashMap<String, Integer>> d = Arrays.asList(hashDataseminner);
					eld=hashDataseminner.toString();
					
					
				}
				
				else if(func.equals("drop")){
					
				
					String []param=stringdata.split(":")[1].split(",",4);
					
					System.out.println(param.length);
					
					String studentid=param[1];
					studentid=studentid.trim();
					//System.out.println(studentid);
					 
				    String courseid =param[2];
				    courseid=courseid.trim();
				    //System.out.println(courseid);
				    
					String semester =param[3];
					semester=semester.trim();
					//System.out.println(semester);
					
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
   
public static void startCompserver()  {
	// TODO Auto-generated method stub
    try{

        // This block configure the logger with handler and formatter  
    	String path="C:\\Users\\iknoor\\workspace\\DCRS_1\\clientlogs\\ServerComp.log";
    	
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
	
        System.out.println("ServerComp Server ready.");
        logger.info("ServerComp Server ready.");
		implementationRmiweb = new ImplementationRmi();
		Endpoint endpoint = Endpoint.publish("http://localhost:8080/CompServer", implementationRmiweb);       
        ServerComp.inputValues("FALL");
        ServerComp.inputValues("WINTER");
        ServerComp.inputValues("SUMMER");

		List<HashMap<String,HashMap<String,Integer>>> d = Arrays.asList(hashDataComp);
		System.out.println(hashDataComp);
		logger.info(hashDataComp.toString());        
        
     }// end try
     catch (Exception re) {
        System.out.println("Exception in ServerComp: " + re);
        logger.info("Exception in ServerComp: " + re);
     } // end catch
	
}

} 

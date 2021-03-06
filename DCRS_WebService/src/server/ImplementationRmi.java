package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;



@WebService(endpointInterface = "server.WebInterface")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class ImplementationRmi implements WebInterface
{

    
	
	public ImplementationRmi() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/*
	HashMap<String, HashMap<String, Integer>> hashDataComp=new HashMap<String, HashMap<String, Integer>>();
	HashMap<String, Integer> hashDataCompinner=new HashMap<String, Integer>();
	HashMap<String, HashMap<String, Integer>> hashDataSoen=new HashMap<String, HashMap<String, Integer>>();
	HashMap<String, Integer> hashDataSoeninner=new HashMap<String, Integer>();
	HashMap<String, HashMap<String, Integer>> hashDataInse=new HashMap<String, HashMap<String, Integer>>();
	HashMap<String, Integer> hashDataInseinner =new HashMap<String, Integer>();
	*/
	
	static HashMap<String, HashMap<String, HashSet<String>>> studenthash=new HashMap<String, HashMap<String, HashSet<String>>>();
	static HashMap<String, HashSet<String>> studenthashinner = new HashMap<String, HashSet<String>>();
	
	//HashMap<String, ArrayList> studenthashinner = new HashMap<String, ArrayList>();
	
	static HashSet<String> coursestud    = new HashSet();
	static HashSet<String> coursestudF   = new HashSet();
	static HashSet<String> coursestudW   = new HashSet();
	static HashSet<String> coursestudS   = new HashSet();
	static HashSet<String> coursenewstud=  new HashSet();
	HashSet<String> fallschedulearray  =studenthashinner.get("FALL");
	HashSet<String> winterschedulearray=studenthashinner.get("WINTER");
	HashSet<String> Summerschedulearray=studenthashinner.get("SUMMER");
	
	

	@Override
	public boolean addCourse(String courseID, String semester,String capacity) {
		
		
		String department=courseID.substring(0,4).toUpperCase();
		//int capacity = 10;
		Integer intcapacity=Integer.parseInt(capacity);
		if (department.equals("COMP")){
				ServerComp.hashDataCompinner=ServerComp.hashDataComp.get(semester);
				if (ServerComp.hashDataCompinner.containsKey(courseID)){
					System.out.println("Course :"+ courseID+ "already exits in this term");
					return false;
				}
				else{
					ServerComp.hashDataCompinner.put(courseID,intcapacity);
				return true;
				}
			
		}
		else if (department.equals("SOEN")){
			ServerSoen.hashDataSoeninner=ServerSoen.hashDataSoen.get(semester);
			if (ServerSoen.hashDataSoeninner.containsKey(courseID)){
				System.out.println("Course :"+ courseID+ "already exits in this term");
				return false;
			}
			else{
				ServerSoen.hashDataSoeninner.put(courseID,intcapacity);
			return true;
			}			
			
		}
		else if (department.equals("INSE")){
			ServerInse.hashDataInseinner=ServerInse.hashDataInse.get(semester);
			if (ServerInse.hashDataInseinner.containsKey(courseID)){
				System.out.println("Course :"+ courseID+ "already exits in this term");
				return false;
			}
			else{
				ServerInse.hashDataInseinner.put(courseID,intcapacity);
			return true;
			}			
		}
		
		return false;
		
		
	}
	@Override
	public synchronized boolean removeCourse(String courseID, String semester) {
	
		String department=courseID.substring(0,4).toUpperCase();
		boolean removeflag=false;
		if (department.equals("COMP")){
			ServerComp.hashDataCompinner=ServerComp.hashDataComp.get(semester);
			if (ServerComp.hashDataCompinner.containsKey(courseID)){
				
				ServerComp.hashDataCompinner.remove(courseID);
				ServerComp.hashDataComp.put(semester, ServerComp.hashDataCompinner);	
				removeflag=true;
				}
			 			
			else{
				
				System.out.println("Course :"+ courseID+ "doesnt exits in this term");
				return false;
			}
		
	}

	else if (department.equals("SOEN")){
		ServerSoen.hashDataSoeninner=ServerSoen.hashDataSoen.get(semester);
		if (ServerSoen.hashDataSoeninner.containsKey(courseID)){
			
			ServerSoen.hashDataSoeninner.remove(courseID);
			ServerSoen.hashDataSoen.put(semester,ServerSoen.hashDataSoeninner);
			removeflag=true;    
	        }
			
		
		else{
			
			System.out.println("Course :"+ courseID+ "doesnt exits in this term");
			return false;
		}		
		
	}
	
	else if (department.equals("INSE")){
		ServerInse.hashDataInseinner=ServerInse.hashDataInse.get(semester);
		if (ServerInse.hashDataInseinner.containsKey(courseID)){
			
			ServerInse.hashDataInseinner.remove(courseID);
			ServerInse.hashDataInse.put(semester,ServerInse.hashDataInseinner);
			removeflag=true;			
		}
	        
		else{
			
			System.out.println("Course :"+ courseID+ "doesnt exits in this term");
			return false;
		}			
	}

	if (removeflag==true){
		
		for ( String studid:studenthash.keySet()){
			
			HashMap<String, HashSet<String>> studenthashinner1 = studenthash.get(studid);
			HashSet<String> courseset = studenthashinner1.get(semester);
			for(String coursestud:courseset){
				if (coursestud.equals(courseID)){
					courseset.remove(courseID);
					studenthashinner1.put(semester, courseset);
					studenthash.put(studid, studenthashinner1);
					System.out.println(studenthash);
					return true;
				}
			}
		}
		return true;
	}
	return false;
		
	}
	@Override
	public String listCourseAvailability(String semester) {
		char server=semester.charAt(semester.length()-1);
		if (server=='C'){
			semester=semester.substring(0, semester.length()-1);
			String result=ServerComp.listCourseAvailability(semester);
			return result;
		}
		else if(server=='S'){
			semester=semester.substring(0, semester.length()-1);
			String result=ServerSoen.listCourseAvailability(semester);
			return result;
		}
		else if (server=='I'){
			semester=semester.substring(0, semester.length()-1);
			String result=ServerInse.listCourseAvailability(semester);	
			return result;
		}
		else{
			System.out.println("wrong semester entered ");
			return "nodata";
		}
		
		
	}

	@Override
	public synchronized String enrolCourse(String studentID, String courseID, String semester) {//start function
		
		HashMap<String, HashSet<String>> studenthashinner = new HashMap<String, HashSet<String>>();
		
		String server=studentID.substring(0,4);
		String course=courseID.substring(0,4);						
		
		if (server.equals("COMP") && course.equals("COMP")){
			//start server check			
	    	String checkresult=ServerComp.enrol(studentID,courseID,semester);
	    	System.out.println(checkresult);
	    	return checkresult;			
		}
	    else if (server.equals("COMP") && course.equals("SOEN")){
	    	String checkresult=ServerComp.enrol(studentID,courseID,semester);
	    	System.out.println(checkresult);
	    	return checkresult;
	    }	
	    else if (server.equals("COMP") && course.equals("INSE")){
	    	String checkresult=ServerComp.enrol(studentID,courseID,semester);
	    	System.out.println(checkresult);
	    	return checkresult;
	    }
	    else if (server.equals("SOEN") && course.equals("SOEN")){
	    	String checkresult=ServerSoen.enrol(studentID,courseID,semester);
	    	System.out.println(checkresult);
	    	return checkresult;
		}
	    		    		    
	    else if (server.equals("SOEN") && course.equals("COMP")){
	    	String checkresult=ServerSoen.enrol(studentID,courseID,semester);
	    	System.out.println(checkresult);
	    	return checkresult;	    	
	    }
	    else if (server.equals("SOEN") && course.equals("INSE")){
	    	String checkresult=ServerSoen.enrol(studentID,courseID,semester);
	    	System.out.println(checkresult);
	    	return checkresult;	    	
	    }
	    else if (server.equals("INSE") && course.equals("INSE")){
	    	String checkresult=ServerInse.enrol(studentID,courseID,semester);
	    	System.out.println(checkresult);
	    	return checkresult;
		}	    	
	    
	    else if (server.equals("INSE") && course.equals("COMP")){
	    	String checkresult=ServerInse.enrol(studentID,courseID,semester);
	    	System.out.println(checkresult);
	    	return checkresult;	    	
	    }
	    else if (server.equals("INSE") && course.equals("SOEN")){
	    	String checkresult=ServerInse.enrol(studentID,courseID,semester);
	    	System.out.println(checkresult);
	    	return checkresult;	    	
	    }
		
				
		return "fail";
		
	}//end func
	
	public synchronized static String enrolcrosserver(String studentID, String courseID, String semester){
		System.out.println("inside enrolcrosserver");
		HashMap<String, HashSet<String>> studenthashinner = new HashMap<String, HashSet<String>>();
		String crdep=courseID.substring(0,4).toUpperCase().trim();
		
		boolean flag=studenthash.containsKey(studentID);
		if (!flag){
			System.out.println("The StudentID : "+ studentID +"  does not exist in the system");
			coursenewstud= new HashSet(); 
			coursenewstud.add(courseID);
			studenthashinner.put(semester,coursenewstud);
	        
			  studenthash.put(studentID,studenthashinner);
			  List<HashMap<String, HashMap<String, HashSet<String>>>> d = Arrays.asList(studenthash);
			  System.out.println("Student hash map :- "+d);	
			  
			  
			return "success";
			
		}else{
						
			studenthashinner=studenthash.get(studentID);
			coursestudF = new HashSet();
			coursestudW = new HashSet();
			coursestudS = new HashSet();
			coursestud = new HashSet();
			
			
			if(studenthashinner.containsKey("FALL")){
				coursestudF = studenthashinner.get("FALL");	
				
			}
			if(studenthashinner.containsKey("WINTER")){
				coursestudW = studenthashinner.get("WINTER");
				
			}
			if(studenthashinner.containsKey("SUMMER")){
				coursestudS = studenthashinner.get("SUMMER");	
				
			}
			
			if(studenthashinner.get(semester)!=null){//check semester box present 
				coursestud=studenthashinner.get(semester);
				
				
				//if (!(studenthashinner.get("Fall").contains(courseID)||studenthashinner.get("Winter").contains(courseID)||studenthashinner.get("Summer").contains(courseID))) {
				if(!(coursestudF.contains(courseID) || coursestudW.contains(courseID) || coursestudS.contains(courseID))){//already exits course
					
					int countotherdept=0;
					String studdept=studentID.substring(0,4).toUpperCase().trim();
					
					HashMap<String, HashSet<String>> studenthashinner1 = studenthash.get(studentID);
					
					for (String sem:studenthashinner1.keySet()){
						HashSet<String> sett=studenthashinner1.get(sem);
						for (String courses :sett){
							String coursedept=courses.substring(0,4).toUpperCase().trim();
							System.out.println("check 2 courses"+courses);
							System.out.println("course dept"+coursedept);
							System.out.println("student dept"+studdept);
							 if(!coursedept.equals(studdept)){
								 System.out.println("check 2");
								 System.out.println(courses);
								 countotherdept=countotherdept+1;
						         System.out.println(countotherdept);
							 }
							 
						}
					}
					
					System.out.println("countotherdept :"+countotherdept);
					if (countotherdept<2 || crdep.equals(studdept)){
						System.out.println("inside second countother department count");								
					if (	
							coursestud.size()<3){	
						   
							coursestud.add(courseID);
							studenthashinner.put(semester,coursestud);
							studenthash.put(studentID,studenthashinner);
									
							List<HashMap<String, HashMap<String,HashSet<String> >>> e = Arrays.asList(studenthash);
							System.out.println("student hash map "+e);
					
							return "success";
						}
						else{
							System.out.println("Cannot add more courses in this semester");
							return "fail";
						}
					
					}//count to dept close
				
				
					else
					{
						System.out.println("Cannot exceed more than 2 courses out of department");
						return "fail";
					}
					
				}//already exits course close
			
				else{
					
					System.out.println("This course already exists for this student");
					return "fail";
			
				}
			}//check semester box exits if close
											
			
			else{//check semester box present else
				
				if(!(coursestudF.contains(courseID) || coursestudW.contains(courseID) || coursestudS.contains(courseID))){
				//if (!(studenthashinner.get("Fall").contains(courseID)||studenthashinner.get("Winter").contains(courseID)||studenthashinner.get("Summer").contains(courseID))) {
					System.out.println("inside first countother department count");
					
					int countotherdept=0;
					String studdept=studentID.substring(0,4).toUpperCase().trim();
					
					HashMap<String, HashSet<String>> studenthashinner1 = studenthash.get(studentID);
					
					for (String sem:studenthashinner1.keySet()){
						HashSet<String> sett=studenthashinner1.get(sem);
						System.out.println(sett);
						for (String courses :sett){
							
							String coursedept=courses.substring(0,4).toUpperCase().trim();	
							System.out.println("check 1 courses "+courses);
							System.out.println("course dept"+coursedept);
							System.out.println("student dept"+studdept);
							 if(!(coursedept.equals(studdept))){
								 System.out.println(courses);
								 System.out.println("check 1");
								 countotherdept=countotherdept+1;
						         System.out.println(countotherdept);		
							 }
						}
					}
					
				System.out.println("countotherdept:" +countotherdept);	
				if (countotherdept<2||crdep.equals(studdept)){
					
				
					coursenewstud= new HashSet(); 
					
					coursenewstud.add(courseID);
					studenthashinner.put(semester,coursenewstud);
					studenthash.put(studentID,studenthashinner);
			
					List<HashMap<String, HashMap<String, HashSet<String>>>> e = Arrays.asList(studenthash);
					System.out.println("STUDENT hash map :- "+e);
			
		
					return "success";
				}
				
				else
				 {
					System.out.println("Cannot exceed more than 2 courses out of department");
					return "fail";
				 }
		
				}//if close course check exits
			else
			{
				System.out.println("This course already exists for this student");
				return "fail";
			}
			 
			}
			
		}//major else close
		
	}
	@Override
	public synchronized String swapCourse(String studentID, String oldcourseID, String newcourseID) {
		HashMap<String, HashSet<String>> studenthashinner = new HashMap<String, HashSet<String>>();	
		String semester=null;
		if(!studenthash.containsKey(studentID))
			System.out.println("Studentid :"+studentID+"does'nt contain any courses,please check the id");
		else
		{
			studenthashinner=studenthash.get(studentID);
			for(String sem :studenthashinner.keySet()){
				HashSet<String> s=studenthashinner.get(sem);
				for(String course:s){
					if(course.equals(oldcourseID)){
						//studenthashinner.get(sem).remove(oldcourseID);
						//studenthash.put(studentID,studenthashinner);							
						semester=sem;
						break;
					}
				}
						
				
			}
		
	    if (semester!=null){
	    	boolean resultswapdrop=dropCourse(studentID,oldcourseID);
	    	System.out.println(resultswapdrop);
	    	if (resultswapdrop) {
	    		String resultswapenrol=enrolCourse(studentID,newcourseID,semester);
	    		
	    		if(resultswapenrol.equals("success")) {
	    			System.out.println("Swap Successfull "+"New Course "+newcourseID+" enrolled and "+oldcourseID+" dropped");
	    			return "success";
	    		}
	    		else
	    		{	    			
	    			enrolCourse(studentID,oldcourseID,semester);
	    			System.out.println("The course "+newcourseID+" cannot be enrolled for student"+studentID);	
	    			return "fail";
	    		}
	    	}
	    	else
	    	{
				System.out.println("The course "+oldcourseID+" cannot be dropped for student "+studentID);
				return "fail";	    		
	    	}
	    	
	    }
	    else {
			System.out.println("The course "+oldcourseID+" "+"doesnt exist for student "+studentID);
			return "fail";
	    	
	    }
	    
	    
			
		}//else
		return "fail";
		
	}
	@Override
	public String getClassSchedule(String studentID) {
	
		// TODO Auto-generated method stub
		boolean flag=studenthash.containsKey(studentID);
		String resultschedule=null;
		if (flag){
			System.out.println("The StudentID : "+ studentID +"  exists in the system");
			studenthashinner=studenthash.get(studentID);
			fallschedulearray  =studenthashinner.get("FALL");
			winterschedulearray=studenthashinner.get("WINTER");
			Summerschedulearray=studenthashinner.get("SUMMER");
				
			resultschedule="Fall scheduled classes:"+" "+fallschedulearray+"Winter scheduled classes:"+" "+winterschedulearray+"Summer scheduled classes:"+" "+Summerschedulearray;
			
			
			return resultschedule;
		}
		else{
			resultschedule="The StudentID "+ studentID + " has no classes scheduled.";
			return resultschedule;
		}
	}
	@Override
	public synchronized boolean dropCourse(String studentID, String courseID) {
		// TODO Auto-generated method stub
		HashMap<String, HashSet<String>> studenthashinner = new HashMap<String, HashSet<String>>();
		String semester=null;
		boolean flag=studenthash.containsKey(studentID);
		
		if (flag){
			System.out.println("The StudentID : "+ studentID +  "  exists in the system");
			studenthashinner=studenthash.get(studentID);

				
				for(String sem :studenthashinner.keySet()){
					HashSet<String> s=studenthashinner.get(sem);
					for(String course:s){
						if(course.equals(courseID)){
							studenthashinner.get(sem).remove(courseID);
							studenthash.put(studentID,studenthashinner);							
							semester=sem;
							break;
						}
					}
							
					
				}
				
				

				
			if (semester!=null){
				
			  if(courseID.substring(0,4).toUpperCase().equals("COMP") && studentID.substring(0,4).toUpperCase().equals("COMP") ){
				  
				  String checkresult=ServerComp.drop(studentID,courseID,semester);
			    	
				  if(checkresult.equals("success")){
			    		return true;
			    	}
			    	return false;				  
				  
			  }
			  
			  else if(courseID.substring(0,4).toUpperCase().equals("SOEN") && studentID.substring(0,4).toUpperCase().equals("COMP") ){
			    	String checkresult=ServerComp.drop(studentID,courseID,semester);
			    	if(checkresult.equals("success")){
			    		return true;
			    	}
			    	return false;			    	
	
				  
			  }
			  else if(courseID.substring(0,4).toUpperCase().equals("INSE") && studentID.substring(0,4).toUpperCase().equals("COMP") ){
				
			    	String checkresult=ServerComp.drop(studentID,courseID,semester);
			    	if(checkresult.equals("success")){
			    		return true;
			    	}
			    	return false;
				  
			  }			  
			  
			  else if (courseID.substring(0,4).toUpperCase().equals("SOEN") && studentID.substring(0,4).toUpperCase().equals("SOEN")){
			    	String checkresult=ServerSoen.drop(studentID,courseID,semester);
			    	if(checkresult.equals("success")){
			    		return true;
			    	}
			    	return false;
			  }
			  else if(courseID.substring(0,4).toUpperCase().equals("COMP") && studentID.substring(0,4).toUpperCase().equals("SOEN") ){
			    	String checkresult=ServerSoen.drop(studentID,courseID,semester);
			    	if(checkresult.equals("success")){
			    		return true;
			    	}
			    	return false;
				  
			  }
			  else if(courseID.substring(0,4).toUpperCase().equals("INSE") && studentID.substring(0,4).toUpperCase().equals("SOEN") ){
			    	String checkresult=ServerSoen.drop(studentID,courseID,semester);
			    	if(checkresult.equals("success")){
			    		return true;
			    	}
			    	return false;				  
				  
			  }				  
			  			  
			  else if(courseID.substring(0,4).toUpperCase().equals("INSE") && studentID.substring(0,4).toUpperCase().equals("INSE")){
			    	String checkresult=ServerInse.drop(studentID,courseID,semester);
			    	if(checkresult.equals("success")){
			    		return true;
			    	}
			    	return false;	
			  }
			  else if(courseID.substring(0,4).toUpperCase().equals("COMP") && studentID.substring(0,4).toUpperCase().equals("INSE") ){
			    	String checkresult=ServerSoen.drop(studentID,courseID,semester);
			    	if(checkresult.equals("success")){
			    		return true;
			    	}
			    	return false;			  
				  
			  }
			  else if(courseID.substring(0,4).toUpperCase().equals("SOEN") && studentID.substring(0,4).toUpperCase().equals("INSE") ){
			    	String checkresult=ServerSoen.drop(studentID,courseID,semester);
			    	if(checkresult.equals("success")){
			    		return true;
			    	}
			    	return false;				  
				  
			  }				  
			  			  
			}
			else
			{
				System.out.println("The course "+courseID+" doesnt exist for student "+studentID);
				return false;
			}
			
		}
		
		else{
			System.out.println("This StudentID doesnt exists in the system");
			return false;	
		
	}
		return false;
	}


	
	
}


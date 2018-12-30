package server;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface WebInterface{
	public boolean addCourse(String courseID,String semester,String capacity);
	public boolean removeCourse(String courseID,String semester);
	public String listCourseAvailability(String semester);
	public String enrolCourse(String studentID, String courseID,String semester);
	public String getClassSchedule(String studentID);
	public boolean dropCourse(String studentID,String courseID);
	public String swapCourse(String studentID, String oldcourseID, String newcourseID);

}




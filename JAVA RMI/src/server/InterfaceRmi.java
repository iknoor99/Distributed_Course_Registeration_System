package server;

import java.rmi.*;
import java.util.HashMap;

public interface InterfaceRmi extends Remote{
	public boolean addCourse (String courseID, String semester) throws RemoteException;
	public boolean removeCourse (String courseID,String semester) throws RemoteException;
	public String  listCourseAvailability (String semester) throws RemoteException;
	public String  enrolCourse (String studentID, String courseID, String semester) throws RemoteException;
	public String  getClassSchedule (String studentID) throws RemoteException;
	public boolean dropCourse (String studentID,String courseID) throws RemoteException;

}




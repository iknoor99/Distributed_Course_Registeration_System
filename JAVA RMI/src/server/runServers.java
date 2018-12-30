package server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class runServers {
	
	   static void startRegistry(int RMIPortNum)
			      throws RemoteException{
			      try {
			         Registry registry = LocateRegistry.getRegistry(RMIPortNum);
			         registry.list( );  // This call will throw an exception
			                            // if the registry does not already exist
			      }
			      catch (RemoteException e) { 
			         // No valid registry at that port.
			/**/     System.out.println
			/**/        ("RMI registry cannot be located at port " 
			/**/        + RMIPortNum);
			         Registry registry = 
			            LocateRegistry.createRegistry(RMIPortNum);
			/**/        System.out.println(
			/**/           "RMI registry created at port " + RMIPortNum);
						
			      }
			   } // end startRegistry
	   
	   
	
	public static void main(String[] args) throws RemoteException, MalformedURLException{
		// TODO Auto-generated method stub
		ServerComp.startCompserver(3535);
	    ServerSoen.startSoenserver(3536);
        ServerInse.startInseserver(3537);
	}    

}




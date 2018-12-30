package server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import DCRS_1Corba.InterfaceCorba;
import DCRS_1Corba.InterfaceCorbaHelper;

public class runServers {
	
	
	public static void main(String args[]) throws RemoteException, MalformedURLException, NotFound, CannotProceed, InvalidName, org.omg.CORBA.ORBPackage.InvalidName, AdapterInactive, ServantNotActive, WrongPolicy{
		
		//ServerComp.startCompserver(args);
	//    ServerSoen.startSoenserver(args);
   //     ServerInse.startInseserver(args);
		
		ORB orb = ORB.init(args, null);
		
		POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		rootpoa.the_POAManager().activate();
		
		org.omg.CORBA.Object objRef =  orb.resolve_initial_references("NameService");
	    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	    
	    ImplementationRmi implcomp = new ImplementationRmi();
	    
	    implcomp.setORB(orb);
	    
	    org.omg.CORBA.Object objRefComp = rootpoa.servant_to_reference(implcomp);
	    InterfaceCorba objRefComp1 = InterfaceCorbaHelper.narrow(objRefComp);
		NameComponent nameobjectComp[] = ncRef.to_name("ServerComp");
		ncRef.rebind(nameobjectComp, objRefComp1);
		ServerComp.startCompserver();
			
	    ImplementationRmi implsoen = new ImplementationRmi();
	    
	    implsoen.setORB(orb);
	    
	    org.omg.CORBA.Object objRefSoen = rootpoa.servant_to_reference(implsoen);
	    InterfaceCorba objRefSoen1 = InterfaceCorbaHelper.narrow(objRefSoen);
		NameComponent nameobjectSoen[] = ncRef.to_name("ServerSoen");
		ncRef.rebind(nameobjectSoen, objRefSoen1);
		ServerSoen.startSoenserver();	
		
	    ImplementationRmi implinse = new ImplementationRmi();
	    
	    implinse.setORB(orb);
	    
	    org.omg.CORBA.Object objRefInse = rootpoa.servant_to_reference(implinse);
	    InterfaceCorba objRefInse1 = InterfaceCorbaHelper.narrow(objRefInse);
		NameComponent nameobjectInse[] = ncRef.to_name("ServerInse");
		ncRef.rebind(nameobjectInse, objRefInse1);
		ServerInse.startInseserver();		
		
		orb.run();
		
		
	}    

}




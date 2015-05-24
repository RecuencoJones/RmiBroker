package es.unizar.as.rmi.broker.clients.library;

import es.unizar.as.rmi.broker.broker.methods.BrokerIface;
import es.unizar.as.rmi.broker.clients.library.methods.LibraryIface;
import es.unizar.as.rmi.broker.clients.library.methods.LibraryImpl;
import es.unizar.as.rmi.broker.proxy.ServiceCaller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

/**
 * Created by david on 24/05/2015.
 */
public class LibraryClient {

    public static final String brokerName = "broker";
    public static final String hostIP = "localhost";
    public static final int hostPort = 2022;

    public static final String myIP = "localhost";
    public static final int myPort = 2021;

    public static final String proxyName = "libraryproxy";
    public static final String addBook = "addbook";
    public static final String getBooks = "getbooks";

    public static void main(String[] args) {
        try {

            //ProxySetup
            LibraryProxy libraryProxy = new LibraryProxy();
            ServiceCaller proxyStub = (ServiceCaller) UnicastRemoteObject.exportObject(libraryProxy, 0);
            Registry proxyRegistry = LocateRegistry.createRegistry(myPort);
            proxyRegistry.bind(proxyName,proxyStub);

            //BrokerRegister
            Registry brokerRegistry = LocateRegistry.getRegistry(hostIP, hostPort);
            BrokerIface brokerStub = (BrokerIface) brokerRegistry.lookup("broker");
            boolean response = brokerStub.registerServer(myIP+":"+myPort,proxyName);
            if(response) response = brokerStub.registerService(proxyName,addBook,new String[1]);
            if(response) response = brokerStub.registerService(proxyName,getBooks,new String[0]);

            //Execution
            brokerStub.executeService(addBook, "La historia interminable");
            System.out.println(brokerStub.executeService(getBooks));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected static class LibraryProxy implements ServiceCaller {

        LibraryImpl library = new LibraryImpl();

        public String call(String method, String... args) throws RemoteException {
            System.out.println("Called method: "+method+" with args: "+Arrays.toString(args));
            switch (method){
                case addBook:
                    return library.addBook(args[0])+"";
                case getBooks:
                    return Arrays.toString(library.getBooks());
                default:
                    return null;
            }
        }
    }
}

package es.unizar.as.rmi.broker.clients.library;

import es.unizar.as.rmi.broker.broker.methods.BrokerIface;
import es.unizar.as.rmi.broker.clients.library.methods.LibraryImpl;
import es.unizar.as.rmi.broker.proxy.ServiceCaller;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.logging.Logger;

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
            proxyInit();

            //BrokerRegister
            BrokerIface brokerStub = registerMethods();

            //Execution
            brokerStub.executeService(addBook, "La historia interminable");
            System.out.println(brokerStub.executeService(getBooks));

            //TODO eliminar las dos líneas de arriba y hacer una interfaz iterativa
            System.out.println("Available services:");
            String[] services = brokerStub.getServices();
            for (String s : services){
                System.out.println(" - "+s);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void proxyInit() throws RemoteException, AlreadyBoundException {
        LibraryProxy libraryProxy = new LibraryProxy();
        ServiceCaller proxyStub = (ServiceCaller) UnicastRemoteObject.exportObject(libraryProxy, 0);
        Registry proxyRegistry = LocateRegistry.createRegistry(myPort);
        proxyRegistry.bind(proxyName,proxyStub);
    }

    private static BrokerIface registerMethods() throws RemoteException, NotBoundException {
        Registry brokerRegistry = LocateRegistry.getRegistry(hostIP, hostPort);
        BrokerIface brokerStub = (BrokerIface) brokerRegistry.lookup(brokerName);
        boolean response = brokerStub.registerServer(myIP+":"+myPort,proxyName);
        if(response) response = brokerStub.registerService(proxyName,addBook,new String[1]);
        if(response) response = brokerStub.registerService(proxyName,getBooks,new String[0]);
        return brokerStub;
    }

    protected static class LibraryProxy implements ServiceCaller {

        Logger log = Logger.getLogger(this.getClass().getName());
        LibraryImpl library = new LibraryImpl();

        public String call(String method, String... args) throws RemoteException {
            log.info("Called method: "+method+" with args: "+Arrays.toString(args));
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

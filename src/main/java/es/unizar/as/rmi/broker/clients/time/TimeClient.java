package es.unizar.as.rmi.broker.clients.time;

import es.unizar.as.rmi.broker.broker.methods.BrokerIface;
import es.unizar.as.rmi.broker.clients.time.methods.TimeImpl;
import es.unizar.as.rmi.broker.proxy.ServiceCaller;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

/**
 * Created by david on 24/05/2015.
 */
public class TimeClient {

    public static final String brokerName = "broker";
    public static final String hostIP = "localhost";
    public static final int hostPort = 2022;

    public static final String myIP = "localhost";
    public static final int myPort = 2020;

    public static final String proxyName = "timeproxy";
    public static final String getDate = "getdate";
    public static final String getHour = "gethour";

    public static void main(String[] args) {
        try {

            //ProxySetup
            proxyInit();

            //BrokerRegister
            BrokerIface brokerStub = registerMethods();

            //Execution
            System.out.println(brokerStub.executeService(getDate));
            System.out.println(brokerStub.executeService(getHour));

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
        TimeProxy timeProxy = new TimeProxy();
        ServiceCaller proxyStub = (ServiceCaller) UnicastRemoteObject.exportObject(timeProxy, 0);
        Registry proxyRegistry = LocateRegistry.createRegistry(myPort);
        proxyRegistry.bind(proxyName,proxyStub);
    }

    private static BrokerIface registerMethods() throws RemoteException, NotBoundException {
        Registry brokerRegistry = LocateRegistry.getRegistry(hostIP, hostPort);
        BrokerIface brokerStub = (BrokerIface) brokerRegistry.lookup(brokerName);
        boolean response = brokerStub.registerServer(myIP+":"+myPort,proxyName);
        if(response) response = brokerStub.registerService(proxyName,getDate,new String[0]);
        if(response) response = brokerStub.registerService(proxyName,getHour,new String[0]);
        return brokerStub;
    }

    protected static class TimeProxy implements ServiceCaller {

        TimeImpl timer = new TimeImpl();

        public String call(String method, String... args) throws RemoteException {
            System.out.println("Called method: "+method+" with args: "+Arrays.toString(args));
            switch (method){
                case getDate:
                    return timer.getDate();
                case getHour:
                    return timer.getHour();
                default:
                    return null;
            }
        }
    }
}

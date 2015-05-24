package es.unizar.as.rmi.broker.broker;

import es.unizar.as.rmi.broker.broker.methods.BrokerIface;
import es.unizar.as.rmi.broker.broker.methods.BrokerImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Broker main class
 */
public class Broker {

    /**
     * Broker port and name
     */
    public static final int port = 2022;
    public static final String brokerName = "broker";

    /**
     * Broker initialization
     */
    public static void main(String[] args) {
        try{
            BrokerImpl broker = new BrokerImpl();
            BrokerIface stub = (BrokerIface) UnicastRemoteObject.exportObject(broker,0);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind(brokerName,stub);
            System.out.println("Broker ready");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

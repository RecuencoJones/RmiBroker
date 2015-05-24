package es.unizar.as.rmi.broker.broker;

import es.unizar.as.rmi.broker.broker.methods.BrokerIface;
import es.unizar.as.rmi.broker.broker.methods.BrokerImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by david on 24/05/2015.
 */
public class Broker {

    public static final int port = 2020;

    public static void main(String[] args) {
        try{
            BrokerImpl broker = new BrokerImpl();
            BrokerIface stub = (BrokerIface) UnicastRemoteObject.exportObject(broker,0);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("broker",stub);
            System.out.println("Broker ready");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

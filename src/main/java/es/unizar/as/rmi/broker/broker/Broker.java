package es.unizar.as.rmi.broker.broker;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
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
     * Broker initialization
     */
    public static void main(String[] args) {

        /**
         * Load configuration
         */
        Config conf = ConfigFactory.load("broker");

        /**
         * Broker port and name
         */
        final int port = conf.getInt("port");
        final String brokerName = conf.getString("name");

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

package es.unizar.as.rmi.broker.broker.methods;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by david on 24/05/2015.
 */
public interface BrokerIface extends Remote {

    String executeService(String serviceName, String... args) throws RemoteException;

    boolean registerServer(String remoteHost, String serverName) throws RemoteException;

    boolean registerService(String serverName, String serviceName, String... args) throws RemoteException;

    String[] getServices() throws RemoteException;
}

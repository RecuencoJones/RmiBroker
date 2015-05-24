package es.unizar.as.rmi.broker.broker.methods;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Broker stub methods
 */
public interface BrokerIface extends Remote {

    /**
     * Method that indicates the broker to execute a service
     * @param serviceName name of the service
     * @param args list of arguments for the service
     * @return the result of invoking this service
     * @throws RemoteException
     */
    String executeService(String serviceName, String... args) throws RemoteException;

    /**
     * Method that registers a physical server (host) to the broker
     * @param remoteHost remote host IP:port
     * @param serverName remote host name
     * @return whether register went right or wrong
     * @throws RemoteException
     */
    boolean registerServer(String remoteHost, String serverName) throws RemoteException;

    /**
     * Method that registers a service from a server
     * @param serverName remote host name
     * @param serviceName service name
     * @param args service list of arguments
     * @return whether register went right or wrong
     * @throws RemoteException
     */
    boolean registerService(String serverName, String serviceName, String... args) throws RemoteException;

    /**
     * Method that retrieves all available services on the broker
     * @return list of services
     * @throws RemoteException
     */
    String[] getServices() throws RemoteException;
}

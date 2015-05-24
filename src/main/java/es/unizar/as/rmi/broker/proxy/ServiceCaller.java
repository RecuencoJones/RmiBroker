package es.unizar.as.rmi.broker.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Proxy stub methods
 */
public interface ServiceCaller extends Remote {

    /**
     * Method that handles service ad-hoc invocation on client
     * @param method requested method
     * @param args requested method list of arguments
     * @return invoked method's response
     * @throws RemoteException
     */
    String call(String method, String... args) throws RemoteException;
}

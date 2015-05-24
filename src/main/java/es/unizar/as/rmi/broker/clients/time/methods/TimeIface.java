package es.unizar.as.rmi.broker.clients.time.methods;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Time stub methods
 */
public interface TimeIface extends Remote {

    /**
     * Method that gets current Date in format yyyy-mm-dd
     * @return current Date in format yyyy-mm-dd
     * @throws RemoteException
     */
    String getDate() throws RemoteException;

    /**
     * Method that gets current hour in format hh:mm
     * @return current hour in format hh:mm
     * @throws RemoteException
     */
    String getHour() throws RemoteException;
}

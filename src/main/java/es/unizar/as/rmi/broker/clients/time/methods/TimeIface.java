package es.unizar.as.rmi.broker.clients.time.methods;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by david on 24/05/2015.
 */
public interface TimeIface extends Remote {

    /**
     *
     * @return
     * @throws RemoteException
     */
    String getDate() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    String getHour() throws RemoteException;
}

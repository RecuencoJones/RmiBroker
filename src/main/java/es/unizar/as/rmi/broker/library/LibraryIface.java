package es.unizar.as.rmi.broker.library;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by david on 24/05/2015.
 */
public interface LibraryIface extends Remote {

    /**
     *
     * @param title
     * @return
     * @throws RemoteException
     */
    boolean addBook(String title) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    String[] getBooks() throws RemoteException;
}

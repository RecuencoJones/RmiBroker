package es.unizar.as.rmi.broker.clients.library.methods;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Library stub methods
 */
public interface LibraryIface extends Remote {

    /**
     * Method that adds a new book to the library
     * @param title new book title
     * @return whether adding went right or wrong
     * @throws RemoteException
     */
    boolean addBook(String title) throws RemoteException;

    /**
     * Method that returns the list of books of this library
     * @return list of books
     * @throws RemoteException
     */
    String[] getBooks() throws RemoteException;
}

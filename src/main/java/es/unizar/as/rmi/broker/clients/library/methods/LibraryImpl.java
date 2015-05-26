package es.unizar.as.rmi.broker.clients.library.methods;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Implementation of LibraryIface stubs
 */
public class LibraryImpl implements LibraryIface {

    ArrayList<String> list = new ArrayList<String>();

    /**
     * Method that adds a new book to the library
     * @param title new book title
     * @return whether adding went right or wrong
     * @throws RemoteException
     */
    public boolean addBook(String title) throws RemoteException {
        return list.add(title);
    }

    /**
     * Method that retrieves all available services on the broker
     * @return list of services
     * @throws RemoteException
     */
    public String[] getBooks() throws RemoteException {
        return list.toArray(new String[list.size()]);
    }
}

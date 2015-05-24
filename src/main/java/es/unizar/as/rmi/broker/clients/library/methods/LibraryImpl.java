package es.unizar.as.rmi.broker.clients.library.methods;

import es.unizar.as.rmi.broker.clients.library.methods.LibraryIface;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by david on 24/05/2015.
 */
public class LibraryImpl implements LibraryIface {

    ArrayList<String> list = new ArrayList<>();

    public boolean addBook(String title) throws RemoteException {
        return list.add(title);
    }

    public String[] getBooks() throws RemoteException {
        return list.toArray(new String[list.size()]);
    }
}
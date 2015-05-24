package es.unizar.as.rmi.broker.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by david on 24/05/2015.
 */
public interface ServiceCaller extends Remote {

    String call(String method, String... args) throws RemoteException;
}

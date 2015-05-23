package example.hello;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by david on 22/05/15.
 */
public interface Hello extends Remote {
    String sayHello() throws RemoteException;
}

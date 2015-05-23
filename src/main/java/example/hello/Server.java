package example.hello;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by david on 22/05/15.
 */
public class Server implements Hello {


    public Server() {}

    public String sayHello() throws RemoteException {
        return "Hello, world!";
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
//            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello",stub);
            System.out.println("Server ready");
        }catch (Exception e){
            System.err.println("Server exception");
            e.printStackTrace();
        }
    }
}

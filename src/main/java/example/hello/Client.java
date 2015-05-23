package example.hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by david on 22/05/15.
 */
public class Client {

    private Client(){}

    public static void main(String[] args) {

        String host = "localhost";

        try{
            Registry registry = LocateRegistry.getRegistry(host,1099);
            Hello stub = (Hello) registry.lookup("Hello");
            String response = stub.sayHello();
            System.out.println(response);
        }catch (Exception e){
            System.err.println("Client Exception");
            e.printStackTrace();
        }
    }
}

package es.unizar.as.rmi.broker.broker.methods;

import es.unizar.as.rmi.broker.broker.BrokerService;
import es.unizar.as.rmi.broker.broker.Host;
import es.unizar.as.rmi.broker.proxy.ServiceCaller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by david on 24/05/2015.
 */
public class BrokerImpl implements BrokerIface {

    private final ConcurrentHashMap<String, Host> serversToHosts = new ConcurrentHashMap<String, Host>();
    private final ConcurrentHashMap<String, String> servicesToServers = new ConcurrentHashMap<String, String>();
    private final ConcurrentHashMap<String, BrokerService> services = new ConcurrentHashMap<String, BrokerService>();

    public String executeService(String serviceName, String... args) throws RemoteException {

        BrokerService service = services.get(serviceName);
        Host host = serversToHosts.get(servicesToServers.get(serviceName));

        try {
            Registry registry = LocateRegistry.getRegistry(host.getIP(), host.getPort());
            ServiceCaller stub = (ServiceCaller) registry.lookup(service.getServiceName());
            String response = stub.call(service.getServiceName(),service.getArgs());
            return response;
        }catch (Exception e){
            System.err.println("Broker exception");
            e.printStackTrace();
            return null;
        }
    }

    public boolean registerServer(String remoteHost, String serverName) throws RemoteException {
        try {
            serversToHosts.put(serverName, new Host(remoteHost));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerService(String serverName, String serviceName, String... args) throws RemoteException {
        try {
            services.put(serviceName, new BrokerService(serviceName, args));
            servicesToServers.put(serviceName, serverName);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

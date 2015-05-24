package es.unizar.as.rmi.broker.broker.methods;

import es.unizar.as.rmi.broker.broker.Host;
import es.unizar.as.rmi.broker.proxy.ServiceCaller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Implementation of BrokerIface stubs
 */
public class BrokerImpl implements BrokerIface {

    Logger log = Logger.getLogger(this.getClass().getName());
    private final ConcurrentHashMap<String, Host> serversToHosts = new ConcurrentHashMap<String, Host>();
    private final ConcurrentHashMap<String, String> servicesToServers = new ConcurrentHashMap<String, String>();
    private final ArrayList<String> services = new ArrayList<>();

    /**
     * Method that indicates the broker to execute a service
     * @param serviceName name of the service
     * @param args list of arguments for the service
     * @return the result of invoking this service
     * @throws RemoteException
     */
    public String executeService(String serviceName, String... args) throws RemoteException {

        String server = servicesToServers.get(serviceName);
        Host host = serversToHosts.get(server);
        log.info("Called service: " + serviceName + " with args: " + Arrays.toString(args));

        try {
            Registry registry = LocateRegistry.getRegistry(host.getIP(), host.getPort());
            ServiceCaller serverStub = (ServiceCaller) registry.lookup(server);
            String response = serverStub.call(serviceName,args);
            return response;
        }catch (Exception e){
            log.severe("Broker exception");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method that registers a physical server (host) to the broker
     * @param remoteHost remote host IP:port
     * @param serverName remote host name
     * @return whether register went right or wrong
     * @throws RemoteException
     */
    public boolean registerServer(String remoteHost, String serverName) throws RemoteException {
        try {
            serversToHosts.put(serverName, new Host(remoteHost));
            log.info("Registered host: " + remoteHost + " with server name: " + serverName);
            return true;
        }catch (Exception e){
            log.severe("Broker exception");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method that registers a service from a server
     * @param serverName remote host name
     * @param serviceName service name
     * @param args service list of arguments
     * @return whether register went right or wrong
     * @throws RemoteException
     */
    public boolean registerService(String serverName, String serviceName, String... args) throws RemoteException {
        try {
            services.add(serviceName);
            servicesToServers.put(serviceName, serverName);
            log.info("Registered service: " + serviceName + " on server name: " + serverName);
            return true;
        }catch (Exception e){
            log.severe("Broker exception");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method that retrieves all available services on the broker
     * @return list of services
     * @throws RemoteException
     */
    public String[] getServices() throws RemoteException {
        return services.toArray(new String[services.size()]);
    }


}

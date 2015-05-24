package es.unizar.as.rmi.broker.broker.methods;

import es.unizar.as.rmi.broker.broker.BrokerService;
import es.unizar.as.rmi.broker.broker.Host;
import es.unizar.as.rmi.broker.proxy.ServiceCaller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Created by david on 24/05/2015.
 */
public class BrokerImpl implements BrokerIface {

    Logger log = Logger.getLogger(this.getClass().getName());
    private final ConcurrentHashMap<String, Host> serversToHosts = new ConcurrentHashMap<String, Host>();
    private final ConcurrentHashMap<String, String> servicesToServers = new ConcurrentHashMap<String, String>();
    private final ConcurrentHashMap<String, BrokerService> services = new ConcurrentHashMap<String, BrokerService>();

    public String executeService(String serviceName, String... args) throws RemoteException {

        BrokerService service = services.get(serviceName);
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

    public boolean registerService(String serverName, String serviceName, String... args) throws RemoteException {
        try {
            services.put(serviceName, new BrokerService(serviceName, args));
            servicesToServers.put(serviceName, serverName);
            log.info("Registered service: " + serviceName + " on server name: " + serverName);
            return true;
        }catch (Exception e){
            log.severe("Broker exception");
            e.printStackTrace();
            return false;
        }
    }

    public String[] getServices() throws RemoteException {
        List keys = Collections.list(services.keys());
        return (String[]) keys.toArray(new String[keys.size()]);
    }


}

package es.unizar.as.rmi.broker.broker;

/**
 * Object representing an RMI remote host
 */
public class Host {

    private String IP;
    private int port;

    /**
     * Constructor for Host object
     * @param host String in the form of IP or IP:port
     */
    public Host(String host) {
        if(host.contains(":")){
            set(host.split(":")[0], Integer.parseInt(host.split(":")[1]));
        }else{
            set(host,1099);
        }
    }

    /**
     * Sets this host's properties
     * @param IP the IP
     * @param port the port
     */
    private void set(String IP, int port) {
        this.IP = IP;
        this.port = port;
    }

    /**
     * Returns this host's IP
     * @return IP
     */
    public String getIP() {
        return IP;
    }

    /**
     * Returns this host's port
     * @return port
     */
    public int getPort() {
        return port;
    }
}

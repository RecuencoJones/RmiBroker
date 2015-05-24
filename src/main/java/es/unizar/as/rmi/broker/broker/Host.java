package es.unizar.as.rmi.broker.broker;

/**
 * Created by david on 24/05/2015.
 */
public class Host {

    private String IP;
    private int port;

    public Host(String host) {
        if(host.contains(":")){
            set(host.split(":")[0], Integer.parseInt(host.split(":")[1]));
        }else{
            set(host,1099);
        }
    }

    private void set(String IP, int port) {
        this.IP = IP;
        this.port = port;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return port;
    }
}

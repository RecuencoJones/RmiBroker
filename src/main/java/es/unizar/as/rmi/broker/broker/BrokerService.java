package es.unizar.as.rmi.broker.broker;

/**
 * Created by david on 24/05/2015.
 */
public class BrokerService {


    private String serviceName;
    private String[] args;

    public BrokerService(String serviceName, String[] args) {
        this.serviceName = serviceName;
        this.args = args;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String[] getArgs() {
        return args;
    }
}

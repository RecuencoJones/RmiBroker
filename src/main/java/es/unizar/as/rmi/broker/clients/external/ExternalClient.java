package es.unizar.as.rmi.broker.clients.external;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import es.unizar.as.rmi.broker.broker.methods.BrokerIface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Created by david on 26/05/2015.
 */
public class ExternalClient {

    public static String brokerName;
    public static String hostIP;
    public static int hostPort;

    public static void main(String[] args) {

        //Config
        doConfiguration();

        try {

            //BrokerStub
            BrokerIface brokerStub = getBrokerStub();

            //Execution
            doInteraction(brokerStub);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Client initialization
     */
    private static void doConfiguration() {
        Config conf = ConfigFactory.load("external");

        brokerName = conf.getString("broker.name");
        hostIP = conf.getString("broker.host");
        hostPort = conf.getInt("broker.port");
    }

    /**
     * Aux method to get broker stub
     * @return broker stubs
     * @throws RemoteException
     * @throws java.rmi.NotBoundException
     */
    private static BrokerIface getBrokerStub() throws RemoteException, NotBoundException {
        Registry brokerRegistry = LocateRegistry.getRegistry(hostIP, hostPort);
        BrokerIface brokerStub = (BrokerIface) brokerRegistry.lookup(brokerName);
        return brokerStub;
    }

    /**
     * Aux method that does interaction with the user
     * @param brokerStub stub with broker methods
     * @throws RemoteException
     */
    private static void doInteraction(BrokerIface brokerStub) throws RemoteException {

        Scanner input = new Scanner(System.in);
        boolean finish = true;
        while(finish){
            System.out.println("Available services:");
            String[] services = brokerStub.getServices();
            int cont = 1;
            System.out.println(0+") - Update");
            for (String s : services){
                System.out.println(cont+") - "+s);
                cont++;
            }
            System.out.println(cont+") - Finish execution");
            System.out.print("Selection: ");
            int optionUserI = input.nextInt(); input.nextLine();

            if(optionUserI==0){

            }else if(optionUserI==cont){
                finish = false;
            }else{
                String aux = services[optionUserI-1];
                String paramList = aux.substring(aux.indexOf("(")+1,aux.indexOf(")"));
                String[] params = paramList.split(",");
                String[] inputs = new String[params.length];
                if(!paramList.equals("")) {
                    for (int i = 0; i < params.length; i++) {
                        System.out.print("Required parameter \"" + params[i] + "\": ");
                        inputs[i] = input.nextLine();
                    }
                }
                System.out.println(brokerStub.executeService(services[optionUserI-1], inputs));
            }
        }

        System.out.println("ExternalClient finished.");
        System.exit(0);
    }

}
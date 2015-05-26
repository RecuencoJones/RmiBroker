package es.unizar.as.rmi.broker.clients.time;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import es.unizar.as.rmi.broker.broker.methods.BrokerIface;
import es.unizar.as.rmi.broker.clients.time.methods.TimeImpl;
import es.unizar.as.rmi.broker.proxy.ServiceCaller;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * TimeClient main class
 */
public class TimeClient {

    public static String brokerName;
    public static String hostIP;
    public static int hostPort;

    public static String proxyName;
    public static String myIP;
    public static int myPort;
    public static final String getDate = "getDate()";
    public static final String getHour = "getHour()";

    /**
     * Client initialization
     */
    public static void main(String[] args) {

        //Config
        doConfiguration();

        try {

            //ProxySetup
            proxyInit();

            //BrokerRegister
            BrokerIface brokerStub = registerMethods();

            //Execution
            doInteraction(brokerStub);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Client initialization
     */
    private static void doConfiguration() {
        Config conf = ConfigFactory.load("time");

        brokerName = conf.getString("broker.name");
        hostIP = conf.getString("broker.host");
        hostPort = conf.getInt("broker.port");

        proxyName = conf.getString("client.name");
        myIP = conf.getString("client.host");
        myPort = conf.getInt("client.port");
    }

    /**
     * ClientProxy initialization
     */
    private static void proxyInit() throws RemoteException, AlreadyBoundException {
        TimeProxy timeProxy = new TimeProxy();
        ServiceCaller proxyStub = (ServiceCaller) UnicastRemoteObject.exportObject(timeProxy, 0);
        Registry proxyRegistry = LocateRegistry.createRegistry(myPort);
        proxyRegistry.bind(proxyName, proxyStub);
    }

    /**
     * Aux method to handle register services on broker
     *
     * @return broker stubs
     * @throws RemoteException
     * @throws NotBoundException
     */
    private static BrokerIface registerMethods() throws RemoteException, NotBoundException {
        Registry brokerRegistry = LocateRegistry.getRegistry(hostIP, hostPort);
        BrokerIface brokerStub = (BrokerIface) brokerRegistry.lookup(brokerName);
        boolean response = brokerStub.registerServer(myIP + ":" + myPort, proxyName);
        if (response) response = brokerStub.registerService(proxyName, getDate, new String[0]);
        if (response) response = brokerStub.registerService(proxyName, getHour, new String[0]);
        return brokerStub;
    }

    /**
     * Aux method that does interaction with the user
     *
     * @param brokerStub stub with broker methods
     * @throws RemoteException
     */
    private static void doInteraction(BrokerIface brokerStub) throws RemoteException {

        Scanner input = new Scanner(System.in);
        boolean finish = true;
        while (finish) {
            System.out.println("Available services:");
            String[] services = brokerStub.getServices();
            int cont = 1;
            System.out.println(0 + ") - Update");
            for (String s : services) {
                System.out.println(cont + ") - " + s);
                cont++;
            }
            System.out.println(cont + ") - Finish execution");
//            System.out.print("Selection: ");
//            int optionUserI = input.nextInt();
//            input.nextLine();
            int optionUserI = Integer.parseInt(JOptionPane.showInputDialog(null, "Selection: ", "TimeClient", JOptionPane.PLAIN_MESSAGE));

            if (optionUserI == 0) {

            } else if (optionUserI == cont) {
                finish = false;
            } else {
                String aux = services[optionUserI - 1];
                String paramList = aux.substring(aux.indexOf("(") + 1, aux.indexOf(")"));
                String[] params = paramList.split(",");
                String[] inputs = new String[params.length];
                if (!paramList.equals("")) {
                    for (int i = 0; i < params.length; i++) {
//                        System.out.print("Required parameter \"" + params[i] + "\": ");
//                        inputs[i] = input.nextLine();
                        inputs[i] = JOptionPane.showInputDialog(null, "Required parameter \"" + params[i] + "\": ", "TimeClient", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                System.out.println(brokerStub.executeService(services[optionUserI - 1], inputs));
            }
        }

        System.out.println("TimeClient finished.");
        System.exit(0);
    }

    /**
     * This client's proxy implementation
     */
    protected static class TimeProxy implements ServiceCaller {

        TimeImpl timer = new TimeImpl();
        Logger log = Logger.getLogger(this.getClass().getName());

        /**
         * Method that handles service ad-hoc invocation on client
         *
         * @param method requested method
         * @param args   requested method list of arguments
         * @return invoked method's response
         * @throws RemoteException
         */
        public String call(String method, String... args) throws RemoteException {
            log.info("Called method: " + method + " with args: " + Arrays.toString(args));
            switch (method) {
                case getDate:
                    return timer.getDate();
                case getHour:
                    return timer.getHour();
                default:
                    return null;
            }
        }
    }
}

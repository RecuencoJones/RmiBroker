package es.unizar.as.rmi.broker.clients.time.methods;

import es.unizar.as.rmi.broker.clients.time.methods.TimeIface;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of LibraryIface stubs
 */
public class TimeImpl implements TimeIface {

    /**
     * Method that gets current Date in format yyyy-mm-dd
     * @return current Date in format yyyy-mm-dd
     * @throws RemoteException
     */
    public String getDate() throws RemoteException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        return formatter.format(new Date());
    }

    /**
     * Method that gets current hour in format hh:mm
     * @return current hour in format hh:mm
     * @throws RemoteException
     */
    public String getHour() throws RemoteException {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        return formatter.format(new Date());
    }
}

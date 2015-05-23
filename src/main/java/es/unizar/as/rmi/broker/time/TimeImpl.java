package es.unizar.as.rmi.broker.time;

import javax.swing.text.DateFormatter;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by david on 24/05/2015.
 */
public class TimeImpl implements TimeIface {

    /**
     *
     * @return
     * @throws RemoteException
     */
    public String getDate() throws RemoteException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        return formatter.format(new Date());
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    public String getHour() throws RemoteException {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        return formatter.format(new Date());
    }
}

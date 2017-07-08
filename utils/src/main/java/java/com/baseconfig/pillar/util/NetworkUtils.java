package java.com.baseconfig.pillar.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utils for common network tasks, plus checking network connectivity and speed
 * <p>
 * As provided via: <a href="https://gist.github.com/emil2k/5130324">Github Gist</a>
 *
 * @author emil http://stackoverflow.com/users/220710/emil
 */
public class NetworkUtils {

    /**
     * Check if there is any connectivity to a mobile network
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Get the network info
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if connected
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }


    /**
     * Check if connected with types of <ul> <li> {@link ConnectivityManager#TYPE_MOBILE} <li>
     * {@link ConnectivityManager#TYPE_WIFI} <li> {@link ConnectivityManager#TYPE_WIMAX} <li> {@link
     * ConnectivityManager#TYPE_ETHERNET} <li> {@link ConnectivityManager#TYPE_BLUETOOTH} </ul> or
     * other types defined by {@link ConnectivityManager}
     */
    public static boolean isConnected(Context context, int networkType) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == networkType);
    }

}

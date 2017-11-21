package com.yanzhenjie.andserver.sample.utils;

import android.util.Log;

import com.yanzhenjie.andserver.sample.BuildConfig;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * Network related tools
 * Created by Nan.Tofu on 17-11-21.
 */

public class NetUtils {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = DEBUG ? "NetUtils" : "";

    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

    public static String getLocalIPAddress() {
        Enumeration enumeration = null;

        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException var4) {
            if (DEBUG) {
                Log.wtf(TAG, "getLocalIPAddress: ", var4);
            }
        }

        if(enumeration != null) {
            while(true) {
                Enumeration inetAddresses;
                do {
                    if(!enumeration.hasMoreElements()) {
                        return "";
                    }

                    NetworkInterface nif = (NetworkInterface)enumeration.nextElement();
                    inetAddresses = nif.getInetAddresses();
                } while(inetAddresses == null);

                while(inetAddresses.hasMoreElements()) {
                    InetAddress ip = (InetAddress)inetAddresses.nextElement();
                    if(!ip.isLoopbackAddress() && isIPv4Address(ip.getHostAddress())) {
                        return ip.getHostAddress();
                    }
                }
            }
        } else {
            return "";
        }
    }

    private static boolean isIPv4Address(String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }
}

package me.th.limit.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class IpUtils {

    private static final String COMMA = ",";
    private static final String LOCALHOST_IP_1 = "127.0.0.1";
    private static final String LOCALHOST_IP_2 = "0:0:0:0:0:0:0:1";
    private static final String UNKNOWN = "unknown";

    private IpUtils() {
    }

    public static String getIP(HttpServletRequest request) {
        String ip = null;
        ip = request.getHeader("X-Original-Forwarded-For");
        if (ipIsNull(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ipIsNull(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ipIsNull(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ipIsNull(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipIsNull(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipIsNull(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipIsNull(ip)) {
            ip = request.getRemoteAddr();
            if (LOCALHOST_IP_1.equalsIgnoreCase(ip) || LOCALHOST_IP_2.equalsIgnoreCase(ip)) {
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    log.error(e.getMessage(), e);
                    ip = "";
                }
            }
        }
        if (ip.contains(COMMA)) {
            ip = ip.split(COMMA)[0];
        }
        return ip;
    }

    private static boolean ipIsNull(String ip) {
        return ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip);
    }
}

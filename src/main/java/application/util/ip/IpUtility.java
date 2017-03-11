package application.util.ip;

import com.google.common.net.InetAddresses;
import org.springframework.stereotype.Component;

import java.net.Inet6Address;
import java.net.InetAddress;

/**
 * A utility class used for operating on IP addresses.
 * A huge credit for the solution used in this class must go to
 * user "halidave" who wrote and posted this elegant piece of code
 * on stackoverflow: "http://stackoverflow.com/questions/4256438/calculate-whether-an-ip-address-is-in-a-specified-range-in-java".
 *
 * Resource accessed: 25/01/2017
 */
@Component
public class IpUtility {

    /**
     * This method is used to make sure that a given ip address
     * fits within the range between ipStart and ipEnd.
     *
     * @param ip
     * @param ipStart
     * @param ipEnd
     * @return boolean
     */
    public boolean checkIfIpInRange(String ip, String ipStart, String ipEnd) {

        long address = ipToLong(InetAddresses.forString(ip));
        long rangeStart = ipToLong(InetAddresses.forString(ipStart));
        long rangeEnd = ipToLong(InetAddresses.forString(ipEnd));

        if (address >= rangeStart && address <= rangeEnd)
            return true;
        else
            return false;
    }

    public boolean validate(String ip) {
        return InetAddresses.isInetAddress(ip);
    }

    /**
     * This method converts ip addresses into
     * longs. Ip address is a 32-bit long number
     * with 8-bit octets hence it is quite easy
     * to convert them into integers of longs.
     *
     * @param ip
     * @return long
     */
    private long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }
}

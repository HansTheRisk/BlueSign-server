package unit.util.ip;

import main.application.util.ip.IpUtility;
import org.junit.Assert;
import org.junit.Test;

public class IpUtilityTest {

    @Test
    public void ipWithinRange() {

        IpUtility utility = new IpUtility();
        String ip = "128.200.0.15";
        String ipStart = "128.200.0.0";
        String ipEnd = "128.255.0.0";

        Assert.assertTrue(utility.checkIfIpInRange(ip, ipStart, ipEnd));
    }

    @Test
    public void ipOutsideOfRange() {

        IpUtility utility = new IpUtility();
        String ip = "128.255.0.15";
        String ipStart = "128.200.0.0";
        String ipEnd = "128.255.0.0";

        Assert.assertFalse(utility.checkIfIpInRange(ip, ipStart, ipEnd));
    }

}

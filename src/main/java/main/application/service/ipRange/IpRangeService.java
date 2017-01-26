package main.application.service.ipRange;

import main.application.domain.ipRange.IpRange;
import main.application.repository.ipRange.IpRangeRepository;
import main.application.service.NaturallyIdentifiableService;
import main.application.util.ip.IpUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IpRangeService implements NaturallyIdentifiableService<IpRange> {

    @Autowired
    private IpRangeRepository ipRangeRepository;
    @Autowired
    private IpUtility ipUtility;

    public List<IpRange> getAllIpRanges() {
        return ipRangeRepository.findAllRanges();
    }

    public boolean checkIfIpInRange(String ip) {
        boolean returnVal = false;
        List<IpRange> ranges = getAllIpRanges();
        if (ranges.isEmpty()) {
            returnVal = true;
        }
        else {
            for(IpRange ipRange : ranges) {
                if(ipUtility.checkIfIpInRange(ip, ipRange.getIpStart(), ipRange.getIpEnd())) {
                    returnVal = true;
                    break;
                }
            }
        }
        return returnVal;
    }

    @Override
    public IpRange findByUUID(String uuid) {
        return null;
    }

    @Override
    public IpRange findById(Long id) {
        return null;
    }

    @Override
    public IpRange save(IpRange object) {
        return null;
    }
}

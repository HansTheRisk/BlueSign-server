package application.service.ipRange;

import application.domain.ipRange.IpRange;
import application.repository.ipRange.IpRangeRepository;
import application.service.NaturallyIdentifiableService;
import application.util.ip.IpUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This service allows operations on the ip_range table.
 */
@Component
public class IpRangeService implements NaturallyIdentifiableService<IpRange> {

    @Autowired
    private IpRangeRepository ipRangeRepository;
    @Autowired
    private IpUtility ipUtility;

    /**
     * This method returns all the ip
     * ranges from the db.
     * @return List of IpRanges
     */
    public List<IpRange> getAllIpRanges() {
        return ipRangeRepository.findAllRanges();
    }

    /**
     * This method checks if the given ip
     * is within range of the stored ip
     * ranges.
     * @param ip
     * @return boolean
     */
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

    /**
     * Returns ip range by uuid
     * @param uuid
     * @return IpRange
     */
    @Override
    public IpRange findByUUID(String uuid) {
        return ipRangeRepository.findByUuid(uuid);
    }

    /**
     * Returns ip range by its id.
     * @param id
     * @return IpRange
     */
    @Override
    public IpRange findById(Long id) {
        return ipRangeRepository.findById(id);
    }

    /**
     * Saves an ip range.
     * @param object
     * @return IpRange
     */
    @Override
    public IpRange save(IpRange object) {
        return ipRangeRepository.save(object);
    }

    public boolean delete(String uuid) {
        return ipRangeRepository.delete(uuid);
    }
}

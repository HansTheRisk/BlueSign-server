package application.repository.ipRange;

import application.domain.ipRange.IpRange;
import application.repository.BaseJDBCRepository;
import application.repository.NaturallyIdentifiableRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This repository allows operating on the ip_range table.
 */
@Component
public class IpRangeRepository extends BaseJDBCRepository implements NaturallyIdentifiableRepository<IpRange>{

    /**
     * This method returns all the ip ranges from the table.
     * @return List of IpRanges
     */
    public List<IpRange> findAllRanges() {
        String sql = "SELECT id, uuid, start, end " +
                     "FROM ip_range";
        return executor.query(sql, new IpRangeRowMapper());
    }

    @Override
    public IpRange findByUuid(String uuid) {
        return null;
    }

    @Override
    public IpRange findById(Long id) {
        return null;
    }
}

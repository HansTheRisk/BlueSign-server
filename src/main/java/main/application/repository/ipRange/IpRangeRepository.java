package main.application.repository.ipRange;

import main.application.domain.ipRange.IpRange;
import main.application.repository.BaseJDBCRepository;
import main.application.repository.NaturallyIdentifiableRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IpRangeRepository extends BaseJDBCRepository implements NaturallyIdentifiableRepository<IpRange>{

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

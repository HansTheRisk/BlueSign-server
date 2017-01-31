package application.domain.ipRange;

import application.domain.entity.NaturallyIdentifiableEntity;

public class IpRange implements NaturallyIdentifiableEntity {

    private Long id;
    private String uuid;
    private String ipStart;
    private String ipEnd;

    public IpRange() {

    }

    public IpRange(Long id, String uuid, String ipStart, String ipEnd) {
        this.id = id;
        this.uuid = uuid;
        this.ipStart = ipStart;
        this.ipEnd = ipEnd;
    }

    public IpRange(String uuid, String ipStart, String ipEnd) {
        this(null, uuid, ipStart, ipEnd);
    }

    public String getIpStart() {
        return ipStart;
    }

    public void setIpStart(String ipStart) {
        this.ipStart = ipStart;
    }

    public String getIpEnd() {
        return ipEnd;
    }

    public void setIpEnd(String ipEnd) {
        this.ipEnd = ipEnd;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}

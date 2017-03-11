package application.api.resource.ip;

import application.api.resource.NaturallyIdentifiableResource;
import application.domain.ipRange.IpRange;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IpRangeResource extends NaturallyIdentifiableResource<IpRange>{

    public IpRangeResource() {
        super(new IpRange());
    }

    /**
     * Constructor for the class.
     *
     * @param object
     */
    public IpRangeResource(IpRange object) {
        super(object);
    }

    @JsonProperty("ipRangeStart")
    public String getRangeStart() {
        return object.getIpStart();
    }

    @JsonProperty("ipRangeStart")
    public void setRangeStart(String start) {
        object.setIpStart(start);
    }

    @JsonProperty("ipRangeEnd")
    public void setRangeEnd(String end) {
        object.setIpEnd(end);
    }

    @JsonProperty("ipRangeEnd")
    public String getRangeEnd() {
        return object.getIpEnd();
    }
}

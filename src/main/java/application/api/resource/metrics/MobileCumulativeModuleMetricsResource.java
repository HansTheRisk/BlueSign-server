package application.api.resource.metrics;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.BaseResource;
import application.domain.metrics.MobileCumulativeModuleMetrics;

public class MobileCumulativeModuleMetricsResource extends BaseResource<MobileCumulativeModuleMetrics> {
    public MobileCumulativeModuleMetricsResource(MobileCumulativeModuleMetrics object) {
        super(object);
    }

    @JsonProperty("moduleCode")
    public String getModuleCode() {
        return object.getModuleCode();
    }

    @JsonProperty("totalToDate")
    public long getTotalToDate() {
        return object.getTotalToDate();
    }

    @JsonProperty("totalAttended")
    public long getTotalAttended() {
        return object.getTotalAttended();
    }
}
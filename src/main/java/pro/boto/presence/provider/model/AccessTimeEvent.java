package pro.boto.presence.provider.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AccessTimeEvent {

    @JsonProperty("eventId")
    private String eventId;

    @JsonProperty("eventTime")
    private Long eventTime;

    @JsonProperty("employee")
    private String employee;

    @JsonProperty("control")
    private String control;

    private AccessTimeEvent() { }

    public String eventId() {
        return eventId;
    }

    public Long eventTime() {
        return eventTime;
    }

    public String employee() {
        return employee;
    }

    public String control() {
        return control;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("eventId", eventId)
                .append("eventTime", eventTime)
                .append("employee", employee)
                .append("control", control)
                .toString();
    }
}

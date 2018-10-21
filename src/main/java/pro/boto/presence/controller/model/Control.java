package pro.boto.presence.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Control {

    @JsonProperty
    private String type;
    @JsonProperty
    private LocalTime time;


    public Control withTime(LocalTime time) {
        this.time = time;
        return this;
    }

    public Control withType(String type) {
        this.type = type;
        return this;
    }
}

package pro.boto.presence.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Presence {

    @JsonProperty
    private LocalDate date;
    @JsonProperty
    private LocalTime workedTime;
    @JsonProperty
    private Boolean insideOffice;
    @JsonProperty
    private List<Control> controls;


    public Presence withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Presence withWorkedTime(LocalTime time) {
        this.workedTime = time;
        return this;
    }

    public Presence withInsideOffice(boolean insideOffice) {
        this.insideOffice = insideOffice;
        return this;
    }

    public Presence withControls(List<Control> controls) {
        this.controls = controls;
        return this;
    }

}

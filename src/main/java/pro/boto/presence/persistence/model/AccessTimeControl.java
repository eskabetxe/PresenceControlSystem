package pro.boto.presence.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "AccessTimeControl")
public class AccessTimeControl {

    @Id
    private String id;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    private String employee;

    @NotNull
    private String control;

    private AccessTimeControl() {}

    public AccessTimeControl(String id, LocalDateTime timestamp, String employee, String control) {
        this.id = id;
        this.employee = employee;
        this.timestamp = timestamp;
        this.control = control;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getEmployee() {
        return employee;
    }

    public String getControl() {
        return control;
    }
}

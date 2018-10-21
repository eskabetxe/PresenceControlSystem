package pro.boto.presence.service.access.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AccessControl {

    private LocalDate date;
    private LocalTime timeWorked;
    private boolean insideOffice;
    private List<TimeControl> control;

    private AccessControl() { }

    public LocalDate getDate() {
        return date;
    }

    public List<TimeControl> getControl() {
        return control;
    }

    public LocalTime getTimeWorked() {
        return timeWorked;
    }

    public boolean isInsideOffice() {
        return insideOffice;
    }

    public static class Builder {
        private AccessControl access;

        private Builder(){
            access = new AccessControl();
            access.control = new ArrayList<>();
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withDate(LocalDate date) {
            access.date = date;
            return this;
        }

        public Builder withControls(List<TimeControl> controls) {
            if (controls == null) return this;
            access.control = controls;
            return this;
        }

        public Builder addControl(TimeControl control) {
            if (control == null) return this;
            access.control.add(control);
            return this;
        }

        private void calculateValues() {
            long worked = 0L;
            long currentIn = 0L;
            for (TimeControl control : access.control) {
                switch (control.getType()) {
                    case IN: {
                        currentIn = control.getTime().toNanoOfDay();
                        access.insideOffice = true;
                        break;
                    }
                    case OUT: {
                        worked += control.getTime().toNanoOfDay() - currentIn;
                        currentIn = 0L;
                        access.insideOffice = false;
                        break;
                    }
                }
            }


            access.timeWorked = LocalTime.ofNanoOfDay(worked);
        }

        public AccessControl build() {
            calculateValues();
            return access;
        }
    }


}

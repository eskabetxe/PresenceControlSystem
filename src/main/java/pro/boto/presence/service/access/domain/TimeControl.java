package pro.boto.presence.service.access.domain;

import java.time.LocalTime;

public class TimeControl {

    public enum Type {IN,OUT}

    private Type type;

    private LocalTime time;

    protected TimeControl() {}

    public Type getType() {
        return type;
    }

    public LocalTime getTime() {
        return time;
    }


    public static class Builder {
        private TimeControl access;

        private Builder(){
            access = new TimeControl();
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withType(Type control) {
            access.type = control;
            return this;
        }

        public Builder withTime(LocalTime time) {
            access.time = time;
            return this;
        }

        public TimeControl build() {
            return access;
        }
    }

}

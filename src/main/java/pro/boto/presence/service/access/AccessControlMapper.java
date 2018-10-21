package pro.boto.presence.service.access;

import pro.boto.presence.persistence.model.AccessTimeControl;
import pro.boto.presence.provider.model.AccessTimeEvent;
import pro.boto.presence.service.access.domain.AccessControl;
import pro.boto.presence.service.access.domain.TimeControl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

final class AccessControlMapper {

    private AccessControlMapper() {}

    static List<AccessControl> mapFrom(List<AccessTimeControl> controls) {
        return mapAccessControl(controls);

    }

    private static List<AccessControl> mapAccessControl(List<AccessTimeControl> controls) {

        Map<LocalDate, AccessControl.Builder> accesses = new HashMap<>();

        controls.forEach(control -> {
            LocalDateTime dateTime = control.getTimestamp();

            LocalDate date = dateTime.toLocalDate();

            if (!accesses.containsKey(date)) {
                accesses.put(date, AccessControl.Builder.create()
                                            .withDate(date));
            }

            accesses.get(date)
                    .addControl(TimeControl.Builder.create()
                            .withTime(dateTime.toLocalTime())
                            .withType(mapControlType(control.getControl()))
                            .build());

        });

        return accesses.values().stream()
                .map(AccessControl.Builder::build)
                .collect(Collectors.toList());

    }

    private static TimeControl.Type mapControlType(String control) {
        return TimeControl.Type.valueOf(control.toUpperCase());
    }

    public static AccessTimeControl mapAccessTimeControl(AccessTimeEvent event) {
        LocalDateTime timestamp = LocalDateTime
                .ofInstant(Instant.ofEpochMilli(event.eventTime()), TimeZone.getDefault().toZoneId());

        return new AccessTimeControl(event.eventId(), timestamp, event.employee(), event.control());
    }

}

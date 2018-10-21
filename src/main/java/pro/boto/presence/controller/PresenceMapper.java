package pro.boto.presence.controller;

import pro.boto.presence.controller.model.Control;
import pro.boto.presence.controller.model.Presence;
import pro.boto.presence.service.access.domain.AccessControl;
import pro.boto.presence.service.access.domain.TimeControl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

final class PresenceMapper {

    private PresenceMapper() {}

    static Presence mapToPresence(List<AccessControl> access) {
        Presence presence = new Presence();
        if(access != null && !access.isEmpty()){
            AccessControl control = access.get(0);
            presence = presence
                    .withInsideOffice(control.isInsideOffice())
                    .withWorkedTime(control.getTimeWorked());
        }

        return presence;
    }

    static List<Presence> mapToWorked(List<AccessControl> access) {
        List<Presence> control = new ArrayList<>();

        if(access !=null && !access.isEmpty()) {
            control = access.stream()
                    .map(ac -> new Presence().withDate(ac.getDate()).withWorkedTime(ac.getTimeWorked()))
                    .collect(Collectors.toList());
        }

        return control;
    }

    static List<Presence> mapToControl(List<AccessControl> access) {
        List<Presence> control = new ArrayList<>();

        if(access !=null && !access.isEmpty()) {
            control = access.stream()
                    .map(ac -> new Presence()
                            .withDate(ac.getDate())
                            .withWorkedTime(ac.getTimeWorked())
                            .withControls(mapControls(ac.getControl())))
                    .collect(Collectors.toList());
        }

        return control;
    }

    private static List<Control> mapControls(List<TimeControl> controls) {
        return controls.stream()
                .map(control -> new Control().withTime(control.getTime()).withType(control.getType().name()))
                .collect(Collectors.toList());
    }
}

package pro.boto.presence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.boto.presence.controller.model.Presence;
import pro.boto.presence.service.access.AccessControlService;
import pro.boto.presence.service.access.domain.AccessControl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class PresenceController {

    private AccessControlService controlService;

    @Autowired
    public PresenceController(AccessControlService controlService) {
        this.controlService = controlService;
    }

    @GetMapping(value = "/presence/{employee}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Presence employee(@PathVariable String employee,
                             @RequestParam(name = "day", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate day) {

        LocalDate date = checkDate(day, LocalDate.now());

        List<AccessControl> access = controlService.accessControl(employee, date);

        return PresenceMapper.mapToPresence(access);
    }

    @GetMapping(value = "/worked/{employee}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Presence> worked(@PathVariable String employee,
                                 @RequestParam(name = "dayFrom", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dayFrom,
                                 @RequestParam(name = "dayTo", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dayTo) {

        LocalDate fromDay = checkDate(dayFrom, LocalDate.now().minusDays(7));
        LocalDate toDay = checkDate(dayTo, LocalDate.now());

        List<AccessControl> access = controlService.accessControl(employee, fromDay, toDay);

        return PresenceMapper.mapToWorked(access);
    }

    @GetMapping(value = "/controls/{employee}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Presence> controls(@PathVariable String employee,
                                   @RequestParam(name = "dayFrom", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dayFrom,
                                   @RequestParam(name = "dayTo", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dayTo) {

        LocalDate fromDay = checkDate(dayFrom, LocalDate.now().minusMonths(1));
        LocalDate toDay = checkDate(dayTo, LocalDate.now());

        List<AccessControl> access = controlService.accessControl(employee, fromDay, toDay);

        return PresenceMapper.mapToControl(access);
    }


    private LocalDate checkDate(LocalDate toCheck, LocalDate defaultDate) {
        return Optional.ofNullable(toCheck).orElse(defaultDate);
    }


}

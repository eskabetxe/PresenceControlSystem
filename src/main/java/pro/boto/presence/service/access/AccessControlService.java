package pro.boto.presence.service.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.boto.presence.persistence.AccessTimeControlRepository;
import pro.boto.presence.persistence.model.AccessTimeControl;
import pro.boto.presence.provider.model.AccessTimeEvent;
import pro.boto.presence.service.access.domain.AccessControl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

@Service
public class AccessControlService {

    private AccessTimeControlRepository controlRepository;

    @Autowired
    public AccessControlService(AccessTimeControlRepository controlRepository) {
        this.controlRepository = controlRepository;
    }

    public List<AccessControl> accessControl(String employee, LocalDate date) {
        return accessControl(employee, date, date);
    }

    public List<AccessControl> accessControl(String employee, LocalDate fromDate, LocalDate toDate) {
        List<AccessTimeControl> controls = search(employee, fromDate, toDate);

        return AccessControlMapper.mapFrom(controls);
    }

    private List<AccessTimeControl> search(String employee, LocalDate fromDate, LocalDate toDate) {
        return controlRepository.findByEmployeeAndDate(employee, fromDate.atStartOfDay(), toDate.plusDays(1).atStartOfDay());
    }

    @KafkaListener(topics = "presence")
    @Transactional
    protected void saveEvent(AccessTimeEvent event) {
        if (event == null) return;

        AccessTimeControl control = AccessControlMapper.mapAccessTimeControl(event);
        controlRepository.saveAndFlush(control);
    }

}

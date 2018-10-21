package pro.boto.presence.service.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.boto.presence.service.access.AccessControlService;
import pro.boto.presence.service.access.domain.AccessControl;
import pro.boto.presence.service.employee.EmployeeService;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

@EnableScheduling
@Service
public class NotificationService {

    private final Long HOUR40 = 40 * 3600000L;

    private EmployeeService employeeService;
    private AccessControlService accessControlService;
    private NotificationProvider notificationProvider;

    @Autowired
    public NotificationService(EmployeeService employeeService,
                               AccessControlService accessControlService,
                               NotificationProvider notificationProvider) {
        this.accessControlService = accessControlService;
        this.employeeService = employeeService;
        this.notificationProvider = notificationProvider;
    }

    @Scheduled(cron = "0 0 10 * * SUN")
    public void reportCurrentTime() {
        employeeService.activeEmployees()
                .forEach(this::checkWeekWork);
    }

    private void checkWeekWork(String employee) {
        LocalDate date = LocalDate.now();

        Long workedTimeWeek = accessControlService.accessControl(employee, date.minusWeeks(1), date).stream()
                .map(AccessControl::getTimeWorked)
                .map(time -> time.getLong(ChronoField.MILLI_OF_DAY))
                .reduce((t1, t2) -> t1 + t2)
                .orElse(0L);

        if(HOUR40 > workedTimeWeek) {
            notificationProvider.sentNotification(employee, workedTimeWeek);
        }
    }

}

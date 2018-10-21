package pro.boto.presence.service.notifications;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import pro.boto.presence.persistence.AccessTimeControlRepository;
import pro.boto.presence.persistence.model.AccessTimeControl;
import pro.boto.presence.service.access.AccessControlService;
import pro.boto.presence.service.employee.EmployeeService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
public class NotificationServiceTest {

    private final String USER = "jboto";

    @Test
    public void testNotification() throws Exception {
        AccessTimeControlRepository controlRepository = Mockito.mock(AccessTimeControlRepository.class);
        Mockito.when(controlRepository.findByEmployeeAndDate(Mockito.eq(USER), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(create());

        NotificationProvider notificationProvider = Mockito.mock(NotificationProvider.class);
        NotificationService notificationService = new NotificationService(new EmployeeService(), new AccessControlService(controlRepository),notificationProvider);


        notificationService.reportCurrentTime();

        Mockito.verify(notificationProvider, Mockito.times(1)).sentNotification(Mockito.eq(USER),Mockito.anyLong());
    }

    private List<AccessTimeControl> create() throws Exception {
        LocalDateTime time = LocalDateTime.now();
        return Arrays.asList(
                new AccessTimeControl(UUID.randomUUID().toString(), time.minusHours(4), USER,"in"),
                new AccessTimeControl(UUID.randomUUID().toString(), time, USER,"out")
        );
    }
}

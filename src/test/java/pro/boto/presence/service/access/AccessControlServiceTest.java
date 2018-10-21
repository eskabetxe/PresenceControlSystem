package pro.boto.presence.service.access;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import pro.boto.presence.persistence.AccessTimeControlRepository;
import pro.boto.presence.persistence.model.AccessTimeControl;
import pro.boto.presence.service.access.AccessControlService;
import pro.boto.presence.service.access.domain.AccessControl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AccessControlServiceTest {

    private final String USER = "jboto";

    @Test
    public void testAccessControl() throws Exception {
        AccessTimeControlRepository repository = Mockito.mock(AccessTimeControlRepository.class);
        Mockito.when(repository.findByEmployeeAndDate(Mockito.eq(USER), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(create());

        AccessControlService service = new AccessControlService(repository);

        List<AccessControl> accessControls = service.accessControl(USER, LocalDate.now());

        Assertions.assertEquals(1, accessControls.size());
        Assertions.assertEquals(2, accessControls.get(0).getControl().size());
        Assertions.assertEquals(LocalTime.of(4,0), accessControls.get(0).getTimeWorked());
    }


    private List<AccessTimeControl> create() throws Exception {
        LocalDateTime time = LocalDateTime.now();
        return Arrays.asList(
                new AccessTimeControl(UUID.randomUUID().toString(), time.minusHours(4), USER,"in"),
                new AccessTimeControl(UUID.randomUUID().toString(), time, USER,"out")
        );
    }
}

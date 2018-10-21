package pro.boto.presence.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.boto.presence.persistence.AccessTimeControlRepository;
import pro.boto.presence.persistence.model.AccessTimeControl;
import pro.boto.presence.service.access.AccessControlService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PresenceController.class,AccessControlService.class,AccessTimeControlRepository.class})
@WebMvcTest(PresenceController.class)
public class PresenceControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String USER = "jboto";

    @MockBean
    AccessTimeControlRepository repository;

    @Test
    public void testPresence() throws Exception {
        Mockito.when(repository.findByEmployeeAndDate(Mockito.eq(USER), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(create());

        mvc.perform(MockMvcRequestBuilders.get("/presence/"+USER).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workedTime", is("04:00:00")))
                .andExpect(jsonPath("$.insideOffice", is(false)))
        ;
    }

    @Test
    public void testWorked() throws Exception {
        Mockito.when(repository.findByEmployeeAndDate(Mockito.eq(USER), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(create());

        mvc.perform(MockMvcRequestBuilders.get("/worked/"+USER).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[0].workedTime", is("04:00:00")))
        ;
    }

    @Test
    public void testControls() throws Exception {
        Mockito.when(repository.findByEmployeeAndDate(Mockito.eq(USER), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(create());

        mvc.perform(MockMvcRequestBuilders.get("/controls/"+USER).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[0].workedTime", is("04:00:00")))
                .andExpect(jsonPath("$[0].controls", hasSize(2)))
                .andExpect(jsonPath("$[0].controls[0].type", is("IN")))
                .andExpect(jsonPath("$[0].controls[1].type", is("OUT")))
        ;

    }

    private List<AccessTimeControl> create() throws Exception {
        LocalDateTime time = LocalDateTime.now();
        return Arrays.asList(
                new AccessTimeControl(UUID.randomUUID().toString(), time.minusHours(4), USER,"in"),
                new AccessTimeControl(UUID.randomUUID().toString(), time, USER,"out")
        );
    }

}



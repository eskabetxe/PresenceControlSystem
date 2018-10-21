package pro.boto.presence.service.employee;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    public List<String> activeEmployees() {
        // this should connect to employees service to get the active employees
        return Arrays.asList("jboto");
    }

}

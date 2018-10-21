package pro.boto.presence.service.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationProvider {

    private static final Logger log = LoggerFactory.getLogger(NotificationProvider.class);

    public void sentNotification(String employee, Long workedTimeWeek){
        // this could sent the information to a new Kafka topic or simple sent an email notification
        log.info("the employee {} this week only worked {} ", employee, workedTimeWeek);
    }

}

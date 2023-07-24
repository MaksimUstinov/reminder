package ampus.domain.notifications;


import ampus.domain.notifications.entity.UserNotification;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.stereotype.Service;

@Service
public class NotificationJobFactory {

    public static JobDetail buildJob(UserNotification notification) {
        return JobBuilder
                .newJob()
                .ofType(EmailSender.class)
                .build();
    }
}

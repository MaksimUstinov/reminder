package ampus.domain.notifications;

import ampus.domain.notifications.entity.UserNotification;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.*;

import static org.quartz.TriggerBuilder.newTrigger;


@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationSenderJob implements Job, Listener<UserNotification> {

    private final UserNotificationGateway userNotificationGateway;

    private final Scheduler scheduler;
    private LocalDateTime currentStart;
    private LocalDateTime currentEnd;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Uploading notifications starting......");
        updateCurrentPeriod();
        var oneHourNotifications = userNotificationGateway.findAll(currentStart, currentEnd);
        oneHourNotifications.forEach(this::scheduleNotificationJob);
        log.info("Notification schedule planed.....");
    }

    private void scheduleNotificationJob(UserNotification userNotification) {
        try {
            scheduler
                    .scheduleJob(
                            NotificationJobFactory.buildJob(userNotification),
                            newTrigger()
                                    .startAt(
                                            Date.from(userNotification.getNotifyAt().toInstant(ZoneOffset.UTC))
                                    ).build());
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserNotification newUserNotification) {
        scheduleNotificationJob(newUserNotification);
    }

    private synchronized void updateCurrentPeriod() {
        currentStart = LocalDateTime.now(ZoneId.of(ZoneOffset.UTC.getId()));
        currentEnd = currentStart.plusHours(1L);
        log.info("Period set up from " + currentStart + " to " + currentEnd);
    }
}

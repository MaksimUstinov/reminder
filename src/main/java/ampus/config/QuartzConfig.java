package ampus.config;

import ampus.domain.notifications.NotificationSenderJob;
import ampus.domain.notifications.UserNotificationGateway;
import ampus.domain.notifications.entity.UserNotification;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
@Slf4j
public class QuartzConfig {

    @PostConstruct
    public void init() {
        log.info("Settings for Quartz dine....");
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory(ApplicationContext applicationContext) {
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean factory) throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
       var job = newJob().ofType(NotificationSenderJob.class)
                .storeDurably()
                .withIdentity(JobKey.jobKey("upload_"))
                .withDescription("Invoke Sample Job service...").build();

        int frequencyInHour = 1;

        var trigger = newTrigger()
                .forJob(job)
                .withIdentity(TriggerKey.triggerKey("one_hour_trigger"))
                .withSchedule(simpleSchedule().withIntervalInHours(frequencyInHour)
                        .repeatForever()).build();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
        return scheduler;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(springBeanJobFactory(applicationContext));
        return factory;
    }
}

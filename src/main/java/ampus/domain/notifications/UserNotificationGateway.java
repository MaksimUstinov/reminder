package ampus.domain.notifications;

import ampus.domain.notifications.in.dto.SearchNotificationsDto;
import ampus.domain.notifications.entity.UserNotification;
import ampus.domain.notifications.out.dto.SearchNotificationResultDto;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Validated
public interface UserNotificationGateway {

    void delete(List<Long> id);
    UserNotification create(UserNotification userNotification);
    SearchNotificationResultDto find(SearchNotificationsDto searchNotificationsDto);
    List<UserNotification> findAll(LocalDateTime from, LocalDateTime to);
}

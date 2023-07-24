package ampus.api.response;

import ampus.domain.notifications.entity.UserNotification;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserNotificationResponse {

    public UserNotificationResponse(UserNotification userNotification) {
        this.id = userNotification.getId();
        this.notifyAt = userNotification.getNotifyAt();
        this.title = userNotification.getTitle();
        this.description = userNotification.getDescription();
    }

    private final Long id;
    private final String title;
    private final String description;
    private final LocalDateTime notifyAt;

}

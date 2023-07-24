package ampus.db.mappers;

import ampus.db.entities.Notification;
import ampus.db.entities.User;
import ampus.domain.notifications.entity.UserNotification;

import java.time.ZoneId;

public class DomainEntityMapper {

    public Notification toDomainEntity(User user, UserNotification userNotification) {
        return Notification
                .builder()
                .user(user)
                .title(userNotification.getTitle())
                .description(userNotification.getDescription())
                .remind(userNotification.getNotifyAt())
                .build();
    }

    public UserNotification toDomainEntity(User user, Notification notification) {
        return UserNotification
                .builder()
                .userName(user.getUserName())
                .id(notification.getId())
                .title(notification.getTitle())
                .description(notification.getDescription())
                .notifyAt(notification.getRemind())
                .build();
    }
}

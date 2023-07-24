package ampus.domain.notifications.in;

import ampus.domain.notifications.in.dto.CreateNotificationDto;

public interface NotificationCreator {

    void create(CreateNotificationDto createNotificationDto);
}

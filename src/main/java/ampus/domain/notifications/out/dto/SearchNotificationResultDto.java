package ampus.domain.notifications.out.dto;

import ampus.domain.notifications.entity.UserNotification;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchNotificationResultDto {

    private long current;
    private long total;
    private List<UserNotification> notifications;
}

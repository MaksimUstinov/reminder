package ampus.domain.notifications.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserNotification {

    private Long id;
    private String userName;
    private String title;
    private String description;
    private LocalDateTime notifyAt;

}

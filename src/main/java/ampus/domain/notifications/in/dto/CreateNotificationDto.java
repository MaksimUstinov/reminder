package ampus.domain.notifications.in.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Builder
@Getter
public class CreateNotificationDto {

    private Long id;
    private String userName;
    private String title;
    private String description;
    private LocalDateTime notifyAt;
}

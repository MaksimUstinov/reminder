package ampus.domain.notifications.in.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DeleteNotificationDto {

    private List<Long> ids;
}

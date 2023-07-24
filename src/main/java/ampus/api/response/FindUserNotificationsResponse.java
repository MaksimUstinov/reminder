package ampus.api.response;

import ampus.domain.notifications.out.dto.SearchNotificationResultDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FindUserNotificationsResponse {

    public FindUserNotificationsResponse(SearchNotificationResultDto searchNotificationResultDto){
        this.current = searchNotificationResultDto.getCurrent();
        this.total = searchNotificationResultDto.getTotal();
        this.notifications = searchNotificationResultDto
                .getNotifications().stream().map(UserNotificationResponse::new)
                .collect(Collectors.toList());
    }

    @Schema(description = "Общее количество страниц")
    private final long total;

    @Schema(description = "Номер текущей страницы")
    private final long current;

    @Schema(description = "Напоминания пользователя")
    private final List<UserNotificationResponse> notifications;
}

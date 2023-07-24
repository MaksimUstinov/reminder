package ampus.api;


import ampus.api.requests.CreateNotificationRequest;
import ampus.api.requests.ReceiveNotificationsRequest;
import ampus.api.requests.SortParam;
import ampus.api.response.FindUserNotificationsResponse;
import ampus.domain.notifications.in.NotificationCreator;
import ampus.domain.notifications.in.NotificationDeleter;
import ampus.domain.notifications.in.NotificationSearcher;
import ampus.domain.notifications.in.dto.CreateNotificationDto;
import ampus.domain.notifications.in.dto.SearchNotificationsDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/domain/api/v1/reminder")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationSearcher notificationSearcher;
    private final NotificationCreator notificationCreator;
    private final NotificationDeleter notificationDeleter;

    @RequestMapping(method = RequestMethod.PUT, value = "/create")
    public void createNotification(
            @AuthenticationPrincipal OAuth2User user,
            @RequestBody CreateNotificationRequest createNotificationRequest) {
        notificationCreator.create(CreateNotificationDto
                .builder()
                .userName((String) user.getAttributes().get("login"))
                .title(createNotificationRequest.getTitle())
                .description(createNotificationRequest.getDescription())
                .notifyAt(createNotificationRequest.getNotifyAt().toLocalDateTime())
                .build());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public void deleteNotification(@RequestBody List<Long> notificationIds) {

    }


    @RequestMapping(method = RequestMethod.POST, value = "/find")
    public FindUserNotificationsResponse find(
            @AuthenticationPrincipal OAuth2User user,
            @RequestBody ReceiveNotificationsRequest receiveNotificationsRequest) {
        return new FindUserNotificationsResponse(notificationSearcher.search(SearchNotificationsDto
                .builder()
                .username((String) user.getAttributes().get("login"))
                .to(receiveNotificationsRequest.getTo())
                .from(receiveNotificationsRequest.getFrom())
                .page(receiveNotificationsRequest.getPage())
                .pageSize(receiveNotificationsRequest.getPageSize())
                .sortBy(receiveNotificationsRequest.getSortBy().stream().map(SortParam::value).collect(Collectors.toList()))
                .build()));
    }
}

package ampus.domain.notifications;

import ampus.domain.notifications.in.dto.CreateNotificationDto;
import ampus.domain.notifications.in.dto.SearchNotificationsDto;
import ampus.domain.notifications.entity.UserNotification;
import ampus.domain.notifications.in.NotificationCreator;
import ampus.domain.notifications.in.NotificationDeleter;
import ampus.domain.notifications.in.NotificationSearcher;
import ampus.domain.notifications.out.dto.SearchNotificationResultDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationsHandler implements
        NotificationSearcher,
        NotificationDeleter,
        NotificationCreator, Publisher<UserNotification> {

    private final DateTimeVerifier dateTimeVerifier;
    private final UserNotificationGateway userNotificationGateway;

    private final List<Listener<UserNotification>> newNotificationEventListeners;

    @Override
    public void create(CreateNotificationDto createNotificationDto) {
        dateTimeVerifier.checkIsNotificationNotInPast(createNotificationDto.getNotifyAt());
        var newNotification = userNotificationGateway.create(UserNotification
                .builder()
                .userName(createNotificationDto.getUserName())
                .title(createNotificationDto.getTitle())
                .description(createNotificationDto.getDescription())
                .notifyAt(createNotificationDto.getNotifyAt())
                .build());
        newNotificationEventListeners.forEach(listener -> listener.update(newNotification));
    }

    @Override
    public void delete(List<Long> ids) {
        userNotificationGateway.delete(ids);
    }

    @Override
    public SearchNotificationResultDto search(SearchNotificationsDto dto) {
        return userNotificationGateway.find(
                SearchNotificationsDto.builder()
                        .from(Optional.ofNullable(dto.getFrom()).orElse(LocalDateTime.now()))
                        .to(Optional.ofNullable(dto.getTo()).orElse(LocalDateTime.now().plusDays(365)))
                        .username(dto.getUsername())
                        .page(Optional.ofNullable(dto.getPage()).orElse(0))
                        .pageSize(Optional.ofNullable(dto.getPageSize()).orElse(50))
                        .sortBy(ObjectUtils.isNotEmpty(dto.getSortBy()) ? dto.getSortBy() : List.of("remind"))
                        .build());
    }

    @Override
    public void subscribe(Listener<UserNotification> listener) {
        newNotificationEventListeners.add(listener);
    }
}

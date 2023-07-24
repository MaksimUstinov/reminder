package ampus.db;

import ampus.db.entities.User;
import ampus.db.mappers.DomainEntityMapper;
import ampus.db.reposirory.NotificationRepository;
import ampus.db.reposirory.UserRepository;
import ampus.domain.notifications.UserNotificationGateway;
import ampus.domain.notifications.entity.UserNotification;
import ampus.domain.notifications.in.dto.SearchNotificationsDto;
import ampus.domain.notifications.out.dto.SearchNotificationResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationGateway implements UserNotificationGateway {


    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final DomainEntityMapper domainEntityMapper = new DomainEntityMapper();

    @Override
    public void delete(List<Long> ids) {
        notificationRepository.deleteAllById(ids);
    }

    @Override
    public UserNotification create(UserNotification userNotification) {
        var user = getUser(userNotification.getUserName());
        return domainEntityMapper.toDomainEntity(user, notificationRepository
                .save(domainEntityMapper.toDomainEntity(user,
                        userNotification)));
    }

    @Override
    public SearchNotificationResultDto find(SearchNotificationsDto searchNotificationsDto) {
        var params = new String[searchNotificationsDto.getSortBy().size()];
        var pageRequest = PageRequest.of(
                searchNotificationsDto.getPage(),
                searchNotificationsDto.getPageSize(),
                Sort.Direction.ASC,
                searchNotificationsDto.getSortBy().toArray(params));
        var user = getUser(searchNotificationsDto.getUsername());
        var result = notificationRepository
                .findAllByRemindGreaterThanEqualAndRemindLessThanEqualAndUserIs(
                        searchNotificationsDto.getFrom(),
                        searchNotificationsDto.getTo(),
                        user,
                        pageRequest);

        return SearchNotificationResultDto.builder()
                .notifications(result.getContent()
                        .stream()
                        .map(dataBaseNotification -> domainEntityMapper.toDomainEntity(user, dataBaseNotification)).collect(Collectors.toList()))
                .total(result.getTotalPages())
                .current(result.getPageable().getPageNumber())
                .build();
    }

    @Override
    public List<UserNotification> findAll(LocalDateTime from, LocalDateTime to) {
        return notificationRepository
                .findAllByRemindGreaterThanEqualAndRemindLessThanEqual(from, to)
                .stream()
                .map(notification -> domainEntityMapper.toDomainEntity(notification.getUser(), notification)).collect(Collectors.toList());
    }

    private User getUser(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));
    }
}

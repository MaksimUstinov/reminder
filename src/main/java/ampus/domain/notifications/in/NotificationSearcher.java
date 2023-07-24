package ampus.domain.notifications.in;

import ampus.domain.notifications.in.dto.SearchNotificationsDto;
import ampus.domain.notifications.out.dto.SearchNotificationResultDto;

public interface NotificationSearcher {

    SearchNotificationResultDto search(SearchNotificationsDto dto);
}

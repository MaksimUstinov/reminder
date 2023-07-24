package ampus.domain.notifications.in;

import java.util.List;

public interface NotificationDeleter {

    void delete(List<Long> ids);
}

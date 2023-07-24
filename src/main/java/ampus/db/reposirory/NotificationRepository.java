package ampus.db.reposirory;

import ampus.db.entities.Notification;
import ampus.db.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findAllByRemindGreaterThanEqualAndRemindLessThanEqualAndUserIs(LocalDateTime from, LocalDateTime to, User user, Pageable pageable);
    List<Notification> findAllByRemindGreaterThanEqualAndRemindLessThanEqual(LocalDateTime from, LocalDateTime to);
}

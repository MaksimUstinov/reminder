package ampus.domain.notifications;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class DateTimeVerifier {

    private final static Long TIME_SHIFT_CAPACITY = 1L;

    public void checkIsNotificationNotInPast(LocalDateTime target) {
        if (LocalDateTime.now(ZoneId.of(ZoneOffset.UTC.getId())).plusMinutes(TIME_SHIFT_CAPACITY).isAfter(target)) {
            throw new RuntimeException("Установите время напоминания как минимум на 1 минуту");
        }
    }
}

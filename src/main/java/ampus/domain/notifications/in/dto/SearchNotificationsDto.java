package ampus.domain.notifications.in.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SearchNotificationsDto {

    private String username;
    private Integer page;
    private Integer pageSize;
    private LocalDateTime from;
    private LocalDateTime to;
    private List<String> sortBy;
}

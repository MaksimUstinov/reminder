package ampus.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveNotificationsRequest {

    @Schema(description = "Номер страницы")
    private Integer page = 0;

    @Schema(description = "Размер страницы")
    @Min(1)
    private Integer pageSize = 50;

    @Schema(description = "Время с ")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime from;

    @Schema(description = "Время по ")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime to;

    @Schema(description = "Параметры сортировки")
    private List<SortParam> sortBy;
}

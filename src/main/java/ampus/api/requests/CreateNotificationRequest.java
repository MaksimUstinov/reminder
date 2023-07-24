package ampus.api.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CreateNotificationRequest {

    @Max(255)
    @NotEmpty
    @Schema(description = "Заголовок напоминания", maximum = "255 символов")
    private String title;

    @Max(4096)
    @Schema(description = "Текст напоминания", maximum = "4096 символов")
    private String description;

    @Schema(description = "Время напоминания")
    private OffsetDateTime notifyAt;
}

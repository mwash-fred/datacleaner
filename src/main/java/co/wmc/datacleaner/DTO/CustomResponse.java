package co.wmc.datacleaner.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @ToString @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {
    private Integer httpStatus;
    private Object data;
    private String message;
    private String error;
}

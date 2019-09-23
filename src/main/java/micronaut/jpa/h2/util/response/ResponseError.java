package micronaut.jpa.h2.util.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError{

    private final String type = ResponseType.ERROR.getType();
    private Integer code;
    private String message;
    private String detail;
}

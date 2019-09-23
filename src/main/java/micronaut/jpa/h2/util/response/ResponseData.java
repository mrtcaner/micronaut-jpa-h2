package micronaut.jpa.h2.util.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData{

    private final String type = ResponseType.DATA.getType();
    private Object data;
}

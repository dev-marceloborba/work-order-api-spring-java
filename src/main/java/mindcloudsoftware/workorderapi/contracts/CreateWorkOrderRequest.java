package mindcloudsoftware.workorderapi.contracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateWorkOrderRequest {
    private String equipmentName;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate target;
}

package mindcloudsoftware.workorderapi.contracts;

import lombok.Getter;
import lombok.Setter;
import mindcloudsoftware.workorderapi.enums.EWorkOrderStatus;
import java.time.LocalDate;

@Getter
@Setter
public class WorkOrderResponse {
    private Long id;
    private String equipmentName;
    private String description;
    private EWorkOrderStatus workOrderStatus;
    private LocalDate createdAt;
    private LocalDate target;
}

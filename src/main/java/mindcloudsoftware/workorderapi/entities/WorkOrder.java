package mindcloudsoftware.workorderapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mindcloudsoftware.workorderapi.enums.EWorkOrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_orders")
@Getter
@Setter
public class WorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String equipmentName;
    private String description;
    private EWorkOrderStatus workOrderStatus;
    private LocalDate createdAt = LocalDate.now();
    private LocalDate target;

    public void checkIFItsLate(LocalDate currentDate) {
        if (currentDate.isAfter(target)) {
            workOrderStatus = EWorkOrderStatus.LATE;
        }
    }

    public void executeOrder() {
        workOrderStatus = EWorkOrderStatus.IN_EXECUTION;
    }

    public void finishWorkOrder() {
        workOrderStatus = EWorkOrderStatus.FINISHED;
    }
}

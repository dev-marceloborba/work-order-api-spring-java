package mindcloudsoftware.workorderapi.services;

import mindcloudsoftware.workorderapi.entities.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface WorkOrderService extends JpaRepository<WorkOrder, Long> {
}

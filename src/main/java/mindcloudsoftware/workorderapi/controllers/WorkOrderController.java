package mindcloudsoftware.workorderapi.controllers;

import mindcloudsoftware.workorderapi.contracts.CreateWorkOrderRequest;
import mindcloudsoftware.workorderapi.contracts.WorkOrderResponse;
import mindcloudsoftware.workorderapi.entities.WorkOrder;
import mindcloudsoftware.workorderapi.services.WorkOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.TypeToken;

@RestController
@RequestMapping(path = "/api/v1/work-orders")
public class WorkOrderController {
    private final WorkOrderService workOrderService;
    private final ModelMapper modelMapper;

    @Autowired
    public WorkOrderController(WorkOrderService workOrderService, ModelMapper modelMapper) {
        this.workOrderService = workOrderService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Long> createWorkOrder(@RequestBody CreateWorkOrderRequest request) {
        WorkOrder workOrder = new WorkOrder();
        modelMapper.map(request, workOrder);
        workOrder.executeOrder();
        workOrderService.save(workOrder);
        return ResponseEntity.ok(workOrder.getId());
    }

    @GetMapping
    public ResponseEntity<Iterable<WorkOrderResponse>> findAllOrders() {
        List<WorkOrder> workOrders = workOrderService.findAll();

        Type listType = new TypeToken<List<WorkOrderResponse>>() {}.getType();

        List<WorkOrderResponse> response = modelMapper.map(workOrders, listType);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkOrderResponse> updateOrder(@RequestParam("id") Long id, @RequestBody CreateWorkOrderRequest request) {
        WorkOrderResponse response = new WorkOrderResponse();
        Optional<WorkOrder> workOrder = workOrderService.findById(id);

        modelMapper.map(request, workOrder);
        modelMapper.map(workOrder, response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteWorkOrder(@RequestParam("id") Long id) {
        Optional<WorkOrder> workOrder = workOrderService.findById(id);
        workOrderService.delete(workOrder.get());
        return ResponseEntity.ok(workOrder.get().getId());
    }
}

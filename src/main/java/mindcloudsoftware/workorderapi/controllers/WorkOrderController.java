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
import java.util.List;

import org.modelmapper.TypeToken;
import org.springframework.web.client.ResourceAccessException;

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
        var workOrder = new WorkOrder();
        modelMapper.map(request, workOrder);
        workOrder.executeOrder();
        workOrderService.save(workOrder);
        return ResponseEntity.ok(workOrder.getId());
    }

    @GetMapping
    public ResponseEntity<Iterable<WorkOrderResponse>> findAllOrders() {
        var workOrders = workOrderService.findAll();

        Type listType = new TypeToken<List<WorkOrderResponse>>() {}.getType();

        List<WorkOrderResponse> response = modelMapper.map(workOrders, listType);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public  ResponseEntity<WorkOrderResponse> findOrderById(@PathVariable Long id) {
        var workOrder = workOrderService.findById(id).orElseThrow(() -> new ResourceAccessException("Ordem de serviço não existe" + id));
        Type responseType = new TypeToken<WorkOrderResponse>() {}.getType();

        return ResponseEntity.ok(modelMapper.map(workOrder, responseType));
    }

    @PutMapping("{id}")
    public ResponseEntity<WorkOrderResponse> updateOrder(@PathVariable Long id, @RequestBody CreateWorkOrderRequest request) {
        var workOrder = workOrderService.findById(id).orElseThrow(() -> new ResourceAccessException("Ordem de serviço não existe" + id));
        modelMapper.map(request, workOrder);
        workOrderService.save(workOrder);
        Type responseType = new TypeToken<WorkOrderResponse>() {}.getType();

        return ResponseEntity.ok(modelMapper.map(workOrder, responseType));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> deleteWorkOrder(@PathVariable Long id) {
        var workOrder = workOrderService.findById(id).orElseThrow(() -> new ResourceAccessException("Ordem de serviço não existe" + id));
        workOrderService.delete(workOrder);
        return ResponseEntity.ok(workOrder.getId());
    }
}

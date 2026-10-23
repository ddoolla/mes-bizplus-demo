package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.material.input.MaterialInputService;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.work.order.dto.*;
import com.bizplus.mes.domain.work.order.facade.WorkCompleteService;
import com.bizplus.mes.domain.work.order.facade.WorkStartService;
import com.bizplus.mes.domain.work.order.facade.WorkStopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/work-orders")
@RequiredArgsConstructor
public class WorkOrderController {

    private final MessageService messageService;
    private final CommonCodeService commonCodeService;
    private final WorkOrderService workOrderService;

    private final WorkStartService workStartService;
    private final WorkStopService workStopService;
    private final WorkCompleteService workCompleteService;
    private final MaterialInputService materialInputService;

    @GetMapping
    @PreAuthorize("hasAuthority('WORK_ORDER_READ')")
    @UserAction(menu = MenuCode.WORK_ORDER, type = ActionType.READ)
    public String viewList(Model model,
                           WorkOrderSearchDto dto,
                           @PageableDefault Pageable pageable) {
        model.addAttribute("workOrderStatuses", WorkOrderListType.ACTIVE.getStatuses());
        model.addAttribute("data", workOrderService.getWorkOrders(WorkOrderListType.ACTIVE, dto, pageable));

        return "pages/work-order/list";
    }

    @GetMapping("/completed")
    @PreAuthorize("hasAuthority('WORK_ORDER_READ')")
    public String viewCompletedList(Model model,
                                    WorkOrderSearchDto dto,
                                    @PageableDefault Pageable pageable) {
        model.addAttribute("data", workOrderService.getWorkOrders(WorkOrderListType.COMPLETED, dto, pageable));

        return "pages/work-order/completed-list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('WORK_ORDER_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        WorkOrderDto workOrder = workOrderService.getWorkOrder(id).getWorkOrder();

        model.addAttribute("workOrder", workOrder);
        model.addAttribute("progressRate", workOrderService.getWorkOrder(id).getProgressRate());
        model.addAttribute("equipmentTypes", commonCodeService.getCommonCodes(CodeGroupKey.EQUIPMENT_TYPE));
        model.addAttribute("availableMaterials", materialInputService.getAvailableMaterials(id, workOrder.getProcess().routingProcessId()));
        model.addAttribute("materialInputs", materialInputService.getAll(id));

        return "pages/work-order/detail";
    }

    @PatchMapping("/{id}/start")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> startWork(@PathVariable Long id) {
        workStartService.start(id);

        return ResponseEntity.ok(ApiResponse.success("작업을 시작합니다."));
    }

    @PatchMapping("/{id}/equipment")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> changeEquipment(@PathVariable Long id, @RequestBody Long equipmentId) {
        workOrderService.changeEquipment(id, equipmentId);

        return ResponseEntity.ok(ApiResponse.success("작업 설비가 설정되었습니다."));
    }

    @PatchMapping("/{id}/stop")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> stopWork(@PathVariable Long id, @RequestBody WorkStopDto dto) {
        workStopService.stop(id, dto);

        return ResponseEntity.ok(ApiResponse.success("작업을 중지합니다."));
    }

    @PatchMapping("/{id}/resume")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> resumeWork(@PathVariable Long id) {
        workOrderService.resumeWork(id);

        return ResponseEntity.ok(ApiResponse.success("작업을 재개합니다."));
    }

    @PatchMapping("/{id}/complete")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> completeWork(@PathVariable Long id, @Valid WorkCompleteDto dto) {
        workCompleteService.complete(id, dto);

        return ResponseEntity.ok(ApiResponse.success("작업이 완료되었습니다."));
    }

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('WORK_ORDER_CREATE')")
    @UserAction(menu = MenuCode.WORK_ORDER, type = ActionType.CREATE)
    public ResponseEntity<ApiResponse<Void>> createWorkOrders(@RequestBody @Valid WorkOrderCreateDto dto) {
        workOrderService.createWorkOrders(dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @DeleteMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('WORK_ORDER_DELETE')")
    @UserAction(menu = MenuCode.WORK_ORDER, type = ActionType.DELETE)
    public ResponseEntity<ApiResponse<Void>> deleteWorkOrders(@RequestBody List<Long> ids) {
        workOrderService.deleteWorkOrders(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}

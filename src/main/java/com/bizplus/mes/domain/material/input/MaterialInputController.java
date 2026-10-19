package com.bizplus.mes.domain.material.input;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/material-inputs")
@RequiredArgsConstructor
public class MaterialInputController {

    private final MaterialInputService materialInputService;

    @GetMapping("/modal/input/material/selection")
    public String viewMaterialSelectionModal(Model model,
                                             @RequestParam Long workOrderId,
                                             @RequestParam Long routingProcessId) {
        model.addAttribute(
                "consumptionMaterials",
                materialInputService.getConsumptionMaterials(workOrderId, routingProcessId)
        );

        return "pages/material-input/modal/input/material-selection :: list";
    }

    @GetMapping("/modal/input/material-lot/selection")
    public String viewMaterialLotSelectionModal() {
        return "pages/material-input/modal/input/material-lot-selection :: list";
    }

}

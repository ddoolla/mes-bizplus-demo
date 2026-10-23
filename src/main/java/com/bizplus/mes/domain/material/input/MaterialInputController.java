package com.bizplus.mes.domain.material.input;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/material-inputs")
@RequiredArgsConstructor
public class MaterialInputController {

    private final MaterialInputService materialInputService;
}

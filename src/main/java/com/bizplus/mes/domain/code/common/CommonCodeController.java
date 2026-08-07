package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.code.common.dto.CommonCodeCreateDto;
import com.bizplus.mes.domain.code.common.dto.CommonCodeDto;
import com.bizplus.mes.domain.code.common.dto.CommonCodeUpdateDto;
import com.bizplus.mes.domain.code.group.CodeGroupService;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommonCodeController {

    private final CodeGroupService codeGroupService;
    private final CommonCodeService commonCodeService;
    private final MessageService messageService;

    @GetMapping("/code-groups/{groupId}/codes")
    @PreAuthorize("hasAuthority('COMMON_CODE_READ')")
    public String viewList(Model model,
                           @PathVariable Long groupId,
                           @RequestParam(required = false) String code,
                           @RequestParam(required = false) String name) {

        model.addAttribute("codeGroup", codeGroupService.getCodeGroup(groupId));
        model.addAttribute("commonCodes", commonCodeService.getCommonCodes(groupId, code, name));

        return "pages/common-code/list";
    }

    @GetMapping("/common-codes/{id}")
    @ResponseBody
    public CommonCodeDto readCommonCode(@PathVariable Long id) {

        return commonCodeService.getCommonCode(id);
    }

    @GetMapping("/common-codes/check-code")
    @ResponseBody
    public boolean checkCode(@RequestParam Long groupId,
                             @RequestParam(required = false) Long id,
                             @RequestParam String code) {

        return commonCodeService.checkCode(groupId, id, code);
    }

    @PostMapping("/code-groups/{groupId}/codes")
    @ResponseBody
    @PreAuthorize("hasAuthority('COMMON_CODE_CREATE')")
    @UserAction(menu = MenuCode.COMMON_CODE, type = ActionType.CREATE)
    public ResponseEntity<ApiResponse<Void>> createCommonCode(@PathVariable Long groupId,
                                                              @Valid CommonCodeCreateDto dto) {

        commonCodeService.createCommonCode(groupId, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.CREATED)));
    }

    @PutMapping("/common-codes/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('COMMON_CODE_UPDATE')")
    @UserAction(menu = MenuCode.COMMON_CODE, type = ActionType.UPDATE)
    public ResponseEntity<ApiResponse<Void>> updateCommonCode(@PathVariable Long id,
                                                              @Valid CommonCodeUpdateDto dto) {

        commonCodeService.updateCommonCode(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.UPDATED)));
    }

    @DeleteMapping("/common-codes")
    @UserAction(menu = MenuCode.COMMON_CODE, type = ActionType.UPDATE)
    public ResponseEntity<ApiResponse<Void>> deleteCommonCodes(@RequestBody List<Long> ids) {

        commonCodeService.deleteCommonCodes(ids);
        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}

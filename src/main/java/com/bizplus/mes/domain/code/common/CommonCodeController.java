package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.common.service.MessageService;
import com.bizplus.mes.domain.code.common.dto.CommonCodeCreateDto;
import com.bizplus.mes.domain.code.common.dto.CommonCodeDto;
import com.bizplus.mes.domain.code.common.dto.CommonCodeUpdateDto;
import com.bizplus.mes.domain.code.group.CodeGroupService;
import com.bizplus.mes.domain.code.group.dto.CodeGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommonCodeController {

    private final CommonCodeService commonCodeService;
    private final CodeGroupService codeGroupService;
    private final MessageService messageService;

    @GetMapping("/code-groups/{groupId}/common-codes")
    @PreAuthorize("hasAuthority('COMMON_CODE_READ')")
    public String viewList(Model model,
                           @PathVariable Long groupId,
                           @RequestParam(required = false) String code,
                           @RequestParam(required = false) String name) {

        CodeGroupDto codeGroup = codeGroupService.getCodeGroup(groupId);
        List<CommonCodeDto> commonCodes = commonCodeService.getCommonCodes(groupId, code, name);

        model.addAttribute("codeGroup", codeGroup);
        model.addAttribute("commonCodes", commonCodes);

        return "pages/common-code/list";
    }

    @PostMapping("/code-groups/{groupId}/common-codes")
    @PreAuthorize("hasAuthority('COMMON_CODE_CREATE')")
    public String createCommonCode(@PathVariable Long groupId,
                                   CommonCodeCreateDto dto,
                                   RedirectAttributes reAtt) {

        commonCodeService.createCommonCode(groupId, dto);

        reAtt.addAttribute("groupId", groupId);
        reAtt.addFlashAttribute("message", messageService.get("common.created"));

        return "redirect:/code-groups/{groupId}/common-codes";
    }

    @PostMapping("/code-groups/{groupId}/common-codes/{id}")
    @PreAuthorize("hasAuthority('COMMON_CODE_UPDATE')")
    public String updateCommonCode(@PathVariable Long groupId,
                                   @PathVariable Long id,
                                   CommonCodeUpdateDto dto,
                                   RedirectAttributes reAtt) {

        commonCodeService.updateCommonCode(id, dto);

        reAtt.addAttribute("groupId", groupId);
        reAtt.addFlashAttribute("message", messageService.get("common.updated"));

        return "redirect:/code-groups/{groupId}/common-codes";
    }

    @DeleteMapping("/common-codes")
    public ResponseEntity<ApiResponse<Void>> deleteCommonCodes(@RequestBody List<Long> ids) {

        commonCodeService.deleteCommonCodes(ids);
        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("common.deleted")));
    }
}

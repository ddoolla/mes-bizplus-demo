package com.bizplus.mes.domain.item;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.code.common.CommonCodeService;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.item.dto.ItemCreateDto;
import com.bizplus.mes.domain.item.dto.ItemListDto;
import com.bizplus.mes.domain.item.dto.ItemSearchDto;
import com.bizplus.mes.domain.item.dto.ItemUpdateDto;
import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.UserAction;
import com.bizplus.mes.domain.menu.MenuCode;
import com.bizplus.mes.domain.uom.UomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final UomService uomService;
    private final CommonCodeService commonCodeService;
    private final MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('ITEM_READ')")
    @UserAction(menu = MenuCode.ITEM, type = ActionType.READ)
    public String viewList(Model model,
                           ItemSearchDto dto,
                           @PageableDefault Pageable pageable) {
        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("selectedCategory", dto.getCategoryId());
        model.addAttribute("selectedType", dto.getType());
        model.addAttribute("data", itemService.getItems(dto, pageable));

        return "pages/item/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ITEM_READ')")
    public String viewDetail(Model model, @PathVariable Long id) {
        model.addAttribute("item", itemService.getItem(id));

        return "pages/item/detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ITEM_CREATE')")
    public String viewNew(Model model) {
        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("uoms", uomService.getUoms());

        return "pages/item/new";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ITEM_UPDATE')")
    public String viewEdit(Model model, @PathVariable Long id) {
        model.addAttribute("itemCategories", commonCodeService.getCommonCodes(CodeGroupKey.ITEM_CATEGORY));
        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("uoms", uomService.getUoms());
        model.addAttribute("item", itemService.getItem(id));

        return "pages/item/edit";
    }

    @GetMapping("/modal/list/single")
    public String viewSingleListModal(Model model,
                                      ItemSearchDto dto,
                                      @PageableDefault Pageable pageable) {
        model.addAttribute("data", getItemsByGroup(dto, pageable));

        return "pages/item/modal/list/single :: list";
    }

    @GetMapping("/modal/list/multiple")
    public String viewMultipleListModal(Model model,
                                        ItemSearchDto dto,
                                        @PageableDefault Pageable pageable) {
        model.addAttribute("data", getItemsByGroup(dto, pageable));

        return "pages/item/modal/list/multiple :: list";
    }

    private ItemListDto getItemsByGroup(ItemSearchDto dto, Pageable pageable) {
        ItemGroup group = dto.getGroup();

        if (group == null) {
            return itemService.getItems(dto, pageable);
        }

        return switch (group) {
            case PRODUCT -> itemService.getProducts(dto, pageable);
            case MATERIAL -> itemService.getMaterials(dto, pageable);
            case BOM_ITEM -> itemService.getBomItems(dto, pageable);
        };
    }

    /*
     * 논리 삭제된 코드도 중복으로 간주
     * */
    @GetMapping("/check-code")
    @ResponseBody
    public boolean checkCode(@RequestParam(required = false) Long id,
                             @RequestParam String code) {
        return itemService.checkCode(id, code);
    }

    @GetMapping("/check-lot-managed")
    @ResponseBody
    public boolean checkLotManaged(@RequestParam(required = false) Long id,
                                   @RequestParam boolean lotManaged) {
        return itemService.checkLotManage(id, lotManaged);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ITEM_CREATE')")
    @UserAction(menu = MenuCode.ITEM, type = ActionType.CREATE)
    public String createItem(@Valid ItemCreateDto dto, RedirectAttributes reAtt) {
        itemService.createItem(dto);

        reAtt.addFlashAttribute("message", messageService.get(MessageCode.CREATED));

        return "redirect:/items";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ITEM_UPDATE')")
    @UserAction(menu = MenuCode.ITEM, type = ActionType.UPDATE)
    public String updateItem(@PathVariable Long id,
                             @Valid ItemUpdateDto dto,
                             RedirectAttributes reAtt) {
        itemService.updateItem(id, dto);

        reAtt.addAttribute("id", id);
        reAtt.addFlashAttribute("message", messageService.get(MessageCode.UPDATED));

        return "redirect:/items/{id}";
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> deleteItems(@RequestBody List<Long> ids) {
        itemService.deleteItems(ids);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.DELETED)));
    }
}

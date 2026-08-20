package com.bizplus.mes.domain.routing;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.routing.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutingServiceImpl implements RoutingService {

    private final RoutingRepository routingRepository;

    private final ItemReader itemReader;
    private final RoutingReader routingReader;

    @Override
    public RoutingListDto getRoutings(RoutingSearchDto dto, Pageable pageable) {
        Page<RoutingDto> routingPage = routingRepository.findRoutings(dto, pageable);

        return new RoutingListDto(routingPage.getContent(), Pagination.of(routingPage));
    }

    @Override
    public RoutingDto getRouting(Long id) {
        return routingRepository.findRouting(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROUTING_NOT_FOUND, "id: " + id));
    }

    @Override
    public boolean checkRoutingCode(Long id, String code) {
        boolean exists = routingRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Transactional
    @Override
    public Long createRouting(RoutingCreateDto dto) {
        Item item = itemReader.getById(dto.getItemId());

        // 기본 공정은 무조건 1개 -> 모두 false면 추후 사용 시 기본 공정 선택되지 않음을 표시
        if (dto.isPrimary()) {
            routingRepository.resetPrimary(item.getId());
        }

        return routingRepository.save(RoutingMapper.toEntity(item, dto)).getId();
    }

    @Transactional
    @Override
    public void updateRouting(Long id, RoutingUpdateDto dto) {
        Routing routing = routingReader.getById(id);

        // 기본 공정은 무조건 1개 -> 모두 false면 추후 사용 시 기본 공정 선택되지 않음을 표시
        if (dto.isPrimary() && !routing.isPrimary()) {
            routingRepository.resetPrimary(routing.getItem().getId());
        }

        RoutingMapper.apply(routing, dto);
    }

    @Transactional
    @Override
    public void deleteRoutings(List<Long> ids) {
        ids.forEach(id -> routingReader.getById(id).delete());
    }
}

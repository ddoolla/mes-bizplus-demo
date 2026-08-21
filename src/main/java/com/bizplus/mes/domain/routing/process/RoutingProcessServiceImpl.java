package com.bizplus.mes.domain.routing.process;

import com.bizplus.mes.domain.process.Process;
import com.bizplus.mes.domain.process.ProcessReader;
import com.bizplus.mes.domain.routing.Routing;
import com.bizplus.mes.domain.routing.RoutingReader;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessCreateDto;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessDto;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutingProcessServiceImpl implements RoutingProcessService {

    private final RoutingProcessRepository routingProcessRepository;

    private final ProcessReader processReader;
    private final RoutingReader routingReader;
    private final RoutingProcessReader routingProcessReader;

    @Override
    public List<RoutingProcessDto> getRoutingProcesses(Long routingId) {
        return routingProcessRepository.findRoutingProcesses(routingId);
    }

    @Transactional
    @Override
    public void createRoutingProcesses(Long routingId, RoutingProcessCreateDto dto) {
        Routing routing = routingReader.getById(routingId);

        int stepNo = 1;

        for (Long processId : dto.getProcessIds()) {
            Process process = processReader.getById(processId);

            routingProcessRepository.save(new RoutingProcess(routing, process, stepNo++));
        }
    }

    @Transactional
    @Override
    public void updateRoutingProcesses(List<RoutingProcessUpdateDto> dtos) {
        dtos.forEach(dto -> {
            routingProcessReader.getById(dto.getId())
                    .updateStep(dto.getStepNo());
        });
    }

    /*
    * 물리 삭제 데이터
    * */
    @Override
    public void deleteRoutingProcesses(List<Long> ids) {
        ids.forEach(routingProcessRepository::deleteById);
    }
}

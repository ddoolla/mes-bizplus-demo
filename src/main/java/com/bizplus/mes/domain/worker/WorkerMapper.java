package com.bizplus.mes.domain.worker;

import com.bizplus.mes.domain.user.User;
import com.bizplus.mes.domain.worker.dto.WorkerCreateDto;
import com.bizplus.mes.domain.worker.dto.WorkerUpdateDto;

public class WorkerMapper {

    public static Worker toEntity(User user, WorkerCreateDto dto) {
        return new Worker(
                user,
                dto.getCode(),
                dto.getRemark()
        );
    }

    public static void apply(Worker worker, WorkerUpdateDto dto) {
        worker.update(
                dto.getCode(),
                dto.getRemark()
        );
    }
}

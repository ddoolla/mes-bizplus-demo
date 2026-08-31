package com.bizplus.mes.domain.worker.process;

import com.bizplus.mes.common.entity.AuditableEntity;
import com.bizplus.mes.domain.process.Process;
import com.bizplus.mes.domain.worker.Worker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 물리 삭제 데이터
 * */
@Entity
@Table(name = "worker_processes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkerProcess extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id", nullable = false)
    private Process process;

    public WorkerProcess(Worker worker, Process process) {
        this.worker = worker;
        this.process = process;
    }
}


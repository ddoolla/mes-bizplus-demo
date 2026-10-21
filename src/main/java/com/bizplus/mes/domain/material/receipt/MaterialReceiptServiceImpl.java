package com.bizplus.mes.domain.material.receipt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialReceiptServiceImpl implements MaterialReceiptService {

    private final MaterialReceiptRepository materialReceiptRepository;

}

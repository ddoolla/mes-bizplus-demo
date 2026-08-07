package com.bizplus.mes.domain.uom;

import java.util.List;

public interface UomQueryRepository {

    List<Uom> findUoms(String code, String name);
}

package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.RejectRecordDao;
import com.rosellete.textilesale.model.RejectRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@DisplayName("退货DAO测试用例")
@SpringBootTest
class RejectRecordServiceTest {
    @Autowired
    private RejectRecordDao rejectRecordDao;

    @AfterEach
    void tearDown() {
        rejectRecordDao = null;
    }

    @DisplayName("测试排序分页")
    @Test
    void findRecordList() {
        Assertions.assertNotNull(rejectRecordDao, "rejectRecordDao is null");
        List<RejectRecord> pageBySupplierInfo = rejectRecordDao.findPageBySupplierInfo(null, null,
                null, null, null, null, 0, 10);
        pageBySupplierInfo.parallelStream().forEachOrdered(e -> System.out.println(e.getRecordNo() + "\t" + e.getRejectedDate()));
        int size = rejectRecordDao.findBySupplierInfo(null, null,
                null, null, null, null).size();
        System.out.println("size = " + size);
    }
}
package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.PaymethodMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymethodMapperDao extends BaseRepository<PaymethodMapper,String> {
    @Query(value = "select * from t_paymethod_mapper where status = 1",nativeQuery = true)
    List<PaymethodMapper> getAllPaymethod();
}

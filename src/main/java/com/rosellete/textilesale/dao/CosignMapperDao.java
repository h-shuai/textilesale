package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.CosignMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CosignMapperDao extends BaseRepository<CosignMapper,String> {
    @Query(value = "select * from t_cosign_mapper where status = 1",nativeQuery = true)
    List<CosignMapper> getAllCosign();
}

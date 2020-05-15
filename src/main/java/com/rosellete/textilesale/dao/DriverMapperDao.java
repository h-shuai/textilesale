package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.DriverMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverMapperDao extends BaseRepository<DriverMapper,String> {
    @Query(value = "select * from t_driver_mapper where status = 1",nativeQuery = true)
    List<DriverMapper> getAllDriver();

    int countByDriverName(String driverName);
}

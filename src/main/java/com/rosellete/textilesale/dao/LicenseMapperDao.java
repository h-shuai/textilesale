package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.LicenseMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseMapperDao extends BaseRepository<LicenseMapper,String> {
    @Query(value = "select * from t_license_mapper where status = 1",nativeQuery = true)
    List<LicenseMapper> getAllLicense();

    int countByLicenseNo(String licenseNo);
}

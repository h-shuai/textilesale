package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.CosignMapperDao;
import com.rosellete.textilesale.dao.DriverMapperDao;
import com.rosellete.textilesale.dao.LicenseMapperDao;
import com.rosellete.textilesale.model.DriverMapper;
import com.rosellete.textilesale.model.LicenseMapper;
import com.rosellete.textilesale.vo.MapperVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class MapperService {
    @Autowired
    private DriverMapperDao driverMapperDao;
    @Autowired
    private LicenseMapperDao licenseMapperDao;
    @Autowired
    private CosignMapperDao cosignMapperDao;

    public MapperVO getAllMapperData(){
        MapperVO mapperVO = new MapperVO();
        mapperVO.setDriverMappers(driverMapperDao.getAllDriver());
        mapperVO.setLicenseMappers(licenseMapperDao.getAllLicense());
        mapperVO.setCosignMappers(cosignMapperDao.getAllCosign());
        return mapperVO;
    }

    public Long saveMapperInfo(Map param){
        String type = (String)param.get("type");
        int count = 0;
        if (type.equals("1")) {
            count = driverMapperDao.countByDriverName((String)param.get("value"));
            if (count == 0) {
                DriverMapper driverMapper = new DriverMapper();
                driverMapper.setDriverName((String)param.get("value"));
                driverMapper.setDriverPhone(null);
                driverMapper.setStatus(1);
                DriverMapper result = driverMapperDao.save(driverMapper);
                return result.getId();
            }
        } else {
            count = licenseMapperDao.countByLicenseNo((String)param.get("value"));
            if (count == 0) {
                LicenseMapper licenseMapper = new LicenseMapper();
                licenseMapper.setLicenseNo((String)param.get("value"));
                licenseMapper.setIfRestriction(0);
                licenseMapper.setStatus(1);
                LicenseMapper result = licenseMapperDao.save(licenseMapper);
                return result.getId();
            }
        }
        return null;
    }
}

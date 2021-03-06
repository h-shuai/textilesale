package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.CosignMapperDao;
import com.rosellete.textilesale.dao.DriverMapperDao;
import com.rosellete.textilesale.dao.LicenseMapperDao;
import com.rosellete.textilesale.dao.PaymethodMapperDao;
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
    @Autowired
    private PaymethodMapperDao paymethodMapperDao;

    public MapperVO getAllMapperData(String type){
        MapperVO mapperVO = new MapperVO();
        if (type.indexOf("1") > -1){
            mapperVO.setDriverMappers(driverMapperDao.getAllDriver());
        }
        if (type.indexOf("2") > -1){
            mapperVO.setLicenseMappers(licenseMapperDao.getAllLicense());
        }
        if (type.indexOf("3") > -1){
            mapperVO.setCosignMappers(cosignMapperDao.getAllCosign());
        }
        if (type.indexOf("4") > -1){
            mapperVO.setPaymethodMappers(paymethodMapperDao.getAllPaymethod());
        }
        return mapperVO;
    }

    public Long saveMapperInfo(Map param){
        String type = (String)param.get("type");
        int count = 0;
        if (type.equals("1")) {
            count = driverMapperDao.countByDriverName(String.valueOf(param.get("value")));
            if (count == 0) {
                DriverMapper driverMapper = new DriverMapper();
                driverMapper.setDriverName(String.valueOf(param.get("value")));
                driverMapper.setDriverPhone(null);
                driverMapper.setStatus(1);
                driverMapper.setIsSelf(2);
                DriverMapper result = driverMapperDao.save(driverMapper);
                return result.getId();
            }
        } else {
            count = licenseMapperDao.countByLicenseNo(String.valueOf(param.get("value")));
            if (count == 0) {
                LicenseMapper licenseMapper = new LicenseMapper();
                licenseMapper.setLicenseNo(String.valueOf(param.get("value")));
                licenseMapper.setIfRestriction(0);
                licenseMapper.setStatus(1);
                LicenseMapper result = licenseMapperDao.save(licenseMapper);
                return result.getId();
            }
        }
        return null;
    }
}

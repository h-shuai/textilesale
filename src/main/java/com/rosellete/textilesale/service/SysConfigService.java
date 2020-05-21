package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.SysConfigDao;
import com.rosellete.textilesale.model.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class SysConfigService {
    @Autowired
    private SysConfigDao sysConfigDao;

    public SysConfig findByCodeName(String codeName) {
        SysConfig config = new SysConfig();
        config.setCodeName(codeName);
        Example<SysConfig> example = Example.of(config);
        return sysConfigDao.findOne(example).orElse(null);
    }

    public String getImagePath() {
        String folder;
        SysConfig imagePath = this.findByCodeName("imagePath");
        if (null != imagePath) {
            folder = imagePath.getCodeValue();
        } else {
            folder = "/Users/shadow/upload";
        }
        return folder;
    }
}

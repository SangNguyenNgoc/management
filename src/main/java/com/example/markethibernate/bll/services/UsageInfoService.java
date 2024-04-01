package com.example.markethibernate.bll.services;

import com.example.markethibernate.dal.dao.UsageInfoDao;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.dal.entities.UsageInfoEntity;

import java.time.LocalDateTime;
import java.util.List;

public class UsageInfoService {

    private static class UsageInfoServiceHolder {
        private static final UsageInfoService INSTANCE = new UsageInfoService();
    }

    private UsageInfoService() {
    }

    public static UsageInfoService getInstance() {
        return UsageInfoServiceHolder.INSTANCE;
    }

    public List<UsageInfoEntity> findAll() {
        return UsageInfoDao.getInstance().findAll();
    }
    public UsageInfoEntity findById(Long id) {
        if(id == null || id <= 0)
            return null;
        return UsageInfoDao.getInstance().findById(id);
    }
    public UsageInfoEntity save(UsageInfoEntity usageInfo) {
        if(usageInfo == null)
            return null;
        if(usageInfo.getDevice() == null)
            return null;
        return UsageInfoDao.getInstance().save(usageInfo);
    }
    public UsageInfoEntity update(UsageInfoEntity usageInfo) {
        if(usageInfo == null)
            return null;
        if(usageInfo.getId() == null || usageInfo.getId() <= 0)
            return null;
        if(usageInfo.getDevice() == null)
            return null;
        return UsageInfoDao.getInstance().update(usageInfo);
    }

    public Boolean deleteById(Long id) {
        if(id == null || id <= 0)
            return null;
        return UsageInfoDao.getInstance().deleteById(id);
    }
}

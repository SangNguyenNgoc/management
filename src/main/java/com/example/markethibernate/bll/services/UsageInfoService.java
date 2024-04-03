package com.example.markethibernate.bll.services;

import com.example.markethibernate.dal.dao.UsageInfoDao;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.dal.entities.UsageInfoEntity;
import com.example.markethibernate.gui.utils.DialogUtil;
import com.example.markethibernate.utils.AppUtil;
import javafx.scene.control.Alert;

import java.time.LocalDateTime;
import java.util.List;

public class UsageInfoService {

    private UsageInfoService() {
    }

    public static UsageInfoService getInstance() {
        return UsageInfoServiceHolder.INSTANCE;
    }

    public List<UsageInfoEntity> findAll() {
        return UsageInfoDao.getInstance().findAll();
    }

    public UsageInfoEntity getById(String idString) {
        Long id = AppUtil.parseId(idString);
        if (id == null) {
            return null;
        }
        return UsageInfoDao.getInstance().findById(id);
    }

    public UsageInfoEntity save(UsageInfoEntity usageInfo) {
        if (usageInfo == null)
            return null;
        if (usageInfo.getDevice() == null)
            return null;
        return UsageInfoDao.getInstance().save(usageInfo);
    }

    public UsageInfoEntity update(UsageInfoEntity usageInfo) {
        if (usageInfo == null)
            return null;
        if (usageInfo.getId() == null || usageInfo.getId() <= 0)
            return null;
        if (usageInfo.getDevice() == null)
            return null;
        return UsageInfoDao.getInstance().update(usageInfo);
    }

    public Boolean deleteById(Long id) {
        if (id == null || id <= 0)
            return null;
        return UsageInfoDao.getInstance().deleteById(id);
    }

    public UsageInfoEntity checkIn(String userId) {
        PersonEntity person = PersonService.getInstance().getById(userId);
        if (person == null) {
            return null;
        }
        if (PenalizeService.getInstance().isPenalize(userId)) {
            DialogUtil.getInstance().showAlert("Lỗi", "Thành viên này vẫn đang trong hình phạt.", Alert.AlertType.ERROR);
            return null;
        }
        UsageInfoEntity usageInfo = UsageInfoEntity.builder()
                .person(person)
                .checkinTime(LocalDateTime.now())
                .build();
        return UsageInfoDao.getInstance().save(usageInfo);
    }

    public UsageInfoEntity borrowDevice(String userId, String deviceId) {
        PersonEntity person = PersonService.getInstance().getById(userId);
        if (person == null) {
            DialogUtil.getInstance().showAlert("Lỗi", "Không tìm thấy thành viên.", Alert.AlertType.ERROR);
            return null;
        }
        if (PenalizeService.getInstance().isPenalize(userId)) {
            DialogUtil.getInstance().showAlert("Lỗi", "Thành viên này vẫn đang trong hình phạt.", Alert.AlertType.ERROR);
            return null;
        }
        DeviceEntity device = DeviceService.getInstance().getById(deviceId);
        if (device == null) {
            DialogUtil.getInstance().showAlert("Lỗi", "Không tìm thấy thiết bị.", Alert.AlertType.ERROR);
            return null;
        }
        UsageInfoEntity usageInfo = UsageInfoEntity.builder()
                .person(person)
                .device(device)
                .borrowTime(LocalDateTime.now())
                .build();
        return UsageInfoDao.getInstance().save(usageInfo);
    }

    public UsageInfoEntity returnDevice(String usageId) {
        UsageInfoEntity usageInfo = getById(usageId);
        if(usageInfo.getBorrowTime() == null) {
            return null;
        }
        usageInfo.setReturnTime(LocalDateTime.now());
        return UsageInfoDao.getInstance().update(usageInfo);
    }

    private static class UsageInfoServiceHolder {
        private static final UsageInfoService INSTANCE = new UsageInfoService();
    }


}

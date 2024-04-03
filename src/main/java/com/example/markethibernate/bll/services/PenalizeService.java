package com.example.markethibernate.bll.services;


import com.example.markethibernate.dal.dao.DeviceDao;
import com.example.markethibernate.dal.dao.PenalizeDao;
import com.example.markethibernate.dal.dao.PersonDao;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PenalizeEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.utils.AppUtil;

import java.util.List;

public class PenalizeService {

    private static class PenalizeServiceHolder {
        private static final PenalizeService INSTANCE = new PenalizeService();
    }

    private PenalizeService() {
    }

    public static PenalizeService getInstance() {
        return PenalizeServiceHolder.INSTANCE;
    }

    public PenalizeEntity getById(String idString) {
        Long id = AppUtil.parseId(idString);
        if (id == null) {
            return null;
        }
        return PenalizeDao.getInstance().findById(id);
    }

    public boolean isPenalize(String userId) {
        PersonEntity person = PersonService.getInstance().getById(userId);
        if(person == null) {
            return true;
        }
        List<PenalizeEntity> penalizes = PenalizeDao.getInstance().findByPersonIsPenalize(AppUtil.parseId(userId));
        return !penalizes.isEmpty();
    }

}

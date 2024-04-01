package com.example.markethibernate.bll.services;


import com.example.markethibernate.dal.dao.PenalizeDao;
import com.example.markethibernate.dal.entities.PenalizeEntity;

public class PenalizeService {

    private static class PenalizeServiceHolder {
        private static final PenalizeService INSTANCE = new PenalizeService();
    }

    private PenalizeService() {
    }

    public static PenalizeService getInstance() {
        return PenalizeServiceHolder.INSTANCE;
    }

    public PenalizeEntity addPerson(PenalizeEntity penalize) {
        if (penalize == null) {
            return null;
        }
        return PenalizeDao.getInstance().addPenalize(penalize);
    }


    public PenalizeEntity updatePerson(PenalizeEntity penalize) {
        if (penalize == null) {
            return null;
        }
        return PenalizeDao.getInstance().updatePenalize(penalize);
    }
    public PenalizeEntity deletePersonById(int id) {
        if (id == 0) {
            return null;
        }
        return PenalizeDao.getInstance().deletePenalizeById(id);
    }

}

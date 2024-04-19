package com.example.markethibernate.bll.services;


import com.example.markethibernate.dal.dao.DeviceDao;
import com.example.markethibernate.dal.dao.PenalizeDao;
import com.example.markethibernate.dal.dao.PersonDao;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PenalizeEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.gui.utils.DialogUtil;
import com.example.markethibernate.utils.AppUtil;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PenalizeService {

    private static class PenalizeServiceHolder {
        private static final PenalizeService INSTANCE = new PenalizeService();
    }

    private PenalizeService() {
    }

    public static PenalizeService getInstance() {
        return PenalizeServiceHolder.INSTANCE;
    }

    public List<PenalizeEntity> getALl() {
        return PenalizeDao.getInstance().findAll();
    }

    public PenalizeEntity getById(String idString) {
        Long id = AppUtil.parseId(idString);
        if (id == null) {
            return null;
        }
        return PenalizeDao.getInstance().findByIdAndPerson(id);
    }

    public PenalizeEntity getByIdAndPerson(String idString) {
        Long id = AppUtil.parseId(idString);
        if (id == null) {
            return null;
        }
        return PenalizeDao.getInstance().findByIdAndPerson(id);
    }

    public boolean isPenalize(String userId) {
        PersonEntity person = PersonService.getInstance().getById(userId);
        if(person == null) {
            return true;
        }
        List<PenalizeEntity> penalizes = PenalizeDao.getInstance().findByPersonIsPenalize(AppUtil.parseId(userId));
        return !penalizes.isEmpty();
    }

    public PenalizeEntity create(Long personId, String type, String payment) {
        Integer pay = AppUtil.parseInt(payment);
        PenalizeEntity penalize = PenalizeEntity.builder()
                .payment(pay)
                .date(LocalDateTime.now())
                .type(type)
                .person(PersonService.getInstance().getById(personId.toString()))
                .status(true)
                .build();
        return PenalizeDao.getInstance().addPenalize(penalize);
    }

    public PenalizeEntity update(String idString, String type, String payment, Boolean status) {
        PenalizeEntity penalize = getById(idString);
        Integer pay = AppUtil.parseInt(payment);
        penalize.setType(type);
        penalize.setPayment(pay);
        penalize.setStatus(status);
        return PenalizeDao.getInstance().updatePenalize(penalize);
    }

    public Optional<PenalizeEntity> updateStatus(String idString) {
        PenalizeEntity penalize = getById(idString);
        if(penalize == null) {
            return Optional.empty();
        }
        penalize.setStatus(!penalize.getStatus());
        return Optional.ofNullable(PenalizeDao.getInstance().updatePenalize(penalize));
    }

    public String checkPayment(String payment) {
        Integer pay = AppUtil.parseInt(payment);
        if(pay == null) {
            return "Số tiền phải là 1 số";
        }
        return "";
    }

}

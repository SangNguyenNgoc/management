package com.example.markethibernate.bll.services;

public class PenalizeService {

    private static class PenalizeServiceHolder {
        private static final PenalizeService INSTANCE = new PenalizeService();
    }

    private PenalizeService() {
    }

    public static PenalizeService getInstance() {
        return PenalizeServiceHolder.INSTANCE;
    }



}

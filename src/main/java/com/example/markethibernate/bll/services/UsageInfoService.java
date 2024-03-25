package com.example.markethibernate.bll.services;

public class UsageInfoService {

    private static class UsageInfoServiceHolder {
        private static final UsageInfoService INSTANCE = new UsageInfoService();
    }

    private UsageInfoService() {
    }

    public static UsageInfoService getInstance() {
        return UsageInfoServiceHolder.INSTANCE;
    }
}

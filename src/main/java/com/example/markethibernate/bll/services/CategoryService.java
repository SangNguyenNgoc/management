package com.example.markethibernate.bll.services;

import com.example.markethibernate.dal.dao.VegetableDao;
import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.logging.Logger;

public class CategoryService {

    private static final Logger logger = Logger.getLogger(CategoryService.class.getName());

    private static class CategoryServiceHolder {
        private static final CategoryService INSTANCE = new CategoryService();
    }

    private CategoryService() {

    }

    public static CategoryService getInstance() {
        return CategoryService.CategoryServiceHolder.INSTANCE;
    }
}

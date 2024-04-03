package com.example.markethibernate.utils;

import com.example.markethibernate.bll.dtos.PersonValidator;
import com.example.markethibernate.dal.entities.PersonEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorUtilTest {

    @Test
    void validateField() {
        String result = ValidatorUtil.validateField(
                PersonValidator.builder().email("invalid@gmail.com").build(),
                "email"
        );
        System.out.println(result);
    }
}
package com.example.markethibernate.utils;


import org.apache.commons.lang3.StringUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validator;


import java.util.Set;

public class ValidatorUtil {
    public static String validateField(Object object, String fieldName) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validateProperty(object, fieldName);
        if (!constraintViolations.isEmpty()) {
            StringBuilder result = new StringBuilder();
            for (ConstraintViolation<Object> violation : constraintViolations) {
                if(!result.isEmpty()) {
                    result.append(", ");
                }
                result.append(violation.getMessage());
            }
            return StringUtils.capitalize(result.toString());
        } else {
            return "";
        }
    }

    public static String validateObject(Object object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (!constraintViolations.isEmpty()) {
            StringBuilder result = new StringBuilder();
            for (ConstraintViolation<Object> violation : constraintViolations) {
                if(!result.isEmpty()) {
                    result.append(", ");
                }
                result.append(violation.getMessage());
            }
            return StringUtils.capitalize(result.toString());
        } else {
            return "";
        }
    }
}

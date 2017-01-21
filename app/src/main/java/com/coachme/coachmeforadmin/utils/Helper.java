package com.coachme.coachmeforadmin.utils;

import com.coachme.coachmeforadmin.utils.errors.MyValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class Helper {
    static ObjectMapper mapper = new ObjectMapper();


    public static <T> T convertJsonToObject(String jsonString, Class<T> className) {
        try {
            return mapper.readValue(jsonString, className);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertObjectToJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void checkConstraintsValidations(Object object) throws MyValidationException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Object>> constraintViolations =
                validator.validate(object);

        if (constraintViolations.size() > 0) {
            MyValidationException validationException = new MyValidationException();
            for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
                String fieldName = constraintViolation.getPropertyPath().toString();
                String errorDescription = "Le champ " + fieldName + " " + constraintViolation.getMessage();
                validationException.addError(400, fieldName, errorDescription);
            }
            throw validationException;
        }
    }
}

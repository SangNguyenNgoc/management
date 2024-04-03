package com.example.markethibernate.bll.services;

import com.example.markethibernate.dal.dao.DeviceDao;
import com.example.markethibernate.dal.dao.PersonDao;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.utils.AppUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {
    @Test
    public void testAddPerson() {
        PersonEntity addedPerson = PersonService.getInstance().addPerson(
                "Tony Start",
                "ironman343@gmail.com",
                "123456789",
                "CNTT",
                "KTPM",
                "0916921132"
        );
        Assertions.assertNotNull(addedPerson);
        System.out.println(AppUtil.toJson(addedPerson));
    }

    @Test
    public void testUpdatePerson() {
        System.out.println(PersonDao.getInstance().findById(12L).toString());
        PersonEntity updatedPerson = PersonService.getInstance().updatePerson(
                "12",
                "Kong and Godzilla",
                "kongandgod3674@gmail.com",
                "12898989898",
                "CNTT",
                "KTPM",
                "0977686535"
        );
        Assertions.assertNotNull(updatedPerson);
        System.out.println(updatedPerson.toString());

    }

    @Test
    public void testDeletePersonById() {
        String id = "9"; //id Person cần xóa

        PersonEntity deletedPerson = PersonService.getInstance().deletePersonById(id);

        Assertions.assertNotNull(deletedPerson);
    }

    @Test
    void createPassword() {
        System.out.println(PersonService.getInstance().createPassword("3121410417"));
    }

    @Test
    void comparePassword() {
        assertTrue(PersonService.getInstance().comparePassword(
                "3121410417",
                "$2a$10$IM2jQT0G/Gmj.ph2eAtCBuGFvRpIlGd4QlnwSlJJgro5Znfycstva")
        );
    }

    @Test
    void getAll() {
        List<PersonEntity> result = PersonService.getInstance().getAll();
        System.out.println(result);
    }

    @Test
    void getById() {
    }
}
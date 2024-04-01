package com.example.markethibernate.bll.services;

import com.example.markethibernate.dal.entities.PersonEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {
    @Test
    public void testAddPerson() {
        PersonEntity person = new PersonEntity();
        person.setName("Thanh Tung");
        person.setDepartment("CNTT");
        person.setProfession("");
        person.setPhoneNumber("033456789");
        person.setStatus(true);

        PersonEntity addedPerson = PersonService.getInstance().addPerson(person);

        Assertions.assertNotNull(addedPerson);
    }

    @Test
    public void testUpdatePerson() {
        PersonEntity person = new PersonEntity();
        person.setId(9L); //id Person cần update
        person.setName("Tung Duong");
        person.setDepartment("CNTT");
        person.setProfession("");
        person.setPhoneNumber("053456789");
        person.setStatus(true);

        PersonEntity updatedPerson = PersonService.getInstance().updatePerson(person);

        Assertions.assertNotNull(updatedPerson);
    }

    @Test
    public void testDeletePersonById() {
        int id = 9; //id Person cần xóa

        PersonEntity deletedPerson = PersonService.getInstance().deletePersonById(id);

        Assertions.assertNotNull(deletedPerson);
    }
}
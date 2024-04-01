package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.PersonEntity;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {
    private static PersonDao personDao;

    @BeforeAll
    public static void setUp() {
        personDao = PersonDao.getInstance();
    }
    @Test
    public void testAddPerson() {
        PersonEntity person = new PersonEntity();
        person.setName("Nguyễn Văn B");
        person.setDepartment("CNTT");
        person.setProfession("KTPM");
        person.setPhoneNumber("0382747238");
        person.setStatus(true);

        PersonEntity addedPerson = personDao.addPerson(person);

        assertNotNull(addedPerson);

    }
    @Test
    public void testUpdatePerson() {
        PersonEntity person = new PersonEntity();

        person.setId(8L); //id Person cần sửa

        person.setName("Nguyễn Văn C");
        person.setDepartment("CNTT");
        person.setProfession("KTPM");
        person.setPhoneNumber("0382747238");
        person.setStatus(true);

        PersonEntity updatePerson = personDao.updatePerson(person);

        assertNotNull(updatePerson);

    }
    @Test
    public void testDeletePerson(){
        PersonEntity deletePerson = personDao.deletePersonById(8);
        assertNotNull(deletePerson);
    }
}
package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.utils.AppUtil;
import org.junit.jupiter.api.*;

import java.util.List;

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
        System.out.println(AppUtil.toJson(addedPerson));

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
        PersonEntity deletePerson = personDao.deletePersonById(8L);
        assertNotNull(deletePerson);
    }

    @Test
    void findByYear() {
        List<PersonEntity> result = PersonDao.getInstance().findByYear(22L);
        result.forEach(item -> {
            System.out.println(item.toString());
        });
    }

    @Test
    void deletePersonByYear() {
        System.out.println(PersonDao.getInstance().deletePersonByYear(22L));
    }
}
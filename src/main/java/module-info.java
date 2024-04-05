module com.example.markethibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires org.mapstruct;
    requires lombok;
    requires jakarta.persistence;
    requires java.naming;
    requires gson;
    requires org.hibernate.validator;
    requires jakarta.validation;
    requires org.apache.commons.lang3;
    requires spring.security.crypto;


    opens com.example.markethibernate to javafx.fxml;
    opens com.example.markethibernate.dal.entities to org.hibernate.orm.core, gson, org.hibernate.validator;
    opens com.example.markethibernate.gui.controller to javafx.fxml;


    exports com.example.markethibernate;
    exports com.example.markethibernate.bll.dtos;
    exports com.example.markethibernate.dal.entities;
    exports com.example.markethibernate.bll.mappers;
    opens com.example.markethibernate.bll.dtos to gson, org.hibernate.orm.core, org.hibernate.validator;
    exports com.example.markethibernate.gui.controller;
}
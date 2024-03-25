module com.example.markethibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires org.mapstruct;
    requires lombok;
    requires jakarta.persistence;
    requires java.naming;


    opens com.example.markethibernate to javafx.fxml;
    opens com.example.markethibernate.dal.entities to org.hibernate.orm.core;
    opens com.example.markethibernate.gui to javafx.fxml;


    exports com.example.markethibernate;
    exports com.example.markethibernate.gui;
    exports com.example.markethibernate.bll.dtos;
    exports com.example.markethibernate.dal.entities;
    opens com.example.markethibernate.bll.dtos to org.hibernate.orm.core;
    exports com.example.markethibernate.bll.mappers;
}
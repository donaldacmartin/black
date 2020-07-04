module scot.martin.black.model {
    requires java.persistence;
    requires java.validation;
    requires org.hibernate.orm.core;

    exports scot.martin.black.model.entity;
    exports scot.martin.black.model.request;
}

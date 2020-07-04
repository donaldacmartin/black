module scot.martin.black.rest {
    requires java.validation;
    requires org.slf4j;
    requires spring.beans;
    requires spring.data.commons;
    requires spring.web;

    requires scot.martin.black.model;
    requires scot.martin.black.repo;
    requires scot.martin.black.rss;

    exports scot.martin.black.rest.controller.crud;
    exports scot.martin.black.rest.controller.podcast;
}

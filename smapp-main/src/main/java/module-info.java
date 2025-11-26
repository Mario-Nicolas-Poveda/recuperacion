module com.chathumal.smapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.naming;
    requires org.postgresql.jdbc;
    requires java.sql;
    requires com.jfoenix;
    requires static lombok;

    opens com.chathumal.smapp.controller to javafx.fxml;

    exports com.chathumal.smapp;
    exports com.chathumal.smapp.controller;
    exports com.chathumal.smapp.entity;
    exports com.chathumal.smapp.service;
    exports com.chathumal.smapp.dao;
    exports com.chathumal.smapp.configuration;
}

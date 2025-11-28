module com.hedgeflows.desktop {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive com.google.gson;
    requires transitive org.kordamp.ikonli.core;
    requires transitive org.kordamp.ikonli.javafx;
    requires transitive org.kordamp.ikonli.fontawesome5;
    requires static lombok;

    opens com.hedgeflows.desktop to javafx.fxml;
    opens com.hedgeflows.desktop.controller to javafx.fxml;
    opens com.hedgeflows.desktop.model to com.google.gson, javafx.base;

    exports com.hedgeflows.desktop;
    exports com.hedgeflows.desktop.controller;
    exports com.hedgeflows.desktop.model;
    exports com.hedgeflows.desktop.repository;
    exports com.hedgeflows.desktop.service;
}



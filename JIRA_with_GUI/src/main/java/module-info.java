module pl.polsl.jira_with_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens pl.polsl.jira_with_gui to javafx.fxml;
    opens pl.polsl.controller to javafx.fxml;
    exports pl.polsl.jira_with_gui;
    exports pl.polsl.model;
    exports pl.polsl.controller;
    exports pl.polsl.otherClasses;
}

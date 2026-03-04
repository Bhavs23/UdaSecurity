open module com.udacity.catpoint.security {
    requires com.udacity.catpoint.image;
    requires javafx.controls;
    requires java.desktop;
    requires java.prefs;
    requires com.google.gson;
    requires com.google.common;
    requires org.slf4j;
    requires com.miglayout.swing;

    exports com.udacity.catpoint.application;
    exports com.udacity.catpoint.data;
    exports com.udacity.catpoint.securityservice;
}
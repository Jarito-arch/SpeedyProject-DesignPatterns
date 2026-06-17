module codingcourse.speedyprojectdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;

    opens SpeedyProject.Application to javafx.fxml;
    opens SpeedyProject.Controllers to javafx.fxml;

    exports SpeedyProject.Application;
    exports SpeedyProject.Controllers;
}
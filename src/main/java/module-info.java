module codingcourse.speedyprojectdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens codingcourse.speedyprojectdemo to javafx.fxml;
    opens codingcourse.speedyprojectdemo.Controllers to javafx.fxml;

    exports codingcourse.speedyprojectdemo;
}
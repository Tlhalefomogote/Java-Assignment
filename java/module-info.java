module bank.bankingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires jbcrypt;

    opens bank.bankingsystem to javafx.fxml;
    opens bank.bankingsystem.Controllers to javafx.fxml;
    
    exports bank.bankingsystem;
    exports bank.bankingsystem.Controllers;
    exports bank.bankingsystem.models;
    exports bank.bankingsystem.Services;
    exports bank.bankingsystem.DAO;
}

package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.db.DataBase;
import com.dongz.codeutils.utils.DataBaseUtils;
import com.dongz.codeutils.utils.StringUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author dong
 * @date 2020/2/10 21:52
 * @desc
 */
public class StepController {
    private static final String STEP1 = "/ui/stepFirst.fxml";
    private static final String STEP2 = "/ui/step2.fxml";

    public Button forwardBtn;
    public Button nextBtn;
    public ComboBox dbType;
    public Button close;
    public TextField host;
    public PasswordField password;
    public TextField user;
    public ComboBox database;
    public TextField port;

    public void next() throws IOException {
        changeStep(nextBtn, STEP2);
    }

    public void forward() throws IOException {
        changeStep(forwardBtn, STEP1);
    }

    private void changeStep(Button btn,String step) throws IOException {
        Stage secondStage = (Stage) btn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(step));
        Scene scene = new Scene(root);
        secondStage.setScene(scene);
    }

    public void close() {
        Stage window = (Stage) close.getScene().getWindow();
        window.close();
    }

    public void testConnect() throws SQLException, ClassNotFoundException {
        String type = (String) dbType.getValue();
        if (StringUtils.isBlank(type)) {
            alert(Alert.AlertType.WARNING, "请选择数据库类型");
            return;
        }
        String ip = host.getText();
        if (StringUtils.isBlank(ip)) {
            alert(Alert.AlertType.WARNING, "请输入服务器ip");
            return;
        }
        String username = user.getText();
        if (StringUtils.isBlank(username)) {
            alert(Alert.AlertType.WARNING, "请输入用户名");
            return;
        }
        String passwordText = password.getText();
        if (StringUtils.isBlank(passwordText)) {
            alert(Alert.AlertType.WARNING, "请输入密码");
            return;
        }
        String portText = port.getText();
        if (StringUtils.isBlank(portText)) {
            alert(Alert.AlertType.WARNING, "请输入服务器端口");
            return;
        }

        DataBase db = new DataBase(type,ip,portText,"");
        db.setUserName(username);
        db.setPassWord(passwordText);

        List<String> catalogs = DataBaseUtils.getSchemas(db);
        database.getItems().removeAll();
        database.getItems().addAll(catalogs);
        alert(Alert.AlertType.INFORMATION, "连接成功");
    }

    private void alert(Alert.AlertType type,String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}

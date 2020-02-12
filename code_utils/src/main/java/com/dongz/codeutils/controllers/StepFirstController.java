package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.db.DataBase;
import com.dongz.codeutils.utils.DataBaseUtils;
import com.dongz.codeutils.utils.StringUtils;
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
public class StepFirstController extends BaseController{

    public Button nextBtn;
    public ComboBox dbType;
    public Button close;
    public TextField host;
    public PasswordField password;
    public TextField user;
    public ComboBox database;
    public TextField port;

    /**
     * database to entity
     * @throws IOException
     */
    public void next() throws IOException {
        if (!isConnection) {
            alert(Alert.AlertType.WARNING, "请连接服务器");
            return;
        }
        String databaseValue = (String) database.getValue();
        if (StringUtils.isBlank(databaseValue)) {
            alert(Alert.AlertType.WARNING, "请选择数据库");
            return;
        }
        db.setDb(databaseValue);
        changeStep(nextBtn, STEP2);
    }

    @Override
    public void reload() {
        host.setText(db.getIp());
        port.setText(db.getPort());
        user.setText(db.getUserName());
        password.setText(db.getPassWord());
        dbType.setValue(db.getDbType());
        database.setValue(db.getDb());
        database.getItems().removeAll();
        database.getItems().addAll(db.getDbList());
    }

    public void close() {
        Stage window = (Stage) close.getScene().getWindow();
        window.close();
    }

    public void testConnect() {
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

        db = new DataBase(type,ip,portText,"");
        db.setUserName(username);
        db.setPassWord(passwordText);

        List<String> catalogs = null;
        try {
            catalogs = DataBaseUtils.getSchemas(db);
        } catch (SQLException e) {
            alert(Alert.AlertType.ERROR, "无法连接数据库，请核对连接信息是否正确");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            alert(Alert.AlertType.ERROR, "无法加载驱动类");
            e.printStackTrace();
        }
        db.setDbList(catalogs);
        database.getItems().removeAll();
        database.getItems().addAll(catalogs);
        isConnection = true;
        alert(Alert.AlertType.INFORMATION, "连接成功");
    }
}

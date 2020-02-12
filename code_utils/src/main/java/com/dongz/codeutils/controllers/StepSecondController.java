package com.dongz.codeutils.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author dong
 * @date 2020/2/10 21:52
 * @desc
 */
public class StepSecondController extends BaseController{

    public Button forwardBtn;
    public TextField lab;
    public Button nextBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lab.setText("123");
    }

    public void forward() throws IOException {
        changeStep(forwardBtn, STEP1);
    }

    public void next(ActionEvent actionEvent) {
    }
}


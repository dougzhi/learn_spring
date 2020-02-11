package com.dongz.codeutils.controllers;

import javafx.scene.control.Button;

import java.io.IOException;

/**
 * @author dong
 * @date 2020/2/10 21:52
 * @desc
 */
public class StepScendController extends BaseController{

    public Button forwardBtn;

    public void forward() throws IOException {
        changeStep(forwardBtn, STEP1);
    }

}

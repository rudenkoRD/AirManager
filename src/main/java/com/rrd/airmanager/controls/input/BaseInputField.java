package com.rrd.airmanager.controls.input;


import javafx.scene.control.Label;

public abstract class BaseInputField<T> implements Input<T>{
    protected final Label fieldLabel;

    BaseInputField(Label fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    protected void updateLabelState(boolean isValid){
        if (!isValid) fieldLabel.getStyleClass().add("invalid");
        else fieldLabel.getStyleClass().remove("invalid");
    }
}

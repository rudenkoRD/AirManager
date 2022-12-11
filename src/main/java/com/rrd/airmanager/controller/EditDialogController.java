package com.rrd.airmanager.controller;

import com.rrd.airmanager.controls.input.Input;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public abstract class EditDialogController<T> implements Initializable {
    @FXML
    public DialogPane dialogPane;

    abstract protected List<Input<?>> getInputFields();

    protected boolean isFormInputsValid() {
        boolean isValid = true;

        for (Input<?> item : getInputFields()) {
            isValid &= item.validate();
        }

        return isValid;
    }

    public abstract void setItemToEdit(T item);

    public abstract void onSaveItem();

    public abstract boolean isFormValid();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!isFormValid()) {
                event.consume();
            }
        });
        okButton.addEventHandler(ActionEvent.ACTION, actionEvent -> {
            onSaveItem();
        });
    }
}

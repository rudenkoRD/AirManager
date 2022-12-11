package com.rrd.airmanager.controls.input;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class EnumInputField<T extends Enum<T>> extends BaseInputField<T>{
    private final ComboBox<T> comboBox;

    public EnumInputField(Label fieldLabel, ComboBox<T> comboBox) {
        super(fieldLabel);
        this.comboBox = comboBox;
    }

    @Override
    public boolean validate() {
        boolean isValid = comboBox.getValue() != null;

        updateLabelState(isValid);
        return isValid;
    }

    @Override
    public void setValue(T value) {
        comboBox.setValue(value);
    }

    @Override
    public T getValue() {
        return comboBox.getValue();
    }
}

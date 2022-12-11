package com.rrd.airmanager.controls.input;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TextInputField<T> extends BaseInputField<T> {
    private final TextField inputField;
    private final Parser<T> parser;

    public TextInputField(Label fieldLabel, TextField inputField, Parser<T> parser) {
        super(fieldLabel);
        this.inputField = inputField;
        this.parser = parser;
    }

    @Override
    public boolean validate() {
        String value = inputField.getText();
        boolean isValid = value != null && !value.isEmpty();

        try {
            parser.apply(value);
        } catch (Exception e) {
            isValid = false;
        }

        updateLabelState(isValid);
        return isValid;
    }

    @Override
    public void setValue(T value) {
        if (value != null) inputField.setText(String.valueOf(value));
        else inputField.clear();
    }

    @Override
    public T getValue() {
        return parser.apply(inputField.getText());
    }
}

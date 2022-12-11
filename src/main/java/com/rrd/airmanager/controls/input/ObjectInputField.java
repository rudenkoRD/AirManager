package com.rrd.airmanager.controls.input;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class ObjectInputField<T> extends BaseInputField<T> {
    private final ComboBox<T> objectPicker;
    private final boolean nullable;

    public ObjectInputField(Label fieldLabel, ComboBox<T> objectPicker, boolean nullable) {
        super(fieldLabel);
        this.objectPicker = objectPicker;
        this.nullable = nullable;
    }

    @Override
    public boolean validate() {
        boolean isValid = true;

        if (!nullable) isValid = objectPicker.getValue() != null;
        updateLabelState(isValid);

        return isValid;
    }

    @Override
    public void setValue(T value) {
        objectPicker.setValue(value);
    }

    @Override
    public T getValue() {
        return objectPicker.getValue();
    }
}

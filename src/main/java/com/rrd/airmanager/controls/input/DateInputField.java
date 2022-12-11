package com.rrd.airmanager.controls.input;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class DateInputField extends BaseInputField<LocalDate> {
    private final DatePicker datePicker;

    public DateInputField(Label fieldLabel, DatePicker datePicker) {
        super(fieldLabel);
        this.datePicker = datePicker;
    }

    @Override
    public boolean validate() {
        boolean isValid = datePicker.getValue() != null;

        updateLabelState(isValid);
        return isValid;
    }

    @Override
    public void setValue(LocalDate value) {
        datePicker.setValue(value);
    }

    @Override
    public LocalDate getValue() {
        return datePicker.getValue();
    }
}

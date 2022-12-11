package com.rrd.airmanager.controls.input;

import javafx.scene.control.Label;

import java.time.LocalDateTime;

public class DateTimeInputField extends BaseInputField<LocalDateTime> {
    private final DateTimePicker dateTimePicker;

    public DateTimeInputField(Label fieldLabel, DateTimePicker dateTimePicker) {
        super(fieldLabel);
        this.dateTimePicker = dateTimePicker;
    }

    @Override
    public boolean validate() {
        boolean isValid = dateTimePicker.getValue() != null;

        updateLabelState(isValid);
        return isValid;
    }

    @Override
    public void setValue(LocalDateTime value) {
        dateTimePicker.dateTimeValueProperty().set(value);
    }

    @Override
    public LocalDateTime getValue() {
        return dateTimePicker.getDateTimeValue();
    }
}

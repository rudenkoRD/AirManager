package com.rrd.airmanager.controls.input;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimePicker extends DatePicker {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final SimpleObjectProperty<LocalDateTime> dateTimeValue = new SimpleObjectProperty<>();

    public DateTimePicker() {
        setConverter(
                new StringConverter<>() {
                    @Override
                    public String toString(LocalDate localDate) {
                        if (dateTimeValue.get() != null) {
                            return dateTimeValue.get().format(formatter);
                        } else return "";
                    }

                    @Override
                    public LocalDate fromString(String s) {
                        if (s == null || s.isEmpty()) {
                            return null;
                        }

                        try {
                            dateTimeValue.set(LocalDateTime.parse(s, formatter));
                        } catch (Exception e) {
                            return null;
                        }

                        return dateTimeValue.get().toLocalDate();
                    }
                }
        );

        valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null && oldValue == null) {
                dateTimeValue.set(null);
            }
            if (!(newValue == null)) {
                if (dateTimeValue.get() == null) {
                    dateTimeValue.set(LocalDateTime.of(newValue, LocalTime.now()));
                } else {
                    final LocalTime time = dateTimeValue.get().toLocalTime();
                    dateTimeValue.set(LocalDateTime.of(newValue, time));
                }
            }
        });

        dateTimeValue.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                valueProperty().set(oldValue.toLocalDate());
                dateTimeValue.set(oldValue);
                return;
            }

            valueProperty().set(newValue.toLocalDate());
        });
    }

    public LocalDateTime getDateTimeValue() {
        return dateTimeValue.get();
    }

    public SimpleObjectProperty<LocalDateTime> dateTimeValueProperty() {
        return dateTimeValue;
    }
}

package com.rrd.airmanager.controls.selector;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.Arrays;

public class EnumSelector<T extends Enum<T>> implements Selector<T> {
    private final ComboBox<SelectorOption<T>> optionSelector;

    public EnumSelector(
            ComboBox<SelectorOption<T>> optionSelector,
            Class<T> s,
            InvalidationListener optionListener
    ) {
        this.optionSelector = optionSelector;

        ObservableList<SelectorOption<T>> types = FXCollections.observableArrayList(
                Arrays.stream(s.getEnumConstants()).map(SelectorOption<T>::new).toList()
        );
        SelectorOption<T> all = new SelectorOption<T>(null);
        types.add(all);

        optionSelector.setItems(types);
        optionSelector.setValue(all);
        optionSelector.valueProperty().addListener(optionListener);
    }

    @Override
    public T getSelection() {
        return optionSelector.getValue().option();
    }
}



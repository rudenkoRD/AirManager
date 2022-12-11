package com.rrd.airmanager.controller.airport;

import com.rrd.airmanager.controller.EditDialogController;
import com.rrd.airmanager.events.SaveItemEvent;
import com.rrd.airmanager.controls.input.Input;
import com.rrd.airmanager.controls.input.TextInputField;
import com.rrd.airmanager.models.airport.Airport;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AirportDialogController extends EditDialogController<Airport> implements Initializable {
    private final ApplicationContext context;
    private Airport airport;

    @FXML
    public TextField locationField;

    @FXML
    public TextField maxCapacityField;

    @FXML
    public Label locationLabel;

    @FXML
    public Label capacityLabel;

    @FXML
    public DialogPane dialogPane;

    private TextInputField<String> locationInputField;
    private TextInputField<Integer> capacityInputField;

    public AirportDialogController(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected List<Input<?>> getInputFields() {
        return Arrays.asList(locationInputField, capacityInputField);
    }

    @Override
    public void setItemToEdit(Airport airport) {
        this.airport = airport;

        locationInputField.setValue(airport.getLocation());
        capacityInputField.setValue(airport.getMaxCapacity());
    }

    @Override
    public void onSaveItem() {
        airport.setLocation(locationField.textProperty().get());
        airport.setMaxCapacity(Integer.parseInt(maxCapacityField.textProperty().get()));

        context.publishEvent(new SaveItemEvent<>(airport));
    }

    @Override
    public boolean isFormValid() {
        return isFormInputsValid();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        airport = new Airport();

        locationInputField = new TextInputField<>(locationLabel, locationField, String::valueOf);
        capacityInputField = new TextInputField<>(capacityLabel, maxCapacityField, Integer::parseInt);
    }
}

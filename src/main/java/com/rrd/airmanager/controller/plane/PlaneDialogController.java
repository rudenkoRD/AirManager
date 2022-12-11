package com.rrd.airmanager.controller.plane;

import com.rrd.airmanager.controller.EditDialogController;
import com.rrd.airmanager.controls.input.EnumInputField;
import com.rrd.airmanager.controls.input.Input;
import com.rrd.airmanager.controls.input.TextInputField;
import com.rrd.airmanager.events.SaveItemEvent;
import com.rrd.airmanager.models.plane.Plane;
import com.rrd.airmanager.models.plane.PlaneType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class PlaneDialogController extends EditDialogController<Plane> implements Initializable {
    private final ApplicationContext context;
    private Plane plane;

    @FXML
    public TextField nameField;
    @FXML
    public Label nameLabel;
    @FXML
    public Label fuelConsLabel;
    @FXML
    public TextField capacityField;
    @FXML
    public Label capacityLabel;
    @FXML
    public TextField flightDistField;
    @FXML
    public Label flightDistLabel;
    @FXML
    public Label planeTypeLabel;
    @FXML
    public ComboBox<PlaneType> planeTypeField;
    @FXML
    public TextField fuelConsField;

    private TextInputField<String> nameInputField;
    private TextInputField<Integer> capacityInputField;
    private TextInputField<Double> flightDistInputField;
    private TextInputField<Double> fuelConsInputField;
    private EnumInputField<PlaneType> planeTypeInputField;

    @Autowired
    public PlaneDialogController(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected List<Input<?>> getInputFields() {
        return Arrays.asList(
                nameInputField,
                capacityInputField,
                flightDistInputField,
                fuelConsInputField,
                planeTypeInputField
        );
    }

    @Override
    public void setItemToEdit(Plane item) {
        this.plane = item;

        nameInputField.setValue(plane.getName());
        capacityInputField.setValue(plane.getCapacity());
        flightDistInputField.setValue(plane.getMaxFlightDistance());
        fuelConsInputField.setValue(plane.getFuelConsumption());
        planeTypeInputField.setValue(plane.getType());
    }

    @Override
    public void onSaveItem() {
        plane.setName(nameInputField.getValue());
        plane.setCapacity(capacityInputField.getValue());
        plane.setMaxFlightDistance(flightDistInputField.getValue());
        plane.setFuelConsumption(fuelConsInputField.getValue());
        plane.setType(planeTypeInputField.getValue());

        context.publishEvent(new SaveItemEvent<>(plane));
    }

    @Override
    public boolean isFormValid() {
        return isFormInputsValid();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        ObservableList<PlaneType> types = FXCollections.observableArrayList();
        types.addAll(PlaneType.values());
        planeTypeField.setItems(types);
        plane = new Plane();

        nameInputField = new TextInputField<>(nameLabel, nameField, String::valueOf);
        capacityInputField = new TextInputField<>(capacityLabel, capacityField, Integer::parseInt);
        flightDistInputField = new TextInputField<>(flightDistLabel, flightDistField, Double::parseDouble);
        fuelConsInputField = new TextInputField<>(fuelConsLabel, fuelConsField, Double::parseDouble);
        planeTypeInputField = new EnumInputField<>(planeTypeLabel, planeTypeField);
    }
}

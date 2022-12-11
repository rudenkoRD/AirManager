package com.rrd.airmanager.controller.pilot;

import com.rrd.airmanager.controller.EditDialogController;
import com.rrd.airmanager.controls.input.DateInputField;
import com.rrd.airmanager.controls.input.EnumInputField;
import com.rrd.airmanager.controls.input.Input;
import com.rrd.airmanager.controls.input.TextInputField;
import com.rrd.airmanager.events.SaveItemEvent;
import com.rrd.airmanager.models.pilot.Pilot;
import com.rrd.airmanager.models.pilot.PilotType;
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
public class PilotDialogController extends EditDialogController<Pilot> implements Initializable {
    private final ApplicationContext context;
    private Pilot pilot;

    @FXML
    public TextField nameField;

    @FXML
    public Label nameLabel;

    @FXML
    public Label birthdayLabel;

    @FXML
    public TextField salaryField;

    @FXML
    public Label salaryLabel;

    @FXML
    public TextField flyHoursField;

    @FXML
    public Label flyHoursLabel;

    @FXML
    public Label pilotTypeLabel;

    @FXML
    public DatePicker birthdayField;

    @FXML
    public ComboBox<PilotType> pilotTypeField;

    private TextInputField<String> nameInputField;
    private TextInputField<Integer> salaryInputField;
    private TextInputField<Double> flyHoursInputField;
    private DateInputField birthdayInputField;
    private EnumInputField<PilotType> pilotTypeInputField;

    @Autowired
    public PilotDialogController(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected List<Input<?>> getInputFields() {
        return Arrays.asList(
                nameInputField,
                salaryInputField,
                flyHoursInputField,
                birthdayInputField,
                pilotTypeInputField
        );
    }

    @Override
    public void setItemToEdit(Pilot item) {
        this.pilot = item;

        nameInputField.setValue(pilot.getName());
        salaryInputField.setValue(pilot.getSalary());
        flyHoursInputField.setValue(pilot.getFlyingHours());
        birthdayInputField.setValue(pilot.getBirthday());
        pilotTypeInputField.setValue(pilot.getPilotType());
    }

    @Override
    public void onSaveItem() {
        pilot.setName(nameInputField.getValue());
        pilot.setSalary(salaryInputField.getValue());
        pilot.setFlyingHours(flyHoursInputField.getValue());
        pilot.setBirthday(birthdayInputField.getValue());
        pilot.setPilotType(pilotTypeInputField.getValue());

        context.publishEvent(new SaveItemEvent<>(pilot));
    }

    @Override
    public boolean isFormValid() {
        return isFormInputsValid();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        ObservableList<PilotType> types = FXCollections.observableArrayList();
        types.addAll(PilotType.values());
        pilotTypeField.setItems(types);
        pilot = new Pilot();

        nameInputField = new TextInputField<>(nameLabel, nameField, String::valueOf);
        salaryInputField = new TextInputField<>(salaryLabel, salaryField, Integer::parseInt);
        flyHoursInputField = new TextInputField<>(flyHoursLabel, flyHoursField, Double::parseDouble);
        birthdayInputField = new DateInputField(birthdayLabel, birthdayField);
        pilotTypeInputField = new EnumInputField<>(pilotTypeLabel, pilotTypeField);
    }
}

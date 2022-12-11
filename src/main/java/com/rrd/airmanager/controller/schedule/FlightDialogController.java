package com.rrd.airmanager.controller.schedule;

import com.rrd.airmanager.controller.EditDialogController;
import com.rrd.airmanager.controls.input.DateTimeInputField;
import com.rrd.airmanager.controls.input.DateTimePicker;
import com.rrd.airmanager.controls.input.Input;
import com.rrd.airmanager.controls.input.ObjectInputField;
import com.rrd.airmanager.events.SaveItemEvent;
import com.rrd.airmanager.models.airport.Airport;
import com.rrd.airmanager.models.flight.Flight;
import com.rrd.airmanager.models.pilot.Pilot;
import com.rrd.airmanager.models.plane.Plane;
import com.rrd.airmanager.services.AppService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class FlightDialogController extends EditDialogController<Flight> implements Initializable {
    private final ApplicationContext context;
    private final AppService<Airport> airportsService;
    private final AppService<Pilot> pilotsService;
    private final AppService<Plane> planesService;

    private Flight flight;

    @FXML
    public Label errorLabel;
    @FXML
    public Label startAirportLabel;
    @FXML
    public Label arrivalAirportLabel;
    @FXML
    public Label startTimeLabel;
    @FXML
    public Label endTimeLabel;
    @FXML
    public Label firstPilotLabel;
    @FXML
    public ComboBox<Pilot> firstPilotField;
    @FXML
    public Label secondPilotLabel;
    @FXML
    public ComboBox<Pilot> secondPilotField;
    @FXML
    public ComboBox<Airport> arrivalAirportField;
    @FXML
    public ComboBox<Airport> startAirportField;
    @FXML
    public DateTimePicker startTimeField;
    @FXML
    public DateTimePicker endTimeField;
    @FXML
    public Label planeLabel;
    @FXML
    public ComboBox<Plane> planeField;

    private DateTimeInputField startTimeInputField;
    private DateTimeInputField endTimeInputField;
    private ObjectInputField<Airport> startAirportInputField;
    private ObjectInputField<Airport> arrivalAirportInputField;
    private ObjectInputField<Pilot> firstPilotInputField;
    private ObjectInputField<Pilot> secondPilotInputField;
    private ObjectInputField<Plane> planeInputField;

    @Autowired
    public FlightDialogController(
            ApplicationContext context,
            AppService<Airport> airportsService,
            AppService<Pilot> pilotsService,
            AppService<Plane> planesService
    ) {
        this.context = context;
        this.airportsService = airportsService;
        this.pilotsService = pilotsService;
        this.planesService = planesService;
    }

    @Override
    protected List<Input<?>> getInputFields() {
        return Arrays.asList(
                startTimeInputField,
                endTimeInputField,
                startAirportInputField,
                arrivalAirportInputField,
                firstPilotInputField,
                secondPilotInputField,
                planeInputField
        );
    }

    @Override
    public void setItemToEdit(Flight flight) {
        this.flight = flight;

        startAirportInputField.setValue(flight.getStartAirport());
        arrivalAirportInputField.setValue(flight.getArrivalAirport());
        startTimeInputField.setValue(flight.getStartTime());
        endTimeInputField.setValue(flight.getEndTime());
        firstPilotInputField.setValue(flight.getFirstPilot());
        secondPilotInputField.setValue(flight.getSecondPilot());
        planeInputField.setValue(flight.getPlane());
    }

    @Override
    public void onSaveItem() {
        flight.setStartAirport(startAirportInputField.getValue());
        flight.setArrivalAirport(arrivalAirportInputField.getValue());
        flight.setStartTime(startTimeInputField.getValue());
        flight.setEndTime(endTimeInputField.getValue());
        flight.setFirstPilot(firstPilotField.getValue());
        flight.setSecondPilot(secondPilotField.getValue());
        flight.setPlane(planeInputField.getValue());

        context.publishEvent(new SaveItemEvent<>(flight));
    }

    @Override
    public boolean isFormValid() {
        if (!isFormInputsValid()) {
            return false;
        }

        boolean isTimeIntervalValid = startTimeInputField.getValue().isBefore(endTimeInputField.getValue());

        if (!isTimeIntervalValid) {
            errorLabel.setText("Invalid flight time!");
        }

        boolean isPilotsValid = true;
        if (secondPilotField.getValue() != null)
            isPilotsValid = firstPilotField.getValue().getId() != secondPilotField.getValue().getId();
        System.out.println(isPilotsValid);

        if (!isPilotsValid) {
            errorLabel.setText("Invalid pilots team!");
        }

        if (isTimeIntervalValid && isPilotsValid) {
            errorLabel.setText("");
        }

        return isTimeIntervalValid && isPilotsValid;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        startTimeInputField = new DateTimeInputField(startTimeLabel, startTimeField);
        endTimeInputField = new DateTimeInputField(endTimeLabel, endTimeField);
        startAirportInputField = new ObjectInputField<>(startAirportLabel, startAirportField, false);
        arrivalAirportInputField = new ObjectInputField<>(arrivalAirportLabel, arrivalAirportField, false);
        firstPilotInputField = new ObjectInputField<>(firstPilotLabel, firstPilotField, false);
        secondPilotInputField = new ObjectInputField<>(secondPilotLabel, secondPilotField, true);
        planeInputField = new ObjectInputField<>(planeLabel, planeField, false);

        flight = new Flight();

        firstPilotField.setItems(FXCollections.observableArrayList(pilotsService.findAll()));
        secondPilotField.setItems(FXCollections.observableArrayList(pilotsService.findAll()));

        startAirportField.setItems(FXCollections.observableArrayList(airportsService.findAll()));
        arrivalAirportField.setItems(FXCollections.observableArrayList(airportsService.findAll()));

        planeField.setItems(FXCollections.observableArrayList(planesService.findAll()));
    }
}

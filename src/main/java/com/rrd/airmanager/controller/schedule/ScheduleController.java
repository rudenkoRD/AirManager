package com.rrd.airmanager.controller.schedule;

import com.rrd.airmanager.AppFXMLLoader;
import com.rrd.airmanager.controller.TableViewController;
import com.rrd.airmanager.controls.selector.SelectorOption;
import com.rrd.airmanager.controls.selector.EnumSelector;
import com.rrd.airmanager.events.CreateOrUpdateItemEvent;
import com.rrd.airmanager.events.DeleteItemsEvent;
import com.rrd.airmanager.events.InfoEvent;
import com.rrd.airmanager.events.SaveItemEvent;
import com.rrd.airmanager.models.flight.Flight;
import com.rrd.airmanager.models.flight.FlightStatus;
import com.rrd.airmanager.services.flights.FlightsService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ScheduleController extends TableViewController<Flight> implements Initializable {
    @FXML
    public ComboBox<SelectorOption<FlightStatus>> flightStatusSelector;
    @Value("classpath:/fxml/schedule/edit_flight.fxml")
    private Resource updateCreateDialog;
    private final AppFXMLLoader fxmlLoader;
    private final FlightsService flightsService;
    private EnumSelector<FlightStatus> enumSelector;

    @Autowired
    public ScheduleController(ApplicationContext context, FlightsService flightsService, AppFXMLLoader fxmlLoader) {
        super(context);
        this.fxmlLoader = fxmlLoader;
        this.flightsService = flightsService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enumSelector = new EnumSelector<>(flightStatusSelector, FlightStatus.class, __ -> loadData());
        super.initialize(url, resourceBundle);
    }

    @Override
    protected List<TableColumn<Flight, ? extends Serializable>> getColumns() {
        TableColumn<Flight, Integer> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Flight, String> startAirportCol = new TableColumn<>("Start airport");
        startAirportCol.setCellValueFactory(new PropertyValueFactory<>("startAirport"));

        TableColumn<Flight, String> arrivalAirportCol = new TableColumn<>("Arrival airport");
        arrivalAirportCol.setCellValueFactory(new PropertyValueFactory<>("arrivalAirport"));

        TableColumn<Flight, LocalDateTime> startTimeCol = new TableColumn<>("Start time");
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        TableColumn<Flight, LocalDateTime> endTimeCol = new TableColumn<>("End time");
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        TableColumn<Flight, String> firstPilotCol = new TableColumn<>("First pilot");
        firstPilotCol.setCellValueFactory(new PropertyValueFactory<>("firstPilot"));

        TableColumn<Flight, String> secondPilotCol = new TableColumn<>("Second pilot");
        secondPilotCol.setCellValueFactory(new PropertyValueFactory<>("secondPilot"));

        TableColumn<Flight, String> planeCol = new TableColumn<>("Plane");
        planeCol.setCellValueFactory(new PropertyValueFactory<>("plane"));

        TableColumn<Flight, FlightStatus> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        return Arrays.asList(
                idCol,
                startAirportCol,
                arrivalAirportCol,
                startTimeCol,
                endTimeCol,
                firstPilotCol,
                secondPilotCol,
                planeCol,
                statusCol
        );
    }

    @Override
    protected Service<List<Flight>> getLoadAllAppService() {
        return new Service<>() {
            @Override
            protected Task<List<Flight>> createTask() {
                return new Task<>() {
                    @Override
                    public List<Flight> call() {
                        FlightStatus status = enumSelector.getSelection();
                        return status == null ? flightsService.findAll() : flightsService.findByStatus(status);
                    }
                };
            }
        };
    }

    @Override
    public void onCreateOrUpdateItemEvent(CreateOrUpdateItemEvent<Flight> event) {
        context.publishEvent(new InfoEvent("Opened flight editing dialog"));
        fxmlLoader.loadEditDialog(updateCreateDialog, event.getItem());
    }

    @Override
    public void onSaveItemEvent(SaveItemEvent<Flight> saveEvent) {
        flightsService.save(saveEvent.getItem());
        context.publishEvent(new InfoEvent("Saved flight"));
        loadData();
    }

    @Override
    public void onDeleteItemEvent(DeleteItemsEvent<Flight> deleteEvent) {
        flightsService.deleteInBatch(deleteEvent.getItems());
        context.publishEvent(new InfoEvent("Deleted flight(s)"));
        loadData();
    }

    @Override
    protected Flight defaultNewItem() {
        return new Flight();
    }
}

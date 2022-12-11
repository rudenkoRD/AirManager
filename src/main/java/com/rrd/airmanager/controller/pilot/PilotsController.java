package com.rrd.airmanager.controller.pilot;

import com.rrd.airmanager.AppFXMLLoader;
import com.rrd.airmanager.controls.selector.SelectorOption;
import com.rrd.airmanager.controls.selector.EnumSelector;
import com.rrd.airmanager.events.CreateOrUpdateItemEvent;
import com.rrd.airmanager.events.DeleteItemsEvent;
import com.rrd.airmanager.events.InfoEvent;
import com.rrd.airmanager.events.SaveItemEvent;
import com.rrd.airmanager.controller.TableViewController;
import com.rrd.airmanager.models.pilot.Pilot;
import com.rrd.airmanager.models.pilot.PilotType;
import com.rrd.airmanager.services.pilots.PilotsService;
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
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class PilotsController extends TableViewController<Pilot> implements Initializable {
    @FXML
    public ComboBox<SelectorOption<PilotType>> pilotTypeSelector;
    @Value("classpath:/fxml/pilots/edit_pilot.fxml")
    private Resource updateCreateDialog;
    private final AppFXMLLoader fxmlLoader;
    private final PilotsService pilotsService;
    private EnumSelector<PilotType> pilotTypeEnumSelector;

    @Autowired
    public PilotsController(ApplicationContext context, PilotsService pilotsService, AppFXMLLoader fxmlLoader) {
        super(context);
        this.fxmlLoader = fxmlLoader;
        this.pilotsService = pilotsService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pilotTypeEnumSelector = new EnumSelector<>(pilotTypeSelector, PilotType.class, __ -> loadData());
        super.initialize(url, resourceBundle);
    }

    @Override
    protected List<TableColumn<Pilot, ? extends Serializable>> getColumns() {
        TableColumn<Pilot, Integer> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Pilot, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Pilot, LocalDateTime> birthdayCol = new TableColumn<>("Birthday");
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        TableColumn<Pilot, Integer> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        TableColumn<Pilot, Double> flyHoursCol = new TableColumn<>("Fly hours");
        flyHoursCol.setCellValueFactory(new PropertyValueFactory<>("flyingHours"));

        TableColumn<Pilot, PilotType> typeCol = new TableColumn<>("Pilot type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("pilotType"));

        return Arrays.asList(idCol, nameCol, birthdayCol, salaryCol, flyHoursCol, typeCol);
    }

    @Override
    protected Service<List<Pilot>> getLoadAllAppService() {
        return new Service<>() {
            @Override
            protected Task<List<Pilot>> createTask() {
                return new Task<>() {
                    @Override
                    public List<Pilot> call() {
                        PilotType pilotType = pilotTypeEnumSelector.getSelection();
                        return pilotType == null ? pilotsService.findAll() : pilotsService.findByType(pilotType);
                    }
                };
            }
        };
    }

    @Override
    public void onCreateOrUpdateItemEvent(CreateOrUpdateItemEvent<Pilot> event) {
        context.publishEvent(new InfoEvent("Opened pilot editing dialog"));
        fxmlLoader.loadEditDialog(updateCreateDialog, event.getItem());
    }

    @Override
    public void onSaveItemEvent(SaveItemEvent<Pilot> saveEvent) {
        pilotsService.save(saveEvent.getItem());
        context.publishEvent(new InfoEvent("Saved pilot"));
        loadData();
    }

    @EventListener
    public void onDeleteItemEvent(DeleteItemsEvent<Pilot> deleteEvent) {
        pilotsService.deleteInBatch(deleteEvent.getItems());
        context.publishEvent(new InfoEvent("Deleted pilot(s)"));
        loadData();
    }

    @Override
    protected Pilot defaultNewItem() {
        return new Pilot();
    }
}

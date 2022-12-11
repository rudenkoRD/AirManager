package com.rrd.airmanager.controller.airport;

import com.rrd.airmanager.AppFXMLLoader;
import com.rrd.airmanager.events.CreateOrUpdateItemEvent;
import com.rrd.airmanager.events.DeleteItemsEvent;
import com.rrd.airmanager.events.InfoEvent;
import com.rrd.airmanager.events.SaveItemEvent;
import com.rrd.airmanager.controller.TableViewController;
import com.rrd.airmanager.models.airport.Airport;
import com.rrd.airmanager.services.airports.AirportsService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Component
public class AirportsController extends TableViewController<Airport> implements Initializable {
    @Value("classpath:/fxml/airports/edit_airport.fxml")
    private Resource updateCreateDialog;
    private final AppFXMLLoader fxmlLoader;
    private final AirportsService airportsService;

    @Autowired
    public AirportsController(ApplicationContext context, AirportsService airportsService, AppFXMLLoader fxmlLoader) {
        super(context);
        this.fxmlLoader = fxmlLoader;
        this.airportsService = airportsService;
    }

    @Override
    protected List<TableColumn<Airport, ? extends Serializable>> getColumns() {
        TableColumn<Airport, Integer> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Airport, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        TableColumn<Airport, Integer> maxCapacityCol = new TableColumn<>("Max capacity");
        maxCapacityCol.setCellValueFactory(new PropertyValueFactory<>("maxCapacity"));

        return Arrays.asList(idCol, locationCol, maxCapacityCol);
    }

    @Override
    protected Service<List<Airport>> getLoadAllAppService() {
        return new Service<>() {
            @Override
            protected Task<List<Airport>> createTask() {
                return new Task<>() {
                    @Override
                    public List<Airport> call() {
                        return airportsService.findAll();
                    }
                };
            }
        };
    }

    @Override
    protected Airport defaultNewItem() {
        return new Airport();
    }

    @Override
    public void onCreateOrUpdateItemEvent(CreateOrUpdateItemEvent<Airport> event) {
        context.publishEvent(new InfoEvent("Opened airport editing dialog"));
        fxmlLoader.loadEditDialog(updateCreateDialog, event.getItem());
    }

    @Override
    public void onSaveItemEvent(SaveItemEvent<Airport> saveEvent) {
        airportsService.save(saveEvent.getItem());
        context.publishEvent(new InfoEvent("Saved airport"));
        loadData();
    }

    @Override
    public void onDeleteItemEvent(DeleteItemsEvent<Airport> deleteEvent) {
        airportsService.deleteInBatch(deleteEvent.getItems());
        context.publishEvent(new InfoEvent("Deleted airport(s)"));
        loadData();
    }
}

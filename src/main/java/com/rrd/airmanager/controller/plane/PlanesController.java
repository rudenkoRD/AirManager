package com.rrd.airmanager.controller.plane;

import com.rrd.airmanager.AppFXMLLoader;
import com.rrd.airmanager.controller.TableViewController;
import com.rrd.airmanager.controls.selector.SelectorOption;
import com.rrd.airmanager.controls.selector.EnumSelector;
import com.rrd.airmanager.events.CreateOrUpdateItemEvent;
import com.rrd.airmanager.events.DeleteItemsEvent;
import com.rrd.airmanager.events.InfoEvent;
import com.rrd.airmanager.events.SaveItemEvent;
import com.rrd.airmanager.models.plane.Plane;
import com.rrd.airmanager.models.plane.PlaneFlightStatus;
import com.rrd.airmanager.models.plane.PlaneType;
import com.rrd.airmanager.services.planes.PlanesService;
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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class PlanesController extends TableViewController<Plane> implements Initializable {
    @FXML
    public ComboBox<SelectorOption<PlaneType>> planeTypeSelector;
    @FXML
    public ComboBox<SelectorOption<PlaneFlightStatus>> planeFlightStatusSelector;
    @Value("classpath:/fxml/planes/edit_plane.fxml")
    private Resource updateCreateDialog;
    private final AppFXMLLoader fxmlLoader;
    private final PlanesService planesService;
    private EnumSelector<PlaneType> planeTypeEnumSelector;
    private EnumSelector<PlaneFlightStatus> planeFlightStatusEnumSelector;

    @Autowired
    public PlanesController(ApplicationContext context, PlanesService planesService, AppFXMLLoader fxmlLoader) {
        super(context);
        this.fxmlLoader = fxmlLoader;
        this.planesService = planesService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        planeTypeEnumSelector = new EnumSelector<>(planeTypeSelector, PlaneType.class, __ -> loadData());
        planeFlightStatusEnumSelector = new EnumSelector<>(
                planeFlightStatusSelector,
                PlaneFlightStatus.class,
                __ -> loadData()
        );

        super.initialize(url, resourceBundle);
    }

    @Override
    protected List<TableColumn<Plane, ? extends Serializable>> getColumns() {
        TableColumn<Plane, Integer> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Plane, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Plane, PlaneType> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Plane, Double> fuelConsumptionCol = new TableColumn<>("Fuel consumption");
        fuelConsumptionCol.setCellValueFactory(new PropertyValueFactory<>("fuelConsumption"));

        TableColumn<Plane, Integer> capacityCol = new TableColumn<>("Capacity");
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        TableColumn<Plane, Double> flightDistanceCol = new TableColumn<>("Flight distance");
        flightDistanceCol.setCellValueFactory(new PropertyValueFactory<>("maxFlightDistance"));

        return Arrays.asList(idCol, nameCol, typeCol, fuelConsumptionCol, capacityCol, flightDistanceCol);
    }

    @Override
    protected Service<List<Plane>> getLoadAllAppService() {
        return new Service<>() {
            @Override
            protected Task<List<Plane>> createTask() {
                return new Task<>() {
                    @Override
                    public List<Plane> call() {
                        PlaneType type = planeTypeEnumSelector.getSelection();
                        PlaneFlightStatus status = planeFlightStatusEnumSelector.getSelection();
                        return planesService.findByFlightStatusAndType(
                                status == null ? null : status.name(),
                                type == null ? null : type.name()
                        );
                    }
                };
            }
        };
    }

    @Override
    public void onCreateOrUpdateItemEvent(CreateOrUpdateItemEvent<Plane> event) {
        context.publishEvent(new InfoEvent("Opened plane editing dialog"));
        fxmlLoader.loadEditDialog(updateCreateDialog, event.getItem());
    }

    @Override
    public void onSaveItemEvent(SaveItemEvent<Plane> saveEvent) {
        planesService.save(saveEvent.getItem());
        context.publishEvent(new InfoEvent("Saved plane"));
        loadData();
    }

    @Override
    public void onDeleteItemEvent(DeleteItemsEvent<Plane> deleteEvent) {
        planesService.deleteInBatch(deleteEvent.getItems());
        context.publishEvent(new InfoEvent("Deleted plane(s)"));
        loadData();
    }

    @Override
    protected Plane defaultNewItem() {
        return new Plane();
    }
}

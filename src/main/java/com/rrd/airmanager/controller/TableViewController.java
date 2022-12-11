package com.rrd.airmanager.controller;

import com.rrd.airmanager.events.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class TableViewController<T> implements Initializable {
    protected ApplicationContext context;
    protected final TableView<T> tableView = new TableView<>();

    @FXML
    protected BorderPane pane;

    @FXML
    public Button updateButton;

    @FXML
    public Button deleteButton;
    private final ObservableList<T> objectsList = FXCollections.observableArrayList();

    public TableViewController(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showLoading();
        listenSelectedItems();
        setUpTable();
        loadData();
    }

    private void listenSelectedItems() {
        deleteButton.visibleProperty().bind(Bindings.isNotEmpty(tableView.getSelectionModel().getSelectedItems()));
        updateButton.visibleProperty().bind(Bindings.isNotEmpty(tableView.getSelectionModel().getSelectedItems()));
    }

    private void showLoading() {
        pane.setCenter(new ProgressIndicator());
    }

    protected void loadData() {
        Service<List<T>> loadAllAppService = getLoadAllAppService();
        System.out.println(loadAllAppService);

        loadAllAppService.setOnFailed(e -> context.publishEvent(
                new ErrorEvent("Failed to load item(s)",
                        loadAllAppService.getException()
                )));

        loadAllAppService.setOnSucceeded(e -> {
            objectsList.clear();
            objectsList.addAll(loadAllAppService.getValue());
            tableView.sort();
            pane.setCenter(tableView);
        });

        loadAllAppService.start();
    }

    abstract protected List<TableColumn<T, ? extends Serializable>> getColumns();

    abstract protected Service<List<T>> getLoadAllAppService();

    @EventListener
    abstract public void onCreateOrUpdateItemEvent(CreateOrUpdateItemEvent<T> event);

    private void setUpTable() {
        tableView.getColumns().setAll(getColumns());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setItems(objectsList);
        tableView.setPlaceholder(new Label("No Data!"));
    }

    abstract protected T defaultNewItem();

    @FXML
    protected void delete() {
        List<T> selectedItems = tableView.getSelectionModel().getSelectedItems();
        if (selectedItems.isEmpty()) return;

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent() && action.get() == ButtonType.OK) {
            try {
                context.publishEvent(new DeleteItemsEvent<>(selectedItems));
            } catch (Exception e) {
                context.publishEvent(new ErrorEvent("Failed to delete item(s)", e));
            }
        }
    }

    @FXML
    protected void add() {
        context.publishEvent(new CreateOrUpdateItemEvent<>(defaultNewItem()));
    }

    @FXML
    protected void update() {
        T selectedItem = tableView.getSelectionModel().getSelectedItem();
        context.publishEvent(new CreateOrUpdateItemEvent<>(selectedItem));
    }

    @EventListener
    public abstract void onSaveItemEvent(SaveItemEvent<T> saveEvent);

    @EventListener
    public abstract void onDeleteItemEvent(DeleteItemsEvent<T> deleteEvent);
}

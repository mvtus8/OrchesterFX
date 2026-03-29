package com.example.orchesterfx;

import com.example.orchesterfx.model.DychovyNastroj;
import com.example.orchesterfx.model.Nastroj;
import com.example.orchesterfx.model.RytmickyNastroj;
import com.example.orchesterfx.util.JsonLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TableView<Nastroj> instrumentTableView;

    @FXML
    private TableColumn<Nastroj, String> druhColumn;

    @FXML
    private TableColumn<Nastroj, Integer> ksColumn;

    @FXML
    private TableColumn<Nastroj, Double> cenaColumn;

    @FXML
    private TableColumn<Nastroj, String> zvukColumn;

    @FXML
    private TableColumn<Nastroj, Integer> pocetZvukovColumn;

    @FXML
    private TableColumn<Nastroj, String> laDenieColumn;

    @FXML
    private TableColumn<Nastroj, Integer> pocetDierColumn;

    @FXML
    public void initialize() {
        // Set up table columns with property value factories
        druhColumn.setCellValueFactory(new PropertyValueFactory<>("druh"));
        ksColumn.setCellValueFactory(new PropertyValueFactory<>("pocet"));
        cenaColumn.setCellValueFactory(new PropertyValueFactory<>("cena"));
        zvukColumn.setCellValueFactory(new PropertyValueFactory<>("zvuk"));

        // For columns that don't exist on base class, we need custom cell factories
        pocetZvukovColumn.setCellValueFactory(cellData -> {
            Nastroj nastroj = cellData.getValue();
            if (nastroj instanceof RytmickyNastroj) {
                RytmickyNastroj rytmicky = (RytmickyNastroj) nastroj;
                return javafx.beans.binding.Bindings.createObjectBinding(() -> rytmicky.getPocetZvukov());
            }
            return javafx.beans.binding.Bindings.createObjectBinding(() -> null);
        });

        laDenieColumn.setCellValueFactory(cellData -> {
            Nastroj nastroj = cellData.getValue();
            if (nastroj instanceof DychovyNastroj) {
                DychovyNastroj dychovy = (DychovyNastroj) nastroj;
                return javafx.beans.binding.Bindings.createObjectBinding(() -> dychovy.getLadenie());
            }
            return javafx.beans.binding.Bindings.createObjectBinding(() -> null);
        });

        pocetDierColumn.setCellValueFactory(cellData -> {
            Nastroj nastroj = cellData.getValue();
            if (nastroj instanceof DychovyNastroj) {
                DychovyNastroj dychovy = (DychovyNastroj) nastroj;
                return javafx.beans.binding.Bindings.createObjectBinding(() -> dychovy.getPocetDier());
            }
            return javafx.beans.binding.Bindings.createObjectBinding(() -> null);
        });

        // Load instruments from JSON
        try {
            String filePath = "nastroje.json";
            instrumentTableView.setItems(JsonLoader.loadInstruments(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}

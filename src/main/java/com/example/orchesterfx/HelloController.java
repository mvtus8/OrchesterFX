package com.example.orchesterfx;

import com.example.orchesterfx.model.DychovyNastroj;
import com.example.orchesterfx.model.Nastroj;
import com.example.orchesterfx.model.RytmickyNastroj;
import com.example.orchesterfx.util.JsonLoader;
import com.example.orchesterfx.util.JsonSaver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class HelloController {
    @FXML private Label welcomeText;

    @FXML private TableView<Nastroj> instrumentTableView;

    @FXML private TableColumn<Nastroj, String> druhColumn;

    @FXML private TableColumn<Nastroj, Integer> ksColumn;

    @FXML private TableColumn<Nastroj, Double> cenaColumn;

    @FXML private TableColumn<Nastroj, String> zvukColumn;

    @FXML private TableColumn<Nastroj, Integer> pocetZvukovColumn;

    @FXML private TableColumn<Nastroj, String> laDenieColumn;

    @FXML private TableColumn<Nastroj, Integer> pocetDierColumn;

    @FXML private TextField inputNazov;

    @FXML private TextField inputKs;

    @FXML private TextField inputCena;

    @FXML private Button cenabtn;
    @FXML private Button hrajbtn;
    @FXML private Button zoznambtn;
    @FXML private TextArea outputArea;

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

        // Prefill inputs when selection changes
        instrumentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, sel) -> {
            if (sel != null) {
                try {
                    inputNazov.setText(sel.getDruh());
                    inputKs.setText(String.valueOf(sel.getPocet()));
                    inputCena.setText(String.valueOf(sel.getCena()));
                } catch (Exception ex) {
                    inputNazov.clear();
                    inputKs.clear();
                    inputCena.clear();
                }
            } else {
                inputNazov.clear();
                inputKs.clear();
                inputCena.clear();
            }
        });
    }

    @FXML
    protected void onAddInstrument() {
        Dialog<Nastroj> dialog = new Dialog<>();
        dialog.setTitle("Pridať nástroj");
        ButtonType addButtonType = new ButtonType("Pridať", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<String> category = new ComboBox<>(FXCollections.observableArrayList("rytmicke", "dychove"));
        category.setValue("rytmicke");
        TextField nazovField = new TextField();
        TextField cenaField = new TextField();
        TextField zvukField = new TextField();
        TextField ksField = new TextField();
        TextField zvukyField = new TextField();
        TextField pocetDierField = new TextField();
        TextField ladenieField = new TextField();

        Label categoryLabel = new Label("Kategória:");
        Label nazovLabel = new Label("Názov:");
        Label cenaLabel = new Label("Cena:");
        Label zvukLabel = new Label("Zvuk:");
        Label ksLabel = new Label("Ks:");
        Label zvukyLabel = new Label("Zvuky:");
        Label pocetDierLabel = new Label("Počet dier:");
        Label ladenieLabel = new Label("Ladenie:");

        grid.add(categoryLabel, 0, 0);
        grid.add(category, 1, 0);
        grid.add(nazovLabel, 0, 1);
        grid.add(nazovField, 1, 1);
        grid.add(cenaLabel, 0, 2);
        grid.add(cenaField, 1, 2);
        grid.add(zvukLabel, 0, 3);
        grid.add(zvukField, 1, 3);
        grid.add(ksLabel, 0, 4);
        grid.add(ksField, 1, 4);
        grid.add(zvukyLabel, 0, 5);
        grid.add(zvukyField, 1, 5);
        grid.add(pocetDierLabel, 0, 6);
        grid.add(pocetDierField, 1, 6);
        grid.add(ladenieLabel, 0, 7);
        grid.add(ladenieField, 1, 7);

        Runnable updateVisibility = () -> {
            boolean rytmicke = "rytmicke".equals(category.getValue());
            zvukyLabel.setVisible(rytmicke);
            zvukyLabel.setManaged(rytmicke);
            zvukyField.setVisible(rytmicke);
            zvukyField.setManaged(rytmicke);

            pocetDierLabel.setVisible(!rytmicke);
            pocetDierLabel.setManaged(!rytmicke);
            pocetDierField.setVisible(!rytmicke);
            pocetDierField.setManaged(!rytmicke);

            ladenieLabel.setVisible(!rytmicke);
            ladenieLabel.setManaged(!rytmicke);
            ladenieField.setVisible(!rytmicke);
            ladenieField.setManaged(!rytmicke);
        };

        updateVisibility.run();
        category.valueProperty().addListener((obs, oldV, newV) -> updateVisibility.run());

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    String kategoria = category.getValue();
                    String nazov = nazovField.getText().trim();
                    double cena = Double.parseDouble(cenaField.getText().trim());
                    String zvuk = zvukField.getText().trim();
                    int ks = Integer.parseInt(ksField.getText().trim());
                    if (kategoria.equals("rytmicke")) {
                        int zvuky = Integer.parseInt(zvukyField.getText().trim());
                        return new RytmickyNastroj(nazov, cena, zvuk, ks, zvuky);
                    } else {
                        int pocetDier = 0;
                        String pd = pocetDierField.getText().trim();
                        if (!pd.isEmpty())
                            pocetDier = Integer.parseInt(pd);
                        String ladenie = ladenieField.getText().trim();
                        if (ladenie.isEmpty())
                            ladenie = "C";
                        return new DychovyNastroj(nazov, cena, zvuk, ks, pocetDier, ladenie);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }
            }
            return null;
        });

        Optional<Nastroj> result = dialog.showAndWait();
        result.ifPresent(nastroj -> {
            instrumentTableView.getItems().add(nastroj);
            try {
                JsonSaver.saveInstruments(java.nio.file.Path.of("nastroje.json"), instrumentTableView.getItems());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onDeleteInstrument() {
        Nastroj selected = instrumentTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Zmazať nástroj");
            alert.setHeaderText(null);
            alert.setContentText("Nie je vybraný žiadny nástroj.");
            alert.showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Potvrdiť zmazanie");
        confirm.setHeaderText(null);
        confirm.setContentText("Naozaj zmazať vybraný nástroj: " + selected.getDruh() + "?");
        Optional<ButtonType> res = confirm.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            instrumentTableView.getItems().remove(selected);
            try {
                JsonSaver.saveInstruments(java.nio.file.Path.of("nastroje.json"), instrumentTableView.getItems());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    protected void onUpdateInstrument() {
        Nastroj selected = instrumentTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upraviť nástroj");
            alert.setHeaderText(null);
            alert.setContentText("Nie je vybraný žiadny nástroj.");
            alert.showAndWait();
            return;
        }

        try {
            String newName = inputNazov.getText().trim();
            int newKs = Integer.parseInt(inputKs.getText().trim());
            double newCena = Double.parseDouble(inputCena.getText().trim());

            selected.setDruh(newName);
            selected.setPocet(newKs);
            selected.setCena(newCena);

            instrumentTableView.refresh();

            try {
                JsonSaver.saveInstruments(java.nio.file.Path.of("nastroje.json"), instrumentTableView.getItems());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba vstupu");
            alert.setHeaderText(null);
            alert.setContentText("Zadajte platné čísla pre ks a cenu.");
            alert.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    protected void onCenaSkladu() {
        var items = instrumentTableView.getItems();
        if (items == null || items.isEmpty()) {
            outputArea.setText("Žiadne nástroje v sklade.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        double total = 0.0;
        for (Nastroj n : items) {
            double subtotal = n.getCena() * n.getPocet();
            sb.append(String.format("%s — ks: %d × cena: %.2f = %.2f\n", n.getDruh(), n.getPocet(), n.getCena(),
                    subtotal));
            total += subtotal;
        }
        sb.append(String.format("\nCelková hodnota skladu: %.2f", total));
        outputArea.setText(sb.toString());
    }
    @FXML
    protected void onHrajBtnClick() {

        var items = instrumentTableView.getItems();

        if (items == null || items.isEmpty()) {
            outputArea.setText("Žiadne nástroje v sklade.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (Nastroj n : items) {
            sb.append(String.format("%s -> %s\n", n.getDruh(), n.getZvuk()));
        }
        outputArea.setText(sb.toString());
    }
    @FXML
    protected void onZoznamBtnClick() {
        Platform.exit();
        var items = instrumentTableView.getItems();

        if (items == null || items.isEmpty()) {
            outputArea.setText("Žiadne nástroje v sklade.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (Nastroj n : items) {
            sb.append(String.format("%s -> %s\n", n.getDruh(), n.getZvuk()));
        }
        outputArea.setText(sb.toString());
    }
}

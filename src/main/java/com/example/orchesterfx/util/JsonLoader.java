package com.example.orchesterfx.util;

import com.example.orchesterfx.model.DychovyNastroj;
import com.example.orchesterfx.model.Nastroj;
import com.example.orchesterfx.model.RytmickyNastroj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonLoader {

    public static ObservableList<Nastroj> loadInstruments(String filePath) throws IOException {
        ObservableList<Nastroj> instruments = FXCollections.observableArrayList();

        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));

        // Extract all instrument objects from the JSON array
        Pattern objectPattern = Pattern.compile("\\{[^{}]*(?:\\{[^{}]*\\}[^{}]*)*\\}");
        Matcher matcher = objectPattern
                .matcher(jsonContent.substring(jsonContent.indexOf("hudobne_nastroje"), jsonContent.lastIndexOf("}")));

        while (matcher.find()) {
            String jsonObj = matcher.group();
            try {
                String kategoria = extractString(jsonObj, "kategoria");
                String nazov = extractString(jsonObj, "nazov");
                double cena = extractDouble(jsonObj, "cena");
                String zvuk = extractString(jsonObj, "zvuk");
                int ks = extractInt(jsonObj, "ks");

                if (kategoria.equals("rytmicke")) {
                    int zvuky = extractInt(jsonObj, "zvuky");
                    instruments.add(new RytmickyNastroj(nazov, cena, zvuk, ks, zvuky));
                } else if (kategoria.equals("dychove")) {
                    int zvuky = extractInt(jsonObj, "zvuky");
                    int pocetDier = extractInt(jsonObj, "pocetDier");
                    if (pocetDier == 0)
                        pocetDier = zvuky; // fallback when JSON lacks explicit pocetDier
                    String ladenie = extractString(jsonObj, "ladenie");
                    if (ladenie.isEmpty())
                        ladenie = "C";
                    DychovyNastroj dychovy = new DychovyNastroj(nazov, cena, zvuk, ks, pocetDier, ladenie);
                    instruments.add(dychovy);
                }
            } catch (Exception e) {
                System.err.println("Error parsing instrument: " + e.getMessage());
            }
        }

        return instruments;
    }

    private static String extractString(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private static double extractDouble(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*([\\d.]+)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return 0.0;
    }

    private static int extractInt(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }
}

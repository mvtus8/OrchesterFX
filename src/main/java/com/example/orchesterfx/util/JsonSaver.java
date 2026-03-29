package com.example.orchesterfx.util;

import com.example.orchesterfx.model.DychovyNastroj;
import com.example.orchesterfx.model.Nastroj;
import com.example.orchesterfx.model.RytmickyNastroj;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class JsonSaver {

    private static String esc(String s) {
        if (s == null)
            return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public static void saveInstruments(Path file, ObservableList<Nastroj> items) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\t\"hudobne_nastroje\": [\n");

        for (int i = 0; i < items.size(); i++) {
            Nastroj n = items.get(i);
            sb.append("\t\t{");
            if (n instanceof RytmickyNastroj) {
                RytmickyNastroj r = (RytmickyNastroj) n;
                sb.append(String.format("\"kategoria\": \"rytmicke\", "));
                sb.append(String.format("\"nazov\": \"%s\", ", esc(r.getDruh())));
                sb.append(String.format("\"cena\": %s, ", r.getCena()));
                sb.append(String.format("\"zvuk\": \"%s\", ", esc(r.getZvuk())));
                sb.append(String.format("\"ks\": %d, ", r.getPocet()));
                sb.append(String.format("\"zvuky\": %d", r.getPocetZvukov()));
            } else if (n instanceof DychovyNastroj) {
                DychovyNastroj d = (DychovyNastroj) n;
                sb.append(String.format("\"kategoria\": \"dychove\", "));
                sb.append(String.format("\"nazov\": \"%s\", ", esc(d.getDruh())));
                sb.append(String.format("\"cena\": %s, ", d.getCena()));
                sb.append(String.format("\"zvuk\": \"%s\", ", esc(d.getZvuk())));
                sb.append(String.format("\"ks\": %d, ", d.getPocet()));
                sb.append(String.format("\"zvuky\": %d, ", Math.max(1, d.getPocetDier())));
                sb.append(String.format("\"ladenie\": \"%s\"", esc(d.getLadenie())));
            } else {
                // generic Nastroj
                sb.append(String.format("\"kategoria\": \"unknown\", "));
                sb.append(String.format("\"nazov\": \"%s\", ", esc(n.getDruh())));
                sb.append(String.format("\"cena\": %s, ", n.getCena()));
                sb.append(String.format("\"zvuk\": \"%s\", ", esc(n.getZvuk())));
                sb.append(String.format("\"ks\": %d", n.getPocet()));
            }

            sb.append("}");
            if (i < items.size() - 1)
                sb.append(",");
            sb.append("\n");
        }

        sb.append("\t]\n}");

        // Write atomically
        Path tmp = file.resolveSibling(file.getFileName().toString() + ".tmp");
        Files.writeString(tmp, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        Files.move(tmp, file, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }
}

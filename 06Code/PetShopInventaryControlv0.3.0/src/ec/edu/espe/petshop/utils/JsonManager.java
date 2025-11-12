package ec.edu.espe.petshop.utils;
//
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import ec.edu.espe.petshop.model.Product;
import ec.edu.espe.petshop.model.Movement;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;

public class JsonManager {
    private final Path productsPath;
    private final Path movementsPath;
    private final Gson gson;

    public JsonManager(String productsFile, String movementsFile) {
        productsPath = Paths.get(productsFile);
        movementsPath = Paths.get(movementsFile);
        gson = new GsonBuilder().setPrettyPrinting().create();
        ensureFiles();
    }

    private void ensureFiles() {
        try {
            if (!Files.exists(productsPath)) Files.write(productsPath, "[]".getBytes(), StandardOpenOption.CREATE);
            if (!Files.exists(movementsPath)) Files.write(movementsPath, "[]".getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.out.println("Error creando archivos JSON: " + e.getMessage());
        }
    }

    public List<Product> readProducts() {
        try (Reader r = Files.newBufferedReader(productsPath)) {
            Type listType = new TypeToken<List<Product>>(){}.getType();
            List<Product> list = gson.fromJson(r, listType);
            return list == null ? new ArrayList<>() : list;
        } catch (IOException e) {
            System.out.println("Error leyendo products.json: " + e.getMessage());
            return new ArrayList<>();
        } catch (JsonSyntaxException e) {
            System.out.println("JSON inválido en products.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveProducts(List<Product> products) {
        try (Writer w = Files.newBufferedWriter(productsPath, StandardOpenOption.TRUNCATE_EXISTING)) {
            gson.toJson(products, w);
        } catch (IOException e) {
            System.out.println("Error guardando products.json: " + e.getMessage());
        }
    }

    public List<Movement> readMovements() {
        try (Reader r = Files.newBufferedReader(movementsPath)) {
            Type listType = new TypeToken<List<Movement>>(){}.getType();
            List<Movement> list = gson.fromJson(r, listType);
            return list == null ? new ArrayList<>() : list;
        } catch (IOException e) {
            System.out.println("Error leyendo movements.json: " + e.getMessage());
            return new ArrayList<>();
        } catch (JsonSyntaxException e) {
            System.out.println("JSON inválido en movements.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveMovements(List<Movement> movements) {
        try (Writer w = Files.newBufferedWriter(movementsPath, StandardOpenOption.TRUNCATE_EXISTING)) {
            gson.toJson(movements, w);
        } catch (IOException e) {
            System.out.println("Error guardando movements.json: " + e.getMessage());
        }
    }

    public void appendMovement(Movement m) {
        List<Movement> movs = readMovements();
        movs.add(m);
        saveMovements(movs);
    }
}


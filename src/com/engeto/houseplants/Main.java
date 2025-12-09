package com.engeto.houseplants;

import com.engeto.houseplants.exception.PlantException;
import com.engeto.houseplants.model.Plant;
import com.engeto.houseplants.service.PlantManager;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        PlantManager manager = new PlantManager();

        try {
            System.out.println("--- 1. Loading plants ---");
            manager.loadPlantsFromFile("resources/kvetiny.txt");

            System.out.println("\n--- 2. Watering info ---");
            for (Plant plant : manager.copy()) {
                System.out.println(plant.getWateringInfo());
            }

            System.out.println("\n--- 3. Adding new plant ---");
            Plant bazalka = new Plant("Bazalka", "V kuchyni", 3, LocalDate.now(), LocalDate.now());
            manager.addPlant(bazalka);

            System.out.println("\n--- 4. Adding 10 tulips ---");
            for (int i = 1; i <= 10; i++) {
                Plant tulipan = new Plant("Tulipán na prodej " + i, 14);
                manager.addPlant(tulipan);
            }

            System.out.println("\n--- 5. Removing 3. item ---");
            Plant plantToRemove = manager.getPlant(2);
            System.out.println("Removing: " + plantToRemove.getName());
            manager.removePlant(plantToRemove);

            System.out.println("\n--- 6. Saving updated file ---");
            manager.savePlantsToFile("resources/vystup.txt");

            System.out.println("\n--- 7. File re-load ---");
            PlantManager manager2 = new PlantManager();
            manager2.loadPlantsFromFile("resources/vystup.txt");

            for (Plant p : manager2.copy()) {
                System.out.println("Loaded: " + p.getName());
            }

            System.out.println("\n--- 8. Plants sorting ---");

            System.out.println(">> By name (A-Z):");
            manager2.sortPlantsByName();
            manager2.copy().forEach(p -> System.out.println(p.getName()));

            System.out.println("\n>> By watering info: ");
            manager2.sortPlantsByWatering();
            manager2.copy().forEach(p -> System.out.println(p.getName() + " (Watering: " + p.getWatering() + ")"));


        } catch (PlantException e) {
            System.err.println("Error message: " + e.getMessage());
        }
    }
}
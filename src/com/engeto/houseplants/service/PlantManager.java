package com.engeto.houseplants.service;

import com.engeto.houseplants.config.Settings;
import com.engeto.houseplants.exception.PlantException;
import com.engeto.houseplants.model.Plant;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class PlantManager {
    private List<Plant> plantList = new ArrayList<>();

    public void addPlant(Plant plant) {
        plantList.add(plant);
    }

    public void addPlants(List<Plant> plants) {
        plantList.addAll(plants);
    }

    public Plant getPlant(int index) {
        return plantList.get(index);
    }

    public void removePlant(Plant plant) {
        plantList.remove(plant);
    }

    public List<Plant> copy() {
        return new ArrayList<>(plantList);
    }

    public List<Plant> getPlantsToWater() {
        List<Plant> plantsToWater = new ArrayList<>();

        for(Plant plant : plantList){
            if(plant.getWatering().plusDays(plant.getFrequencyOfWatering()).isBefore(LocalDate.now())){
                plantsToWater.add(plant);
            }
        }
        return plantsToWater;
    }

    public void loadPlantsFromFile(String filename) throws PlantException {
        int lineNumber = 0;

        try (Scanner scanner = new Scanner(new File(filename))) {

            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();

                String[] parts = line.split(Settings.getDelimiter());

                if (parts.length != 5) {
                    throw new PlantException(
                            "Line " + lineNumber + ": Expected 5 items on line, found " + parts.length + "\n" + line
                    );
                }

                try {
                    String name = parts[0];
                    String notes = parts[1];
                    int frequency = Integer.parseInt(parts[2]);
                    LocalDate watering = LocalDate.parse(parts[3]);
                    LocalDate planted = LocalDate.parse(parts[4]);

                    Plant newPlant = new Plant(name, notes, frequency, planted, watering);
                    plantList.add(newPlant);

                } catch (NumberFormatException e) {
                    throw new PlantException("Line: " + lineNumber + ": Incorrect integer format: " + parts[2]);
                } catch (DateTimeParseException e) {
                    throw new PlantException("Line: " + lineNumber + ": Incorrect date format: " + e.getParsedString());
                } catch (PlantException e) {
                    throw new PlantException("Line: " + lineNumber + ": Invalid data on line: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("File not found " + filename);
        }
    }

    public void savePlantsToFile(String filename) throws PlantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {

            for (Plant plant : plantList) {
                String line = plant.getName() + Settings.getDelimiter()
                        + plant.getNotes() + Settings.getDelimiter()
                        + plant.getFrequencyOfWatering() + Settings.getDelimiter()
                        + plant.getWatering() + Settings.getDelimiter()
                        + plant.getPlanted();

                writer.println(line);
            }
        } catch (IOException e) {
            throw new PlantException("Couldn't write the file: " + e.getMessage());
        }
    }

    public void sortPlantsByName() {
        Collections.sort(plantList);
    }

    public void sortPlantsByWatering() {
        plantList.sort(Comparator.comparing(Plant::getWatering));
    }
}
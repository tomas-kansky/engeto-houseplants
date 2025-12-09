package com.engeto.houseplants.model;

import com.engeto.houseplants.exception.PlantException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plant implements Comparable<Plant>{
    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate watering;
    private int frequencyOfWatering;

    // ----- Constructors --- BEGIN -----
    public Plant(String name, String notes, int frequencyOfWatering, LocalDate planted,
                      LocalDate watering) throws PlantException
    {
        this.name = name;
        this.notes = notes;
        this.planted = planted;

        if(watering.isBefore(LocalDate.now())) throw new PlantException("Watering cannot be past(Value: " + watering + ")");
        this.watering = watering;

        if(frequencyOfWatering <= 0) throw new PlantException("Watering Frequency cannot be 0 or negative(Value: " + frequencyOfWatering + ")");
        this.frequencyOfWatering = frequencyOfWatering;
    }

    public Plant(String name, int frequencyOfWatering) throws PlantException
    {
        this(name,"", frequencyOfWatering, LocalDate.now(), LocalDate.now());
    }

    public Plant(String name) throws PlantException
    {
        this(name, 7);
    }
    // ----- Constructors --- END -----

    // ----- Custom methods --- BEGIN -----
    public String getWateringInfo()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        LocalDate nextWateringDate = watering.plusDays(frequencyOfWatering);

        return "Plant name: " + this.name + ", last watering date: " + this.watering.format(formatter) +
                ", next recommended watering date: " + nextWateringDate.format(formatter);
    }

    public void doWateringNow()
    {
        this.watering = LocalDate.now();
    }
    // ----- Custom methods --- END -----

    // ----- Getters, Setters --- BEGIN -----
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getPlanted() {
        return planted;
    }

    public void setPlanted(LocalDate planted) {
        this.planted = planted;
    }

    public LocalDate getWatering() {
        return watering;
    }

    public void setWatering(LocalDate watering) throws PlantException
    {
        if(watering.isBefore(LocalDate.now())) throw new PlantException("Watering cannot be in past: (Value: " + frequencyOfWatering + ")");
        this.watering = watering;
    }

    public int getFrequencyOfWatering() {
        return frequencyOfWatering;
    }

    public void setFrequencyOfWatering(int frequencyOfWatering) throws PlantException
    {
        if(frequencyOfWatering <= 0) throw new PlantException("Watering Frequency cannot be 0 or negative(Value: " + frequencyOfWatering + ")");
        this.frequencyOfWatering = frequencyOfWatering;
    }
    // ----- Getters, Setters --- END -----

    @Override
    public int compareTo(Plant otherPlant) {
        return this.name.compareTo(otherPlant.name);
    }
}

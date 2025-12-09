package com.engeto.houseplants.config;

import com.engeto.houseplants.exception.PlantException;

public enum InputFileType {
    CORRECT(1,"resources/kvetiny"),
    WRONG_FREQUENCY(2, "resources/kvetiny-spatne-frekvence.txt"),
    WRONG_DATE(3, "resources/kvetiny-spatne-datum.txt");

    private final int id;
    private final String filePath;

    InputFileType(int id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public static InputFileType fromId(int id) throws PlantException {
        StringBuilder allowedOptions = new StringBuilder();

        for (InputFileType type : InputFileType.values()) {
            if(!allowedOptions.isEmpty()) allowedOptions.append(", ");

            allowedOptions.append(type.id);

            if (type.id == id) return type;
        }

        throw new PlantException("Incorrect File Selection " + id + ". Allowed options: " + allowedOptions);
    }
}
package com.engeto.houseplants.config;

import com.engeto.houseplants.exception.PlantException;

public class Settings {
    private static final String DELIMITER = "\t";

    public static String getInputFilename(InputFileType fileType) throws PlantException {
        return fileType.getFilePath();
    }

    public static String getDelimiter() {
        return DELIMITER;
    }
}

package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.BiomeType;
import eu.deltasource.internship.livingecosystem.enums.HabitatType;

public class Biome {

    private BiomeType biomeType;
    private HabitatType habitatType;

    public Biome(BiomeType biomeType, HabitatType habitatType) {
        this.biomeType = biomeType;
        this.habitatType = habitatType;
    }
}


package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.BiomType;
import eu.deltasource.internship.livingecosystem.enums.HabitatType;

public class Biome {

    private BiomType biomType;
    private HabitatType habitatType;

    public Biome(BiomType biomType, HabitatType habitatType) {
        this.biomType = biomType;
        this.habitatType = habitatType;
    }
}


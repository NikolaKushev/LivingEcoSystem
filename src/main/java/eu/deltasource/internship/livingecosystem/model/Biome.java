package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.BiomeType;
import eu.deltasource.internship.livingecosystem.enums.LivingHabitat;

public class Biome {

    private BiomeType biomeType;
    private LivingHabitat livingHabitat;

    public Biome(BiomeType biomeType, LivingHabitat livingHabitat) {
        this.biomeType = biomeType;
        this.livingHabitat = livingHabitat;
    }
}


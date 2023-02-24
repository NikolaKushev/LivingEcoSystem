package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.BiomeType;
import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Animal {

    private int age;
    private int maxAge;
    private final int originalReproductionRate;
    private int reproductionRate;
    private int groupMembers;
    private double weight;
    private String animalType;
    private HabitatType habitatType;
    private LivingStatus livingStatus;
    private List<Biome> biomes;

    public Animal(String animalType, int maxAge, double weight, int reproductionRate, int groupMembers, HabitatType habitatType, LivingStatus livingStatus) {
        this.animalType = animalType;
        this.age = 1;
        this.maxAge = maxAge;
        this.weight = weight;
        this.reproductionRate = reproductionRate;
        this.groupMembers = groupMembers;
        this.habitatType = habitatType;
        this.livingStatus = livingStatus;
        this.originalReproductionRate = getReproductionRate();
        this.biomes = new ArrayList<>();
        biomes.add(new Biome(BiomeType.SAVANNA, HabitatType.LAND));
    }

    public int getOriginalReproductionRate() {
        return originalReproductionRate;
    }

    public void setReproductionRate(int reproductionRate) {
        this.reproductionRate = reproductionRate;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBiomes(List<Biome> biomes) {
        this.biomes = biomes;
    }

    public String getAnimalType() {
        return animalType;
    }

    public List<Biome> getBiomes() {
        return Collections.unmodifiableList(biomes);
    }

    public int getAge() {
        return age;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public double getWeight() {
        return weight;
    }

    public int getReproductionRate() {
        return reproductionRate;
    }

    public int getGroupMembers() {
        return groupMembers;
    }

    public HabitatType getHabitatType() {
        return habitatType;
    }

    public LivingStatus getLivingStatus() {
        return livingStatus;
    }

    public abstract void setHungerRate(int hungerRate);

    public abstract int getHungerRate();

    public abstract double getPoints();

    public abstract void setPoints(double points);
}

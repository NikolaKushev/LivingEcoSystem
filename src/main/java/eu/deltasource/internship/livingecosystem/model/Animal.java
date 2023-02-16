package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Animal {

    private double age;
    private double maxAge;
    private double weight;
    private int reproductionRate;
    private int groupMembers;
    private String animalType;
    private HabitatType habitatType;
    private LivingStatus livingStatus;
    private List<Biome> biomes;

    public Animal(String animalType, double maxAge, double weight, int reproductionRate, HabitatType habitatType, LivingStatus livingStatus) {
        this.animalType = animalType;
        this.age = new Random().nextInt(1, 5);
        this.maxAge = maxAge;
        this.weight = weight;
        this.reproductionRate = reproductionRate;
        this.habitatType = habitatType;
        this.livingStatus = livingStatus;
        this.biomes = new ArrayList<>();
    }

    public Animal(String animalType, double maxAge, double weight, int reproductionRate, int groupMembers, HabitatType habitatType, LivingStatus livingStatus) {
        this.animalType = animalType;
        this.age = new Random().nextInt(0, 5);
        this.maxAge = maxAge;
        this.weight = weight;
        this.reproductionRate = reproductionRate;
        this.groupMembers = groupMembers;
        this.habitatType = habitatType;
        this.livingStatus = livingStatus;
    }

    public double scalePoints(double points) {
        return points * (1 - age / maxAge);
    }

    public String getAnimalType() {
        return animalType;
    }

    public List<Biome> getBiomes() {
        return Collections.unmodifiableList(biomes);
    }

    public double getAge() {
        return age;
    }

    public double getMaxAge() {
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
}

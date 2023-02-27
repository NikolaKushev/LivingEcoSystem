package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.BiomeType;
import eu.deltasource.internship.livingecosystem.enums.LivingHabitat;
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
    private String specie;
    private LivingHabitat livingHabitat;
    private LivingStatus livingStatus;
    private List<Biome> biomes;

    public Animal(String specie, int maxAge, double weight, int reproductionRate, int groupMembers, LivingHabitat livingHabitat, LivingStatus livingStatus) {
        this.specie = specie;
        this.age = 1;
        this.maxAge = maxAge;
        this.weight = weight;
        this.reproductionRate = reproductionRate;
        this.groupMembers = groupMembers;
        this.livingHabitat = livingHabitat;
        this.livingStatus = livingStatus;
        this.originalReproductionRate = getReproductionRate();
        this.biomes = new ArrayList<>();
        biomes.add(new Biome(BiomeType.SAVANNA, LivingHabitat.LAND));
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

    public String getSpecie() {
        return specie;
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

    public LivingHabitat getHabitatType() {
        return livingHabitat;
    }

    public LivingStatus getLivingStatus() {
        return livingStatus;
    }

    public abstract double getPoints();

    public abstract void setPoints(double points);
}

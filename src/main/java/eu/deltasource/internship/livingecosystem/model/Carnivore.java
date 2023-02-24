package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;

public class Carnivore extends Animal {

    private double attackPoints;
    private int hungerLevel;

    private int hungerRate;

    public Carnivore(String specie, int maxAge, double weight, int reproductionRate, int groupMembers, HabitatType habitatType,
                     LivingStatus livingStatus, double attackPoints, int hungerLevel, int hungerRate) {
        super(specie, maxAge, weight, reproductionRate, groupMembers, habitatType, livingStatus);
        this.hungerLevel = 0;
        this.attackPoints = attackPoints;
        this.hungerRate = hungerRate;
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    @Override
    public void setPoints(double points) {
        this.attackPoints = points;
    }

    @Override
    public double getPoints() {
        return attackPoints;
    }

    @Override
    public void setHungerRate(int hungerRate) {
        this.hungerRate = hungerRate;
    }

    @Override
    public int getHungerRate() {
        return hungerRate;
    }
}

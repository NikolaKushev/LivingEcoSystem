package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.LivingHabitat;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;

public class Carnivore extends Animal {

    private double attackPoints;
    private int hungerLevel;
    private int hungerRate;

    public Carnivore(String specie, int maxAge, double weight, int reproductionRate, int groupMembers, LivingHabitat livingHabitat,
                     LivingStatus livingStatus, double attackPoints, int hungerRate) {
        super(specie, maxAge, weight, reproductionRate, groupMembers, livingHabitat, livingStatus);
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

    public void setHungerRate(int hungerRate) {
        this.hungerRate = hungerRate;
    }

    public int getHungerRate() {
        return hungerRate;
    }
}

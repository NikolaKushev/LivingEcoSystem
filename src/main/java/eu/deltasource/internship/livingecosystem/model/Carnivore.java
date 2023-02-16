package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;

public class Carnivore extends Animal {

    private double attackPoints;
    private int hungerRate;


    public Carnivore(String animalType, double maxAge, double weight, int reproductionRate, HabitatType habitatType, LivingStatus livingStatus,
                     double attackPoints, int hungerRate) {
        super(animalType, maxAge, weight, reproductionRate, habitatType, livingStatus);
        this.attackPoints = attackPoints;
        this.hungerRate = hungerRate;
    }

    public Carnivore(String animalType, double maxAge, double weight, int reproductionRate, int groupMembers, HabitatType habitatType,
                     LivingStatus livingStatus, double attackPoints, int hungerRate) {
        super(animalType, maxAge, weight, reproductionRate, groupMembers, habitatType, livingStatus);
        this.attackPoints = attackPoints;
        this.hungerRate = hungerRate;
    }

    @Override
    public void setHungerRate(int hungerRate) {
        this.hungerRate = hungerRate;
    }

    @Override
    public int getHungerRate() {
        return hungerRate;
    }


    @Override
    public double getPoints() {
        return attackPoints;
    }
}

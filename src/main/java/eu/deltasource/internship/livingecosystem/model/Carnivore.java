package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;

public class Carnivore extends Animal {

    private int attackPoints;
    private int hungerRate;

    public Carnivore(String animalType, int maxAge, double weight, int reproductionRate, int groupMembers, HabitatType habitatType,
                     LivingStatus livingStatus, int attackPoints, int hungerRate) {
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
    public int getPoints() {
        return attackPoints;
    }
}

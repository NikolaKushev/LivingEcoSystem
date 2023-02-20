package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;

public class Herbivore extends Animal {

    private int escapePoints;

    public Herbivore(String animalType, int maxAge, double weight, int reproductionRate, int groupMembers, HabitatType habitatType, LivingStatus livingStatus, int escapePoints) {
        super(animalType, maxAge, weight, reproductionRate, groupMembers, habitatType, livingStatus);
        this.escapePoints = escapePoints;
    }

    @Override
    public void setHungerRate(int hungerRate) {
    }

    @Override
    public int getHungerRate() {
        return 0;
    }

    @Override
    public int getPoints() {
        return escapePoints;
    }
}

package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;

public class Herbivore extends Animal {

    private double escapePoints;

    public Herbivore(String specie, int maxAge, double weight, int reproductionRate, int groupMembers,
                     HabitatType habitatType, LivingStatus livingStatus, double escapePoints) {
        super(specie, maxAge, weight, reproductionRate, groupMembers, habitatType, livingStatus);
        this.escapePoints = escapePoints;
    }

    @Override
    public double getPoints() {
        return escapePoints;
    }

    @Override
    public void setPoints(double escapePoints) {
        this.escapePoints = escapePoints;
    }

    @Override
    public void setHungerRate(int hungerRate) {
    }

    @Override
    public int getHungerRate() {
        return 0;
    }
}

package eu.deltasource.internship.livingecosystem.model;

import eu.deltasource.internship.livingecosystem.enums.LivingHabitat;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;

public class Herbivore extends Animal {

    private double escapePoints;

    public Herbivore(String specie, int maxAge, double weight, int reproductionRate, int groupMembers,
                     LivingHabitat livingHabitat, LivingStatus livingStatus, double escapePoints) {
        super(specie, maxAge, weight, reproductionRate, groupMembers, livingHabitat, livingStatus);
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
}

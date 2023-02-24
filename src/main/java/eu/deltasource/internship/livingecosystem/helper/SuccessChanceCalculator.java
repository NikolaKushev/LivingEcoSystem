package eu.deltasource.internship.livingecosystem.helper;

import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.Animal;

public class SuccessChanceCalculator {

    public double getSuccessChance(Animal herbivore, Animal carnivore) {
        double successChance = calculateSuccessChanceForAttack(herbivore, carnivore);

        successChance = reduceSuccessChanceByWeightRatio(herbivore, carnivore, successChance);
        return successChance;
    }

    private double calculateSuccessChanceForAttack(Animal herbivore, Animal carnivore) {
        double successChance = calculateSuccessChance(herbivore, carnivore);

        if (herbivore.getLivingStatus().equals(LivingStatus.GROUP)) {
            successChance = calculateSuccessChance(herbivore, carnivore) + 0.3 * calculateSuccessChance(herbivore, carnivore);
        }

        if (carnivore.getLivingStatus().equals(LivingStatus.ALONE)) {
            successChance = calculateSuccessChance(herbivore, carnivore) - 0.5 * calculateSuccessChance(herbivore, carnivore);
        }
        //used for rounding to two decimal places
        return Math.round(successChance * 100.0) / 100.0;
    }

    private double scalePoints(Animal animal, double points) {
        return points * (1 - (double)animal.getAge() / animal.getMaxAge());
    }

    private double reduceSuccessChanceByWeightRatio(Animal herbivore, Animal carnivore, double successChance) {
        if (herbivore.getWeight() > carnivore.getWeight() && carnivore.getLivingStatus().equals(LivingStatus.ALONE)) {
            successChance /= herbivore.getWeight() / carnivore.getWeight();
        }
        return successChance;
    }

    private double calculateSuccessChance(Animal herbivore, Animal carnivore) {
        double escapePoints = herbivore.getPoints();
        double attackPoints = carnivore.getPoints();
        double successChance = (attackPoints / (attackPoints + escapePoints)) * 100;

        escapePoints = scalePoints(herbivore, herbivore.getPoints());
        attackPoints = scalePoints(carnivore, carnivore.getPoints());

        herbivore.setPoints(escapePoints);
        carnivore.setPoints(attackPoints);

        return successChance;
    }
}

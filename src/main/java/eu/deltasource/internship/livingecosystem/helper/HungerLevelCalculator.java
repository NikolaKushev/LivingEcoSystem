package eu.deltasource.internship.livingecosystem.helper;

import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.Carnivore;
import eu.deltasource.internship.livingecosystem.model.Herbivore;
import eu.deltasource.internship.livingecosystem.service.CarnivoreService;

import java.util.List;

public class HungerLevelCalculator {

    private final CarnivoreService carnivoreService;

    public HungerLevelCalculator(CarnivoreService carnivoreService) {
        this.carnivoreService = carnivoreService;
    }

    public void decreaseHungerLevelOfCarnivore(Herbivore herbivore, Carnivore carnivore, int carnivoreHungerLevel) {
        carnivoreHungerLevel -= (herbivore.getWeight() / carnivore.getWeight()) / 100;
        carnivore.setHungerLevel(carnivoreHungerLevel);
    }

    public void increaseHungerLevel(Carnivore carnivore) {
        if (carnivore.getLivingStatus().equals(LivingStatus.GROUP)) {
            List<Carnivore> animals = carnivoreService.getCarnivoreGroup(carnivore);
            for (Carnivore animal : animals) {
                increaseHungerLevelIfAnimalIsAlone(animal);
            }
        } else {
            increaseHungerLevelIfAnimalIsAlone(carnivore);
        }
    }

    public void decreaseHungerRateIfCarnivoreInGroup(Herbivore herbivore, Carnivore carnivore) {
        if (carnivore.getLivingStatus().equals(LivingStatus.GROUP)) {
            double distributedWeight = herbivore.getWeight() / (carnivore.getGroupMembers() + 1);

            List<Carnivore> animals = carnivoreService.getCarnivoreGroup(carnivore);
            for (Carnivore animal : animals) {
                checkIfHungerLevelLessThanZero(animal, distributedWeight);
                carnivore.setHungerLevel(animal.getHungerLevel());
            }
        }
    }

    private void increaseHungerLevelIfAnimalIsAlone(Carnivore carnivore) {
        int increaseOfHungerRate = carnivore.getHungerRate();

        int carnivoreHungerLevel = carnivore.getHungerLevel() + increaseOfHungerRate;
        carnivore.setHungerLevel(carnivoreHungerLevel);
    }

    private int decreaseHungerLevel(Carnivore carnivore, double distributedWeight) {
        int hungerLevel = carnivore.getHungerLevel();
        hungerLevel -= distributedWeight;
        carnivore.setHungerLevel(hungerLevel);
        return hungerLevel;
    }

    private void checkIfHungerLevelLessThanZero(Carnivore carnivore, double distributedWeight) {
        if (decreaseHungerLevel(carnivore, distributedWeight) < 0) {
            carnivore.setHungerLevel(0);
        }
    }
}

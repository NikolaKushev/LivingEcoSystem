package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.helper.HungerLevelCalculator;
import eu.deltasource.internship.livingecosystem.helper.AgeCalculator;
import eu.deltasource.internship.livingecosystem.helper.ReproductionRateCalculator;
import eu.deltasource.internship.livingecosystem.helper.SuccessChanceCalculator;
import eu.deltasource.internship.livingecosystem.model.Carnivore;
import eu.deltasource.internship.livingecosystem.model.Herbivore;

import java.util.Random;

//TODO fix uml diagram for demo.
public class EcoSystemService {

    CarnivoreService carnivoreService;
    HerbivoreService herbivoreService;
    ReproductionRateCalculator reproductionRateCalculator;
    SuccessChanceCalculator successChanceCalculator;
    HungerLevelCalculator hungerLevelCalculator;
    AgeCalculator ageCalculator;

    public EcoSystemService(CarnivoreService carnivoreService, HerbivoreService herbivoreService,
                            ReproductionRateCalculator reproductionRateCalculator, SuccessChanceCalculator successChanceCalculator,
                            HungerLevelCalculator hungerLevelCalculator, AgeCalculator ageCalculator) {
        this.reproductionRateCalculator = reproductionRateCalculator;
        this.ageCalculator = ageCalculator;
        this.hungerLevelCalculator = hungerLevelCalculator;
        this.successChanceCalculator = successChanceCalculator;
        this.carnivoreService = carnivoreService;
        this.herbivoreService = herbivoreService;
    }

    public void simulate(int randomSuccessChance) {
        while (true) {
            if (herbivoreService.getHerbivoresList().size() == 0 || carnivoreService.getCarnivoresList().size() == 0) {
                break;
            }

            Herbivore herbivore = herbivoreService.getHerbivoresList().get(new Random().nextInt(herbivoreService.getHerbivoresList().size()));
            Carnivore carnivore = carnivoreService.getCarnivoresList().get(new Random().nextInt(carnivoreService.getCarnivoresList().size()));

            double successChance = successChanceCalculator.getSuccessChance(herbivore, carnivore);

            if (successChance > randomSuccessChance) {
                attack(herbivore, carnivore);
            }

            ageCalculator.increaseAge();

            carnivoreService.removeCarnivoreIfReachedMaxHungerRate(carnivore, carnivore.getHungerRate());

            herbivoreService.removeHerbivoreIfReachedMaxAge(herbivore);
            carnivoreService.removeCarnivoreIfReachedMaxAge(carnivore);

            hungerLevelCalculator.increaseHungerLevel(carnivore);
            reproductionRateCalculator.reduceReproductionRate(herbivore, carnivore);
            reproductionRateCalculator.reproduceIfReproductionRateIsZero(carnivore, herbivore);
        }
    }

    private void attack(Herbivore herbivore, Carnivore carnivore) {
        int carnivoreHungerRate = carnivore.getHungerRate();

        herbivoreService.removeHerbivore(herbivore);

        hungerLevelCalculator.decreaseHungerLevelOfCarnivore(herbivore, carnivore, carnivoreHungerRate);

        hungerLevelCalculator.decreaseHungerRateIfCarnivoreInGroup(herbivore, carnivore);
    }
}

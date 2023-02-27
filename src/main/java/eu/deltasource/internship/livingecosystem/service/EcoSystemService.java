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

    private final CarnivoreService carnivoreService;
    private final HerbivoreService herbivoreService;
    private final ReproductionRateCalculator reproductionRateCalculator;
    private final SuccessChanceCalculator successChanceCalculator;
    private final HungerLevelCalculator hungerLevelCalculator;
    private final AgeCalculator ageCalculator;

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

    public void simulateEcoSystem() {
        while (true) {
            if (herbivoreService.getHerbivoresList().size() == 0 || carnivoreService.getCarnivoresList().size() == 0) {
                System.out.println("No animals left!");
                break;
            }

            Herbivore herbivore = herbivoreService.getHerbivoresList().get(new Random().nextInt(herbivoreService.getHerbivoresList().size()));
            Carnivore carnivore = carnivoreService.getCarnivoresList().get(new Random().nextInt(carnivoreService.getCarnivoresList().size()));

            double successChance = successChanceCalculator.getSuccessChance(herbivore, carnivore);

            int randomSuccessChance = new Random().nextInt(0, 100);

            if (successChance > randomSuccessChance) {
                initiateAttack(herbivore, carnivore);
            } else {
                System.out.printf("%s escaped from %s\n", herbivore.getSpecie(), carnivore.getSpecie());
            }
            ageCalculator.increaseAge();
            carnivoreService.removeCarnivoreIfStarving(carnivore);
            herbivoreService.removeHerbivoreIfDiedFromOldAge(herbivore);
            carnivoreService.removeCarnivoreIfDiedFromOldAge(carnivore);
            hungerLevelCalculator.increaseHungerLevel(carnivore);
            reproductionRateCalculator.reduceReproductionRate(herbivore, carnivore);
            reproductionRateCalculator.reproduceIfReproductionRateIsZero(carnivore, herbivore);
        }
    }

    private void initiateAttack(Herbivore herbivore, Carnivore carnivore) {
        int carnivoreHungerLevel = carnivore.getHungerLevel();
        System.out.printf("%s killed %s\n", carnivore.getSpecie(), herbivore.getSpecie());

        herbivoreService.removeHerbivore(herbivore);
        hungerLevelCalculator.decreaseHungerLevelOfCarnivore(herbivore, carnivore, carnivoreHungerLevel);
        hungerLevelCalculator.decreaseHungerLevelIfCarnivoreInGroup(herbivore, carnivore);
    }
}

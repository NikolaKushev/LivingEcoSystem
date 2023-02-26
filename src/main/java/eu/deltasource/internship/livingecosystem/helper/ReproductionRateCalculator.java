package eu.deltasource.internship.livingecosystem.helper;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Carnivore;
import eu.deltasource.internship.livingecosystem.model.Herbivore;
import eu.deltasource.internship.livingecosystem.service.CarnivoreService;
import eu.deltasource.internship.livingecosystem.service.HerbivoreService;

public class ReproductionRateCalculator {

    private final CarnivoreService carnivoreService;
    private final HerbivoreService herbivoreService;

    public ReproductionRateCalculator(CarnivoreService carnivoreService, HerbivoreService herbivoreService) {
        this.carnivoreService = carnivoreService;
        this.herbivoreService = herbivoreService;
    }

    public void reduceReproductionRate(Animal herbivore, Animal carnivore) {
        int herbivoreReproductionRate = herbivore.getReproductionRate();
        int carnivoreReproductionRate = carnivore.getReproductionRate();

        herbivoreReproductionRate--;
        carnivoreReproductionRate--;

        herbivore.setReproductionRate(herbivoreReproductionRate);
        carnivore.setReproductionRate(carnivoreReproductionRate);
    }

    public void reproduceIfReproductionRateIsZero(Carnivore carnivore, Herbivore herbivore) {
        if (carnivore.getReproductionRate() == 0) {
            carnivoreService.addCarnivore(new Carnivore(carnivore.getSpecie(), carnivore.getMaxAge(), carnivore.getWeight(), carnivore.getReproductionRate(),
                    carnivore.getGroupMembers(), carnivore.getHabitatType(), carnivore.getLivingStatus(), carnivore.getPoints(),
                    carnivore.getHungerLevel(), carnivore.getHungerRate()));
            carnivore.setReproductionRate(carnivore.getOriginalReproductionRate());
            System.out.printf("New %s was born!\n", carnivore.getSpecie());
        }
        if (herbivore.getReproductionRate() == 0) {
            herbivoreService.addHerbivore(new Herbivore(herbivore.getSpecie(), herbivore.getMaxAge(), herbivore.getWeight(), herbivore.getReproductionRate(),
                    herbivore.getGroupMembers(), herbivore.getHabitatType(), herbivore.getLivingStatus(), herbivore.getPoints()));
            herbivore.setReproductionRate(herbivore.getOriginalReproductionRate());
            System.out.printf("New %s was born!\n", herbivore.getSpecie());
        }
    }
}

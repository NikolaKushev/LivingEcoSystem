package eu.deltasource.internship.livingecosystem.helper;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.service.CarnivoreService;
import eu.deltasource.internship.livingecosystem.service.HerbivoreService;

public class AgeCalculator {

    private  final CarnivoreService carnivoreService;
    private  final HerbivoreService herbivoreService;

    public AgeCalculator(CarnivoreService carnivoreService, HerbivoreService herbivoreService) {
        this.carnivoreService = carnivoreService;
        this.herbivoreService = herbivoreService;
    }

    public void increaseAge() {
        for (Animal animal : carnivoreService.getCarnivoresList()) {
            increaseAge(animal);
        }

        for (Animal animal : herbivoreService.getHerbivoresList()) {
            increaseAge(animal);
        }
    }

    private void increaseAge(Animal animal) {
        int age = animal.getAge();
        age++;
        animal.setAge(age);
    }
}

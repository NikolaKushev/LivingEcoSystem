package eu.deltasource.internship.livingecosystem;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.*;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepository;

import java.util.List;
import java.util.Random;

public class EcoSystemService {

    final static int increaseOfHungerRate = 20;
    final static int maxHungerRate = 100;

    CarnivoreRepository carnivoreRepository;
    HerbivoreRepository herbivoreRepository;
    CarnivoreGroupRepository carnivoreGroupRepository;
    HerbivoreGroupRepository herbivoreGroupRepository;

    public EcoSystemService(CarnivoreRepository carnivoreRepository, HerbivoreRepository herbivoreRepository,
                            CarnivoreGroupRepository carnivoreGroupRepository, HerbivoreGroupRepository herbivoreGroupRepository) {
        this.carnivoreRepository = carnivoreRepository;
        this.herbivoreRepository = herbivoreRepository;
        this.carnivoreGroupRepository = carnivoreGroupRepository;
        this.herbivoreGroupRepository = herbivoreGroupRepository;
    }

    private void makeEcoSystem() {
        Animal zebra = new Herbivore("zebra", 50, 300, 10, 4, HabitatType.LAND, LivingStatus.GROUP, 80);
        Animal hare = new Herbivore("hare", 24, 5, 100, HabitatType.LAND, LivingStatus.ALONE, 100);
        Animal gazelle = new Herbivore("gazelle", 25, 25, 5, 4, HabitatType.LAND, LivingStatus.GROUP, 80);
        Animal buffallo = new Herbivore("buffalo", 35, 800, 9, 4, HabitatType.LAND, LivingStatus.GROUP, 40);

        addHerbivore(zebra);
        addHerbivore(hare);
        addHerbivore(buffallo);
        addHerbivore(gazelle);

        Animal lion = new Carnivore("lion", 30, 150, 5, 4, HabitatType.LAND, LivingStatus.GROUP, 80, 20);
        Animal cheetah = new Carnivore("cheetah", 30, 60, 5, HabitatType.LAND, LivingStatus.ALONE, 110, 20);
        Animal tiger = new Carnivore("tiger", 20, 200, 6, HabitatType.LAND, LivingStatus.ALONE, 75, 18);
        Animal hyena = new Carnivore("hyena", 24, 50, 5, 3, HabitatType.LAND, LivingStatus.GROUP, 80, 14);

        addCarnivore(lion);
        addCarnivore(cheetah);
        addCarnivore(tiger);
        addCarnivore(hyena);
    }

    public void simulate() {
        makeEcoSystem();

        while (true) {
            Animal herbivore = herbivoreRepository.getHerbivoresList().get(new Random().nextInt(herbivoreRepository.getHerbivoresList().size()));
            Animal carnivore = carnivoreRepository.getCarnivoresList().get(new Random().nextInt(carnivoreRepository.getCarnivoresList().size()));

            makeGroup(herbivore, carnivore);

            double successChance = calculateSuccessChanceForAttack(herbivore, carnivore);

            successChance = reduceSuccessChanceByWeightRatio(herbivore, carnivore, successChance);

            int randomSuccessChance = new Random().nextInt(0, 100);

            if (successChance > randomSuccessChance) {
                attack(herbivore, carnivore);
            }

            removeCarnivoreIfReachedMaxHungerRate(carnivore, carnivore.getHungerRate());

            increaseHungerRate(carnivore);

        }
    }

    private void attack(Animal herbivore, Animal carnivore) {
        int carnivoreHungerRate = carnivore.getHungerRate();

        herbivoreRepository.removeHerbivore(herbivore);

        carnivoreHungerRate -= (herbivore.getWeight() / carnivore.getWeight()) / 100;
        carnivore.setHungerRate(carnivoreHungerRate);

        decreaseHungerRateIfAnimalInGroup(herbivore, carnivore);

        increaseAge(herbivore, carnivore);
    }

    private void increaseHungerRate(Animal carnivore) {
        int carnivoreHungerRate = carnivore.getHungerRate() + increaseOfHungerRate;
        carnivore.setHungerRate(carnivoreHungerRate);
    }

    private void decreaseHungerRateIfAnimalInGroup(Animal herbivore, Animal carnivore) {
        if (carnivore.getLivingStatus().equals(LivingStatus.GROUP)) {
            double distributedWeight = herbivore.getWeight() / (carnivore.getGroupMembers() + 1);

            List<Animal> animals = carnivoreGroupRepository.getGroup(carnivore);
            for (Animal animal : animals) {
                decreaseHungerRateOfCarnivore(animal, distributedWeight);
                carnivore.setHungerRate(animal.getHungerRate());
            }
            int hungerRate = carnivore.getHungerRate();
            hungerRate -= distributedWeight;
        }
    }

    private void decreaseHungerRateOfCarnivore(Animal carnivore, double distributedWeight) {
        int hungerRate = carnivore.getHungerRate();
        hungerRate -= distributedWeight;
        carnivore.setHungerRate(hungerRate);

        if (hungerRate <= 0) {
            carnivore.setHungerRate(0);
        }
    }

    private void removeCarnivoreIfReachedMaxHungerRate(Animal carnivore, int carnivoreHungerRate) {
        if (carnivoreHungerRate >= maxHungerRate) {
            carnivoreRepository.removeCarnivore(carnivore);
        }
    }

    private void increaseAge(Animal herbivore, Animal carnivore) {
        double carnivoreAge = carnivore.getAge();
        double herbivoreAge = herbivore.getAge();
        carnivoreAge++;
        herbivoreAge++;
    }

    private double reduceSuccessChanceByWeightRatio(Animal herbivore, Animal carnivore, double successChance) {
        if (herbivore.getWeight() > carnivore.getWeight() && carnivore.getLivingStatus().equals(LivingStatus.ALONE)) {
            successChance /= herbivore.getWeight() / carnivore.getWeight();
        }
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

        return successChance;
    }

    private double calculateSuccessChance(Animal herbivore, Animal carnivore) {
        double escapePoints = herbivore.scalePoints(herbivore.getPoints());
        double attackPoints = carnivore.scalePoints(carnivore.getPoints());

        return (attackPoints / (attackPoints + escapePoints)) * 100;
    }

    private void makeGroup(Animal herbivore, Animal carnivore) {
        if (herbivore.getLivingStatus().equals(LivingStatus.GROUP)) {
            addHerbivoreToGroup(herbivore);
        }

        if (carnivore.getLivingStatus().equals(LivingStatus.GROUP)) {
            addCarnivoreToGroup(carnivore);
        }
    }

    private void addHerbivore(Animal animal) {
        herbivoreRepository.addHerbivore(animal);
    }

    private void addCarnivore(Animal animal) {
        carnivoreRepository.addCarnivore(animal);
    }

    private void addCarnivoreToGroup(Animal animal) {
        if (animal.getLivingStatus().equals(LivingStatus.GROUP)) {
            Group group = new CarnivoreGroup(animal.getAnimalType());

            for (int i = 0; i < animal.getGroupMembers(); i++) {
                group.addAnimal(new Carnivore(animal.getAnimalType(), animal.getMaxAge(), animal.getWeight(), animal.getReproductionRate(),
                        animal.getGroupMembers(), animal.getHabitatType(), animal.getLivingStatus(), animal.getPoints(), animal.getHungerRate()));
            }
            carnivoreGroupRepository.addGroup(group);
        }
    }

    private void addHerbivoreToGroup(Animal animal) {
        if (animal.getLivingStatus().equals(LivingStatus.GROUP)) {
            Group group = new HerbivoreGroup(animal.getAnimalType());

            for (int i = 0; i < animal.getGroupMembers(); i++) {
                group.addAnimal(new Herbivore(animal.getAnimalType(), animal.getMaxAge(), animal.getWeight(), animal.getReproductionRate(),
                        animal.getGroupMembers(), animal.getHabitatType(), animal.getLivingStatus(), animal.getPoints()));
            }
            herbivoreGroupRepository.addGroup(group);
        }
    }
}

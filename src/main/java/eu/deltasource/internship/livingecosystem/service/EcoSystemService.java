package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Carnivore;
import eu.deltasource.internship.livingecosystem.model.Herbivore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;
//TODO fix reproduction rate logic and figure out how to use Simulation class.
public class EcoSystemService {

    CarnivoreService carnivoreService;
    HerbivoreService herbivoreService;

    public EcoSystemService(CarnivoreService carnivoreService, HerbivoreService herbivoreService) {
        this.carnivoreService = carnivoreService;
        this.herbivoreService = herbivoreService;
    }

    public void createHerbivores(HerbivoreService herbivoreService) throws IOException {
        BufferedReader herbivoreBuffer = new BufferedReader(new FileReader("HerbivoreInfo.txt"));
        String herbivoreInfo;

        Animal[] herbivores = new Herbivore[0];
        while ((herbivoreInfo = herbivoreBuffer.readLine()) != null) {
            String[] values = herbivoreInfo.split("-");
            String herbivoreSpecie = values[0];
            int herbivoreMaxAge = Integer.parseInt(values[1]);
            double herbivoreWeight = Double.parseDouble(values[2]);
            int herbivoreReproductionRate = Integer.parseInt(values[3]);
            int herbivoreGroupMembers = Integer.parseInt(values[4]);
            HabitatType herbivoreHabitatType = HabitatType.valueOf(values[5]);
            LivingStatus herbivoreLivingStatus = LivingStatus.valueOf(values[6]);
            int herbivoreEscapePoints = Integer.parseInt(values[7]);

            Animal herbivore = new Herbivore(herbivoreSpecie, herbivoreMaxAge, herbivoreWeight, herbivoreReproductionRate, herbivoreGroupMembers, herbivoreHabitatType, herbivoreLivingStatus, herbivoreEscapePoints);
            herbivores = addHerbivore(herbivores, herbivore);

            herbivoreService.addHerbivore(herbivore);
            herbivoreService.addHerbivoreToGroup(herbivore);
        }
    }

    public void createCarnivores(CarnivoreService carnivoreService) throws IOException {
        Animal[] carnivores = new Carnivore[0];
        BufferedReader carnivoreBuffer = new BufferedReader(new FileReader("CarnivoreInfo.txt"));
        String carnivoreInfo;
        while ((carnivoreInfo = carnivoreBuffer.readLine()) != null) {
            String[] values = carnivoreInfo.split("-");
            String carnivoreSpecie = values[0];
            int carnivoreMaxAge = Integer.parseInt(values[1]);
            double carnivoreWeight = Double.parseDouble(values[2]);
            int carnivoreReproductionRate = Integer.parseInt(values[3]);
            int carnivoreGroupMembers = Integer.parseInt(values[4]);
            HabitatType carnivoreHabitatType = HabitatType.valueOf(values[5]);
            LivingStatus carnivoreLivingStatus = LivingStatus.valueOf(values[6]);
            int carnivoreAttackPoints = Integer.parseInt(values[7]);
            int hungerRate = Integer.parseInt(values[8]);

            Animal carnivore = new Carnivore(carnivoreSpecie, carnivoreMaxAge, carnivoreWeight, carnivoreReproductionRate,
                    carnivoreGroupMembers, carnivoreHabitatType, carnivoreLivingStatus, carnivoreAttackPoints, hungerRate);
            carnivores = addCarnivore(carnivores, carnivore);

            carnivoreService.addCarnivore(carnivore);
            carnivoreService.addCarnivoreToGroup(carnivore);
        }
    }

    public void simulate() {

        while (true) {

            if (herbivoreService.getHerbivoresList().size() == 0) {
                break;
            }

            Animal herbivore = herbivoreService.getHerbivoresList().get(new Random().nextInt(herbivoreService.getHerbivoresList().size()));
            Animal carnivore = carnivoreService.getCarnivoresList().get(new Random().nextInt(carnivoreService.getCarnivoresList().size()));

            double successChance = calculateSuccessChanceForAttack(herbivore, carnivore);

            successChance = reduceSuccessChanceByWeightRatio(herbivore, carnivore, successChance);

            int randomSuccessChance = new Random().nextInt(0, 100);

            if (successChance > randomSuccessChance) {
                attack(herbivore, carnivore);
            } else {
                increaseHungerRate(carnivore);
            }

            removeCarnivoreIfReachedMaxHungerRate(carnivore, carnivore.getHungerRate());

            reduceReproductionRate(herbivore, carnivore);
            reproduceIfReproductionRateIsZero(carnivore, herbivore);
        }
    }

    private void reduceReproductionRate(Animal herbivore, Animal carnivore) {
        int herbivoreReproductionRate = herbivore.getReproductionRate();
        int carnivoreReproductionRate = carnivore.getReproductionRate();

        herbivoreReproductionRate--;
        carnivoreReproductionRate--;

        herbivore.setReproductionRate(herbivoreReproductionRate);
        carnivore.setReproductionRate(carnivoreReproductionRate);

    }

    private void attack(Animal herbivore, Animal carnivore) {
        int carnivoreHungerRate = carnivore.getHungerRate();

        removeHerbivore(herbivore);

        carnivoreHungerRate -= (herbivore.getWeight() / carnivore.getWeight()) / 100;
        carnivore.setHungerRate(carnivoreHungerRate);

        decreaseHungerRateIfAnimalInGroup(herbivore, carnivore);

        increaseAge(herbivore, carnivore);
    }

    public int scalePoints(Animal animal, int points) {
        return points * (1 - animal.getAge() / animal.getMaxAge());
    }

    private void reproduceIfReproductionRateIsZero(Animal carnivore, Animal herbivore) {
        if (carnivore.getReproductionRate() == 0) {
            carnivoreService.addCarnivore(new Carnivore(carnivore.getAnimalType(), carnivore.getMaxAge(), carnivore.getWeight(), carnivore.getReproductionRate(),
                    carnivore.getGroupMembers(), carnivore.getHabitatType(), carnivore.getLivingStatus(), carnivore.getPoints(), carnivore.getHungerRate()));
        }
        if (herbivore.getReproductionRate() == 0) {
            herbivoreService.addHerbivore(new Herbivore(herbivore.getAnimalType(), herbivore.getMaxAge(), herbivore.getWeight(), herbivore.getReproductionRate(),
                    herbivore.getGroupMembers(), herbivore.getHabitatType(), herbivore.getLivingStatus(), herbivore.getPoints()));
        }
    }

    private void increaseHungerRate(Animal carnivore) {
        int increaseOfHungerRate = 20;
        int carnivoreHungerRate = carnivore.getHungerRate() + increaseOfHungerRate;
        carnivore.setHungerRate(carnivoreHungerRate);
    }

    private void decreaseHungerRateIfAnimalInGroup(Animal herbivore, Animal carnivore) {
        if (carnivore.getLivingStatus().equals(LivingStatus.GROUP)) {
            double distributedWeight = herbivore.getWeight() / (carnivore.getGroupMembers() + 1);

            List<Animal> animals = carnivoreService.getGroup(carnivore);
            for (Animal animal : animals) {
                decreaseHungerRateOfCarnivore(animal, distributedWeight);
                carnivore.setHungerRate(animal.getHungerRate());
            }
            decreaseHungerRate(carnivore, distributedWeight);
        }
    }

    private void decreaseHungerRateOfCarnivore(Animal carnivore, double distributedWeight) {
        if (decreaseHungerRate(carnivore, distributedWeight) <= 0) {
            carnivore.setHungerRate(0);
        }
    }

    private int decreaseHungerRate(Animal carnivore, double distributedWeight) {
        int hungerRate = carnivore.getHungerRate();
        hungerRate -= distributedWeight;
        carnivore.setHungerRate(hungerRate);
        return hungerRate;
    }

    private void removeCarnivoreIfReachedMaxHungerRate(Animal carnivore, int carnivoreHungerRate) {
        int maxHungerRate = 100;
        if (carnivoreHungerRate >= maxHungerRate) {
            carnivoreService.removeCarnivore(carnivore);
            carnivoreService.getGroup(carnivore).remove(carnivore);
        }
    }

    private void removeHerbivore(Animal herbivore) {
        herbivoreService.removeHerbivore(herbivore);
        herbivoreService.getGroup(herbivore).remove(herbivore);
    }

    private void increaseAge(Animal herbivore, Animal carnivore) {
        int carnivoreAge = carnivore.getAge();
        int herbivoreAge = herbivore.getAge();

        carnivoreAge++;
        herbivoreAge++;

        carnivore.setAge(carnivoreAge);
        herbivore.setAge(herbivoreAge);
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
        double escapePoints = scalePoints(herbivore, herbivore.getPoints());
        double attackPoints = scalePoints(carnivore, carnivore.getPoints());

        return (attackPoints / (attackPoints + escapePoints)) * 100;
    }

    private Animal[] addCarnivore(Animal[] animals, Animal animalToAdd) {
        Animal[] newAnimals = new Carnivore[animals.length + 1];
        System.arraycopy(animals, 0, newAnimals, 0, animals.length);
        newAnimals[newAnimals.length - 1] = animalToAdd;

        return newAnimals;
    }

    private Animal[] addHerbivore(Animal[] animals, Animal animalToAdd) {
        Animal[] newAnimals = new Herbivore[animals.length + 1];
        System.arraycopy(animals, 0, newAnimals, 0, animals.length);
        newAnimals[newAnimals.length - 1] = animalToAdd;

        return newAnimals;
    }
}

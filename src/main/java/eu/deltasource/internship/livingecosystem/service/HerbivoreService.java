package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Group;
import eu.deltasource.internship.livingecosystem.model.Herbivore;
import eu.deltasource.internship.livingecosystem.model.HerbivoreGroup;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HerbivoreService {

    HerbivoreRepository herbivoreRepository;
    HerbivoreGroupRepository herbivoreGroupRepository;

    public HerbivoreService(HerbivoreRepository herbivoreRepository, HerbivoreGroupRepository herbivoreGroupRepository) {
        this.herbivoreRepository = herbivoreRepository;
        this.herbivoreGroupRepository = herbivoreGroupRepository;
    }

    public void addHerbivore(Herbivore animal) {
        herbivoreRepository.addHerbivore(animal);
    }

    public void removeHerbivore(Herbivore animal){
        herbivoreRepository.removeHerbivore(animal);
        getHerbivoreGroup(animal).remove(animal);
    }

    public List<Herbivore> getHerbivoresList() {
        return Collections.unmodifiableList(herbivoreRepository.getHerbivoresList());
    }

    public List<Herbivore> getHerbivoreGroup(Herbivore animal) {
        List<Herbivore> animals = new ArrayList<>();
        List<Group> groups = herbivoreGroupRepository.getHerbivoresList().stream().findAny().filter(a -> a.getAnimalType().equals(animal.getAnimalType())).stream().toList();
        for (Group group : groups) {
            List<Herbivore> animalList = group.getHerbivoreList();
            animals.addAll(animalList);
        }

        return animals;
    }

    public void removeHerbivoreIfReachedMaxAge(Herbivore herbivore) {
        if (herbivore.getAge() >= herbivore.getMaxAge()) {
            removeHerbivore(herbivore);
            getHerbivoreGroup(herbivore).remove(herbivore);
        }
    }

    public void addHerbivoreToGroup(Herbivore animal) {
        if (animal.getLivingStatus().equals(LivingStatus.GROUP)) {
            Group group = new HerbivoreGroup(animal.getAnimalType());
            group.addHerbivore(animal);
            for (int i = 0; i < animal.getGroupMembers()-1; i++) {
                group.addHerbivore(new Herbivore(animal.getAnimalType(), animal.getMaxAge(), animal.getWeight(), animal.getReproductionRate(),
                        animal.getGroupMembers(), animal.getHabitatType(), animal.getLivingStatus(), animal.getPoints()));
            }
            herbivoreGroupRepository.addGroup(group);
        }
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

            Herbivore herbivore = new Herbivore(herbivoreSpecie, herbivoreMaxAge, herbivoreWeight, herbivoreReproductionRate,
                    herbivoreGroupMembers, herbivoreHabitatType, herbivoreLivingStatus, herbivoreEscapePoints);
            herbivores = addHerbivore(herbivores, herbivore);
            herbivore.setBiomes(herbivore.getBiomes());

            herbivoreService.addHerbivore(herbivore);
            herbivoreService.addHerbivoreToGroup(herbivore);
        }
    }

    private Animal[] addHerbivore(Animal[] animals, Animal animalToAdd) {
        Animal[] newAnimals = new Herbivore[animals.length + 1];
        System.arraycopy(animals, 0, newAnimals, 0, animals.length);
        newAnimals[newAnimals.length - 1] = animalToAdd;

        return newAnimals;
    }
}

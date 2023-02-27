package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Group;
import eu.deltasource.internship.livingecosystem.model.Herbivore;
import eu.deltasource.internship.livingecosystem.model.HerbivoreGroup;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepository;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void removeHerbivore(Herbivore herbivore){
        herbivoreRepository.removeHerbivore(herbivore);
        for (Group group : herbivoreGroupRepository.getHerbivoreGroupList()) {
            if (group.getHerbivoreList().contains(herbivore)) {
                for (int i = 0; i < herbivore.getGroupMembers(); i++) {
                    group.removeHerbivore(group.getHerbivoreList().get(0));
                }
                if (group.getHerbivoreList().isEmpty()) {
                    herbivoreGroupRepository.removeGroup(group);
                }
                return;
            }
        }
    }

    public List<Herbivore> getHerbivoresList() {
        return Collections.unmodifiableList(herbivoreRepository.getHerbivoresList());
    }

    public List<Herbivore> getHerbivoreGroup(Herbivore animal) {
        List<Herbivore> animals = new ArrayList<>();
        List<Group> groups = new ArrayList<>();

        for (Group group : herbivoreGroupRepository.getHerbivoreGroupList()) {
            if (group.getSpecie().equals(animal.getSpecie())) {
                groups.add(group);
            }
        }

        for (Group group : groups) {
            List<Herbivore> animalList = group.getHerbivoreList();
            animals.addAll(animalList);
        }

        return animals;
    }

    public void removeHerbivoreIfDiedFromOldAge(Herbivore herbivore) {
        if (herbivore.getAge() >= herbivore.getMaxAge()) {
            removeHerbivore(herbivore);
            System.out.printf("%s reached it's max age and died.\n", herbivore.getSpecie());
        }
    }

    public void addHerbivoresToGroup(Herbivore animal) {
        if (animal.getLivingStatus().equals(LivingStatus.GROUP)) {
            Group group = new HerbivoreGroup(animal.getSpecie());
            group.addHerbivore(animal);
            for (int i = 0; i < animal.getGroupMembers()-1; i++) {
                group.addHerbivore(new Herbivore(animal.getSpecie(), animal.getMaxAge(), animal.getWeight(), animal.getReproductionRate(),
                        animal.getGroupMembers(), animal.getHabitatType(), animal.getLivingStatus(), animal.getPoints()));
            }
            herbivoreGroupRepository.addGroup(group);
        }
    }

    public Animal[] addHerbivoreToCollection(Animal[] animals, Animal animalToAdd) {
        Animal[] newAnimals = Arrays.copyOf(animals, animals.length + 1);
        newAnimals[newAnimals.length - 1] = animalToAdd;

        return newAnimals;
    }
}

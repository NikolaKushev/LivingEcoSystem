package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Group;
import eu.deltasource.internship.livingecosystem.model.Herbivore;
import eu.deltasource.internship.livingecosystem.model.HerbivoreGroup;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepository;

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

    public void addHerbivore(Animal animal) {
        herbivoreRepository.addHerbivore(animal);
    }

    public void removeHerbivore(Animal animal){
        herbivoreRepository.removeHerbivore(animal);
    }

    public List<Animal> getHerbivoresList() {
        return Collections.unmodifiableList(herbivoreRepository.getHerbivoresList());
    }

    public List<Animal> getGroup(Animal animal) {
        List<Animal> animals = new ArrayList<>();
        List<Group> groups = herbivoreGroupRepository.getHerbivoresList().stream().findAny().filter(a -> a.getAnimalType().equals(animal.getAnimalType())).stream().toList();
        for (Group group : groups) {
            List<Animal> animalList = group.getAnimalList();
            animals.addAll(animalList);
        }

        return animals;
    }

    public void addHerbivoreToGroup(Animal animal) {
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

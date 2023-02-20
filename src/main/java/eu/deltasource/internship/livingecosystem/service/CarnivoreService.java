package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Carnivore;
import eu.deltasource.internship.livingecosystem.model.CarnivoreGroup;
import eu.deltasource.internship.livingecosystem.model.Group;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarnivoreService {

    CarnivoreRepository carnivoreRepository;
    CarnivoreGroupRepository carnivoreGroupRepository;

    public CarnivoreService(CarnivoreRepository carnivoreRepository, CarnivoreGroupRepository carnivoreGroupRepository) {
        this.carnivoreRepository = carnivoreRepository;
        this.carnivoreGroupRepository = carnivoreGroupRepository;
    }

    public void addCarnivore(Animal animal) {
        carnivoreRepository.addCarnivore(animal);
    }

    public void removeCarnivore(Animal animal){
        carnivoreRepository.removeCarnivore(animal);
    }

    public List<Animal> getCarnivoresList() {
        return Collections.unmodifiableList(carnivoreRepository.getCarnivoresList());
    }

    public List<Animal> getGroup(Animal animal) {
        List<Animal> animals = new ArrayList<>();
        List<Group> groups = carnivoreGroupRepository.getCarnivoreGroupList().stream().findAny().filter(a -> a.getAnimalType().equals(animal.getAnimalType())).stream().toList();
        for (Group group : groups) {
            List<Animal> animalList = group.getAnimalList();
            animals.addAll(animalList);
        }

        return animals;
    }

    public void addCarnivoreToGroup(Animal animal) {
        if (animal.getLivingStatus().equals(LivingStatus.GROUP)) {
            Group group = new CarnivoreGroup(animal.getAnimalType());

            for (int i = 0; i < animal.getGroupMembers(); i++) {
                group.addAnimal(new Carnivore(animal.getAnimalType(), animal.getMaxAge(), animal.getWeight(), animal.getReproductionRate(),
                        animal.getGroupMembers(), animal.getHabitatType(), animal.getLivingStatus(), animal.getPoints(), animal.getHungerRate()));
            }
            carnivoreGroupRepository.addGroup(group);
        }
    }

}

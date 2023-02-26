package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Carnivore;
import eu.deltasource.internship.livingecosystem.model.CarnivoreGroup;
import eu.deltasource.internship.livingecosystem.model.Group;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CarnivoreService {

    CarnivoreRepository carnivoreRepository;
    CarnivoreGroupRepository carnivoreGroupRepository;

    public CarnivoreService(CarnivoreRepository carnivoreRepository, CarnivoreGroupRepository carnivoreGroupRepository) {
        this.carnivoreRepository = carnivoreRepository;
        this.carnivoreGroupRepository = carnivoreGroupRepository;
    }

    public void addCarnivore(Carnivore animal) {
        carnivoreRepository.addCarnivore(animal);
    }

    public void removeCarnivore(Carnivore carnivore) {
        carnivoreRepository.removeCarnivore(carnivore);
        for (Group group : carnivoreGroupRepository.getCarnivoreGroupList()) {
            if (group.getCarnivoreList().contains(carnivore)) {
                for (int i = 0; i < carnivore.getGroupMembers(); i++) {
                    group.removeCarnivore(group.getCarnivoreList().get(0));
                }
                if (group.getCarnivoreList().isEmpty()) {
                    carnivoreGroupRepository.removeGroup(group);
                }
                return;
            }
        }
    }

    public List<Carnivore> getCarnivoresList() {
        return Collections.unmodifiableList(carnivoreRepository.getCarnivoresList());
    }

    public void removeCarnivoreIfStarving(Carnivore carnivore) {
        if (carnivore.getHungerLevel() >= 100) {
            removeCarnivore(carnivore);
            System.out.printf("%s died from starvation.\n", carnivore.getSpecie());
        }
    }

    public void removeCarnivoreIfDiedFromOldAge(Carnivore carnivore) {
        if (carnivore.getAge() >= carnivore.getMaxAge()) {
            removeCarnivore(carnivore);
            System.out.printf("%s reached it's max age and died.\n", carnivore.getSpecie());
        }
    }

    public List<Carnivore> getCarnivoreGroup(Carnivore carnivore) {
        List<Carnivore> animals = new ArrayList<>();
        List<Group> groups = new ArrayList<>();

        for (Group group : carnivoreGroupRepository.getCarnivoreGroupList()) {
            if (group.getSpecie().equals(carnivore.getSpecie())) {
                groups.add(group);
            }
        }

        for (Group group : groups) {
            List<Carnivore> animalList = group.getCarnivoreList();
            animals.addAll(animalList);
        }

        return animals;
    }

    public void addCarnivoresToGroup(Carnivore carnivore) {
        if (carnivore.getLivingStatus().equals(LivingStatus.GROUP)) {
            Group group = new CarnivoreGroup(carnivore.getSpecie());
            group.addCarnivore(carnivore);
            for (int i = 0; i < carnivore.getGroupMembers() - 1; i++) {
                group.addCarnivore(new Carnivore(carnivore.getSpecie(), carnivore.getMaxAge(), carnivore.getWeight(), carnivore.getReproductionRate(),
                        carnivore.getGroupMembers(), carnivore.getHabitatType(), carnivore.getLivingStatus(),
                        carnivore.getPoints(), carnivore.getHungerRate()));
            }
            carnivoreGroupRepository.addGroup(group);
        }
    }

    public Animal[] addCarnivoreToCollection(Animal[] animals, Animal animalToAdd) {
        Animal[] newAnimals = Arrays.copyOf(animals, animals.length + 1);
        newAnimals[newAnimals.length - 1] = animalToAdd;

        return newAnimals;
    }

}

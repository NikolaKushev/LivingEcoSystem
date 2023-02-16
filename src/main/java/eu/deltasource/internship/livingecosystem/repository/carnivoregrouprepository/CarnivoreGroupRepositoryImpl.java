package eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarnivoreGroupRepositoryImpl implements CarnivoreGroupRepository {

    private List<Group> carnivoreGroupList;

    public CarnivoreGroupRepositoryImpl() {
        this.carnivoreGroupList = new ArrayList<>();
    }

    @Override
    public List<Animal> getGroup(Animal animal) {
        List<Animal> animals = new ArrayList<>();
        List<Group> groups = carnivoreGroupList.stream().findAny().filter(a -> a.getAnimalType().equals(animal.getAnimalType())).stream().toList();
        for (Group group : groups) {
            List<Animal> animalList = group.getAnimalList();
            animals.addAll(animalList);
        }

        return animals;
    }

    @Override
    public void addGroup(Group group) {
        carnivoreGroupList.add(group);
    }

}

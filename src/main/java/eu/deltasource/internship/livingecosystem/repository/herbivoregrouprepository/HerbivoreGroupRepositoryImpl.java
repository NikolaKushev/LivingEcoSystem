package eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HerbivoreGroupRepositoryImpl implements HerbivoreGroupRepository {

    private List<Group> herbivoreGroupList = new ArrayList<>();

    public List<Group> getHerbivoresList() {
        return Collections.unmodifiableList(herbivoreGroupList);
    }

    public List<Animal> getGroup(Animal animal) {
        List<Animal> animals = new ArrayList<>();
        List<Group> groups = herbivoreGroupList.stream().findAny().filter(a -> a.getAnimalType().equals(animal.getAnimalType())).stream().toList();
        for (Group group : groups) {
            List<Animal> animalList = group.getAnimalList();
            animals.addAll(animalList);
        }

        return animals;
    }

    @Override
    public void addGroup(Group group) {
        herbivoreGroupList.add(group);
    }

}

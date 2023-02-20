package eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Group;

import java.util.List;
import java.util.Optional;

public interface CarnivoreGroupRepository {
    void addGroup(Group group);
    List<Group> getCarnivoreGroupList();
    List<Animal> getGroup(Animal animal);
}

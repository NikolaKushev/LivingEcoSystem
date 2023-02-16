package eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Group;

import java.util.List;

public interface HerbivoreGroupRepository {
    void addGroup(Group group);
    List<Animal> getGroup(Animal animal);
}

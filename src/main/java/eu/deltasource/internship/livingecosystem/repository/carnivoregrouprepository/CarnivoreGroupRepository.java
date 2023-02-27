package eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository;

import eu.deltasource.internship.livingecosystem.model.Group;

import java.util.List;

public interface CarnivoreGroupRepository {
    void addGroup(Group group);
    void removeGroup(Group group);
    List<Group> getCarnivoreGroupList();
}

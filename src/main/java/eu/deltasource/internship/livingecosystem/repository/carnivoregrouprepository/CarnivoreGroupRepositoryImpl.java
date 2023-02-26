package eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarnivoreGroupRepositoryImpl implements CarnivoreGroupRepository {

    private List<Group> carnivoreGroupList = new ArrayList<>();

    public List<Group> getCarnivoreGroupList() {
        return Collections.unmodifiableList(carnivoreGroupList);
    }

    @Override
    public void addGroup(Group group) {
        carnivoreGroupList.add(group);
    }

    @Override
    public void removeGroup(Group group) {
        carnivoreGroupList.remove(group);
    }


}

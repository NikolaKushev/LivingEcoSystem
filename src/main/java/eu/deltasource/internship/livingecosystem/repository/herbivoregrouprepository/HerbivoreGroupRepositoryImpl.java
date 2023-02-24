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

    @Override
    public void addGroup(Group group) {
        herbivoreGroupList.add(group);
    }
}

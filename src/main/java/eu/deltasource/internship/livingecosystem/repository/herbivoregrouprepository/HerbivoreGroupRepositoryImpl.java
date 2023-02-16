package eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository;

import eu.deltasource.internship.livingecosystem.model.Group;

import java.util.ArrayList;
import java.util.List;

public class HerbivoreGroupRepositoryImpl implements HerbivoreGroupRepository {

    private List<Group> herbivoreGroupList;

    public HerbivoreGroupRepositoryImpl() {
        this.herbivoreGroupList = new ArrayList<>();
    }

    @Override
    public void addGroup(Group group) {
        herbivoreGroupList.add(group);
    }

}

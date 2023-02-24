package eu.deltasource.internship.livingecosystem.repository.herbivorerepository;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Herbivore;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HerbivoreRepositoryImpl implements HerbivoreRepository {

    private List<Herbivore> herbivoresList = new ArrayList<>();

    public List<Herbivore> getHerbivoresList() {
        return Collections.unmodifiableList(herbivoresList);
    }

    @Override
    public void addHerbivore(Herbivore animal) {
        herbivoresList.add(animal);
    }

    @Override
    public void removeHerbivore(Herbivore animal) {
        herbivoresList.remove(animal);
    }

}

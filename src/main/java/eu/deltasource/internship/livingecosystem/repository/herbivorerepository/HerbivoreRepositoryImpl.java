package eu.deltasource.internship.livingecosystem.repository.herbivorerepository;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HerbivoreRepositoryImpl implements HerbivoreRepository {

    private List<Animal> herbivoresList = new ArrayList<>();

    public List<Animal> getHerbivoresList() {
        return Collections.unmodifiableList(herbivoresList);
    }

    @Override
    public void addHerbivore(Animal animal) {
        herbivoresList.add(animal);
    }

    @Override
    public void removeHerbivore(Animal animal) {
        herbivoresList.remove(animal);
    }

}

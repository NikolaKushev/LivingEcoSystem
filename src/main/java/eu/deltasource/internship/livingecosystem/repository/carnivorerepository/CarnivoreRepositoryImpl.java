package eu.deltasource.internship.livingecosystem.repository.carnivorerepository;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Carnivore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarnivoreRepositoryImpl implements CarnivoreRepository {

    private List<Carnivore> carnivoresList= new ArrayList<>();

    @Override
    public void addCarnivore(Carnivore animal) {
        carnivoresList.add(animal);
    }

    public void removeCarnivore(Carnivore animal){
        carnivoresList.remove(animal);
    }

    public List<Carnivore> getCarnivoresList() {
        return Collections.unmodifiableList(carnivoresList);
    }
}

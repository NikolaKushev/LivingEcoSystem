package eu.deltasource.internship.livingecosystem.repository.carnivorerepository;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarnivoreRepositoryImpl implements CarnivoreRepository {

    private List<Animal> carnivoresList= new ArrayList<>();

    @Override
    public void addCarnivore(Animal animal) {
        carnivoresList.add(animal);
    }

    public void removeCarnivore(Animal animal){
        carnivoresList.remove(animal);
    }

    public List<Animal> getCarnivoresList() {
        return Collections.unmodifiableList(carnivoresList);
    }
}

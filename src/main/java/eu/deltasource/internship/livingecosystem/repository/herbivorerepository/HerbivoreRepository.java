package eu.deltasource.internship.livingecosystem.repository.herbivorerepository;

import eu.deltasource.internship.livingecosystem.model.Animal;

import java.util.List;

public interface HerbivoreRepository {
    void addHerbivore(Animal animal);
    void removeHerbivore(Animal animal);
    List<Animal> getHerbivoresList();
}

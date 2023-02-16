package eu.deltasource.internship.livingecosystem.repository.carnivorerepository;

import eu.deltasource.internship.livingecosystem.model.Animal;

import java.util.List;

public interface CarnivoreRepository {
    void addCarnivore(Animal animal);
    void removeCarnivore(Animal animal);
    List<Animal> getCarnivoresList();
}

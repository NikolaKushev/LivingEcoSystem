package eu.deltasource.internship.livingecosystem.repository.carnivorerepository;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Carnivore;

import java.util.List;

public interface CarnivoreRepository {
    void addCarnivore(Carnivore animal);
    void removeCarnivore(Carnivore animal);
    List<Carnivore> getCarnivoresList();
}

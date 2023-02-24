package eu.deltasource.internship.livingecosystem.repository.herbivorerepository;

import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Herbivore;

import java.util.List;

public interface HerbivoreRepository {
    void addHerbivore(Herbivore animal);
    void removeHerbivore(Herbivore animal);
    List<Herbivore> getHerbivoresList();
}

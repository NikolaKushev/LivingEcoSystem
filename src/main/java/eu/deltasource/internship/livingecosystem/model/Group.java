package eu.deltasource.internship.livingecosystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Group {

    private List<Carnivore> carnivoreList;
    private List<Herbivore> herbivoreList;
    private String animalType;

    public Group(String animalType) {
        this.carnivoreList = new ArrayList<>();
        this.herbivoreList = new ArrayList<>();
        this.animalType = animalType;
    }

    public List<Carnivore> getCarnivoreList() {
        return Collections.unmodifiableList(carnivoreList);
    }

    public void addCarnivore(Carnivore animal){
        carnivoreList.add(animal);
    }

    public List<Herbivore> getHerbivoreList() {
        return Collections.unmodifiableList(herbivoreList);
    }

    public void addHerbivore(Herbivore animal){
        herbivoreList.add(animal);
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }
}

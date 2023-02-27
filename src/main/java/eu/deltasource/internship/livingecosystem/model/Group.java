package eu.deltasource.internship.livingecosystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Group {

    private List<Carnivore> carnivoreList;
    private List<Herbivore> herbivoreList;
    private String specie;

    public Group(String specie) {
        this.carnivoreList = new ArrayList<>();
        this.herbivoreList = new ArrayList<>();
        this.specie = specie;
    }

    public List<Carnivore> getCarnivoreList() {
        return Collections.unmodifiableList(carnivoreList);
    }

    public void addCarnivore(Carnivore animal){
        carnivoreList.add(animal);
    }

    public void removeCarnivore(Carnivore carnivore){
        carnivoreList.remove(carnivore);
    }

    public void removeHerbivore(Herbivore herbivore){
        herbivoreList.remove(herbivore);
    }

    public List<Herbivore> getHerbivoreList() {
        return Collections.unmodifiableList(herbivoreList);
    }

    public void addHerbivore(Herbivore animal){
        herbivoreList.add(animal);
    }

    public String getSpecie() {
        return specie;
    }
}

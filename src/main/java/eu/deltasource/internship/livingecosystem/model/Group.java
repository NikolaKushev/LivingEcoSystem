package eu.deltasource.internship.livingecosystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Group {

    private List<Animal> animalList;
    private String animalType;

    public Group(String animalType) {
        this.animalList = new ArrayList<>();
        this.animalType = animalType;
    }

    public List<Animal> getAnimalList() {
        return Collections.unmodifiableList(animalList);
    }

    public void addAnimal(Animal animal){
        animalList.add(animal);
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }
}

package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Carnivore;
import eu.deltasource.internship.livingecosystem.model.CarnivoreGroup;
import eu.deltasource.internship.livingecosystem.model.Group;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarnivoreService {

    CarnivoreRepository carnivoreRepository;
    CarnivoreGroupRepository carnivoreGroupRepository;

    public CarnivoreService(CarnivoreRepository carnivoreRepository, CarnivoreGroupRepository carnivoreGroupRepository) {
        this.carnivoreRepository = carnivoreRepository;
        this.carnivoreGroupRepository = carnivoreGroupRepository;
    }

    public void addCarnivore(Carnivore animal) {
        carnivoreRepository.addCarnivore(animal);
    }

    public void removeCarnivore(Carnivore carnivore){
        carnivoreRepository.removeCarnivore(carnivore);
        getCarnivoreGroup(carnivore).remove(carnivore);
    }

    public List<Carnivore> getCarnivoresList() {
        return Collections.unmodifiableList(carnivoreRepository.getCarnivoresList());
    }

    public void removeCarnivoreIfReachedMaxHungerRate(Carnivore carnivore, int carnivoreHungerRate) {
        int maxHungerRate = 100;
        if (carnivoreHungerRate >= maxHungerRate) {
            removeCarnivore(carnivore);
        }
    }

    public void removeCarnivoreIfReachedMaxAge(Carnivore carnivore) {
        if (carnivore.getAge() >= carnivore.getMaxAge()) {
            removeCarnivore(carnivore);
        }
    }

    public List<Carnivore> getCarnivoreGroup(Carnivore carnivore) {
        List<Carnivore> animals = new ArrayList<>();
        List<Group> groups = carnivoreGroupRepository.getCarnivoreGroupList().stream().findAny().filter(a -> a.getAnimalType().equals(carnivore.getAnimalType())).stream().toList();
        for (Group group : groups) {
            List<Carnivore> animalList = group.getCarnivoreList();
            animals.addAll(animalList);
        }

        return animals;
    }

    public void addCarnivoreToGroup(Carnivore carnivore) {
        if (carnivore.getLivingStatus().equals(LivingStatus.GROUP)) {
            Group group = new CarnivoreGroup(carnivore.getAnimalType());
            group.addCarnivore(carnivore);
            for (int i = 0; i < carnivore.getGroupMembers()-1; i++) {
                group.addCarnivore(new Carnivore(carnivore.getAnimalType(), carnivore.getMaxAge(), carnivore.getWeight(), carnivore.getReproductionRate(),
                        carnivore.getGroupMembers(), carnivore.getHabitatType(), carnivore.getLivingStatus(), carnivore.getPoints(),
                        carnivore.getHungerLevel(), carnivore.getHungerRate()));
            }
            carnivoreGroupRepository.addGroup(group);
        }
    }

    public void createCarnivores(CarnivoreService carnivoreService) throws IOException {
        Animal[] carnivores = new Carnivore[0];
        BufferedReader carnivoreBuffer = new BufferedReader(new FileReader("CarnivoreInfo.txt"));
        String carnivoreInfo;
        while ((carnivoreInfo = carnivoreBuffer.readLine()) != null) {
            String[] values = carnivoreInfo.split("-");
            String carnivoreSpecie = values[0];
            int carnivoreMaxAge = Integer.parseInt(values[1]);
            double carnivoreWeight = Double.parseDouble(values[2]);
            int carnivoreReproductionRate = Integer.parseInt(values[3]);
            int carnivoreGroupMembers = Integer.parseInt(values[4]);
            HabitatType carnivoreHabitatType = HabitatType.valueOf(values[5]);
            LivingStatus carnivoreLivingStatus = LivingStatus.valueOf(values[6]);
            int carnivoreAttackPoints = Integer.parseInt(values[7]);
            int hungerLevel = Integer.parseInt(values[8]);
            int hungerRate = Integer.parseInt(values[9]);

            Carnivore carnivore = new Carnivore(carnivoreSpecie, carnivoreMaxAge, carnivoreWeight, carnivoreReproductionRate,
                    carnivoreGroupMembers, carnivoreHabitatType, carnivoreLivingStatus, carnivoreAttackPoints,
                    hungerLevel, hungerRate);
            carnivores = addCarnivore(carnivores, carnivore);
            carnivore.setBiomes(carnivore.getBiomes());

            carnivoreService.addCarnivore(carnivore);
            carnivoreService.addCarnivoreToGroup(carnivore);
        }
    }

    private Animal[] addCarnivore(Animal[] animals, Animal animalToAdd) {
        Animal[] newAnimals = new Carnivore[animals.length + 1];
        System.arraycopy(animals, 0, newAnimals, 0, animals.length);
        newAnimals[newAnimals.length - 1] = animalToAdd;

        return newAnimals;
    }

}

package eu.deltasource.internship.livingecosystem;

import eu.deltasource.internship.livingecosystem.model.Carnivore;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
        HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
        CarnivoreGroupRepository carnivoreGroupRepository = new CarnivoreGroupRepositoryImpl();
        HerbivoreGroupRepository herbivoreGroupRepository = new HerbivoreGroupRepositoryImpl();

        EcoSystemService ecoSystemService = new EcoSystemService(carnivoreRepository,herbivoreRepository,
                carnivoreGroupRepository,herbivoreGroupRepository);
        ecoSystemService.simulate();
    }
}

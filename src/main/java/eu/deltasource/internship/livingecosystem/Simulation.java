package eu.deltasource.internship.livingecosystem;

import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.livingecosystem.service.CarnivoreService;
import eu.deltasource.internship.livingecosystem.service.EcoSystemService;
import eu.deltasource.internship.livingecosystem.service.HerbivoreService;

import java.io.IOException;

public class Simulation {
    public static void main(String[] args) throws IOException {

        CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
        CarnivoreGroupRepository carnivoreGroupRepository = new CarnivoreGroupRepositoryImpl();
        CarnivoreService carnivoreService = new CarnivoreService(carnivoreRepository, carnivoreGroupRepository);

        HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
        HerbivoreGroupRepository herbivoreGroupRepository = new HerbivoreGroupRepositoryImpl();
        HerbivoreService herbivoreService = new HerbivoreService(herbivoreRepository, herbivoreGroupRepository);

        EcoSystemService ecoSystemService = new EcoSystemService(carnivoreService, herbivoreService);

        ecoSystemService.createHerbivores(herbivoreService);
        ecoSystemService.createCarnivores(carnivoreService);

        ecoSystemService.simulate();
    }






}

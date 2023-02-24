package eu.deltasource.internship.livingecosystem;

import eu.deltasource.internship.livingecosystem.helper.HungerLevelCalculator;
import eu.deltasource.internship.livingecosystem.helper.AgeCalculator;
import eu.deltasource.internship.livingecosystem.helper.ReproductionRateCalculator;
import eu.deltasource.internship.livingecosystem.helper.SuccessChanceCalculator;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.livingecosystem.service.*;

import java.io.IOException;

public class Simulation {
    public static void main(String[] args) throws IOException {

        CarnivoreRepository carnivoreRepository = new CarnivoreRepositoryImpl();
        CarnivoreGroupRepository carnivoreGroupRepository = new CarnivoreGroupRepositoryImpl();
        CarnivoreService carnivoreService = new CarnivoreService(carnivoreRepository, carnivoreGroupRepository);

        HerbivoreRepository herbivoreRepository = new HerbivoreRepositoryImpl();
        HerbivoreGroupRepository herbivoreGroupRepository = new HerbivoreGroupRepositoryImpl();
        HerbivoreService herbivoreService = new HerbivoreService(herbivoreRepository, herbivoreGroupRepository);

        ReproductionRateCalculator reproductionRateCalculator = new ReproductionRateCalculator(carnivoreService, herbivoreService);
        SuccessChanceCalculator successChanceCalculator = new SuccessChanceCalculator();
        HungerLevelCalculator hungerLevelCalculator = new HungerLevelCalculator(carnivoreService);
        AgeCalculator ageCalculator = new AgeCalculator(carnivoreService, herbivoreService);

        EcoSystemService ecoSystemService = new EcoSystemService(carnivoreService, herbivoreService, reproductionRateCalculator,
                successChanceCalculator, hungerLevelCalculator, ageCalculator);

        herbivoreService.createHerbivores(herbivoreService);
        carnivoreService.createCarnivores(carnivoreService);

        ecoSystemService.simulate(90);
    }


}

package eu.deltasource.internship.livingecosystem;

import eu.deltasource.internship.livingecosystem.enums.LivingHabitat;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.helper.HungerLevelCalculator;
import eu.deltasource.internship.livingecosystem.helper.AgeCalculator;
import eu.deltasource.internship.livingecosystem.helper.ReproductionRateCalculator;
import eu.deltasource.internship.livingecosystem.helper.SuccessChanceCalculator;
import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Carnivore;
import eu.deltasource.internship.livingecosystem.model.Herbivore;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.livingecosystem.service.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Application {
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

        createHerbivores(herbivoreService);
        createCarnivores(carnivoreService);

        ecoSystemService.simulateEcoSystem();
    }

    public static void createHerbivores(HerbivoreService herbivoreService) throws IOException {
        BufferedReader herbivoreBuffer = new BufferedReader(new FileReader("HerbivoreInfo.txt"));
        String herbivoreInfo;

        Animal[] herbivores = new Herbivore[0];
        while ((herbivoreInfo = herbivoreBuffer.readLine()) != null) {
            String[] values = herbivoreInfo.split("-");
            String herbivoreSpecie = values[0];
            int herbivoreMaxAge = Integer.parseInt(values[1]);
            double herbivoreWeight = Double.parseDouble(values[2]);
            int herbivoreReproductionRate = Integer.parseInt(values[3]);
            int herbivoreGroupMembers = Integer.parseInt(values[4]);
            LivingHabitat herbivoreLivingHabitat = LivingHabitat.valueOf(values[5]);
            LivingStatus herbivoreLivingStatus = LivingStatus.valueOf(values[6]);
            int herbivoreEscapePoints = Integer.parseInt(values[7]);

            Herbivore herbivore = new Herbivore(herbivoreSpecie, herbivoreMaxAge, herbivoreWeight, herbivoreReproductionRate,
                    herbivoreGroupMembers, herbivoreLivingHabitat, herbivoreLivingStatus, herbivoreEscapePoints);
            herbivores = herbivoreService.addHerbivoreToCollection(herbivores, herbivore);
            herbivore.setBiomes(herbivore.getBiomes());

            herbivoreService.addHerbivore(herbivore);
            herbivoreService.addHerbivoresToGroup(herbivore);
        }
    }

    public static void createCarnivores(CarnivoreService carnivoreService) throws IOException {
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
            LivingHabitat carnivoreLivingHabitat = LivingHabitat.valueOf(values[5]);
            LivingStatus carnivoreLivingStatus = LivingStatus.valueOf(values[6]);
            int carnivoreAttackPoints = Integer.parseInt(values[7]);
            int hungerLevel = Integer.parseInt(values[8]);
            int hungerRate = Integer.parseInt(values[9]);

            Carnivore carnivore = new Carnivore(carnivoreSpecie, carnivoreMaxAge, carnivoreWeight, carnivoreReproductionRate,
                    carnivoreGroupMembers, carnivoreLivingHabitat, carnivoreLivingStatus, carnivoreAttackPoints,
                    hungerLevel, hungerRate);
            carnivores = carnivoreService.addCarnivoreToCollection(carnivores, carnivore);
            carnivore.setBiomes(carnivore.getBiomes());

            carnivoreService.addCarnivore(carnivore);
            carnivoreService.addCarnivoresToGroup(carnivore);
        }
    }

}

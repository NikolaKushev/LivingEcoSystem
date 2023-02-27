package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.LivingHabitat;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.helper.AgeCalculator;
import eu.deltasource.internship.livingecosystem.helper.HungerLevelCalculator;
import eu.deltasource.internship.livingecosystem.helper.ReproductionRateCalculator;
import eu.deltasource.internship.livingecosystem.helper.SuccessChanceCalculator;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuccessChanceCalculatorTest {

    HerbivoreRepository herbivoreRepository;
    CarnivoreRepository carnivoreRepository;
    HerbivoreGroupRepository herbivoreGroupRepository;
    CarnivoreGroupRepository carnivoreGroupRepository;

    CarnivoreService carnivoreService;
    HerbivoreService herbivoreService;
    EcoSystemService ecoSystemService;
    ReproductionRateCalculator reproductionRateCalculator;
    SuccessChanceCalculator successChanceCalculator;
    HungerLevelCalculator hungerLevelCalculator;
    AgeCalculator ageCalculator;

    Herbivore zebra;
    Herbivore hare;
    Carnivore cheetah;
    Carnivore lion;

    @BeforeEach
    void setUp() {
        herbivoreRepository = new HerbivoreRepositoryImpl();
        herbivoreGroupRepository = new HerbivoreGroupRepositoryImpl();
        carnivoreRepository = new CarnivoreRepositoryImpl();
        carnivoreGroupRepository = new CarnivoreGroupRepositoryImpl();

        herbivoreService = new HerbivoreService(herbivoreRepository, herbivoreGroupRepository);
        carnivoreService = new CarnivoreService(carnivoreRepository, carnivoreGroupRepository);
        reproductionRateCalculator = new ReproductionRateCalculator(carnivoreService , herbivoreService);
        hungerLevelCalculator = new HungerLevelCalculator(carnivoreService);
        ageCalculator = new AgeCalculator(carnivoreService, herbivoreService);
        successChanceCalculator = new SuccessChanceCalculator();
        ecoSystemService = new EcoSystemService(carnivoreService, herbivoreService, reproductionRateCalculator,
                successChanceCalculator, hungerLevelCalculator, ageCalculator);
    }

    @Test
    public void testCalculateSuccessChanceWhenAnimalsAreBothInGroup(){
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);
        lion = new Carnivore("lion", 30, 150, 6, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80,20);

        //WHEN
        double successChance = successChanceCalculator.getSuccessChance(zebra, lion);

        //THEN
        assertEquals(64.45, successChance);
    }

    @Test
    public void testCalculateSuccessChanceIfWhenCarnivoreIsAloneAndHerbivoreIsInGroup() {
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);
        cheetah = new Carnivore("cheetah", 30, 60, 5, 1,
                LivingHabitat.LAND, LivingStatus.ALONE, 110,15);

        //WHEN
        double successChance = successChanceCalculator.getSuccessChance(zebra, cheetah);

        //THEN
        assertEquals(5.72, successChance);
    }

    @Test
    public void testCalculateSuccessChanceWhenCarnivoreAndHerbivoreAreAlone() {
        //GIVEN
        cheetah = new Carnivore("cheetah", 30, 60, 5, 1,
                LivingHabitat.LAND, LivingStatus.ALONE, 110,15);
        hare = new Herbivore("hare", 24, 5, 3, 1,
                LivingHabitat.LAND, LivingStatus.ALONE, 100);

        //WHEN
        double successChance = successChanceCalculator.getSuccessChance(hare, cheetah);

        //THEN
        assertEquals(26.19, successChance);
    }
}
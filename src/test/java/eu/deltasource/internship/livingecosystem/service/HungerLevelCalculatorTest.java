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

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO
class HungerLevelCalculatorTest {

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

    Carnivore lion;
    Herbivore zebra;

    @BeforeEach
    void setUp() {
        herbivoreRepository = new HerbivoreRepositoryImpl();
        carnivoreRepository = new CarnivoreRepositoryImpl();
        herbivoreGroupRepository = new HerbivoreGroupRepositoryImpl();
        carnivoreGroupRepository = new CarnivoreGroupRepositoryImpl();

        herbivoreService = new HerbivoreService(herbivoreRepository, herbivoreGroupRepository);
        carnivoreService = new CarnivoreService(carnivoreRepository, carnivoreGroupRepository);
        ecoSystemService = new EcoSystemService(carnivoreService, herbivoreService, reproductionRateCalculator,
                successChanceCalculator, hungerLevelCalculator, ageCalculator);
        hungerLevelCalculator= new HungerLevelCalculator(carnivoreService);
    }

    @Test
    public void testIncreaseOfHungerLevelAfterEachIteration(){
        //GIVEN
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80, 20);
        carnivoreService.addCarnivore(lion);
        carnivoreService.addCarnivoresToGroup(lion);

        //WHEN
        hungerLevelCalculator.increaseHungerLevel(lion);

        //THEN
        assertEquals(20, lion.getHungerLevel());
    }

    @Test
    public void testDecreaseHungerLevelOfCarnivoreIfAttackIsSuccessful(){
        //GIVEN
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80, 20);
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);
        lion.setHungerLevel(50);
        carnivoreService.addCarnivore(lion);
        carnivoreService.addCarnivoresToGroup(lion);
        herbivoreService.addHerbivore(zebra);
        herbivoreService.addHerbivoresToGroup(zebra);

        //WHEN
        hungerLevelCalculator.decreaseHungerLevelOfCarnivore(zebra, lion, lion.getHungerLevel());

        //THEN
        assertEquals(49, lion.getHungerLevel());
    }

    @Test
    public void testDecreaseHungerLevelOfCarnivoreGroup(){
        //GIVEN
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80, 20);
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);

        carnivoreService.addCarnivore(lion);
        carnivoreService.addCarnivoresToGroup(lion);
        herbivoreService.addHerbivore(zebra);
        herbivoreService.addHerbivoresToGroup(zebra);
        lion.setHungerLevel(70);

        //WHEN
        hungerLevelCalculator.decreaseHungerLevelIfCarnivoreInGroup(zebra, lion);

        //THEN
        assertEquals(10, lion.getHungerLevel());
    }
}
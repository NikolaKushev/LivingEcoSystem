package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.LivingHabitat;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.helper.AgeCalculator;
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

class AgeCalculatorTest {

    HerbivoreRepository herbivoreRepository;
    HerbivoreGroupRepository herbivoreGroupRepository;
    CarnivoreRepository carnivoreRepository;
    CarnivoreGroupRepository carnivoreGroupRepository;

    CarnivoreService carnivoreService;
    HerbivoreService herbivoreService;
    AgeCalculator ageCalculator;

    Herbivore zebra;
    Carnivore lion;

    @BeforeEach
    void setUp() {
        herbivoreRepository = new HerbivoreRepositoryImpl();
        herbivoreGroupRepository = new HerbivoreGroupRepositoryImpl();
        carnivoreRepository = new CarnivoreRepositoryImpl();
        carnivoreGroupRepository = new CarnivoreGroupRepositoryImpl();

        herbivoreService = new HerbivoreService(herbivoreRepository, herbivoreGroupRepository);
        carnivoreService = new CarnivoreService(carnivoreRepository, carnivoreGroupRepository);
        ageCalculator = new AgeCalculator(carnivoreService, herbivoreService);
    }

    @Test
    public void testIsAgeIncreasedAfterEachIteration() {
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80, 0, 20);
        carnivoreService.addCarnivore(lion);
        herbivoreService.addHerbivore(zebra);

        //WHEN
        ageCalculator.increaseAge();

        //THEN
        assertEquals(2, zebra.getAge());
        assertEquals(2, lion.getAge());
    }
}
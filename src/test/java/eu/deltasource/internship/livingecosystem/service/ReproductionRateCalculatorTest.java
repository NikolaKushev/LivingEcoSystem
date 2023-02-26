package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.LivingHabitat;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.helper.ReproductionRateCalculator;
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

class ReproductionRateCalculatorTest {

    HerbivoreRepository herbivoreRepository;
    CarnivoreRepository carnivoreRepository;
    HerbivoreGroupRepository herbivoreGroupRepository;
    CarnivoreGroupRepository carnivoreGroupRepository;

    CarnivoreService carnivoreService;
    HerbivoreService herbivoreService;
    ReproductionRateCalculator reproductionRateCalculator;


    Herbivore zebra;
    Carnivore lion;

    @BeforeEach
    void setUp() {
        herbivoreRepository = new HerbivoreRepositoryImpl();
        carnivoreRepository = new CarnivoreRepositoryImpl();
        herbivoreGroupRepository = new HerbivoreGroupRepositoryImpl();
        carnivoreGroupRepository = new CarnivoreGroupRepositoryImpl();

        herbivoreService = new HerbivoreService(herbivoreRepository, herbivoreGroupRepository);
        carnivoreService = new CarnivoreService(carnivoreRepository, carnivoreGroupRepository);
        reproductionRateCalculator = new ReproductionRateCalculator(carnivoreService, herbivoreService);
    }

    @Test
    public void testReduceReproductionRateAfterEachIteration() {
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80, 0, 20);

        //WHEN
        reproductionRateCalculator.reduceReproductionRate(zebra, lion);

        //THEN
        assertEquals(9, zebra.getReproductionRate());
        assertEquals(4, lion.getReproductionRate());
    }

    @Test
    public void testReproduceAnimalIfReproductionRateIsZero() {
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80, 0, 20);
        zebra.setReproductionRate(0);
        lion.setReproductionRate(0);
        carnivoreService.addCarnivore(lion);
        herbivoreService.addHerbivore(zebra);

        //WHEN
        reproductionRateCalculator.reproduceIfReproductionRateIsZero(lion, zebra);

        //THEN
        assertEquals(2, carnivoreService.getCarnivoresList().size());
        assertEquals(2, herbivoreService.getHerbivoresList().size());
    }

    @Test
    public void testIsReproductionSetToItsOriginalAfterReproduction() {
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80, 0, 20);
        zebra.setReproductionRate(0);
        lion.setReproductionRate(0);
        carnivoreService.addCarnivore(lion);
        herbivoreService.addHerbivore(zebra);

        //WHEN
        reproductionRateCalculator.reproduceIfReproductionRateIsZero(lion, zebra);

        //THEN
        assertEquals(10, zebra.getReproductionRate());
        assertEquals(5, lion.getReproductionRate());
    }
}
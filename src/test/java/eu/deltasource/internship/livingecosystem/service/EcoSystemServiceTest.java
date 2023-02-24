package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EcoSystemServiceTest {

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
    Carnivore lion;
    Carnivore cheetah;

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

        hare = new Herbivore("hare", 24, 5, 3, 1,
                HabitatType.LAND, LivingStatus.ALONE, 100);
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                HabitatType.LAND, LivingStatus.GROUP, 80);
        lion = new Carnivore("lion", 30, 150, 6, 4,
                HabitatType.LAND, LivingStatus.GROUP, 80,0, 20);
        cheetah = new Carnivore("cheetah", 30, 60, 5, 1,
                HabitatType.LAND, LivingStatus.ALONE, 110,0, 15);
    }

    @Test
    public void testRemoveCarnivoreWhenAttackIsNotExecuted() {
        //GIVEN
        carnivoreService.addCarnivore(cheetah);
        herbivoreService.addHerbivore(zebra);
        herbivoreService.addHerbivoreToGroup(zebra);

        //WHEN
        ecoSystemService.simulate(34);

        //THEN
        assertEquals(0, carnivoreService.getCarnivoresList().size());
    }

    @Test
    public void testRemoveHerbivoreWhenAttackIsExecuted() {
        //GIVEN
        carnivoreService.addCarnivore(lion);
        carnivoreService.addCarnivoreToGroup(lion);
        herbivoreService.addHerbivore(zebra);
        herbivoreService.addHerbivoreToGroup(zebra);

        //WHEN
        ecoSystemService.simulate(50);
    }

//    @Test
//    public void testDecreaseHungerRateOfCarnivore() {
//        //GIVEN
//        int hungerRate = lion.getHungerRate();
//
//        //WHEN
//        ecoSystemService.decreaseHungerRateOfCarnivore(zebra, lion, hungerRate);
//
//        //THEN
//        assertEquals(19, lion.getHungerRate());
//    }
//
//    @Test
//    public void testIncreaseAge() {
//        //GIVEN
//
//        //WHEN
//        ecoSystemService.increaseAge(lion);
//        ecoSystemService.increaseAge(zebra);
//        int carnivoreAge = lion.getAge();
//        int herbivoreAge = zebra.getAge();
//
//        //THEN
//        assertEquals(2, carnivoreAge);
//        assertEquals(2, herbivoreAge);
//    }
//
//    @Test
//    public void testReduceReproductionRate() {
//        //GIVEN
//
//        //WHEN
//        ecoSystemService.reduceReproductionRate(zebra, lion);
//        int herbivoreReproductionRate = zebra.getReproductionRate();
//        int carnivoreReproductionRate = lion.getReproductionRate();
//
//        //THEN
//        assertEquals(9, herbivoreReproductionRate);
//        assertEquals(5, carnivoreReproductionRate);
//
//    }
//
//    @Test
//    public void testCalculateSuccessChance() {
//        //GIVEN
//        double successChance;
//        //WHEN
//        successChance = ecoSystemService.calculateSuccessChance(zebra, lion);
//
//        //THEN
//        assertEquals(50, successChance);
//    }
//
//    @Test
//    public void testIfHungerRateIsSetToZeroIfAfterDecreasingIsNegative() {
//        //GIVEN
//        double distributedWeight = zebra.getWeight() / (lion.getGroupMembers() + 1);
//
//        //WHEN
//        ecoSystemService.checkIfHungerRateLessThanZero(lion, distributedWeight);
//
//        //THEN
//        assertEquals(0, lion.getHungerRate());
//    }
//
//    @Test
//    public void testCalculateSuccessChanceIfHerbivoreAndCarnivoreAreGrouped() {
//        //GIVEN
//        double successChance;
//
//        //WHEN
//        successChance = ecoSystemService.calculateSuccessChanceForAttack(zebra, lion);
//
//        //THEN
//        assertEquals(65, successChance);
//    }
//
//    @Test
//    public void testCalculateSuccessChanceIfCarnivoreIsAloneAndHerbivoreInGroup() {
//        //GIVEN
//        double successChance;
//
//        //WHEN
//        successChance = ecoSystemService.calculateSuccessChanceForAttack(zebra, cheetah);
//
//        //THEN
//        assertEquals(28.95, successChance);
//    }
//
//    @Test
//    public void testCalculateSuccessChanceIfCarnivoreAndHerbivoreAreAlone() {
//        //GIVEN
//        double successChance;
//
//        //WHEN
//        successChance = ecoSystemService.calculateSuccessChanceForAttack(hare, cheetah);
//
//        //THEN
//        assertEquals(26.19, successChance);
//    }
//
//    @Test
//    public void testScaleLionAttackPoints() {
//        //GIVEN
//        double attackPoints = lion.getPoints();
//
//        //WHEN
//        ecoSystemService.scalePoints(lion, attackPoints);
//
//        //THEN
//        assertEquals(80, lion.getPoints());
//    }
//
//    @Test
//    public void testScaleZebraEscapePoints() {
//        //GIVEN
//        double escapePoints = zebra.getPoints();
//
//        //WHEN
//        ecoSystemService.scalePoints(zebra, escapePoints);
//
//        //THEN
//        assertEquals(80, zebra.getPoints());
//    }

}
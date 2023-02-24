package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Carnivore;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivoregrouprepository.CarnivoreGroupRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepository;
import eu.deltasource.internship.livingecosystem.repository.carnivorerepository.CarnivoreRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarnivoreServiceTest {

    CarnivoreRepository carnivoreRepository;
    CarnivoreGroupRepository carnivoreGroupRepository;
    CarnivoreService carnivoreService;
    Carnivore lion;

    @BeforeEach
    void setUp() {
        carnivoreRepository = new CarnivoreRepositoryImpl();
        carnivoreGroupRepository = new CarnivoreGroupRepositoryImpl();
        carnivoreService = new CarnivoreService(carnivoreRepository, carnivoreGroupRepository);
    }

    @Test
    public void testCreationOfCarnivore() {
        //GIVEN
        String carnivoreInfo = "lion-30-150-6-4-LAND-GROUP-80-20";
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

        //WHEN
        carnivoreService.addCarnivore(carnivore);

        //THEN
        assertEquals(1, carnivoreRepository.getCarnivoresList().size());
    }

    @Test
    public void testRemoveCarnivoreFromCarnivoreRepository() {
        //GIVEN
        lion = new Carnivore("lion", 30, 150, 5, 4,
                HabitatType.LAND, LivingStatus.GROUP, 80,0, 20);
        carnivoreService.addCarnivore(lion);

        //WHEN
        carnivoreService.removeCarnivore(lion);

        //THEN
        assertEquals(0, carnivoreService.getCarnivoresList().size());
    }

    @Test
    public void testRemoveCarnivoreIfReachedMaximumHungerRate() {
        //GIVEN
        lion = new Carnivore("lion", 30, 150, 5, 4,
                HabitatType.LAND, LivingStatus.GROUP, 80,0, 20);
        lion.setHungerRate(100);
        carnivoreService.addCarnivore(lion);

        //WHEN
        carnivoreService.removeCarnivoreIfReachedMaxHungerRate(lion, lion.getHungerRate());

        //THEN
        assertEquals(0, carnivoreService.getCarnivoresList().size());
    }

    @Test
    public void testRemoveCarnivoreIfReachedMaximumAge(){
        //GIVEN
        lion = new Carnivore("lion", 30, 150, 5, 4,
                HabitatType.LAND, LivingStatus.GROUP, 80,0, 20);
        lion.setAge(30);
        carnivoreService.addCarnivore(lion);

        //WHEN
        carnivoreService.removeCarnivoreIfReachedMaxAge(lion);

        //THEN
        assertEquals(0, carnivoreService.getCarnivoresList().size());
    }

    @Test
    public void testAddCarnivoreToGroup(){
        //GIVEN
        lion = new Carnivore("lion", 30, 150, 5, 4,
                HabitatType.LAND, LivingStatus.GROUP, 80,0, 20);
        carnivoreService.addCarnivore(lion);

        //WHEN
        carnivoreService.addCarnivoreToGroup(lion);

        //THEN
        assertEquals(1, carnivoreGroupRepository.getCarnivoreGroupList().size());
    }
}
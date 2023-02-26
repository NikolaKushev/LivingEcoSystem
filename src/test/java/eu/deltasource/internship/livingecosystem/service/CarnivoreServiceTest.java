package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.LivingHabitat;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
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
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80,20);

        //WHEN
        carnivoreService.addCarnivore(lion);

        //THEN
        assertEquals(1, carnivoreRepository.getCarnivoresList().size());
    }

    @Test
    public void testRemoveCarnivoreFromCarnivoreRepository() {
        //GIVEN
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80,20);
        carnivoreService.addCarnivore(lion);

        //WHEN
        carnivoreService.removeCarnivore(lion);

        //THEN
        assertEquals(0, carnivoreRepository.getCarnivoresList().size());
    }

    @Test
    public void testRemoveCarnivoreIfReachedMaximumHungerLevel() {
        //GIVEN
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80,20);
        lion.setHungerLevel(100);
        carnivoreService.addCarnivore(lion);

        //WHEN
        carnivoreService.removeCarnivoreIfStarving(lion);

        //THEN
        assertEquals(0, carnivoreRepository.getCarnivoresList().size());
    }

    @Test
    public void testRemoveCarnivoreIfReachedMaximumAge(){
        //GIVEN
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80,20);
        lion.setAge(30);
        carnivoreService.addCarnivore(lion);

        //WHEN
        carnivoreService.removeCarnivoreIfDiedFromOldAge(lion);

        //THEN
        assertEquals(0, carnivoreRepository.getCarnivoresList().size());
    }

    @Test
    public void testRemoveCarnivoreFromCarnivoreGroupRepository(){
        //GIVEN
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80,20);
        carnivoreService.addCarnivoresToGroup(lion);

        //WHEN
        carnivoreService.removeCarnivore(lion);

        //THEN
        assertEquals(0, carnivoreGroupRepository.getCarnivoreGroupList().size());
    }

    @Test
    public void testAddCarnivoresToGroup(){
        //GIVEN
        lion = new Carnivore("lion", 30, 150, 5, 4,
                LivingHabitat.LAND, LivingStatus.GROUP, 80,20);
        carnivoreService.addCarnivore(lion);

        //WHEN
        carnivoreService.addCarnivoresToGroup(lion);

        //THEN
        assertEquals(1, carnivoreGroupRepository.getCarnivoreGroupList().size());
    }
}
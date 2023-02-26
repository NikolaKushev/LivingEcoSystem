package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.LivingHabitat;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.Herbivore;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivoregrouprepository.HerbivoreGroupRepositoryImpl;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepository;
import eu.deltasource.internship.livingecosystem.repository.herbivorerepository.HerbivoreRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HerbivoreServiceTest {

    HerbivoreRepository herbivoreRepository;
    HerbivoreGroupRepository herbivoreGroupRepository;
    HerbivoreService herbivoreService;
    Herbivore zebra;

    @BeforeEach
    void setUp() {
        herbivoreRepository = new HerbivoreRepositoryImpl();
        herbivoreGroupRepository = new HerbivoreGroupRepositoryImpl();
        herbivoreService = new HerbivoreService(herbivoreRepository, herbivoreGroupRepository);
    }

    @Test
    public void testCreationOfHerbivore() {
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);

        //WHEN
        herbivoreRepository.addHerbivore(zebra);

        //THEN
        assertEquals(1, herbivoreRepository.getHerbivoresList().size());
    }

    @Test
    public void testRemoveHerbivoreFromHerbivoreRepository() {
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);
        herbivoreService.addHerbivore(zebra);

        //WHEN
        herbivoreService.removeHerbivore(zebra);

        //THEN
        assertEquals(0, herbivoreRepository.getHerbivoresList().size());
    }

    @Test
    public void testRemoveHerbivoreIfReachedMaximumAge(){
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);
        zebra.setAge(50);
        herbivoreService.addHerbivore(zebra);

        //WHEN
        herbivoreService.removeHerbivoreIfDiedFromOldAge(zebra);

        //THEN
        assertEquals(0, herbivoreRepository.getHerbivoresList().size());
    }

    @Test
    public void testRemoveHerbivoreFromHerbivoreGroupRepository(){
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);
        herbivoreService.addHerbivoresToGroup(zebra);

        //WHEN
        herbivoreService.removeHerbivore(zebra);

        //THEN
        assertEquals(0, herbivoreGroupRepository.getHerbivoreGroupList().size());
    }

    @Test
    public void testAddHerbivoreToGroup(){
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                LivingHabitat.LAND, LivingStatus.GROUP, 80);
        herbivoreService.addHerbivore(zebra);

        //WHEN
        herbivoreService.addHerbivoresToGroup(zebra);

        //THEN
        assertEquals(1, herbivoreGroupRepository.getHerbivoreGroupList().size());
    }
}
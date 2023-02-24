package eu.deltasource.internship.livingecosystem.service;

import eu.deltasource.internship.livingecosystem.enums.HabitatType;
import eu.deltasource.internship.livingecosystem.enums.LivingStatus;
import eu.deltasource.internship.livingecosystem.model.Animal;
import eu.deltasource.internship.livingecosystem.model.Carnivore;
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
        String herbivoreInfo = "zebra-50-300-10-5-LAND-GROUP-80";
        String[] values = herbivoreInfo.split("-");
        String herbivoreSpecie = values[0];
        int herbivoreMaxAge = Integer.parseInt(values[1]);
        double herbivoreWeight = Double.parseDouble(values[2]);
        int herbivoreReproductionRate = Integer.parseInt(values[3]);
        int herbivoreGroupMembers = Integer.parseInt(values[4]);
        HabitatType herbivoreHabitatType = HabitatType.valueOf(values[5]);
        LivingStatus herbivoreLivingStatus = LivingStatus.valueOf(values[6]);
        int herbivoreEscapePoints = Integer.parseInt(values[7]);

        Herbivore herbivore = new Herbivore(herbivoreSpecie, herbivoreMaxAge, herbivoreWeight, herbivoreReproductionRate,
                herbivoreGroupMembers, herbivoreHabitatType, herbivoreLivingStatus, herbivoreEscapePoints);
        //WHEN
        herbivoreRepository.addHerbivore(herbivore);

        //THEN
        assertEquals(1, herbivoreRepository.getHerbivoresList().size());
    }

    @Test
    public void testRemoveHerbivoreFromCarnivoreRepository() {
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                HabitatType.LAND, LivingStatus.GROUP, 80);
        herbivoreService.addHerbivore(zebra);

        //WHEN
        herbivoreService.removeHerbivore(zebra);

        //THEN
        assertEquals(0, herbivoreService.getHerbivoresList().size());
    }

    @Test
    public void testRemoveHerbivoreIfReachedMaximumAge(){
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                HabitatType.LAND, LivingStatus.GROUP, 80);
        zebra.setAge(50);
        herbivoreService.addHerbivore(zebra);

        //WHEN
        herbivoreService.removeHerbivoreIfReachedMaxAge(zebra);

        //THEN
        assertEquals(0, herbivoreService.getHerbivoresList().size());
    }

    @Test
    public void testAddHerbivoreToGroup(){
        //GIVEN
        zebra = new Herbivore("zebra", 50, 300, 10, 5,
                HabitatType.LAND, LivingStatus.GROUP, 80);
        herbivoreService.addHerbivore(zebra);

        //WHEN
        herbivoreService.addHerbivoreToGroup(zebra);

        //THEN
        assertEquals(1, herbivoreGroupRepository.getHerbivoresList().size());
    }
}
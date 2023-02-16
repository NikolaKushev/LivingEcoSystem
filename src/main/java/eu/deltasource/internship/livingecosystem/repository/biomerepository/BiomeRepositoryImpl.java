package eu.deltasource.internship.livingecosystem.repository.biomerepository;

import eu.deltasource.internship.livingecosystem.model.Biome;
import eu.deltasource.internship.livingecosystem.repository.biomerepository.BiomeRepository;

import java.util.ArrayList;
import java.util.List;

public class BiomeRepositoryImpl implements BiomeRepository {

    private List<Biome> biomes=new ArrayList<>();

    @Override
    public void addBiome(Biome biome) {
        biomes.add(biome);
    }
}

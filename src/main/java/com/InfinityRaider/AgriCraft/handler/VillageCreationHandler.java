package com.InfinityRaider.AgriCraft.handler;

import com.InfinityRaider.AgriCraft.world.StructureGreenhouseIrrigated;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.List;
import java.util.Random;

public class VillageCreationHandler implements VillagerRegistry.IVillageCreationHandler {
    //get the weight to spawn the greenhouse
    @Override
    public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i) {
        //args: structure, weight, max spawns
        return new StructureVillagePieces.PieceWeight(StructureGreenhouseIrrigated.class, 20, 1);
    }

    @Override
    public Class<?> getComponentClass() {
        return StructureGreenhouseIrrigated.class;
    }

    @Override
    public Object buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5) {
        return StructureGreenhouseIrrigated.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
    }
}

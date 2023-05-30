package maxhyper.dtneapolitan.trees;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.block.rooty.SoilHelper;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.tree.species.PalmSpecies;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class BananaSpecies extends PalmSpecies {

    public static final TypedRegistry.EntryType<Species> TYPE = createDefaultType(BananaSpecies::new);

    public BananaSpecies(ResourceLocation resourceLocation, Family family, LeavesProperties leavesProperties) {
        super(resourceLocation, family, leavesProperties);
    }


    @Override
    public boolean canSaplingGrowNaturally(Level level, BlockPos pos) {
        return SoilHelper.isSoilAcceptable(level.getBlockState(pos.below()),
                SoilHelper.getSoilFlags("sand_like", "gravel_like"))
                && super.canSaplingGrowNaturally(level, pos);
    }

    @Override
    public boolean canSaplingGrow(Level level, BlockPos pos) {
        return level.isRainingAt(pos) && super.canSaplingGrow(level, pos);
    }

}
package maxhyper.dtneapolitan.trees;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.block.rooty.SoilHelper;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.tree.species.SwampOakSpecies;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class WillowSpecies extends SwampOakSpecies {

    public static final TypedRegistry.EntryType<Species> TYPE = createDefaultType(WillowSpecies::new);

    public WillowSpecies(ResourceLocation resourceLocation, Family family, LeavesProperties leavesProperties) {
        super(resourceLocation, family, leavesProperties);
    }


    @Override
    public boolean canSaplingGrowNaturally(Level level, BlockPos pos) {
        return SoilHelper.isSoilAcceptable(level.getBlockState(pos.below()),
                SoilHelper.getSoilFlags("dirt_like"))
                && super.canSaplingGrowNaturally(level, pos);
    }
}

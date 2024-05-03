package maxhyper.dtenvironmental.blocks;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.block.leaves.WillowLeavesProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
    
public class WillowLeavesProperties extends WillowLeavesProperties {
        
    public static final TypedRegistry.EntryType<LeavesProperties> TYPE = TypedRegistry.newType(WillowLeavesProperties::new);

    public WillowLeavesProperties(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    public BlockBehaviour.Properties getDefaultBlockProperties(Material material, MaterialColor materialColor) {
        return BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_LIGHT_GREEN).strength(0.2F).sound(SoundType.WEEPING_VINES)
                .noOcclusion().isSuffocating((s, r, p) -> false).isViewBlocking((s, r, p) -> false).randomTicks()
                .isValidSpawn((s, r, p, e) -> e == EntityType.OCELOT || e == EntityType.PARROT);

    }
}

package maxhyper.dtneapolitan.blocks;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.block.leaves.PalmLeavesProperties;
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

public class BananaLeavesProperties extends PalmLeavesProperties {

    public static final TypedRegistry.EntryType<LeavesProperties> TYPE = TypedRegistry.newType(BananaLeavesProperties::new);

    public BananaLeavesProperties(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    public BlockBehaviour.Properties getDefaultBlockProperties(Material material, MaterialColor materialColor) {
        return BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_LIGHT_GREEN).strength(0.2F).sound(SoundType.WEEPING_VINES)
                .noOcclusion().isSuffocating((s, r, p) -> false).isViewBlocking((s, r, p) -> false).randomTicks()
                .isValidSpawn((s, r, p, e) -> e == EntityType.OCELOT || e == EntityType.PARROT);

    }

    @Override
    protected DynamicLeavesBlock createDynamicLeaves(BlockBehaviour.Properties properties) {
        return new DynamicPalmLeavesBlock(this, properties){
            //When destroying, we update the fruit below if one is there.
            private void updateFruit (LevelAccessor world, BlockPos pos, int offset){
                BlockState downState = world.getBlockState(pos.below(offset));
                if (downState.getBlock() instanceof BananaFruitBlock){
                    downState.onNeighborChange(world, pos.below(offset), pos.below(offset).above());
                }
            }
            @Override
            public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
                updateFruit(world, pos, 2);
                super.destroy(world, pos, state);
            }
            @Override
            public void onPlace(BlockState thisState, Level world, BlockPos pos, BlockState oldState, boolean bool) {
                updateFruit(world, pos, 2);
                updateFruit(world, pos, 3);
                super.onPlace(thisState, world, pos, oldState, bool);
            }

//            @Deprecated
//            public float getDestroyProgress(BlockState state, Player player, BlockGetter reader, BlockPos pos) {
//                float f = state.getDestroySpeed(reader, pos);
//                if (f == -1.0F) {
//                    return 0.0F;
//                } else {
//                    int i = net.minecraftforge.common.ForgeHooks.canEntityDestroy(state, player, reader, pos) ? 30 : 100;
//                    return player.getDigSpeed(state, pos) / f / (float)i;
//                }
//            }
        };
    }

}
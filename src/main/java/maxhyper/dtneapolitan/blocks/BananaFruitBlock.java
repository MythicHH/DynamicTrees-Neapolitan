package maxhyper.dtneapolitan.blocks;

import com.ferreusveritas.dynamictrees.blocks.FruitBlock;
import com.ferreusveritas.dynamictrees.systems.fruit.Fruit;
import com.minecraftabnormals.neapolitan.common.entity.PlantainSpiderEntity;
import com.minecraftabnormals.neapolitan.core.NeapolitanConfig;
import com.minecraftabnormals.neapolitan.core.registry.NeapolitanEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BananaFruitBlock extends FruitBlock {

    public BananaFruitBlock(Properties properties, Fruit fruit) {
        super(properties, fruit);
    }

    @Override
    public boolean isSupported(IBlockReader world, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.above(2)).getBlock() instanceof LeavesBlock;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
                                BlockRayTraceResult hit) {
        ActionResultType result = super.use(state, worldIn, pos, player, handIn, hit);
        if (result == ActionResultType.SUCCESS) {
            spawnSpiderEntity(worldIn, pos, player);
        }
        return result;
    }

    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        spawnSpiderEntity(world, pos, player);
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos,
                                boolean isMoving) {
        if (!this.isSupported(world, pos, state)) {
            drop(world, pos, state);
            spawnSpiderEntity(world, pos, null);
        }
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
    }

    private void spawnSpiderEntity(World world, BlockPos pos, PlayerEntity player) {
        if (!playerHasSilkTouch(player) && world.getRandom().nextFloat() <= 0.05F &&
                NeapolitanConfig.COMMON.plantainSpidersFromBundles.get()) {
            PlantainSpiderEntity spider = NeapolitanEntities.PLANTAIN_SPIDER.get().create(world);
            if (spider != null) {
                spider.moveTo((double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.1D,
                        (double) pos.getZ() + 0.5D,
                        0.0F, 0.0F);
                spider.setLastHurtByMob(player);
                world.addFreshEntity(spider);
                if (world.getRandom().nextFloat() <= 0.25F) {
                    world.addFreshEntity(spider);
                }
                if (world.getRandom().nextFloat() <= 0.45F) {
                    world.addFreshEntity(spider);
                }
            }
        }
    }

    private boolean playerHasSilkTouch(Entity entity) {
        if (entity instanceof PlayerEntity) {
            final ItemStack stack = ((PlayerEntity) entity).getMainHandItem();
            return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0;
        }
        return false;
    }

}

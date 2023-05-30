package maxhyper.dtneapolitan.blocks;

import com.ferreusveritas.dynamictrees.block.FruitBlock;
import com.ferreusveritas.dynamictrees.systems.fruit.Fruit;
import com.teamabnormals.neapolitan.common.entity.monster.PlantainSpider;
import com.teamabnormals.neapolitan.core.NeapolitanConfig;
import com.teamabnormals.neapolitan.core.registry.NeapolitanEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BananaFruitBlock extends FruitBlock {

    public BananaFruitBlock(Properties properties, Fruit fruit) {
        super(properties, fruit);
    }

    @Override
    public boolean isSupported(LevelReader world, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.above(2)).getBlock() instanceof LeavesBlock;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        InteractionResult result = super.use(state, level, pos, player, hand, hit);
        if (result == InteractionResult.SUCCESS) {
            spawnSpiderEntity(level, pos, player);
        }
        return result;
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        spawnSpiderEntity(world, pos, player);
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos,
                                boolean isMoving) {
        if (!this.isSupported(world, pos, state)) {
            drop(world, pos, state);
            spawnSpiderEntity(world, pos, null);
        }
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
    }

    private void spawnSpiderEntity(Level world, BlockPos pos, Player player) {
        if (!playerHasSilkTouch(player) && world.getRandom().nextFloat() <= 0.05F &&
                NeapolitanConfig.COMMON.plantainSpidersFromBundles.get()) {
            PlantainSpider spider = NeapolitanEntityTypes.PLANTAIN_SPIDER.get().create(world);
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
        if (entity instanceof Player) {
            final ItemStack stack = ((Player) entity).getMainHandItem();
            return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0;
        }
        return false;
    }

}
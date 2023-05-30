package maxhyper.dtneapolitan.genfeatures;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.compat.season.SeasonHelper;
import com.ferreusveritas.dynamictrees.systems.fruit.Fruit;
import com.ferreusveritas.dynamictrees.systems.genfeature.FruitGenFeature;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeatureConfiguration;
import com.ferreusveritas.dynamictrees.systems.genfeature.context.PostGenerationContext;
import com.ferreusveritas.dynamictrees.systems.genfeature.context.PostGrowContext;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;

public class BananaFruitGenFeature extends FruitGenFeature {

    public BananaFruitGenFeature(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected void registerProperties() {
        this.register(FRUIT, QUANTITY, FRUITING_RADIUS, PLACE_CHANCE);
    }

    @Override
    public GenFeatureConfiguration createDefaultConfiguration() {
        return new GenFeatureConfiguration(this)
                .with(FRUIT, Fruit.NULL)
                .with(QUANTITY, 8)
                .with(FRUITING_RADIUS, 6)
                .with(PLACE_CHANCE, 0.25f);
    }

    @Override
    protected boolean postGenerate(GenFeatureConfiguration configuration, PostGenerationContext context) {
        if (context.endPoints().isEmpty()) {
            return false;
        }
        int qty = configuration.get(QUANTITY);
        qty *= context.fruitProductionFactor();
        for (int i = 0; i < qty; i++) {
            this.placeDuringWorldGen(configuration, configuration.get(FRUIT), context.level(), context.pos(),
                    context.endPoints().get(0), context.seasonValue());
        }
        return true;
    }

    protected void placeDuringWorldGen(GenFeatureConfiguration configuration, Fruit fruit, LevelAccessor level,
                                       BlockPos rootPos, BlockPos leavesPos, Float seasonValue) {
        Direction placeDirection = CoordUtils.HORIZONTALS[level.getRandom().nextInt(4)];
        if (shouldPlaceDuringWorldGen(configuration, level, rootPos, leavesPos, placeDirection)) {
            fruit.placeDuringWorldGen(level, leavesPos.offset(placeDirection.getNormal()), seasonValue);
        }
    }

    protected boolean shouldPlaceDuringWorldGen(GenFeatureConfiguration configuration, LevelAccessor level, BlockPos rootPos,
                                                BlockPos leavesPos, Direction placeDirection) {
        return leavesPos.getY() != rootPos.getY() && level.isEmptyBlock(leavesPos.offset(placeDirection.getNormal()))
                && level.getRandom().nextFloat() <= configuration.get(PLACE_CHANCE);
    }

    @Override
    protected boolean postGrow(GenFeatureConfiguration configuration, PostGrowContext context) {
        LevelAccessor level = context.level();
        BlockPos rootPos = context.pos();
        if (TreeHelper.getRadius(level, rootPos.above()) >= configuration.get(FRUITING_RADIUS) && context.natural()) {
            final Fruit fruit = configuration.get(FRUIT);
            final float fruitingFactor = fruit.seasonalFruitProductionFactor(context.levelContext(), rootPos);
            if (fruitingFactor > level.getRandom().nextFloat()) {
                place(configuration, fruit, level, rootPos, getLeavesHeight(rootPos, level).below(),
                        SeasonHelper.getSeasonValue(context.levelContext(), rootPos));
            }
            return true;
        }
        return false;
    }

    private BlockPos getLeavesHeight(BlockPos rootPos, LevelAccessor level) {
        for (int y = 1; y < 20; y++) {
            BlockPos testPos = rootPos.above(y);
            if ((level.getBlockState(testPos).getBlock() instanceof LeavesBlock)) {
                return testPos;
            }
        }
        return rootPos;
    }

    protected void place(GenFeatureConfiguration configuration, Fruit fruit, LevelAccessor level, BlockPos rootPos,
                         BlockPos leavesPos, Float seasonValue) {
        Direction placeDirection = CoordUtils.HORIZONTALS[level.getRandom().nextInt(4)];
        if (shouldPlace(configuration, level, rootPos, leavesPos, placeDirection)) {
            fruit.place(level, leavesPos.offset(placeDirection.getNormal()), seasonValue);
        }
    }

    protected boolean shouldPlace(GenFeatureConfiguration configuration, LevelAccessor level, BlockPos rootPos,
                                  BlockPos leavesPos, Direction placeDirection) {
        return leavesPos.getY() != rootPos.getY() && level.isEmptyBlock(leavesPos.offset(placeDirection.getNormal()))
                && level.getRandom().nextFloat() <= configuration.get(PLACE_CHANCE);
    }

}
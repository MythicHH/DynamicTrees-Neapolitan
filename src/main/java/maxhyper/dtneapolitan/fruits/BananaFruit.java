package maxhyper.dtneapolitan.fruits;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.blocks.FruitBlock;
import com.ferreusveritas.dynamictrees.systems.fruit.Fruit;
import maxhyper.dtneapolitan.blocks.BananaFruitBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.util.ResourceLocation;

/**
 * @author Harley O'Connor
 */
public final class BananaFruit extends Fruit {

    public static final TypedRegistry.EntryType<Fruit> TYPE = TypedRegistry.newType(BananaFruit::new);

    public BananaFruit(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected FruitBlock createBlock(AbstractBlock.Properties properties) {
        return new BananaFruitBlock(properties, this);
    }

}

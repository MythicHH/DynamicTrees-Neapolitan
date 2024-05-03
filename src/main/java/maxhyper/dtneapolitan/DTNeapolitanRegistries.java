package maxhyper.dtneapolitan;

import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import maxhyper.dtenvironmental.blocks.BananaLeavesProperties;
import maxhyper.dtenvironmental.genfeatures.DTEnvironmentalGenFeatures;
import maxhyper.dtenvironmental.trees.BananaSpecies;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTEnvironmentalRegistries {
    public static final ResourceLocation BANANA = new ResourceLocation(DynamicTreesEnvironmental.MOD_ID, "willow");

    @SubscribeEvent
    public static void registerSpeciesType(final TypeRegistryEvent<Species> event) {
        event.registerType(WILLOW, WillowSpecies.TYPE);
    }

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(final TypeRegistryEvent<LeavesProperties> event) {
        event.registerType(WILLOW, WillowLeavesProperties.TYPE);
    }

    @SubscribeEvent
    public static void onGenFeatureRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<GenFeature> event) {
        DTEnvironmentalGenFeatures.register(event.getRegistry());
    }
}

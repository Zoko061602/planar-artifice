package leppa.planarartifice.registry;

import java.util.ArrayList;

import leppa.planarartifice.blocks.*;
import leppa.planarartifice.main.PlanarArtifice;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.oredict.OreDictionary;

public class PABlocks {

	public static final ArrayList<Block> BLOCKS = new ArrayList<>();

	public static final Block alkimium_block = new BlockAlkimium("alkimium_block").setCreativeTab(PlanarArtifice.creativetab).setHardness(3);
	public static final Block alchemical_alkimium_construct = new BlockPA(Material.IRON, "alchemical_alkimium_construct").setHardness(3);
	
	public static final Block alkimium_smeltery = new BlockAlkimiumSmeltery("alkimium_smeltery", 14, 0.85F, 375).setCreativeTab(PlanarArtifice.creativetab);
	public static final Block alkimium_smeltery_thaumium = new BlockAlkimiumSmeltery("alkimium_smeltery_thaumium", 7, 0.85F, 375).setCreativeTab(PlanarArtifice.creativetab);
	public static final Block alkimium_smeltery_void = new BlockAlkimiumSmeltery("alkimium_smeltery_void", 10, 0.95F, 375).setCreativeTab(PlanarArtifice.creativetab);

	public static final Block smelter_aux = new BlockAlkimiumSmelteryAux("smelter_aux").setCreativeTab(PlanarArtifice.creativetab);
	public static final Block smelter_vent = new BlockAlkimiumSmelteryVent("smelter_vent").setCreativeTab(PlanarArtifice.creativetab);

	public static final Block teleporter = new BlockTeleporter("teleporter");
	public static final Block teleporter_matrix = new BlockPA(Material.ROCK, "teleporter_matrix").setCreativeTab(PlanarArtifice.creativetab).setHardness(3);
	
	public static final Block potion_mixer = new BlockPotionMixer("potion_mixer");// .setCreativeTab(PlanarArtifice.creativetab);;
	public static final Block flux_scrubber = new BlockFluxScrubber("flux_scrubber").setHardness(3).setHardness(3).setCreativeTab(PlanarArtifice.creativetab);;

	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		BLOCKS.forEach(b -> event.getRegistry().register(b));
		OreDictionary.registerOre("blockAlchemical", new ItemStack(alkimium_block));
	}

	public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
		BLOCKS.forEach(b -> event.getRegistry().register(new ItemBlock(b).setRegistryName(b.getRegistryName())));
	}

	public static void registerModels() {
		BLOCKS.forEach(b -> registerRender(b));
	}

	public static void registerRender(Block block) {
		
		if(block instanceof BlockFluidBase)
			ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
		else
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));

	}
}

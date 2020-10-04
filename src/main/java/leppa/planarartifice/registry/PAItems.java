package leppa.planarartifice.registry;

import java.util.ArrayList;

import leppa.planarartifice.items.*;
import leppa.planarartifice.items.baubles.ItemAuraMeter;
import leppa.planarartifice.items.baubles.ItemBeltOfSuspension;
import leppa.planarartifice.items.baubles.ItemMirroredAmulet;
import leppa.planarartifice.main.PlanarArtifice;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.oredict.OreDictionary;

public class PAItems{
	
	public static final ArrayList<Item> ITEMS = new ArrayList<>();
	
	public static final ArmorMaterial armorMatAlkimium = EnumHelper.addArmorMaterial("alkimium", "planarartifice:goggles", 325, new int[]{3, 3, 3, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2);
	public static final ArmorMaterial armorMatHarness = EnumHelper.addArmorMaterial("thaumostaticJetHarness", "planarartifice:null_texture", 1, new int[]{6, 6, 6, 6}, 30, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
	public static final ToolMaterial toolMatBismuth = EnumHelper.addToolMaterial("bismuth", 3, 1200, 8, 3.5f, 20);

	// Crafting Items
	public static Item alkimium_ingot = new ItemPA("alkimium_ingot");
	public static Item alkimium_plate  = new ItemPA("alkimium_plate");
	public static Item alkimium_nugget = new ItemPA("alkimium_nugget");
	
	public static Item bismuth_ingot = new ItemPA("bismuth_ingot");
//	public static Item bismuth_plate = new ItemPA("bismuth_plate");
	public static Item bismuth_nugget = new  ItemPA("bismuth_nugget");
	
	public static Item flux_venting_circuit = new ItemPA("flux_venting_circuit");
	public static Item condensed_crystal_cluster = new ItemPA("condensed_crystal_cluster");
	public static Item dimensional_singularity = new ItemPA("dimensional_singularity");
	public static Item dimensional_curiosity = new ItemDimensionalCuriosity("dimensional_curiosity");

	// Baubles
	public static Item aura_meter = new ItemAuraMeter("aura_meter");
	public static Item belt_of_suspension = new ItemBeltOfSuspension("belt_of_suspension");
	public static Item mirrored_amulet  = new ItemMirroredAmulet("mirrored_amulet");
	
	//Food
	public static Item magic_apple = new ItemMagicApple("magic_apple");

	//Tools
	public static Item alkimium_goggles = new ItemAlkimiumGoggles("alkimium_goggles", armorMatAlkimium);
	public static Item alchemical_scribing_tools = new ItemAlchemicalScribingTools("alchemical_scribing_tools");
	public static Item bismuth_caster = new ItemBismuthCaster("bismuth_caster").setCreativeTab(PlanarArtifice.creativetab);
	public static Item bismuth_claymore = new ItemBismuthSword("bismuth_claymore", toolMatBismuth);
	
	
	public static void registerItems(RegistryEvent.Register<Item> event){
		ITEMS.forEach(i -> event.getRegistry().register(i));
		
		OreDictionary.registerOre("ingotAlkimium", alkimium_ingot);
		OreDictionary.registerOre("plateAlkimium", alkimium_plate);
		OreDictionary.registerOre("nuggetAlkimium", alkimium_nugget);
		OreDictionary.registerOre("ingotBismuth", bismuth_ingot);
//		OreDictionary.registerOre("plateBismuth", bismuth_plate);
		OreDictionary.registerOre("nuggetBismuth", bismuth_nugget);

	}
	
	public static void registerModels(){
		ITEMS.forEach(i -> registerRender(i));
	}
	
	public static void registerRender(Item item){
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
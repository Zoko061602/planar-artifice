package leppa.planarartifice.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApi.BluePrint;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.IDustTrigger;
import thaumcraft.api.crafting.Part;
import thaumcraft.common.lib.crafting.DustTriggerMultiblock;

public class PAMultiblocks{
	
	public static BluePrint mirromerous_teleporter;
	

	public static void registerMultiblocks() {
		//To name a Part, put where it is + what it turns into.
		Part top = new Part(PABlocks.teleporter_matrix, new ItemStack(PABlocks.teleporter,1,0));
		Part middle = new Part(BlocksTC.stoneArcaneBrick, new ItemStack(PABlocks.teleporter,1,1));
		Part bottom = new Part(BlocksTC.stoneArcaneBrick, new ItemStack(PABlocks.teleporter,1,2));
		mirromerous_teleporter = new BluePrint("MIRRORTELEPORTER", new Part[][][] {{{top}}, {{middle}}, {{bottom}}}, new ItemStack(PABlocks.teleporter_matrix), new ItemStack(BlocksTC.stoneArcaneBrick, 2));
		IDustTrigger.registerDustTrigger(new DustTriggerMultiblock("MIRRORTELEPORTER", mirromerous_teleporter.getParts()));
		ThaumcraftApi.addMultiblockRecipeToCatalog(new ResourceLocation("planarartifice:mirror_teleporter"), mirromerous_teleporter);

	}
}
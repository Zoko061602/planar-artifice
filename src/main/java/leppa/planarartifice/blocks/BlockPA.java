package leppa.planarartifice.blocks;

import leppa.planarartifice.registry.PABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPA extends Block {
	
	public BlockPA(Material material, String name) {
		super(material);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		
		PABlocks.BLOCKS.add(this);
		
	}

}

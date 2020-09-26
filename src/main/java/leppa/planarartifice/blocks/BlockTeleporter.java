package leppa.planarartifice.blocks;

import java.util.Random;

import leppa.planarartifice.registry.PABlocks;
import leppa.planarartifice.tiles.TileTeleporter;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemGenericEssentiaContainer;
import thaumcraft.common.items.resources.ItemCrystalEssence;

public class BlockTeleporter extends BlockPA implements ITileEntityProvider {

	public static final PropertyEnum<EnumTeleporter> position = PropertyEnum.create("pa_position", EnumTeleporter.class);

	public BlockTeleporter(String name) {
		super(Material.ROCK, name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(position, EnumTeleporter.TOP));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(world.getTileEntity(pos) instanceof TileTeleporter) {

			pos = pos.up(state.getValue(position).ordinal());
			TileTeleporter tile = (TileTeleporter) world.getTileEntity(pos);

				if(world.isRemote) {


				if(!player.isSneaking()) {
					
				TileTeleporter dest = null;
				int count = 0;
				
				for(TileEntity e : world.loadedTileEntityList) {
					if(e instanceof TileTeleporter) {
						dest = (TileTeleporter) e;
						if(tile.getAspect() == dest.getAspect() && tile.getPos() != dest.getPos())
							count++;
					}
				}

				if(count == 1) {					
					int diffX = dest.getPos().getX() - tile.getPos().getX();
					int diffY = dest.getPos().getY() - tile.getPos().getY();
					int diffZ = dest.getPos().getZ() - tile.getPos().getZ();
					player.setPositionAndUpdate(player.getPosition().getX() + diffX + 0.5, player.getPosition().getY() + diffY + 0.5, player.getPosition().getZ() + diffZ + 0.5);
					return true;
				}

				if(count == 0) {
					player.sendMessage(new TextComponentString("There are no Waystones with this Aspect."));
					return true;
				}

				if(count > 1) {
					player.sendMessage(new TextComponentString("There are too many Waystones with this Aspect."));
					return true;
				}
				}
			}
				
				IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
				if(player.isSneaking()){
					if(player.getHeldItem(hand).isEmpty()){
						player.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
					}else if(player.getHeldItem(hand).getItem() instanceof ItemCrystalEssence){
						player.setHeldItem(hand, itemHandler.insertItem(0, player.getHeldItem(hand), false));
					}
					tile.markDirty();
				}else{
					ItemStack stack = itemHandler.getStackInSlot(0);
					if (stack != null){
						if(stack.getItem() instanceof ItemCrystalEssence) player.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE.toString() + ((ItemGenericEssentiaContainer)stack.getItem()).getAspects(stack).getAspects()[0].getName()));
					}else{
						player.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "Empty"));
					}
				}
			
		}

		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {		
		pos = pos.up(state.getValue(position).ordinal());	
		
		worldIn.setBlockState(pos, PABlocks.teleporter_matrix.getDefaultState());
		worldIn.setBlockState(pos.down(), BlocksTC.stoneArcaneBrick.getDefaultState());
		worldIn.setBlockState(pos.down(2), BlocksTC.stoneArcaneBrick.getDefaultState());
		worldIn.removeTileEntity(pos);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.AIR);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, position);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(position).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		if(meta > EnumTeleporter.values().length)
			meta = 0;
		return this.blockState.getBaseState().withProperty(position, EnumTeleporter.values()[meta]);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return meta == 0 ? new TileTeleporter() : null;
	}

	public static enum EnumTeleporter implements IStringSerializable {

		TOP, MIDDLE, BOTTOM;

		@Override
		public String getName() {
			return toString().toLowerCase();
		}

	}

}

package leppa.planarartifice.main;

import leppa.planarartifice.compat.PACompatHandler;
import leppa.planarartifice.network.MessageProjectingAttack;
import leppa.planarartifice.network.PacketRequestUpdateTeleporter;
import leppa.planarartifice.network.PacketUpdateTeleporter;
import leppa.planarartifice.tiles.TileAlkimiumSmeltery;
import leppa.planarartifice.tiles.TileFluxScrubber;
import leppa.planarartifice.tiles.TilePotionMixer;
import leppa.planarartifice.tiles.TileTeleporter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
	
    public static SimpleNetworkWrapper network;
	
	public void preInit(FMLPreInitializationEvent e){	
    	PACompatHandler.setup();
		PACompatHandler.preInit(e);
		
        GameRegistry.registerTileEntity(TileTeleporter.class, new ResourceLocation(PlanarArtifice.MODID, "mirrorTeleporter"));
        GameRegistry.registerTileEntity(TileFluxScrubber.class, new ResourceLocation(PlanarArtifice.MODID,"fluxScrubber"));
        GameRegistry.registerTileEntity(TilePotionMixer.class, new ResourceLocation(PlanarArtifice.MODID,"potionMixer"));
        GameRegistry.registerTileEntity(TileAlkimiumSmeltery.class, new ResourceLocation(PlanarArtifice.MODID,"alkimiumSmeltery"));
        
        network = NetworkRegistry.INSTANCE.newSimpleChannel(PlanarArtifice.MODID);
        network.registerMessage(new PacketUpdateTeleporter.Handler(), PacketUpdateTeleporter.class, 0, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateTeleporter.Handler(), PacketRequestUpdateTeleporter.class, 1, Side.SERVER);
        network.registerMessage(new MessageProjectingAttack.Handler(), MessageProjectingAttack.class, 2, Side.SERVER);

        
		NetworkRegistry.INSTANCE.registerGuiHandler(PlanarArtifice.instance, new PAGuiHandler());
		
	}
	
	public void init(FMLInitializationEvent e){
		PACompatHandler.init(e);
	}
	
	public void postInit(FMLPostInitializationEvent e){
		PACompatHandler.postInit(e);		
	}

	public static EntityPlayer getPlayerEntityFromContext(MessageContext ctx) {
		return ctx.side.isClient() ? Minecraft.getMinecraft().player : ctx.getServerHandler().player;
		
	}

}

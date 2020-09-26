package leppa.planarartifice.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import leppa.planarartifice.enchantment.EnumInfusionEnchantmentII;
import leppa.planarartifice.util.ReflectionUtils;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.FocusEngine;
import thaumcraft.api.casters.IFocusElement;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.common.items.casters.ItemCaster;

@EventBusSubscriber
@Mod(modid = PlanarArtifice.MODID, version = PlanarArtifice.VERSION, name = PlanarArtifice.NAME, dependencies = "required-after:thaumcraft;after:tconstruct")
public class PlanarArtifice implements LoadingCallback {
	public static final String MODID = "planarartifice";
	public static final String NAME = "Planar Artifice";
	public static final String VERSION = "1.0";

	public static final PlanarTab creativetab = new PlanarTab();

	public static boolean isSingleplayer;

	public static ArrayList<FocusEffect> focusEffects = new ArrayList<>();
	public static int currentFocusEffect = 0;
	public static int currentColourPicked = 0xffffff;

	static HashMap<String, Integer> p;

	public static final EnumGolemTrait golemTraitTeleporter = EnumHelper.addEnum(EnumGolemTrait.class, "TELEPORTER",
			new Class[] {ResourceLocation.class},
			new ResourceLocation(MODID, "textures/misc/golem/tag_teleporter.png"));
	public static final EnumRarity rarityPA = EnumHelper.addRarity(MODID, TextFormatting.GREEN, NAME);

	@SidedProxy(serverSide = "leppa.planarartifice.main.CommonProxy", clientSide = "leppa.planarartifice.client.ClientProxy")
	public static CommonProxy proxy;

	@Instance(MODID)
	public static PlanarArtifice instance = new PlanarArtifice();

	static {
		p = (HashMap<String, Integer>) ReflectionUtils.getPrivateObject("elementColor", new FocusEngine());
	}


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		ForgeChunkManager.setForcedChunkLoadingCallback(this, this);


		for(int i = 0; i < FocusEngine.elements.values().size(); i++) {
			try {
				if(((Class<IFocusElement>) FocusEngine.elements.values().toArray()[i])
						.newInstance() instanceof FocusEffect)
					PlanarArtifice.focusEffects
							.add(((FocusEffect) ((Class<IFocusElement>) FocusEngine.elements.values().toArray()[i])
									.newInstance()));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent
	public static void onTick(ServerTickEvent tick) {
		isSingleplayer = !FMLCommonHandler.instance().getMinecraftServerInstance().isDedicatedServer();

		p.put("planarartifice.FOCUSCOLOURED", currentColourPicked);
	}

	@SubscribeEvent
	public static void onItemRightClick(PlayerInteractEvent event) {
		Item i = event.getEntityPlayer().getHeldItem(event.getHand()).getItem();
		if(i instanceof ItemCaster)
			if(event.getEntityPlayer().getHeldItem(event.getHand()).hasTagCompound()) {
				NBTTagCompound l = event.getEntityPlayer().getHeldItem(event.getHand()).getTagCompound()
						.getCompoundTag("focus").getCompoundTag("tag");
				if(l.hasKey("color"))
					currentColourPicked = l.getInteger("color");
			}
	}

	@SideOnly(value = Side.CLIENT)
	@SubscribeEvent
	public static void tooltipEvent(ItemTooltipEvent event) {
		event.getItemStack();
		NBTTagList nbttaglist = EnumInfusionEnchantmentII.getInfusionEnchantmentTagList(event.getItemStack());
		if(nbttaglist != null) {
			for(int j = 0; j < nbttaglist.tagCount(); ++j) {
				short k = nbttaglist.getCompoundTagAt(j).getShort("id");
				short l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
				if(k < 0 || k >= EnumInfusionEnchantmentII.values().length)
					continue;
				String s = TextFormatting.GOLD + I18n
						.translateToLocal("enchantment.infusion." + EnumInfusionEnchantmentII.values()[k].toString());
				if(EnumInfusionEnchantmentII.values()[k].maxLevel > 1) {
					s = s + " " + I18n.translateToLocal("enchantment.level." + l);
				}
				event.getToolTip().add(1, s);
			}
		}
	}

	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world) {
		
	}
}

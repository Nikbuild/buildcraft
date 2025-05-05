/*     */ package buildcraft.core.proxy;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.ICoreProxy;
/*     */ import buildcraft.core.LaserKind;
/*     */ import buildcraft.core.lib.EntityBlock;
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import cpw.mods.fml.common.SidedProxy;
/*     */ import java.lang.ref.WeakReference;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.util.FakePlayer;
/*     */ import net.minecraftforge.common.util.FakePlayerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoreProxy
/*     */   implements ICoreProxy
/*     */ {
/*     */   @SidedProxy(clientSide = "buildcraft.core.proxy.CoreProxyClient", serverSide = "buildcraft.core.proxy.CoreProxy")
/*     */   public static CoreProxy proxy;
/*  37 */   protected static WeakReference<EntityPlayer> buildCraftPlayer = new WeakReference<EntityPlayer>(null);
/*     */   
/*     */   public String getMinecraftVersion() {
/*  40 */     return Loader.instance().getMinecraftModContainer().getVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getClient() {
/*  45 */     return null;
/*     */   }
/*     */   
/*     */   public World getClientWorld() {
/*  49 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeEntity(Entity entity) {
/*  54 */     entity.field_70170_p.func_72900_e(entity);
/*     */   }
/*     */   
/*     */   public String getItemDisplayName(ItemStack newStack) {
/*  58 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeRendering() {}
/*     */ 
/*     */   
/*     */   public void initializeEntityRendering() {}
/*     */ 
/*     */   
/*     */   public void onCraftingPickup(World world, EntityPlayer player, ItemStack stack) {
/*  69 */     stack.func_77980_a(world, player, stack.field_77994_a);
/*     */   }
/*     */   
/*     */   public String playerName() {
/*  73 */     return "";
/*     */   }
/*     */   
/*     */   private WeakReference<EntityPlayer> createNewPlayer(WorldServer world) {
/*  77 */     FakePlayer fakePlayer = FakePlayerFactory.get(world, BuildCraftCore.gameProfile);
/*     */     
/*  79 */     return new WeakReference(fakePlayer);
/*     */   }
/*     */   
/*     */   private WeakReference<EntityPlayer> createNewPlayer(WorldServer world, int x, int y, int z) {
/*  83 */     FakePlayer fakePlayer = FakePlayerFactory.get(world, BuildCraftCore.gameProfile);
/*  84 */     ((EntityPlayer)fakePlayer).field_70165_t = x;
/*  85 */     ((EntityPlayer)fakePlayer).field_70163_u = y;
/*  86 */     ((EntityPlayer)fakePlayer).field_70161_v = z;
/*  87 */     return new WeakReference(fakePlayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public final WeakReference<EntityPlayer> getBuildCraftPlayer(WorldServer world) {
/*  92 */     if (buildCraftPlayer.get() == null) {
/*  93 */       buildCraftPlayer = createNewPlayer(world);
/*     */     } else {
/*  95 */       ((EntityPlayer)buildCraftPlayer.get()).field_70170_p = (World)world;
/*     */     } 
/*     */     
/*  98 */     return buildCraftPlayer;
/*     */   }
/*     */   
/*     */   public final WeakReference<EntityPlayer> getBuildCraftPlayer(WorldServer world, int x, int y, int z) {
/* 102 */     if (buildCraftPlayer.get() == null) {
/* 103 */       buildCraftPlayer = createNewPlayer(world, x, y, z);
/*     */     } else {
/* 105 */       ((EntityPlayer)buildCraftPlayer.get()).field_70170_p = (World)world;
/* 106 */       ((EntityPlayer)buildCraftPlayer.get()).field_70165_t = x;
/* 107 */       ((EntityPlayer)buildCraftPlayer.get()).field_70163_u = y;
/* 108 */       ((EntityPlayer)buildCraftPlayer.get()).field_70161_v = z;
/*     */     } 
/*     */     
/* 111 */     return buildCraftPlayer;
/*     */   }
/*     */   
/*     */   public EntityBlock newEntityBlock(World world, double i, double j, double k, double iSize, double jSize, double kSize, LaserKind laserKind) {
/* 115 */     return new EntityBlock(world, i, j, k, iSize, jSize, kSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayer getPlayerFromNetHandler(INetHandler handler) {
/* 123 */     if (handler instanceof NetHandlerPlayServer) {
/* 124 */       return (EntityPlayer)((NetHandlerPlayServer)handler).field_147369_b;
/*     */     }
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity getServerTile(TileEntity source) {
/* 131 */     return source;
/*     */   }
/*     */   
/*     */   public EntityPlayer getClientPlayer() {
/* 135 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\proxy\CoreProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.api.core;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.Loader;
/*    */ import net.minecraftforge.fml.common.ModContainer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BuildCraftAPI
/*    */ {
/*    */   public static IFakePlayerProvider fakePlayerProvider;
/* 24 */   public static final Set<Block> softBlocks = Sets.newHashSet();
/* 25 */   public static final HashMap<String, IWorldProperty> worldProperties = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getVersion() {
/* 31 */     ModContainer container = (ModContainer)Loader.instance().getIndexedModList().get("buildcraftlib");
/* 32 */     if (container != null) {
/* 33 */       return container.getDisplayVersion();
/*    */     }
/* 35 */     return "UNKNOWN VERSION";
/*    */   }
/*    */   
/*    */   public static IWorldProperty getWorldProperty(String name) {
/* 39 */     return worldProperties.get(name);
/*    */   }
/*    */   
/*    */   public static void registerWorldProperty(String name, IWorldProperty property) {
/* 43 */     if (worldProperties.containsKey(name)) {
/* 44 */       BCLog.logger.warn("The WorldProperty key '" + name + "' is being overridden with " + property.getClass().getSimpleName() + "!");
/*    */     }
/* 46 */     worldProperties.put(name, property);
/*    */   }
/*    */   
/*    */   public static boolean isSoftBlock(World world, BlockPos pos) {
/* 50 */     return ((IWorldProperty)worldProperties.get("soft")).get(world, pos);
/*    */   }
/*    */   
/*    */   public static ResourceLocation nameToResourceLocation(String name) {
/* 54 */     if (name.indexOf(':') > 0) return new ResourceLocation(name); 
/* 55 */     ModContainer modContainer = Loader.instance().activeModContainer();
/* 56 */     if (modContainer == null) {
/* 57 */       throw new IllegalStateException("Illegal recipe name " + name + ". Provide domain id to register it correctly.");
/*    */     }
/* 59 */     return new ResourceLocation(modContainer.getModId(), name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\BuildCraftAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
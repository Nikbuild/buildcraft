/*    */ package buildcraft.energy;
/*    */ 
/*    */ import cpw.mods.fml.common.SidedProxy;
/*    */ import cpw.mods.fml.common.registry.GameRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnergyProxy
/*    */ {
/*    */   @SidedProxy(clientSide = "buildcraft.energy.EnergyProxyClient", serverSide = "buildcraft.energy.EnergyProxy")
/*    */   public static EnergyProxy proxy;
/*    */   
/*    */   public void registerTileEntities() {
/* 19 */     GameRegistry.registerTileEntity(TileEngineStone.class, "net.minecraft.src.buildcraft.energy.TileEngineStone");
/* 20 */     GameRegistry.registerTileEntity(TileEngineIron.class, "net.minecraft.src.buildcraft.energy.TileEngineIron");
/* 21 */     GameRegistry.registerTileEntity(TileEngineCreative.class, "net.minecraft.src.buildcraft.energy.TileEngineCreative");
/*    */   }
/*    */   
/*    */   public void registerBlockRenderers() {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\EnergyProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.factory;
/*    */ 
/*    */ import buildcraft.core.lib.EntityBlock;
/*    */ import cpw.mods.fml.common.SidedProxy;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FactoryProxy
/*    */ {
/*    */   @SidedProxy(clientSide = "buildcraft.factory.FactoryProxyClient", serverSide = "buildcraft.factory.FactoryProxy")
/*    */   public static FactoryProxy proxy;
/*    */   
/*    */   public void initializeTileEntities() {}
/*    */   
/*    */   public void initializeEntityRenders() {}
/*    */   
/*    */   public EntityBlock newPumpTube(World w) {
/* 28 */     return new EntityBlock(w);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\FactoryProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
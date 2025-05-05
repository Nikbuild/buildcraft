/*    */ package buildcraft.builders;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderProxy
/*    */ {
/*    */   @SidedProxy(clientSide = "buildcraft.builders.BuilderProxyClient", serverSide = "buildcraft.builders.BuilderProxy")
/*    */   public static BuilderProxy proxy;
/*    */   public static int frameRenderId;
/*    */   
/*    */   public void registerClientHook() {}
/*    */   
/*    */   public void registerBlockRenderers() {}
/*    */   
/*    */   public EntityBlock newDrill(World w, double i, double j, double k, double l, double d, double e, boolean xz) {
/* 32 */     return new EntityBlock(w, i, j, k, l, d, e);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityBlock newDrillHead(World w, double i, double j, double k, double l, double d, double e) {
/* 37 */     return new EntityBlock(w, i, j, k, l, d, e);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\BuilderProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
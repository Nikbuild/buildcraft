/*    */ package buildcraft.silicon;
/*    */ 
/*    */ import cpw.mods.fml.common.SidedProxy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SiliconProxy
/*    */ {
/*    */   @SidedProxy(clientSide = "buildcraft.silicon.SiliconProxyClient", serverSide = "buildcraft.silicon.SiliconProxy")
/*    */   public static SiliconProxy proxy;
/* 16 */   public static int laserBlockModel = -1;
/* 17 */   public static int laserTableModel = -1;
/*    */   
/*    */   public void registerRenderers() {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\SiliconProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
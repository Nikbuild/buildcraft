/*    */ package buildcraft.api.transport;
/*    */ 
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IPipeConnection
/*    */ {
/*    */   ConnectOverride overridePipeConnection(IPipeTile.PipeType paramPipeType, ForgeDirection paramForgeDirection);
/*    */   
/*    */   public enum ConnectOverride
/*    */   {
/* 17 */     CONNECT, DISCONNECT, DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\transport\IPipeConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
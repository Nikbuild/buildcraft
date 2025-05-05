/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public interface IPipeConnection
/*    */ {
/*    */   ConnectOverride overridePipeConnection(Object paramObject, EnumFacing paramEnumFacing);
/*    */   
/*    */   public enum ConnectOverride
/*    */   {
/* 15 */     CONNECT,
/* 16 */     DISCONNECT,
/* 17 */     DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\IPipeConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
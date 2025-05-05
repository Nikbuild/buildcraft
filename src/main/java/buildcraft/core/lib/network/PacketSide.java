/*    */ package buildcraft.core.lib.network;
/*    */ 
/*    */ public enum PacketSide {
/*  4 */   CLIENT_ONLY(true, false),
/*  5 */   SERVER_ONLY(false, true),
/*  6 */   BOTH_SIDES(true, true);
/*    */   
/*    */   private final boolean validOnClient;
/*    */   private final boolean validOnServer;
/*    */   
/*    */   PacketSide(boolean validOnClient, boolean validOnServer) {
/* 12 */     this.validOnClient = validOnClient;
/* 13 */     this.validOnServer = validOnServer;
/*    */   }
/*    */   
/*    */   public boolean isValidOnClient() {
/* 17 */     return this.validOnClient;
/*    */   }
/*    */   
/*    */   public boolean isValidOnServer() {
/* 21 */     return this.validOnServer;
/*    */   }
/*    */   
/*    */   public boolean contains(PacketSide other) {
/* 25 */     return (this == BOTH_SIDES || this == other);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\PacketSide.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.transport.network;
/*    */ 
/*    */ import buildcraft.core.lib.network.Packet;
/*    */ import buildcraft.core.lib.utils.NetworkUtils;
/*    */ import buildcraft.transport.TravelingItem;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ public class PacketPipeTransportItemStack
/*    */   extends Packet
/*    */ {
/*    */   private ItemStack stack;
/*    */   private int entityId;
/*    */   
/*    */   public PacketPipeTransportItemStack() {}
/*    */   
/*    */   public PacketPipeTransportItemStack(int entityId, ItemStack stack) {
/* 29 */     this.entityId = entityId;
/* 30 */     this.stack = stack;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 35 */     data.writeInt(this.entityId);
/* 36 */     NetworkUtils.writeStack(data, this.stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 41 */     this.entityId = data.readInt();
/* 42 */     this.stack = NetworkUtils.readStack(data);
/* 43 */     TravelingItem item = TravelingItem.clientCache.get(this.entityId);
/* 44 */     if (item != null) {
/* 45 */       item.setItemStack(this.stack);
/*    */     }
/*    */   }
/*    */   
/*    */   public int getEntityId() {
/* 50 */     return this.entityId;
/*    */   }
/*    */   
/*    */   public ItemStack getItemStack() {
/* 54 */     return this.stack;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getID() {
/* 59 */     return 6;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\network\PacketPipeTransportItemStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.core.lib.network;
/*    */ 
/*    */ import buildcraft.api.core.ISerializable;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.Entity;
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
/*    */ public class PacketEntityUpdate
/*    */   extends PacketUpdate
/*    */ {
/*    */   public int entityId;
/*    */   
/*    */   public PacketEntityUpdate() {
/* 23 */     super(7);
/*    */   }
/*    */   
/*    */   public PacketEntityUpdate(ISerializable payload) {
/* 27 */     this(7, payload);
/*    */   }
/*    */   
/*    */   public PacketEntityUpdate(int packetId, ISerializable payload) {
/* 31 */     super(packetId, payload);
/*    */     
/* 33 */     Entity entity = (Entity)payload;
/* 34 */     this.entityId = entity.func_145782_y();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeIdentificationData(ByteBuf data) {
/* 39 */     data.writeInt(this.entityId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readIdentificationData(ByteBuf data) {
/* 44 */     this.entityId = data.readInt();
/*    */   }
/*    */   
/*    */   public boolean targetExists(World world) {
/* 48 */     return (world.func_73045_a(this.entityId) != null);
/*    */   }
/*    */   
/*    */   public Entity getTarget(World world) {
/* 52 */     return world.func_73045_a(this.entityId);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\PacketEntityUpdate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.core.lib.network;
/*    */ 
/*    */ import buildcraft.api.core.ISerializable;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ public class PacketTileUpdate
/*    */   extends PacketUpdate
/*    */ {
/*    */   public int posX;
/*    */   public int posY;
/*    */   public int posZ;
/*    */   
/*    */   public PacketTileUpdate() {
/* 25 */     super(0);
/*    */   }
/*    */   
/*    */   public PacketTileUpdate(ISerializable tile) {
/* 29 */     this(0, tile);
/*    */   }
/*    */   
/*    */   public PacketTileUpdate(int packetId, ISerializable tile) {
/* 33 */     super(packetId, tile);
/*    */     
/* 35 */     TileEntity entity = (TileEntity)tile;
/* 36 */     this.posX = entity.field_145851_c;
/* 37 */     this.posY = entity.field_145848_d;
/* 38 */     this.posZ = entity.field_145849_e;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeIdentificationData(ByteBuf data) {
/* 43 */     data.writeInt(this.posX);
/* 44 */     data.writeShort(this.posY);
/* 45 */     data.writeInt(this.posZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readIdentificationData(ByteBuf data) {
/* 50 */     this.posX = data.readInt();
/* 51 */     this.posY = data.readShort();
/* 52 */     this.posZ = data.readInt();
/*    */   }
/*    */   
/*    */   public boolean targetExists(World world) {
/* 56 */     return world.func_72899_e(this.posX, this.posY, this.posZ);
/*    */   }
/*    */   
/*    */   public TileEntity getTarget(World world) {
/* 60 */     return world.func_147438_o(this.posX, this.posY, this.posZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\PacketTileUpdate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
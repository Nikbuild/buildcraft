/*    */ package buildcraft.core.lib.network.command;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ public class CommandTargetTile
/*    */   extends CommandTarget
/*    */ {
/*    */   public Class<?> getHandledClass() {
/* 20 */     return TileEntity.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf data, Object target) {
/* 25 */     TileEntity tile = (TileEntity)target;
/* 26 */     data.writeInt(tile.field_145851_c);
/* 27 */     data.writeShort(tile.field_145848_d);
/* 28 */     data.writeInt(tile.field_145849_e);
/*    */   }
/*    */ 
/*    */   
/*    */   public ICommandReceiver handle(EntityPlayer player, ByteBuf data, World world) {
/* 33 */     int posX = data.readInt();
/* 34 */     int posY = data.readShort();
/* 35 */     int posZ = data.readInt();
/* 36 */     if (world.func_72899_e(posX, posY, posZ)) {
/* 37 */       TileEntity tile = world.func_147438_o(posX, posY, posZ);
/* 38 */       if (tile instanceof ICommandReceiver) {
/* 39 */         return (ICommandReceiver)tile;
/*    */       }
/*    */     } 
/* 42 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\command\CommandTargetTile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
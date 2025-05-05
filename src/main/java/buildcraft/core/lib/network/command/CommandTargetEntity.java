/*    */ package buildcraft.core.lib.network.command;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ public class CommandTargetEntity
/*    */   extends CommandTarget
/*    */ {
/*    */   public Class<?> getHandledClass() {
/* 20 */     return Entity.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf data, Object target) {
/* 25 */     Entity entity = (Entity)target;
/* 26 */     data.writeInt(entity.func_145782_y());
/*    */   }
/*    */ 
/*    */   
/*    */   public ICommandReceiver handle(EntityPlayer player, ByteBuf data, World world) {
/* 31 */     int id = data.readInt();
/* 32 */     Entity entity = world.func_73045_a(id);
/* 33 */     if (entity != null && entity instanceof ICommandReceiver) {
/* 34 */       return (ICommandReceiver)entity;
/*    */     }
/* 36 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\command\CommandTargetEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
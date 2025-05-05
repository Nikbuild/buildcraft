/*    */ package buildcraft.core.lib.network.command;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
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
/*    */ public class CommandTargetContainer
/*    */   extends CommandTarget
/*    */ {
/*    */   public Class<?> getHandledClass() {
/* 20 */     return Container.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public ICommandReceiver handle(EntityPlayer player, ByteBuf data, World world) {
/* 25 */     Container container = player.field_71070_bA;
/* 26 */     if (container != null && container instanceof ICommandReceiver) {
/* 27 */       return (ICommandReceiver)container;
/*    */     }
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   public void write(ByteBuf data, Object target) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\command\CommandTargetContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
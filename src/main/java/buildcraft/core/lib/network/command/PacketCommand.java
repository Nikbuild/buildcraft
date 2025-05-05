/*    */ package buildcraft.core.lib.network.command;
/*    */ 
/*    */ import buildcraft.core.lib.network.Packet;
/*    */ import buildcraft.core.lib.utils.NetworkUtils;
/*    */ import cpw.mods.fml.common.FMLCommonHandler;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketCommand
/*    */   extends Packet
/*    */ {
/*    */   public ByteBuf stream;
/*    */   public String command;
/* 32 */   public static final ArrayList<CommandTarget> targets = new ArrayList<CommandTarget>(); static {
/* 33 */     targets.add(new CommandTargetTile());
/* 34 */     targets.add(new CommandTargetEntity());
/* 35 */     targets.add(new CommandTargetContainer());
/*    */   }
/*    */   public Object target;
/*    */   public CommandTarget handler;
/*    */   private CommandWriter writer;
/*    */   
/*    */   public PacketCommand() {}
/*    */   
/*    */   public PacketCommand(Object target, String command, CommandWriter writer) {
/* 44 */     this.target = target;
/* 45 */     this.command = command;
/* 46 */     this.writer = writer;
/*    */     
/* 48 */     this.isChunkDataPacket = true;
/*    */ 
/*    */     
/* 51 */     for (CommandTarget c : targets) {
/* 52 */       if (c.getHandledClass().isAssignableFrom(target.getClass())) {
/* 53 */         this.handler = c;
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void handle(EntityPlayer player) {
/* 60 */     if (this.handler != null) {
/* 61 */       ICommandReceiver receiver = this.handler.handle(player, this.stream, player.field_70170_p);
/* 62 */       if (receiver != null) {
/* 63 */         receiver.receiveCommand(this.command, FMLCommonHandler.instance().getEffectiveSide(), player, this.stream);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeData(ByteBuf data) {
/* 70 */     NetworkUtils.writeUTF(data, this.command);
/* 71 */     data.writeByte(targets.indexOf(this.handler));
/* 72 */     this.handler.write(data, this.target);
/* 73 */     if (this.writer != null) {
/* 74 */       this.writer.write(data);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData(ByteBuf data) {
/* 80 */     this.command = NetworkUtils.readUTF(data);
/* 81 */     this.handler = targets.get(data.readUnsignedByte());
/* 82 */     this.stream = data;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getID() {
/* 87 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\network\command\PacketCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
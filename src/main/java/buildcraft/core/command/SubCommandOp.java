/*    */ package buildcraft.core.command;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.core.lib.commands.CommandHelpers;
/*    */ import buildcraft.core.lib.commands.SubCommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ public class SubCommandOp
/*    */   extends SubCommand {
/*    */   public SubCommandOp() {
/* 12 */     super("op");
/* 13 */     setPermLevel(SubCommand.PermLevel.SERVER_ADMIN);
/*    */   }
/*    */ 
/*    */   
/*    */   public void processSubCommand(ICommandSender sender, String[] args) {
/* 18 */     MinecraftServer.func_71276_C().func_71203_ab().func_152605_a(BuildCraftCore.gameProfile);
/* 19 */     CommandHelpers.sendLocalizedChatMessage(sender, "commands.op.success", new Object[] { "[BuildCraft]" });
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\command\SubCommandOp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
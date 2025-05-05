/*    */ package buildcraft.core.command;
/*    */ 
/*    */ import buildcraft.core.Version;
/*    */ import buildcraft.core.lib.commands.SubCommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ 
/*    */ public class SubCommandChangelog
/*    */   extends SubCommand {
/*    */   public SubCommandChangelog() {
/* 10 */     super("changelog");
/*    */   }
/*    */ 
/*    */   
/*    */   public void processSubCommand(ICommandSender sender, String[] args) {
/* 15 */     Version.displayChangelog(sender);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\command\SubCommandChangelog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
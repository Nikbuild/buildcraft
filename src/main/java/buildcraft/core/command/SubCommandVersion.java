/*    */ package buildcraft.core.command;
/*    */ 
/*    */ import buildcraft.core.Version;
/*    */ import buildcraft.core.lib.commands.SubCommand;
/*    */ import buildcraft.core.proxy.CoreProxy;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.ChatStyle;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class SubCommandVersion extends SubCommand {
/*    */   public SubCommandVersion() {
/* 14 */     super("version");
/*    */   }
/*    */ 
/*    */   
/*    */   public void processSubCommand(ICommandSender sender, String[] args) {
/* 19 */     sender.func_145747_a((new ChatComponentTranslation("command.buildcraft.version", new Object[] { Version.getVersion(), CoreProxy.proxy
/* 20 */             .getMinecraftVersion(), Version.getRecommendedVersion()
/* 21 */           })).func_150255_a((new ChatStyle()).func_150238_a(Version.isOutdated() ? EnumChatFormatting.RED : EnumChatFormatting.GREEN)));
/*    */     
/* 23 */     if (Version.needsUpdateNoticeAndMarkAsSeen()) {
/* 24 */       sender.func_145747_a((IChatComponent)new ChatComponentTranslation("bc_update.new_version", new Object[] {
/* 25 */               Version.getRecommendedVersion(), CoreProxy.proxy
/* 26 */               .getMinecraftVersion() }));
/* 27 */       sender.func_145747_a((IChatComponent)new ChatComponentTranslation("bc_update.download", new Object[0]));
/* 28 */       sender.func_145747_a((IChatComponent)new ChatComponentTranslation("bc_update.changelog", new Object[0]));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\command\SubCommandVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
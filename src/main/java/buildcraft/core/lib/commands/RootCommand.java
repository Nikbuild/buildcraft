/*    */ package buildcraft.core.lib.commands;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.SortedSet;
/*    */ import java.util.TreeSet;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RootCommand
/*    */   extends CommandBase
/*    */   implements IModCommand
/*    */ {
/*    */   public final String name;
/* 22 */   private final List<String> aliases = new ArrayList<String>();
/* 23 */   private final SortedSet<SubCommand> children = new TreeSet<SubCommand>(new Comparator<SubCommand>()
/*    */       {
/*    */         public int compare(SubCommand o1, SubCommand o2) {
/* 26 */           return o1.compareTo(o2);
/*    */         }
/*    */       });
/*    */   
/*    */   public RootCommand(String name) {
/* 31 */     this.name = name;
/*    */   }
/*    */   
/*    */   public void addChildCommand(SubCommand child) {
/* 35 */     child.setParent(this);
/* 36 */     this.children.add(child);
/*    */   }
/*    */   
/*    */   public void addAlias(String alias) {
/* 40 */     this.aliases.add(alias);
/*    */   }
/*    */ 
/*    */   
/*    */   public SortedSet<SubCommand> getChildren() {
/* 45 */     return this.children;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_71517_b() {
/* 50 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_82362_a() {
/* 55 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinimumPermissionLevel() {
/* 60 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> func_71514_a() {
/* 65 */     return this.aliases;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_71518_a(ICommandSender sender) {
/* 70 */     return "/" + func_71517_b() + " help";
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_71515_b(ICommandSender sender, String[] args) {
/* 75 */     if (!CommandHelpers.processStandardCommands(sender, this, args)) {
/* 76 */       CommandHelpers.throwWrongUsage(sender, this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFullCommandString() {
/* 82 */     return func_71517_b();
/*    */   }
/*    */ 
/*    */   
/*    */   public void printHelp(ICommandSender sender) {
/* 87 */     CommandHelpers.printHelp(sender, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\commands\RootCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
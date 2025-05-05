/*     */ package buildcraft.core.lib.commands;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SubCommand
/*     */   implements IModCommand
/*     */ {
/*     */   private final String name;
/*     */   
/*     */   public enum PermLevel
/*     */   {
/*  22 */     EVERYONE(0), ADMIN(2), SERVER_ADMIN(3);
/*     */     final int permLevel;
/*     */     
/*     */     PermLevel(int permLevel) {
/*  26 */       this.permLevel = permLevel;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  31 */   private final List<String> aliases = new ArrayList<String>();
/*  32 */   private PermLevel permLevel = PermLevel.EVERYONE; private IModCommand parent;
/*     */   
/*  34 */   private final SortedSet<SubCommand> children = new TreeSet<SubCommand>(new Comparator<SubCommand>()
/*     */       {
/*     */         public int compare(SubCommand o1, SubCommand o2)
/*     */         {
/*  38 */           return o1.compareTo(o2);
/*     */         }
/*     */       });
/*     */   
/*     */   public SubCommand(String name) {
/*  43 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String func_71517_b() {
/*  48 */     return this.name;
/*     */   }
/*     */   
/*     */   public SubCommand addChildCommand(SubCommand child) {
/*  52 */     child.setParent(this);
/*  53 */     this.children.add(child);
/*  54 */     return this;
/*     */   }
/*     */   
/*     */   void setParent(IModCommand parent) {
/*  58 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public SortedSet<SubCommand> getChildren() {
/*  63 */     return this.children;
/*     */   }
/*     */   
/*     */   public void addAlias(String alias) {
/*  67 */     this.aliases.add(alias);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> func_71514_a() {
/*  72 */     return this.aliases;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<?> func_71516_a(ICommandSender sender, String[] text) {
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void func_71515_b(ICommandSender sender, String[] args) {
/*  82 */     if (!CommandHelpers.processStandardCommands(sender, this, args)) {
/*  83 */       processSubCommand(sender, args);
/*     */     }
/*     */   }
/*     */   
/*     */   public void processSubCommand(ICommandSender sender, String[] args) {
/*  88 */     CommandHelpers.throwWrongUsage(sender, this);
/*     */   }
/*     */   
/*     */   public SubCommand setPermLevel(PermLevel permLevel) {
/*  92 */     this.permLevel = permLevel;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getMinimumPermissionLevel() {
/*  98 */     return this.permLevel.permLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_71519_b(ICommandSender sender) {
/* 103 */     return sender.func_70003_b(getMinimumPermissionLevel(), func_71517_b());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82358_a(String[] args, int index) {
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_71518_a(ICommandSender sender) {
/* 113 */     return "/" + getFullCommandString() + " help";
/*     */   }
/*     */ 
/*     */   
/*     */   public void printHelp(ICommandSender sender) {
/* 118 */     CommandHelpers.printHelp(sender, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFullCommandString() {
/* 123 */     return this.parent.getFullCommandString() + " " + func_71517_b();
/*     */   }
/*     */   
/*     */   public int compareTo(ICommand command) {
/* 127 */     return func_71517_b().compareTo(command.func_71517_b());
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Object command) {
/* 132 */     return compareTo((ICommand)command);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\commands\SubCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
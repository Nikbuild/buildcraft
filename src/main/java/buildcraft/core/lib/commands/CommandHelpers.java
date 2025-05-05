/*     */ package buildcraft.core.lib.commands;
/*     */ 
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.ChatStyle;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CommandHelpers
/*     */ {
/*     */   public static World getWorld(ICommandSender sender, IModCommand command, String[] args, int worldArgIndex) {
/*  29 */     if (worldArgIndex < args.length) {
/*     */       try {
/*  31 */         int dim = Integer.parseInt(args[worldArgIndex]);
/*  32 */         WorldServer worldServer = MinecraftServer.func_71276_C().func_71218_a(dim);
/*  33 */         if (worldServer != null) {
/*  34 */           return (World)worldServer;
/*     */         }
/*  36 */       } catch (Exception ex) {
/*  37 */         throwWrongUsage(sender, command);
/*     */       } 
/*     */     }
/*  40 */     return getWorld(sender, command);
/*     */   }
/*     */   
/*     */   public static World getWorld(ICommandSender sender, IModCommand command) {
/*  44 */     if (sender instanceof EntityPlayer) {
/*  45 */       EntityPlayer player = (EntityPlayer)sender;
/*  46 */       return player.field_70170_p;
/*     */     } 
/*  48 */     return (World)MinecraftServer.func_71276_C().func_71218_a(0);
/*     */   }
/*     */   
/*     */   public static String[] getPlayers() {
/*  52 */     return MinecraftServer.func_71276_C().func_71213_z();
/*     */   }
/*     */   
/*     */   public static void sendLocalizedChatMessage(ICommandSender sender, String locTag, Object... args) {
/*  56 */     sender.func_145747_a((IChatComponent)new ChatComponentTranslation(locTag, args));
/*     */   }
/*     */   
/*     */   public static void sendLocalizedChatMessage(ICommandSender sender, ChatStyle chatStyle, String locTag, Object... args) {
/*  60 */     ChatComponentTranslation chat = new ChatComponentTranslation(locTag, args);
/*  61 */     chat.func_150255_a(chatStyle);
/*  62 */     sender.func_145747_a((IChatComponent)chat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendChatMessage(ICommandSender sender, String message) {
/*  73 */     sender.func_145747_a((IChatComponent)new ChatComponentText(message));
/*     */   }
/*     */   
/*     */   public static void throwWrongUsage(ICommandSender sender, IModCommand command) throws WrongUsageException {
/*  77 */     throw new WrongUsageException(String.format(StatCollector.func_74838_a("command.buildcraft.help"), new Object[] { command.func_71518_a(sender) }), new Object[0]);
/*     */   }
/*     */   
/*     */   public static void processChildCommand(ICommandSender sender, SubCommand child, String[] args) {
/*  81 */     if (!sender.func_70003_b(child.getMinimumPermissionLevel(), child.getFullCommandString())) {
/*  82 */       throw new WrongUsageException(StatCollector.func_74838_a("command.buildcraft.noperms"), new Object[0]);
/*     */     }
/*  84 */     String[] newargs = new String[args.length - 1];
/*  85 */     System.arraycopy(args, 1, newargs, 0, newargs.length);
/*  86 */     child.func_71515_b(sender, newargs);
/*     */   }
/*     */   
/*     */   public static void printHelp(ICommandSender sender, IModCommand command) {
/*  90 */     ChatStyle header = new ChatStyle();
/*  91 */     header.func_150238_a(EnumChatFormatting.GRAY);
/*  92 */     header.func_150227_a(Boolean.valueOf(true));
/*  93 */     sendLocalizedChatMessage(sender, header, "command.buildcraft." + command.getFullCommandString().replace(" ", ".") + ".format", new Object[] { command.getFullCommandString() });
/*  94 */     ChatStyle body = new ChatStyle();
/*  95 */     body.func_150238_a(EnumChatFormatting.GRAY);
/*  96 */     if (command.func_71514_a().size() > 0) {
/*  97 */       sendLocalizedChatMessage(sender, body, "command.buildcraft.aliases", new Object[] { command.func_71514_a().toString().replace("[", "").replace("]", "") });
/*     */     }
/*  99 */     if (command.getMinimumPermissionLevel() > 0) {
/* 100 */       sendLocalizedChatMessage(sender, body, "command.buildcraft.permlevel", new Object[] { Integer.valueOf(command.getMinimumPermissionLevel()) });
/*     */     }
/* 102 */     sendLocalizedChatMessage(sender, body, "command.buildcraft." + command.getFullCommandString().replace(" ", ".") + ".help", new Object[0]);
/* 103 */     if (!command.getChildren().isEmpty()) {
/* 104 */       sendLocalizedChatMessage(sender, "command.buildcraft.list", new Object[0]);
/* 105 */       for (SubCommand child : command.getChildren()) {
/* 106 */         sendLocalizedChatMessage(sender, "command.buildcraft." + child.getFullCommandString().replace(" ", ".") + ".desc", new Object[] { child.func_71517_b() });
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean processStandardCommands(ICommandSender sender, IModCommand command, String[] args) {
/* 112 */     if (args.length >= 1) {
/* 113 */       if ("help".equals(args[0])) {
/* 114 */         if (args.length >= 2) {
/* 115 */           for (SubCommand child : command.getChildren()) {
/* 116 */             if (matches(args[1], child)) {
/* 117 */               child.printHelp(sender);
/* 118 */               return true;
/*     */             } 
/*     */           } 
/*     */         }
/* 122 */         command.printHelp(sender);
/* 123 */         return true;
/*     */       } 
/* 125 */       for (SubCommand child : command.getChildren()) {
/* 126 */         if (matches(args[0], child)) {
/* 127 */           processChildCommand(sender, child, args);
/* 128 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 132 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean matches(String commandName, IModCommand command) {
/* 136 */     if (commandName.equals(command.func_71517_b()))
/* 137 */       return true; 
/* 138 */     if (command.func_71514_a() != null) {
/* 139 */       for (String alias : command.func_71514_a()) {
/* 140 */         if (commandName.equals(alias)) {
/* 141 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 145 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\commands\CommandHelpers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
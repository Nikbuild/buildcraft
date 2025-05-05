/*     */ package buildcraft.core;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import cpw.mods.fml.common.event.FMLInterModComms;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.ChatStyle;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraftforge.common.config.Property;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Version
/*     */   implements Runnable
/*     */ {
/*     */   public static final String VERSION = "7.1.26";
/*     */   
/*     */   public enum EnumUpdateState
/*     */   {
/*  34 */     CURRENT, OUTDATED, CONNECTION_ERROR;
/*     */   }
/*     */ 
/*     */   
/*  38 */   public static EnumUpdateState currentVersion = EnumUpdateState.CURRENT;
/*     */ 
/*     */   
/*     */   private static final String REMOTE_VERSION_FILE = "https://raw.githubusercontent.com/BuildCraft/BuildCraft/master/buildcraft_resources/versions.txt";
/*     */   
/*     */   private static final String REMOTE_CHANGELOG_ROOT = "https://raw.githubusercontent.com/BuildCraft/BuildCraft/master/buildcraft_resources/changelog/";
/*     */   
/*     */   private static String recommendedVersion;
/*     */   
/*     */   private static String[] cachedChangelog;
/*     */   
/*  49 */   private static Version instance = new Version();
/*     */   
/*     */   private static boolean sentIMCOutdatedMessage = false;
/*     */   
/*     */   public static String getVersion() {
/*  54 */     return "7.1.26";
/*     */   }
/*     */   
/*     */   public static boolean isOutdated() {
/*  58 */     return (currentVersion == EnumUpdateState.OUTDATED);
/*     */   }
/*     */   
/*     */   public static boolean needsUpdateNoticeAndMarkAsSeen() {
/*  62 */     if (!isOutdated() || sentIMCOutdatedMessage) {
/*  63 */       return false;
/*     */     }
/*     */     
/*  66 */     Property property = BuildCraftCore.mainConfiguration.get("vars", "version.seen", "7.1.26");
/*  67 */     property.comment = "indicates the last version the user has been informed about and will suppress further notices on it.";
/*  68 */     String seenVersion = property.getString();
/*     */     
/*  70 */     if (recommendedVersion == null || recommendedVersion.equals(seenVersion)) {
/*  71 */       return false;
/*     */     }
/*     */     
/*  74 */     property.set(recommendedVersion);
/*  75 */     BuildCraftCore.mainConfiguration.save();
/*  76 */     return true;
/*     */   }
/*     */   
/*     */   public static String getRecommendedVersion() {
/*  80 */     return recommendedVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void versionCheck() {
/*     */     try {
/*  86 */       if ("0.0.0".equals("7.1.26")) {
/*     */         return;
/*     */       }
/*     */       
/*  90 */       String location = "https://raw.githubusercontent.com/BuildCraft/BuildCraft/master/buildcraft_resources/versions.txt";
/*  91 */       HttpURLConnection conn = null;
/*  92 */       while (location != null && !location.isEmpty()) {
/*  93 */         URL url = new URL(location);
/*     */         
/*  95 */         if (conn != null) {
/*  96 */           conn.disconnect();
/*     */         }
/*     */         
/*  99 */         conn = (HttpURLConnection)url.openConnection();
/* 100 */         conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; ru; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 (.NET CLR 3.5.30729)");
/*     */         
/* 102 */         conn.connect();
/* 103 */         location = conn.getHeaderField("Location");
/*     */       } 
/*     */       
/* 106 */       if (conn == null) {
/* 107 */         throw new NullPointerException();
/*     */       }
/*     */       
/* 110 */       BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*     */ 
/*     */       
/* 113 */       String mcVersion = CoreProxy.proxy.getMinecraftVersion(); String line;
/* 114 */       while ((line = reader.readLine()) != null) {
/* 115 */         String[] tokens = line.split(":");
/* 116 */         if (mcVersion.matches(tokens[0]) && 
/* 117 */           "BuildCraft".matches(tokens[1])) {
/* 118 */           recommendedVersion = tokens[2];
/*     */           
/* 120 */           if ("7.1.26".matches(tokens[2])) {
/* 121 */             BCLog.logger.trace("Using the latest version [" + getVersion() + "] for Minecraft " + mcVersion);
/* 122 */             currentVersion = EnumUpdateState.CURRENT;
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 129 */       BCLog.logger.warn("Using outdated version [7.1.26] for Minecraft " + mcVersion + ". Consider updating to " + recommendedVersion + ".");
/*     */       
/* 131 */       currentVersion = EnumUpdateState.OUTDATED;
/* 132 */       sendIMCOutdatedMessage();
/*     */       
/* 134 */       conn.disconnect();
/* 135 */       reader.close();
/* 136 */     } catch (Exception e) {
/* 137 */       BCLog.logger.warn("Unable to read from remote version authority.");
/* 138 */       BCLog.logger.warn(e.toString());
/* 139 */       currentVersion = EnumUpdateState.CONNECTION_ERROR;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String[] getChangelog() {
/* 144 */     if (cachedChangelog == null) {
/* 145 */       cachedChangelog = grabChangelog(recommendedVersion);
/*     */     }
/*     */     
/* 148 */     return cachedChangelog;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] grabChangelog(String version) {
/*     */     try {
/* 154 */       String location = "https://raw.githubusercontent.com/BuildCraft/BuildCraft/master/buildcraft_resources/changelog/" + version;
/* 155 */       HttpURLConnection conn = null;
/* 156 */       while (location != null && !location.isEmpty()) {
/* 157 */         URL url = new URL(location);
/*     */         
/* 159 */         if (conn != null) {
/* 160 */           conn.disconnect();
/*     */         }
/*     */         
/* 163 */         conn = (HttpURLConnection)url.openConnection();
/* 164 */         conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; ru; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 (.NET CLR 3.5.30729)");
/*     */         
/* 166 */         conn.connect();
/* 167 */         location = conn.getHeaderField("Location");
/*     */       } 
/*     */       
/* 170 */       if (conn == null) {
/* 171 */         throw new NullPointerException();
/*     */       }
/*     */       
/* 174 */       BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*     */ 
/*     */       
/* 177 */       ArrayList<String> changelog = new ArrayList<String>(); String line;
/* 178 */       while ((line = reader.readLine()) != null) {
/* 179 */         if (line.isEmpty()) {
/*     */           continue;
/*     */         }
/*     */         
/* 183 */         changelog.add(line);
/*     */       } 
/*     */       
/* 186 */       conn.disconnect();
/*     */       
/* 188 */       return changelog.<String>toArray(new String[changelog.size()]);
/*     */     }
/* 190 */     catch (Exception ex) {
/* 191 */       ex.printStackTrace();
/* 192 */       BCLog.logger.warn("Unable to read changelog from remote site.");
/*     */ 
/*     */       
/* 195 */       return new String[] { String.format("Unable to retrieve changelog for %s %s", new Object[] { "BuildCraft", version }) };
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 201 */     int count = 0;
/* 202 */     currentVersion = null;
/*     */     
/* 204 */     BCLog.logger.info("Beginning version check");
/*     */     
/*     */     try {
/* 207 */       while (count < 3 && (currentVersion == null || currentVersion == EnumUpdateState.CONNECTION_ERROR)) {
/* 208 */         versionCheck();
/* 209 */         count++;
/*     */         
/* 211 */         if (currentVersion == EnumUpdateState.CONNECTION_ERROR) {
/* 212 */           BCLog.logger.info("Version check attempt " + count + " failed, trying again in 10 seconds");
/* 213 */           Thread.sleep(10000L);
/*     */         } 
/*     */       } 
/* 216 */     } catch (InterruptedException e) {
/* 217 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 220 */     if (currentVersion == EnumUpdateState.CONNECTION_ERROR) {
/* 221 */       BCLog.logger.info("Version check failed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendIMCOutdatedMessage() {
/* 231 */     if (Loader.isModLoaded("VersionChecker")) {
/* 232 */       NBTTagCompound compound = new NBTTagCompound();
/* 233 */       compound.func_74778_a("modDisplayName", "BuildCraft");
/* 234 */       compound.func_74778_a("oldVersion", "7.1.26");
/* 235 */       compound.func_74778_a("newVersion", getRecommendedVersion());
/*     */       
/* 237 */       compound.func_74778_a("updateUrl", "http://www.mod-buildcraft.com/download/");
/* 238 */       compound.func_74757_a("isDirectLink", false);
/*     */       
/* 240 */       StringBuilder stringBuilder = new StringBuilder();
/* 241 */       for (String changeLogLine : getChangelog()) {
/* 242 */         stringBuilder.append(changeLogLine).append("\n");
/*     */       }
/* 244 */       compound.func_74778_a("changeLog", stringBuilder.toString());
/*     */       
/* 246 */       FMLInterModComms.sendRuntimeMessage("BuildCraft|Core", "VersionChecker", "addUpdate", compound);
/* 247 */       sentIMCOutdatedMessage = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void displayChangelog(ICommandSender sender) {
/* 252 */     sender.func_145747_a((new ChatComponentTranslation("command.buildcraft.changelog_header", new Object[] { getRecommendedVersion()
/* 253 */           })).func_150255_a((new ChatStyle()).func_150238_a(EnumChatFormatting.GRAY).func_150227_a(Boolean.valueOf(true))));
/* 254 */     for (String updateLine : getChangelog()) {
/* 255 */       EnumChatFormatting format = EnumChatFormatting.BLUE;
/* 256 */       if (updateLine.startsWith("*")) {
/* 257 */         format = EnumChatFormatting.WHITE;
/* 258 */       } else if (updateLine.trim().endsWith(":")) {
/* 259 */         format = EnumChatFormatting.GOLD;
/*     */       } 
/* 261 */       sender.func_145747_a((new ChatComponentText(updateLine)).func_150255_a((new ChatStyle()).func_150238_a(format)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void check() {
/* 266 */     (new Thread(instance)).start();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\Version.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
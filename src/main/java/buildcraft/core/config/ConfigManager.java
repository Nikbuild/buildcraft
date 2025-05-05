/*     */ package buildcraft.core.config;
/*     */ 
/*     */ import cpw.mods.fml.client.IModGuiFactory;
/*     */ import cpw.mods.fml.client.config.GuiConfig;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraftforge.common.config.ConfigCategory;
/*     */ import net.minecraftforge.common.config.Configuration;
/*     */ import net.minecraftforge.common.config.Property;
/*     */ 
/*     */ public class ConfigManager
/*     */   implements IModGuiFactory
/*     */ {
/*     */   public static Configuration config;
/*     */   
/*     */   public static class GuiConfigManager
/*     */     extends GuiConfig {
/*     */     public GuiConfigManager(GuiScreen parentScreen) {
/*  22 */       super(parentScreen, new ArrayList(), "BuildCraft|Core", "config", false, false, I18n.func_135052_a("config.buildcraft", new Object[0]));
/*     */       
/*  24 */       for (String s : ConfigManager.config.getCategoryNames()) {
/*  25 */         if (!s.contains("."))
/*  26 */           this.configElements.add(new BCConfigElement(ConfigManager.config.getCategory(s))); 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public enum RestartRequirement
/*     */   {
/*  33 */     NONE, WORLD, GAME;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConfigManager() {}
/*     */ 
/*     */   
/*     */   public ConfigManager(Configuration c) {
/*  41 */     config = c;
/*     */   }
/*     */   
/*     */   public ConfigCategory getCat(String name) {
/*  45 */     return config.getCategory(name);
/*     */   }
/*     */   
/*     */   public Property get(String iName) {
/*  49 */     int sep = iName.lastIndexOf(".");
/*  50 */     return get(iName.substring(0, sep), iName.substring(sep + 1));
/*     */   }
/*     */   
/*     */   public Property get(String catName, String propName) {
/*  54 */     ConfigCategory c = config.getCategory(catName);
/*  55 */     return c.get(propName);
/*     */   }
/*     */   
/*     */   private Property create(String s, Object o) {
/*     */     Property p;
/*  60 */     if (o instanceof Integer) {
/*  61 */       p = new Property(s, o.toString(), Property.Type.INTEGER);
/*  62 */     } else if (o instanceof String) {
/*  63 */       p = new Property(s, (String)o, Property.Type.STRING);
/*  64 */     } else if (o instanceof Double || o instanceof Float) {
/*  65 */       p = new Property(s, o.toString(), Property.Type.DOUBLE);
/*  66 */     } else if (o instanceof Boolean) {
/*  67 */       p = new Property(s, o.toString(), Property.Type.BOOLEAN);
/*  68 */     } else if (o instanceof String[]) {
/*  69 */       p = new Property(s, (String[])o, Property.Type.STRING);
/*     */     } else {
/*  71 */       return null;
/*     */     } 
/*  73 */     return p;
/*     */   }
/*     */   public Property register(String catName, String propName, Object property, String comment, RestartRequirement restartRequirement) {
/*     */     Property p;
/*  77 */     ConfigCategory c = config.getCategory(catName);
/*  78 */     ConfigCategory parent = c;
/*  79 */     while (parent != null) {
/*  80 */       parent.setLanguageKey("config." + parent.getQualifiedName());
/*  81 */       parent = parent.parent;
/*     */     } 
/*     */     
/*  84 */     if (c.get(propName) != null) {
/*  85 */       p = c.get(propName);
/*     */     } else {
/*  87 */       p = create(propName, property);
/*  88 */       c.put(propName, p);
/*     */     } 
/*  90 */     p.comment = comment;
/*  91 */     RestartRequirement r = restartRequirement;
/*  92 */     p.setLanguageKey("config." + catName + "." + propName);
/*  93 */     p.setRequiresWorldRestart((r == RestartRequirement.WORLD));
/*  94 */     p.setRequiresMcRestart((r == RestartRequirement.GAME));
/*  95 */     return p;
/*     */   }
/*     */   
/*     */   public Property register(String name, Object property, String comment, RestartRequirement restartRequirement) {
/*  99 */     String prefix = name.substring(0, name.lastIndexOf("."));
/* 100 */     String suffix = name.substring(name.lastIndexOf(".") + 1);
/*     */     
/* 102 */     return register(prefix, suffix, property, comment, restartRequirement);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(Minecraft minecraftInstance) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? extends GuiScreen> mainConfigGuiClass() {
/* 112 */     return (Class)GuiConfigManager.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IModGuiFactory.RuntimeOptionGuiHandler getHandlerFor(IModGuiFactory.RuntimeOptionCategoryElement element) {
/* 122 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\config\ConfigManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
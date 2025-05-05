/*    */ package buildcraft.core.config;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraftforge.common.config.Configuration;
/*    */ import net.minecraftforge.common.config.Property;
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
/*    */ public class BuildCraftConfiguration
/*    */   extends Configuration
/*    */ {
/*    */   public BuildCraftConfiguration(File file) {
/* 21 */     super(file);
/*    */   }
/*    */ 
/*    */   
/*    */   public void save() {
/* 26 */     Property versionProp = get("general", "version", "7.1.26");
/* 27 */     versionProp.setShowInGui(false);
/* 28 */     versionProp.set("7.1.26");
/* 29 */     super.save();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\config\BuildCraftConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
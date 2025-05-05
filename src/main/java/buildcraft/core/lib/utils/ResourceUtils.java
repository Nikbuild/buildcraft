/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ResourceUtils
/*    */ {
/*    */   public static IIcon getIconPriority(IIconRegister register, String prefix, String[] suffixes) {
/* 18 */     for (int i = 0; i < suffixes.length; i++) {
/* 19 */       String suffix = suffixes[i];
/* 20 */       String path = prefix + "/" + suffix;
/* 21 */       if (i == suffixes.length - 1 || resourceExists(iconToResourcePath(register, path))) {
/* 22 */         return register.func_94245_a(path);
/*    */       }
/*    */     } 
/* 25 */     return null;
/*    */   }
/*    */   
/*    */   public static IIcon getIcon(IIconRegister register, String prefix, String suffix) {
/* 29 */     return register.func_94245_a(prefix + "/" + suffix);
/*    */   }
/*    */   
/*    */   public static String iconToResourcePath(IIconRegister register, String name) {
/* 33 */     int splitLocation = name.indexOf(":");
/*    */     
/* 35 */     if (register instanceof TextureMap) {
/* 36 */       String dir = (((TextureMap)register).func_130086_a() == 1) ? "items" : "blocks";
/* 37 */       return name.substring(0, splitLocation) + ":textures/" + dir + "/" + name.substring(splitLocation + 1) + ".png";
/*    */     } 
/*    */     
/* 40 */     return name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getObjectPrefix(String objectName) {
/* 51 */     if (objectName == null) {
/* 52 */       return null;
/*    */     }
/*    */     
/* 55 */     int splitLocation = objectName.indexOf(":");
/* 56 */     return objectName.substring(0, splitLocation).replaceAll("[^a-zA-Z0-9\\s]", "") + objectName.substring(splitLocation);
/*    */   }
/*    */   
/*    */   public static boolean resourceExists(String name) {
/*    */     try {
/* 61 */       IResource resource = Minecraft.func_71410_x().func_110442_L().func_110536_a(new ResourceLocation(name));
/* 62 */       return (resource != null);
/* 63 */     } catch (IOException e) {
/* 64 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\ResourceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
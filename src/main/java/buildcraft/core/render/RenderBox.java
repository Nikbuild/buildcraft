/*    */ package buildcraft.core.render;
/*    */ 
/*    */ import buildcraft.core.Box;
/*    */ import buildcraft.core.LaserData;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RenderBox
/*    */ {
/*    */   public static void doRender(TextureManager t, ResourceLocation texture, Box box) {
/* 28 */     GL11.glPushMatrix();
/* 29 */     GL11.glDisable(2896);
/*    */     
/* 31 */     box.createLaserData();
/*    */     
/* 33 */     for (LaserData l : box.lasersData) {
/* 34 */       l.update();
/* 35 */       GL11.glPushMatrix();
/* 36 */       GL11.glTranslated(0.5D, 0.5D, 0.5D);
/* 37 */       RenderLaser.doRenderLaser(t, l, texture);
/* 38 */       GL11.glPopMatrix();
/*    */     } 
/*    */     
/* 41 */     GL11.glEnable(2896);
/* 42 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\render\RenderBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
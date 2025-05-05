/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.core.render.RenderLaser;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ public class RenderPathMarker
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/* 23 */   private ModelBase model = new ModelBase() {
/*    */     
/*    */     };
/*    */   
/*    */   public RenderPathMarker() {
/* 28 */     this.box = new ModelRenderer(this.model, 0, 1);
/* 29 */     this.box.func_78789_a(-8.0F, -8.0F, -8.0F, 16, 4, 16);
/* 30 */     this.box.field_78800_c = 8.0F;
/* 31 */     this.box.field_78797_d = 8.0F;
/* 32 */     this.box.field_78798_e = 8.0F;
/*    */   }
/*    */   private ModelRenderer box;
/*    */   
/*    */   public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f) {
/* 37 */     TilePathMarker marker = (TilePathMarker)tileentity;
/*    */     
/* 39 */     if (marker != null) {
/* 40 */       GL11.glPushMatrix();
/* 41 */       GL11.glPushAttrib(8192);
/* 42 */       GL11.glEnable(2884);
/* 43 */       GL11.glEnable(2896);
/* 44 */       GL11.glEnable(3042);
/* 45 */       GL11.glBlendFunc(770, 771);
/*    */       
/* 47 */       GL11.glTranslated(x, y, z);
/* 48 */       GL11.glTranslated(-tileentity.field_145851_c, -tileentity.field_145848_d, -tileentity.field_145849_e);
/*    */       
/* 50 */       for (LaserData laser : marker.lasers) {
/* 51 */         if (laser != null) {
/* 52 */           GL11.glPushMatrix();
/*    */           
/* 54 */           RenderLaser.doRenderLaser(TileEntityRendererDispatcher.field_147556_a.field_147553_e, laser, EntityLaser.LASER_TEXTURES[3]);
/*    */ 
/*    */           
/* 57 */           GL11.glPopMatrix();
/*    */         } 
/*    */       } 
/*    */ 
/*    */       
/* 62 */       GL11.glPopAttrib();
/* 63 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\RenderPathMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
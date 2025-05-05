/*    */ package buildcraft.core.lib.render;
/*    */ 
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ public class RenderMultiTESR extends TileEntitySpecialRenderer {
/*    */   private final TileEntitySpecialRenderer[] renderers;
/*    */   
/*    */   public RenderMultiTESR(TileEntitySpecialRenderer[] renderers) {
/* 11 */     this.renderers = renderers;
/* 12 */     for (TileEntitySpecialRenderer r : renderers) {
/* 13 */       r.func_147497_a(TileEntityRendererDispatcher.field_147556_a);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_147500_a(TileEntity tile, double x, double y, double z, float f) {
/* 19 */     for (TileEntitySpecialRenderer r : this.renderers)
/* 20 */       r.func_147500_a(tile, x, y, z, f); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\RenderMultiTESR.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
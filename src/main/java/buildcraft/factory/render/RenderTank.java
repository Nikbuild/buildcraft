/*    */ package buildcraft.factory.render;
/*    */ 
/*    */ import buildcraft.core.lib.render.FluidRenderer;
/*    */ import buildcraft.core.lib.render.RenderUtils;
/*    */ import buildcraft.core.lib.utils.MathUtils;
/*    */ import buildcraft.core.proxy.CoreProxy;
/*    */ import buildcraft.factory.TileTank;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.fluids.FluidStack;
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
/*    */ public class RenderTank
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/*    */   public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f) {
/*    */     int color;
/* 30 */     TileTank tank = (TileTank)CoreProxy.proxy.getServerTile(tileentity);
/*    */     
/* 32 */     FluidStack liquid = tank.tank.getFluid();
/* 33 */     if (liquid == null || liquid.getFluid() == null || liquid.amount <= 0) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 39 */     if ((tank.func_145831_w()).field_72995_K) {
/* 40 */       color = tank.tank.colorRenderCache;
/*    */     } else {
/* 42 */       color = liquid.getFluid().getColor(liquid);
/*    */     } 
/*    */     
/* 45 */     int[] displayList = FluidRenderer.getFluidDisplayLists(liquid, tileentity.func_145831_w(), false);
/* 46 */     if (displayList == null) {
/*    */       return;
/*    */     }
/*    */     
/* 50 */     GL11.glPushMatrix();
/* 51 */     GL11.glPushAttrib(8192);
/* 52 */     GL11.glEnable(2884);
/* 53 */     GL11.glDisable(2896);
/* 54 */     GL11.glEnable(3042);
/* 55 */     GL11.glBlendFunc(770, 771);
/*    */     
/* 57 */     func_147499_a(TextureMap.field_110575_b);
/* 58 */     RenderUtils.setGLColorFromInt(color);
/*    */     
/* 60 */     GL11.glTranslatef((float)x + 0.125F, (float)y + 0.5F, (float)z + 0.125F);
/* 61 */     GL11.glScalef(0.75F, 0.999F, 0.75F);
/* 62 */     GL11.glTranslatef(0.0F, -0.5F, 0.0F);
/*    */     
/* 64 */     int dl = (int)(liquid.amount / tank.tank.getCapacity() * 99.0F);
/* 65 */     GL11.glCallList(displayList[MathUtils.clamp(dl, 0, 99)]);
/*    */     
/* 67 */     GL11.glPopAttrib();
/* 68 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\render\RenderTank.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
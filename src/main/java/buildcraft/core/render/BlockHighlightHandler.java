/*    */ package buildcraft.core.render;
/*    */ 
/*    */ import buildcraft.core.lib.render.ICustomHighlight;
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.RenderGlobal;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraftforge.client.event.DrawBlockHighlightEvent;
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
/*    */ public class BlockHighlightHandler
/*    */ {
/*    */   @SideOnly(Side.CLIENT)
/*    */   @SubscribeEvent
/*    */   public void handleBlockHighlight(DrawBlockHighlightEvent e) {
/* 31 */     if (e.target.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 32 */       int x = e.target.field_72311_b;
/* 33 */       int y = e.target.field_72312_c;
/* 34 */       int z = e.target.field_72309_d;
/* 35 */       Block block = e.player.field_70170_p.func_147439_a(x, y, z);
/* 36 */       if (block instanceof ICustomHighlight) {
/* 37 */         AxisAlignedBB[] aabbs = ((ICustomHighlight)block).getBoxes(e.player.field_70170_p, x, y, z, e.player);
/* 38 */         Vec3 pos = e.player.func_70666_h(e.partialTicks);
/*    */         
/* 40 */         GL11.glEnable(3042);
/* 41 */         OpenGlHelper.func_148821_a(770, 771, 1, 0);
/* 42 */         GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
/* 43 */         GL11.glLineWidth(2.0F);
/* 44 */         GL11.glDisable(3553);
/* 45 */         GL11.glDepthMask(false);
/*    */         
/* 47 */         double exp = ((ICustomHighlight)block).getExpansion();
/* 48 */         for (AxisAlignedBB aabb : aabbs) {
/* 49 */           RenderGlobal.func_147590_a(aabb.func_72329_c().func_72314_b(exp, exp, exp)
/* 50 */               .func_72317_d(x, y, z)
/* 51 */               .func_72317_d(-pos.field_72450_a, -pos.field_72448_b, -pos.field_72449_c), -1);
/*    */         }
/*    */         
/* 54 */         GL11.glDepthMask(true);
/* 55 */         GL11.glEnable(3553);
/* 56 */         GL11.glDisable(3042);
/* 57 */         e.setCanceled(true);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\render\BlockHighlightHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
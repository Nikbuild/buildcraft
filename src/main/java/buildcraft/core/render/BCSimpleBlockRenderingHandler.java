/*    */ package buildcraft.core.render;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ 
/*    */ public abstract class BCSimpleBlockRenderingHandler implements ISimpleBlockRenderingHandler {
/*    */   protected void fixEmptyAlphaPass(int x, int y, int z) {
/*  9 */     if (BuildCraftCore.alphaPassBugPresent) {
/* 10 */       Tessellator.field_78398_a.func_78374_a(x, y, z, 0.0D, 0.0D);
/* 11 */       Tessellator.field_78398_a.func_78374_a(x, y, z, 0.0D, 0.0D);
/* 12 */       Tessellator.field_78398_a.func_78374_a(x, y, z, 0.0D, 0.0D);
/* 13 */       Tessellator.field_78398_a.func_78374_a(x, y, z, 0.0D, 0.0D);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\render\BCSimpleBlockRenderingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
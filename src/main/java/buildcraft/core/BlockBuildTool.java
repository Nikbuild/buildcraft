/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockBuildTool
/*    */   extends Block
/*    */ {
/*    */   public BlockBuildTool() {
/* 20 */     super(Material.field_151573_f);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_149651_a(IIconRegister itemRegister) {}
/*    */ 
/*    */   
/*    */   public IIcon func_149691_a(int i, int j) {
/* 29 */     return BuildCraftCore.redLaserTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\BlockBuildTool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.api.transport.IPipeTile;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import buildcraft.core.lib.utils.ResourceUtils;
/*    */ import buildcraft.core.lib.utils.Utils;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ public abstract class BlockHatched
/*    */   extends BlockBuildCraft
/*    */ {
/*    */   private IIcon itemHatch;
/*    */   
/*    */   protected BlockHatched(Material material) {
/* 21 */     super(material);
/*    */     
/* 23 */     setRotatable(true);
/* 24 */     setPassCount(2);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightValue(IBlockAccess world, int x, int y, int z) {
/* 29 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_149651_a(IIconRegister register) {
/* 34 */     super.func_149651_a(register);
/* 35 */     String base = ResourceUtils.getObjectPrefix(Block.field_149771_c.func_148750_c(this));
/* 36 */     this.itemHatch = register.func_94245_a(base + "/item_hatch");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon func_149673_e(IBlockAccess access, int x, int y, int z, int side) {
/* 42 */     if (this.renderPass == 1) {
/* 43 */       return Utils.isPipeConnected(access, x, y, z, ForgeDirection.getOrientation(side), IPipeTile.PipeType.ITEM) ? this.itemHatch : BuildCraftCore.transparentTexture;
/*    */     }
/* 45 */     return super.func_149673_e(access, x, y, z, side);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getIconAbsolute(IBlockAccess access, int x, int y, int z, int side, int meta) {
/* 51 */     if (this.renderPass == 0) {
/* 52 */       return super.getIconAbsolute(access, x, y, z, side, meta);
/*    */     }
/* 54 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getIconAbsolute(int side, int meta) {
/* 60 */     if (this.renderPass == 0) {
/* 61 */       return super.getIconAbsolute(side, meta);
/*    */     }
/* 63 */     return (side == 1) ? this.itemHatch : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149686_d() {
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\BlockHatched.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.core.lib.utils.ResourceUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
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
/*    */ public class BlockPathMarker
/*    */   extends BlockMarker
/*    */ {
/*    */   private IIcon activeMarker;
/*    */   
/*    */   public TileEntity func_149915_a(World world, int metadata) {
/* 31 */     return (TileEntity)new TilePathMarker();
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon getIconAbsolute(IBlockAccess iblockaccess, int x, int y, int z, int side, int metadata) {
/* 36 */     TilePathMarker marker = (TilePathMarker)iblockaccess.func_147438_o(x, y, z);
/*    */     
/* 38 */     if (side == 1 || (marker != null && marker.tryingToConnect)) {
/* 39 */       return this.activeMarker;
/*    */     }
/* 41 */     return getIconAbsolute(side, metadata);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_149651_a(IIconRegister par1IconRegister) {
/* 48 */     super.func_149651_a(par1IconRegister);
/* 49 */     this.activeMarker = par1IconRegister.func_94245_a(ResourceUtils.getObjectPrefix(Block.field_149771_c.func_148750_c(this)) + "/active");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\BlockPathMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
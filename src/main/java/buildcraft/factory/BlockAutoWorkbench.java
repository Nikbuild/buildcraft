/*    */ package buildcraft.factory;
/*    */ 
/*    */ import buildcraft.BuildCraftFactory;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ public class BlockAutoWorkbench
/*    */   extends BlockBuildCraft
/*    */ {
/*    */   public BlockAutoWorkbench() {
/* 23 */     super(Material.field_151575_d);
/* 24 */     func_149711_c(3.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 29 */     if (super.func_149727_a(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
/* 30 */       return true;
/*    */     }
/*    */     
/* 33 */     if (entityplayer.func_70093_af()) {
/* 34 */       return false;
/*    */     }
/*    */     
/* 37 */     if (entityplayer.func_71045_bC() != null && 
/* 38 */       entityplayer.func_71045_bC().func_77973_b() instanceof buildcraft.api.transport.IItemPipe) {
/* 39 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 43 */     if (!world.field_72995_K) {
/* 44 */       entityplayer.openGui(BuildCraftFactory.instance, 30, world, i, j, k);
/*    */     }
/*    */     
/* 47 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int metadata) {
/* 52 */     return (TileEntity)new TileAutoWorkbench();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\BlockAutoWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
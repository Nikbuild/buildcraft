/*    */ package buildcraft.silicon;
/*    */ 
/*    */ import buildcraft.BuildCraftSilicon;
/*    */ import buildcraft.core.BCCreativeTab;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockPackager extends BlockBuildCraft {
/*    */   public BlockPackager() {
/* 14 */     super(Material.field_151573_f);
/*    */     
/* 16 */     func_149711_c(10.0F);
/* 17 */     func_149647_a((CreativeTabs)BCCreativeTab.get("main"));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 22 */     if (super.func_149727_a(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
/* 23 */       return true;
/*    */     }
/*    */     
/* 26 */     if (!world.field_72995_K) {
/* 27 */       entityplayer.openGui(BuildCraftSilicon.instance, 10, world, i, j, k);
/*    */     }
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int meta) {
/* 34 */     return (TileEntity)new TilePackager();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\BlockPackager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
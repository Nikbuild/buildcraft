/*    */ package buildcraft.builders;
/*    */ 
/*    */ import buildcraft.BuildCraftBuilders;
/*    */ import buildcraft.core.BCCreativeTab;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockFiller
/*    */   extends BlockBuildCraft
/*    */ {
/*    */   public BlockFiller() {
/* 26 */     super(Material.field_151573_f);
/*    */     
/* 28 */     func_149711_c(5.0F);
/* 29 */     func_149647_a((CreativeTabs)BCCreativeTab.get("main"));
/* 30 */     setRotatable(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 35 */     if (super.func_149727_a(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
/* 36 */       return true;
/*    */     }
/*    */     
/* 39 */     if (entityplayer.func_70093_af()) {
/* 40 */       return false;
/*    */     }
/*    */     
/* 43 */     if (!world.field_72995_K) {
/* 44 */       entityplayer.openGui(BuildCraftBuilders.instance, 12, world, i, j, k);
/*    */     }
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int metadata) {
/* 52 */     return (TileEntity)new TileFiller();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149686_d() {
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightValue(IBlockAccess world, int x, int y, int z) {
/* 67 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\BlockFiller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.builders;
/*    */ 
/*    */ import buildcraft.BuildCraftBuilders;
/*    */ import buildcraft.core.BCCreativeTab;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
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
/*    */ public class BlockBlueprintLibrary
/*    */   extends BlockBuildCraft
/*    */ {
/*    */   public BlockBlueprintLibrary() {
/* 23 */     super(Material.field_151573_f, (CreativeTabs)BCCreativeTab.get("main"));
/* 24 */     func_149711_c(5.0F);
/* 25 */     setRotatable(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 30 */     if (super.func_149727_a(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
/* 31 */       return true;
/*    */     }
/*    */     
/* 34 */     if (entityplayer.func_70093_af()) {
/* 35 */       return false;
/*    */     }
/*    */     
/* 38 */     TileEntity tile = world.func_147438_o(i, j, k);
/* 39 */     if (tile instanceof TileBlueprintLibrary && 
/* 40 */       !world.field_72995_K) {
/* 41 */       entityplayer.openGui(BuildCraftBuilders.instance, 13, world, i, j, k);
/*    */     }
/*    */ 
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int metadata) {
/* 50 */     return (TileEntity)new TileBlueprintLibrary();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\BlockBlueprintLibrary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
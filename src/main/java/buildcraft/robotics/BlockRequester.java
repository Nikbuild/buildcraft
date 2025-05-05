/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import buildcraft.BuildCraftRobotics;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import buildcraft.core.lib.block.IComparatorInventory;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ public class BlockRequester
/*    */   extends BlockBuildCraft
/*    */   implements IComparatorInventory
/*    */ {
/*    */   public BlockRequester() {
/* 24 */     super(Material.field_151573_f);
/* 25 */     setRotatable(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int meta) {
/* 30 */     return (TileEntity)new TileRequester();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 36 */     if (super.func_149727_a(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
/* 37 */       return true;
/*    */     }
/*    */     
/* 40 */     if (!world.field_72995_K) {
/* 41 */       entityplayer.openGui(BuildCraftRobotics.instance, 16, world, i, j, k);
/*    */     }
/*    */ 
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doesSlotCountComparator(TileEntity tile, int slot, ItemStack stack) {
/* 50 */     return (((TileRequester)tile).getRequestTemplate(slot) != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\BlockRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
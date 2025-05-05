/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
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
/*    */ 
/*    */ public class BlockFilteredBuffer
/*    */   extends BlockBuildCraft
/*    */   implements IComparatorInventory
/*    */ {
/*    */   public BlockFilteredBuffer() {
/* 25 */     super(Material.field_151573_f);
/* 26 */     func_149711_c(5.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int metadata) {
/* 31 */     return (TileEntity)new TileFilteredBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 37 */     if (super.func_149727_a(world, x, y, z, entityplayer, par6, par7, par8, par9)) {
/* 38 */       return true;
/*    */     }
/*    */     
/* 41 */     if (entityplayer.func_70093_af()) {
/* 42 */       return false;
/*    */     }
/*    */     
/* 45 */     if (entityplayer.func_71045_bC() != null && 
/* 46 */       entityplayer.func_71045_bC().func_77973_b() instanceof buildcraft.api.transport.IItemPipe) {
/* 47 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 51 */     if (!world.field_72995_K) {
/* 52 */       entityplayer.openGui(BuildCraftTransport.instance, 60, world, x, y, z);
/*    */     }
/*    */     
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doesSlotCountComparator(TileEntity tile, int slot, ItemStack stack) {
/* 60 */     return (((TileFilteredBuffer)tile).getFilters().func_70301_a(slot) != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\BlockFilteredBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
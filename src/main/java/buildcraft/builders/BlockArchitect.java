/*    */ package buildcraft.builders;
/*    */ 
/*    */ import buildcraft.BuildCraftBuilders;
/*    */ import buildcraft.core.internal.ICustomLEDBlock;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
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
/*    */ public class BlockArchitect
/*    */   extends BlockBuildCraft
/*    */   implements ICustomLEDBlock
/*    */ {
/*    */   public BlockArchitect() {
/* 27 */     super(Material.field_151573_f);
/* 28 */     setRotatable(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int metadata) {
/* 33 */     return (TileEntity)new TileArchitect();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 39 */     if (super.func_149727_a(world, x, y, z, entityplayer, par6, par7, par8, par9)) {
/* 40 */       return true;
/*    */     }
/*    */     
/* 43 */     Item equipped = (entityplayer.func_71045_bC() != null) ? entityplayer.func_71045_bC().func_77973_b() : null;
/* 44 */     if (equipped instanceof ItemConstructionMarker) {
/* 45 */       ItemConstructionMarker.link(entityplayer.func_71045_bC(), world, x, y, z);
/*    */       
/* 47 */       return true;
/*    */     } 
/* 49 */     if (!world.field_72995_K) {
/* 50 */       entityplayer.openGui(BuildCraftBuilders.instance, 10, world, x, y, z);
/*    */     }
/* 52 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149686_d() {
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightValue(IBlockAccess world, int x, int y, int z) {
/* 68 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getLEDSuffixes() {
/* 73 */     return new String[] { "led_red", "led_mode_copy", "led_mode_edit" };
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\BlockArchitect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
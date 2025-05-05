/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import buildcraft.BuildCraftRobotics;
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
/*    */ public class BlockZonePlan
/*    */   extends BlockBuildCraft
/*    */ {
/*    */   public BlockZonePlan() {
/* 22 */     super(Material.field_151573_f);
/* 23 */     setRotatable(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int meta) {
/* 28 */     return (TileEntity)new TileZonePlan();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 34 */     if (super.func_149727_a(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
/* 35 */       return true;
/*    */     }
/*    */     
/* 38 */     if (!world.field_72995_K) {
/* 39 */       entityplayer.openGui(BuildCraftRobotics.instance, 15, world, i, j, k);
/*    */     }
/*    */ 
/*    */     
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\BlockZonePlan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
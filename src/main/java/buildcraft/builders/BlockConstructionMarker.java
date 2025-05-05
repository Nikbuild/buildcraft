/*    */ package buildcraft.builders;
/*    */ 
/*    */ import buildcraft.core.BlockMarker;
/*    */ import buildcraft.core.lib.utils.BlockUtils;
/*    */ import buildcraft.core.lib.utils.Utils;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldServer;
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
/*    */ public class BlockConstructionMarker
/*    */   extends BlockMarker
/*    */ {
/*    */   public TileEntity func_149915_a(World world, int metadata) {
/* 31 */     return (TileEntity)new TileConstructionMarker();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_149749_a(World world, int x, int y, int z, Block block, int par6) {
/* 36 */     Utils.preDestroyBlock(world, x, y, z);
/* 37 */     dropMarkerIfPresent(world, x, y, z, true);
/* 38 */     super.func_149749_a(world, x, y, z, block, par6);
/*    */   }
/*    */   
/*    */   private boolean dropMarkerIfPresent(World world, int x, int y, int z, boolean onBreak) {
/* 42 */     TileConstructionMarker marker = (TileConstructionMarker)world.func_147438_o(x, y, z);
/* 43 */     if (marker != null && marker.itemBlueprint != null && !world.field_72995_K) {
/* 44 */       BlockUtils.dropItem((WorldServer)world, x, y, z, 6000, marker.itemBlueprint);
/* 45 */       marker.itemBlueprint = null;
/* 46 */       if (!onBreak) {
/* 47 */         marker.bluePrintBuilder = null;
/* 48 */         marker.bptContext = null;
/* 49 */         marker.sendNetworkUpdate();
/*    */       } 
/* 51 */       return true;
/*    */     } 
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
/* 58 */     super.func_149689_a(world, i, j, k, entityliving, stack);
/*    */     
/* 60 */     TileConstructionMarker tile = (TileConstructionMarker)world.func_147438_o(i, j, k);
/* 61 */     tile.direction = Utils.get2dOrientation(entityliving);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 67 */     if (super.func_149727_a(world, x, y, z, entityplayer, par6, par7, par8, par9)) {
/* 68 */       return true;
/*    */     }
/*    */     
/* 71 */     TileConstructionMarker marker = (TileConstructionMarker)world.func_147438_o(x, y, z);
/*    */     
/* 73 */     Item equipped = (entityplayer.func_71045_bC() != null) ? entityplayer.func_71045_bC().func_77973_b() : null;
/*    */ 
/*    */     
/* 76 */     if (equipped instanceof ItemBlueprint) {
/* 77 */       if (marker.itemBlueprint == null) {
/* 78 */         ItemStack stack = entityplayer.field_71071_by.func_70448_g().func_77946_l();
/* 79 */         stack.field_77994_a = 1;
/* 80 */         marker.setBlueprint(stack);
/* 81 */         stack = null;
/* 82 */         if ((entityplayer.field_71071_by.func_70448_g()).field_77994_a > 1) {
/* 83 */           stack = entityplayer.func_71045_bC().func_77946_l();
/* 84 */           (entityplayer.func_71045_bC()).field_77994_a--;
/*    */         } 
/* 86 */         entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, stack);
/*    */         
/* 88 */         return true;
/*    */       } 
/* 90 */     } else if (equipped instanceof ItemConstructionMarker) {
/* 91 */       if (ItemConstructionMarker.linkStarted(entityplayer.func_71045_bC())) {
/* 92 */         ItemConstructionMarker.link(entityplayer.func_71045_bC(), world, x, y, z);
/* 93 */         return true;
/*    */       } 
/* 95 */     } else if ((equipped == null || equipped instanceof buildcraft.api.tools.IToolWrench) && entityplayer.func_70093_af()) {
/* 96 */       return dropMarkerIfPresent(world, x, y, z, false);
/*    */     } 
/*    */     
/* 99 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\BlockConstructionMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
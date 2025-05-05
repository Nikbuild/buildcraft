/*    */ package buildcraft.factory;
/*    */ 
/*    */ import buildcraft.api.tools.IToolWrench;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ public class BlockPump
/*    */   extends BlockBuildCraft
/*    */ {
/*    */   public BlockPump() {
/* 25 */     super(Material.field_151573_f);
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int metadata) {
/* 30 */     return (TileEntity)new TilePump();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 35 */     if (super.func_149727_a(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
/* 36 */       return true;
/*    */     }
/*    */     
/* 39 */     TileEntity tile = world.func_147438_o(i, j, k);
/*    */     
/* 41 */     if (tile instanceof TilePump) {
/* 42 */       TilePump pump = (TilePump)tile;
/*    */ 
/*    */       
/* 45 */       if (entityplayer.func_70093_af()) {
/* 46 */         return false;
/*    */       }
/*    */ 
/*    */       
/* 50 */       Item equipped = (entityplayer.func_71045_bC() != null) ? entityplayer.func_71045_bC().func_77973_b() : null;
/* 51 */       if (equipped instanceof IToolWrench && ((IToolWrench)equipped).canWrench(entityplayer, i, j, k)) {
/*    */         
/* 53 */         pump.tank.reset();
/* 54 */         pump.rebuildQueue();
/* 55 */         ((IToolWrench)equipped).wrenchUsed(entityplayer, i, j, k);
/* 56 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_149695_a(World world, int x, int y, int z, Block block) {
/* 65 */     super.func_149695_a(world, x, y, z, block);
/* 66 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 67 */     if (tile instanceof TilePump) {
/* 68 */       ((TilePump)tile).onNeighborBlockChange(block);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightValue(IBlockAccess world, int x, int y, int z) {
/* 74 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\BlockPump.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
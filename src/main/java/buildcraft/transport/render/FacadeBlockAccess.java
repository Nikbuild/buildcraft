/*    */ package buildcraft.transport.render;
/*    */ 
/*    */ import buildcraft.api.transport.IPipeTile;
/*    */ import buildcraft.api.transport.pluggable.IFacadePluggable;
/*    */ import buildcraft.api.transport.pluggable.PipePluggable;
/*    */ import buildcraft.core.CompatHooks;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ public class FacadeBlockAccess
/*    */   implements IBlockAccess
/*    */ {
/*    */   private final IBlockAccess world;
/*    */   private final ForgeDirection side;
/*    */   
/*    */   public FacadeBlockAccess(IBlockAccess world, ForgeDirection side) {
/* 21 */     this.world = world;
/* 22 */     this.side = side;
/*    */   }
/*    */ 
/*    */   
/*    */   public Block func_147439_a(int x, int y, int z) {
/* 27 */     Block compatBlock = CompatHooks.INSTANCE.getVisualBlock(this.world, x, y, z, this.side);
/* 28 */     if (compatBlock != null) {
/* 29 */       return compatBlock;
/*    */     }
/*    */     
/* 32 */     TileEntity tile = this.world.func_147438_o(x, y, z);
/* 33 */     if (tile instanceof IPipeTile) {
/* 34 */       PipePluggable p = ((IPipeTile)tile).getPipePluggable(this.side);
/* 35 */       if (p instanceof IFacadePluggable) {
/* 36 */         return ((IFacadePluggable)p).getCurrentBlock();
/*    */       }
/*    */     } 
/* 39 */     return Blocks.field_150350_a;
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_147438_o(int x, int y, int z) {
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_72802_i(int x, int y, int z, int a) {
/* 49 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_72805_g(int x, int y, int z) {
/* 54 */     int compatMeta = CompatHooks.INSTANCE.getVisualMeta(this.world, x, y, z, this.side);
/* 55 */     if (compatMeta >= 0) {
/* 56 */       return compatMeta;
/*    */     }
/*    */     
/* 59 */     TileEntity tile = this.world.func_147438_o(x, y, z);
/* 60 */     if (tile instanceof IPipeTile) {
/* 61 */       PipePluggable p = ((IPipeTile)tile).getPipePluggable(this.side);
/* 62 */       if (p instanceof IFacadePluggable)
/*    */       {
/* 64 */         return ((IFacadePluggable)p).getCurrentMetadata();
/*    */       }
/*    */     } 
/* 67 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_72879_k(int x, int y, int z, int side) {
/* 72 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_147437_c(int x, int y, int z) {
/* 77 */     return !(this.world.func_147438_o(x, y, z) instanceof IPipeTile);
/*    */   }
/*    */ 
/*    */   
/*    */   public BiomeGenBase func_72807_a(int x, int z) {
/* 82 */     return this.world.func_72807_a(x, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_72800_K() {
/* 87 */     return this.world.func_72800_K();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_72806_N() {
/* 92 */     return this.world.func_72806_N();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean def) {
/* 97 */     return this.world.isSideSolid(x, y, z, side, def);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\FacadeBlockAccess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
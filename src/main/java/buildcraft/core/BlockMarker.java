/*     */ package buildcraft.core;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.core.lib.block.BlockBuildCraft;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockMarker
/*     */   extends BlockBuildCraft
/*     */ {
/*     */   public BlockMarker() {
/*  28 */     super(Material.field_151594_q);
/*     */     
/*  30 */     func_149715_a(0.5F);
/*  31 */     func_149711_c(0.0F);
/*  32 */     func_149647_a(BCCreativeTab.get("main"));
/*     */   }
/*     */   
/*     */   public static boolean canPlaceTorch(World world, int x, int y, int z, ForgeDirection side) {
/*  36 */     Block block = world.func_147439_a(x, y, z);
/*  37 */     return (block != null && ((block.func_149686_d() && block.func_149662_c()) || block.isSideSolid((IBlockAccess)world, x, y, z, side)));
/*     */   }
/*     */   
/*     */   private AxisAlignedBB getBoundingBox(int meta) {
/*  41 */     double w = 0.15D;
/*  42 */     double h = 0.65D;
/*     */     
/*  44 */     ForgeDirection dir = ForgeDirection.getOrientation(meta);
/*  45 */     switch (dir) {
/*     */       case DOWN:
/*  47 */         return AxisAlignedBB.func_72330_a(0.5D - w, 1.0D - h, 0.5D - w, 0.5D + w, 1.0D, 0.5D + w);
/*     */       case UP:
/*  49 */         return AxisAlignedBB.func_72330_a(0.5D - w, 0.0D, 0.5D - w, 0.5D + w, h, 0.5D + w);
/*     */       case SOUTH:
/*  51 */         return AxisAlignedBB.func_72330_a(0.5D - w, 0.5D - w, 0.0D, 0.5D + w, 0.5D + w, h);
/*     */       case NORTH:
/*  53 */         return AxisAlignedBB.func_72330_a(0.5D - w, 0.5D - w, 1.0D - h, 0.5D + w, 0.5D + w, 1.0D);
/*     */       case EAST:
/*  55 */         return AxisAlignedBB.func_72330_a(0.0D, 0.5D - w, 0.5D - w, h, 0.5D + w, 0.5D + w);
/*     */     } 
/*  57 */     return AxisAlignedBB.func_72330_a(1.0D - h, 0.5D - w, 0.5D - w, 1.0D, 0.5D + w, 0.5D + w);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149633_g(World world, int x, int y, int z) {
/*  63 */     int meta = world.func_72805_g(x, y, z);
/*  64 */     AxisAlignedBB bBox = getBoundingBox(meta);
/*  65 */     bBox.func_72317_d(x, y, z);
/*  66 */     return bBox;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149719_a(IBlockAccess world, int x, int y, int z) {
/*  71 */     int meta = world.func_72805_g(x, y, z);
/*  72 */     AxisAlignedBB bb = getBoundingBox(meta);
/*  73 */     func_149676_a((float)bb.field_72340_a, (float)bb.field_72338_b, (float)bb.field_72339_c, (float)bb.field_72336_d, (float)bb.field_72337_e, (float)bb.field_72334_f);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/*  78 */     return BuildCraftCore.markerModel;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int metadata) {
/*  83 */     return (TileEntity)new TileMarker();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/*  88 */     if (super.func_149727_a(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
/*  89 */       return true;
/*     */     }
/*     */     
/*  92 */     if (entityplayer.field_71071_by.func_70448_g() != null && entityplayer.field_71071_by
/*  93 */       .func_70448_g().func_77973_b() instanceof buildcraft.api.items.IMapLocation) {
/*  94 */       return false;
/*     */     }
/*     */     
/*  97 */     if (entityplayer.func_70093_af()) {
/*  98 */       return false;
/*     */     }
/*     */     
/* 101 */     TileEntity tile = world.func_147438_o(i, j, k);
/* 102 */     if (tile instanceof TileMarker) {
/* 103 */       ((TileMarker)tile).tryConnection();
/* 104 */       return true;
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block block) {
/* 126 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 127 */     if (tile instanceof TileMarker) {
/* 128 */       ((TileMarker)tile).updateSignals();
/*     */     }
/* 130 */     dropTorchIfCantStay(world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149707_d(World world, int x, int y, int z, int side) {
/* 135 */     ForgeDirection dir = ForgeDirection.getOrientation(side);
/* 136 */     return canPlaceTorch(world, x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149660_a(World world, int x, int y, int z, int side, float par6, float par7, float par8, int meta) {
/* 141 */     return side;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149726_b(World world, int x, int y, int z) {
/* 146 */     super.func_149726_b(world, x, y, z);
/* 147 */     dropTorchIfCantStay(world, x, y, z);
/*     */   }
/*     */   
/*     */   private void dropTorchIfCantStay(World world, int x, int y, int z) {
/* 151 */     int meta = world.func_72805_g(x, y, z);
/* 152 */     if (!func_149707_d(world, x, y, z, meta) && world.func_147439_a(x, y, z) == this) {
/* 153 */       func_149697_b(world, x, y, z, 0, 0);
/* 154 */       world.func_147468_f(x, y, z);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\BlockMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
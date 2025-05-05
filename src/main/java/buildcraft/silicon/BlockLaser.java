/*     */ package buildcraft.silicon;
/*     */ 
/*     */ import buildcraft.core.BCCreativeTab;
/*     */ import buildcraft.core.lib.block.BlockBuildCraft;
/*     */ import buildcraft.core.lib.render.ICustomHighlight;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
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
/*     */ public class BlockLaser
/*     */   extends BlockBuildCraft
/*     */   implements ICustomHighlight
/*     */ {
/*  34 */   private static final AxisAlignedBB[][] boxes = new AxisAlignedBB[][] {
/*  35 */       { AxisAlignedBB.func_72330_a(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D), AxisAlignedBB.func_72330_a(0.3125D, 0.1875D, 0.3125D, 0.6875D, 0.75D, 0.6875D)
/*  36 */       }, { AxisAlignedBB.func_72330_a(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), AxisAlignedBB.func_72330_a(0.3125D, 0.25D, 0.3125D, 0.6875D, 0.8125D, 0.6875D)
/*  37 */       }, { AxisAlignedBB.func_72330_a(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D), AxisAlignedBB.func_72330_a(0.3125D, 0.3125D, 0.1875D, 0.6875D, 0.6875D, 0.75D)
/*  38 */       }, { AxisAlignedBB.func_72330_a(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D), AxisAlignedBB.func_72330_a(0.3125D, 0.3125D, 0.25D, 0.6875D, 0.6875D, 0.8125D)
/*  39 */       }, { AxisAlignedBB.func_72330_a(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), AxisAlignedBB.func_72330_a(0.1875D, 0.3125D, 0.3125D, 0.75D, 0.6875D, 0.6875D)
/*  40 */       }, { AxisAlignedBB.func_72330_a(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D), AxisAlignedBB.func_72330_a(0.25D, 0.3125D, 0.3125D, 0.8125D, 0.6875D, 0.6875D) }
/*     */     };
/*     */   
/*     */   public BlockLaser() {
/*  44 */     super(Material.field_151573_f);
/*  45 */     func_149711_c(10.0F);
/*  46 */     func_149647_a((CreativeTabs)BCCreativeTab.get("main"));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB[] getBoxes(World wrd, int x, int y, int z, EntityPlayer player) {
/*  51 */     return boxes[wrd.func_72805_g(x, y, z)];
/*     */   }
/*     */ 
/*     */   
/*     */   public double getExpansion() {
/*  56 */     return 0.0075D;
/*     */   }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition func_149731_a(World wrd, int x, int y, int z, Vec3 origin, Vec3 direction) {
/*  61 */     AxisAlignedBB[] aabbs = boxes[wrd.func_72805_g(x, y, z)];
/*  62 */     MovingObjectPosition closest = null;
/*  63 */     for (AxisAlignedBB aabb : aabbs) {
/*  64 */       MovingObjectPosition mop = aabb.func_72325_c(x, y, z).func_72327_a(origin, direction);
/*  65 */       if (mop != null) {
/*  66 */         if (closest != null && mop.field_72307_f.func_72438_d(origin) < closest.field_72307_f.func_72438_d(origin)) {
/*  67 */           closest = mop;
/*     */         } else {
/*  69 */           closest = mop;
/*     */         } 
/*     */       }
/*     */     } 
/*  73 */     if (closest != null) {
/*  74 */       closest.field_72311_b = x;
/*  75 */       closest.field_72312_c = y;
/*  76 */       closest.field_72309_d = z;
/*     */     } 
/*  78 */     return closest;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149743_a(World wrd, int x, int y, int z, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity ent) {
/*  84 */     AxisAlignedBB[] aabbs = boxes[wrd.func_72805_g(x, y, z)];
/*  85 */     for (AxisAlignedBB aabb : aabbs) {
/*  86 */       AxisAlignedBB aabbTmp = aabb.func_72325_c(x, y, z);
/*  87 */       if (mask.func_72326_a(aabbTmp)) {
/*  88 */         list.add(aabbTmp);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/*  95 */     return SiliconProxy.laserBlockModel;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int metadata) {
/* 110 */     return (TileEntity)new TileLaser();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149673_e(IBlockAccess access, int x, int y, int z, int side) {
/* 116 */     return func_149691_a(side, access.func_72805_g(x, y, z));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int i, int j) {
/* 122 */     if (i == (j ^ 0x1))
/* 123 */       return this.icons[0][0]; 
/* 124 */     if (i == j) {
/* 125 */       return this.icons[0][1];
/*     */     }
/* 127 */     return this.icons[0][2];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149660_a(World world, int x, int y, int z, int side, float par6, float par7, float par8, int meta) {
/* 133 */     super.func_149660_a(world, x, y, z, side, par6, par7, par8, meta);
/*     */     
/* 135 */     int retMeta = meta;
/*     */     
/* 137 */     if (side <= 6) {
/* 138 */       retMeta = side;
/*     */     }
/*     */     
/* 141 */     return retMeta;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149646_a(IBlockAccess world, int x, int y, int z, int side) {
/* 151 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\BlockLaser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
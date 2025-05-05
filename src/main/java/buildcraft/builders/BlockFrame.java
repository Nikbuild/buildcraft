/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.core.internal.IFramePipeConnection;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentSkipListSet;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
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
/*     */ 
/*     */ public class BlockFrame
/*     */   extends Block
/*     */   implements IFramePipeConnection
/*     */ {
/*  39 */   private static final ThreadLocal<Boolean> isRemovingFrames = new ThreadLocal<Boolean>();
/*     */   
/*     */   public BlockFrame() {
/*  42 */     super(Material.field_151592_s);
/*  43 */     func_149711_c(0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int x, int y, int z, Block block, int meta) {
/*  48 */     if (world.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  52 */     if (isRemovingFrames.get() == null) {
/*  53 */       removeNeighboringFrames(world, x, y, z);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeNeighboringFrames(World world, int x, int y, int z) {
/*  58 */     isRemovingFrames.set(Boolean.valueOf(true));
/*     */     
/*  60 */     Set<BlockIndex> frameCoords = new ConcurrentSkipListSet<BlockIndex>();
/*  61 */     frameCoords.add(new BlockIndex(x, y, z));
/*     */     
/*  63 */     while (frameCoords.size() > 0) {
/*  64 */       Iterator<BlockIndex> frameCoordIterator = frameCoords.iterator();
/*  65 */       while (frameCoordIterator.hasNext()) {
/*  66 */         BlockIndex i = frameCoordIterator.next();
/*  67 */         for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*  68 */           Block nBlock = world.func_147439_a(i.x + dir.offsetX, i.y + dir.offsetY, i.z + dir.offsetZ);
/*  69 */           if (nBlock == this) {
/*  70 */             world.func_147468_f(i.x + dir.offsetX, i.y + dir.offsetY, i.z + dir.offsetZ);
/*  71 */             frameCoords.add(new BlockIndex(i.x + dir.offsetX, i.y + dir.offsetY, i.z + dir.offsetZ));
/*     */           } 
/*     */         } 
/*  74 */         frameCoordIterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/*  78 */     isRemovingFrames.remove();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item func_149650_a(int i, Random random, int j) {
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
/*  98 */     return new ArrayList<ItemStack>();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/* 103 */     return BuilderProxy.frameRenderId;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
/* 108 */     float xMin = 0.25F, xMax = 0.75F, yMin = 0.25F, yMax = 0.75F, zMin = 0.25F, zMax = 0.75F;
/*     */     
/* 110 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i - 1, j, k)) {
/* 111 */       xMin = 0.0F;
/*     */     }
/*     */     
/* 114 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i + 1, j, k)) {
/* 115 */       xMax = 1.0F;
/*     */     }
/*     */     
/* 118 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i, j - 1, k)) {
/* 119 */       yMin = 0.0F;
/*     */     }
/*     */     
/* 122 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i, j + 1, k)) {
/* 123 */       yMax = 1.0F;
/*     */     }
/*     */     
/* 126 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i, j, k - 1)) {
/* 127 */       zMin = 0.0F;
/*     */     }
/*     */     
/* 130 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i, j, k + 1)) {
/* 131 */       zMax = 1.0F;
/*     */     }
/*     */     
/* 134 */     return AxisAlignedBB.func_72330_a(i + xMin, j + yMin, k + zMin, i + xMax, j + yMax, k + zMax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149633_g(World world, int i, int j, int k) {
/* 141 */     return func_149668_a(world, i, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149743_a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity) {
/* 147 */     func_149676_a(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
/* 148 */     super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*     */     
/* 150 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i - 1, j, k)) {
/* 151 */       func_149676_a(0.0F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
/* 152 */       super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*     */     } 
/*     */     
/* 155 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i + 1, j, k)) {
/* 156 */       func_149676_a(0.25F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
/* 157 */       super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*     */     } 
/*     */     
/* 160 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i, j - 1, k)) {
/* 161 */       func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 0.75F, 0.75F);
/* 162 */       super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*     */     } 
/*     */     
/* 165 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i, j + 1, k)) {
/* 166 */       func_149676_a(0.25F, 0.25F, 0.25F, 0.75F, 1.0F, 0.75F);
/* 167 */       super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*     */     } 
/*     */     
/* 170 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i, j, k - 1)) {
/* 171 */       func_149676_a(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.75F);
/* 172 */       super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*     */     } 
/*     */     
/* 175 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i, j, k + 1)) {
/* 176 */       func_149676_a(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 1.0F);
/* 177 */       super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*     */     } 
/*     */     
/* 180 */     func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition func_149731_a(World world, int i, int j, int k, Vec3 vec3d, Vec3 vec3d1) {
/* 185 */     float xMin = 0.25F, xMax = 0.75F, yMin = 0.25F, yMax = 0.75F, zMin = 0.25F, zMax = 0.75F;
/*     */     
/* 187 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i - 1, j, k)) {
/* 188 */       xMin = 0.0F;
/*     */     }
/*     */     
/* 191 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i + 1, j, k)) {
/* 192 */       xMax = 1.0F;
/*     */     }
/*     */     
/* 195 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i, j - 1, k)) {
/* 196 */       yMin = 0.0F;
/*     */     }
/*     */     
/* 199 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i, j + 1, k)) {
/* 200 */       yMax = 1.0F;
/*     */     }
/*     */     
/* 203 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i, j, k - 1)) {
/* 204 */       zMin = 0.0F;
/*     */     }
/*     */     
/* 207 */     if (Utils.checkLegacyPipesConnections((IBlockAccess)world, i, j, k, i, j, k + 1)) {
/* 208 */       zMax = 1.0F;
/*     */     }
/*     */     
/* 211 */     func_149676_a(xMin, yMin, zMin, xMax, yMax, zMax);
/*     */     
/* 213 */     MovingObjectPosition r = super.func_149731_a(world, i, j, k, vec3d, vec3d1);
/*     */     
/* 215 */     func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 217 */     return r;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPipeConnected(IBlockAccess blockAccess, int x1, int y1, int z1, int x2, int y2, int z2) {
/* 222 */     return (blockAccess.func_147439_a(x2, y2, z2) == this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149666_a(Item item, CreativeTabs tab, List<ItemStack> list) {
/* 228 */     list.add(new ItemStack(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149651_a(IIconRegister register) {
/* 233 */     this.field_149761_L = register.func_94245_a("buildcraftbuilders:frameBlock/default");
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\BlockFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
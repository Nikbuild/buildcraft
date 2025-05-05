/*     */ package buildcraft.core.lib.block;
/*     */ 
/*     */ import buildcraft.core.lib.render.EntityDropParticleFX;
/*     */ import buildcraft.core.lib.utils.ResourceUtils;
/*     */ import cpw.mods.fml.client.FMLClientHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.BlockFluidClassic;
/*     */ import net.minecraftforge.fluids.Fluid;
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
/*     */ public class BlockBuildCraftFluid
/*     */   extends BlockFluidClassic
/*     */ {
/*     */   protected float particleRed;
/*     */   protected float particleGreen;
/*     */   protected float particleBlue;
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected IIcon[] theIcon;
/*     */   protected boolean flammable;
/*     */   protected boolean dense = false;
/*  44 */   protected int flammability = 0;
/*     */   private MapColor mapColor;
/*     */   
/*     */   public BlockBuildCraftFluid(Fluid fluid, Material material, MapColor iMapColor) {
/*  48 */     super(fluid, material);
/*     */     
/*  50 */     this.mapColor = iMapColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_149691_a(int side, int meta) {
/*  55 */     return (side != 0 && side != 1) ? this.theIcon[1] : this.theIcon[0];
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister iconRegister) {
/*  61 */     String prefix = ResourceUtils.getObjectPrefix(Block.field_149771_c.func_148750_c(this));
/*  62 */     prefix = prefix.substring(0, prefix.indexOf(":") + 1) + "fluids/";
/*  63 */     this
/*  64 */       .theIcon = new IIcon[] { iconRegister.func_94245_a(prefix + this.fluidName + "_still"), iconRegister.func_94245_a(prefix + this.fluidName + "_flow") };
/*     */   }
/*     */   
/*     */   public static boolean isFluidExplosive(World world, int x, int z) {
/*  68 */     return (world.field_73011_w.field_76574_g == -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149674_a(World world, int x, int y, int z, Random rand) {
/*  73 */     if (this.flammable && isFluidExplosive(world, x, z)) {
/*  74 */       world.func_147465_d(x, y, z, Blocks.field_150350_a, 0, 2);
/*  75 */       world.func_72885_a(null, x, y, z, 4.0F, true, true);
/*     */     } else {
/*  77 */       super.func_149674_a(world, x, y, z, rand);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
/*  83 */     world.func_147465_d(x, y, z, Blocks.field_150350_a, 0, 2);
/*  84 */     for (ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS) {
/*  85 */       Block block = world.func_147439_a(x + fd.offsetX, y + fd.offsetY, z + fd.offsetZ);
/*  86 */       if (block instanceof BlockBuildCraftFluid) {
/*  87 */         world.func_147464_a(x + fd.offsetX, y + fd.offsetY, z + fd.offsetZ, block, 2);
/*     */       } else {
/*  89 */         world.func_147460_e(x + fd.offsetX, y + fd.offsetY, z + fd.offsetZ, block);
/*     */       } 
/*     */     } 
/*  92 */     func_149723_a(world, x, y, z, explosion);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149670_a(World world, int x, int y, int z, Entity entity) {
/*  98 */     if (!this.dense || entity == null) {
/*     */       return;
/*     */     }
/*     */     
/* 102 */     entity.field_70181_x = Math.min(0.0D, entity.field_70181_x);
/*     */     
/* 104 */     if (entity.field_70181_x < -0.05D) {
/* 105 */       entity.field_70181_x *= 0.05D;
/*     */     }
/*     */     
/* 108 */     entity.field_70159_w = Math.max(-0.05D, Math.min(0.05D, entity.field_70159_w * 0.05D));
/* 109 */     entity.field_70181_x -= 0.05D;
/* 110 */     entity.field_70179_y = Math.max(-0.05D, Math.min(0.05D, entity.field_70179_y * 0.05D));
/*     */   }
/*     */   
/*     */   public BlockBuildCraftFluid setDense(boolean dense) {
/* 114 */     this.dense = dense;
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   public BlockBuildCraftFluid setFlammable(boolean flammable) {
/* 119 */     this.flammable = flammable;
/* 120 */     return this;
/*     */   }
/*     */   
/*     */   public BlockBuildCraftFluid setFlammability(int flammability) {
/* 124 */     this.flammability = flammability;
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
/* 130 */     return this.flammable ? 300 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
/* 135 */     return this.flammability;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
/* 140 */     return this.flammable;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {
/* 145 */     return (this.flammable && this.flammability == 0);
/*     */   }
/*     */   
/*     */   public BlockBuildCraftFluid setParticleColor(float particleRed, float particleGreen, float particleBlue) {
/* 149 */     this.particleRed = particleRed;
/* 150 */     this.particleGreen = particleGreen;
/* 151 */     this.particleBlue = particleBlue;
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149734_b(World world, int x, int y, int z, Random rand) {
/* 158 */     super.func_149734_b(world, x, y, z, rand);
/*     */     
/* 160 */     if (rand.nextInt(10) == 0 && 
/* 161 */       World.func_147466_a((IBlockAccess)world, x, y - 1, z) && 
/* 162 */       !world.func_147439_a(x, y - 2, z).func_149688_o().func_76230_c()) {
/*     */       
/* 164 */       double px = (x + rand.nextFloat());
/* 165 */       double py = y - 1.05D;
/* 166 */       double pz = (z + rand.nextFloat());
/*     */       
/* 168 */       EntityDropParticleFX entityDropParticleFX = new EntityDropParticleFX(world, px, py, pz, this.particleRed, this.particleGreen, this.particleBlue);
/* 169 */       (FMLClientHandler.instance().getClient()).field_71452_i.func_78873_a((EntityFX)entityDropParticleFX);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
/* 175 */     if (world.func_147439_a(x, y, z).func_149688_o().func_76224_d()) {
/* 176 */       return false;
/*     */     }
/* 178 */     return super.canDisplace(world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean displaceIfPossible(World world, int x, int y, int z) {
/* 183 */     if (world.func_147439_a(x, y, z).func_149688_o().func_76224_d()) {
/* 184 */       return false;
/*     */     }
/* 186 */     return super.displaceIfPossible(world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public MapColor func_149728_f(int meta) {
/* 191 */     return this.mapColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149659_a(Explosion explosion) {
/* 196 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\block\BlockBuildCraftFluid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
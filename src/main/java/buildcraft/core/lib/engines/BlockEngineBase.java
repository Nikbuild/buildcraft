/*     */ package buildcraft.core.lib.engines;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.events.BlockInteractionEvent;
/*     */ import buildcraft.core.lib.block.BlockBuildCraft;
/*     */ import buildcraft.core.lib.render.ICustomHighlight;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
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
/*     */ public abstract class BlockEngineBase
/*     */   extends BlockBuildCraft
/*     */   implements ICustomHighlight
/*     */ {
/*  39 */   private static final AxisAlignedBB[][] boxes = new AxisAlignedBB[][] {
/*  40 */       { AxisAlignedBB.func_72330_a(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D), AxisAlignedBB.func_72330_a(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D)
/*  41 */       }, { AxisAlignedBB.func_72330_a(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), AxisAlignedBB.func_72330_a(0.25D, 0.5D, 0.25D, 0.75D, 1.0D, 0.75D)
/*  42 */       }, { AxisAlignedBB.func_72330_a(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D), AxisAlignedBB.func_72330_a(0.25D, 0.25D, 0.0D, 0.75D, 0.75D, 0.5D)
/*  43 */       }, { AxisAlignedBB.func_72330_a(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D), AxisAlignedBB.func_72330_a(0.25D, 0.25D, 0.5D, 0.75D, 0.75D, 1.0D)
/*  44 */       }, { AxisAlignedBB.func_72330_a(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), AxisAlignedBB.func_72330_a(0.0D, 0.25D, 0.25D, 0.5D, 0.75D, 0.75D)
/*  45 */       }, { AxisAlignedBB.func_72330_a(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D), AxisAlignedBB.func_72330_a(0.5D, 0.25D, 0.25D, 1.0D, 0.75D, 0.75D) }
/*     */     };
/*     */   
/*     */   public BlockEngineBase() {
/*  49 */     super(Material.field_151573_f);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String getTexturePrefix(int paramInt, boolean paramBoolean);
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getIconAbsolute(int side, int metadata) {
/*  57 */     return (this.icons[metadata] == null) ? this.icons[0][0] : this.icons[metadata][0];
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister register) {
/*  63 */     this.icons = new IIcon[16][];
/*  64 */     for (int meta = 0; meta < 16; meta++) {
/*  65 */       String prefix = getTexturePrefix(meta, false);
/*  66 */       if (prefix != null) {
/*  67 */         this.icons[meta] = new IIcon[1];
/*  68 */         this.icons[meta][0] = register.func_94245_a(prefix + "/icon");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/*  85 */     return BuildCraftCore.blockByEntityModel;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/*  90 */     TileEntity tile = world.func_147438_o(x, y, z);
/*     */     
/*  92 */     if (tile instanceof TileEngineBase) {
/*  93 */       return (((TileEngineBase)tile).orientation.getOpposite() == side);
/*     */     }
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
/* 101 */     TileEntity tile = world.func_147438_o(x, y, z);
/*     */     
/* 103 */     if (tile instanceof TileEngineBase) {
/* 104 */       return ((TileEngineBase)tile).switchOrientation(false);
/*     */     }
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer player, int side, float par7, float par8, float par9) {
/* 112 */     TileEntity tile = world.func_147438_o(i, j, k);
/*     */     
/* 114 */     BlockInteractionEvent event = new BlockInteractionEvent(player, (Block)this, world.func_72805_g(i, j, k));
/* 115 */     FMLCommonHandler.instance().bus().post((Event)event);
/* 116 */     if (event.isCanceled()) {
/* 117 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 121 */     if (player.func_71045_bC() != null && 
/* 122 */       player.func_71045_bC().func_77973_b() instanceof buildcraft.api.transport.IItemPipe) {
/* 123 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 127 */     if (tile instanceof TileEngineBase) {
/* 128 */       return ((TileEngineBase)tile).onBlockActivated(player, ForgeDirection.getOrientation(side));
/*     */     }
/*     */     
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149743_a(World wrd, int x, int y, int z, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity ent) {
/* 137 */     TileEntity tile = wrd.func_147438_o(x, y, z);
/* 138 */     if (tile instanceof TileEngineBase) {
/* 139 */       AxisAlignedBB[] aabbs = boxes[((TileEngineBase)tile).orientation.ordinal()];
/* 140 */       for (AxisAlignedBB aabb : aabbs) {
/* 141 */         AxisAlignedBB aabbTmp = aabb.func_72325_c(x, y, z);
/* 142 */         if (mask.func_72326_a(aabbTmp)) {
/* 143 */           list.add(aabbTmp);
/*     */         }
/*     */       } 
/*     */     } else {
/* 147 */       super.func_149743_a(wrd, x, y, z, mask, list, ent);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB[] getBoxes(World wrd, int x, int y, int z, EntityPlayer player) {
/* 153 */     TileEntity tile = wrd.func_147438_o(x, y, z);
/* 154 */     if (tile instanceof TileEngineBase) {
/* 155 */       return boxes[((TileEngineBase)tile).orientation.ordinal()];
/*     */     }
/* 157 */     return new AxisAlignedBB[] { AxisAlignedBB.func_72330_a(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getExpansion() {
/* 163 */     return 0.0075D;
/*     */   }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition func_149731_a(World wrd, int x, int y, int z, Vec3 origin, Vec3 direction) {
/* 168 */     TileEntity tile = wrd.func_147438_o(x, y, z);
/* 169 */     if (tile instanceof TileEngineBase) {
/* 170 */       AxisAlignedBB[] aabbs = boxes[((TileEngineBase)tile).orientation.ordinal()];
/* 171 */       MovingObjectPosition closest = null;
/* 172 */       for (AxisAlignedBB aabb : aabbs) {
/* 173 */         MovingObjectPosition mop = aabb.func_72325_c(x, y, z).func_72327_a(origin, direction);
/* 174 */         if (mop != null) {
/* 175 */           if (closest != null && mop.field_72307_f.func_72438_d(origin) < closest.field_72307_f.func_72438_d(origin)) {
/* 176 */             closest = mop;
/*     */           } else {
/* 178 */             closest = mop;
/*     */           } 
/*     */         }
/*     */       } 
/* 182 */       if (closest != null) {
/* 183 */         closest.field_72311_b = x;
/* 184 */         closest.field_72312_c = y;
/* 185 */         closest.field_72309_d = z;
/*     */       } 
/* 187 */       return closest;
/*     */     } 
/* 189 */     return super.func_149731_a(wrd, x, y, z, origin, direction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149714_e(World world, int x, int y, int z, int par5) {
/* 195 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 196 */     if (tile instanceof TileEngineBase) {
/* 197 */       TileEngineBase engine = (TileEngineBase)tile;
/* 198 */       engine.orientation = ForgeDirection.UP;
/* 199 */       if (!engine.isOrientationValid()) {
/* 200 */         engine.switchOrientation(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149692_a(int i) {
/* 207 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149734_b(World world, int i, int j, int k, Random random) {
/* 213 */     TileEntity tile = world.func_147438_o(i, j, k);
/*     */     
/* 215 */     if (!(tile instanceof TileEngineBase)) {
/*     */       return;
/*     */     }
/*     */     
/* 219 */     if (((TileEngineBase)tile).getEnergyStage() == TileEngineBase.EnergyStage.OVERHEAT) {
/* 220 */       for (int f = 0; f < 16; f++) {
/* 221 */         world.func_72869_a("smoke", (i + 0.4F + random.nextFloat() * 0.2F), (j + random
/* 222 */             .nextFloat() * 0.5F), (k + 0.4F + random
/* 223 */             .nextFloat() * 0.2F), (random
/* 224 */             .nextFloat() * 0.04F - 0.02F), (random
/* 225 */             .nextFloat() * 0.05F + 0.02F), (random
/* 226 */             .nextFloat() * 0.04F - 0.02F));
/*     */       }
/* 228 */     } else if (((TileEngineBase)tile).isBurning()) {
/* 229 */       float f = i + 0.5F;
/* 230 */       float f1 = j + 0.0F + random.nextFloat() * 6.0F / 16.0F;
/* 231 */       float f2 = k + 0.5F;
/* 232 */       float f3 = 0.52F;
/* 233 */       float f4 = random.nextFloat() * 0.6F - 0.3F;
/*     */       
/* 235 */       world.func_72869_a("reddust", (f - f3), f1, (f2 + f4), 0.0D, 0.0D, 0.0D);
/* 236 */       world.func_72869_a("reddust", (f + f3), f1, (f2 + f4), 0.0D, 0.0D, 0.0D);
/* 237 */       world.func_72869_a("reddust", (f + f4), f1, (f2 - f3), 0.0D, 0.0D, 0.0D);
/* 238 */       world.func_72869_a("reddust", (f + f4), f1, (f2 + f3), 0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block block) {
/* 244 */     TileEntity tile = world.func_147438_o(x, y, z);
/*     */     
/* 246 */     if (tile instanceof TileEngineBase) {
/* 247 */       ((TileEngineBase)tile).onNeighborUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int metadata) {
/* 253 */     return null;
/*     */   }
/*     */   
/*     */   public abstract String getUnlocalizedName(int paramInt);
/*     */   
/*     */   public abstract TileEntity createTileEntity(World paramWorld, int paramInt);
/*     */   
/*     */   public abstract boolean hasEngine(int paramInt);
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\engines\BlockEngineBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
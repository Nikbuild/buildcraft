/*     */ package buildcraft.factory;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.core.BCCreativeTab;
/*     */ import buildcraft.core.lib.block.BlockBuildCraft;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.IFluidContainerItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockTank
/*     */   extends BlockBuildCraft
/*     */ {
/*     */   private static final boolean DEBUG_MODE = false;
/*     */   private IIcon textureStackedSide;
/*     */   
/*     */   public BlockTank() {
/*  39 */     super(Material.field_151592_s);
/*  40 */     func_149676_a(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
/*  41 */     func_149711_c(0.5F);
/*  42 */     func_149647_a((CreativeTabs)BCCreativeTab.get("main"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int x, int y, int z, Block block, int par6) {
/*  47 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  48 */     if (tile != null && tile instanceof TileTank) {
/*  49 */       TileTank tank = (TileTank)tile;
/*  50 */       tank.onBlockBreak();
/*     */     } 
/*     */     
/*  53 */     TileEntity tileAbove = world.func_147438_o(x, y + 1, z);
/*  54 */     TileEntity tileBelow = world.func_147438_o(x, y - 1, z);
/*     */     
/*  56 */     super.func_149749_a(world, x, y, z, block, par6);
/*     */     
/*  58 */     if (tileAbove instanceof TileTank) {
/*  59 */       ((TileTank)tileAbove).updateComparators();
/*     */     }
/*     */     
/*  62 */     if (tileBelow instanceof TileTank) {
/*  63 */       ((TileTank)tileBelow).updateComparators();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int metadata) {
/*  79 */     return (TileEntity)new TileTank();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getIconAbsolute(IBlockAccess iblockaccess, int i, int j, int k, int side, int metadata) {
/*  85 */     if (side >= 2 && iblockaccess.func_147439_a(i, j - 1, k) instanceof BlockTank) {
/*  86 */       return this.textureStackedSide;
/*     */     }
/*  88 */     return getIconAbsolute(side, metadata);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/*  94 */     if (super.func_149727_a(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
/*  95 */       return true;
/*     */     }
/*     */     
/*  98 */     ItemStack current = entityplayer.field_71071_by.func_70448_g();
/*     */     
/* 100 */     if (current != null) {
/* 101 */       TileEntity tile = world.func_147438_o(i, j, k);
/*     */       
/* 103 */       if (tile instanceof TileTank) {
/* 104 */         TileTank tank = (TileTank)tile;
/*     */         
/* 106 */         if (FluidContainerRegistry.isContainer(current)) {
/* 107 */           FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(current);
/*     */           
/* 109 */           if (liquid != null) {
/* 110 */             int qty = tank.fill(ForgeDirection.UNKNOWN, liquid, true);
/*     */             
/* 112 */             if (qty != 0 && !BuildCraftCore.debugWorldgen && !entityplayer.field_71075_bZ.field_75098_d) {
/* 113 */               if (current.field_77994_a > 1) {
/* 114 */                 if (!entityplayer.field_71071_by.func_70441_a(FluidContainerRegistry.drainFluidContainer(current))) {
/* 115 */                   entityplayer.func_71019_a(FluidContainerRegistry.drainFluidContainer(current), false);
/*     */                 }
/*     */                 
/* 118 */                 entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, InvUtils.consumeItem(current));
/*     */               } else {
/* 120 */                 entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, FluidContainerRegistry.drainFluidContainer(current));
/*     */               } 
/*     */             }
/*     */             
/* 124 */             return true;
/*     */           } 
/*     */ 
/*     */           
/* 128 */           FluidStack available = (tank.getTankInfo(ForgeDirection.UNKNOWN)[0]).fluid;
/*     */           
/* 130 */           if (available != null) {
/* 131 */             ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, current);
/*     */             
/* 133 */             liquid = FluidContainerRegistry.getFluidForFilledItem(filled);
/*     */             
/* 135 */             if (liquid != null) {
/* 136 */               if (!BuildCraftCore.debugWorldgen && !entityplayer.field_71075_bZ.field_75098_d) {
/* 137 */                 if (current.field_77994_a > 1) {
/* 138 */                   if (!entityplayer.field_71071_by.func_70441_a(filled)) {
/* 139 */                     return false;
/*     */                   }
/* 141 */                   entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, InvUtils.consumeItem(current));
/*     */                 } else {
/*     */                   
/* 144 */                   entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, InvUtils.consumeItem(current));
/* 145 */                   entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, filled);
/*     */                 } 
/*     */               }
/*     */               
/* 149 */               tank.drain(ForgeDirection.UNKNOWN, liquid.amount, true);
/*     */               
/* 151 */               return true;
/*     */             }
/*     */           
/*     */           } 
/* 155 */         } else if (current.func_77973_b() instanceof IFluidContainerItem) {
/* 156 */           if (current.field_77994_a != 1) {
/* 157 */             return false;
/*     */           }
/*     */           
/* 160 */           if (!world.field_72995_K) {
/* 161 */             IFluidContainerItem container = (IFluidContainerItem)current.func_77973_b();
/* 162 */             FluidStack liquid = container.getFluid(current);
/* 163 */             FluidStack tankLiquid = (tank.getTankInfo(ForgeDirection.UNKNOWN)[0]).fluid;
/* 164 */             boolean mustDrain = (liquid == null || liquid.amount == 0);
/* 165 */             boolean mustFill = (tankLiquid == null || tankLiquid.amount == 0);
/* 166 */             if (!mustDrain || !mustFill)
/*     */             {
/* 168 */               if (mustDrain || !entityplayer.func_70093_af()) {
/* 169 */                 liquid = tank.drain(ForgeDirection.UNKNOWN, 1000, false);
/* 170 */                 int qtyToFill = container.fill(current, liquid, true);
/* 171 */                 tank.drain(ForgeDirection.UNKNOWN, qtyToFill, true);
/* 172 */               } else if ((mustFill || entityplayer.func_70093_af()) && 
/* 173 */                 liquid.amount > 0) {
/* 174 */                 int qty = tank.fill(ForgeDirection.UNKNOWN, liquid, false);
/* 175 */                 tank.fill(ForgeDirection.UNKNOWN, container.drain(current, qty, true), true);
/*     */               } 
/*     */             }
/*     */           } 
/*     */           
/* 180 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
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
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149646_a(IBlockAccess world, int x, int y, int z, int side) {
/* 199 */     if (side <= 1) {
/* 200 */       return !(world.func_147439_a(x, y, z) instanceof BlockTank);
/*     */     }
/* 202 */     return super.func_149646_a(world, x, y, z, side);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister par1IconRegister) {
/* 209 */     super.func_149651_a(par1IconRegister);
/* 210 */     this.textureStackedSide = par1IconRegister.func_94245_a("buildcraftfactory:tankBlock/side_stacked");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightValue(IBlockAccess world, int x, int y, int z) {
/* 215 */     TileEntity tile = world.func_147438_o(x, y, z);
/*     */     
/* 217 */     if (tile instanceof TileTank) {
/* 218 */       TileTank tank = (TileTank)tile;
/* 219 */       return tank.getFluidLightLevel();
/*     */     } 
/*     */     
/* 222 */     return super.getLightValue(world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149740_M() {
/* 227 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149736_g(World world, int x, int y, int z, int side) {
/* 232 */     TileEntity tile = world.func_147438_o(x, y, z);
/*     */     
/* 234 */     if (tile instanceof TileTank) {
/* 235 */       TileTank tank = (TileTank)tile;
/* 236 */       return tank.getComparatorInputOverride();
/*     */     } 
/*     */     
/* 239 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\BlockTank.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
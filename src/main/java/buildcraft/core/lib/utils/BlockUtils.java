/*     */ package buildcraft.core.lib.utils;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S27PacketExplosion;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.world.ChunkPosition;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.event.ForgeEventFactory;
/*     */ import net.minecraftforge.event.world.BlockEvent;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.IFluidBlock;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BlockUtils
/*     */ {
/*     */   public static List<ItemStack> getItemStackFromBlock(WorldServer world, int i, int j, int k) {
/*  53 */     Block block = world.func_147439_a(i, j, k);
/*     */     
/*  55 */     if (block == null || block.isAir((IBlockAccess)world, i, j, k)) {
/*  56 */       return null;
/*     */     }
/*     */     
/*  59 */     int meta = world.func_72805_g(i, j, k);
/*     */     
/*  61 */     ArrayList<ItemStack> dropsList = block.getDrops((World)world, i, j, k, meta, 0);
/*  62 */     float dropChance = ForgeEventFactory.fireBlockHarvesting(dropsList, (World)world, block, i, j, k, meta, 0, 1.0F, false, CoreProxy.proxy
/*  63 */         .getBuildCraftPlayer(world).get());
/*     */     
/*  65 */     ArrayList<ItemStack> returnList = new ArrayList<ItemStack>();
/*  66 */     for (ItemStack s : dropsList) {
/*  67 */       if (world.field_73012_v.nextFloat() <= dropChance) {
/*  68 */         returnList.add(s);
/*     */       }
/*     */     } 
/*     */     
/*  72 */     return returnList;
/*     */   }
/*     */   
/*     */   public static boolean breakBlock(WorldServer world, int x, int y, int z) {
/*  76 */     return breakBlock(world, x, y, z, BuildCraftCore.itemLifespan * 20);
/*     */   }
/*     */   
/*     */   public static boolean breakBlock(WorldServer world, int x, int y, int z, int forcedLifespan) {
/*  80 */     List<ItemStack> items = new ArrayList<ItemStack>();
/*     */     
/*  82 */     if (breakBlock(world, x, y, z, items)) {
/*  83 */       for (ItemStack item : items) {
/*  84 */         dropItem(world, x, y, z, forcedLifespan, item);
/*     */       }
/*  86 */       return true;
/*     */     } 
/*  88 */     return false;
/*     */   }
/*     */   
/*     */   public static EntityPlayer getFakePlayerWithTool(WorldServer world, int x, int y, int z, ItemStack tool) {
/*  92 */     EntityPlayer player = CoreProxy.proxy.getBuildCraftPlayer(world, x, y, z).get();
/*  93 */     int i = 0;
/*     */     
/*  95 */     while (player.func_70694_bm() != tool && i < 9) {
/*  96 */       if (i > 0) {
/*  97 */         player.field_71071_by.func_70299_a(i - 1, null);
/*     */       }
/*     */       
/* 100 */       player.field_71071_by.func_70299_a(i, tool);
/* 101 */       i++;
/*     */     } 
/*     */     
/* 104 */     return player;
/*     */   }
/*     */   
/*     */   public static boolean harvestBlock(WorldServer world, int x, int y, int z, ItemStack tool) {
/* 108 */     Block block = world.func_147439_a(x, y, z);
/* 109 */     int meta = world.func_72805_g(x, y, z);
/*     */     
/* 111 */     EntityPlayer player = getFakePlayerWithTool(world, x, y, z, tool);
/*     */     
/* 113 */     BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(x, y, z, (World)world, world.func_147439_a(x, y, z), world.func_72805_g(x, y, z), player);
/* 114 */     MinecraftForge.EVENT_BUS.post((Event)breakEvent);
/*     */     
/* 116 */     if (breakEvent.isCanceled()) {
/* 117 */       return false;
/*     */     }
/*     */     
/* 120 */     if (!block.canHarvestBlock(player, meta)) {
/* 121 */       return false;
/*     */     }
/*     */     
/* 124 */     if (tool != null && tool.func_77973_b() != null && tool.func_77973_b().onBlockStartBreak(tool, x, y, z, player)) {
/* 125 */       return false;
/*     */     }
/*     */     
/* 128 */     block.func_149681_a((World)world, x, y, z, meta, player);
/* 129 */     block.func_149636_a((World)world, player, x, y, z, meta);
/* 130 */     world.func_147468_f(x, y, z);
/*     */     
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean breakBlock(WorldServer world, int x, int y, int z, List<ItemStack> drops) {
/* 137 */     BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(x, y, z, (World)world, world.func_147439_a(x, y, z), world.func_72805_g(x, y, z), CoreProxy.proxy.getBuildCraftPlayer(world).get());
/* 138 */     MinecraftForge.EVENT_BUS.post((Event)breakEvent);
/*     */     
/* 140 */     if (breakEvent.isCanceled()) {
/* 141 */       return false;
/*     */     }
/*     */     
/* 144 */     if (!world.func_147437_c(x, y, z) && !world.field_72995_K && world
/* 145 */       .func_82736_K().func_82766_b("doTileDrops")) {
/* 146 */       drops.addAll(getItemStackFromBlock(world, x, y, z));
/*     */     }
/* 148 */     world.func_147468_f(x, y, z);
/*     */     
/* 150 */     return true;
/*     */   }
/*     */   
/*     */   public static void dropItem(WorldServer world, int x, int y, int z, int forcedLifespan, ItemStack stack) {
/* 154 */     float var = 0.7F;
/* 155 */     double dx = (world.field_73012_v.nextFloat() * var) + (1.0F - var) * 0.5D;
/* 156 */     double dy = (world.field_73012_v.nextFloat() * var) + (1.0F - var) * 0.5D;
/* 157 */     double dz = (world.field_73012_v.nextFloat() * var) + (1.0F - var) * 0.5D;
/* 158 */     EntityItem entityitem = new EntityItem((World)world, x + dx, y + dy, z + dz, stack);
/*     */     
/* 160 */     entityitem.lifespan = forcedLifespan;
/* 161 */     entityitem.field_145804_b = 10;
/*     */     
/* 163 */     world.func_72838_d((Entity)entityitem);
/*     */   }
/*     */   
/*     */   public static boolean canChangeBlock(World world, int x, int y, int z) {
/* 167 */     return canChangeBlock(world.func_147439_a(x, y, z), world, x, y, z);
/*     */   }
/*     */   
/*     */   public static boolean canChangeBlock(Block block, World world, int x, int y, int z) {
/* 171 */     if (block == null || block.isAir((IBlockAccess)world, x, y, z)) {
/* 172 */       return true;
/*     */     }
/*     */     
/* 175 */     if (isUnbreakableBlock(world, x, y, z, block)) {
/* 176 */       return false;
/*     */     }
/*     */     
/* 179 */     if (block == Blocks.field_150353_l || block == Blocks.field_150356_k)
/* 180 */       return false; 
/* 181 */     if (block instanceof IFluidBlock && ((IFluidBlock)block).getFluid() != null) {
/* 182 */       Fluid f = ((IFluidBlock)block).getFluid();
/* 183 */       if (f.getDensity(world, x, y, z) >= 3000) {
/* 184 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 188 */     return true;
/*     */   }
/*     */   
/*     */   public static float getBlockHardnessMining(World world, int x, int y, int z, Block b, ItemStack tool) {
/* 192 */     if (world instanceof WorldServer && !BuildCraftCore.miningAllowPlayerProtectedBlocks) {
/* 193 */       float relativeHardness = b.func_149737_a(getFakePlayerWithTool((WorldServer)world, x, y, z, tool), world, x, y, z);
/*     */       
/* 195 */       if (relativeHardness <= 0.0F) {
/* 196 */         return -1.0F;
/*     */       }
/*     */     } 
/*     */     
/* 200 */     return b.func_149712_f(world, x, y, z);
/*     */   }
/*     */   
/*     */   public static boolean isUnbreakableBlock(World world, int x, int y, int z, Block b) {
/* 204 */     if (b == null) {
/* 205 */       return false;
/*     */     }
/*     */     
/* 208 */     return (getBlockHardnessMining(world, x, y, z, b, null) < 0.0F);
/*     */   }
/*     */   
/*     */   public static boolean isUnbreakableBlock(World world, int x, int y, int z) {
/* 212 */     return isUnbreakableBlock(world, x, y, z, world.func_147439_a(x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isToughBlock(World world, int x, int y, int z) {
/* 219 */     return !world.func_147439_a(x, y, z).func_149688_o().func_76229_l();
/*     */   }
/*     */   
/*     */   public static boolean isFullFluidBlock(World world, int x, int y, int z) {
/* 223 */     return isFullFluidBlock(world.func_147439_a(x, y, z), world, x, y, z);
/*     */   }
/*     */   
/*     */   public static boolean isFullFluidBlock(Block block, World world, int x, int y, int z) {
/* 227 */     if (block instanceof IFluidBlock)
/* 228 */       return (((IFluidBlock)block).getFilledPercentage(world, x, y, z) == 1.0F); 
/* 229 */     if (block instanceof net.minecraft.block.BlockStaticLiquid) {
/* 230 */       return (world.func_72805_g(x, y, z) == 0);
/*     */     }
/* 232 */     return false;
/*     */   }
/*     */   
/*     */   public static Fluid getFluid(Block block) {
/* 236 */     if (block instanceof IFluidBlock) {
/* 237 */       return ((IFluidBlock)block).getFluid();
/*     */     }
/* 239 */     return FluidRegistry.lookupFluidForBlock(block);
/*     */   }
/*     */ 
/*     */   
/*     */   public static FluidStack drainBlock(World world, int x, int y, int z, boolean doDrain) {
/* 244 */     return drainBlock(world.func_147439_a(x, y, z), world, x, y, z, doDrain);
/*     */   }
/*     */   
/*     */   public static FluidStack drainBlock(Block block, World world, int x, int y, int z, boolean doDrain) {
/* 248 */     if (block instanceof IFluidBlock) {
/* 249 */       IFluidBlock fluidBlock = (IFluidBlock)block;
/* 250 */       if (!fluidBlock.canDrain(world, x, y, z)) {
/* 251 */         return null;
/*     */       }
/* 253 */       return fluidBlock.drain(world, x, y, z, doDrain);
/*     */     } 
/* 255 */     Fluid fluid = getFluid(block);
/*     */     
/* 257 */     if (fluid != null && FluidRegistry.isFluidRegistered(fluid)) {
/* 258 */       int meta = world.func_72805_g(x, y, z);
/* 259 */       if (meta != 0) {
/* 260 */         return null;
/*     */       }
/*     */       
/* 263 */       if (doDrain) {
/* 264 */         world.func_147468_f(x, y, z);
/*     */       }
/*     */       
/* 267 */       return new FluidStack(fluid, 1000);
/*     */     } 
/* 269 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void explodeBlock(World world, int x, int y, int z) {
/* 279 */     if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
/*     */       return;
/*     */     }
/*     */     
/* 283 */     Explosion explosion = new Explosion(world, null, x + 0.5D, y + 0.5D, z + 0.5D, 3.0F);
/* 284 */     explosion.field_77281_g.add(new ChunkPosition(x, y, z));
/* 285 */     explosion.func_77279_a(true);
/*     */     
/* 287 */     for (EntityPlayer player : world.field_73010_i) {
/* 288 */       if (!(player instanceof EntityPlayerMP)) {
/*     */         continue;
/*     */       }
/*     */       
/* 292 */       if (player.func_70092_e(x, y, z) < 4096.0D) {
/* 293 */         ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)new S27PacketExplosion(x + 0.5D, y + 0.5D, z + 0.5D, 3.0F, explosion.field_77281_g, null));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int computeBlockBreakEnergy(World world, int x, int y, int z) {
/* 299 */     return (int)Math.floor((160.0F * BuildCraftCore.miningMultiplier * (world.func_147439_a(x, y, z).func_149712_f(world, x, y, z) + 1.0F) * 2.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TileEntity getTileEntity(World world, int x, int y, int z) {
/* 306 */     return getTileEntity(world, x, y, z, false);
/*     */   }
/*     */   
/*     */   public static TileEntity getTileEntity(World world, int x, int y, int z, boolean force) {
/* 310 */     if (!force) {
/* 311 */       if (y < 0 || y > 255) {
/* 312 */         return null;
/*     */       }
/* 314 */       Chunk chunk = ThreadSafeUtils.getChunk(world, x >> 4, z >> 4);
/* 315 */       return (chunk != null) ? chunk.getTileEntityUnsafe(x & 0xF, y, z & 0xF) : null;
/*     */     } 
/* 317 */     return world.func_147438_o(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Block getBlock(World world, int x, int y, int z) {
/* 322 */     return getBlock(world, x, y, z, false);
/*     */   }
/*     */   
/*     */   public static Block getBlock(World world, int x, int y, int z, boolean force) {
/* 326 */     if (!force) {
/* 327 */       if (y < 0 || y > 255) {
/* 328 */         return Blocks.field_150350_a;
/*     */       }
/* 330 */       Chunk chunk = ThreadSafeUtils.getChunk(world, x >> 4, z >> 4);
/* 331 */       return (chunk != null) ? chunk.func_150810_a(x & 0xF, y, z & 0xF) : Blocks.field_150350_a;
/*     */     } 
/* 333 */     return world.func_147439_a(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getBlockMetadata(World world, int x, int y, int z) {
/* 338 */     return getBlockMetadata(world, x, y, z, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getBlockMetadata(World world, int x, int y, int z, boolean force) {
/* 343 */     if (!force) {
/* 344 */       if (y < 0 || y > 255) {
/* 345 */         return 0;
/*     */       }
/* 347 */       Chunk chunk = ThreadSafeUtils.getChunk(world, x >> 4, z >> 4);
/* 348 */       return (chunk != null) ? chunk.func_76628_c(x & 0xF, y, z & 0xF) : 0;
/*     */     } 
/* 350 */     return world.func_72805_g(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean useItemOnBlock(World world, EntityPlayer player, ItemStack stack, int x, int y, int z, ForgeDirection direction) {
/* 356 */     boolean done = stack.func_77973_b().onItemUseFirst(stack, player, world, x, y, z, direction
/* 357 */         .ordinal(), 0.5F, 0.5F, 0.5F);
/*     */     
/* 359 */     if (!done) {
/* 360 */       done = stack.func_77973_b().func_77648_a(stack, player, world, x, y, z, direction.ordinal(), 0.5F, 0.5F, 0.5F);
/*     */     }
/*     */     
/* 363 */     return done;
/*     */   }
/*     */   
/*     */   public static void onComparatorUpdate(World world, int x, int y, int z, Block block) {
/* 367 */     world.func_147453_f(x, y, z, block);
/*     */   }
/*     */   
/*     */   public static TileEntityChest getOtherDoubleChest(TileEntity inv) {
/* 371 */     if (inv instanceof TileEntityChest) {
/* 372 */       TileEntityChest chest = (TileEntityChest)inv;
/*     */       
/* 374 */       TileEntityChest adjacent = null;
/*     */       
/* 376 */       if (chest.field_145991_k != null) {
/* 377 */         adjacent = chest.field_145991_k;
/*     */       }
/*     */       
/* 380 */       if (chest.field_145990_j != null) {
/* 381 */         adjacent = chest.field_145990_j;
/*     */       }
/*     */       
/* 384 */       if (chest.field_145992_i != null) {
/* 385 */         adjacent = chest.field_145992_i;
/*     */       }
/*     */       
/* 388 */       if (chest.field_145988_l != null) {
/* 389 */         adjacent = chest.field_145988_l;
/*     */       }
/*     */       
/* 392 */       return adjacent;
/*     */     } 
/* 394 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\BlockUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package buildcraft.core.lib.utils;
/*     */ 
/*     */ import buildcraft.api.core.IAreaProvider;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.api.tiles.ITileAreaProvider;
/*     */ import buildcraft.api.transport.IInjectable;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.CompatHooks;
/*     */ import buildcraft.core.internal.IDropControlInventory;
/*     */ import buildcraft.core.internal.IFramePipeConnection;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.engines.BlockEngineBase;
/*     */ import buildcraft.core.lib.inventory.ITransactor;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.inventory.Transactor;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import cpw.mods.fml.common.network.internal.FMLProxyPacket;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.MathHelper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Utils
/*     */ {
/*     */   public static final boolean CAULDRON_DETECTED;
/*  55 */   public static final XorShift128Random RANDOM = new XorShift128Random();
/*  56 */   private static final List<ForgeDirection> directions = new ArrayList<ForgeDirection>(Arrays.asList(ForgeDirection.VALID_DIRECTIONS));
/*     */   
/*     */   static {
/*  59 */     boolean cauldron = false;
/*     */     try {
/*  61 */       cauldron = (Utils.class.getClassLoader().loadClass("org.spigotmc.SpigotConfig") != null);
/*  62 */     } catch (ClassNotFoundException classNotFoundException) {}
/*     */ 
/*     */     
/*  65 */     CAULDRON_DETECTED = cauldron;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRegistered(Block block) {
/*  76 */     return (block != null && Block.func_149682_b(block) >= 0);
/*     */   }
/*     */   
/*     */   public static boolean isRegistered(Item item) {
/*  80 */     return (item != null && Item.func_150891_b(item) >= 0);
/*     */   }
/*     */   
/*     */   public static boolean isRegistered(ItemStack stack) {
/*  84 */     if (stack == null) {
/*  85 */       return false;
/*     */     }
/*  87 */     if (stack.func_77973_b() != null) {
/*  88 */       Block block = Block.func_149634_a(stack.func_77973_b());
/*  89 */       if (block instanceof BlockEngineBase) {
/*  90 */         return (isRegistered(block) && ((BlockEngineBase)block).hasEngine(stack.func_77960_j()));
/*     */       }
/*     */     } 
/*  93 */     return isRegistered(stack.func_77973_b());
/*     */   }
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
/*     */   public static int addToRandomInventoryAround(World world, int x, int y, int z, ItemStack stack) {
/* 108 */     Collections.shuffle(directions);
/* 109 */     for (ForgeDirection orientation : directions) {
/* 110 */       Position pos = new Position(x, y, z, orientation);
/* 111 */       pos.moveForwards(1.0D);
/*     */       
/* 113 */       TileEntity tileInventory = BlockUtils.getTileEntity(world, (int)pos.x, (int)pos.y, (int)pos.z);
/* 114 */       ITransactor transactor = Transactor.getTransactorFor(tileInventory);
/* 115 */       if (transactor != null && !(tileInventory instanceof buildcraft.api.power.IEngine) && !(tileInventory instanceof buildcraft.api.power.ILaserTarget) && (transactor.add(stack, orientation.getOpposite(), false)).field_77994_a > 0) {
/* 116 */         return (transactor.add(stack, orientation.getOpposite(), true)).field_77994_a;
/*     */       }
/*     */     } 
/* 119 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ForgeDirection get2dOrientation(EntityLivingBase entityliving) {
/* 128 */     ForgeDirection[] orientationTable = { ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.EAST };
/*     */     
/* 130 */     int orientationIndex = MathHelper.func_76128_c((entityliving.field_70177_z + 45.0D) / 90.0D) & 0x3;
/* 131 */     return orientationTable[orientationIndex];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int addToRandomInjectableAround(World world, int x, int y, int z, ForgeDirection from, ItemStack stack) {
/* 142 */     List<IInjectable> possiblePipes = new ArrayList<IInjectable>();
/* 143 */     List<ForgeDirection> pipeDirections = new ArrayList<ForgeDirection>();
/*     */     
/* 145 */     for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 146 */       if (from.getOpposite() != side) {
/*     */ 
/*     */ 
/*     */         
/* 150 */         Position pos = new Position(x, y, z, side);
/*     */         
/* 152 */         pos.moveForwards(1.0D);
/*     */         
/* 154 */         TileEntity tile = BlockUtils.getTileEntity(world, (int)pos.x, (int)pos.y, (int)pos.z);
/*     */         
/* 156 */         if (tile instanceof IInjectable)
/* 157 */         { if (((IInjectable)tile).canInjectItems(side.getOpposite())) {
/*     */ 
/*     */ 
/*     */             
/* 161 */             possiblePipes.add((IInjectable)tile);
/* 162 */             pipeDirections.add(side.getOpposite());
/*     */           }  }
/* 164 */         else { IInjectable wrapper = CompatHooks.INSTANCE.getInjectableWrapper(tile, side);
/* 165 */           if (wrapper != null) {
/* 166 */             possiblePipes.add(wrapper);
/* 167 */             pipeDirections.add(side.getOpposite());
/*     */           }  }
/*     */       
/*     */       } 
/*     */     } 
/* 172 */     if (possiblePipes.size() > 0) {
/* 173 */       int choice = RANDOM.nextInt(possiblePipes.size());
/*     */       
/* 175 */       IInjectable pipeEntry = possiblePipes.get(choice);
/*     */       
/* 177 */       return pipeEntry.injectItem(stack, true, pipeDirections.get(choice), null);
/*     */     } 
/* 179 */     return 0;
/*     */   }
/*     */   
/*     */   public static void dropTryIntoPlayerInventory(World world, int x, int y, int z, ItemStack stack, EntityPlayer player) {
/* 183 */     if (player != null && player.field_71071_by.func_70441_a(stack) && 
/* 184 */       player instanceof EntityPlayerMP) {
/* 185 */       ((EntityPlayerMP)player).func_71120_a(player.field_71069_bz);
/*     */     }
/*     */     
/* 188 */     InvUtils.dropItems(world, stack, x, y, z);
/*     */   }
/*     */   
/*     */   public static IAreaProvider getNearbyAreaProvider(World world, int i, int j, int k) {
/* 192 */     for (TileEntity t : world.field_147482_g) {
/* 193 */       if (t instanceof ITileAreaProvider && ((ITileAreaProvider)t).isValidFromLocation(i, j, k)) {
/* 194 */         return (IAreaProvider)t;
/*     */       }
/*     */     } 
/*     */     
/* 198 */     return null;
/*     */   }
/*     */   
/*     */   public static void preDestroyBlock(World world, int i, int j, int k) {
/* 202 */     TileEntity tile = BlockUtils.getTileEntity(world, i, j, k);
/*     */     
/* 204 */     if (tile instanceof IInventory && !world.field_72995_K && (
/* 205 */       !(tile instanceof IDropControlInventory) || ((IDropControlInventory)tile).doDrop())) {
/* 206 */       InvUtils.dropItems(world, (IInventory)tile, i, j, k);
/* 207 */       InvUtils.wipeInventory((IInventory)tile);
/*     */     } 
/*     */ 
/*     */     
/* 211 */     if (tile instanceof TileBuildCraft) {
/* 212 */       ((TileBuildCraft)tile).destroy();
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isFakePlayer(EntityPlayer player) {
/* 217 */     if (player instanceof net.minecraftforge.common.util.FakePlayer) {
/* 218 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 223 */     if (!player.field_70175_ag) {
/* 224 */       return true;
/*     */     }
/*     */     
/* 227 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean checkPipesConnections(TileEntity tile1, TileEntity tile2) {
/* 231 */     if (tile1 == null || tile2 == null) {
/* 232 */       return false;
/*     */     }
/*     */     
/* 235 */     if (!(tile1 instanceof IPipeTile) && !(tile2 instanceof IPipeTile)) {
/* 236 */       return false;
/*     */     }
/*     */     
/* 239 */     ForgeDirection o = ForgeDirection.UNKNOWN;
/*     */     
/* 241 */     if (tile1.field_145851_c - 1 == tile2.field_145851_c) {
/* 242 */       o = ForgeDirection.WEST;
/* 243 */     } else if (tile1.field_145851_c + 1 == tile2.field_145851_c) {
/* 244 */       o = ForgeDirection.EAST;
/* 245 */     } else if (tile1.field_145848_d - 1 == tile2.field_145848_d) {
/* 246 */       o = ForgeDirection.DOWN;
/* 247 */     } else if (tile1.field_145848_d + 1 == tile2.field_145848_d) {
/* 248 */       o = ForgeDirection.UP;
/* 249 */     } else if (tile1.field_145849_e - 1 == tile2.field_145849_e) {
/* 250 */       o = ForgeDirection.NORTH;
/* 251 */     } else if (tile1.field_145849_e + 1 == tile2.field_145849_e) {
/* 252 */       o = ForgeDirection.SOUTH;
/*     */     } 
/*     */     
/* 255 */     if (tile1 instanceof IPipeTile && !((IPipeTile)tile1).isPipeConnected(o)) {
/* 256 */       return false;
/*     */     }
/*     */     
/* 259 */     if (tile2 instanceof IPipeTile && !((IPipeTile)tile2).isPipeConnected(o.getOpposite())) {
/* 260 */       return false;
/*     */     }
/*     */     
/* 263 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean checkLegacyPipesConnections(IBlockAccess blockAccess, int x1, int y1, int z1, int x2, int y2, int z2) {
/* 268 */     Block b1 = blockAccess.func_147439_a(x1, y1, z1);
/* 269 */     Block b2 = blockAccess.func_147439_a(x2, y2, z2);
/*     */     
/* 271 */     if (!(b1 instanceof IFramePipeConnection) && !(b2 instanceof IFramePipeConnection)) {
/* 272 */       return false;
/*     */     }
/*     */     
/* 275 */     if (b1 instanceof IFramePipeConnection && !((IFramePipeConnection)b1).isPipeConnected(blockAccess, x1, y1, z1, x2, y2, z2)) {
/* 276 */       return false;
/*     */     }
/*     */     
/* 279 */     if (b2 instanceof IFramePipeConnection && !((IFramePipeConnection)b2).isPipeConnected(blockAccess, x2, y2, z2, x1, y1, z1)) {
/* 280 */       return false;
/*     */     }
/*     */     
/* 283 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPipeConnected(IBlockAccess access, int x, int y, int z, ForgeDirection dir, IPipeTile.PipeType type) {
/* 288 */     TileEntity tile = access.func_147438_o(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
/* 289 */     return (tile instanceof IPipeTile && ((IPipeTile)tile).getPipeType() == type && ((IPipeTile)tile).isPipeConnected(dir.getOpposite()));
/*     */   }
/*     */   
/*     */   public static int[] createSlotArray(int first, int count) {
/* 293 */     int[] slots = new int[count];
/* 294 */     for (int k = first; k < first + count; k++) {
/* 295 */       slots[k - first] = k;
/*     */     }
/* 297 */     return slots;
/*     */   }
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
/*     */   public static FMLProxyPacket toPacket(Packet packet, int discriminator) {
/* 311 */     ByteBuf buf = Unpooled.buffer();
/*     */     
/* 313 */     buf.writeByte((byte)discriminator);
/* 314 */     packet.writeData(buf);
/*     */     
/* 316 */     return new FMLProxyPacket(buf, "BC-CORE");
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
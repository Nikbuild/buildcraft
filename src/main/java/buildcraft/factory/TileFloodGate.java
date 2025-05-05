/*     */ package buildcraft.factory;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.BuildCraftAPI;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.fluids.Tank;
/*     */ import buildcraft.core.lib.fluids.TankUtils;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Deque;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.BlockFluidBase;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
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
/*     */ public class TileFloodGate
/*     */   extends TileBuildCraft
/*     */   implements IFluidHandler
/*     */ {
/*  40 */   public static final int[] REBUILD_DELAY = new int[8];
/*     */   public static final int MAX_LIQUID = 2000;
/*  42 */   private final TreeMap<Integer, Deque<BlockIndex>> pumpLayerQueues = new TreeMap<Integer, Deque<BlockIndex>>();
/*  43 */   private final Set<BlockIndex> visitedBlocks = new HashSet<BlockIndex>();
/*  44 */   private Deque<BlockIndex> fluidsFound = new LinkedList<BlockIndex>();
/*  45 */   private final Tank tank = new Tank("tank", 2000, (TileEntity)this);
/*     */   private int rebuildDelay;
/*  47 */   private int tick = Utils.RANDOM.nextInt();
/*     */   private boolean powered = false;
/*  49 */   private boolean[] blockedSides = new boolean[6];
/*     */   
/*     */   static {
/*  52 */     REBUILD_DELAY[0] = 128;
/*  53 */     REBUILD_DELAY[1] = 256;
/*  54 */     REBUILD_DELAY[2] = 512;
/*  55 */     REBUILD_DELAY[3] = 1024;
/*  56 */     REBUILD_DELAY[4] = 2048;
/*  57 */     REBUILD_DELAY[5] = 4096;
/*  58 */     REBUILD_DELAY[6] = 8192;
/*  59 */     REBUILD_DELAY[7] = 16384;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  67 */     super.func_145845_h();
/*     */     
/*  69 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  73 */     if (this.powered) {
/*     */       return;
/*     */     }
/*     */     
/*  77 */     this.tick++;
/*  78 */     if (this.tick % 16 == 0) {
/*  79 */       FluidStack fluidtoFill = this.tank.drain(1000, false);
/*  80 */       if (fluidtoFill != null && fluidtoFill.amount == 1000) {
/*  81 */         Fluid fluid = fluidtoFill.getFluid();
/*  82 */         if (fluid == null || !fluid.canBePlacedInWorld()) {
/*     */           return;
/*     */         }
/*     */         
/*  86 */         if (fluid == FluidRegistry.WATER && this.field_145850_b.field_73011_w.field_76574_g == -1) {
/*  87 */           this.tank.drain(1000, true);
/*     */           
/*     */           return;
/*     */         } 
/*  91 */         if (this.tick % REBUILD_DELAY[this.rebuildDelay] == 0) {
/*  92 */           this.rebuildDelay++;
/*  93 */           if (this.rebuildDelay >= REBUILD_DELAY.length) {
/*  94 */             this.rebuildDelay = REBUILD_DELAY.length - 1;
/*     */           }
/*  96 */           rebuildQueue();
/*     */         } 
/*  98 */         BlockIndex index = getNextIndexToFill(true);
/*     */         
/* 100 */         if (index != null && placeFluid(index.x, index.y, index.z, fluid)) {
/* 101 */           this.tank.drain(1000, true);
/* 102 */           this.rebuildDelay = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean placeFluid(int x, int y, int z, Fluid fluid) {
/* 109 */     Block block = BlockUtils.getBlock(this.field_145850_b, x, y, z);
/*     */     
/* 111 */     if (canPlaceFluidAt(block, x, y, z)) {
/*     */       boolean placed;
/* 113 */       Block b = TankUtils.getFluidBlock(fluid, true);
/*     */       
/* 115 */       if (b instanceof BlockFluidBase) {
/* 116 */         BlockFluidBase blockFluid = (BlockFluidBase)b;
/* 117 */         placed = this.field_145850_b.func_147465_d(x, y, z, b, blockFluid.getMaxRenderHeightMeta(), 3);
/*     */       } else {
/* 119 */         placed = this.field_145850_b.func_147449_b(x, y, z, b);
/*     */       } 
/*     */       
/* 122 */       if (placed) {
/* 123 */         queueAdjacent(x, y, z);
/* 124 */         expandQueue();
/*     */       } 
/*     */       
/* 127 */       return placed;
/*     */     } 
/*     */     
/* 130 */     return false;
/*     */   }
/*     */   
/*     */   private BlockIndex getNextIndexToFill(boolean remove) {
/* 134 */     if (this.pumpLayerQueues.isEmpty()) {
/* 135 */       return null;
/*     */     }
/*     */     
/* 138 */     Deque<BlockIndex> bottomLayer = (Deque<BlockIndex>)this.pumpLayerQueues.firstEntry().getValue();
/*     */     
/* 140 */     if (bottomLayer != null) {
/* 141 */       if (bottomLayer.isEmpty()) {
/* 142 */         this.pumpLayerQueues.pollFirstEntry();
/*     */       }
/* 144 */       if (remove) {
/* 145 */         BlockIndex index = bottomLayer.pollFirst();
/* 146 */         return index;
/*     */       } 
/* 148 */       return bottomLayer.peekFirst();
/*     */     } 
/*     */     
/* 151 */     return null;
/*     */   }
/*     */   
/*     */   private Deque<BlockIndex> getLayerQueue(int layer) {
/* 155 */     Deque<BlockIndex> pumpQueue = this.pumpLayerQueues.get(Integer.valueOf(layer));
/* 156 */     if (pumpQueue == null) {
/* 157 */       pumpQueue = new LinkedList<BlockIndex>();
/* 158 */       this.pumpLayerQueues.put(Integer.valueOf(layer), pumpQueue);
/*     */     } 
/* 160 */     return pumpQueue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void rebuildQueue() {
/* 167 */     this.pumpLayerQueues.clear();
/* 168 */     this.visitedBlocks.clear();
/* 169 */     this.fluidsFound.clear();
/*     */     
/* 171 */     queueAdjacent(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     
/* 173 */     expandQueue();
/*     */   }
/*     */   
/*     */   private void expandQueue() {
/* 177 */     if (this.tank.getFluidType() == null) {
/*     */       return;
/*     */     }
/* 180 */     while (!this.fluidsFound.isEmpty()) {
/* 181 */       Deque<BlockIndex> fluidsToExpand = this.fluidsFound;
/* 182 */       this.fluidsFound = new LinkedList<BlockIndex>();
/*     */       
/* 184 */       for (BlockIndex index : fluidsToExpand) {
/* 185 */         queueAdjacent(index.x, index.y, index.z);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void queueAdjacent(int x, int y, int z) {
/* 191 */     if (this.tank.getFluidType() == null) {
/*     */       return;
/*     */     }
/* 194 */     for (int i = 0; i < 6; i++) {
/* 195 */       if (i != 1 && !this.blockedSides[i]) {
/* 196 */         ForgeDirection dir = ForgeDirection.getOrientation(i);
/* 197 */         queueForFilling(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void queueForFilling(int x, int y, int z) {
/* 203 */     if (y < 0 || y > 255) {
/*     */       return;
/*     */     }
/* 206 */     BlockIndex index = new BlockIndex(x, y, z);
/* 207 */     if (this.visitedBlocks.add(index)) {
/* 208 */       if ((x - this.field_145851_c) * (x - this.field_145851_c) + (z - this.field_145849_e) * (z - this.field_145849_e) > 4096) {
/*     */         return;
/*     */       }
/*     */       
/* 212 */       Block block = BlockUtils.getBlock(this.field_145850_b, x, y, z);
/* 213 */       if (BlockUtils.getFluid(block) == this.tank.getFluidType()) {
/* 214 */         this.fluidsFound.add(index);
/*     */       }
/* 216 */       if (canPlaceFluidAt(block, x, y, z)) {
/* 217 */         getLayerQueue(y).addLast(index);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean canPlaceFluidAt(Block block, int x, int y, int z) {
/* 223 */     return (BuildCraftAPI.isSoftBlock(this.field_145850_b, x, y, z) && !BlockUtils.isFullFluidBlock(block, this.field_145850_b, x, y, z));
/*     */   }
/*     */   
/*     */   public void onNeighborBlockChange(Block block) {
/* 227 */     boolean p = this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 228 */     if (this.powered != p) {
/* 229 */       this.powered = p;
/* 230 */       if (!p) {
/* 231 */         rebuildQueue();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound data) {
/* 238 */     super.func_145839_a(data);
/* 239 */     this.tank.readFromNBT(data);
/* 240 */     this.rebuildDelay = data.func_74771_c("rebuildDelay");
/* 241 */     this.powered = data.func_74767_n("powered");
/* 242 */     for (int i = 0; i < 6; i++) {
/* 243 */       this.blockedSides[i] = data.func_74767_n("blocked[" + i + "]");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound data) {
/* 249 */     super.func_145841_b(data);
/* 250 */     this.tank.writeToNBT(data);
/* 251 */     data.func_74774_a("rebuildDelay", (byte)this.rebuildDelay);
/* 252 */     data.func_74757_a("powered", this.powered);
/* 253 */     for (int i = 0; i < 6; i++) {
/* 254 */       if (this.blockedSides[i]) {
/* 255 */         data.func_74757_a("blocked[" + i + "]", true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 262 */     byte flags = stream.readByte();
/* 263 */     for (int i = 0; i < 6; i++) {
/* 264 */       this.blockedSides[i] = ((flags & 1 << i) != 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 270 */     byte flags = 0;
/* 271 */     for (int i = 0; i < 6; i++) {
/* 272 */       if (this.blockedSides[i]) {
/* 273 */         flags = (byte)(flags | 1 << i);
/*     */       }
/*     */     } 
/* 276 */     stream.writeByte(flags);
/*     */   }
/*     */   
/*     */   public void switchSide(ForgeDirection side) {
/* 280 */     if (side.ordinal() != 1) {
/* 281 */       this.blockedSides[side.ordinal()] = !this.blockedSides[side.ordinal()];
/*     */       
/* 283 */       rebuildQueue();
/* 284 */       sendNetworkUpdate();
/* 285 */       this.field_145850_b.func_147458_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 291 */     super.func_145843_s();
/* 292 */     destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 297 */     this.pumpLayerQueues.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/* 303 */     return this.tank.fill(resource, doFill);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/* 308 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 313 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 318 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 323 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/* 328 */     return new FluidTankInfo[] { this.tank.getInfo() };
/*     */   }
/*     */   
/*     */   public boolean isSideBlocked(int side) {
/* 332 */     return this.blockedSides[side];
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\TileFloodGate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
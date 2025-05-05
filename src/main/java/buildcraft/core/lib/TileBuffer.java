/*     */ package buildcraft.core.lib;
/*     */ 
/*     */ import buildcraft.api.core.SafeTimeTracker;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ public final class TileBuffer
/*     */ {
/*  23 */   private Block block = null;
/*     */   
/*     */   private TileEntity tile;
/*  26 */   private final SafeTimeTracker tracker = new SafeTimeTracker(20L, 5L);
/*     */   
/*     */   private final World world;
/*     */   private final int x;
/*     */   
/*     */   public TileBuffer(World world, int x, int y, int z, boolean loadUnloaded) {
/*  32 */     this.world = world;
/*  33 */     this.x = x;
/*  34 */     this.y = y;
/*  35 */     this.z = z;
/*  36 */     this.loadUnloaded = loadUnloaded;
/*     */     
/*  38 */     refresh();
/*     */   }
/*     */   private final int y; private final int z; private final boolean loadUnloaded;
/*     */   public void refresh() {
/*  42 */     this.tile = null;
/*  43 */     this.block = null;
/*     */     
/*  45 */     if (!this.loadUnloaded && !this.world.func_72899_e(this.x, this.y, this.z)) {
/*     */       return;
/*     */     }
/*     */     
/*  49 */     this.block = this.world.func_147439_a(this.x, this.y, this.z);
/*     */     
/*  51 */     if (this.block != null && this.block.hasTileEntity(BlockUtils.getBlockMetadata(this.world, this.x, this.y, this.z))) {
/*  52 */       this.tile = this.world.func_147438_o(this.x, this.y, this.z);
/*     */     }
/*     */   }
/*     */   
/*     */   public void set(Block block, TileEntity tile) {
/*  57 */     this.block = block;
/*  58 */     this.tile = tile;
/*  59 */     this.tracker.markTime(this.world);
/*     */   }
/*     */   
/*     */   private void tryRefresh() {
/*  63 */     if (Utils.CAULDRON_DETECTED || (this.tile != null && this.tile.func_145837_r()) || (this.tile == null && this.tracker.markTimeIfDelay(this.world))) {
/*  64 */       refresh();
/*     */     }
/*     */   }
/*     */   
/*     */   public Block getBlock() {
/*  69 */     tryRefresh();
/*     */     
/*  71 */     return this.block;
/*     */   }
/*     */   
/*     */   public TileEntity getTile() {
/*  75 */     return getTile(false);
/*     */   }
/*     */   
/*     */   public TileEntity getTile(boolean forceUpdate) {
/*  79 */     if (!Utils.CAULDRON_DETECTED && this.tile != null && !this.tile.func_145837_r()) {
/*  80 */       return this.tile;
/*     */     }
/*     */     
/*  83 */     if (Utils.CAULDRON_DETECTED || (forceUpdate && this.tile != null && this.tile.func_145837_r()) || this.tracker.markTimeIfDelay(this.world)) {
/*  84 */       refresh();
/*     */       
/*  86 */       if (this.tile != null && !this.tile.func_145837_r()) {
/*  87 */         return this.tile;
/*     */       }
/*     */     } 
/*     */     
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   public boolean exists() {
/*  95 */     if (this.tile != null && !Utils.CAULDRON_DETECTED && !this.tile.func_145837_r()) {
/*  96 */       return true;
/*     */     }
/*     */     
/*  99 */     return this.world.func_72899_e(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public static TileBuffer[] makeBuffer(World world, int x, int y, int z, boolean loadUnloaded) {
/* 103 */     TileBuffer[] buffer = new TileBuffer[6];
/*     */     
/* 105 */     for (int i = 0; i < 6; i++) {
/* 106 */       ForgeDirection d = ForgeDirection.getOrientation(i);
/* 107 */       buffer[i] = new TileBuffer(world, x + d.offsetX, y + d.offsetY, z + d.offsetZ, loadUnloaded);
/*     */     } 
/*     */     
/* 110 */     return buffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\TileBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
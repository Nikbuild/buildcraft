/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.api.tools.IToolWrench;
/*     */ import buildcraft.core.lib.TileBuffer;
/*     */ import buildcraft.transport.Pipe;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ public abstract class PipeLogicWood
/*     */ {
/*     */   protected final Pipe<?> pipe;
/*     */   
/*     */   public PipeLogicWood(Pipe<?> pipe) {
/*  26 */     this.pipe = pipe;
/*     */   }
/*     */   
/*     */   private void switchSource() {
/*  30 */     int meta = this.pipe.container.func_145832_p();
/*  31 */     ForgeDirection newFacing = null;
/*     */     
/*  33 */     for (int i = meta + 1; i <= meta + 6; i++) {
/*  34 */       ForgeDirection facing = ForgeDirection.getOrientation(i % 6);
/*  35 */       if (isValidFacing(facing)) {
/*  36 */         newFacing = facing;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  41 */     if (newFacing == null) {
/*  42 */       newFacing = ForgeDirection.UNKNOWN;
/*     */     }
/*     */     
/*  45 */     setSource(newFacing);
/*     */   }
/*     */   
/*     */   private void setSource(ForgeDirection newFacing) {
/*  49 */     if (newFacing == ForgeDirection.UNKNOWN || isValidFacing(newFacing)) {
/*  50 */       int meta = this.pipe.container.func_145832_p();
/*     */       
/*  52 */       if (newFacing.ordinal() != meta) {
/*  53 */         this.pipe.container.func_145831_w().func_72921_c(this.pipe.container.field_145851_c, this.pipe.container.field_145848_d, this.pipe.container.field_145849_e, newFacing.ordinal(), 3);
/*  54 */         this.pipe.container.scheduleRenderUpdate();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void switchSourceIfNeeded() {
/*  60 */     int meta = this.pipe.container.func_145832_p();
/*     */     
/*  62 */     if (meta > 5) {
/*  63 */       switchSource();
/*     */     } else {
/*  65 */       ForgeDirection facing = ForgeDirection.getOrientation(meta);
/*  66 */       if (!isValidFacing(facing)) {
/*  67 */         switchSource();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isValidFacing(ForgeDirection side) {
/*  73 */     TileBuffer[] tileBuffer = this.pipe.container.getTileCache();
/*  74 */     if (tileBuffer == null) {
/*  75 */       return true;
/*     */     }
/*     */     
/*  78 */     if (!tileBuffer[side.ordinal()].exists()) {
/*  79 */       return true;
/*     */     }
/*     */     
/*  82 */     if (this.pipe.container.hasBlockingPluggable(side)) {
/*  83 */       return false;
/*     */     }
/*     */     
/*  86 */     TileEntity tile = tileBuffer[side.ordinal()].getTile();
/*  87 */     return isValidConnectingTile(tile);
/*     */   }
/*     */   
/*     */   protected abstract boolean isValidConnectingTile(TileEntity paramTileEntity);
/*     */   
/*     */   public void initialize() {
/*  93 */     if (!(this.pipe.container.func_145831_w()).field_72995_K) {
/*  94 */       switchSourceIfNeeded();
/*     */     }
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public boolean blockActivated(EntityPlayer entityplayer) {
/* 100 */     return blockActivated(entityplayer, ForgeDirection.UNKNOWN);
/*     */   }
/*     */   
/*     */   public boolean blockActivated(EntityPlayer entityplayer, ForgeDirection side) {
/* 104 */     Item equipped = (entityplayer.func_71045_bC() != null) ? entityplayer.func_71045_bC().func_77973_b() : null;
/* 105 */     if (equipped instanceof IToolWrench && ((IToolWrench)equipped).canWrench(entityplayer, this.pipe.container.field_145851_c, this.pipe.container.field_145848_d, this.pipe.container.field_145849_e)) {
/* 106 */       if (side != ForgeDirection.UNKNOWN) {
/* 107 */         setSource(side);
/*     */       } else {
/* 109 */         switchSource();
/*     */       } 
/* 111 */       ((IToolWrench)equipped).wrenchUsed(entityplayer, this.pipe.container.field_145851_c, this.pipe.container.field_145848_d, this.pipe.container.field_145849_e);
/* 112 */       return true;
/*     */     } 
/*     */     
/* 115 */     return false;
/*     */   }
/*     */   
/*     */   public void onNeighborBlockChange() {
/* 119 */     if (!(this.pipe.container.func_145831_w()).field_72995_K)
/* 120 */       switchSourceIfNeeded(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeLogicWood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
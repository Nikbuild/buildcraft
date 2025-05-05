/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.api.tools.IToolWrench;
/*     */ import buildcraft.core.lib.TileBuffer;
/*     */ import buildcraft.transport.Pipe;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
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
/*     */ public abstract class PipeLogicIron
/*     */ {
/*     */   protected final Pipe<?> pipe;
/*     */   private boolean lastPower = false;
/*     */   
/*     */   public PipeLogicIron(Pipe<?> pipe) {
/*  28 */     this.pipe = pipe;
/*     */   }
/*     */   
/*     */   public void switchOnRedstone() {
/*  32 */     boolean currentPower = this.pipe.container.func_145831_w().func_72864_z(this.pipe.container.field_145851_c, this.pipe.container.field_145848_d, this.pipe.container.field_145849_e);
/*     */     
/*  34 */     if (currentPower != this.lastPower) {
/*  35 */       switchPosition();
/*     */       
/*  37 */       this.lastPower = currentPower;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void switchPosition() {
/*  42 */     int meta = this.pipe.container.func_145832_p();
/*     */     
/*  44 */     for (int i = meta + 1; i <= meta + 6; i++) {
/*  45 */       ForgeDirection facing = ForgeDirection.getOrientation(i % 6);
/*  46 */       if (setFacing(facing)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isValidFacing(ForgeDirection side) {
/*  53 */     if (!this.pipe.container.isPipeConnected(side)) {
/*  54 */       return false;
/*     */     }
/*     */     
/*  57 */     TileBuffer[] tileBuffer = this.pipe.container.getTileCache();
/*     */     
/*  59 */     if (tileBuffer == null)
/*  60 */       return true; 
/*  61 */     if (!tileBuffer[side.ordinal()].exists()) {
/*  62 */       return true;
/*     */     }
/*     */     
/*  65 */     TileEntity tile = tileBuffer[side.ordinal()].getTile();
/*  66 */     return isValidOutputTile(tile);
/*     */   }
/*     */   
/*     */   protected boolean isValidOutputTile(TileEntity tile) {
/*  70 */     return ((!(tile instanceof IInventory) || ((IInventory)tile).func_70297_j_() != 0) && isValidConnectingTile(tile));
/*     */   }
/*     */   
/*     */   protected abstract boolean isValidConnectingTile(TileEntity paramTileEntity);
/*     */   
/*     */   public void initialize() {
/*  76 */     this.lastPower = this.pipe.container.func_145831_w().func_72864_z(this.pipe.container.field_145851_c, this.pipe.container.field_145848_d, this.pipe.container.field_145849_e);
/*     */   }
/*     */   
/*     */   public void onBlockPlaced() {
/*  80 */     this.pipe.container.func_145831_w().func_72921_c(this.pipe.container.field_145851_c, this.pipe.container.field_145848_d, this.pipe.container.field_145849_e, 1, 3);
/*  81 */     switchPosition();
/*     */   }
/*     */   
/*     */   public boolean setFacing(ForgeDirection facing) {
/*  85 */     if (facing.ordinal() != this.pipe.container.func_145832_p() && isValidFacing(facing)) {
/*  86 */       this.pipe.container.func_145831_w().func_72921_c(this.pipe.container.field_145851_c, this.pipe.container.field_145848_d, this.pipe.container.field_145849_e, facing.ordinal(), 3);
/*  87 */       this.pipe.container.scheduleRenderUpdate();
/*  88 */       return true;
/*     */     } 
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public boolean blockActivated(EntityPlayer entityplayer) {
/*  95 */     return blockActivated(entityplayer, ForgeDirection.UNKNOWN);
/*     */   }
/*     */   
/*     */   public boolean blockActivated(EntityPlayer entityplayer, ForgeDirection side) {
/*  99 */     Item equipped = (entityplayer.func_71045_bC() != null) ? entityplayer.func_71045_bC().func_77973_b() : null;
/* 100 */     if (equipped instanceof IToolWrench && ((IToolWrench)equipped).canWrench(entityplayer, this.pipe.container.field_145851_c, this.pipe.container.field_145848_d, this.pipe.container.field_145849_e)) {
/* 101 */       if (side == ForgeDirection.UNKNOWN) {
/* 102 */         switchPosition();
/*     */       } else {
/* 104 */         setFacing(side);
/*     */       } 
/* 106 */       this.pipe.container.scheduleRenderUpdate();
/* 107 */       ((IToolWrench)equipped).wrenchUsed(entityplayer, this.pipe.container.field_145851_c, this.pipe.container.field_145848_d, this.pipe.container.field_145849_e);
/*     */       
/* 109 */       return true;
/*     */     } 
/*     */     
/* 112 */     return false;
/*     */   }
/*     */   
/*     */   public ForgeDirection getOutputDirection() {
/* 116 */     return ForgeDirection.getOrientation(this.pipe.container.func_145832_p());
/*     */   }
/*     */   
/*     */   public boolean outputOpen(ForgeDirection to) {
/* 120 */     return (to.ordinal() == this.pipe.container.func_145832_p());
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeLogicIron.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
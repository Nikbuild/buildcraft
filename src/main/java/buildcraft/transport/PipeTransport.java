/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.lib.utils.BitSetUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ 
/*     */ public abstract class PipeTransport
/*     */ {
/*     */   public TileGenericPipe container;
/*  28 */   protected boolean[] inputsOpen = new boolean[ForgeDirection.VALID_DIRECTIONS.length];
/*  29 */   protected boolean[] outputsOpen = new boolean[ForgeDirection.VALID_DIRECTIONS.length];
/*     */   
/*     */   public PipeTransport() {
/*  32 */     for (int b = 0; b < ForgeDirection.VALID_DIRECTIONS.length; b++) {
/*  33 */       this.inputsOpen[b] = true;
/*  34 */       this.outputsOpen[b] = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract IPipeTile.PipeType getPipeType();
/*     */   
/*     */   public World getWorld() {
/*  41 */     return this.container.func_145831_w();
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  45 */     if (nbt.func_74764_b("inputOpen") && nbt.func_74764_b("outputOpen")) {
/*  46 */       BitSet inputBuf = BitSetUtils.fromByteArray(new byte[] { nbt.func_74771_c("inputOpen") });
/*  47 */       BitSet outputBuf = BitSetUtils.fromByteArray(new byte[] { nbt.func_74771_c("outputOpen") });
/*     */       
/*  49 */       for (int b = 0; b < ForgeDirection.VALID_DIRECTIONS.length; b++) {
/*  50 */         this.inputsOpen[b] = inputBuf.get(b);
/*  51 */         this.outputsOpen[b] = outputBuf.get(b);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  57 */     BitSet inputBuf = new BitSet(ForgeDirection.VALID_DIRECTIONS.length);
/*  58 */     BitSet outputBuf = new BitSet(ForgeDirection.VALID_DIRECTIONS.length);
/*     */     
/*  60 */     for (int b = 0; b < ForgeDirection.VALID_DIRECTIONS.length; b++) {
/*  61 */       if (this.inputsOpen[b]) {
/*  62 */         inputBuf.set(b, true);
/*     */       } else {
/*  64 */         inputBuf.set(b, false);
/*     */       } 
/*     */       
/*  67 */       if (this.outputsOpen[b]) {
/*  68 */         outputBuf.set(b, true);
/*     */       } else {
/*  70 */         outputBuf.set(b, false);
/*     */       } 
/*     */     } 
/*     */     
/*  74 */     nbt.func_74774_a("inputOpen", BitSetUtils.toByteArray(inputBuf)[0]);
/*  75 */     nbt.func_74774_a("outputOpen", BitSetUtils.toByteArray(outputBuf)[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEntity() {}
/*     */   
/*     */   public void setTile(TileGenericPipe tile) {
/*  82 */     this.container = tile;
/*     */   }
/*     */   
/*     */   public boolean canPipeConnect(TileEntity tile, ForgeDirection side) {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNeighborChange(ForgeDirection direction) {}
/*     */ 
/*     */   
/*     */   public void onBlockPlaced() {}
/*     */ 
/*     */   
/*     */   public void initialize() {}
/*     */   
/*     */   public boolean inputOpen(ForgeDirection from) {
/*  99 */     return this.inputsOpen[from.ordinal()];
/*     */   }
/*     */   
/*     */   public boolean outputOpen(ForgeDirection to) {
/* 103 */     return this.outputsOpen[to.ordinal()];
/*     */   }
/*     */   
/*     */   public void allowInput(ForgeDirection from, boolean allow) {
/* 107 */     if (from != ForgeDirection.UNKNOWN) {
/* 108 */       this.inputsOpen[from.ordinal()] = allow;
/*     */     }
/*     */   }
/*     */   
/*     */   public void allowOutput(ForgeDirection to, boolean allow) {
/* 113 */     if (to != ForgeDirection.UNKNOWN) {
/* 114 */       this.outputsOpen[to.ordinal()] = allow;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropContents() {}
/*     */   
/*     */   public List<ItemStack> getDroppedItems() {
/* 122 */     return new ArrayList<ItemStack>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendDescriptionPacket() {}
/*     */   
/*     */   public boolean delveIntoUnloadedChunks() {
/* 129 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
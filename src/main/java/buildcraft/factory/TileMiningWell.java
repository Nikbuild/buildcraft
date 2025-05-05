/*     */ package buildcraft.factory;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.BuildCraftFactory;
/*     */ import buildcraft.api.core.SafeTimeTracker;
/*     */ import buildcraft.api.tiles.IControllable;
/*     */ import buildcraft.api.tiles.IHasWork;
/*     */ import buildcraft.api.transport.IPipeConnection;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.internal.ILEDProvider;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.utils.BlockMiner;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ public class TileMiningWell
/*     */   extends TileBuildCraft
/*     */   implements IHasWork, IPipeConnection, IControllable, ILEDProvider
/*     */ {
/*     */   private boolean isDigging = true;
/*     */   private BlockMiner miner;
/*     */   private int ledState;
/*  35 */   private int ticksSinceAction = 9001;
/*     */   
/*  37 */   private SafeTimeTracker updateTracker = new SafeTimeTracker(BuildCraftCore.updateFactor);
/*     */ 
/*     */   
/*     */   public TileMiningWell() {
/*  41 */     setBattery(new RFBattery(20480, 880, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  50 */     super.func_145845_h();
/*     */     
/*  52 */     if (this.field_145850_b.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  56 */     if (this.updateTracker.markTimeIfDelay(this.field_145850_b)) {
/*  57 */       sendNetworkUpdate();
/*     */     }
/*     */     
/*  60 */     this.ticksSinceAction++;
/*     */     
/*  62 */     if (this.mode == IControllable.Mode.Off) {
/*  63 */       if (this.miner != null) {
/*  64 */         this.miner.invalidate();
/*  65 */         this.miner = null;
/*     */       } 
/*  67 */       this.isDigging = false;
/*     */       
/*     */       return;
/*     */     } 
/*  71 */     if (getBattery().getEnergyStored() == 0) {
/*     */       return;
/*     */     }
/*     */     
/*  75 */     if (this.miner == null) {
/*  76 */       World world = this.field_145850_b;
/*     */       
/*  78 */       int depth = this.field_145848_d - 1;
/*     */       
/*  80 */       while (world.func_147439_a(this.field_145851_c, depth, this.field_145849_e) == BuildCraftFactory.plainPipeBlock) {
/*  81 */         depth--;
/*     */       }
/*     */       
/*  84 */       if (depth < 1 || depth < this.field_145848_d - BuildCraftFactory.miningDepth || !BlockUtils.canChangeBlock(world, this.field_145851_c, depth, this.field_145849_e)) {
/*  85 */         this.isDigging = false;
/*     */         
/*  87 */         getBattery().useEnergy(0, 10, false);
/*     */         
/*     */         return;
/*     */       } 
/*  91 */       if (world.func_147437_c(this.field_145851_c, depth, this.field_145849_e) || world.func_147439_a(this.field_145851_c, depth, this.field_145849_e).isReplaceable((IBlockAccess)world, this.field_145851_c, depth, this.field_145849_e)) {
/*  92 */         this.ticksSinceAction = 0;
/*  93 */         world.func_147449_b(this.field_145851_c, depth, this.field_145849_e, BuildCraftFactory.plainPipeBlock);
/*     */       } else {
/*  95 */         this.miner = new BlockMiner(world, (TileEntity)this, this.field_145851_c, depth, this.field_145849_e);
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     if (this.miner != null) {
/* 100 */       this.isDigging = true;
/* 101 */       this.ticksSinceAction = 0;
/*     */       
/* 103 */       int usedEnergy = this.miner.acceptEnergy(getBattery().getEnergyStored());
/* 104 */       getBattery().useEnergy(usedEnergy, usedEnergy, false);
/*     */       
/* 106 */       if (this.miner.hasFailed()) {
/* 107 */         this.isDigging = false;
/*     */       }
/*     */       
/* 110 */       if (this.miner.hasFailed() || this.miner.hasMined()) {
/* 111 */         this.miner = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 118 */     super.func_145843_s();
/* 119 */     if (this.miner != null) {
/* 120 */       this.miner.invalidate();
/*     */     }
/* 122 */     if (this.field_145850_b != null && this.field_145848_d > 2) {
/* 123 */       BuildCraftFactory.miningWellBlock.removePipes(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 129 */     super.writeData(stream);
/*     */     
/* 131 */     this.ledState = ((this.ticksSinceAction < 2) ? 16 : 0) | getBattery().getEnergyStored() * 15 / getBattery().getMaxEnergyStored();
/* 132 */     stream.writeByte(this.ledState);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 137 */     super.readData(stream);
/*     */     
/* 139 */     int newLedState = stream.readUnsignedByte();
/* 140 */     if (newLedState != this.ledState) {
/* 141 */       this.ledState = newLedState;
/* 142 */       this.field_145850_b.func_147458_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/* 148 */     return this.isDigging;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IPipeConnection.ConnectOverride overridePipeConnection(IPipeTile.PipeType type, ForgeDirection with) {
/* 154 */     return (type == IPipeTile.PipeType.ITEM) ? IPipeConnection.ConnectOverride.CONNECT : IPipeConnection.ConnectOverride.DEFAULT;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsControlMode(IControllable.Mode mode) {
/* 159 */     return (mode == IControllable.Mode.Off || mode == IControllable.Mode.On);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLEDLevel(int led) {
/* 164 */     if (led == 0) {
/* 165 */       return this.ledState & 0xF;
/*     */     }
/* 167 */     return (this.ledState >> 4 > 0) ? 15 : 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\TileMiningWell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
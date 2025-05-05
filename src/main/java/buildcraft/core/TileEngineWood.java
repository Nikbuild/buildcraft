/*     */ package buildcraft.core;
/*     */ 
/*     */ import buildcraft.api.power.IRedstoneEngine;
/*     */ import buildcraft.api.power.IRedstoneEngineReceiver;
/*     */ import buildcraft.api.transport.IPipeConnection;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.lib.engines.TileEngineBase;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ public class TileEngineWood
/*     */   extends TileEngineBase
/*     */   implements IRedstoneEngine
/*     */ {
/*     */   private boolean hasSent = false;
/*     */   
/*     */   public ResourceLocation getTrunkTexture(TileEngineBase.EnergyStage stage) {
/*  27 */     return super.getTrunkTexture((stage == TileEngineBase.EnergyStage.RED && this.progress < 0.5D) ? TileEngineBase.EnergyStage.YELLOW : stage);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TileEngineBase.EnergyStage computeEnergyStage() {
/*  32 */     double energyLevel = getEnergyLevel();
/*  33 */     if (energyLevel < 0.33000001311302185D)
/*  34 */       return TileEngineBase.EnergyStage.BLUE; 
/*  35 */     if (energyLevel < 0.6600000262260437D)
/*  36 */       return TileEngineBase.EnergyStage.GREEN; 
/*  37 */     if (energyLevel < 0.75D) {
/*  38 */       return TileEngineBase.EnergyStage.YELLOW;
/*     */     }
/*  40 */     return TileEngineBase.EnergyStage.RED;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentOutputLimit() {
/*  46 */     return 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPistonSpeed() {
/*  51 */     if (!this.field_145850_b.field_72995_K) {
/*  52 */       return Math.max(0.08F * getHeatLevel(), 0.01F);
/*     */     }
/*     */     
/*  55 */     switch (getEnergyStage()) {
/*     */       case GREEN:
/*  57 */         return 0.02F;
/*     */       case YELLOW:
/*  59 */         return 0.04F;
/*     */       case RED:
/*  61 */         return 0.08F;
/*     */     } 
/*  63 */     return 0.01F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void engineUpdate() {
/*  69 */     super.engineUpdate();
/*     */     
/*  71 */     if (this.isRedstonePowered && 
/*  72 */       this.field_145850_b.func_82737_E() % 16L == 0L) {
/*  73 */       addEnergy(10);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IPipeConnection.ConnectOverride overridePipeConnection(IPipeTile.PipeType type, ForgeDirection with) {
/*  80 */     return IPipeConnection.ConnectOverride.DISCONNECT;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/*  85 */     return this.isRedstonePowered;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergy() {
/*  90 */     return 1000;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIdealOutput() {
/*  95 */     return 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection from) {
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ForgeDirection side) {
/* 105 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ForgeDirection side) {
/* 110 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendPower() {
/* 115 */     if (this.progressPart == 2 && !this.hasSent) {
/* 116 */       this.hasSent = true;
/*     */       
/* 118 */       TileEntity tile = getTile(this.orientation);
/*     */       
/* 120 */       if (tile instanceof IRedstoneEngineReceiver && ((IRedstoneEngineReceiver)tile).canConnectRedstoneEngine(this.orientation.getOpposite())) {
/* 121 */         super.sendPower();
/*     */       } else {
/* 123 */         this.energy = 0;
/*     */       } 
/* 125 */     } else if (this.progressPart != 2) {
/* 126 */       this.hasSent = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\TileEngineWood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
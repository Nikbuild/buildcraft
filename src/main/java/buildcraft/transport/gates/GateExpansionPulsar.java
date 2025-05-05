/*     */ package buildcraft.transport.gates;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.gates.GateExpansionController;
/*     */ import buildcraft.api.gates.IGate;
/*     */ import buildcraft.api.gates.IGateExpansion;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import java.util.List;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ 
/*     */ public final class GateExpansionPulsar
/*     */   extends GateExpansionBuildcraft
/*     */   implements IGateExpansion
/*     */ {
/*  30 */   public static GateExpansionPulsar INSTANCE = new GateExpansionPulsar();
/*     */   
/*     */   private GateExpansionPulsar() {
/*  33 */     super("pulsar");
/*     */   }
/*     */ 
/*     */   
/*     */   public GateExpansionController makeController(TileEntity pipeTile) {
/*  38 */     return new GateExpansionControllerPulsar(pipeTile);
/*     */   }
/*     */   
/*     */   private class GateExpansionControllerPulsar
/*     */     extends GateExpansionController {
/*     */     private static final int PULSE_PERIOD = 10;
/*     */     private boolean isActive;
/*     */     private boolean singlePulse;
/*     */     private boolean hasPulsed;
/*     */     private int tick;
/*     */     private int count;
/*     */     
/*     */     public GateExpansionControllerPulsar(TileEntity pipeTile) {
/*  51 */       super(GateExpansionPulsar.this, pipeTile);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  57 */       this.tick = (int)(Math.random() * 10.0D);
/*     */     }
/*     */ 
/*     */     
/*     */     public void startResolution() {
/*  62 */       if (isActive()) {
/*  63 */         disablePulse();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean resolveAction(IStatement action, int count) {
/*  69 */       if (action instanceof buildcraft.transport.statements.ActionEnergyPulsar) {
/*  70 */         enablePulse(count);
/*  71 */         return true;
/*  72 */       }  if (action instanceof buildcraft.transport.statements.ActionSingleEnergyPulse) {
/*  73 */         enableSinglePulse(count);
/*  74 */         return true;
/*     */       } 
/*  76 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void addActions(List<IActionInternal> list) {
/*  81 */       super.addActions(list);
/*  82 */       list.add(BuildCraftTransport.actionEnergyPulser);
/*  83 */       list.add(BuildCraftTransport.actionSingleEnergyPulse);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(IGate gate) {
/*  88 */       if (!this.isActive && this.hasPulsed) {
/*  89 */         this.hasPulsed = false;
/*     */       }
/*     */       
/*  92 */       if (this.tick++ % 10 != 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  97 */       if (!this.isActive) {
/*  98 */         gate.setPulsing(false);
/*     */         
/*     */         return;
/*     */       } 
/* 102 */       if (this.pipeTile instanceof IEnergyHandler && (!this.singlePulse || !this.hasPulsed)) {
/* 103 */         gate.setPulsing(true);
/* 104 */         ((IEnergyHandler)this.pipeTile).receiveEnergy(ForgeDirection.UNKNOWN, Math.min(1 << this.count - 1, 64) * 10, false);
/*     */         
/* 106 */         this.hasPulsed = true;
/*     */       } else {
/* 108 */         gate.setPulsing(true);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void enableSinglePulse(int count) {
/* 113 */       this.singlePulse = true;
/* 114 */       this.isActive = true;
/* 115 */       this.count = count;
/*     */     }
/*     */     
/*     */     private void enablePulse(int count) {
/* 119 */       this.isActive = true;
/* 120 */       this.singlePulse = false;
/* 121 */       this.count = count;
/*     */     }
/*     */     
/*     */     private void disablePulse() {
/* 125 */       if (!this.isActive) {
/* 126 */         this.hasPulsed = false;
/*     */       }
/* 128 */       this.isActive = false;
/* 129 */       this.count = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isActive() {
/* 134 */       return this.isActive;
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeToNBT(NBTTagCompound nbt) {
/* 139 */       nbt.func_74757_a("singlePulse", this.singlePulse);
/* 140 */       nbt.func_74757_a("isActive", this.isActive);
/* 141 */       nbt.func_74757_a("hasPulsed", this.hasPulsed);
/* 142 */       nbt.func_74774_a("pulseCount", (byte)this.count);
/* 143 */       nbt.func_74768_a("tick", this.tick);
/*     */     }
/*     */ 
/*     */     
/*     */     public void readFromNBT(NBTTagCompound nbt) {
/* 148 */       this.isActive = nbt.func_74767_n("isActive");
/* 149 */       this.singlePulse = nbt.func_74767_n("singlePulse");
/* 150 */       this.hasPulsed = nbt.func_74767_n("hasPulsed");
/* 151 */       this.count = nbt.func_74771_c("pulseCount");
/* 152 */       this.tick = nbt.func_74762_e("tick");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gates\GateExpansionPulsar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
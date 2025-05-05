/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.power.IRedstoneEngineReceiver;
/*     */ import buildcraft.api.tiles.IDebuggable;
/*     */ import buildcraft.core.lib.RFBattery;
/*     */ import buildcraft.transport.IPipeTransportPowerHook;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTransport;
/*     */ import buildcraft.transport.PipeTransportPower;
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import cofh.api.energy.IEnergyProvider;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
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
/*     */ public class PipePowerWood
/*     */   extends Pipe<PipeTransportPower>
/*     */   implements IPipeTransportPowerHook, IEnergyHandler, IRedstoneEngineReceiver, IDebuggable
/*     */ {
/*  38 */   public final boolean[] powerSources = new boolean[6];
/*     */   
/*  40 */   protected int standardIconIndex = PipeIconProvider.TYPE.PipePowerWood_Standard.ordinal();
/*  41 */   protected int solidIconIndex = PipeIconProvider.TYPE.PipeAllWood_Solid.ordinal();
/*     */   
/*     */   protected RFBattery battery;
/*     */   
/*     */   private int requestedEnergy;
/*     */   
/*     */   public PipePowerWood(Item item) {
/*  48 */     super((PipeTransport)new PipeTransportPower(), item);
/*     */     
/*  50 */     this.battery = new RFBattery(40960, 40960, 0);
/*  51 */     ((PipeTransportPower)this.transport).initFromPipe(getClass());
/*     */   }
/*     */   private int lastRequestedEnergy; private int sources; private boolean allowExtraction = false;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIconProvider getIconProvider() {
/*  57 */     return BuildCraftTransport.instance.pipeIconProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconIndex(ForgeDirection direction) {
/*  62 */     if (direction != ForgeDirection.UNKNOWN && this.powerSources[direction.ordinal()]) {
/*  63 */       return this.solidIconIndex;
/*     */     }
/*  65 */     return this.standardIconIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateEntity() {
/*  71 */     super.updateEntity();
/*     */     
/*  73 */     this.sources = 0;
/*     */     
/*  75 */     for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
/*  76 */       boolean oldPowerSource = this.powerSources[o.ordinal()];
/*     */       
/*  78 */       if (!this.container.isPipeConnected(o)) {
/*  79 */         this.powerSources[o.ordinal()] = false;
/*     */       } else {
/*  81 */         TileEntity tile = this.container.getTile(o);
/*     */         
/*  83 */         this.powerSources[o.ordinal()] = ((PipeTransportPower)this.transport).isPowerSource(tile, o); if (((PipeTransportPower)this.transport).isPowerSource(tile, o)) {
/*  84 */           this.sources++;
/*     */         }
/*     */       } 
/*     */       
/*  88 */       if (oldPowerSource != this.powerSources[o.ordinal()]) {
/*  89 */         this.container.scheduleRenderUpdate();
/*     */       }
/*     */     } 
/*     */     
/*  93 */     if ((this.container.func_145831_w()).field_72995_K) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  99 */     if (this.sources <= 0) {
/* 100 */       this.battery.useEnergy(0, 50, false);
/* 101 */       this.requestedEnergy = 0;
/*     */       
/*     */       return;
/*     */     } 
/* 105 */     if (this.allowExtraction) {
/* 106 */       this.allowExtraction = false;
/*     */       
/* 108 */       int energyMaxExtract = Math.min(((PipeTransportPower)this.transport).maxPower, this.battery.getMaxEnergyStored() - this.battery.getEnergyStored());
/* 109 */       energyMaxExtract /= this.sources;
/*     */       
/* 111 */       for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
/* 112 */         if (this.powerSources[o.ordinal()]) {
/*     */ 
/*     */ 
/*     */           
/* 116 */           TileEntity source = this.container.getNeighborTile(o);
/* 117 */           if (source instanceof IEnergyProvider) {
/* 118 */             int energyExtracted = this.battery.addEnergy(0, ((IEnergyProvider)source)
/* 119 */                 .extractEnergy(o.getOpposite(), energyMaxExtract, true), false);
/*     */             
/* 121 */             ((IEnergyProvider)source).extractEnergy(o.getOpposite(), energyExtracted, false);
/* 122 */           } else if (source instanceof IEnergyHandler) {
/* 123 */             int energyExtracted = this.battery.addEnergy(0, ((IEnergyHandler)source)
/* 124 */                 .extractEnergy(o.getOpposite(), energyMaxExtract, true), false);
/*     */             
/* 126 */             ((IEnergyHandler)source).extractEnergy(o.getOpposite(), energyExtracted, false);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 131 */     int energyToRemove = Math.min(this.battery.getEnergyStored(), this.requestedEnergy);
/*     */     
/* 133 */     energyToRemove /= this.sources;
/*     */     
/* 135 */     if (this.battery.getEnergyStored() > 0) {
/* 136 */       for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {
/* 137 */         if (this.powerSources[o.ordinal()])
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 142 */           this.battery.setEnergy(this.battery.getEnergyStored() - (int)((PipeTransportPower)this.transport).receiveEnergy(o, energyToRemove));
/*     */         }
/*     */       } 
/*     */     }
/* 146 */     this.lastRequestedEnergy = this.requestedEnergy;
/* 147 */     this.requestedEnergy = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound data) {
/* 152 */     super.writeToNBT(data);
/*     */     
/* 154 */     NBTTagCompound batteryNBT = new NBTTagCompound();
/* 155 */     this.battery.writeToNBT(batteryNBT);
/* 156 */     data.func_74782_a("battery", (NBTBase)batteryNBT);
/*     */     
/* 158 */     for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/* 159 */       data.func_74757_a("powerSources[" + i + "]", this.powerSources[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound data) {
/* 165 */     super.readFromNBT(data);
/* 166 */     this.battery.readFromNBT(data.func_74775_l("battery"));
/*     */     
/* 168 */     for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/* 169 */       this.powerSources[i] = data.func_74767_n("powerSources[" + i + "]");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int receiveEnergy(ForgeDirection from, int val) {
/* 175 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int requestEnergy(ForgeDirection from, int amount) {
/* 180 */     if (this.container.getTile(from) instanceof buildcraft.api.transport.IPipeTile) {
/* 181 */       this.requestedEnergy += amount;
/* 182 */       return amount;
/*     */     } 
/* 184 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection from) {
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
/* 196 */     if (from.ordinal() < 6 && this.container.getNeighborTile(from) instanceof buildcraft.api.power.IRedstoneEngine) {
/* 197 */       this.allowExtraction = true;
/* 198 */       return maxReceive;
/*     */     } 
/* 200 */     if (from.ordinal() < 6 && this.powerSources[from.ordinal()]) {
/* 201 */       return this.battery.receiveEnergy(simulate ? Math.min(maxReceive, this.lastRequestedEnergy) : Math.min(maxReceive, this.battery.getMaxEnergyStored() - this.battery.getEnergyStored()), simulate);
/*     */     }
/* 203 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
/* 210 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ForgeDirection from) {
/* 215 */     return this.battery.getEnergyStored();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ForgeDirection from) {
/* 220 */     return this.battery.getMaxEnergyStored();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectRedstoneEngine(ForgeDirection side) {
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getDebugInfo(List<String> info, ForgeDirection side, ItemStack debugger, EntityPlayer player) {
/* 230 */     info.add("Power Acceptor");
/* 231 */     info.add("- requestedEnergy: " + this.requestedEnergy);
/* 232 */     info.add("- lastRequestedEnergy: " + this.lastRequestedEnergy);
/* 233 */     info.add("- stored: " + this.battery.getEnergyStored() + "/" + this.battery.getMaxEnergyStored() + " RF");
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipePowerWood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
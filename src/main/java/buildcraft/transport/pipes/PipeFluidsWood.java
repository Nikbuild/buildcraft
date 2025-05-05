/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.core.ISerializable;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTransport;
/*     */ import buildcraft.transport.PipeTransportFluids;
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
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
/*     */ public class PipeFluidsWood
/*     */   extends Pipe<PipeTransportFluids>
/*     */   implements IEnergyHandler, ISerializable
/*     */ {
/*     */   public int liquidToExtract;
/*  36 */   protected int standardIconIndex = PipeIconProvider.TYPE.PipeFluidsWood_Standard.ordinal();
/*  37 */   protected int solidIconIndex = PipeIconProvider.TYPE.PipeAllWood_Solid.ordinal();
/*     */   
/*  39 */   private PipeLogicWood logic = new PipeLogicWood(this)
/*     */     {
/*     */       protected boolean isValidConnectingTile(TileEntity tile) {
/*  42 */         if (tile instanceof buildcraft.api.transport.IPipeTile) {
/*  43 */           return false;
/*     */         }
/*  45 */         if (!(tile instanceof IFluidHandler)) {
/*  46 */           return false;
/*     */         }
/*     */         
/*  49 */         return true;
/*     */       }
/*     */     };
/*     */   
/*     */   public PipeFluidsWood(Item item) {
/*  54 */     super((PipeTransport)new PipeTransportFluids(), item);
/*     */     
/*  56 */     ((PipeTransportFluids)this.transport).initFromPipe(getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blockActivated(EntityPlayer entityplayer, ForgeDirection side) {
/*  61 */     return this.logic.blockActivated(entityplayer, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(int blockId) {
/*  66 */     this.logic.onNeighborBlockChange();
/*  67 */     super.onNeighborBlockChange(blockId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  72 */     this.logic.initialize();
/*  73 */     super.initialize();
/*     */   }
/*     */   
/*     */   private TileEntity getConnectingTile() {
/*  77 */     int meta = this.container.func_145832_p();
/*  78 */     return (meta >= 6) ? null : this.container.getTile(ForgeDirection.getOrientation(meta));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEntity() {
/*  83 */     super.updateEntity();
/*     */     
/*  85 */     if (this.liquidToExtract <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*  89 */     TileEntity tile = getConnectingTile();
/*     */     
/*  91 */     if (tile == null || !(tile instanceof IFluidHandler)) {
/*  92 */       this.liquidToExtract = 0;
/*     */     } else {
/*  94 */       extractFluid((IFluidHandler)tile, ForgeDirection.getOrientation(this.container.func_145832_p()));
/*     */ 
/*     */       
/*  97 */       this.liquidToExtract -= ((PipeTransportFluids)this.transport).getFlowRate();
/*     */       
/*  99 */       if (this.liquidToExtract < 0)
/* 100 */         this.liquidToExtract = 0; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int extractFluid(IFluidHandler fluidHandler, ForgeDirection side) {
/*     */     FluidStack extracted;
/* 106 */     int amount = (this.liquidToExtract > ((PipeTransportFluids)this.transport).getFlowRate()) ? ((PipeTransportFluids)this.transport).getFlowRate() : this.liquidToExtract;
/* 107 */     FluidTankInfo tankInfo = ((PipeTransportFluids)this.transport).getTankInfo(side)[0];
/*     */ 
/*     */     
/* 110 */     if (tankInfo.fluid != null) {
/* 111 */       extracted = fluidHandler.drain(side.getOpposite(), new FluidStack(tankInfo.fluid, amount), false);
/*     */     } else {
/* 113 */       extracted = fluidHandler.drain(side.getOpposite(), amount, false);
/*     */     } 
/*     */     
/* 116 */     int inserted = 0;
/*     */     
/* 118 */     if (extracted != null) {
/* 119 */       inserted = ((PipeTransportFluids)this.transport).fill(side, extracted, true);
/* 120 */       if (inserted > 0) {
/* 121 */         extracted.amount = inserted;
/* 122 */         fluidHandler.drain(side.getOpposite(), extracted, true);
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     return inserted;
/*     */   }
/*     */   
/*     */   protected int getEnergyMultiplier() {
/* 130 */     return 5 * BuildCraftTransport.pipeFluidsBaseFlowRate;
/*     */   }
/*     */   
/*     */   protected int getMaxExtractionFluid() {
/* 134 */     return 100 * BuildCraftTransport.pipeFluidsBaseFlowRate;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIconProvider getIconProvider() {
/* 140 */     return BuildCraftTransport.instance.pipeIconProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconIndex(ForgeDirection direction) {
/* 145 */     if (direction == ForgeDirection.UNKNOWN) {
/* 146 */       return this.standardIconIndex;
/*     */     }
/* 148 */     int metadata = this.container.func_145832_p();
/*     */     
/* 150 */     if (metadata == direction.ordinal()) {
/* 151 */       return this.solidIconIndex;
/*     */     }
/* 153 */     return this.standardIconIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean outputOpen(ForgeDirection to) {
/* 160 */     int meta = this.container.func_145832_p();
/* 161 */     return (super.outputOpen(to) && meta != to.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectEnergy(ForgeDirection from) {
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
/* 172 */     TileEntity tile = getConnectingTile();
/* 173 */     if (tile == null || !(tile instanceof IFluidHandler)) {
/* 174 */       return 0;
/*     */     }
/*     */     
/* 177 */     int maxToReceive = (getMaxExtractionFluid() - this.liquidToExtract) / getEnergyMultiplier();
/* 178 */     int received = Math.min(maxReceive, maxToReceive);
/* 179 */     if (!simulate) {
/* 180 */       this.liquidToExtract += getEnergyMultiplier() * received;
/*     */     }
/* 182 */     return received;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
/* 188 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ForgeDirection from) {
/* 193 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ForgeDirection from) {
/* 198 */     return 1000 / getEnergyMultiplier();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/* 203 */     data.writeShort(this.liquidToExtract);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/* 208 */     this.liquidToExtract = data.readShort();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeFluidsWood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
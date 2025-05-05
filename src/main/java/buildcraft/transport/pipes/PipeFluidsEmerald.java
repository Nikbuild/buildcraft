/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.ISerializable;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.utils.FluidUtils;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTransportFluids;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ public class PipeFluidsEmerald
/*     */   extends PipeFluidsWood
/*     */   implements ISerializable
/*     */ {
/*  33 */   private SimpleInventory filters = new SimpleInventory(1, "Filters", 1);
/*     */   
/*     */   public PipeFluidsEmerald(Item item) {
/*  36 */     super(item);
/*     */     
/*  38 */     this.standardIconIndex = PipeIconProvider.TYPE.PipeFluidsEmerald_Standard.ordinal();
/*  39 */     this.solidIconIndex = PipeIconProvider.TYPE.PipeAllEmerald_Solid.ordinal();
/*     */     
/*  41 */     ((PipeTransportFluids)this.transport).initFromPipe(getClass());
/*     */   }
/*     */   
/*     */   public IInventory getFilters() {
/*  45 */     return (IInventory)this.filters;
/*     */   }
/*     */ 
/*     */   
/*     */   public int extractFluid(IFluidHandler fluidHandler, ForgeDirection side) {
/*  50 */     FluidStack targetFluidStack = FluidUtils.getFluidStackFromItemStack(this.filters.func_70301_a(0));
/*  51 */     if (targetFluidStack == null) {
/*  52 */       return super.extractFluid(fluidHandler, side);
/*     */     }
/*     */     
/*  55 */     int flowRate = ((PipeTransportFluids)this.transport).getFlowRate();
/*  56 */     FluidStack toExtract = new FluidStack(targetFluidStack, (this.liquidToExtract > flowRate) ? flowRate : this.liquidToExtract);
/*  57 */     FluidStack extracted = fluidHandler.drain(side.getOpposite(), toExtract, false);
/*     */     
/*  59 */     if (extracted != null) {
/*  60 */       toExtract.amount = ((PipeTransportFluids)this.transport).fill(side, extracted, true);
/*  61 */       if (toExtract.amount > 0) {
/*  62 */         fluidHandler.drain(side.getOpposite(), toExtract, true);
/*     */       }
/*     */     } 
/*  65 */     return toExtract.amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blockActivated(EntityPlayer entityplayer, ForgeDirection side) {
/*  70 */     if (entityplayer.func_71045_bC() != null && 
/*  71 */       Block.func_149634_a(entityplayer.func_71045_bC().func_77973_b()) instanceof buildcraft.transport.BlockGenericPipe) {
/*  72 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  76 */     if (super.blockActivated(entityplayer, side)) {
/*  77 */       return true;
/*     */     }
/*     */     
/*  80 */     if (!(this.container.func_145831_w()).field_72995_K) {
/*  81 */       entityplayer.openGui(BuildCraftTransport.instance, 54, this.container.func_145831_w(), this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/*     */     }
/*     */     
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/*  89 */     NBTTagCompound nbt = new NBTTagCompound();
/*  90 */     this.filters.writeToNBT(nbt);
/*  91 */     NetworkUtils.writeNBT(data, nbt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/*  96 */     NBTTagCompound nbt = NetworkUtils.readNBT(data);
/*  97 */     this.filters.readFromNBT(nbt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 102 */     super.readFromNBT(nbt);
/* 103 */     this.filters.readFromNBT(nbt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 108 */     super.writeToNBT(nbt);
/* 109 */     this.filters.writeToNBT(nbt);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeFluidsEmerald.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
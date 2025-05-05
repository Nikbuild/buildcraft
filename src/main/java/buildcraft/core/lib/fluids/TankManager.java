/*     */ package buildcraft.core.lib.fluids;
/*     */ 
/*     */ import com.google.common.collect.ForwardingList;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
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
/*     */ public class TankManager<T extends Tank>
/*     */   extends ForwardingList<T>
/*     */   implements IFluidHandler, List<T>
/*     */ {
/*  31 */   private List<T> tanks = new ArrayList<T>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TankManager(T... tanks) {
/*  37 */     addAll(Arrays.asList(tanks));
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<T> delegate() {
/*  42 */     return this.tanks;
/*     */   }
/*     */ 
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/*  47 */     for (Tank tank : this.tanks) {
/*  48 */       int used = tank.fill(resource, doFill);
/*  49 */       if (used > 0) {
/*  50 */         return used;
/*     */       }
/*     */     } 
/*  53 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/*  58 */     if (resource == null) {
/*  59 */       return null;
/*     */     }
/*  61 */     for (Tank tank : this.tanks) {
/*  62 */       if (!resource.isFluidEqual(tank.getFluid())) {
/*     */         continue;
/*     */       }
/*  65 */       FluidStack drained = tank.drain(resource.amount, doDrain);
/*  66 */       if (drained != null && drained.amount > 0) {
/*  67 */         return drained;
/*     */       }
/*     */     } 
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/*  75 */     for (Tank tank : this.tanks) {
/*  76 */       FluidStack drained = tank.drain(maxDrain, doDrain);
/*  77 */       if (drained != null && drained.amount > 0) {
/*  78 */         return drained;
/*     */       }
/*     */     } 
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/*  96 */     FluidTankInfo[] info = new FluidTankInfo[size()];
/*  97 */     for (int i = 0; i < size(); i++) {
/*  98 */       info[i] = ((Tank)get(i)).getInfo();
/*     */     }
/* 100 */     return info;
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound data) {
/* 104 */     for (Tank tank : this.tanks) {
/* 105 */       tank.writeToNBT(data);
/*     */     }
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound data) {
/* 110 */     for (Tank tank : this.tanks) {
/* 111 */       tank.readFromNBT(data);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeData(ByteBuf data) {
/* 116 */     for (Tank tank : this.tanks) {
/* 117 */       FluidStack fluidStack = tank.getFluid();
/* 118 */       if (fluidStack != null && fluidStack.getFluid() != null) {
/* 119 */         data.writeShort(fluidStack.getFluid().getID());
/* 120 */         data.writeInt(fluidStack.amount);
/* 121 */         data.writeInt(fluidStack.getFluid().getColor(fluidStack)); continue;
/*     */       } 
/* 123 */       data.writeShort(-1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void readData(ByteBuf data) {
/* 130 */     for (Tank tank : this.tanks) {
/* 131 */       int fluidId = data.readShort();
/* 132 */       if (FluidRegistry.getFluid(fluidId) != null) {
/* 133 */         tank.setFluid(new FluidStack(FluidRegistry.getFluid(fluidId), data.readInt()));
/* 134 */         tank.colorRenderCache = data.readInt(); continue;
/*     */       } 
/* 136 */       tank.setFluid(null);
/* 137 */       tank.colorRenderCache = 16777215;
/*     */     } 
/*     */   }
/*     */   
/*     */   public TankManager() {}
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\fluids\TankManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.core.lib.fluids;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SingleUseTank
/*    */   extends Tank
/*    */ {
/*    */   private Fluid acceptedFluid;
/*    */   
/*    */   public SingleUseTank(String name, int capacity, TileEntity tile) {
/* 23 */     super(name, capacity, tile);
/*    */   }
/*    */ 
/*    */   
/*    */   public int fill(FluidStack resource, boolean doFill) {
/* 28 */     if (resource == null) {
/* 29 */       return 0;
/*    */     }
/*    */     
/* 32 */     if (doFill && this.acceptedFluid == null) {
/* 33 */       this.acceptedFluid = resource.getFluid();
/*    */     }
/*    */     
/* 36 */     if (this.acceptedFluid == null || this.acceptedFluid == resource.getFluid()) {
/* 37 */       return super.fill(resource, doFill);
/*    */     }
/*    */     
/* 40 */     return 0;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 44 */     this.acceptedFluid = null;
/*    */   }
/*    */   
/*    */   public void setAcceptedFluid(Fluid fluid) {
/* 48 */     this.acceptedFluid = fluid;
/*    */   }
/*    */   
/*    */   public Fluid getAcceptedFluid() {
/* 52 */     return this.acceptedFluid;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeTankToNBT(NBTTagCompound nbt) {
/* 57 */     super.writeTankToNBT(nbt);
/* 58 */     if (this.acceptedFluid != null) {
/* 59 */       nbt.func_74778_a("acceptedFluid", this.acceptedFluid.getName());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void readTankFromNBT(NBTTagCompound nbt) {
/* 65 */     super.readTankFromNBT(nbt);
/* 66 */     this.acceptedFluid = FluidRegistry.getFluid(nbt.func_74779_i("acceptedFluid"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\fluids\SingleUseTank.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
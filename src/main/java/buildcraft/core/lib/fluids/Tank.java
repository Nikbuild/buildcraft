/*    */ package buildcraft.core.lib.fluids;
/*    */ 
/*    */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*    */ import buildcraft.core.lib.gui.tooltips.ToolTipLine;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidTank;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Tank
/*    */   extends FluidTank
/*    */ {
/* 23 */   public int colorRenderCache = 16777215;
/*    */   
/* 25 */   protected final ToolTip toolTip = new ToolTip(new ToolTipLine[0])
/*    */     {
/*    */       public void refresh() {
/* 28 */         Tank.this.refreshTooltip();
/*    */       }
/*    */     };
/*    */   
/*    */   private final String name;
/*    */   
/*    */   public Tank(String name, int capacity, TileEntity tile) {
/* 35 */     super(capacity);
/* 36 */     this.name = name;
/* 37 */     this.tile = tile;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 41 */     return (getFluid() == null || (getFluid()).amount <= 0);
/*    */   }
/*    */   
/*    */   public boolean isFull() {
/* 45 */     return (getFluid() != null && (getFluid()).amount >= getCapacity());
/*    */   }
/*    */   
/*    */   public Fluid getFluidType() {
/* 49 */     return (getFluid() != null) ? getFluid().getFluid() : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public final NBTTagCompound writeToNBT(NBTTagCompound nbt) {
/* 54 */     NBTTagCompound tankData = new NBTTagCompound();
/* 55 */     super.writeToNBT(tankData);
/* 56 */     writeTankToNBT(tankData);
/* 57 */     nbt.func_74782_a(this.name, (NBTBase)tankData);
/* 58 */     return nbt;
/*    */   }
/*    */ 
/*    */   
/*    */   public final FluidTank readFromNBT(NBTTagCompound nbt) {
/* 63 */     if (nbt.func_74764_b(this.name)) {
/*    */       
/* 65 */       setFluid(null);
/*    */       
/* 67 */       NBTTagCompound tankData = nbt.func_74775_l(this.name);
/* 68 */       super.readFromNBT(tankData);
/* 69 */       readTankFromNBT(tankData);
/*    */     } 
/* 71 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeTankToNBT(NBTTagCompound nbt) {}
/*    */ 
/*    */   
/*    */   public void readTankFromNBT(NBTTagCompound nbt) {}
/*    */   
/*    */   public ToolTip getToolTip() {
/* 81 */     return this.toolTip;
/*    */   }
/*    */   
/*    */   protected void refreshTooltip() {
/* 85 */     this.toolTip.clear();
/* 86 */     int amount = 0;
/* 87 */     if (getFluid() != null && (getFluid()).amount > 0) {
/* 88 */       ToolTipLine fluidName = new ToolTipLine(getFluid().getFluid().getLocalizedName(getFluid()));
/* 89 */       fluidName.setSpacing(2);
/* 90 */       this.toolTip.add(fluidName);
/* 91 */       amount = (getFluid()).amount;
/*    */     } 
/* 93 */     this.toolTip.add(new ToolTipLine(String.format(Locale.ENGLISH, "%,d / %,d", new Object[] { Integer.valueOf(amount), Integer.valueOf(getCapacity()) })));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\fluids\Tank.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
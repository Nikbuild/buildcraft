/*    */ package buildcraft.api.items;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ 
/*    */ public class FluidItemDrops
/*    */ {
/*    */   public static IItemFluidShard item;
/*    */   
/*    */   public static void addFluidDrops(NonNullList<ItemStack> toDrop, FluidStack... fluids) {
/* 14 */     if (item != null) {
/* 15 */       for (FluidStack fluid : fluids) {
/* 16 */         item.addFluidDrops(toDrop, fluid);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public static void addFluidDrops(NonNullList<ItemStack> toDrop, IFluidTank... tanks) {
/* 22 */     if (item != null)
/* 23 */       for (IFluidTank tank : tanks)
/* 24 */         item.addFluidDrops(toDrop, tank.getFluid());  
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\items\FluidItemDrops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
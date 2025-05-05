/*    */ package buildcraft.api.recipes;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ 
/*    */ public class CraftingResult<T>
/*    */ {
/* 18 */   public T crafted = null;
/* 19 */   public ArrayList<ItemStack> usedItems = new ArrayList<ItemStack>();
/* 20 */   public ArrayList<FluidStack> usedFluids = new ArrayList<FluidStack>();
/* 21 */   public int energyCost = 0;
/* 22 */   public long craftingTime = 0L;
/*    */   public IFlexibleRecipe<T> recipe;
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\recipes\CraftingResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
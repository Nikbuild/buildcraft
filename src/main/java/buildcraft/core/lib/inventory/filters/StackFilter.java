/*    */ package buildcraft.core.lib.inventory.filters;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntityFurnace;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum StackFilter
/*    */   implements IStackFilter
/*    */ {
/* 21 */   ALL
/*    */   {
/*    */     public boolean matches(ItemStack stack) {
/* 24 */       return true;
/*    */     }
/*    */   },
/* 27 */   FUEL
/*    */   {
/*    */     public boolean matches(ItemStack stack) {
/* 30 */       return (TileEntityFurnace.func_145952_a(stack) > 0);
/*    */     }
/*    */   };
/*    */   
/*    */   public abstract boolean matches(ItemStack paramItemStack);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\StackFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
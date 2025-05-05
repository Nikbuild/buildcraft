/*    */ package buildcraft.core.lib.inventory.filters;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.oredict.OreDictionary;
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
/*    */ public class OreStackFilter
/*    */   implements IStackFilter
/*    */ {
/*    */   private final String[] ores;
/*    */   
/*    */   public OreStackFilter(String... iOres) {
/* 23 */     this.ores = iOres;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack stack) {
/* 28 */     int[] ids = OreDictionary.getOreIDs(stack);
/*    */     
/* 30 */     if (ids.length == 0) {
/* 31 */       return false;
/*    */     }
/*    */     
/* 34 */     for (String ore : this.ores) {
/* 35 */       int expected = OreDictionary.getOreID(ore);
/*    */       
/* 37 */       for (int id : ids) {
/* 38 */         if (id == expected) {
/* 39 */           return true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\filters\OreStackFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
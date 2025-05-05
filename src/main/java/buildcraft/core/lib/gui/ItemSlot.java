/*    */ package buildcraft.core.lib.gui;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSlot
/*    */   extends AdvancedSlot
/*    */ {
/*    */   public ItemStack stack;
/*    */   
/*    */   public ItemSlot(GuiAdvancedInterface gui, int x, int y) {
/* 17 */     super(gui, x, y);
/*    */   }
/*    */   
/*    */   public ItemSlot(GuiAdvancedInterface gui, int x, int y, ItemStack iStack) {
/* 21 */     super(gui, x, y);
/*    */     
/* 23 */     this.stack = iStack;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 28 */     return this.stack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\ItemSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
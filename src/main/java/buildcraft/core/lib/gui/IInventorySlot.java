/*    */ package buildcraft.core.lib.gui;
/*    */ 
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ public class IInventorySlot
/*    */   extends AdvancedSlot
/*    */ {
/*    */   private IInventory tile;
/*    */   private int slot;
/*    */   
/*    */   public IInventorySlot(GuiAdvancedInterface gui, int x, int y, IInventory tile, int slot) {
/* 24 */     super(gui, x, y);
/* 25 */     this.tile = tile;
/* 26 */     this.slot = slot;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 31 */     return this.tile.func_70301_a(this.slot);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\IInventorySlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
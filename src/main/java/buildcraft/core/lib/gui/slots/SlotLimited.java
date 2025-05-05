/*    */ package buildcraft.core.lib.gui.slots;
/*    */ 
/*    */ import net.minecraft.inventory.IInventory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotLimited
/*    */   extends SlotBase
/*    */ {
/*    */   private final int limit;
/*    */   
/*    */   public SlotLimited(IInventory iinventory, int slotIndex, int posX, int posY, int limit) {
/* 18 */     super(iinventory, slotIndex, posX, posY);
/* 19 */     this.limit = limit;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_75219_a() {
/* 24 */     return this.limit;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\slots\SlotLimited.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
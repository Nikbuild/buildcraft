/*    */ package buildcraft.core.lib.gui.slots;
/*    */ 
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotHidden
/*    */   extends Slot
/*    */ {
/*    */   private int saveX;
/*    */   private int saveY;
/*    */   
/*    */   public SlotHidden(IInventory inv, int index, int x, int y) {
/* 20 */     super(inv, index, x, y);
/*    */     
/* 22 */     this.saveX = x;
/* 23 */     this.saveY = y;
/*    */   }
/*    */   
/*    */   public void show() {
/* 27 */     this.field_75223_e = this.saveX;
/* 28 */     this.field_75221_f = this.saveY;
/*    */   }
/*    */   
/*    */   public void hide() {
/* 32 */     this.field_75223_e = 9999;
/* 33 */     this.field_75221_f = 9999;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\slots\SlotHidden.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
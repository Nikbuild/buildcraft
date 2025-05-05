/*    */ package buildcraft.core.lib.gui.slots;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotPhantom
/*    */   extends SlotBase
/*    */   implements IPhantomSlot
/*    */ {
/*    */   public SlotPhantom(IInventory iinventory, int slotIndex, int posX, int posY) {
/* 17 */     super(iinventory, slotIndex, posX, posY);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canAdjust() {
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_82869_a(EntityPlayer par1EntityPlayer) {
/* 27 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\slots\SlotPhantom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.core.lib.gui.slots;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ public class SlotUntouchable
/*    */   extends SlotBase
/*    */   implements IPhantomSlot
/*    */ {
/*    */   public SlotUntouchable(IInventory contents, int id, int x, int y) {
/* 21 */     super(contents, id, x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack itemstack) {
/* 26 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_82869_a(EntityPlayer par1EntityPlayer) {
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canAdjust() {
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canShift() {
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public boolean func_111238_b() {
/* 47 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\slots\SlotUntouchable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
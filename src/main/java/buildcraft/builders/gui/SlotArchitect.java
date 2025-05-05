/*    */ package buildcraft.builders.gui;
/*    */ 
/*    */ import buildcraft.builders.TileArchitect;
/*    */ import buildcraft.core.lib.gui.slots.SlotBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotArchitect
/*    */   extends SlotBase
/*    */ {
/*    */   private TileArchitect architect;
/*    */   private EntityPlayer player;
/*    */   private int slot;
/*    */   
/*    */   public SlotArchitect(IInventory iinventory, EntityPlayer player, int slotIndex, int posX, int posY) {
/* 23 */     super(iinventory, slotIndex, posX, posY);
/* 24 */     this.architect = (TileArchitect)iinventory;
/* 25 */     this.slot = slotIndex;
/* 26 */     this.player = player;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75218_e() {
/* 31 */     if (this.slot == 0) {
/* 32 */       this.architect.currentAuthorName = this.player.getDisplayName();
/*    */     }
/*    */     
/* 35 */     this.field_75224_c.func_70296_d();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\gui\SlotArchitect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
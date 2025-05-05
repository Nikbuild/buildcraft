/*    */ package buildcraft.builders.gui;
/*    */ 
/*    */ import buildcraft.builders.TileBlueprintLibrary;
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
/*    */ public class SlotBlueprintLibrary
/*    */   extends SlotBase
/*    */ {
/*    */   private TileBlueprintLibrary library;
/*    */   private EntityPlayer player;
/*    */   private int slot;
/*    */   
/*    */   public SlotBlueprintLibrary(IInventory iinventory, EntityPlayer player, int slotIndex, int posX, int posY) {
/* 23 */     super(iinventory, slotIndex, posX, posY);
/* 24 */     this.library = (TileBlueprintLibrary)iinventory;
/* 25 */     this.slot = slotIndex;
/* 26 */     this.player = player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_75218_e() {
/* 37 */     if (this.slot == 0) {
/* 38 */       this.library.uploadingPlayer = this.player;
/* 39 */     } else if (this.slot == 2) {
/* 40 */       this.library.downloadingPlayer = this.player;
/*    */     } 
/*    */     
/* 43 */     this.field_75224_c.func_70296_d();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\gui\SlotBlueprintLibrary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
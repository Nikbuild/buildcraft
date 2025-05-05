/*    */ package buildcraft.transport.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.slots.SlotPhantom;
/*    */ import buildcraft.core.lib.gui.slots.SlotValidated;
/*    */ import buildcraft.transport.TileFilteredBuffer;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerFilteredBuffer
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   IInventory playerInventory;
/*    */   TileFilteredBuffer filteredBuffer;
/*    */   
/*    */   private class SlotPhantomLockable
/*    */     extends SlotPhantom
/*    */   {
/*    */     final IInventory locks;
/*    */     
/*    */     public SlotPhantomLockable(IInventory storage, IInventory locks, int par2, int par3, int par4) {
/* 28 */       super(storage, par2, par3, par4);
/* 29 */       this.locks = locks;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean canAdjust() {
/* 34 */       return (this.locks.func_70301_a(getSlotIndex()) == null);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ContainerFilteredBuffer(InventoryPlayer playerInventory, TileFilteredBuffer tile) {
/* 42 */     super(tile.func_70302_i_());
/*    */     
/* 44 */     this.playerInventory = (IInventory)playerInventory;
/* 45 */     this.filteredBuffer = tile;
/*    */     
/* 47 */     IInventory filters = tile.getFilters();
/*    */     
/* 49 */     for (int col = 0; col < 9; col++) {
/*    */       
/* 51 */       func_75146_a((Slot)new SlotPhantomLockable(filters, (IInventory)tile, col, 8 + col * 18, 27));
/*    */       
/* 53 */       func_75146_a((Slot)new SlotValidated((IInventory)tile, col, 8 + col * 18, 61));
/*    */     } 
/*    */ 
/*    */     
/* 57 */     for (int l = 0; l < 3; l++) {
/* 58 */       for (int k1 = 0; k1 < 9; k1++) {
/* 59 */         func_75146_a(new Slot((IInventory)playerInventory, k1 + l * 9 + 9, 8 + k1 * 18, 86 + l * 18));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 64 */     for (int i1 = 0; i1 < 9; i1++) {
/* 65 */       func_75146_a(new Slot((IInventory)playerInventory, i1, 8 + i1 * 18, 144));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityPlayer) {
/* 71 */     return this.filteredBuffer.func_70300_a(entityPlayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gui\ContainerFilteredBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
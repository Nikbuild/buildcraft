/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.slots.SlotOutput;
/*    */ import buildcraft.core.lib.gui.slots.SlotValidated;
/*    */ import buildcraft.silicon.TilePackager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerPackager
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   private final TilePackager tile;
/*    */   
/*    */   public ContainerPackager(InventoryPlayer inventoryplayer, TilePackager t) {
/* 25 */     super(t.func_70302_i_());
/*    */     
/* 27 */     this.tile = t;
/*    */ 
/*    */ 
/*    */     
/* 31 */     func_75146_a((Slot)new SlotValidated((IInventory)this.tile, 9, 124, 7));
/*    */     
/* 33 */     for (int i = 0; i < 9; i++) {
/* 34 */       func_75146_a(new Slot((IInventory)this.tile, i, 8 + i * 18, 84));
/*    */     }
/*    */     int y;
/* 37 */     for (y = 0; y < 3; y++) {
/* 38 */       for (int j = 0; j < 3; j++) {
/* 39 */         func_75146_a((Slot)new SlotPackager((IInventory)this.tile.inventoryPattern, j + y * 3, 30 + j * 18, 17 + y * 18));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 44 */     func_75146_a((Slot)new SlotOutput((IInventory)this.tile, 11, 123, 59));
/*    */     
/* 46 */     for (y = 0; y < 3; y++) {
/* 47 */       for (int j = 0; j < 9; j++) {
/* 48 */         func_75146_a(new Slot((IInventory)inventoryplayer, j + y * 9 + 9, 8 + j * 18, 115 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 52 */     for (int x = 0; x < 9; x++) {
/* 53 */       func_75146_a(new Slot((IInventory)inventoryplayer, x, 8 + x * 18, 173));
/*    */     }
/*    */     
/* 56 */     func_75130_a((IInventory)this.tile);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_75144_a(int slotNum, int mouseButton, int modifier, EntityPlayer player) {
/* 61 */     Slot slot = (slotNum < 0) ? null : this.field_75151_b.get(slotNum);
/* 62 */     ItemStack out = super.func_75144_a(slotNum, mouseButton, (slot instanceof SlotPackager) ? 0 : modifier, player);
/*    */     
/* 64 */     if (slot instanceof SlotPackager) {
/* 65 */       int idx = slot.getSlotIndex();
/* 66 */       ItemStack stack = (player != null && player.field_71071_by != null) ? player.field_71071_by.func_70445_o() : null;
/* 67 */       if (stack == null) {
/* 68 */         this.tile.setPatternSlot(idx, !this.tile.isPatternSlotSet(idx));
/*    */       } else {
/* 70 */         this.tile.setPatternSlot(idx, true);
/*    */       } 
/* 72 */       this.tile.sendNetworkUpdate();
/*    */     } 
/* 74 */     return out;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 79 */     return this.tile.func_70300_a(entityplayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\ContainerPackager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.factory.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.factory.TileHopper;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
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
/*    */ 
/*    */ public class ContainerHopper
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   IInventory playerIInventory;
/*    */   TileHopper hopper;
/*    */   
/*    */   public ContainerHopper(InventoryPlayer inventory, TileHopper tile) {
/* 25 */     super(tile.func_70302_i_());
/* 26 */     this.playerIInventory = (IInventory)inventory;
/* 27 */     this.hopper = tile;
/*    */ 
/*    */     
/* 30 */     func_75146_a(new Slot((IInventory)tile, 0, 62, 18));
/* 31 */     func_75146_a(new Slot((IInventory)tile, 1, 80, 18));
/* 32 */     func_75146_a(new Slot((IInventory)tile, 2, 98, 18));
/* 33 */     func_75146_a(new Slot((IInventory)tile, 3, 80, 36));
/*    */ 
/*    */     
/* 36 */     for (int i1 = 0; i1 < 3; i1++) {
/* 37 */       for (int l1 = 0; l1 < 9; l1++) {
/* 38 */         func_75146_a(new Slot((IInventory)inventory, l1 + i1 * 9 + 9, 8 + l1 * 18, 71 + i1 * 18));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 43 */     for (int j1 = 0; j1 < 9; j1++) {
/* 44 */       func_75146_a(new Slot((IInventory)inventory, j1, 8 + j1 * 18, 129));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityPlayer) {
/* 51 */     return this.hopper.func_70300_a(entityPlayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\gui\ContainerHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
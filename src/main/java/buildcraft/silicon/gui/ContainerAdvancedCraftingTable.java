/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.slots.SlotOutput;
/*    */ import buildcraft.core.lib.gui.slots.SlotPhantom;
/*    */ import buildcraft.core.lib.gui.slots.SlotUntouchable;
/*    */ import buildcraft.silicon.TileAdvancedCraftingTable;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ICrafting;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerAdvancedCraftingTable
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   private TileAdvancedCraftingTable workbench;
/*    */   
/*    */   public ContainerAdvancedCraftingTable(InventoryPlayer playerInventory, TileAdvancedCraftingTable table) {
/* 27 */     super(table.func_70302_i_());
/* 28 */     this.workbench = table;
/*    */     int y;
/* 30 */     for (y = 0; y < 3; y++) {
/* 31 */       for (int x = 0; x < 3; x++) {
/* 32 */         func_75146_a((Slot)new SlotPhantom(table.getCraftingSlots(), x + y * 3, 33 + x * 18, 16 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 36 */     func_75146_a((Slot)new SlotUntouchable(table.getOutputSlot(), 0, 127, 34));
/*    */     
/* 38 */     for (y = 0; y < 3; y++) {
/* 39 */       for (int x = 0; x < 5; x++) {
/* 40 */         func_75146_a(new Slot((IInventory)this.workbench, x + y * 5, 15 + x * 18, 85 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 44 */     for (y = 0; y < 3; y++) {
/* 45 */       for (int x = 0; x < 3; x++) {
/* 46 */         func_75146_a((Slot)new SlotOutput((IInventory)this.workbench, 15 + x + y * 3, 109 + x * 18, 85 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 50 */     for (int l = 0; l < 3; l++) {
/* 51 */       for (int k1 = 0; k1 < 9; k1++) {
/* 52 */         func_75146_a(new Slot((IInventory)playerInventory, k1 + l * 9 + 9, 8 + k1 * 18, 153 + l * 18));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 57 */     for (int i1 = 0; i1 < 9; i1++) {
/* 58 */       func_75146_a(new Slot((IInventory)playerInventory, i1, 8 + i1 * 18, 211));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer var1) {
/* 64 */     return this.workbench.func_70300_a(var1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 69 */     super.func_75142_b();
/* 70 */     for (Object crafter : this.field_75149_d) {
/* 71 */       this.workbench.sendGUINetworkData((Container)this, (ICrafting)crafter);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75137_b(int par1, int par2) {
/* 77 */     this.workbench.getGUINetworkData(par1, par2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\ContainerAdvancedCraftingTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
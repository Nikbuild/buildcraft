/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.slots.SlotOutput;
/*    */ import buildcraft.core.lib.gui.slots.SlotUntouchable;
/*    */ import buildcraft.core.lib.gui.slots.SlotValidated;
/*    */ import buildcraft.silicon.TileIntegrationTable;
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
/*    */ public class ContainerIntegrationTable
/*    */   extends BuildCraftContainer
/*    */ {
/* 23 */   public static final int[] SLOT_X = new int[] { 44, 44, 69, 69, 69, 44, 19, 19, 19 };
/*    */ 
/*    */   
/* 26 */   public static final int[] SLOT_Y = new int[] { 49, 24, 24, 49, 74, 74, 74, 49, 24 };
/*    */ 
/*    */   
/*    */   private TileIntegrationTable table;
/*    */ 
/*    */   
/*    */   public ContainerIntegrationTable(InventoryPlayer playerInventory, TileIntegrationTable table) {
/* 33 */     super(table.func_70302_i_());
/* 34 */     this.table = table;
/*    */     
/* 36 */     for (int i = 0; i < 9; i++) {
/* 37 */       addSlot((Slot)new SlotValidated((IInventory)table, i, SLOT_X[i], SLOT_Y[i]));
/*    */     }
/*    */     
/* 40 */     addSlot((Slot)new SlotOutput((IInventory)table, 9, 138, 49));
/* 41 */     addSlot((Slot)new SlotUntouchable(table.clientOutputInv, 0, 101, 36));
/*    */     
/* 43 */     for (int y = 0; y < 3; y++) {
/* 44 */       for (int j = 0; j < 9; j++) {
/* 45 */         func_75146_a(new Slot((IInventory)playerInventory, j + y * 9 + 9, 8 + j * 18, 109 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 49 */     for (int x = 0; x < 9; x++) {
/* 50 */       func_75146_a(new Slot((IInventory)playerInventory, x, 8 + x * 18, 167));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer var1) {
/* 56 */     return this.table.func_70300_a(var1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 61 */     super.func_75142_b();
/* 62 */     for (Object crafter : this.field_75149_d) {
/* 63 */       this.table.sendGUINetworkData((Container)this, (ICrafting)crafter);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75137_b(int par1, int par2) {
/* 69 */     this.table.getGUINetworkData(par1, par2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\ContainerIntegrationTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
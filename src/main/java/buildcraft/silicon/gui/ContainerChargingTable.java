/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.slots.SlotValidated;
/*    */ import buildcraft.silicon.TileChargingTable;
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
/*    */ public class ContainerChargingTable
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   private TileChargingTable table;
/*    */   
/*    */   public ContainerChargingTable(InventoryPlayer playerInventory, TileChargingTable table) {
/* 25 */     super(table.func_70302_i_());
/* 26 */     this.table = table;
/*    */     
/* 28 */     addSlot((Slot)new SlotValidated((IInventory)table, 0, 80, 18));
/*    */     
/* 30 */     for (int y = 0; y < 3; y++) {
/* 31 */       for (int i = 0; i < 9; i++) {
/* 32 */         func_75146_a(new Slot((IInventory)playerInventory, i + y * 9 + 9, 8 + i * 18, 50 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 36 */     for (int x = 0; x < 9; x++) {
/* 37 */       func_75146_a(new Slot((IInventory)playerInventory, x, 8 + x * 18, 108));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer var1) {
/* 43 */     return this.table.func_70300_a(var1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75137_b(int i, int j) {
/* 48 */     this.table.getGUINetworkData(i, j);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 53 */     super.func_75142_b();
/*    */     
/* 55 */     for (Object crafter : this.field_75149_d)
/* 56 */       this.table.sendGUINetworkData((Container)this, (ICrafting)crafter); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\ContainerChargingTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
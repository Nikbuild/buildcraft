/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.slots.SlotOutput;
/*    */ import buildcraft.core.lib.gui.slots.SlotValidated;
/*    */ import buildcraft.silicon.TileStampingTable;
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
/*    */ public class ContainerStampingTable
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   private TileStampingTable table;
/*    */   
/*    */   public ContainerStampingTable(InventoryPlayer playerInventory, TileStampingTable table) {
/* 26 */     super(table.func_70302_i_());
/* 27 */     this.table = table;
/*    */     
/* 29 */     addSlot((Slot)new SlotValidated((IInventory)table, 0, 15, 18));
/* 30 */     addSlot((Slot)new SlotOutput((IInventory)table, 1, 143, 18));
/* 31 */     addSlot((Slot)new SlotOutput((IInventory)table, 2, 111, 45));
/* 32 */     addSlot((Slot)new SlotOutput((IInventory)table, 3, 129, 45));
/* 33 */     addSlot((Slot)new SlotOutput((IInventory)table, 4, 147, 45));
/*    */     
/* 35 */     for (int y = 0; y < 3; y++) {
/* 36 */       for (int i = 0; i < 9; i++) {
/* 37 */         func_75146_a(new Slot((IInventory)playerInventory, i + y * 9 + 9, 8 + i * 18, 69 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 41 */     for (int x = 0; x < 9; x++) {
/* 42 */       func_75146_a(new Slot((IInventory)playerInventory, x, 8 + x * 18, 127));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer var1) {
/* 48 */     return this.table.func_70300_a(var1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 53 */     super.func_75142_b();
/* 54 */     for (Object crafter : this.field_75149_d) {
/* 55 */       this.table.sendGUINetworkData((Container)this, (ICrafting)crafter);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75137_b(int par1, int par2) {
/* 61 */     this.table.getGUINetworkData(par1, par2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\ContainerStampingTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
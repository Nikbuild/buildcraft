/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.silicon.TileProgrammingTable;
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ public class ContainerProgrammingTable
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   IInventory playerIInventory;
/*    */   TileProgrammingTable table;
/*    */   
/*    */   public ContainerProgrammingTable(IInventory playerInventory, TileProgrammingTable table) {
/* 24 */     super(table.func_70302_i_());
/* 25 */     this.playerIInventory = playerInventory;
/*    */     
/* 27 */     func_75146_a(new Slot((IInventory)table, 0, 8, 36));
/* 28 */     func_75146_a(new Slot((IInventory)table, 1, 8, 90));
/*    */     
/* 30 */     for (int l = 0; l < 3; l++) {
/* 31 */       for (int k1 = 0; k1 < 9; k1++) {
/* 32 */         func_75146_a(new Slot(playerInventory, k1 + l * 9 + 9, 8 + k1 * 18, 123 + l * 18));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 37 */     for (int i1 = 0; i1 < 9; i1++) {
/* 38 */       func_75146_a(new Slot(playerInventory, i1, 8 + i1 * 18, 181));
/*    */     }
/*    */     
/* 41 */     this.table = table;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 46 */     return this.table.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75137_b(int i, int j) {
/* 51 */     this.table.getGUINetworkData(i, j);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 56 */     super.func_75142_b();
/*    */     
/* 58 */     for (Object crafter : this.field_75149_d)
/* 59 */       this.table.sendGUINetworkData((Container)this, (ICrafting)crafter); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\ContainerProgrammingTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
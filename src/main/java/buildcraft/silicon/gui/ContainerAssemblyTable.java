/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.silicon.TileAssemblyTable;
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
/*    */ public class ContainerAssemblyTable
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   IInventory playerIInventory;
/*    */   TileAssemblyTable table;
/*    */   
/*    */   public ContainerAssemblyTable(IInventory playerInventory, TileAssemblyTable table) {
/* 24 */     super(table.func_70302_i_());
/* 25 */     this.playerIInventory = playerInventory;
/*    */     int l;
/* 27 */     for (l = 0; l < 4; l++) {
/* 28 */       for (int k1 = 0; k1 < 3; k1++) {
/* 29 */         func_75146_a(new Slot((IInventory)table, k1 + l * 3, 8 + k1 * 18, 36 + l * 18));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 34 */     for (l = 0; l < 3; l++) {
/* 35 */       for (int k1 = 0; k1 < 9; k1++) {
/* 36 */         func_75146_a(new Slot(playerInventory, k1 + l * 9 + 9, 8 + k1 * 18, 123 + l * 18));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 41 */     for (int i1 = 0; i1 < 9; i1++) {
/* 42 */       func_75146_a(new Slot(playerInventory, i1, 8 + i1 * 18, 181));
/*    */     }
/*    */     
/* 45 */     this.table = table;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 50 */     return this.table.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75137_b(int i, int j) {
/* 55 */     this.table.getGUINetworkData(i, j);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 60 */     super.func_75142_b();
/*    */     
/* 62 */     for (Object crafter : this.field_75149_d)
/* 63 */       this.table.sendGUINetworkData((Container)this, (ICrafting)crafter); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\ContainerAssemblyTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
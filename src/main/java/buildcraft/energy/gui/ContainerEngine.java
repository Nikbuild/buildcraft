/*    */ package buildcraft.energy.gui;
/*    */ 
/*    */ import buildcraft.core.lib.engines.TileEngineWithInventory;
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
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
/*    */ 
/*    */ public class ContainerEngine
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   protected TileEngineWithInventory engine;
/*    */   
/*    */   public ContainerEngine(InventoryPlayer inventoryplayer, TileEngineWithInventory tileEngine) {
/* 25 */     super(tileEngine.func_70302_i_());
/*    */     
/* 27 */     this.engine = tileEngine;
/*    */     
/* 29 */     if (tileEngine instanceof buildcraft.energy.TileEngineStone) {
/* 30 */       func_75146_a(new Slot((IInventory)tileEngine, 0, 80, 41));
/*    */     } else {
/* 32 */       func_75146_a(new Slot((IInventory)tileEngine, 0, 52, 41));
/*    */     } 
/*    */     
/* 35 */     for (int i = 0; i < 3; i++) {
/* 36 */       for (int k = 0; k < 9; k++) {
/* 37 */         func_75146_a(new Slot((IInventory)inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 42 */     for (int j = 0; j < 9; j++) {
/* 43 */       func_75146_a(new Slot((IInventory)inventoryplayer, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 49 */     super.func_75142_b();
/*    */     
/* 51 */     for (Object crafter : this.field_75149_d) {
/* 52 */       this.engine.sendGUINetworkData((Container)this, (ICrafting)crafter);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75137_b(int i, int j) {
/* 58 */     this.engine.getGUINetworkData(i, j);
/*    */   }
/*    */   
/*    */   public boolean isUsableByPlayer(EntityPlayer entityplayer) {
/* 62 */     return this.engine.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 67 */     return this.engine.func_70300_a(entityplayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\gui\ContainerEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
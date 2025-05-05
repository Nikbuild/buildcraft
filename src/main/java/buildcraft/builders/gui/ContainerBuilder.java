/*    */ package buildcraft.builders.gui;
/*    */ 
/*    */ import buildcraft.builders.TileBuilder;
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.widgets.ScrollbarWidget;
/*    */ import buildcraft.core.lib.gui.widgets.Widget;
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
/*    */ public class ContainerBuilder
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   protected ScrollbarWidget scrollbarWidget;
/*    */   protected IInventory playerIInventory;
/*    */   protected TileBuilder builder;
/*    */   
/*    */   public ContainerBuilder(IInventory playerInventory, TileBuilder builder) {
/* 26 */     super(builder.func_70302_i_());
/* 27 */     this.playerIInventory = playerInventory;
/* 28 */     this.builder = builder;
/*    */     
/* 30 */     this.scrollbarWidget = new ScrollbarWidget(172, 17, 18, 0, 108);
/* 31 */     this.scrollbarWidget.hidden = true;
/* 32 */     addWidget((Widget)this.scrollbarWidget);
/*    */     
/* 34 */     func_75146_a(new Slot((IInventory)builder, 0, 80, 27));
/*    */     
/* 36 */     for (int k = 0; k < 3; k++) {
/* 37 */       for (int j1 = 0; j1 < 9; j1++) {
/* 38 */         func_75146_a(new Slot((IInventory)builder, 1 + j1 + k * 9, 8 + j1 * 18, 72 + k * 18));
/*    */       }
/*    */     } 
/*    */     
/* 42 */     for (int y = 0; y < 3; y++) {
/* 43 */       for (int i = 0; i < 9; i++) {
/* 44 */         func_75146_a(new Slot(playerInventory, i + y * 9 + 9, 8 + i * 18, 140 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 48 */     for (int x = 0; x < 9; x++) {
/* 49 */       func_75146_a(new Slot(playerInventory, x, 8 + x * 18, 198));
/*    */     }
/*    */     
/* 52 */     if (!(builder.func_145831_w()).field_72995_K && playerInventory instanceof InventoryPlayer) {
/*    */ 
/*    */       
/* 55 */       builder.updateRequirementsOnGuiOpen(((InventoryPlayer)playerInventory).field_70458_d);
/* 56 */       builder.addGuiWatcher(((InventoryPlayer)playerInventory).field_70458_d);
/*    */     } 
/*    */   }
/*    */   
/*    */   public TileBuilder getBuilder() {
/* 61 */     return this.builder;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75134_a(EntityPlayer player) {
/* 66 */     super.func_75134_a(player);
/* 67 */     this.builder.removeGuiWatcher(player);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 72 */     return this.builder.func_70300_a(entityplayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\gui\ContainerBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
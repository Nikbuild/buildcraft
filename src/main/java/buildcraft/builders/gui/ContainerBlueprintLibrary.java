/*    */ package buildcraft.builders.gui;
/*    */ 
/*    */ import buildcraft.builders.TileBlueprintLibrary;
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.slots.SlotOutput;
/*    */ import buildcraft.core.lib.gui.widgets.ScrollbarWidget;
/*    */ import buildcraft.core.lib.gui.widgets.Widget;
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
/*    */ public class ContainerBlueprintLibrary
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   protected ScrollbarWidget scrollbarWidget;
/*    */   protected IInventory playerInventory;
/*    */   protected TileBlueprintLibrary library;
/*    */   private int progressIn;
/*    */   private int progressOut;
/*    */   
/*    */   public ContainerBlueprintLibrary(EntityPlayer player, TileBlueprintLibrary library) {
/* 29 */     super(library.func_70302_i_());
/* 30 */     this.playerInventory = (IInventory)player.field_71071_by;
/* 31 */     this.library = library;
/*    */     
/* 33 */     this.scrollbarWidget = new ScrollbarWidget(163, 21, 244, 0, 110);
/* 34 */     this.scrollbarWidget.hidden = true;
/* 35 */     addWidget((Widget)this.scrollbarWidget);
/*    */     
/* 37 */     func_75146_a((Slot)new SlotBlueprintLibrary((IInventory)library, player, 0, 219, 57));
/* 38 */     func_75146_a((Slot)new SlotOutput((IInventory)library, 1, 175, 57));
/*    */     
/* 40 */     func_75146_a((Slot)new SlotBlueprintLibrary((IInventory)library, player, 2, 175, 79));
/* 41 */     func_75146_a((Slot)new SlotOutput((IInventory)library, 3, 219, 79));
/*    */ 
/*    */     
/* 44 */     for (int l = 0; l < 3; l++) {
/* 45 */       for (int k1 = 0; k1 < 9; k1++) {
/* 46 */         func_75146_a(new Slot(this.playerInventory, k1 + l * 9 + 9, 8 + k1 * 18, 138 + l * 18));
/*    */       }
/*    */     } 
/*    */     
/* 50 */     for (int i1 = 0; i1 < 9; i1++) {
/* 51 */       func_75146_a(new Slot(this.playerInventory, i1, 8 + i1 * 18, 196));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 57 */     super.func_75142_b();
/* 58 */     for (Object crafter : this.field_75149_d) {
/* 59 */       ICrafting icrafting = (ICrafting)crafter;
/* 60 */       if (this.progressIn != this.library.progressIn) {
/* 61 */         icrafting.func_71112_a((Container)this, 0, this.library.progressIn);
/*    */       }
/* 63 */       if (this.progressOut != this.library.progressOut) {
/* 64 */         icrafting.func_71112_a((Container)this, 1, this.library.progressOut);
/*    */       }
/*    */     } 
/*    */     
/* 68 */     this.progressIn = this.library.progressIn;
/* 69 */     this.progressOut = this.library.progressOut;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75137_b(int i, int j) {
/* 74 */     if (i == 0) {
/* 75 */       this.library.progressIn = j;
/* 76 */     } else if (i == 1) {
/* 77 */       this.library.progressOut = j;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 83 */     return this.library.func_70300_a(entityplayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\gui\ContainerBlueprintLibrary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
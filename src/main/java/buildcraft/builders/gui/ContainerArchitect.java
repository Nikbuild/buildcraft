/*    */ package buildcraft.builders.gui;
/*    */ 
/*    */ import buildcraft.builders.TileArchitect;
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.slots.SlotOutput;
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
/*    */ 
/*    */ public class ContainerArchitect
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   protected IInventory playerIInventory;
/*    */   protected TileArchitect architect;
/* 24 */   protected int computingTime = 0;
/*    */   
/*    */   public ContainerArchitect(EntityPlayer player, TileArchitect template) {
/* 27 */     super(template.func_70302_i_());
/* 28 */     this.playerIInventory = (IInventory)player.field_71071_by;
/* 29 */     this.architect = template;
/*    */     
/* 31 */     func_75146_a((Slot)new SlotArchitect((IInventory)template, player, 0, 135, 35));
/* 32 */     func_75146_a((Slot)new SlotOutput((IInventory)template, 1, 194, 35));
/*    */     
/* 34 */     for (int l = 0; l < 3; l++) {
/* 35 */       for (int k1 = 0; k1 < 9; k1++) {
/* 36 */         func_75146_a(new Slot((IInventory)player.field_71071_by, k1 + l * 9 + 9, 88 + k1 * 18, 84 + l * 18));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 41 */     for (int i1 = 0; i1 < 9; i1++) {
/* 42 */       func_75146_a(new Slot((IInventory)player.field_71071_by, i1, 88 + i1 * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_75132_a(ICrafting icrafting) {
/* 50 */     super.func_75132_a(icrafting);
/* 51 */     icrafting.func_71112_a((Container)this, 0, this.architect.getComputingProgressScaled(24));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 57 */     super.func_75142_b();
/*    */     
/* 59 */     for (Object crafter : this.field_75149_d) {
/* 60 */       ICrafting icrafting = (ICrafting)crafter;
/* 61 */       if (this.computingTime != this.architect.getComputingProgressScaled(24)) {
/* 62 */         icrafting.func_71112_a((Container)this, 0, this.architect.getComputingProgressScaled(24));
/*    */       }
/*    */     } 
/*    */     
/* 66 */     this.computingTime = this.architect.getComputingProgressScaled(24);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75137_b(int i, int j) {
/* 71 */     if (i == 0) {
/* 72 */       this.computingTime = j;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 78 */     return this.architect.func_70300_a(entityplayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\gui\ContainerArchitect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
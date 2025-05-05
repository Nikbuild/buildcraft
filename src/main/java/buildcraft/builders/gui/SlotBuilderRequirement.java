/*    */ package buildcraft.builders.gui;
/*    */ 
/*    */ import buildcraft.core.blueprints.RequirementItemStack;
/*    */ import buildcraft.core.lib.gui.AdvancedSlot;
/*    */ import buildcraft.core.lib.gui.GuiAdvancedInterface;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class SlotBuilderRequirement
/*    */   extends AdvancedSlot
/*    */ {
/*    */   public RequirementItemStack stack;
/*    */   
/*    */   public SlotBuilderRequirement(GuiAdvancedInterface gui, int x, int y) {
/* 15 */     super(gui, x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 20 */     return (this.stack != null) ? this.stack.stack : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawStack(ItemStack item) {
/* 25 */     int cornerX = (this.gui.field_146294_l - this.gui.getXSize()) / 2;
/* 26 */     int cornerY = (this.gui.field_146295_m - this.gui.getYSize()) / 2;
/*    */     
/* 28 */     this.gui.drawStack(item, cornerX + this.x, cornerY + this.y);
/*    */     
/* 30 */     if (this.stack != null) {
/*    */       
/* 32 */       String s = String.valueOf((this.stack.size > 999) ? (Math.min(99, this.stack.size / 1000) + "K") : Integer.valueOf(this.stack.size));
/* 33 */       GL11.glDisable(2896);
/* 34 */       GL11.glDisable(2929);
/* 35 */       GL11.glDisable(3042);
/* 36 */       this.gui.getFontRenderer().func_78261_a(s, cornerX + this.x + 17 - this.gui
/* 37 */           .getFontRenderer().func_78256_a(s), cornerY + this.y + 9, 16777215);
/* 38 */       GL11.glEnable(2896);
/* 39 */       GL11.glEnable(2929);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\gui\SlotBuilderRequirement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
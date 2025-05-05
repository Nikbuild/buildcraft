/*    */ package buildcraft.factory.gui;
/*    */ 
/*    */ import buildcraft.factory.TileHopper;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiHopper
/*    */   extends GuiContainer
/*    */ {
/* 21 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftfactory:textures/gui/hopper_gui.png");
/*    */   
/*    */   public GuiHopper(InventoryPlayer inventory, TileHopper tile) {
/* 24 */     super((Container)new ContainerHopper(inventory, tile));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 29 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 30 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 31 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 36 */     super.func_146979_b(par1, par2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\gui\GuiHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
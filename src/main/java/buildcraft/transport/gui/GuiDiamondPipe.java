/*    */ package buildcraft.transport.gui;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.transport.IDiamondPipe;
/*    */ import net.minecraft.inventory.IInventory;
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
/*    */ 
/*    */ public class GuiDiamondPipe
/*    */   extends GuiBuildCraft
/*    */ {
/*    */   private static final ResourceLocation TEXTURE;
/*    */   IInventory playerInventory;
/*    */   IInventory filterInventory;
/*    */   
/*    */   static {
/* 28 */     if (!BuildCraftCore.colorBlindMode) {
/* 29 */       TEXTURE = new ResourceLocation("buildcrafttransport:textures/gui/filter.png");
/*    */     } else {
/* 31 */       TEXTURE = new ResourceLocation("buildcrafttransport:textures/gui/filter_cb.png");
/*    */     } 
/*    */   }
/*    */   
/*    */   public GuiDiamondPipe(IInventory playerInventory, IDiamondPipe pipe) {
/* 36 */     super(new ContainerDiamondPipe(playerInventory, pipe), pipe.getFilters(), TEXTURE);
/* 37 */     this.playerInventory = playerInventory;
/* 38 */     this.filterInventory = pipe.getFilters();
/* 39 */     this.field_146999_f = 175;
/* 40 */     this.field_147000_g = 225;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 45 */     this.field_146289_q.func_78276_b(this.filterInventory.func_145825_b(), getCenteredOffset(this.filterInventory.func_145825_b()), 6, 4210752);
/* 46 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 97, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 51 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 52 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 53 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gui\GuiDiamondPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
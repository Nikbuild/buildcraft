/*    */ package buildcraft.transport.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.transport.pipes.PipeItemsEmzuli;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiEmzuliPipe
/*    */   extends GuiBuildCraft
/*    */ {
/* 20 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcrafttransport:textures/gui/pipe_emzuli.png");
/*    */   IInventory filterInventory;
/*    */   PipeItemsEmzuli pipe;
/*    */   
/*    */   public GuiEmzuliPipe(IInventory playerInventory, PipeItemsEmzuli pipe) {
/* 25 */     super(new ContainerEmzuliPipe(playerInventory, pipe), pipe.getFilters(), TEXTURE);
/*    */     
/* 27 */     this.pipe = pipe;
/* 28 */     this.filterInventory = pipe.getFilters();
/*    */     
/* 30 */     this.field_146999_f = 176;
/* 31 */     this.field_147000_g = 166;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 36 */     String title = StringUtils.localize("gui.pipes.emzuli.title");
/* 37 */     this.field_146289_q.func_78276_b(title, (this.field_146999_f - this.field_146289_q.func_78256_a(title)) / 2, 6, 4210752);
/* 38 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 93, 4210752);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gui\GuiEmzuliPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
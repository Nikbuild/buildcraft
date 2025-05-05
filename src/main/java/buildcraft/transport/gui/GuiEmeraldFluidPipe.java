/*    */ package buildcraft.transport.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.transport.pipes.PipeFluidsEmerald;
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
/*    */ public class GuiEmeraldFluidPipe
/*    */   extends GuiBuildCraft
/*    */ {
/* 22 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftcore:textures/gui/generic_one_slot.png");
/*    */   IInventory playerInventory;
/*    */   IInventory filterInventory;
/*    */   
/*    */   public GuiEmeraldFluidPipe(IInventory playerInventory, PipeFluidsEmerald pipe) {
/* 27 */     super(new ContainerEmeraldFluidPipe(playerInventory, pipe), pipe.getFilters(), TEXTURE);
/* 28 */     this.playerInventory = playerInventory;
/* 29 */     this.filterInventory = pipe.getFilters();
/* 30 */     this.field_146999_f = 176;
/* 31 */     this.field_147000_g = 132;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 36 */     this.field_146289_q.func_78276_b(this.filterInventory.func_145825_b(), getCenteredOffset(this.filterInventory.func_145825_b()), 6, 4210752);
/* 37 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 97, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 42 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 43 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 44 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gui\GuiEmeraldFluidPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
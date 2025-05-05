/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.silicon.TileAdvancedCraftingTable;
/*    */ import buildcraft.silicon.TileLaserTableBase;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
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
/*    */ public class GuiAdvancedCraftingTable
/*    */   extends GuiLaserTable
/*    */ {
/* 20 */   public static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftsilicon:textures/gui/assembly_advancedworkbench.png");
/*    */   private final TileAdvancedCraftingTable workbench;
/*    */   
/*    */   public GuiAdvancedCraftingTable(InventoryPlayer playerInventory, TileAdvancedCraftingTable advancedWorkbench) {
/* 24 */     super(playerInventory, new ContainerAdvancedCraftingTable(playerInventory, advancedWorkbench), (TileLaserTableBase)advancedWorkbench, TEXTURE);
/* 25 */     this.workbench = advancedWorkbench;
/* 26 */     this.field_146999_f = 176;
/* 27 */     this.field_147000_g = 240;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int mouseX, int mouseY) {
/* 32 */     super.func_146976_a(f, mouseX, mouseY);
/* 33 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 34 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 35 */     if (this.workbench.getEnergy() > 0) {
/* 36 */       int progress = this.workbench.getProgressScaled(24);
/* 37 */       func_73729_b(this.field_147003_i + 93, this.field_147009_r + 32, 176, 0, progress + 1, 18);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\GuiAdvancedCraftingTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
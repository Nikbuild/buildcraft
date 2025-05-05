/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.silicon.TileIntegrationTable;
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
/*    */ public class GuiIntegrationTable
/*    */   extends GuiLaserTable
/*    */ {
/* 20 */   public static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftsilicon:textures/gui/integration_table.png");
/*    */   private static final int FLASH_DELAY = 3;
/*    */   private final TileIntegrationTable integrationTable;
/*    */   private boolean flash;
/*    */   private int flashDelay;
/*    */   
/*    */   public GuiIntegrationTable(InventoryPlayer playerInventory, TileIntegrationTable table) {
/* 27 */     super(playerInventory, new ContainerIntegrationTable(playerInventory, table), (TileLaserTableBase)table, TEXTURE);
/* 28 */     this.integrationTable = table;
/* 29 */     this.field_146999_f = 176;
/* 30 */     this.field_147000_g = 186;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_73876_c() {
/* 35 */     super.func_73876_c();
/*    */     
/* 37 */     if (this.flashDelay <= 0) {
/* 38 */       this.flashDelay = 3;
/* 39 */       this.flash = !this.flash;
/*    */     } else {
/* 41 */       this.flashDelay--;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 47 */     drawLedgers(par1, par2);
/*    */     
/* 49 */     String title = this.table.func_145825_b();
/* 50 */     this.field_146289_q.func_78276_b(title, getCenteredOffset(title), 6, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int mouseX, int mouseY) {
/* 55 */     super.func_146976_a(f, mouseX, mouseY);
/* 56 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 57 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 58 */     if (this.integrationTable.getEnergy() > 0) {
/* 59 */       int h = this.table.getProgressScaled(69);
/* 60 */       func_73729_b(this.field_147003_i + 164, this.field_147009_r + 18 + 74 - h, 176, 18, 4, h);
/*    */     } 
/* 62 */     if (this.integrationTable.getMaxExpansionCount() > 0)
/* 63 */       for (int i = 8; i > this.integrationTable.getMaxExpansionCount(); i--)
/* 64 */         func_73729_b(this.field_147003_i + ContainerIntegrationTable.SLOT_X[i] - 1, this.field_147009_r + ContainerIntegrationTable.SLOT_Y[i] - 1, 180, 17, 18, 18);  
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\GuiIntegrationTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
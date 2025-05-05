/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.silicon.TileLaserTableBase;
/*    */ import buildcraft.silicon.TileStampingTable;
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
/*    */ public class GuiStampingTable
/*    */   extends GuiLaserTable
/*    */ {
/* 20 */   public static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftsilicon:textures/gui/stamper.png");
/*    */   private static final int FLASH_DELAY = 3;
/*    */   private final TileStampingTable integrationTable;
/*    */   private boolean flash;
/*    */   private int flashDelay;
/*    */   
/*    */   public GuiStampingTable(InventoryPlayer playerInventory, TileStampingTable table) {
/* 27 */     super(playerInventory, new ContainerStampingTable(playerInventory, table), (TileLaserTableBase)table, TEXTURE);
/* 28 */     this.integrationTable = table;
/* 29 */     this.field_146999_f = 176;
/* 30 */     this.field_147000_g = 151;
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
/* 50 */     this.field_146289_q.func_78276_b(title, getCenteredOffset(title), 5, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int mouseX, int mouseY) {
/* 55 */     super.func_146976_a(f, mouseX, mouseY);
/* 56 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 57 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 58 */     if (this.integrationTable.getEnergy() > 0) {
/* 59 */       int progress = this.integrationTable.getProgressScaled(98);
/* 60 */       func_73729_b(this.field_147003_i + 36, this.field_147009_r + 14, 0, this.flash ? 197 : 221, progress, 24);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\GuiStampingTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
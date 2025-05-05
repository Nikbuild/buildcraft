/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.silicon.TilePackager;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
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
/*    */ public class GuiPackager
/*    */   extends GuiBuildCraft
/*    */ {
/* 22 */   public static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftsilicon:textures/gui/packager.png");
/*    */   private TilePackager bench;
/*    */   
/*    */   public GuiPackager(InventoryPlayer inventoryplayer, TilePackager tile) {
/* 26 */     super(new ContainerPackager(inventoryplayer, tile), (IInventory)tile, TEXTURE);
/* 27 */     this.bench = tile;
/* 28 */     this.field_146999_f = 176;
/* 29 */     this.field_147000_g = 197;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 34 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int mouseX, int mouseY) {
/* 39 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 40 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 41 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/* 42 */     for (int y = 0; y < 3; y++) {
/* 43 */       for (int x = 0; x < 3; x++) {
/* 44 */         if (this.bench.isPatternSlotSet(y * 3 + x))
/* 45 */           func_73729_b(this.field_147003_i + 29 + x * 18, this.field_147009_r + 16 + y * 18, this.field_146999_f, 0, 18, 18); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\GuiPackager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
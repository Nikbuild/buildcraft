/*    */ package buildcraft.energy.gui;
/*    */ 
/*    */ import buildcraft.core.lib.engines.TileEngineWithInventory;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.energy.TileEngineStone;
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
/*    */ public class GuiStoneEngine
/*    */   extends GuiEngine
/*    */ {
/* 21 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftenergy:textures/gui/steam_engine_gui.png");
/*    */   
/*    */   public GuiStoneEngine(InventoryPlayer inventoryplayer, TileEngineStone tileEngine) {
/* 24 */     super(new ContainerEngine(inventoryplayer, (TileEngineWithInventory)tileEngine), (IInventory)tileEngine, TEXTURE);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 29 */     super.func_146979_b(par1, par2);
/* 30 */     String title = StringUtils.localize("tile.engineStone.name");
/* 31 */     this.field_146289_q.func_78276_b(title, getCenteredOffset(title), 6, 4210752);
/* 32 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 37 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 38 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 39 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */     
/* 41 */     TileEngineStone engine = (TileEngineStone)this.tile;
/* 42 */     if (engine.getScaledBurnTime(12) > 0) {
/* 43 */       int l = engine.getScaledBurnTime(12);
/*    */       
/* 45 */       func_73729_b(this.field_147003_i + 80, this.field_147009_r + 24 + 12 - l, 176, 12 - l, 14, l + 2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\gui\GuiStoneEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
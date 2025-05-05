/*    */ package buildcraft.energy.gui;
/*    */ 
/*    */ import buildcraft.core.lib.engines.TileEngineWithInventory;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.energy.TileEngineIron;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiCombustionEngine
/*    */   extends GuiEngine
/*    */ {
/* 24 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftenergy:textures/gui/combustion_engine_gui.png");
/*    */   
/*    */   public GuiCombustionEngine(InventoryPlayer inventoryplayer, TileEngineIron tileEngine) {
/* 27 */     super(new ContainerEngine(inventoryplayer, (TileEngineWithInventory)tileEngine), (IInventory)tileEngine, TEXTURE);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 32 */     super.func_146979_b(par1, par2);
/* 33 */     String title = StringUtils.localize("tile.engineIron.name");
/* 34 */     this.field_146289_q.func_78276_b(title, getCenteredOffset(title), 6, 4210752);
/* 35 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 96 + 2, 4210752);
/*    */     
/* 37 */     TileEngineIron engine = (TileEngineIron)this.tile;
/* 38 */     FluidStack stack = null;
/*    */     
/* 40 */     if (engine != null && 
/* 41 */       par2 >= this.field_147009_r + 19 && par2 < this.field_147009_r + 19 + 60) {
/* 42 */       if (par1 >= this.field_147003_i + 104 && par1 < this.field_147003_i + 104 + 16) {
/* 43 */         stack = engine.getFuel();
/* 44 */       } else if (par1 >= this.field_147003_i + 122 && par1 < this.field_147003_i + 122 + 16) {
/* 45 */         stack = engine.getCoolant();
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/* 50 */     if (stack != null && stack.amount > 0) {
/* 51 */       List<String> fluidTip = new ArrayList<String>();
/* 52 */       fluidTip.add(stack.getLocalizedName());
/*    */       
/* 54 */       drawHoveringText(fluidTip, par1 - this.field_147003_i, par2 - this.field_147009_r, this.field_146289_q);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 60 */     super.func_146976_a(f, x, y);
/* 61 */     TileEngineIron engine = (TileEngineIron)this.tile;
/* 62 */     if (engine != null) {
/* 63 */       drawFluid(engine.getFuel(), this.field_147003_i + 104, this.field_147009_r + 19, 16, 58, TileEngineIron.MAX_LIQUID);
/* 64 */       drawFluid(engine.getCoolant(), this.field_147003_i + 122, this.field_147009_r + 19, 16, 58, TileEngineIron.MAX_LIQUID);
/*    */     } 
/* 66 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 67 */     func_73729_b(this.field_147003_i + 104, this.field_147009_r + 19, 176, 0, 16, 60);
/* 68 */     func_73729_b(this.field_147003_i + 122, this.field_147009_r + 19, 176, 0, 16, 60);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\gui\GuiCombustionEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
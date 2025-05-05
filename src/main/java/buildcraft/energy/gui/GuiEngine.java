/*    */ package buildcraft.energy.gui;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.core.CoreIconProvider;
/*    */ import buildcraft.core.lib.engines.TileEngineBase;
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
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
/*    */ public abstract class GuiEngine
/*    */   extends GuiBuildCraft
/*    */ {
/* 25 */   private static final ResourceLocation ITEM_TEXTURE = TextureMap.field_110576_c;
/*    */   
/*    */   protected class EngineLedger
/*    */     extends GuiBuildCraft.Ledger {
/*    */     TileEngineBase engine;
/* 30 */     int headerColour = 14797103;
/* 31 */     int subheaderColour = 11186104;
/* 32 */     int textColour = 0;
/*    */     public EngineLedger(TileEngineBase engine) {
/* 34 */       super(GuiEngine.this);
/* 35 */       this.engine = engine;
/* 36 */       this.maxHeight = 94;
/* 37 */       this.overlayColor = 13921311;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void draw(int x, int y) {
/* 44 */       drawBackground(x, y);
/*    */ 
/*    */       
/* 47 */       (Minecraft.func_71410_x()).field_71446_o.func_110577_a(GuiEngine.ITEM_TEXTURE);
/* 48 */       drawIcon(BuildCraftCore.iconProvider.getIcon(CoreIconProvider.ENERGY), x + 3, y + 4);
/*    */       
/* 50 */       if (!isFullyOpened()) {
/*    */         return;
/*    */       }
/*    */       
/* 54 */       GuiEngine.this.field_146289_q.func_78261_a(StringUtils.localize("gui.energy"), x + 22, y + 8, this.headerColour);
/* 55 */       GuiEngine.this.field_146289_q.func_78261_a(StringUtils.localize("gui.currentOutput") + ":", x + 22, y + 20, this.subheaderColour);
/* 56 */       GuiEngine.this.field_146289_q.func_78276_b(String.format("%d RF/t", new Object[] { Integer.valueOf(this.engine.currentOutput) }), x + 22, y + 32, this.textColour);
/*    */       
/* 58 */       GuiEngine.this.field_146289_q.func_78261_a(StringUtils.localize("gui.stored") + ":", x + 22, y + 44, this.subheaderColour);
/* 59 */       GuiEngine.this.field_146289_q.func_78276_b(String.format("%d RF", new Object[] { Integer.valueOf(this.engine.getEnergyStored()) }), x + 22, y + 56, this.textColour);
/*    */       
/* 61 */       GuiEngine.this.field_146289_q.func_78261_a(StringUtils.localize("gui.heat") + ":", x + 22, y + 68, this.subheaderColour);
/* 62 */       GuiEngine.this.field_146289_q.func_78276_b(String.format("%.2f °C", new Object[] { Double.valueOf(this.engine.getCurrentHeatValue()) }), x + 22, y + 80, this.textColour);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public String getTooltip() {
/* 68 */       return String.format("%d RF/t", new Object[] { Integer.valueOf(this.engine.currentOutput) });
/*    */     }
/*    */   }
/*    */   
/*    */   public GuiEngine(BuildCraftContainer container, IInventory inventory, ResourceLocation texture) {
/* 73 */     super(container, inventory, texture);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void initLedgers(IInventory inventory) {
/* 78 */     super.initLedgers(inventory);
/* 79 */     if (!BuildCraftCore.hidePowerNumbers)
/* 80 */       this.ledgerManager.add(new EngineLedger((TileEngineBase)this.tile)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\gui\GuiEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
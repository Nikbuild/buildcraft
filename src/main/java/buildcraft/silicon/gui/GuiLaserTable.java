/*    */ package buildcraft.silicon.gui;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.core.CoreIconProvider;
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.silicon.TileLaserTableBase;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GuiLaserTable
/*    */   extends GuiBuildCraft
/*    */ {
/*    */   protected final TileLaserTableBase table;
/*    */   
/*    */   private class LaserTableLedger
/*    */     extends GuiBuildCraft.Ledger
/*    */   {
/* 28 */     int headerColour = 14797103;
/* 29 */     int subheaderColour = 11186104;
/* 30 */     int textColour = 0;
/*    */     public LaserTableLedger() {
/* 32 */       super(GuiLaserTable.this);
/* 33 */       this.maxHeight = 94;
/* 34 */       this.overlayColor = 13921311;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void draw(int x, int y) {
/* 41 */       drawBackground(x, y);
/*    */ 
/*    */       
/* 44 */       (Minecraft.func_71410_x()).field_71446_o.func_110577_a(TextureMap.field_110576_c);
/* 45 */       drawIcon(BuildCraftCore.iconProvider.getIcon(CoreIconProvider.ENERGY), x + 3, y + 4);
/*    */       
/* 47 */       if (!isFullyOpened()) {
/*    */         return;
/*    */       }
/*    */       
/* 51 */       GuiLaserTable.this.field_146289_q.func_78261_a(StringUtils.localize("gui.energy"), x + 22, y + 8, this.headerColour);
/* 52 */       GuiLaserTable.this.field_146289_q.func_78261_a(StringUtils.localize("gui.assemblyCurrentRequired") + ":", x + 22, y + 20, this.subheaderColour);
/* 53 */       GuiLaserTable.this.field_146289_q.func_78276_b(String.format("%d RF", new Object[] { Integer.valueOf(this.this$0.table.clientRequiredEnergy) }), x + 22, y + 32, this.textColour);
/* 54 */       GuiLaserTable.this.field_146289_q.func_78261_a(StringUtils.localize("gui.stored") + ":", x + 22, y + 44, this.subheaderColour);
/* 55 */       GuiLaserTable.this.field_146289_q.func_78276_b(String.format("%d RF", new Object[] { Integer.valueOf(this.this$0.table.getEnergy()) }), x + 22, y + 56, this.textColour);
/* 56 */       GuiLaserTable.this.field_146289_q.func_78261_a(StringUtils.localize("gui.assemblyRate") + ":", x + 22, y + 68, this.subheaderColour);
/* 57 */       GuiLaserTable.this.field_146289_q.func_78276_b(String.format("%.1f RF/t", new Object[] { Float.valueOf(this.this$0.table.getRecentEnergyAverage() / 100.0F) }), x + 22, y + 80, this.textColour);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public String getTooltip() {
/* 63 */       return String.format("%.1f RF/t", new Object[] { Float.valueOf(this.this$0.table.getRecentEnergyAverage() / 100.0F) });
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiLaserTable(InventoryPlayer playerInventory, BuildCraftContainer container, TileLaserTableBase table, ResourceLocation texture) {
/* 70 */     super(container, (IInventory)table, texture);
/* 71 */     this.table = table;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 76 */     super.func_146979_b(par1, par2);
/* 77 */     String title = this.table.func_145825_b();
/* 78 */     this.field_146289_q.func_78276_b(title, getCenteredOffset(title), 6, 4210752);
/* 79 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 97, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void initLedgers(IInventory inventory) {
/* 84 */     super.initLedgers(inventory);
/* 85 */     if (!BuildCraftCore.hidePowerNumbers)
/* 86 */       this.ledgerManager.add(new LaserTableLedger()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\GuiLaserTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.builders.gui;
/*    */ 
/*    */ import buildcraft.builders.TileFiller;
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ import buildcraft.core.lib.gui.widgets.Widget;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerFiller
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   IInventory playerIInventory;
/*    */   TileFiller tile;
/*    */   
/*    */   private class PatternWidget
/*    */     extends Widget
/*    */   {
/*    */     public PatternWidget() {
/* 32 */       super(38, 30, 0, 0, 16, 16);
/*    */     }
/*    */ 
/*    */     
/*    */     @SideOnly(Side.CLIENT)
/*    */     public void draw(GuiBuildCraft gui, int guiX, int guiY, int mouseX, int mouseY) {
/* 38 */       gui.bindTexture(TextureMap.field_110576_c);
/* 39 */       gui.func_94065_a(guiX + this.x, guiY + this.y, ContainerFiller.this.tile.currentPattern.getIcon(), 16, 16);
/*    */     }
/*    */   }
/*    */   
/*    */   public ContainerFiller(IInventory playerInventory, TileFiller tile) {
/* 44 */     super(tile.func_70302_i_());
/* 45 */     this.playerIInventory = playerInventory;
/* 46 */     this.tile = tile;
/*    */     
/* 48 */     addWidget(new PatternWidget());
/*    */     int y;
/* 50 */     for (y = 0; y < 3; y++) {
/* 51 */       for (int i = 0; i < 9; i++) {
/* 52 */         func_75146_a(new Slot((IInventory)tile, i + y * 9, 8 + i * 18, 85 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 56 */     for (y = 0; y < 3; y++) {
/* 57 */       for (int i = 0; i < 9; i++) {
/* 58 */         func_75146_a(new Slot(playerInventory, i + y * 9 + 9, 8 + i * 18, 153 + y * 18));
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 63 */     for (int x = 0; x < 9; x++) {
/* 64 */       func_75146_a(new Slot(playerInventory, x, 8 + x * 18, 211));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 70 */     return this.tile.func_70300_a(entityplayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\gui\ContainerFiller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
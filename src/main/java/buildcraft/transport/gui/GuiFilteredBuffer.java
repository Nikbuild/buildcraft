/*    */ package buildcraft.transport.gui;
/*    */ 
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.transport.TileFilteredBuffer;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
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
/*    */ public class GuiFilteredBuffer
/*    */   extends GuiContainer
/*    */ {
/* 23 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcrafttransport:textures/gui/filteredBuffer_gui.png");
/*    */   IInventory playerInventory;
/*    */   TileFilteredBuffer filteredBuffer;
/*    */   
/*    */   public GuiFilteredBuffer(InventoryPlayer playerInventory, TileFilteredBuffer filteredBuffer) {
/* 28 */     super((Container)new ContainerFilteredBuffer(playerInventory, filteredBuffer));
/*    */     
/* 30 */     this.playerInventory = (IInventory)playerInventory;
/* 31 */     this.filteredBuffer = filteredBuffer;
/* 32 */     this.field_146999_f = 175;
/* 33 */     this.field_147000_g = 169;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 38 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 40 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/*    */     
/* 42 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */     
/* 44 */     IInventory filters = this.filteredBuffer.getFilters();
/*    */     
/* 46 */     for (int col = 0; col < filters.func_70302_i_(); col++) {
/* 47 */       if (filters.func_70301_a(col) == null) {
/* 48 */         func_73729_b(this.field_147003_i + 7 + col * 18, this.field_147009_r + 60, 176, 0, 18, 18);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 55 */     String title = StringUtils.localize("tile.filteredBufferBlock.name");
/* 56 */     int xPos = (this.field_146999_f - this.field_146289_q.func_78256_a(title)) / 2;
/* 57 */     this.field_146289_q.func_78276_b(title, xPos, 10, 4210752);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gui\GuiFilteredBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
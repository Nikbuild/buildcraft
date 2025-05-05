/*    */ package buildcraft.core.lib.gui.widgets;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ import buildcraft.core.lib.gui.tooltips.IToolTipProvider;
/*    */ import buildcraft.core.lib.gui.tooltips.ToolTip;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.inventory.ICrafting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Widget
/*    */   implements IToolTipProvider
/*    */ {
/*    */   public final int x;
/*    */   public final int y;
/*    */   public final int u;
/*    */   public final int v;
/*    */   public final int w;
/*    */   public final int h;
/*    */   public boolean hidden;
/*    */   protected BuildCraftContainer container;
/*    */   
/*    */   public Widget(int x, int y, int u, int v, int w, int h) {
/* 36 */     this.x = x;
/* 37 */     this.y = y;
/* 38 */     this.u = u;
/* 39 */     this.v = v;
/* 40 */     this.w = w;
/* 41 */     this.h = h;
/*    */   }
/*    */   
/*    */   public void addToContainer(BuildCraftContainer container) {
/* 45 */     this.container = container;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public final boolean isMouseOver(int mouseX, int mouseY) {
/* 51 */     return (mouseX >= this.x - 1 && mouseX < this.x + this.w + 1 && mouseY >= this.y - 1 && mouseY < this.y + this.h + 1);
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public boolean handleMouseClick(int mouseX, int mouseY, int mouseButton) {
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void handleMouseRelease(int mouseX, int mouseY, int eventType) {}
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void handleMouseMove(int mouseX, int mouseY, int mouseButton, long time) {}
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void handleClientPacketData(DataInputStream data) throws IOException {}
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void draw(GuiBuildCraft gui, int guiX, int guiY, int mouseX, int mouseY) {
/* 73 */     gui.func_73729_b(guiX + this.x, guiY + this.y, this.u, this.v, this.w, this.h);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public ToolTip getToolTip() {
/* 79 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isToolTipVisible() {
/* 84 */     return true;
/*    */   }
/*    */   
/*    */   public void initWidget(ICrafting player) {}
/*    */   
/*    */   public void updateWidget(ICrafting player) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\widgets\Widget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
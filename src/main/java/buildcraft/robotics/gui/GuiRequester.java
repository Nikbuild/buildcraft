/*    */ package buildcraft.robotics.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.AdvancedSlot;
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.GuiAdvancedInterface;
/*    */ import buildcraft.robotics.TileRequester;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiRequester
/*    */   extends GuiAdvancedInterface
/*    */ {
/* 21 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftrobotics:textures/gui/requester_gui.png");
/*    */   private TileRequester requester;
/*    */   
/*    */   private static class RequestSlot
/*    */     extends AdvancedSlot
/*    */   {
/*    */     private int index;
/*    */     
/*    */     public RequestSlot(GuiAdvancedInterface gui, int iIndex, int x, int y) {
/* 30 */       super(gui, x, y);
/*    */       
/* 32 */       this.index = iIndex;
/*    */     }
/*    */     
/*    */     public void setItem(ItemStack itemStack) {
/* 36 */       TileRequester requester = ((GuiRequester)this.gui).requester;
/*    */       
/* 38 */       requester.setRequest(this.index, itemStack);
/* 39 */       ((GuiRequester)this.gui).getContainer().getRequestList();
/*    */     }
/*    */ 
/*    */     
/*    */     public ItemStack getItemStack() {
/* 44 */       ContainerRequester requester = ((GuiRequester)this.gui).getContainer();
/*    */       
/* 46 */       return requester.requests[this.index];
/*    */     }
/*    */   }
/*    */   
/*    */   public GuiRequester(IInventory iPlayerInventory, TileRequester iRequester) {
/* 51 */     super(new ContainerRequester(iPlayerInventory, iRequester), iPlayerInventory, TEXTURE);
/*    */     
/* 53 */     (getContainer()).gui = this;
/* 54 */     getContainer().getRequestList();
/*    */     
/* 56 */     this.field_146999_f = 196;
/* 57 */     this.field_147000_g = 181;
/*    */     
/* 59 */     this.requester = iRequester;
/*    */     
/* 61 */     for (int x = 0; x < 4; x++) {
/* 62 */       for (int y = 0; y < 5; y++) {
/* 63 */         this.slots.add(new RequestSlot(this, x * 5 + y, 9 + 18 * x, 7 + 18 * y));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 71 */     super.func_146976_a(f, x, y);
/*    */     
/* 73 */     drawBackgroundSlots(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void slotClicked(AdvancedSlot slot, int mouseButton) {
/* 78 */     super.slotClicked(slot, mouseButton);
/*    */     
/* 80 */     if (slot instanceof RequestSlot) {
/* 81 */       ((RequestSlot)slot).setItem(this.field_146297_k.field_71439_g.field_71071_by.func_70445_o());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ContainerRequester getContainer() {
/* 87 */     return (ContainerRequester)super.getContainer();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\gui\GuiRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
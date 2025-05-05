/*    */ package buildcraft.transport.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.slots.SlotPhantom;
/*    */ import buildcraft.transport.IDiamondPipe;
/*    */ import buildcraft.transport.Pipe;
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
/*    */ public class ContainerDiamondPipe
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   private final IDiamondPipe pipe;
/*    */   private final IInventory filterInv;
/*    */   
/*    */   public ContainerDiamondPipe(IInventory playerInventory, IDiamondPipe pipe) {
/* 26 */     super(pipe.getFilters().func_70302_i_());
/* 27 */     this.pipe = pipe;
/* 28 */     this.filterInv = pipe.getFilters();
/*    */     
/* 30 */     for (int y = 0; y < 6; y++) {
/* 31 */       for (int x = 0; x < 9; x++) {
/* 32 */         func_75146_a((Slot)new SlotPhantom(this.filterInv, x + y * 9, 8 + x * 18, 18 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 36 */     for (int l = 0; l < 3; l++) {
/* 37 */       for (int k1 = 0; k1 < 9; k1++) {
/* 38 */         func_75146_a(new Slot(playerInventory, k1 + l * 9 + 9, 8 + k1 * 18, 140 + l * 18));
/*    */       }
/*    */     } 
/*    */     
/* 42 */     for (int i1 = 0; i1 < 9; i1++) {
/* 43 */       func_75146_a(new Slot(playerInventory, i1, 8 + i1 * 18, 198));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 49 */     return ((Pipe)this.pipe).container.isUseableByPlayer(entityplayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gui\ContainerDiamondPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
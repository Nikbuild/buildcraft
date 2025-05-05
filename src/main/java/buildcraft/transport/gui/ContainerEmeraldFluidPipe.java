/*    */ package buildcraft.transport.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*    */ import buildcraft.core.lib.gui.slots.SlotPhantom;
/*    */ import buildcraft.transport.pipes.PipeFluidsEmerald;
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
/*    */ public class ContainerEmeraldFluidPipe
/*    */   extends BuildCraftContainer
/*    */ {
/*    */   private final PipeFluidsEmerald pipe;
/*    */   private final IInventory filterInv;
/*    */   
/*    */   public ContainerEmeraldFluidPipe(IInventory playerInventory, PipeFluidsEmerald pipe) {
/* 25 */     super(pipe.getFilters().func_70302_i_());
/* 26 */     this.pipe = pipe;
/* 27 */     this.filterInv = pipe.getFilters();
/*    */     
/* 29 */     func_75146_a((Slot)new SlotPhantom(this.filterInv, 0, 80, 18));
/*    */     
/* 31 */     for (int l = 0; l < 3; l++) {
/* 32 */       for (int k1 = 0; k1 < 9; k1++) {
/* 33 */         func_75146_a(new Slot(playerInventory, k1 + l * 9 + 9, 8 + k1 * 18, 50 + l * 18));
/*    */       }
/*    */     } 
/*    */     
/* 37 */     for (int i1 = 0; i1 < 9; i1++) {
/* 38 */       func_75146_a(new Slot(playerInventory, i1, 8 + i1 * 18, 108));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 44 */     return this.pipe.container.isUseableByPlayer(entityplayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gui\ContainerEmeraldFluidPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
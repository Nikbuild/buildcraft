/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.api.transport.IPipeTile;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
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
/*    */ public class PipeTransportStructure
/*    */   extends PipeTransport
/*    */ {
/*    */   public IPipeTile.PipeType getPipeType() {
/* 20 */     return IPipeTile.PipeType.STRUCTURE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPipeConnect(TileEntity tile, ForgeDirection side) {
/* 25 */     if (tile instanceof IPipeTile) {
/* 26 */       Pipe<?> pipe2 = (Pipe)((IPipeTile)tile).getPipe();
/*    */       
/* 28 */       if (BlockGenericPipe.isValid(pipe2) && !(pipe2.transport instanceof PipeTransportStructure)) {
/* 29 */         return false;
/*    */       }
/*    */       
/* 32 */       return true;
/*    */     } 
/*    */     
/* 35 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeTransportStructure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
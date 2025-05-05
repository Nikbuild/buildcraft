/*    */ package buildcraft.transport.pipes;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.core.IIconProvider;
/*    */ import buildcraft.transport.Pipe;
/*    */ import buildcraft.transport.PipeIconProvider;
/*    */ import buildcraft.transport.PipeTransport;
/*    */ import buildcraft.transport.PipeTransportItems;
/*    */ import buildcraft.transport.pipes.events.PipeEventItem;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.item.Item;
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
/*    */ public class PipeItemsQuartz
/*    */   extends Pipe<PipeTransportItems>
/*    */ {
/*    */   public PipeItemsQuartz(Item item) {
/* 27 */     super((PipeTransport)new PipeTransportItems(), item);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIconProvider getIconProvider() {
/* 34 */     return BuildCraftTransport.instance.pipeIconProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIconIndex(ForgeDirection direction) {
/* 39 */     return PipeIconProvider.TYPE.PipeItemsQuartz.ordinal();
/*    */   }
/*    */   
/*    */   public void eventHandler(PipeEventItem.AdjustSpeed event) {
/* 43 */     event.slowdownAmount /= 4.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsQuartz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
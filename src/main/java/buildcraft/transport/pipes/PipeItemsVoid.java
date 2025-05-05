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
/*    */ public class PipeItemsVoid
/*    */   extends Pipe<PipeTransportItems>
/*    */ {
/*    */   public PipeItemsVoid(Item item) {
/* 27 */     super((PipeTransport)new PipeTransportItems(), item);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIconProvider getIconProvider() {
/* 33 */     return BuildCraftTransport.instance.pipeIconProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIconIndex(ForgeDirection direction) {
/* 38 */     return PipeIconProvider.TYPE.PipeItemsVoid.ordinal();
/*    */   }
/*    */   
/*    */   public void eventHandler(PipeEventItem.DropItem event) {
/* 42 */     event.entity = null;
/*    */   }
/*    */   
/*    */   public void eventHandler(PipeEventItem.ReachedCenter event) {
/* 46 */     ((PipeTransportItems)this.transport).items.scheduleRemoval(event.item);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsVoid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
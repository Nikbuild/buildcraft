/*    */ package buildcraft.transport.pipes;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.core.IIconProvider;
/*    */ import buildcraft.core.lib.utils.MathUtils;
/*    */ import buildcraft.transport.Pipe;
/*    */ import buildcraft.transport.PipeIconProvider;
/*    */ import buildcraft.transport.PipeTransport;
/*    */ import buildcraft.transport.PipeTransportItems;
/*    */ import buildcraft.transport.TravelingItem;
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
/*    */ 
/*    */ public class PipeItemsGold
/*    */   extends Pipe<PipeTransportItems>
/*    */ {
/*    */   public PipeItemsGold(Item item) {
/* 30 */     super((PipeTransport)new PipeTransportItems(), item);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIconProvider getIconProvider() {
/* 36 */     return BuildCraftTransport.instance.pipeIconProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIconIndex(ForgeDirection direction) {
/* 41 */     return PipeIconProvider.TYPE.PipeItemsGold.ordinal();
/*    */   }
/*    */   
/*    */   public void eventHandler(PipeEventItem.AdjustSpeed event) {
/* 45 */     event.handled = true;
/* 46 */     TravelingItem item = event.item;
/* 47 */     item.setSpeed(MathUtils.clamp(item.getSpeed() * 4.0F, 0.04F, 0.15F));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsGold.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.transport.pipes;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.core.IIconProvider;
/*    */ import buildcraft.transport.Pipe;
/*    */ import buildcraft.transport.PipeIconProvider;
/*    */ import buildcraft.transport.PipeTransport;
/*    */ import buildcraft.transport.PipeTransportItems;
/*    */ import buildcraft.transport.pipes.events.PipeEventItem;
/*    */ import buildcraft.transport.pipes.events.PipeEventPriority;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.item.Item;
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
/*    */ 
/*    */ public class PipeItemsClay
/*    */   extends Pipe<PipeTransportItems>
/*    */ {
/*    */   public PipeItemsClay(Item item) {
/* 32 */     super((PipeTransport)new PipeTransportItems(), item);
/*    */     
/* 34 */     ((PipeTransportItems)this.transport).allowBouncing = true;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIconProvider getIconProvider() {
/* 40 */     return BuildCraftTransport.instance.pipeIconProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIconIndex(ForgeDirection direction) {
/* 45 */     return PipeIconProvider.TYPE.PipeItemsClay.ordinal();
/*    */   }
/*    */   
/*    */   @PipeEventPriority(priority = -200)
/*    */   public void eventHandler(PipeEventItem.FindDest event) {
/* 50 */     LinkedList<ForgeDirection> nonPipesList = new LinkedList<ForgeDirection>();
/* 51 */     LinkedList<ForgeDirection> pipesList = new LinkedList<ForgeDirection>();
/*    */     
/* 53 */     for (ForgeDirection o : event.destinations) {
/* 54 */       if (!event.item.blacklist.contains(o) && this.container.pipe.outputOpen(o) && 
/* 55 */         this.container.isPipeConnected(o)) {
/* 56 */         TileEntity entity = this.container.getTile(o);
/* 57 */         if (entity instanceof buildcraft.api.transport.IPipeTile) {
/* 58 */           pipesList.add(o); continue;
/*    */         } 
/* 60 */         nonPipesList.add(o);
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 66 */     event.destinations.clear();
/* 67 */     if (nonPipesList.isEmpty()) {
/* 68 */       event.destinations.addAll(pipesList);
/*    */     } else {
/* 70 */       event.destinations.addAll(nonPipesList);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsClay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
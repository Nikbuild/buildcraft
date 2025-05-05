/*    */ package buildcraft.transport.pipes.events;
/*    */ 
/*    */ import buildcraft.transport.Pipe;
/*    */ import buildcraft.transport.TravelingItem;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.item.EntityItem;
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
/*    */ 
/*    */ public abstract class PipeEventItem
/*    */   extends PipeEvent
/*    */ {
/*    */   public final TravelingItem item;
/*    */   
/*    */   public PipeEventItem(Pipe<?> pipe, TravelingItem item) {
/* 27 */     super(pipe);
/* 28 */     this.item = item;
/*    */   }
/*    */   
/*    */   public static class Entered extends PipeEventItem {
/*    */     public boolean cancelled = false;
/*    */     
/*    */     public Entered(Pipe<?> pipe, TravelingItem item) {
/* 35 */       super(pipe, item);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class ReachedCenter extends PipeEventItem {
/*    */     public ReachedCenter(Pipe<?> pipe, TravelingItem item) {
/* 41 */       super(pipe, item);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class ReachedEnd extends PipeEventItem {
/*    */     public final TileEntity dest;
/*    */     public boolean handled = false;
/*    */     
/*    */     public ReachedEnd(Pipe<?> pipe, TravelingItem item, TileEntity dest) {
/* 50 */       super(pipe, item);
/* 51 */       this.dest = dest;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class DropItem extends PipeEventItem {
/*    */     public EntityItem entity;
/*    */     public ForgeDirection direction;
/*    */     
/*    */     public DropItem(Pipe<?> pipe, TravelingItem item, EntityItem entity) {
/* 60 */       super(pipe, item);
/* 61 */       this.entity = entity;
/* 62 */       this.direction = (item.output != ForgeDirection.UNKNOWN) ? item.output : item.input;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class FindDest extends PipeEventItem {
/*    */     public final List<ForgeDirection> destinations;
/*    */     public boolean shuffle = true;
/*    */     
/*    */     public FindDest(Pipe<?> pipe, TravelingItem item, List<ForgeDirection> destinations) {
/* 71 */       super(pipe, item);
/* 72 */       this.destinations = destinations;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class AdjustSpeed extends PipeEventItem {
/*    */     public boolean handled = false;
/* 78 */     public float slowdownAmount = 0.01F;
/*    */     
/*    */     public AdjustSpeed(Pipe<?> pipe, TravelingItem item) {
/* 81 */       super(pipe, item);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\events\PipeEventItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
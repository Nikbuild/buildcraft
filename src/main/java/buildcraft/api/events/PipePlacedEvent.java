/*    */ package buildcraft.api.events;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PipePlacedEvent
/*    */   extends Event
/*    */ {
/*    */   public EntityPlayer player;
/*    */   public Item pipeType;
/*    */   public BlockPos pos;
/*    */   
/*    */   public PipePlacedEvent(EntityPlayer player, Item pipeType, BlockPos pos) {
/* 19 */     this.player = player;
/* 20 */     this.pipeType = pipeType;
/* 21 */     this.pos = pos;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\events\PipePlacedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
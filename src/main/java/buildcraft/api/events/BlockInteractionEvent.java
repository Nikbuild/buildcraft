/*    */ package buildcraft.api.events;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Cancelable
/*    */ public class BlockInteractionEvent
/*    */   extends Event
/*    */ {
/*    */   public final EntityPlayer player;
/*    */   public final IBlockState state;
/*    */   
/*    */   public BlockInteractionEvent(EntityPlayer player, IBlockState state) {
/* 19 */     this.player = player;
/* 20 */     this.state = state;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\events\BlockInteractionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
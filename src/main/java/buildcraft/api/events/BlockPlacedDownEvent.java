/*    */ package buildcraft.api.events;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Cancelable
/*    */ public class BlockPlacedDownEvent
/*    */   extends Event
/*    */ {
/*    */   public final EntityPlayer player;
/*    */   public final IBlockState state;
/*    */   public final BlockPos pos;
/*    */   
/*    */   public BlockPlacedDownEvent(EntityPlayer player, BlockPos pos, IBlockState state) {
/* 21 */     this.player = player;
/* 22 */     this.state = state;
/* 23 */     this.pos = pos;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\events\BlockPlacedDownEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
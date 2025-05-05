/*    */ package buildcraft.api.events;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Cancelable
/*    */ public class RobotPlacementEvent
/*    */   extends Event
/*    */ {
/*    */   public EntityPlayer player;
/*    */   public String robotProgram;
/*    */   
/*    */   public RobotPlacementEvent(EntityPlayer player, String robotProgram) {
/* 18 */     this.player = player;
/* 19 */     this.robotProgram = robotProgram;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\events\RobotPlacementEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
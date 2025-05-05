/*    */ package buildcraft.api.events;
/*    */ 
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class RobotEvent
/*    */   extends Event
/*    */ {
/*    */   public final EntityRobotBase robot;
/*    */   
/*    */   public RobotEvent(EntityRobotBase robot) {
/* 19 */     this.robot = robot;
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class Place extends RobotEvent {
/*    */     public final EntityPlayer player;
/*    */     
/*    */     public Place(EntityRobotBase robot, EntityPlayer player) {
/* 27 */       super(robot);
/* 28 */       this.player = player;
/*    */     }
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class Interact extends RobotEvent {
/*    */     public final EntityPlayer player;
/*    */     public final ItemStack item;
/*    */     
/*    */     public Interact(EntityRobotBase robot, EntityPlayer player, ItemStack item) {
/* 38 */       super(robot);
/* 39 */       this.player = player;
/* 40 */       this.item = item;
/*    */     }
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class Dismantle extends RobotEvent {
/*    */     public final EntityPlayer player;
/*    */     
/*    */     public Dismantle(EntityRobotBase robot, EntityPlayer player) {
/* 49 */       super(robot);
/* 50 */       this.player = player;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\events\RobotEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
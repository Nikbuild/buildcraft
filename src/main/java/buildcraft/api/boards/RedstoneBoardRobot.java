/*    */ package buildcraft.api.boards;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class RedstoneBoardRobot
/*    */   extends AIRobot
/*    */   implements IRedstoneBoard<EntityRobotBase>
/*    */ {
/*    */   public RedstoneBoardRobot(EntityRobotBase iRobot) {
/* 13 */     super(iRobot);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void updateBoard(EntityRobotBase container) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canLoadFromNBT() {
/* 26 */     return true;
/*    */   }
/*    */   
/*    */   public abstract RedstoneBoardRobotNBT getNBTHandler();
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\boards\RedstoneBoardRobot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
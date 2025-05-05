/*    */ package buildcraft.robotics.boards;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*    */ import buildcraft.robotics.ai.AIRobotBreak;
/*    */ import buildcraft.robotics.ai.AIRobotFetchAndEquipItemStack;
/*    */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*    */ import buildcraft.robotics.ai.AIRobotGotoStationAndUnload;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BoardRobotGenericBreakBlock
/*    */   extends BoardRobotGenericSearchBlock
/*    */ {
/*    */   public BoardRobotGenericBreakBlock(EntityRobotBase iRobot) {
/* 24 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract boolean isExpectedTool(ItemStack paramItemStack);
/*    */   
/*    */   public final void update() {
/* 31 */     if (!isExpectedTool((ItemStack)null) && this.robot.func_70694_bm() == null) {
/* 32 */       startDelegateAI((AIRobot)new AIRobotFetchAndEquipItemStack(this.robot, new IStackFilter()
/*    */             {
/*    */               public boolean matches(ItemStack stack) {
/* 35 */                 return (stack != null && stack
/* 36 */                   .func_77960_j() < stack.func_77958_k() && BoardRobotGenericBreakBlock.this
/* 37 */                   .isExpectedTool(stack));
/*    */               }
/*    */             }));
/* 40 */     } else if (this.robot.func_70694_bm() != null && this.robot.func_70694_bm().func_77960_j() >= this.robot.func_70694_bm().func_77958_k()) {
/* 41 */       startDelegateAI((AIRobot)new AIRobotGotoStationAndUnload(this.robot));
/* 42 */     } else if (blockFound() != null) {
/* 43 */       startDelegateAI((AIRobot)new AIRobotBreak(this.robot, blockFound()));
/*    */     } else {
/* 45 */       super.update();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 51 */     if (ai instanceof AIRobotFetchAndEquipItemStack || ai instanceof AIRobotGotoStationAndUnload) {
/* 52 */       if (!ai.success()) {
/* 53 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*    */       }
/* 55 */     } else if (ai instanceof AIRobotBreak) {
/* 56 */       releaseBlockFound(ai.success());
/*    */     } 
/* 58 */     super.delegateAIEnded(ai);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotGenericBreakBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
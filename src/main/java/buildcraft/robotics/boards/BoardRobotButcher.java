/*    */ package buildcraft.robotics.boards;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRobot;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*    */ import buildcraft.core.lib.utils.IEntityFilter;
/*    */ import buildcraft.robotics.ai.AIRobotAttack;
/*    */ import buildcraft.robotics.ai.AIRobotFetchAndEquipItemStack;
/*    */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*    */ import buildcraft.robotics.ai.AIRobotGotoStationAndUnload;
/*    */ import buildcraft.robotics.ai.AIRobotSearchEntity;
/*    */ import net.minecraft.entity.Entity;
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
/*    */ 
/*    */ public class BoardRobotButcher
/*    */   extends RedstoneBoardRobot
/*    */ {
/*    */   public BoardRobotButcher(EntityRobotBase iRobot) {
/* 31 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobotNBT getNBTHandler() {
/* 36 */     return BCBoardNBT.REGISTRY.get("butcher");
/*    */   }
/*    */ 
/*    */   
/*    */   public final void update() {
/* 41 */     if (this.robot.func_70694_bm() == null) {
/* 42 */       startDelegateAI((AIRobot)new AIRobotFetchAndEquipItemStack(this.robot, new IStackFilter()
/*    */             {
/*    */               public boolean matches(ItemStack stack) {
/* 45 */                 return stack.func_77973_b() instanceof net.minecraft.item.ItemSword;
/*    */               }
/*    */             }));
/* 48 */     } else if (this.robot.func_70694_bm() != null && this.robot.func_70694_bm().func_77960_j() >= this.robot.func_70694_bm().func_77958_k()) {
/* 49 */       startDelegateAI((AIRobot)new AIRobotGotoStationAndUnload(this.robot));
/*    */     } else {
/* 51 */       startDelegateAI((AIRobot)new AIRobotSearchEntity(this.robot, new IEntityFilter()
/*    */             {
/*    */               public boolean matches(Entity entity) {
/* 54 */                 return entity instanceof net.minecraft.entity.passive.EntityAnimal;
/*    */               }
/* 56 */             },  250.0F, this.robot.getZoneToWork()));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 62 */     if (ai instanceof AIRobotFetchAndEquipItemStack) {
/* 63 */       if (!ai.success()) {
/* 64 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*    */       }
/* 66 */     } else if (ai instanceof AIRobotSearchEntity) {
/* 67 */       if (ai.success()) {
/* 68 */         startDelegateAI((AIRobot)new AIRobotAttack(this.robot, ((AIRobotSearchEntity)ai).target));
/*    */       } else {
/* 70 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotButcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
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
/*    */ import net.minecraft.entity.passive.EntityWolf;
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
/*    */ public class BoardRobotKnight
/*    */   extends RedstoneBoardRobot
/*    */ {
/*    */   public BoardRobotKnight(EntityRobotBase iRobot) {
/* 32 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobotNBT getNBTHandler() {
/* 37 */     return BCBoardNBT.REGISTRY.get("knight");
/*    */   }
/*    */ 
/*    */   
/*    */   public final void update() {
/* 42 */     if (this.robot.func_70694_bm() == null) {
/* 43 */       startDelegateAI((AIRobot)new AIRobotFetchAndEquipItemStack(this.robot, new IStackFilter()
/*    */             {
/*    */               public boolean matches(ItemStack stack) {
/* 46 */                 return stack.func_77973_b() instanceof net.minecraft.item.ItemSword;
/*    */               }
/*    */             }));
/* 49 */     } else if (this.robot.func_70694_bm() != null && this.robot.func_70694_bm().func_77960_j() >= this.robot.func_70694_bm().func_77958_k()) {
/* 50 */       startDelegateAI((AIRobot)new AIRobotGotoStationAndUnload(this.robot));
/*    */     } else {
/* 52 */       startDelegateAI((AIRobot)new AIRobotSearchEntity(this.robot, new IEntityFilter()
/*    */             {
/*    */               public boolean matches(Entity entity) {
/* 55 */                 return (entity instanceof net.minecraft.entity.monster.IMob || (entity instanceof EntityWolf && ((EntityWolf)entity).func_70919_bu()));
/*    */               }
/* 57 */             },  250.0F, this.robot.getZoneToWork()));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 63 */     if (ai instanceof AIRobotFetchAndEquipItemStack) {
/* 64 */       if (!ai.success()) {
/* 65 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*    */       }
/* 67 */     } else if (ai instanceof AIRobotSearchEntity) {
/* 68 */       if (ai.success()) {
/* 69 */         startDelegateAI((AIRobot)new AIRobotAttack(this.robot, ((AIRobotSearchEntity)ai).target));
/*    */       } else {
/* 71 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotKnight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
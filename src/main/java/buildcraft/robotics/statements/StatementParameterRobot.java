/*    */ package buildcraft.robotics.statements;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.items.IList;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.api.statements.IStatement;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.StatementMouseClick;
/*    */ import buildcraft.api.statements.StatementParameterItemStack;
/*    */ import buildcraft.core.lib.inventory.StackHelper;
/*    */ import buildcraft.robotics.EntityRobot;
/*    */ import buildcraft.robotics.ItemRobot;
/*    */ import buildcraft.robotics.RobotUtils;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatementParameterRobot
/*    */   extends StatementParameterItemStack
/*    */ {
/*    */   public void onClick(IStatementContainer source, IStatement stmt, ItemStack stack, StatementMouseClick mouse) {
/* 23 */     if (stack == null && (this.stack == null || this.stack.func_77973_b() instanceof ItemRobot)) {
/* 24 */       RedstoneBoardRobotNBT nextBoard = RobotUtils.getNextBoard(this.stack, (mouse.getButton() > 0));
/* 25 */       if (nextBoard != null) {
/* 26 */         this.stack = ItemRobot.createRobotStack(nextBoard, 0);
/*    */       } else {
/* 28 */         this.stack = null;
/*    */       } 
/*    */     } else {
/* 31 */       super.onClick(source, stmt, stack, mouse);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUniqueTag() {
/* 37 */     return "buildcraft:robot";
/*    */   }
/*    */   
/*    */   public static boolean matches(IStatementParameter param, EntityRobotBase robot) {
/* 41 */     ItemStack stack = param.getItemStack();
/* 42 */     if (stack != null) {
/* 43 */       if (stack.func_77973_b() instanceof IList) {
/* 44 */         IList list = (IList)stack.func_77973_b();
/* 45 */         if (list.matches(stack, ItemRobot.createRobotStack(robot.getBoard().getNBTHandler(), robot.getEnergy()))) {
/* 46 */           return true;
/*    */         }
/* 48 */         for (ItemStack target : ((EntityRobot)robot).getWearables()) {
/* 49 */           if (target != null && list.matches(stack, target)) {
/* 50 */             return true;
/*    */           }
/*    */         } 
/* 53 */       } else if (stack.func_77973_b() instanceof ItemRobot) {
/* 54 */         if (ItemRobot.getRobotNBT(stack) == robot.getBoard().getNBTHandler()) {
/* 55 */           return true;
/*    */         }
/* 57 */       } else if (robot instanceof EntityRobot) {
/* 58 */         for (ItemStack target : ((EntityRobot)robot).getWearables()) {
/* 59 */           if (target != null && StackHelper.isMatchingItem(stack, target, true, true)) {
/* 60 */             return true;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     }
/* 65 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\StatementParameterRobot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
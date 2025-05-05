/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.core.IInvSlot;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.inventory.InventoryIterator;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotDisposeItems
/*    */   extends AIRobot
/*    */ {
/*    */   public AIRobotDisposeItems(EntityRobotBase iRobot) {
/* 21 */     super(iRobot);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 26 */     startDelegateAI(new AIRobotGotoStationAndUnload(this.robot));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 31 */     if (ai instanceof AIRobotGotoStationAndUnload)
/* 32 */       if (ai.success()) {
/* 33 */         if (this.robot.containsItems()) {
/* 34 */           startDelegateAI(new AIRobotGotoStationAndUnload(this.robot));
/*    */         } else {
/* 36 */           terminate();
/*    */         } 
/*    */       } else {
/* 39 */         for (IInvSlot slot : InventoryIterator.getIterable((IInventory)this.robot)) {
/* 40 */           if (slot.getStackInSlot() != null) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 46 */             EntityItem entity = new EntityItem(this.robot.field_70170_p, this.robot.field_70165_t, this.robot.field_70163_u, this.robot.field_70161_v, slot.getStackInSlot());
/*    */             
/* 48 */             this.robot.field_70170_p.func_72838_d((Entity)entity);
/*    */             
/* 50 */             slot.setStackInSlot(null);
/*    */           } 
/*    */         } 
/* 53 */         terminate();
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotDisposeItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.inventory.filters.AggregateFilter;
/*    */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*    */ import buildcraft.robotics.statements.ActionRobotFilterTool;
/*    */ import net.minecraft.inventory.IInventory;
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
/*    */ public class AIRobotFetchAndEquipItemStack
/*    */   extends AIRobot
/*    */ {
/*    */   private IStackFilter filter;
/* 23 */   private int delay = 0;
/*    */   
/*    */   public AIRobotFetchAndEquipItemStack(EntityRobotBase iRobot) {
/* 26 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotFetchAndEquipItemStack(EntityRobotBase iRobot, IStackFilter iFilter) {
/* 30 */     this(iRobot);
/*    */     
/* 32 */     this.filter = (IStackFilter)new AggregateFilter(new IStackFilter[] { ActionRobotFilterTool.getGateFilter(iRobot.getLinkedStation()), iFilter });
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 37 */     startDelegateAI(new AIRobotGotoStationToLoad(this.robot, this.filter, 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 42 */     if (this.robot.getDockingStation() == null) {
/* 43 */       setSuccess(false);
/* 44 */       terminate();
/*    */     } 
/*    */     
/* 47 */     if (this.delay++ > 40) {
/* 48 */       if (equipItemStack()) {
/* 49 */         terminate();
/*    */       } else {
/* 51 */         this.delay = 0;
/* 52 */         startDelegateAI(new AIRobotGotoStationToLoad(this.robot, this.filter, 1));
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 59 */     if (ai instanceof AIRobotGotoStationToLoad) {
/* 60 */       if (this.filter == null) {
/*    */ 
/*    */ 
/*    */         
/* 64 */         abort();
/*    */         return;
/*    */       } 
/* 67 */       if (!ai.success()) {
/* 68 */         setSuccess(false);
/* 69 */         terminate();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean equipItemStack() {
/* 75 */     IInventory tileInventory = this.robot.getDockingStation().getItemInput();
/* 76 */     if (tileInventory == null) {
/* 77 */       return false;
/*    */     }
/*    */     
/* 80 */     ItemStack possible = AIRobotLoad.takeSingle(this.robot.getDockingStation(), this.filter, true);
/* 81 */     if (possible == null) {
/* 82 */       return false;
/*    */     }
/* 84 */     this.robot.setItemInUse(possible);
/* 85 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotFetchAndEquipItemStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
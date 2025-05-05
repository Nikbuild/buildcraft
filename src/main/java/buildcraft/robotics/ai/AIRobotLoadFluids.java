/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.inventory.filters.IFluidFilter;
/*    */ import buildcraft.robotics.statements.ActionRobotFilter;
/*    */ import buildcraft.robotics.statements.ActionStationProvideFluids;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.IFluidHandler;
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
/*    */ public class AIRobotLoadFluids
/*    */   extends AIRobot
/*    */ {
/* 25 */   private int waitedCycles = 0;
/*    */   private IFluidFilter filter;
/*    */   
/*    */   public AIRobotLoadFluids(EntityRobotBase iRobot) {
/* 29 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotLoadFluids(EntityRobotBase iRobot, IFluidFilter iFilter) {
/* 33 */     this(iRobot);
/*    */     
/* 35 */     this.filter = iFilter;
/* 36 */     setSuccess(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 41 */     if (this.filter == null) {
/* 42 */       terminate();
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     this.waitedCycles++;
/*    */     
/* 48 */     if (this.waitedCycles > 40) {
/* 49 */       if (load(this.robot, this.robot.getDockingStation(), this.filter, true) == 0) {
/* 50 */         terminate();
/*    */       } else {
/* 52 */         setSuccess(true);
/* 53 */         this.waitedCycles = 0;
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static int load(EntityRobotBase robot, DockingStation station, IFluidFilter filter, boolean doLoad) {
/* 60 */     if (station == null) {
/* 61 */       return 0;
/*    */     }
/*    */     
/* 64 */     if (!ActionRobotFilter.canInteractWithFluid(station, filter, ActionStationProvideFluids.class))
/*    */     {
/* 66 */       return 0;
/*    */     }
/*    */     
/* 69 */     IFluidHandler handler = station.getFluidInput();
/* 70 */     if (handler == null) {
/* 71 */       return 0;
/*    */     }
/*    */     
/* 74 */     ForgeDirection side = station.getFluidInputSide();
/*    */     
/* 76 */     FluidStack drainable = handler.drain(side, 1000, false);
/*    */     
/* 78 */     if (drainable == null || !filter.matches(drainable.getFluid())) {
/* 79 */       return 0;
/*    */     }
/*    */     
/* 82 */     drainable = drainable.copy();
/* 83 */     int filled = robot.fill(ForgeDirection.UNKNOWN, drainable, doLoad);
/*    */     
/* 85 */     if (filled > 0 && doLoad) {
/* 86 */       drainable.amount = filled;
/* 87 */       handler.drain(side, drainable, true);
/*    */     } 
/* 89 */     return filled;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyCost() {
/* 94 */     return 8;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotLoadFluids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
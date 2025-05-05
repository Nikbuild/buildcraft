/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.lib.inventory.filters.IFluidFilter;
/*    */ import buildcraft.core.lib.inventory.filters.SimpleFluidFilter;
/*    */ import buildcraft.robotics.statements.ActionRobotFilter;
/*    */ import buildcraft.robotics.statements.ActionStationAcceptFluids;
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
/*    */ public class AIRobotUnloadFluids
/*    */   extends AIRobot
/*    */ {
/* 25 */   private int waitedCycles = 0;
/*    */   
/*    */   public AIRobotUnloadFluids(EntityRobotBase iRobot) {
/* 28 */     super(iRobot);
/* 29 */     setSuccess(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 34 */     this.waitedCycles++;
/*    */     
/* 36 */     if (this.waitedCycles > 40) {
/* 37 */       if (unload(this.robot, this.robot.getDockingStation(), true) == 0) {
/* 38 */         terminate();
/*    */       } else {
/* 40 */         setSuccess(true);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public static int unload(EntityRobotBase robot, DockingStation station, boolean doUnload) {
/* 46 */     if (station == null) {
/* 47 */       return 0;
/*    */     }
/*    */     
/* 50 */     if (!ActionRobotFilter.canInteractWithFluid(station, (IFluidFilter)new SimpleFluidFilter(
/* 51 */           (robot.getTankInfo(ForgeDirection.UNKNOWN)[0]).fluid), ActionStationAcceptFluids.class))
/*    */     {
/* 53 */       return 0;
/*    */     }
/*    */     
/* 56 */     IFluidHandler fluidHandler = station.getFluidOutput();
/* 57 */     if (fluidHandler == null) {
/* 58 */       return 0;
/*    */     }
/*    */     
/* 61 */     FluidStack drainable = robot.drain(ForgeDirection.UNKNOWN, 1000, false);
/*    */     
/* 63 */     if (drainable == null) {
/* 64 */       return 0;
/*    */     }
/*    */     
/* 67 */     drainable = drainable.copy();
/* 68 */     int filled = fluidHandler.fill(station.getFluidOutputSide(), drainable, doUnload);
/*    */     
/* 70 */     if (filled > 0 && doUnload) {
/* 71 */       drainable.amount = filled;
/* 72 */       robot.drain(ForgeDirection.UNKNOWN, drainable, true);
/*    */     } 
/* 74 */     return filled;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyCost() {
/* 79 */     return 10;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotUnloadFluids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
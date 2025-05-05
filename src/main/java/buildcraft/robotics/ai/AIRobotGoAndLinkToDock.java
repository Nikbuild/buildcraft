/*    */ package buildcraft.robotics.ai;
/*    */ 
/*    */ import buildcraft.api.core.BlockIndex;
/*    */ import buildcraft.api.robots.AIRobot;
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AIRobotGoAndLinkToDock
/*    */   extends AIRobot
/*    */ {
/*    */   private DockingStation station;
/*    */   
/*    */   public AIRobotGoAndLinkToDock(EntityRobotBase iRobot) {
/* 25 */     super(iRobot);
/*    */   }
/*    */   
/*    */   public AIRobotGoAndLinkToDock(EntityRobotBase iRobot, DockingStation iStation) {
/* 29 */     this(iRobot);
/*    */     
/* 31 */     this.station = iStation;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 36 */     if (this.station == this.robot.getLinkedStation() && this.station == this.robot.getDockingStation()) {
/* 37 */       terminate();
/*    */     }
/* 39 */     else if (this.station != null && this.station.takeAsMain(this.robot)) {
/* 40 */       startDelegateAI(new AIRobotGotoBlock(this.robot, this.station
/* 41 */             .x() + (this.station.side()).offsetX * 2, this.station
/* 42 */             .y() + (this.station.side()).offsetY * 2, this.station
/* 43 */             .z() + (this.station.side()).offsetZ * 2));
/*    */     } else {
/* 45 */       setSuccess(false);
/* 46 */       terminate();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void delegateAIEnded(AIRobot ai) {
/* 53 */     if (ai instanceof AIRobotGotoBlock) {
/* 54 */       if (ai.success()) {
/* 55 */         startDelegateAI(new AIRobotStraightMoveTo(this.robot, this.station
/* 56 */               .x() + 0.5F + (this.station.side()).offsetX * 0.5F, this.station
/* 57 */               .y() + 0.5F + (this.station.side()).offsetY * 0.5F, this.station
/* 58 */               .z() + 0.5F + (this.station.side()).offsetZ * 0.5F));
/*    */       } else {
/* 60 */         terminate();
/*    */       } 
/* 62 */     } else if (ai instanceof AIRobotStraightMoveTo) {
/* 63 */       if (ai.success()) {
/* 64 */         this.robot.dock(this.station);
/*    */       }
/* 66 */       terminate();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canLoadFromNBT() {
/* 72 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 77 */     super.writeSelfToNBT(nbt);
/*    */     
/* 79 */     if (this.station != null && this.station.index() != null) {
/* 80 */       NBTTagCompound indexNBT = new NBTTagCompound();
/* 81 */       this.station.index().writeTo(indexNBT);
/* 82 */       nbt.func_74782_a("stationIndex", (NBTBase)indexNBT);
/* 83 */       nbt.func_74774_a("stationSide", (byte)this.station.side().ordinal());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 89 */     if (nbt.func_74764_b("stationIndex")) {
/* 90 */       BlockIndex index = new BlockIndex(nbt.func_74775_l("stationIndex"));
/* 91 */       ForgeDirection side = ForgeDirection.values()[nbt.func_74771_c("stationSide")];
/*    */       
/* 93 */       this.station = this.robot.getRegistry().getStation(index.x, index.y, index.z, side);
/*    */     } else {
/* 95 */       this.station = this.robot.getLinkedStation();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGoAndLinkToDock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
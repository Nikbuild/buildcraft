/*     */ package buildcraft.robotics.ai;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.DockingStation;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AIRobotGotoStation
/*     */   extends AIRobot
/*     */ {
/*     */   private BlockIndex stationIndex;
/*     */   private ForgeDirection stationSide;
/*     */   
/*     */   public AIRobotGotoStation(EntityRobotBase iRobot) {
/*  27 */     super(iRobot);
/*     */   }
/*     */   
/*     */   public AIRobotGotoStation(EntityRobotBase iRobot, DockingStation station) {
/*  31 */     this(iRobot);
/*     */     
/*  33 */     this.stationIndex = station.index();
/*  34 */     this.stationSide = station.side();
/*  35 */     setSuccess(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  40 */     DockingStation station = this.robot.getRegistry().getStation(this.stationIndex.x, this.stationIndex.y, this.stationIndex.z, this.stationSide);
/*     */ 
/*     */     
/*  43 */     if (station == null) {
/*  44 */       terminate();
/*  45 */     } else if (station == this.robot.getDockingStation()) {
/*  46 */       setSuccess(true);
/*  47 */       terminate();
/*     */     }
/*  49 */     else if (station.take(this.robot)) {
/*  50 */       startDelegateAI(new AIRobotGotoBlock(this.robot, station
/*  51 */             .x() + (station.side()).offsetX, station
/*  52 */             .y() + (station.side()).offsetY, station
/*  53 */             .z() + (station.side()).offsetZ));
/*     */     } else {
/*  55 */       terminate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/*  62 */     DockingStation station = this.robot.getRegistry().getStation(this.stationIndex.x, this.stationIndex.y, this.stationIndex.z, this.stationSide);
/*     */ 
/*     */     
/*  65 */     if (station == null) {
/*  66 */       terminate();
/*  67 */     } else if (ai instanceof AIRobotGotoBlock) {
/*  68 */       if (ai.success()) {
/*  69 */         startDelegateAI(new AIRobotStraightMoveTo(this.robot, this.stationIndex.x + 0.5F + this.stationSide.offsetX * 0.5F, this.stationIndex.y + 0.5F + this.stationSide.offsetY * 0.5F, this.stationIndex.z + 0.5F + this.stationSide.offsetZ * 0.5F));
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  74 */         terminate();
/*     */       } 
/*     */     } else {
/*  77 */       setSuccess(true);
/*  78 */       if (this.stationSide.offsetY == 0) {
/*  79 */         this.robot.aimItemAt(this.stationIndex.x + 2 * this.stationSide.offsetX, this.stationIndex.y, this.stationIndex.z + 2 * this.stationSide.offsetZ);
/*     */       } else {
/*     */         
/*  82 */         this.robot.aimItemAt(MathHelper.func_76141_d(this.robot.getAimYaw() / 90.0F) * 90.0F + 180.0F, this.robot.getAimPitch());
/*     */       } 
/*  84 */       this.robot.dock(station);
/*  85 */       terminate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLoadFromNBT() {
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/*  96 */     NBTTagCompound indexNBT = new NBTTagCompound();
/*  97 */     this.stationIndex.writeTo(indexNBT);
/*  98 */     nbt.func_74782_a("stationIndex", (NBTBase)indexNBT);
/*  99 */     nbt.func_74774_a("stationSide", (byte)this.stationSide.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 104 */     this.stationIndex = new BlockIndex(nbt.func_74775_l("stationIndex"));
/* 105 */     this.stationSide = ForgeDirection.values()[nbt.func_74771_c("stationSide")];
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGotoStation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
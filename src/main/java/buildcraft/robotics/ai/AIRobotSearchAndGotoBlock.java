/*     */ package buildcraft.robotics.ai;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.robots.ResourceId;
/*     */ import buildcraft.api.robots.ResourceIdBlock;
/*     */ import buildcraft.core.lib.utils.IBlockFilter;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AIRobotSearchAndGotoBlock
/*     */   extends AIRobot
/*     */ {
/*     */   private BlockIndex blockFound;
/*     */   private IBlockFilter filter;
/*     */   private boolean random;
/*     */   private double maxDistanceToEnd;
/*     */   
/*     */   public AIRobotSearchAndGotoBlock(EntityRobotBase iRobot) {
/*  28 */     super(iRobot);
/*     */     
/*  30 */     this.blockFound = null;
/*     */     
/*  32 */     this.random = false;
/*  33 */     this.filter = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public AIRobotSearchAndGotoBlock(EntityRobotBase iRobot, boolean iRandom, IBlockFilter iPathFound) {
/*  38 */     this(iRobot, iRandom, iPathFound, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public AIRobotSearchAndGotoBlock(EntityRobotBase iRobot, boolean iRandom, IBlockFilter iPathFound, double iMaxDistanceToEnd) {
/*  43 */     this(iRobot);
/*     */     
/*  45 */     this.random = iRandom;
/*  46 */     this.filter = iPathFound;
/*  47 */     this.maxDistanceToEnd = iMaxDistanceToEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  52 */     startDelegateAI(new AIRobotSearchBlock(this.robot, this.random, this.filter, this.maxDistanceToEnd));
/*     */   }
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/*  57 */     if (ai instanceof AIRobotSearchBlock) {
/*  58 */       if (ai.success()) {
/*  59 */         AIRobotSearchBlock searchAI = (AIRobotSearchBlock)ai;
/*  60 */         if (searchAI.takeResource()) {
/*  61 */           this.blockFound = searchAI.blockFound;
/*  62 */           startDelegateAI(new AIRobotGotoBlock(this.robot, searchAI.path));
/*     */         } else {
/*  64 */           terminate();
/*     */         } 
/*     */       } else {
/*  67 */         terminate();
/*     */       } 
/*  69 */     } else if (ai instanceof AIRobotGotoBlock) {
/*  70 */       if (!ai.success()) {
/*  71 */         releaseBlockFound();
/*     */       }
/*  73 */       terminate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean success() {
/*  79 */     return (this.blockFound != null);
/*     */   }
/*     */   
/*     */   private void releaseBlockFound() {
/*  83 */     if (this.blockFound != null) {
/*  84 */       this.robot.getRegistry().release((ResourceId)new ResourceIdBlock(this.blockFound));
/*  85 */       this.blockFound = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public BlockIndex getBlockFound() {
/*  90 */     return this.blockFound;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLoadFromNBT() {
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 100 */     super.writeSelfToNBT(nbt);
/*     */     
/* 102 */     if (this.blockFound != null) {
/* 103 */       NBTTagCompound sub = new NBTTagCompound();
/* 104 */       this.blockFound.writeTo(sub);
/* 105 */       nbt.func_74782_a("indexStored", (NBTBase)sub);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 111 */     super.loadSelfFromNBT(nbt);
/*     */     
/* 113 */     if (nbt.func_74764_b("indexStored"))
/* 114 */       this.blockFound = new BlockIndex(nbt.func_74775_l("indexStored")); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotSearchAndGotoBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
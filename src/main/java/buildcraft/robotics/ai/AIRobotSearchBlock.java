/*     */ package buildcraft.robotics.ai;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.IZone;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.robots.ResourceId;
/*     */ import buildcraft.api.robots.ResourceIdBlock;
/*     */ import buildcraft.core.lib.utils.BlockScannerExpanding;
/*     */ import buildcraft.core.lib.utils.BlockScannerRandom;
/*     */ import buildcraft.core.lib.utils.BlockScannerZoneRandom;
/*     */ import buildcraft.core.lib.utils.IBlockFilter;
/*     */ import buildcraft.core.lib.utils.IIterableAlgorithm;
/*     */ import buildcraft.core.lib.utils.IterableAlgorithmRunner;
/*     */ import buildcraft.core.lib.utils.PathFindingSearch;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ public class AIRobotSearchBlock extends AIRobot {
/*     */   public BlockIndex blockFound;
/*  24 */   private PathFindingSearch blockScanner = null; public LinkedList<BlockIndex> path;
/*     */   private IterableAlgorithmRunner blockScannerJob;
/*     */   private IBlockFilter pathFound;
/*     */   private Iterator<BlockIndex> blockIter;
/*     */   private double maxDistanceToEnd;
/*     */   private IZone zone;
/*     */   
/*     */   public AIRobotSearchBlock(EntityRobotBase iRobot) {
/*  32 */     super(iRobot);
/*     */   }
/*     */ 
/*     */   
/*     */   public AIRobotSearchBlock(EntityRobotBase iRobot, boolean random, IBlockFilter iPathFound, double iMaxDistanceToEnd) {
/*  37 */     super(iRobot);
/*     */     
/*  39 */     this.pathFound = iPathFound;
/*  40 */     this.zone = iRobot.getZoneToWork();
/*  41 */     if (!random) {
/*  42 */       this.blockIter = (new BlockScannerExpanding()).iterator();
/*     */     }
/*  44 */     else if (this.zone != null) {
/*  45 */       BlockIndex pos = new BlockIndex((Entity)iRobot);
/*  46 */       this
/*  47 */         .blockIter = (new BlockScannerZoneRandom(pos.x, pos.y, pos.z, iRobot.field_70170_p.field_73012_v, this.zone)).iterator();
/*     */     } else {
/*  49 */       this.blockIter = (new BlockScannerRandom(iRobot.field_70170_p.field_73012_v, 64)).iterator();
/*     */     } 
/*     */     
/*  52 */     this.blockFound = null;
/*  53 */     this.path = null;
/*  54 */     this.maxDistanceToEnd = iMaxDistanceToEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  59 */     this.blockScanner = new PathFindingSearch(this.robot.field_70170_p, new BlockIndex((Entity)this.robot), this.blockIter, this.pathFound, this.maxDistanceToEnd, 96.0F, this.zone);
/*     */     
/*  61 */     this.blockScannerJob = new IterableAlgorithmRunner((IIterableAlgorithm)this.blockScanner);
/*  62 */     this.blockScannerJob.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  67 */     if (this.blockScannerJob == null) {
/*     */ 
/*     */       
/*  70 */       abort();
/*     */       
/*     */       return;
/*     */     } 
/*  74 */     if (this.blockScannerJob.isDone()) {
/*  75 */       this.path = this.blockScanner.getResult();
/*     */       
/*  77 */       if (this.path != null && this.path.size() > 0) {
/*  78 */         this.path.removeLast();
/*  79 */         this.blockFound = this.blockScanner.getResultTarget();
/*     */       } else {
/*  81 */         this.path = null;
/*     */       } 
/*     */       
/*  84 */       terminate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/*  90 */     if (this.blockScannerJob != null) {
/*  91 */       this.blockScannerJob.terminate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean success() {
/*  97 */     return (this.blockFound != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLoadFromNBT() {
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 107 */     super.writeSelfToNBT(nbt);
/*     */     
/* 109 */     if (this.blockFound != null) {
/* 110 */       NBTTagCompound sub = new NBTTagCompound();
/* 111 */       this.blockFound.writeTo(sub);
/* 112 */       nbt.func_74782_a("blockFound", (NBTBase)sub);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 118 */     super.loadSelfFromNBT(nbt);
/*     */     
/* 120 */     if (nbt.func_74764_b("blockFound")) {
/* 121 */       this.blockFound = new BlockIndex(nbt.func_74775_l("blockFound"));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean takeResource() {
/* 126 */     boolean taken = false;
/* 127 */     if (this.robot.getRegistry().take((ResourceId)new ResourceIdBlock(this.blockFound), this.robot)) {
/* 128 */       taken = true;
/*     */     }
/* 130 */     unreserve();
/* 131 */     return taken;
/*     */   }
/*     */   
/*     */   public void unreserve() {
/* 135 */     this.blockScanner.unreserve(this.blockFound);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyCost() {
/* 140 */     return 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotSearchBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
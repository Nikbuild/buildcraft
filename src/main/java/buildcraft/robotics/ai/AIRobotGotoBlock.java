/*     */ package buildcraft.robotics.ai;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.BuildCraftAPI;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.core.lib.utils.IIterableAlgorithm;
/*     */ import buildcraft.core.lib.utils.IterableAlgorithmRunner;
/*     */ import buildcraft.core.lib.utils.PathFinding;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AIRobotGotoBlock
/*     */   extends AIRobotGoto
/*     */ {
/*     */   private PathFinding pathSearch;
/*     */   private IterableAlgorithmRunner pathSearchJob;
/*     */   private LinkedList<BlockIndex> path;
/*  29 */   private double prevDistance = Double.MAX_VALUE;
/*     */   private float finalX;
/*  31 */   private double maxDistance = 0.0D; private float finalY; private float finalZ;
/*     */   private BlockIndex lastBlockInPath;
/*     */   private boolean loadedFromNBT;
/*     */   
/*     */   public AIRobotGotoBlock(EntityRobotBase iRobot) {
/*  36 */     super(iRobot);
/*     */   }
/*     */   
/*     */   public AIRobotGotoBlock(EntityRobotBase robot, int x, int y, int z) {
/*  40 */     this(robot);
/*  41 */     this.finalX = x;
/*  42 */     this.finalY = y;
/*  43 */     this.finalZ = z;
/*     */   }
/*     */   
/*     */   public AIRobotGotoBlock(EntityRobotBase robot, int x, int y, int z, double iMaxDistance) {
/*  47 */     this(robot, x, y, z);
/*     */     
/*  49 */     this.maxDistance = iMaxDistance;
/*     */   }
/*     */   
/*     */   public AIRobotGotoBlock(EntityRobotBase robot, LinkedList<BlockIndex> iPath) {
/*  53 */     this(robot);
/*  54 */     this.path = iPath;
/*  55 */     this.finalX = ((BlockIndex)this.path.getLast()).x;
/*  56 */     this.finalY = ((BlockIndex)this.path.getLast()).y;
/*  57 */     this.finalZ = ((BlockIndex)this.path.getLast()).z;
/*  58 */     setNextInPath();
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  63 */     this.robot.undock();
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  68 */     if (this.loadedFromNBT) {
/*     */ 
/*     */       
/*  71 */       setNextInPath();
/*  72 */       this.loadedFromNBT = false;
/*     */     } 
/*     */     
/*  75 */     if (this.path == null && this.pathSearch == null) {
/*  76 */       this
/*     */         
/*  78 */         .pathSearch = new PathFinding(this.robot.field_70170_p, new BlockIndex((int)Math.floor(this.robot.field_70165_t), (int)Math.floor(this.robot.field_70163_u), (int)Math.floor(this.robot.field_70161_v)), new BlockIndex((int)Math.floor(this.finalX), (int)Math.floor(this.finalY), (int)Math.floor(this.finalZ)), this.maxDistance, 96.0F);
/*     */       
/*  80 */       this.pathSearchJob = new IterableAlgorithmRunner((IIterableAlgorithm)this.pathSearch, 50);
/*  81 */       this.pathSearchJob.start();
/*  82 */     } else if (this.path != null) {
/*  83 */       double distance = this.robot.func_70011_f(this.nextX, this.nextY, this.nextZ);
/*     */       
/*  85 */       if (!this.robot.isMoving() || distance > this.prevDistance) {
/*  86 */         if (this.path.size() > 0) {
/*  87 */           this.path.removeFirst();
/*     */         }
/*     */         
/*  90 */         setNextInPath();
/*     */       } else {
/*  92 */         this.prevDistance = this.robot.func_70011_f(this.nextX, this.nextY, this.nextZ);
/*     */       }
/*     */     
/*  95 */     } else if (this.pathSearchJob.isDone()) {
/*  96 */       this.path = this.pathSearch.getResult();
/*     */       
/*  98 */       if (this.path.size() == 0) {
/*  99 */         setSuccess(false);
/* 100 */         terminate();
/*     */         
/*     */         return;
/*     */       } 
/* 104 */       this.lastBlockInPath = this.path.getLast();
/*     */       
/* 106 */       setNextInPath();
/*     */     } 
/*     */ 
/*     */     
/* 110 */     if (this.path != null && this.path.size() == 0) {
/* 111 */       this.robot.field_70159_w = 0.0D;
/* 112 */       this.robot.field_70181_x = 0.0D;
/* 113 */       this.robot.field_70179_y = 0.0D;
/*     */       
/* 115 */       if (this.lastBlockInPath != null) {
/* 116 */         this.robot.field_70165_t = (this.lastBlockInPath.x + 0.5F);
/* 117 */         this.robot.field_70163_u = (this.lastBlockInPath.y + 0.5F);
/* 118 */         this.robot.field_70161_v = (this.lastBlockInPath.z + 0.5F);
/*     */       } 
/* 120 */       terminate();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setNextInPath() {
/* 125 */     if (this.path.size() > 0) {
/* 126 */       boolean isFirst = (this.prevDistance == Double.MAX_VALUE);
/*     */       
/* 128 */       BlockIndex next = this.path.getFirst();
/* 129 */       this.prevDistance = Double.MAX_VALUE;
/*     */       
/* 131 */       if (isFirst || BuildCraftAPI.isSoftBlock(this.robot.field_70170_p, next.x, next.y, next.z)) {
/* 132 */         setDestination(this.robot, next.x + 0.5F, next.y + 0.5F, next.z + 0.5F);
/* 133 */         this.robot.aimItemAt(next.x, next.y, next.z);
/*     */       } else {
/*     */         
/* 136 */         this.path = null;
/*     */         
/* 138 */         if (this.pathSearchJob != null) {
/* 139 */           this.pathSearchJob.terminate();
/* 140 */           this.robot.field_70159_w = 0.0D;
/* 141 */           this.robot.field_70181_x = 0.0D;
/* 142 */           this.robot.field_70179_y = 0.0D;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/* 150 */     if (this.pathSearchJob != null) {
/* 151 */       this.pathSearchJob.terminate();
/* 152 */       this.robot.field_70159_w = 0.0D;
/* 153 */       this.robot.field_70181_x = 0.0D;
/* 154 */       this.robot.field_70179_y = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLoadFromNBT() {
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 165 */     super.writeSelfToNBT(nbt);
/*     */     
/* 167 */     nbt.func_74776_a("finalX", this.finalX);
/* 168 */     nbt.func_74776_a("finalY", this.finalY);
/* 169 */     nbt.func_74776_a("finalZ", this.finalZ);
/* 170 */     nbt.func_74780_a("maxDistance", this.maxDistance);
/*     */     
/* 172 */     if (this.path != null) {
/* 173 */       NBTTagList pathList = new NBTTagList();
/*     */       
/* 175 */       for (BlockIndex i : this.path) {
/* 176 */         NBTTagCompound subNBT = new NBTTagCompound();
/* 177 */         i.writeTo(subNBT);
/* 178 */         pathList.func_74742_a((NBTBase)subNBT);
/*     */       } 
/*     */       
/* 181 */       nbt.func_74782_a("path", (NBTBase)pathList);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 187 */     super.loadSelfFromNBT(nbt);
/*     */     
/* 189 */     this.finalX = nbt.func_74760_g("finalX");
/* 190 */     this.finalY = nbt.func_74760_g("finalY");
/* 191 */     this.finalZ = nbt.func_74760_g("finalZ");
/* 192 */     this.maxDistance = nbt.func_74769_h("maxDistance");
/*     */     
/* 194 */     if (nbt.func_74764_b("path")) {
/* 195 */       NBTTagList pathList = nbt.func_150295_c("path", 10);
/*     */       
/* 197 */       this.path = new LinkedList<BlockIndex>();
/*     */       
/* 199 */       for (int i = 0; i < pathList.func_74745_c(); i++) {
/* 200 */         this.path.add(new BlockIndex(pathList.func_150305_b(i)));
/*     */       }
/*     */     } 
/*     */     
/* 204 */     this.loadedFromNBT = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotGotoBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
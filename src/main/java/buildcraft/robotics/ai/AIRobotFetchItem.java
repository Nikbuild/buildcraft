/*     */ package buildcraft.robotics.ai;
/*     */ 
/*     */ import buildcraft.api.core.IZone;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.core.lib.inventory.TransactorSimple;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.robotics.boards.BoardRobotPicker;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.inventory.IInventory;
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
/*     */ 
/*     */ public class AIRobotFetchItem
/*     */   extends AIRobot
/*     */ {
/*     */   private EntityItem target;
/*     */   private float maxRange;
/*     */   private IStackFilter stackFilter;
/*  29 */   private int pickTime = -1;
/*     */   private IZone zone;
/*     */   
/*     */   public AIRobotFetchItem(EntityRobotBase iRobot) {
/*  33 */     super(iRobot);
/*     */   }
/*     */   
/*     */   public AIRobotFetchItem(EntityRobotBase iRobot, float iMaxRange, IStackFilter iStackFilter, IZone iZone) {
/*  37 */     this(iRobot);
/*     */     
/*  39 */     this.maxRange = iMaxRange;
/*  40 */     this.stackFilter = iStackFilter;
/*  41 */     this.zone = iZone;
/*     */   }
/*     */ 
/*     */   
/*     */   public void preempt(AIRobot ai) {
/*  46 */     if (this.target != null && this.target.field_70128_L) {
/*  47 */       terminate();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  53 */     if (this.target == null) {
/*  54 */       scanForItem();
/*     */     } else {
/*  56 */       this.pickTime++;
/*     */       
/*  58 */       if (this.pickTime > 5) {
/*  59 */         TransactorSimple inventoryInsert = new TransactorSimple((IInventory)this.robot);
/*     */         
/*  61 */         (this.target.func_92059_d()).field_77994_a -= inventoryInsert.inject(this.target
/*  62 */             .func_92059_d(), ForgeDirection.UNKNOWN, true);
/*     */ 
/*     */         
/*  65 */         if ((this.target.func_92059_d()).field_77994_a <= 0) {
/*  66 */           this.target.func_70106_y();
/*     */         }
/*     */         
/*  69 */         terminate();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/*  76 */     if (ai instanceof AIRobotGotoBlock) {
/*  77 */       if (this.target == null) {
/*     */ 
/*     */ 
/*     */         
/*  81 */         setSuccess(false);
/*  82 */         terminate();
/*     */         
/*     */         return;
/*     */       } 
/*  86 */       if (!ai.success()) {
/*  87 */         this.robot.unreachableEntityDetected((Entity)this.target);
/*  88 */         setSuccess(false);
/*  89 */         terminate();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/*  96 */     if (this.target != null) {
/*  97 */       BoardRobotPicker.targettedItems.remove(Integer.valueOf(this.target.func_145782_y()));
/*     */     }
/*     */   }
/*     */   
/*     */   private void scanForItem() {
/* 102 */     double previousDistance = Double.MAX_VALUE;
/* 103 */     TransactorSimple inventoryInsert = new TransactorSimple((IInventory)this.robot);
/*     */     
/* 105 */     for (Object o : this.robot.field_70170_p.field_72996_f) {
/* 106 */       Entity e = (Entity)o;
/*     */       
/* 108 */       if (!e.field_70128_L && e instanceof EntityItem && 
/*     */         
/* 110 */         !BoardRobotPicker.targettedItems.contains(Integer.valueOf(e.func_145782_y())) && 
/* 111 */         !this.robot.isKnownUnreachable(e) && (this.zone == null || this.zone
/* 112 */         .contains(e.field_70165_t, e.field_70163_u, e.field_70161_v))) {
/* 113 */         double dx = e.field_70165_t - this.robot.field_70165_t;
/* 114 */         double dy = e.field_70163_u - this.robot.field_70163_u;
/* 115 */         double dz = e.field_70161_v - this.robot.field_70161_v;
/*     */         
/* 117 */         double sqrDistance = dx * dx + dy * dy + dz * dz;
/* 118 */         double maxDistance = (this.maxRange * this.maxRange);
/*     */         
/* 120 */         if (sqrDistance >= maxDistance)
/*     */           continue; 
/* 122 */         if (this.stackFilter != null && !this.stackFilter.matches(((EntityItem)e).func_92059_d())) {
/*     */           continue;
/*     */         }
/* 125 */         EntityItem item = (EntityItem)e;
/*     */         
/* 127 */         if (inventoryInsert.inject(item.func_92059_d(), ForgeDirection.UNKNOWN, false) > 0) {
/* 128 */           if (this.target == null) {
/* 129 */             previousDistance = sqrDistance;
/* 130 */             this.target = item; continue;
/*     */           } 
/* 132 */           if (sqrDistance < previousDistance) {
/* 133 */             previousDistance = sqrDistance;
/* 134 */             this.target = item;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 142 */     if (this.target != null) {
/* 143 */       BoardRobotPicker.targettedItems.add(Integer.valueOf(this.target.func_145782_y()));
/* 144 */       if (Math.floor(this.target.field_70165_t) != Math.floor(this.robot.field_70165_t) || Math.floor(this.target.field_70163_u) != Math.floor(this.robot.field_70163_u) || 
/* 145 */         Math.floor(this.target.field_70161_v) != Math.floor(this.robot.field_70161_v)) {
/* 146 */         startDelegateAI(new AIRobotGotoBlock(this.robot, (int)Math.floor(this.target.field_70165_t), 
/* 147 */               (int)Math.floor(this.target.field_70163_u), (int)Math.floor(this.target.field_70161_v)));
/*     */       }
/*     */     } else {
/*     */       
/* 151 */       setSuccess(false);
/* 152 */       terminate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyCost() {
/* 158 */     return 15;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotFetchItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
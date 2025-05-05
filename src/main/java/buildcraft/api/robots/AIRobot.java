/*     */ package buildcraft.api.robots;
/*     */ 
/*     */ import buildcraft.api.mj.MjAPI;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AIRobot
/*     */ {
/*     */   public EntityRobotBase robot;
/*     */   private AIRobot delegateAI;
/*     */   private AIRobot parentAI;
/*     */   private boolean success;
/*     */   
/*     */   public AIRobot(EntityRobotBase iRobot) {
/*  21 */     this.robot = iRobot;
/*  22 */     this.success = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void preempt(AIRobot ai) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  37 */     terminate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void end() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delegateAIAborted(AIRobot ai) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean success() {
/*  67 */     return this.success;
/*     */   }
/*     */   
/*     */   protected void setSuccess(boolean iSuccess) {
/*  71 */     this.success = iSuccess;
/*     */   }
/*     */   
/*     */   public long getPowerCost() {
/*  75 */     return MjAPI.MJ / 10L;
/*     */   }
/*     */   
/*     */   public boolean canLoadFromNBT() {
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack receiveItem(ItemStack stack) {
/*  84 */     return stack;
/*     */   }
/*     */   
/*     */   public final void terminate() {
/*  88 */     abortDelegateAI();
/*  89 */     end();
/*     */     
/*  91 */     if (this.parentAI != null) {
/*  92 */       this.parentAI.delegateAI = null;
/*  93 */       this.parentAI.delegateAIEnded(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void abort() {
/*  98 */     abortDelegateAI();
/*     */     
/*     */     try {
/* 101 */       end();
/*     */       
/* 103 */       if (this.parentAI != null) {
/* 104 */         this.parentAI.delegateAI = null;
/* 105 */         this.parentAI.delegateAIAborted(this);
/*     */       } 
/* 107 */     } catch (Throwable e) {
/* 108 */       e.printStackTrace();
/* 109 */       this.delegateAI = null;
/*     */       
/* 111 */       if (this.parentAI != null) {
/* 112 */         this.parentAI.delegateAI = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void cycle() {
/*     */     try {
/* 119 */       preempt(this.delegateAI);
/*     */       
/* 121 */       if (this.delegateAI != null) {
/* 122 */         this.delegateAI.cycle();
/*     */       } else {
/* 124 */         this.robot.getBattery().extractPower(1L, getPowerCost());
/* 125 */         update();
/*     */       } 
/* 127 */     } catch (Throwable e) {
/* 128 */       e.printStackTrace();
/* 129 */       abort();
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void startDelegateAI(AIRobot ai) {
/* 134 */     abortDelegateAI();
/* 135 */     this.delegateAI = ai;
/* 136 */     ai.parentAI = this;
/* 137 */     this.delegateAI.start();
/*     */   }
/*     */   
/*     */   public final void abortDelegateAI() {
/* 141 */     if (this.delegateAI != null) {
/* 142 */       this.delegateAI.abort();
/*     */     }
/*     */   }
/*     */   
/*     */   public final AIRobot getActiveAI() {
/* 147 */     if (this.delegateAI != null) {
/* 148 */       return this.delegateAI.getActiveAI();
/*     */     }
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final AIRobot getDelegateAI() {
/* 155 */     return this.delegateAI;
/*     */   }
/*     */   
/*     */   public final void writeToNBT(NBTTagCompound nbt) {
/* 159 */     nbt.func_74778_a("aiName", RobotManager.getAIRobotName((Class)getClass()));
/*     */     
/* 161 */     NBTTagCompound data = new NBTTagCompound();
/* 162 */     writeSelfToNBT(data);
/* 163 */     nbt.func_74782_a("data", (NBTBase)data);
/*     */     
/* 165 */     if (this.delegateAI != null && this.delegateAI.canLoadFromNBT()) {
/* 166 */       NBTTagCompound sub = new NBTTagCompound();
/*     */       
/* 168 */       this.delegateAI.writeToNBT(sub);
/* 169 */       nbt.func_74782_a("delegateAI", (NBTBase)sub);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void loadFromNBT(NBTTagCompound nbt) {
/* 174 */     loadSelfFromNBT(nbt.func_74775_l("data"));
/*     */     
/* 176 */     if (nbt.func_74764_b("delegateAI")) {
/* 177 */       NBTTagCompound sub = nbt.func_74775_l("delegateAI");
/*     */       
/*     */       try {
/*     */         Class<?> aiRobotClass;
/* 181 */         if (sub.func_74764_b("class")) {
/*     */           
/* 183 */           aiRobotClass = RobotManager.getAIRobotByLegacyClassName(sub.func_74779_i("class"));
/*     */         } else {
/* 185 */           aiRobotClass = RobotManager.getAIRobotByName(sub.func_74779_i("aiName"));
/*     */         } 
/* 187 */         if (aiRobotClass != null) {
/* 188 */           this.delegateAI = aiRobotClass.getConstructor(new Class[] { EntityRobotBase.class }).newInstance(new Object[] { this.robot });
/* 189 */           this.delegateAI.parentAI = this;
/*     */           
/* 191 */           if (this.delegateAI.canLoadFromNBT()) {
/* 192 */             this.delegateAI.loadFromNBT(sub);
/*     */           }
/*     */         } 
/* 195 */       } catch (Throwable e) {
/* 196 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static AIRobot loadAI(NBTTagCompound nbt, EntityRobotBase robot) {
/* 202 */     AIRobot ai = null;
/*     */     
/*     */     try {
/*     */       Class<?> aiRobotClass;
/* 206 */       if (nbt.func_74764_b("class")) {
/*     */         
/* 208 */         aiRobotClass = RobotManager.getAIRobotByLegacyClassName(nbt.func_74779_i("class"));
/*     */       } else {
/* 210 */         aiRobotClass = RobotManager.getAIRobotByName(nbt.func_74779_i("aiName"));
/*     */       } 
/* 212 */       if (aiRobotClass != null) {
/* 213 */         ai = aiRobotClass.getConstructor(new Class[] { EntityRobotBase.class }).newInstance(new Object[] { robot });
/* 214 */         ai.loadFromNBT(nbt);
/*     */       } 
/* 216 */     } catch (Throwable e) {
/* 217 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 220 */     return ai;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\robots\AIRobot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
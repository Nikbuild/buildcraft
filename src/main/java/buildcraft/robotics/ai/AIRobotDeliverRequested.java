/*     */ package buildcraft.robotics.ai;
/*     */ 
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.robots.IRequestProvider;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.inventory.filters.ArrayStackOrListFilter;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.robotics.StackRequest;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AIRobotDeliverRequested
/*     */   extends AIRobot
/*     */ {
/*     */   private StackRequest requested;
/*     */   private boolean delivered = false;
/*     */   
/*     */   public AIRobotDeliverRequested(EntityRobotBase iRobot) {
/*  28 */     super(iRobot);
/*     */   }
/*     */   
/*     */   public AIRobotDeliverRequested(EntityRobotBase robot, StackRequest request) {
/*  32 */     this(robot);
/*     */     
/*  34 */     this.requested = request;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  39 */     if (this.requested != null) {
/*  40 */       startDelegateAI(new AIRobotGotoStation(this.robot, this.requested.getStation(this.robot.field_70170_p)));
/*     */     } else {
/*  42 */       setSuccess(false);
/*  43 */       terminate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/*  49 */     if (ai instanceof AIRobotGotoStation) {
/*  50 */       if (!ai.success()) {
/*  51 */         setSuccess(false);
/*  52 */         terminate();
/*     */         
/*     */         return;
/*     */       } 
/*  56 */       IRequestProvider requester = this.requested.getRequester(this.robot.field_70170_p);
/*  57 */       if (requester == null) {
/*  58 */         setSuccess(false);
/*  59 */         terminate();
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  65 */       int count = 0;
/*     */       
/*  67 */       for (IInvSlot slot : InvUtils.getItems((IInventory)this.robot, (IStackFilter)new ArrayStackOrListFilter(new ItemStack[] { this.requested.getStack() }))) {
/*  68 */         int difference = (slot.getStackInSlot()).field_77994_a;
/*  69 */         ItemStack newStack = requester.offerItem(this.requested.getSlot(), slot.getStackInSlot().func_77946_l());
/*     */         
/*  71 */         if (newStack == null) {
/*  72 */           slot.setStackInSlot(newStack);
/*  73 */         } else if (newStack.field_77994_a != (slot.getStackInSlot()).field_77994_a) {
/*  74 */           slot.setStackInSlot(newStack);
/*  75 */           difference = newStack.field_77994_a - difference;
/*     */         } 
/*     */         
/*  78 */         count += difference;
/*     */       } 
/*     */       
/*  81 */       setSuccess((count > 0));
/*  82 */       terminate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean success() {
/*  88 */     return this.delivered;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLoadFromNBT() {
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/*  98 */     super.writeSelfToNBT(nbt);
/*     */     
/* 100 */     if (this.requested != null) {
/* 101 */       NBTTagCompound requestNBT = new NBTTagCompound();
/* 102 */       this.requested.writeToNBT(requestNBT);
/* 103 */       nbt.func_74782_a("currentRequest", (NBTBase)requestNBT);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 109 */     super.loadSelfFromNBT(nbt);
/* 110 */     if (nbt.func_74764_b("currentRequest"))
/* 111 */       this.requested = StackRequest.loadFromNBT(nbt.func_74775_l("currentRequest")); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotDeliverRequested.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package buildcraft.robotics.boards;
/*     */ 
/*     */ import buildcraft.api.boards.RedstoneBoardNBT;
/*     */ import buildcraft.api.boards.RedstoneBoardRobot;
/*     */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.robotics.StackRequest;
/*     */ import buildcraft.robotics.ai.AIRobotDeliverRequested;
/*     */ import buildcraft.robotics.ai.AIRobotDisposeItems;
/*     */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*     */ import buildcraft.robotics.ai.AIRobotGotoStationAndLoad;
/*     */ import buildcraft.robotics.ai.AIRobotSearchStackRequest;
/*     */ import buildcraft.robotics.statements.ActionRobotFilter;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ public class BoardRobotDelivery
/*     */   extends RedstoneBoardRobot
/*     */ {
/*  32 */   private ArrayList<ItemStack> deliveryBlacklist = new ArrayList<ItemStack>();
/*     */   
/*  34 */   private StackRequest currentRequest = null;
/*     */   
/*     */   public BoardRobotDelivery(EntityRobotBase iRobot) {
/*  37 */     super(iRobot);
/*     */   }
/*     */ 
/*     */   
/*     */   public RedstoneBoardRobotNBT getNBTHandler() {
/*  42 */     return BCBoardNBT.REGISTRY.get("delivery");
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  47 */     if (this.robot.containsItems()) {
/*  48 */       startDelegateAI((AIRobot)new AIRobotDisposeItems(this.robot));
/*     */       
/*     */       return;
/*     */     } 
/*  52 */     if (this.currentRequest == null) {
/*  53 */       startDelegateAI((AIRobot)new AIRobotSearchStackRequest(this.robot, ActionRobotFilter.getGateFilter(this.robot
/*  54 */               .getLinkedStation()), this.deliveryBlacklist));
/*     */     } else {
/*  56 */       startDelegateAI((AIRobot)new AIRobotGotoStationAndLoad(this.robot, new IStackFilter()
/*     */             {
/*     */               public boolean matches(ItemStack stack) {
/*  59 */                 return (BoardRobotDelivery.this.currentRequest != null && StackHelper.isMatchingItemOrList(stack, BoardRobotDelivery.this.currentRequest.getStack()));
/*     */               }
/*  61 */             }(this.currentRequest.getStack()).field_77994_a));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/*  67 */     if (ai instanceof AIRobotSearchStackRequest) {
/*  68 */       if (!ai.success()) {
/*  69 */         this.deliveryBlacklist.clear();
/*  70 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       } else {
/*  72 */         this.currentRequest = ((AIRobotSearchStackRequest)ai).request;
/*     */         
/*  74 */         if (!this.currentRequest.getStation(this.robot.field_70170_p).take(this.robot)) {
/*  75 */           releaseCurrentRequest();
/*     */         }
/*     */       } 
/*  78 */     } else if (ai instanceof AIRobotGotoStationAndLoad) {
/*  79 */       if (!ai.success()) {
/*  80 */         this.deliveryBlacklist.add(this.currentRequest.getStack());
/*  81 */         releaseCurrentRequest();
/*     */       } else {
/*  83 */         startDelegateAI((AIRobot)new AIRobotDeliverRequested(this.robot, this.currentRequest));
/*     */       } 
/*  85 */     } else if (ai instanceof AIRobotDeliverRequested) {
/*  86 */       releaseCurrentRequest();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void releaseCurrentRequest() {
/*  91 */     if (this.currentRequest != null) {
/*  92 */       this.robot.getRegistry().release(this.currentRequest.getResourceId(this.robot.field_70170_p));
/*  93 */       this.currentRequest.getStation(this.robot.field_70170_p).release(this.robot);
/*  94 */       this.currentRequest = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLoadFromNBT() {
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 105 */     super.writeSelfToNBT(nbt);
/*     */     
/* 107 */     if (this.currentRequest != null) {
/* 108 */       NBTTagCompound requestNBT = new NBTTagCompound();
/* 109 */       this.currentRequest.writeToNBT(requestNBT);
/* 110 */       nbt.func_74782_a("currentRequest", (NBTBase)requestNBT);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 116 */     super.loadSelfFromNBT(nbt);
/* 117 */     if (nbt.func_74764_b("currentRequest"))
/* 118 */       this.currentRequest = StackRequest.loadFromNBT(nbt.func_74775_l("currentRequest")); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotDelivery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
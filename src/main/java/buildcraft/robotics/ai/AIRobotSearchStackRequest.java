/*     */ package buildcraft.robotics.ai;
/*     */ 
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.DockingStation;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.robots.IRequestProvider;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.robotics.IStationFilter;
/*     */ import buildcraft.robotics.StackRequest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ public class AIRobotSearchStackRequest
/*     */   extends AIRobot
/*     */ {
/*  28 */   public StackRequest request = null;
/*  29 */   public DockingStation station = null;
/*     */   
/*     */   private Collection<ItemStack> blackList;
/*     */   
/*     */   private IStackFilter filter;
/*     */   
/*     */   public AIRobotSearchStackRequest(EntityRobotBase iRobot) {
/*  36 */     super(iRobot);
/*     */   }
/*     */   
/*     */   public AIRobotSearchStackRequest(EntityRobotBase iRobot, IStackFilter iFilter, Collection<ItemStack> iBlackList) {
/*  40 */     this(iRobot);
/*     */     
/*  42 */     this.blackList = iBlackList;
/*  43 */     this.filter = iFilter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  48 */     startDelegateAI(new AIRobotSearchStation(this.robot, new StationProviderFilter(), this.robot.getZoneToWork()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/*  53 */     if (ai instanceof AIRobotSearchStation) {
/*  54 */       if (ai.success()) {
/*  55 */         this.request = getOrderFromRequestingStation(((AIRobotSearchStation)ai).targetStation, true);
/*     */       }
/*     */       
/*  58 */       terminate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean success() {
/*  64 */     return (this.request != null);
/*     */   }
/*     */   
/*     */   private boolean isBlacklisted(ItemStack stack) {
/*  68 */     for (ItemStack black : this.blackList) {
/*  69 */       if (StackHelper.isMatchingItem(stack, black)) {
/*  70 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private StackRequest getOrderFromRequestingStation(DockingStation station, boolean take) {
/*  79 */     for (StackRequest req : getAvailableRequests(station)) {
/*  80 */       if (!isBlacklisted(req.getStack()) && this.filter.matches(req.getStack())) {
/*  81 */         req.setStation(station);
/*  82 */         if (take) {
/*  83 */           if (this.robot.getRegistry().take(req.getResourceId(this.robot.field_70170_p), this.robot))
/*  84 */             return req; 
/*     */           continue;
/*     */         } 
/*  87 */         return req;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  92 */     return null;
/*     */   }
/*     */   
/*     */   private Collection<StackRequest> getAvailableRequests(DockingStation station) {
/*  96 */     List<StackRequest> result = new ArrayList<StackRequest>();
/*     */     
/*  98 */     IRequestProvider provider = station.getRequestProvider();
/*  99 */     if (provider == null) {
/* 100 */       return result;
/*     */     }
/*     */     
/* 103 */     for (int i = 0; i < provider.getRequestsCount(); i++) {
/* 104 */       if (provider.getRequest(i) != null) {
/*     */ 
/*     */         
/* 107 */         StackRequest req = new StackRequest(provider, i, provider.getRequest(i));
/* 108 */         req.setStation(station);
/* 109 */         if (!this.robot.getRegistry().isTaken(req.getResourceId(this.robot.field_70170_p)))
/* 110 */           result.add(req); 
/*     */       } 
/*     */     } 
/* 113 */     return result;
/*     */   }
/*     */   
/*     */   private class StationProviderFilter implements IStationFilter {
/*     */     private StationProviderFilter() {}
/*     */     
/*     */     public boolean matches(DockingStation station) {
/* 120 */       return (AIRobotSearchStackRequest.this.getOrderFromRequestingStation(station, false) != null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotSearchStackRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
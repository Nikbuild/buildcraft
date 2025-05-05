/*     */ package buildcraft.robotics.boards;
/*     */ 
/*     */ import buildcraft.api.blueprints.IBuilderContext;
/*     */ import buildcraft.api.boards.RedstoneBoardNBT;
/*     */ import buildcraft.api.boards.RedstoneBoardRobot;
/*     */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*     */ import buildcraft.api.core.IZone;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.builders.TileConstructionMarker;
/*     */ import buildcraft.core.builders.BuildingItem;
/*     */ import buildcraft.core.builders.BuildingSlot;
/*     */ import buildcraft.core.builders.IBuildingItemsProvider;
/*     */ import buildcraft.core.lib.inventory.filters.ArrayStackFilter;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.robotics.ai.AIRobotDisposeItems;
/*     */ import buildcraft.robotics.ai.AIRobotGotoBlock;
/*     */ import buildcraft.robotics.ai.AIRobotGotoSleep;
/*     */ import buildcraft.robotics.ai.AIRobotGotoStationAndLoad;
/*     */ import buildcraft.robotics.ai.AIRobotRecharge;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoardRobotBuilder
/*     */   extends RedstoneBoardRobot
/*     */ {
/*     */   private static final int MAX_RANGE_SQ = 12288;
/*     */   private TileConstructionMarker markerToBuild;
/*     */   private BuildingSlot currentBuildingSlot;
/*     */   private LinkedList<ItemStack> requirementsToLookFor;
/*  39 */   private int launchingDelay = 0;
/*     */   
/*     */   public BoardRobotBuilder(EntityRobotBase iRobot) {
/*  42 */     super(iRobot);
/*     */   }
/*     */ 
/*     */   
/*     */   public RedstoneBoardRobotNBT getNBTHandler() {
/*  47 */     return BCBoardNBT.REGISTRY.get("builder");
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  52 */     if (this.launchingDelay > 0) {
/*  53 */       this.launchingDelay--;
/*     */       
/*     */       return;
/*     */     } 
/*  57 */     if (this.markerToBuild == null) {
/*  58 */       this.markerToBuild = findClosestMarker();
/*     */       
/*  60 */       if (this.markerToBuild == null) {
/*  61 */         if (this.robot.containsItems()) {
/*  62 */           startDelegateAI((AIRobot)new AIRobotDisposeItems(this.robot));
/*     */         } else {
/*  64 */           startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  70 */     if (!this.markerToBuild.needsToBuild()) {
/*  71 */       this.markerToBuild = null;
/*  72 */       this.currentBuildingSlot = null;
/*     */       
/*     */       return;
/*     */     } 
/*  76 */     if (this.currentBuildingSlot == null) {
/*  77 */       this.currentBuildingSlot = this.markerToBuild.bluePrintBuilder.reserveNextSlot(this.robot.field_70170_p);
/*     */       
/*  79 */       if (this.currentBuildingSlot == null) {
/*     */         
/*  81 */         this.launchingDelay = 40;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     if (this.requirementsToLookFor == null) {
/*  88 */       if (this.robot.containsItems()) {
/*  89 */         startDelegateAI((AIRobot)new AIRobotDisposeItems(this.robot));
/*     */       }
/*     */       
/*  92 */       if (this.robot.field_70170_p.func_72912_H().func_76077_q() != WorldSettings.GameType.CREATIVE) {
/*  93 */         this.requirementsToLookFor = this.currentBuildingSlot.getRequirements((IBuilderContext)this.markerToBuild
/*  94 */             .getContext());
/*     */       } else {
/*  96 */         this.requirementsToLookFor = new LinkedList<ItemStack>();
/*     */       } 
/*     */       
/*  99 */       if (this.requirementsToLookFor == null) {
/* 100 */         this.launchingDelay = 40;
/*     */         
/*     */         return;
/*     */       } 
/* 104 */       if (this.requirementsToLookFor.size() > 4) {
/* 105 */         this.currentBuildingSlot.built = true;
/* 106 */         this.currentBuildingSlot = null;
/* 107 */         this.requirementsToLookFor = null;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 112 */     if (this.requirementsToLookFor.size() > 0) {
/* 113 */       startDelegateAI((AIRobot)new AIRobotGotoStationAndLoad(this.robot, (IStackFilter)new ArrayStackFilter(new ItemStack[] { this.requirementsToLookFor
/* 114 */                 .getFirst() }, ), ((ItemStack)this.requirementsToLookFor.getFirst()).field_77994_a));
/*     */       
/*     */       return;
/*     */     } 
/* 118 */     if (this.requirementsToLookFor.size() == 0) {
/* 119 */       if (this.currentBuildingSlot.stackConsumed == null)
/*     */       {
/*     */         
/* 122 */         this.markerToBuild.bluePrintBuilder.useRequirements((IInventory)this.robot, this.currentBuildingSlot);
/*     */       }
/*     */       
/* 125 */       if (!hasEnoughEnergy()) {
/* 126 */         startDelegateAI((AIRobot)new AIRobotRecharge(this.robot));
/*     */       } else {
/* 128 */         startDelegateAI((AIRobot)new AIRobotGotoBlock(this.robot, 
/* 129 */               (int)(this.currentBuildingSlot.getDestination()).x, 
/* 130 */               (int)(this.currentBuildingSlot.getDestination()).y, 
/* 131 */               (int)(this.currentBuildingSlot.getDestination()).z, 8.0D));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delegateAIEnded(AIRobot ai) {
/* 141 */     if (ai instanceof AIRobotGotoStationAndLoad) {
/* 142 */       if (ai.success()) {
/* 143 */         this.requirementsToLookFor.removeFirst();
/*     */       } else {
/* 145 */         startDelegateAI((AIRobot)new AIRobotGotoSleep(this.robot));
/*     */       } 
/* 147 */     } else if (ai instanceof AIRobotGotoBlock) {
/* 148 */       if (this.markerToBuild == null || this.markerToBuild.bluePrintBuilder == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 153 */       if (!hasEnoughEnergy()) {
/* 154 */         startDelegateAI((AIRobot)new AIRobotRecharge(this.robot));
/*     */         
/*     */         return;
/*     */       } 
/* 158 */       this.robot.getBattery().extractEnergy(this.currentBuildingSlot.getEnergyRequirement(), false);
/* 159 */       this.launchingDelay = this.currentBuildingSlot.getStacksToDisplay().size() * BuildingItem.ITEMS_SPACE;
/*     */       
/* 161 */       this.markerToBuild.bluePrintBuilder.buildSlot(this.robot.field_70170_p, (IBuildingItemsProvider)this.markerToBuild, this.currentBuildingSlot, this.robot.field_70165_t + 0.125D, this.robot.field_70163_u + 0.125D, this.robot.field_70161_v + 0.125D);
/*     */ 
/*     */       
/* 164 */       this.currentBuildingSlot = null;
/* 165 */       this.requirementsToLookFor = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 171 */     super.writeSelfToNBT(nbt);
/*     */     
/* 173 */     nbt.func_74768_a("launchingDelay", this.launchingDelay);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 178 */     super.loadSelfFromNBT(nbt);
/*     */     
/* 180 */     this.launchingDelay = nbt.func_74762_e("launchingDelay");
/*     */   }
/*     */   
/*     */   private TileConstructionMarker findClosestMarker() {
/* 184 */     double minDistance = Double.MAX_VALUE;
/* 185 */     TileConstructionMarker minMarker = null;
/*     */     
/* 187 */     IZone zone = this.robot.getZoneToWork();
/*     */     
/* 189 */     for (TileConstructionMarker marker : TileConstructionMarker.currentMarkers) {
/* 190 */       if (marker.func_145831_w() != this.robot.field_70170_p) {
/*     */         continue;
/*     */       }
/* 193 */       if (!marker.needsToBuild()) {
/*     */         continue;
/*     */       }
/* 196 */       if (zone != null && !zone.contains(marker.field_145851_c, marker.field_145848_d, marker.field_145849_e)) {
/*     */         continue;
/*     */       }
/*     */       
/* 200 */       double dx = this.robot.field_70165_t - marker.field_145851_c;
/* 201 */       double dy = this.robot.field_70163_u - marker.field_145848_d;
/* 202 */       double dz = this.robot.field_70161_v - marker.field_145849_e;
/* 203 */       double distance = dx * dx + dy * dy + dz * dz;
/*     */       
/* 205 */       if (distance < minDistance) {
/* 206 */         minMarker = marker;
/* 207 */         minDistance = distance;
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     if (minMarker != null && minDistance < 12288.0D) {
/* 212 */       return minMarker;
/*     */     }
/* 214 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasEnoughEnergy() {
/* 219 */     return (this.robot.getEnergy() - this.currentBuildingSlot.getEnergyRequirement() > 20000);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BoardRobotBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
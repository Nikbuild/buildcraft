/*     */ package buildcraft.robotics.ai;
/*     */ 
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.DockingStation;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.transport.IInjectable;
/*     */ import buildcraft.core.lib.inventory.InventoryIterator;
/*     */ import buildcraft.core.lib.inventory.filters.ArrayStackOrListFilter;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.robotics.statements.ActionRobotFilter;
/*     */ import buildcraft.robotics.statements.ActionStationAcceptItems;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AIRobotUnload
/*     */   extends AIRobot
/*     */ {
/*  27 */   private int waitedCycles = 0;
/*     */   
/*     */   public AIRobotUnload(EntityRobotBase iRobot) {
/*  30 */     super(iRobot);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  35 */     this.waitedCycles++;
/*     */     
/*  37 */     if (this.waitedCycles > 40) {
/*  38 */       if (unload(this.robot, this.robot.getDockingStation(), true)) {
/*  39 */         this.waitedCycles = 0;
/*     */       } else {
/*  41 */         setSuccess(!this.robot.containsItems());
/*  42 */         terminate();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean unload(EntityRobotBase robot, DockingStation station, boolean doUnload) {
/*  48 */     if (station == null) {
/*  49 */       return false;
/*     */     }
/*     */     
/*  52 */     IInjectable output = station.getItemOutput();
/*  53 */     if (output == null) {
/*  54 */       return false;
/*     */     }
/*     */     
/*  57 */     ForgeDirection injectSide = station.getItemOutputSide();
/*  58 */     if (!output.canInjectItems(injectSide)) {
/*  59 */       return false;
/*     */     }
/*     */     
/*  62 */     for (IInvSlot robotSlot : InventoryIterator.getIterable((IInventory)robot, ForgeDirection.UNKNOWN)) {
/*  63 */       if (robotSlot.getStackInSlot() == null) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/*  68 */       if (!ActionRobotFilter.canInteractWithItem(station, (IStackFilter)new ArrayStackOrListFilter(new ItemStack[] { robotSlot.getStackInSlot() }, ), ActionStationAcceptItems.class)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/*  73 */       ItemStack stack = robotSlot.getStackInSlot();
/*  74 */       int used = output.injectItem(stack, doUnload, injectSide, null);
/*     */       
/*  76 */       if (used > 0) {
/*  77 */         if (doUnload) {
/*  78 */           robotSlot.decreaseStackInSlot(used);
/*     */         }
/*  80 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     if (robot.func_70694_bm() != null) {
/*     */       
/*  86 */       if (!ActionRobotFilter.canInteractWithItem(station, (IStackFilter)new ArrayStackOrListFilter(new ItemStack[] { robot.func_70694_bm() }, ), ActionStationAcceptItems.class))
/*     */       {
/*  88 */         return false;
/*     */       }
/*     */       
/*  91 */       ItemStack stack = robot.func_70694_bm();
/*  92 */       int used = output.injectItem(stack, doUnload, injectSide, null);
/*     */       
/*  94 */       if (used > 0) {
/*  95 */         if (doUnload) {
/*  96 */           if (stack.field_77994_a <= used) {
/*  97 */             robot.setItemInUse(null);
/*     */           } else {
/*  99 */             stack.field_77994_a -= used;
/*     */           } 
/*     */         }
/* 102 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyCost() {
/* 111 */     return 10;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotUnload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
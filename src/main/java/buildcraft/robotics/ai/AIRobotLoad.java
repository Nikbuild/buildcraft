/*     */ package buildcraft.robotics.ai;
/*     */ 
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.DockingStation;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.core.lib.inventory.ITransactor;
/*     */ import buildcraft.core.lib.inventory.InventoryIterator;
/*     */ import buildcraft.core.lib.inventory.Transactor;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.robotics.statements.ActionRobotFilter;
/*     */ import buildcraft.robotics.statements.ActionStationProvideItems;
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
/*     */ 
/*     */ 
/*     */ public class AIRobotLoad
/*     */   extends AIRobot
/*     */ {
/*     */   public static final int ANY_QUANTITY = -1;
/*     */   private IStackFilter filter;
/*     */   private int quantity;
/*  32 */   private int waitedCycles = 0;
/*     */   
/*     */   public AIRobotLoad(EntityRobotBase iRobot) {
/*  35 */     super(iRobot);
/*     */   }
/*     */   
/*     */   public AIRobotLoad(EntityRobotBase iRobot, IStackFilter iFilter, int iQuantity) {
/*  39 */     super(iRobot);
/*     */     
/*  41 */     this.filter = iFilter;
/*  42 */     this.quantity = iQuantity;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  47 */     if (this.filter == null) {
/*  48 */       terminate();
/*     */       
/*     */       return;
/*     */     } 
/*  52 */     this.waitedCycles++;
/*     */     
/*  54 */     if (this.waitedCycles > 40) {
/*  55 */       setSuccess(load(this.robot, this.robot.getDockingStation(), this.filter, this.quantity, true));
/*  56 */       terminate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack takeSingle(DockingStation station, IStackFilter filter, boolean doTake) {
/*  65 */     if (station == null) {
/*  66 */       return null;
/*     */     }
/*     */     
/*  69 */     IInventory tileInventory = station.getItemInput();
/*  70 */     if (tileInventory == null) {
/*  71 */       return null;
/*     */     }
/*     */     
/*  74 */     for (IInvSlot slot : InventoryIterator.getIterable(tileInventory, station.getItemInputSide())) {
/*  75 */       ItemStack stack = slot.getStackInSlot();
/*     */       
/*  77 */       if (stack == null || 
/*  78 */         !slot.canTakeStackFromSlot(stack) || 
/*  79 */         !filter.matches(stack) || 
/*  80 */         !ActionStationProvideItems.canExtractItem(station, stack) || 
/*  81 */         !ActionRobotFilter.canInteractWithItem(station, filter, ActionStationProvideItems.class)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/*  86 */       if (doTake) {
/*  87 */         stack = slot.decreaseStackInSlot(1);
/*     */       } else {
/*  89 */         stack = stack.func_77946_l();
/*  90 */         stack = stack.func_77979_a(1);
/*     */       } 
/*  92 */       return stack;
/*     */     } 
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean load(EntityRobotBase robot, DockingStation station, IStackFilter filter, int quantity, boolean doLoad) {
/*  99 */     if (station == null) {
/* 100 */       return false;
/*     */     }
/*     */     
/* 103 */     int loaded = 0;
/*     */     
/* 105 */     IInventory tileInventory = station.getItemInput();
/* 106 */     if (tileInventory == null) {
/* 107 */       return false;
/*     */     }
/*     */     
/* 110 */     for (IInvSlot slot : InventoryIterator.getIterable(tileInventory, station.getItemInputSide())) {
/* 111 */       ItemStack stack = slot.getStackInSlot();
/*     */       
/* 113 */       if (stack == null || 
/* 114 */         !slot.canTakeStackFromSlot(stack) || 
/* 115 */         !filter.matches(stack) || 
/* 116 */         !ActionStationProvideItems.canExtractItem(station, stack) || 
/* 117 */         !ActionRobotFilter.canInteractWithItem(station, filter, ActionStationProvideItems.class)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 122 */       ITransactor robotTransactor = Transactor.getTransactorFor(robot);
/*     */       
/* 124 */       if (quantity == -1) {
/* 125 */         ItemStack itemStack = robotTransactor.add(slot.getStackInSlot(), ForgeDirection.UNKNOWN, doLoad);
/*     */         
/* 127 */         if (doLoad) {
/* 128 */           slot.decreaseStackInSlot(itemStack.field_77994_a);
/*     */         }
/* 130 */         return (itemStack.field_77994_a > 0);
/*     */       } 
/* 132 */       ItemStack toAdd = slot.getStackInSlot().func_77946_l();
/*     */       
/* 134 */       if (toAdd.field_77994_a > quantity - loaded) {
/* 135 */         toAdd.field_77994_a = quantity - loaded;
/*     */       }
/*     */       
/* 138 */       ItemStack added = robotTransactor.add(toAdd, ForgeDirection.UNKNOWN, doLoad);
/* 139 */       if (doLoad) {
/* 140 */         slot.decreaseStackInSlot(added.field_77994_a);
/*     */       }
/* 142 */       loaded += added.field_77994_a;
/*     */       
/* 144 */       if (quantity - loaded <= 0) {
/* 145 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyCost() {
/* 154 */     return 8;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotLoad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package buildcraft.robotics.statements;
/*     */ 
/*     */ import buildcraft.api.robots.DockingStation;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.StatementParameterItemStack;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.core.lib.inventory.filters.ArrayFluidFilter;
/*     */ import buildcraft.core.lib.inventory.filters.ArrayStackOrListFilter;
/*     */ import buildcraft.core.lib.inventory.filters.IFluidFilter;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import buildcraft.core.lib.inventory.filters.PassThroughFluidFilter;
/*     */ import buildcraft.core.lib.inventory.filters.PassThroughStackFilter;
/*     */ import buildcraft.core.lib.inventory.filters.StatementParameterStackFilter;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.core.statements.BCStatement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ public class ActionRobotFilter
/*     */   extends BCStatement
/*     */   implements IActionInternal
/*     */ {
/*     */   public ActionRobotFilter() {
/*  39 */     super(new String[] { "buildcraft:robot.work_filter" });
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  44 */     return StringUtils.localize("gate.action.robot.filter");
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerIcons(IIconRegister iconRegister) {
/*  49 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:triggers/action_robot_filter");
/*     */   }
/*     */ 
/*     */   
/*     */   public int minParameters() {
/*  54 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  59 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int index) {
/*  64 */     return (IStatementParameter)new StatementParameterItemStack();
/*     */   }
/*     */   
/*     */   public static Collection<ItemStack> getGateFilterStacks(DockingStation station) {
/*  68 */     ArrayList<ItemStack> result = new ArrayList<ItemStack>();
/*     */     
/*  70 */     for (StatementSlot slot : station.getActiveActions()) {
/*  71 */       if (slot.statement instanceof ActionRobotFilter) {
/*  72 */         for (IStatementParameter p : slot.parameters) {
/*  73 */           if (p != null && p instanceof StatementParameterItemStack) {
/*  74 */             StatementParameterItemStack param = (StatementParameterItemStack)p;
/*  75 */             ItemStack stack = param.getItemStack();
/*     */             
/*  77 */             if (stack != null) {
/*  78 */               result.add(stack);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  85 */     return result;
/*     */   }
/*     */   
/*     */   public static IStackFilter getGateFilter(DockingStation station) {
/*  89 */     Collection<ItemStack> stacks = getGateFilterStacks(station);
/*     */     
/*  91 */     if (stacks.size() == 0) {
/*  92 */       return (IStackFilter)new PassThroughStackFilter();
/*     */     }
/*  94 */     return (IStackFilter)new ArrayStackOrListFilter(stacks.<ItemStack>toArray(new ItemStack[stacks.size()]));
/*     */   }
/*     */ 
/*     */   
/*     */   public static IFluidFilter getGateFluidFilter(DockingStation station) {
/*  99 */     Collection<ItemStack> stacks = getGateFilterStacks(station);
/*     */     
/* 101 */     if (stacks.size() == 0) {
/* 102 */       return (IFluidFilter)new PassThroughFluidFilter();
/*     */     }
/* 104 */     return (IFluidFilter)new ArrayFluidFilter(stacks.<ItemStack>toArray(new ItemStack[stacks.size()]));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canInteractWithItem(DockingStation station, IStackFilter filter, Class<?> actionClass) {
/* 109 */     boolean actionFound = false;
/*     */     
/* 111 */     for (StatementSlot s : station.getActiveActions()) {
/* 112 */       if (actionClass.isAssignableFrom(s.statement.getClass())) {
/* 113 */         StatementParameterStackFilter param = new StatementParameterStackFilter(s.parameters);
/*     */         
/* 115 */         if (!param.hasFilter() || param.matches(filter)) {
/* 116 */           actionFound = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 122 */     return actionFound;
/*     */   }
/*     */   
/*     */   public static boolean canInteractWithFluid(DockingStation station, IFluidFilter filter, Class<?> actionClass) {
/* 126 */     boolean actionFound = false;
/*     */     
/* 128 */     for (StatementSlot s : station.getActiveActions()) {
/* 129 */       if (actionClass.isAssignableFrom(s.statement.getClass())) {
/* 130 */         StatementParameterStackFilter param = new StatementParameterStackFilter(s.parameters);
/*     */         
/* 132 */         if (!param.hasFilter()) {
/* 133 */           actionFound = true;
/*     */           break;
/*     */         } 
/* 136 */         for (ItemStack stack : param.getStacks()) {
/* 137 */           if (stack != null) {
/* 138 */             FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
/*     */             
/* 140 */             if (fluid != null && filter.matches(fluid.getFluid())) {
/* 141 */               actionFound = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     return actionFound;
/*     */   }
/*     */   
/*     */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {}
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\ActionRobotFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
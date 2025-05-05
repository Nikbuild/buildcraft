/*     */ package buildcraft.core.statements;
/*     */ 
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.ITriggerExternal;
/*     */ import buildcraft.api.statements.StatementParameterItemStack;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TriggerFluidContainer
/*     */   extends BCStatement
/*     */   implements ITriggerExternal
/*     */ {
/*     */   public State state;
/*     */   
/*     */   public enum State
/*     */   {
/*  32 */     Empty, Contains, Space, Full;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TriggerFluidContainer(State state) {
/*  38 */     super(new String[] { "buildcraft:fluid." + state.name().toLowerCase(Locale.ENGLISH), "buildcraft.fluid." + state.name().toLowerCase(Locale.ENGLISH) });
/*  39 */     this.state = state;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  44 */     return (this.state == State.Contains || this.state == State.Space) ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  49 */     return StringUtils.localize("gate.trigger.fluid." + this.state.name().toLowerCase(Locale.ENGLISH));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTriggerActive(TileEntity tile, ForgeDirection side, IStatementContainer statementContainer, IStatementParameter[] parameters) {
/*  54 */     if (tile instanceof IFluidHandler) {
/*  55 */       IFluidHandler container = (IFluidHandler)tile;
/*     */       
/*  57 */       FluidStack searchedFluid = null;
/*     */       
/*  59 */       if (parameters != null && parameters.length >= 1 && parameters[0] != null && parameters[0].getItemStack() != null) {
/*  60 */         searchedFluid = FluidContainerRegistry.getFluidForFilledItem(parameters[0].getItemStack());
/*     */       }
/*     */       
/*  63 */       if (searchedFluid != null) {
/*  64 */         searchedFluid.amount = 1;
/*     */       }
/*     */       
/*  67 */       FluidTankInfo[] liquids = container.getTankInfo(side);
/*  68 */       if (liquids == null || liquids.length == 0) {
/*  69 */         return false;
/*     */       }
/*     */       
/*  72 */       switch (this.state) {
/*     */         case Empty:
/*  74 */           for (FluidTankInfo c : liquids) {
/*  75 */             if (c != null && c.fluid != null && c.fluid.amount > 0 && (searchedFluid == null || searchedFluid.isFluidEqual(c.fluid))) {
/*  76 */               return false;
/*     */             }
/*     */           } 
/*  79 */           return true;
/*     */         case Contains:
/*  81 */           for (FluidTankInfo c : liquids) {
/*  82 */             if (c != null && c.fluid != null && c.fluid.amount > 0 && (searchedFluid == null || searchedFluid.isFluidEqual(c.fluid))) {
/*  83 */               return true;
/*     */             }
/*     */           } 
/*  86 */           return false;
/*     */         case Space:
/*  88 */           if (searchedFluid == null) {
/*  89 */             for (FluidTankInfo c : liquids) {
/*  90 */               if (c != null && (c.fluid == null || c.fluid.amount < c.capacity)) {
/*  91 */                 return true;
/*     */               }
/*     */             } 
/*  94 */             return false;
/*     */           } 
/*  96 */           return (container.fill(side, searchedFluid, false) > 0);
/*     */         case Full:
/*  98 */           if (searchedFluid == null) {
/*  99 */             for (FluidTankInfo c : liquids) {
/* 100 */               if (c != null && (c.fluid == null || c.fluid.amount < c.capacity)) {
/* 101 */                 return false;
/*     */               }
/*     */             } 
/* 104 */             return true;
/*     */           } 
/* 106 */           return (container.fill(side, searchedFluid, false) <= 0);
/*     */       } 
/*     */     
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerIcons(IIconRegister register) {
/* 115 */     this.icon = register.func_94245_a("buildcraftcore:triggers/trigger_liquidcontainer_" + this.state.name().toLowerCase());
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int index) {
/* 120 */     return (IStatementParameter)new StatementParameterItemStack();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\TriggerFluidContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
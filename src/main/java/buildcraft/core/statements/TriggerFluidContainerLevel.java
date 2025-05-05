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
/*     */ public class TriggerFluidContainerLevel
/*     */   extends BCStatement
/*     */   implements ITriggerExternal
/*     */ {
/*     */   public TriggerType type;
/*     */   
/*     */   public enum TriggerType
/*     */   {
/*  32 */     BELOW25(0.25F), BELOW50(0.5F), BELOW75(0.75F);
/*     */     
/*     */     public final float level;
/*     */     
/*     */     TriggerType(float level) {
/*  37 */       this.level = level;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TriggerFluidContainerLevel(TriggerType type) {
/*  44 */     super(new String[] { "buildcraft:fluid." + type.name().toLowerCase(Locale.ENGLISH), "buildcraft.fluid." + type.name().toLowerCase(Locale.ENGLISH) });
/*  45 */     this.type = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  50 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  55 */     return String.format(StringUtils.localize("gate.trigger.fluidlevel.below"), new Object[] { Integer.valueOf((int)(this.type.level * 100.0F)) });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTriggerActive(TileEntity tile, ForgeDirection side, IStatementContainer statementContainer, IStatementParameter[] parameters) {
/*  60 */     if (tile instanceof IFluidHandler) {
/*  61 */       IFluidHandler container = (IFluidHandler)tile;
/*     */       
/*  63 */       FluidStack searchedFluid = null;
/*     */       
/*  65 */       if (parameters != null && parameters.length >= 1 && parameters[0] != null && parameters[0].getItemStack() != null) {
/*  66 */         searchedFluid = FluidContainerRegistry.getFluidForFilledItem(parameters[0].getItemStack());
/*     */       }
/*     */       
/*  69 */       if (searchedFluid != null) {
/*  70 */         searchedFluid.amount = 1;
/*     */       }
/*     */       
/*  73 */       FluidTankInfo[] liquids = container.getTankInfo(side);
/*  74 */       if (liquids == null || liquids.length == 0) {
/*  75 */         return false;
/*     */       }
/*     */       
/*  78 */       for (FluidTankInfo c : liquids) {
/*  79 */         if (c != null) {
/*     */ 
/*     */           
/*  82 */           if (c.fluid == null) {
/*  83 */             if (searchedFluid == null) {
/*  84 */               return true;
/*     */             }
/*  86 */             return (container.fill(side, searchedFluid, false) > 0);
/*     */           } 
/*     */           
/*  89 */           if (searchedFluid == null || searchedFluid.isFluidEqual(c.fluid)) {
/*  90 */             float percentage = c.fluid.amount / c.capacity;
/*  91 */             return (percentage < this.type.level);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerIcons(IIconRegister register) {
/* 102 */     this.icon = register.func_94245_a("buildcraftcore:triggers/trigger_liquidcontainer_" + this.type.name().toLowerCase());
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int index) {
/* 107 */     return (IStatementParameter)new StatementParameterItemStack();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\TriggerFluidContainerLevel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
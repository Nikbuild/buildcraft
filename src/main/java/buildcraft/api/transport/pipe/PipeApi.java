/*     */ package buildcraft.api.transport.pipe;
/*     */ 
/*     */ import buildcraft.api.core.CapabilitiesHelper;
/*     */ import buildcraft.api.mj.MjAPI;
/*     */ import buildcraft.api.transport.IInjectable;
/*     */ import buildcraft.api.transport.IStripesRegistry;
/*     */ import buildcraft.api.transport.pluggable.IPluggableRegistry;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.CapabilityInject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PipeApi
/*     */ {
/*     */   public static IPipeRegistry pipeRegistry;
/*     */   public static IPluggableRegistry pluggableRegistry;
/*     */   public static IStripesRegistry stripeRegistry;
/*     */   public static PipeFlowType flowStructure;
/*     */   public static PipeFlowType flowItems;
/*     */   public static PipeFlowType flowFluids;
/*     */   public static PipeFlowType flowPower;
/*  30 */   public static FluidTransferInfo fluidInfoDefault = new FluidTransferInfo(20, 10);
/*     */ 
/*     */ 
/*     */   
/*  34 */   public static PowerTransferInfo powerInfoDefault = PowerTransferInfo.createFromResistance(8L * MjAPI.MJ, MjAPI.MJ / 32L, false);
/*     */   
/*  36 */   public static final Map<PipeDefinition, FluidTransferInfo> fluidTransferData = new IdentityHashMap<>();
/*  37 */   public static final Map<PipeDefinition, PowerTransferInfo> powerTransferData = new IdentityHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final Capability<IPipeHolder> CAP_PIPE_HOLDER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FluidTransferInfo getFluidTransferInfo(PipeDefinition def) {
/*  52 */     FluidTransferInfo info = fluidTransferData.get(def);
/*  53 */     if (info == null) {
/*  54 */       return fluidInfoDefault;
/*     */     }
/*  56 */     return info;
/*     */   }
/*     */ 
/*     */   
/*     */   public static PowerTransferInfo getPowerTransferInfo(PipeDefinition def) {
/*  61 */     PowerTransferInfo info = powerTransferData.get(def);
/*  62 */     if (info == null) {
/*  63 */       return powerInfoDefault;
/*     */     }
/*  65 */     return info;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FluidTransferInfo
/*     */   {
/*     */     public final int transferPerTick;
/*     */ 
/*     */     
/*     */     public final double transferDelayMultiplier;
/*     */ 
/*     */ 
/*     */     
/*     */     public FluidTransferInfo(int transferPerTick, int transferDelay) {
/*  80 */       this.transferPerTick = transferPerTick;
/*  81 */       if (transferDelay <= 0) {
/*  82 */         transferDelay = 1;
/*     */       }
/*  84 */       this.transferDelayMultiplier = transferDelay;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PowerTransferInfo
/*     */   {
/*     */     public final long transferPerTick;
/*     */     
/*     */     public final long lossPerTick;
/*     */     
/*     */     public final long resistancePerTick;
/*     */     
/*     */     public final boolean isReceiver;
/*     */ 
/*     */     
/*     */     public static PowerTransferInfo createFromLoss(long transferPerTick, long lossPerTick, boolean isReceiver) {
/* 101 */       return new PowerTransferInfo(transferPerTick, lossPerTick, lossPerTick * MjAPI.MJ / transferPerTick, isReceiver);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static PowerTransferInfo createFromResistance(long transferPerTick, long resistancePerTick, boolean isReceiver) {
/* 110 */       return new PowerTransferInfo(transferPerTick, resistancePerTick, resistancePerTick * transferPerTick / MjAPI.MJ, isReceiver);
/*     */     }
/*     */     
/*     */     public PowerTransferInfo(long transferPerTick, long lossPerTick, long resistancePerTick, boolean isReceiver) {
/* 114 */       if (transferPerTick < 10L) {
/* 115 */         transferPerTick = 10L;
/*     */       }
/* 117 */       this.transferPerTick = transferPerTick;
/* 118 */       this.lossPerTick = lossPerTick;
/* 119 */       this.resistancePerTick = resistancePerTick;
/* 120 */       this.isReceiver = isReceiver;
/*     */     }
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 139 */     CapabilitiesHelper.registerCapability(IPipe.class);
/* 140 */     CapabilitiesHelper.registerCapability(IPipeHolder.class);
/* 141 */     CapabilitiesHelper.registerCapability(IInjectable.class);
/* 142 */     CapabilitiesHelper.registerCapability(PipePluggable.class);
/*     */   } @Nonnull
/* 144 */   public static final Capability<IPipe> CAP_PIPE = CapabilitiesHelper.ensureRegistration(capPipe, IPipe.class); @Nonnull
/* 145 */   public static final Capability<PipePluggable> CAP_PLUG = CapabilitiesHelper.ensureRegistration(capPlug, PipePluggable.class); static {
/* 146 */     CAP_PIPE_HOLDER = CapabilitiesHelper.ensureRegistration(capPipeHolder, IPipeHolder.class);
/* 147 */     CAP_INJECTABLE = CapabilitiesHelper.ensureRegistration(capInjectable, IInjectable.class);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static final Capability<IInjectable> CAP_INJECTABLE;
/*     */   @CapabilityInject(IPipeHolder.class)
/*     */   private static Capability<IPipeHolder> capPipeHolder;
/*     */   @CapabilityInject(IPipe.class)
/*     */   private static Capability<IPipe> capPipe;
/*     */   @CapabilityInject(PipePluggable.class)
/*     */   private static Capability<PipePluggable> capPlug;
/*     */   @CapabilityInject(IInjectable.class)
/*     */   private static Capability<IInjectable> capInjectable;
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeApi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
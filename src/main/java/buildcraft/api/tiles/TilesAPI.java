/*    */ package buildcraft.api.tiles;
/*    */ 
/*    */ import buildcraft.api.core.CapabilitiesHelper;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraftforge.common.capabilities.Capability;
/*    */ import net.minecraftforge.common.capabilities.CapabilityInject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TilesAPI
/*    */ {
/*    */   @Nonnull
/*    */   public static final Capability<IControllable> CAP_CONTROLLABLE;
/*    */   @Nonnull
/*    */   public static final Capability<IHasWork> CAP_HAS_WORK;
/*    */   @Nonnull
/*    */   public static final Capability<IHeatable> CAP_HEATABLE;
/*    */   @Nonnull
/*    */   public static final Capability<ITileAreaProvider> CAP_TILE_AREA_PROVIDER;
/*    */   @CapabilityInject(IControllable.class)
/* 24 */   private static final Capability<IControllable> CAP_CONTROLLABLE_FIRST = null;
/*    */   
/*    */   @CapabilityInject(IHasWork.class)
/* 27 */   private static final Capability<IHasWork> CAP_HAS_WORK_FIRST = null;
/*    */   
/*    */   @CapabilityInject(IHeatable.class)
/* 30 */   private static final Capability<IHeatable> CAP_HEATABLE_FIRST = null;
/*    */   
/*    */   @CapabilityInject(ITileAreaProvider.class)
/* 33 */   private static final Capability<ITileAreaProvider> CAP_TILE_AREA_PROVIDER_FIRST = null;
/*    */   
/*    */   static {
/* 36 */     CapabilitiesHelper.registerCapability(IControllable.class);
/* 37 */     CapabilitiesHelper.registerCapability(IHasWork.class);
/* 38 */     CapabilitiesHelper.registerCapability(IHeatable.class);
/* 39 */     CapabilitiesHelper.registerCapability(ITileAreaProvider.class);
/*    */     
/* 41 */     CAP_CONTROLLABLE = CapabilitiesHelper.ensureRegistration(CAP_CONTROLLABLE_FIRST, IControllable.class);
/* 42 */     CAP_HAS_WORK = CapabilitiesHelper.ensureRegistration(CAP_HAS_WORK_FIRST, IHasWork.class);
/* 43 */     CAP_HEATABLE = CapabilitiesHelper.ensureRegistration(CAP_HEATABLE_FIRST, IHeatable.class);
/* 44 */     CAP_TILE_AREA_PROVIDER = CapabilitiesHelper.ensureRegistration(CAP_TILE_AREA_PROVIDER_FIRST, ITileAreaProvider.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\tiles\TilesAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
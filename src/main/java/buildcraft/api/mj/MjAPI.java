/*     */ package buildcraft.api.mj;
/*     */ 
/*     */ import buildcraft.api.core.CapabilitiesHelper;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.CapabilityInject;
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
/*     */ public class MjAPI
/*     */ {
/*  25 */   public static final long ONE_MINECRAFT_JOULE = getMjValue();
/*     */   
/*  27 */   public static final long MJ = ONE_MINECRAFT_JOULE;
/*     */ 
/*     */   
/*  30 */   public static final DecimalFormat MJ_DISPLAY_FORMAT = new DecimalFormat("#,##0.##");
/*     */   
/*  32 */   public static IMjEffectManager EFFECT_MANAGER = NullaryEffectManager.INSTANCE;
/*     */   
/*     */   @Nonnull
/*     */   public static final Capability<IMjConnector> CAP_CONNECTOR;
/*     */   
/*     */   @Nonnull
/*     */   public static final Capability<IMjReceiver> CAP_RECEIVER;
/*     */ 
/*     */   
/*     */   public static String formatMj(long microMj) {
/*  42 */     return formatMjInternal(microMj / MJ); } @Nonnull
/*     */   public static final Capability<IMjRedstoneReceiver> CAP_REDSTONE_RECEIVER; @Nonnull
/*     */   public static final Capability<IMjReadable> CAP_READABLE; @Nonnull
/*     */   public static final Capability<IMjPassiveProvider> CAP_PASSIVE_PROVIDER; private static String formatMjInternal(double val) {
/*  46 */     return MJ_DISPLAY_FORMAT.format(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum NullaryEffectManager
/*     */     implements IMjEffectManager
/*     */   {
/*  56 */     INSTANCE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void createPowerLossEffect(World world, Vec3d center, long microJoulesLost) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void createPowerLossEffect(World world, Vec3d center, EnumFacing direction, long microJoulesLost) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void createPowerLossEffect(World world, Vec3d center, Vec3d direction, long microJoulesLost) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CapabilityInject(IMjConnector.class)
/*  92 */   private static final Capability<IMjConnector> CAP_CONNECTOR_FIRST = null;
/*     */   
/*     */   @CapabilityInject(IMjReceiver.class)
/*  95 */   private static final Capability<IMjReceiver> CAP_RECEIVER_FIRST = null;
/*     */   
/*     */   @CapabilityInject(IMjRedstoneReceiver.class)
/*  98 */   private static final Capability<IMjRedstoneReceiver> CAP_REDSTONE_RECEIVER_FIRST = null;
/*     */   
/*     */   @CapabilityInject(IMjReadable.class)
/* 101 */   private static final Capability<IMjReadable> CAP_READABLE_FIRST = null;
/*     */   
/*     */   @CapabilityInject(IMjPassiveProvider.class)
/* 104 */   private static final Capability<IMjPassiveProvider> CAP_PASSIVE_PROVIDER_FIRST = null;
/*     */   
/*     */   static {
/* 107 */     CapabilitiesHelper.registerCapability(IMjConnector.class);
/* 108 */     CapabilitiesHelper.registerCapability(IMjReceiver.class);
/* 109 */     CapabilitiesHelper.registerCapability(IMjRedstoneReceiver.class);
/* 110 */     CapabilitiesHelper.registerCapability(IMjReadable.class);
/* 111 */     CapabilitiesHelper.registerCapability(IMjPassiveProvider.class);
/*     */     
/* 113 */     CAP_CONNECTOR = CapabilitiesHelper.ensureRegistration(CAP_CONNECTOR_FIRST, IMjConnector.class);
/* 114 */     CAP_RECEIVER = CapabilitiesHelper.ensureRegistration(CAP_RECEIVER_FIRST, IMjReceiver.class);
/* 115 */     CAP_REDSTONE_RECEIVER = CapabilitiesHelper.ensureRegistration(CAP_REDSTONE_RECEIVER_FIRST, IMjRedstoneReceiver.class);
/* 116 */     CAP_READABLE = CapabilitiesHelper.ensureRegistration(CAP_READABLE_FIRST, IMjReadable.class);
/* 117 */     CAP_PASSIVE_PROVIDER = CapabilitiesHelper.ensureRegistration(CAP_PASSIVE_PROVIDER_FIRST, IMjPassiveProvider.class);
/*     */   }
/*     */   
/*     */   private static long getMjValue() {
/* 121 */     return 1000000L;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\mj\MjAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
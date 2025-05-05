/*    */ package buildcraft.api.mj;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraftforge.common.capabilities.Capability;
/*    */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MjCapabilityHelper
/*    */   implements ICapabilityProvider
/*    */ {
/*    */   @Nonnull
/*    */   private final IMjConnector connector;
/*    */   @Nullable
/*    */   private final IMjReceiver receiver;
/*    */   @Nullable
/*    */   private final IMjRedstoneReceiver rsReceiver;
/*    */   @Nullable
/*    */   private final IMjReadable readable;
/*    */   @Nullable
/*    */   private final IMjPassiveProvider provider;
/*    */   
/*    */   public MjCapabilityHelper(@Nonnull IMjConnector mj) {
/* 30 */     this.connector = mj;
/* 31 */     this.receiver = (mj instanceof IMjReceiver) ? (IMjReceiver)mj : null;
/* 32 */     this.rsReceiver = (mj instanceof IMjRedstoneReceiver) ? (IMjRedstoneReceiver)mj : null;
/* 33 */     this.readable = (mj instanceof IMjReadable) ? (IMjReadable)mj : null;
/* 34 */     this.provider = (mj instanceof IMjPassiveProvider) ? (IMjPassiveProvider)mj : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
/* 39 */     return (getCapability(capability, facing) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
/* 44 */     if (capability == MjAPI.CAP_CONNECTOR) {
/* 45 */       return (T)MjAPI.CAP_CONNECTOR.cast(this.connector);
/*    */     }
/* 47 */     if (capability == MjAPI.CAP_RECEIVER) {
/* 48 */       return (T)MjAPI.CAP_RECEIVER.cast(this.receiver);
/*    */     }
/* 50 */     if (capability == MjAPI.CAP_REDSTONE_RECEIVER) {
/* 51 */       return (T)MjAPI.CAP_REDSTONE_RECEIVER.cast(this.rsReceiver);
/*    */     }
/* 53 */     if (capability == MjAPI.CAP_READABLE) {
/* 54 */       return (T)MjAPI.CAP_READABLE.cast(this.readable);
/*    */     }
/* 56 */     if (capability == MjAPI.CAP_PASSIVE_PROVIDER) {
/* 57 */       return (T)MjAPI.CAP_PASSIVE_PROVIDER.cast(this.provider);
/*    */     }
/* 59 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\mj\MjCapabilityHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
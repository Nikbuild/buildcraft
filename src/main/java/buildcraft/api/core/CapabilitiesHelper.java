/*    */ package buildcraft.api.core;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraftforge.common.capabilities.Capability;
/*    */ import net.minecraftforge.common.capabilities.CapabilityManager;
/*    */ 
/*    */ 
/*    */ public class CapabilitiesHelper
/*    */ {
/*    */   public static <T> void registerCapability(Class<T> clazz) {
/* 13 */     CapabilityManager.INSTANCE.register(clazz, new VoidStorage(), () -> {
/*    */           throw new IllegalStateException("You must create your own instances!");
/*    */         });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static <T> Capability<T> ensureRegistration(Capability<T> cap, Class<T> clazz) {
/* 20 */     if (cap == null) {
/* 21 */       throw new Error("Capability registration failed for " + clazz);
/*    */     }
/* 23 */     return cap;
/*    */   }
/*    */   
/*    */   public static class VoidStorage<T>
/*    */     implements Capability.IStorage<T> {
/*    */     public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
/* 29 */       throw new IllegalStateException("You must create your own instances!");
/*    */     }
/*    */ 
/*    */     
/*    */     public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
/* 34 */       throw new IllegalStateException("You must create your own instances!");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\CapabilitiesHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
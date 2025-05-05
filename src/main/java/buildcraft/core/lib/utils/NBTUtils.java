/*    */ package buildcraft.core.lib.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.CompressedStreamTools;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTSizeTracker;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NBTUtils
/*    */ {
/*    */   public static NBTTagCompound load(byte[] data) {
/*    */     try {
/* 31 */       NBTTagCompound nbt = CompressedStreamTools.func_152457_a(data, NBTSizeTracker.field_152451_a);
/* 32 */       return nbt;
/* 33 */     } catch (IOException e) {
/* 34 */       e.printStackTrace();
/*    */ 
/*    */       
/* 37 */       return null;
/*    */     } 
/*    */   }
/*    */   public static NBTTagCompound getItemData(ItemStack stack) {
/* 41 */     if (stack == null) {
/* 42 */       return null;
/*    */     }
/* 44 */     NBTTagCompound nbt = stack.func_77978_p();
/* 45 */     if (nbt == null) {
/* 46 */       nbt = new NBTTagCompound();
/* 47 */       stack.func_77982_d(nbt);
/*    */     } 
/* 49 */     return nbt;
/*    */   }
/*    */   
/*    */   public static void writeUUID(NBTTagCompound data, String tag, UUID uuid) {
/* 53 */     if (uuid == null) {
/*    */       return;
/*    */     }
/* 56 */     NBTTagCompound nbtTag = new NBTTagCompound();
/* 57 */     nbtTag.func_74772_a("most", uuid.getMostSignificantBits());
/* 58 */     nbtTag.func_74772_a("least", uuid.getLeastSignificantBits());
/* 59 */     data.func_74782_a(tag, (NBTBase)nbtTag);
/*    */   }
/*    */   
/*    */   public static UUID readUUID(NBTTagCompound data, String tag) {
/* 63 */     if (data.func_74764_b(tag)) {
/* 64 */       NBTTagCompound nbtTag = data.func_74775_l(tag);
/* 65 */       return new UUID(nbtTag.func_74763_f("most"), nbtTag.func_74763_f("least"));
/*    */     } 
/* 67 */     return null;
/*    */   }
/*    */   
/*    */   public static byte[] save(NBTTagCompound compound) {
/*    */     try {
/* 72 */       return CompressedStreamTools.func_74798_a(compound);
/* 73 */     } catch (IOException e) {
/* 74 */       e.printStackTrace();
/* 75 */       return new byte[0];
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\NBTUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
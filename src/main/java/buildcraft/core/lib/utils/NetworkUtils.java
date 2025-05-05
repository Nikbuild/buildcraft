/*     */ package buildcraft.core.lib.utils;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTSizeTracker;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NetworkUtils
/*     */ {
/*     */   public static void writeUTF(ByteBuf data, String str) {
/*     */     try {
/*  21 */       if (str == null) {
/*  22 */         data.writeInt(0);
/*     */         return;
/*     */       } 
/*  25 */       byte[] b = str.getBytes("UTF-8");
/*  26 */       data.writeInt(b.length);
/*  27 */       data.writeBytes(b);
/*  28 */     } catch (UnsupportedEncodingException e) {
/*  29 */       e.printStackTrace();
/*  30 */       data.writeInt(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String readUTF(ByteBuf data) {
/*     */     try {
/*  36 */       int len = data.readInt();
/*  37 */       if (len == 0) {
/*  38 */         return "";
/*     */       }
/*  40 */       byte[] b = new byte[len];
/*  41 */       data.readBytes(b);
/*  42 */       return new String(b, "UTF-8");
/*  43 */     } catch (UnsupportedEncodingException e) {
/*  44 */       e.printStackTrace();
/*  45 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeNBT(ByteBuf data, NBTTagCompound nbt) {
/*     */     try {
/*  51 */       byte[] compressed = CompressedStreamTools.func_74798_a(nbt);
/*  52 */       data.writeInt(compressed.length);
/*  53 */       data.writeBytes(compressed);
/*  54 */     } catch (IOException e) {
/*  55 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static NBTTagCompound readNBT(ByteBuf data) {
/*     */     try {
/*  61 */       int length = data.readInt();
/*  62 */       byte[] compressed = new byte[length];
/*  63 */       data.readBytes(compressed);
/*  64 */       return CompressedStreamTools.func_152457_a(compressed, NBTSizeTracker.field_152451_a);
/*  65 */     } catch (IOException e) {
/*  66 */       e.printStackTrace();
/*  67 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeStack(ByteBuf data, ItemStack stack) {
/*  72 */     if (stack == null || stack.func_77973_b() == null || stack.field_77994_a < 0) {
/*  73 */       data.writeByte(0);
/*     */     }
/*     */     else {
/*     */       
/*  77 */       data.writeByte(MathUtils.clamp(stack.field_77994_a + 1, 0, 64) & 0x7F | (stack.func_77942_o() ? 128 : 0));
/*  78 */       data.writeShort(Item.func_150891_b(stack.func_77973_b()));
/*  79 */       data.writeShort(stack.func_77960_j());
/*  80 */       if (stack.func_77942_o()) {
/*  81 */         writeNBT(data, stack.func_77978_p());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ItemStack readStack(ByteBuf data) {
/*  87 */     int flags = data.readUnsignedByte();
/*  88 */     if (flags == 0) {
/*  89 */       return null;
/*     */     }
/*  91 */     boolean hasCompound = ((flags & 0x80) != 0);
/*  92 */     int stackSize = (flags & 0x7F) - 1;
/*  93 */     int itemId = data.readUnsignedShort();
/*  94 */     int itemDamage = data.readShort();
/*  95 */     ItemStack stack = new ItemStack(Item.func_150899_d(itemId), stackSize, itemDamage);
/*  96 */     if (hasCompound) {
/*  97 */       stack.func_77982_d(readNBT(data));
/*     */     }
/*  99 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeByteArray(ByteBuf stream, byte[] data) {
/* 104 */     stream.writeInt(data.length);
/* 105 */     stream.writeBytes(data);
/*     */   }
/*     */   
/*     */   public static byte[] readByteArray(ByteBuf stream) {
/* 109 */     byte[] data = new byte[stream.readInt()];
/* 110 */     stream.readBytes(data, 0, data.length);
/* 111 */     return data;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\NetworkUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
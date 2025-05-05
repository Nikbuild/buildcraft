/*     */ package buildcraft.core.blueprints;
/*     */ 
/*     */ import buildcraft.api.core.ISerializable;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import org.apache.commons.lang3.ArrayUtils;
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
/*     */ public final class LibraryId
/*     */   implements Comparable<LibraryId>, ISerializable
/*     */ {
/*     */   public static final char BPT_SEP_CHARACTER = '-';
/*     */   public byte[] uniqueId;
/*  27 */   public String name = "";
/*  28 */   public String extension = "tpl";
/*     */ 
/*     */   
/*     */   public String completeId;
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateUniqueId(byte[] data) {
/*     */     try {
/*  37 */       MessageDigest digest = MessageDigest.getInstance("SHA-256");
/*  38 */       byte[] id = digest.digest(data);
/*     */       
/*  40 */       this.uniqueId = id;
/*  41 */     } catch (NoSuchAlgorithmException e) {
/*  42 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write(NBTTagCompound nbt) {
/*  47 */     nbt.func_74773_a("uniqueBptId", this.uniqueId);
/*  48 */     nbt.func_74778_a("name", this.name);
/*  49 */     nbt.func_74778_a("extension", this.extension);
/*     */   }
/*     */   
/*     */   public void read(NBTTagCompound nbt) {
/*  53 */     this.uniqueId = nbt.func_74770_j("uniqueBptId");
/*  54 */     this.name = nbt.func_74779_i("name");
/*  55 */     if (nbt.func_74764_b("kind")) {
/*  56 */       this.extension = (nbt.func_74771_c("kind") > 0) ? "bpt" : "tpl";
/*     */     } else {
/*  58 */       this.extension = nbt.func_74779_i("extension");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  64 */     if (obj instanceof LibraryId) {
/*  65 */       return Arrays.equals(this.uniqueId, ((LibraryId)obj).uniqueId);
/*     */     }
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  73 */     return Arrays.hashCode(ArrayUtils.addAll(this.uniqueId, this.name.getBytes()));
/*     */   }
/*     */   
/*     */   public String getCompleteId() {
/*  77 */     if (this.completeId == null) {
/*  78 */       if (this.uniqueId.length > 0) {
/*  79 */         this
/*  80 */           .completeId = this.name + '-' + toString(this.uniqueId);
/*     */       } else {
/*  82 */         this.completeId = this.name;
/*     */       } 
/*     */     }
/*     */     
/*  86 */     return this.completeId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  91 */     return getCompleteId();
/*     */   }
/*     */   
/*     */   private static char toHex(int i) {
/*  95 */     if (i < 10) {
/*  96 */       return (char)(48 + i);
/*     */     }
/*  98 */     return (char)(87 + i);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int fromHex(char c) {
/* 103 */     if (c >= '0' && c <= '9') {
/* 104 */       return c - 48;
/*     */     }
/* 106 */     return c - 87;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(LibraryId o) {
/* 112 */     return getCompleteId().compareTo(o.getCompleteId());
/*     */   }
/*     */   
/*     */   public static String toString(byte[] bytes) {
/* 116 */     char[] ret = new char[bytes.length * 2];
/*     */     
/* 118 */     for (int i = 0; i < bytes.length; i++) {
/* 119 */       int val = bytes[i] + 128;
/*     */       
/* 121 */       ret[i * 2] = toHex(val >> 4);
/* 122 */       ret[i * 2 + 1] = toHex(val & 0xF);
/*     */     } 
/*     */     
/* 125 */     return new String(ret);
/*     */   }
/*     */   
/*     */   public static byte[] toBytes(String suffix) {
/* 129 */     byte[] result = new byte[suffix.length() / 2];
/*     */     
/* 131 */     for (int i = 0; i < result.length; i++) {
/* 132 */       result[i] = 
/* 133 */         (byte)((byte)fromHex(suffix.charAt(i * 2 + 1)) + (byte)(fromHex(suffix.charAt(i * 2)) << 4));
/*     */       
/* 135 */       result[i] = (byte)(result[i] - 128);
/*     */     } 
/*     */     
/* 138 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 143 */     this.uniqueId = NetworkUtils.readByteArray(stream);
/* 144 */     this.name = NetworkUtils.readUTF(stream);
/* 145 */     this.extension = NetworkUtils.readUTF(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 150 */     NetworkUtils.writeByteArray(stream, this.uniqueId);
/* 151 */     NetworkUtils.writeUTF(stream, this.name);
/* 152 */     NetworkUtils.writeUTF(stream, this.extension);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\LibraryId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
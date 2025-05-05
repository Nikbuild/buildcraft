/*     */ package buildcraft.api.core;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTPrimitive;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ 
/*     */ public enum EnumPipePart
/*     */   implements IStringSerializable
/*     */ {
/*  15 */   DOWN(EnumFacing.DOWN),
/*  16 */   UP(EnumFacing.UP),
/*  17 */   NORTH(EnumFacing.NORTH),
/*  18 */   SOUTH(EnumFacing.SOUTH),
/*  19 */   WEST(EnumFacing.WEST),
/*  20 */   EAST(EnumFacing.EAST),
/*     */   
/*  22 */   CENTER(null); public static final EnumPipePart[] VALUES; public static final EnumPipePart[] FACES; public static final EnumPipePart[] HORIZONTALS;
/*     */   static {
/*  24 */     VALUES = values();
/*     */ 
/*     */ 
/*     */     
/*  28 */     facingMap = Maps.newEnumMap(EnumFacing.class);
/*  29 */     nameMap = Maps.newHashMap();
/*  30 */     MAX_VALUES = (values()).length;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  35 */     for (EnumPipePart part : values()) {
/*  36 */       nameMap.put(part.name(), part);
/*  37 */       if (part.face != null) facingMap.put(part.face, part); 
/*     */     } 
/*  39 */     FACES = fromFacingArray(EnumFacing.field_82609_l);
/*  40 */     HORIZONTALS = fromFacingArray(EnumFacing.field_176754_o);
/*     */   }
/*     */   private static final Map<EnumFacing, EnumPipePart> facingMap; private static final Map<String, EnumPipePart> nameMap; private static final int MAX_VALUES; public final EnumFacing face;
/*     */   private static EnumPipePart[] fromFacingArray(EnumFacing... faces) {
/*  44 */     EnumPipePart[] arr = new EnumPipePart[faces.length];
/*  45 */     for (int i = 0; i < faces.length; i++) {
/*  46 */       arr[i] = fromFacing(faces[i]);
/*     */     }
/*  48 */     return arr;
/*     */   }
/*     */   
/*     */   public static int ordinal(EnumFacing face) {
/*  52 */     return (face == null) ? 6 : face.ordinal();
/*     */   }
/*     */   
/*     */   public static EnumPipePart fromFacing(EnumFacing face) {
/*  56 */     if (face == null) {
/*  57 */       return CENTER;
/*     */     }
/*  59 */     return facingMap.get(face);
/*     */   }
/*     */   
/*     */   public static EnumPipePart[] validFaces() {
/*  63 */     return FACES;
/*     */   }
/*     */   
/*     */   public static EnumPipePart fromMeta(int meta) {
/*  67 */     if (meta < 0 || meta >= MAX_VALUES) {
/*  68 */       return CENTER;
/*     */     }
/*  70 */     return VALUES[meta];
/*     */   }
/*     */   
/*     */   EnumPipePart(EnumFacing face) {
/*  74 */     this.face = face;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*  78 */     if (this.face == null) return 6; 
/*  79 */     return this.face.func_176745_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_176610_l() {
/*  84 */     return name().toLowerCase(Locale.ROOT);
/*     */   }
/*     */   
/*     */   public EnumPipePart next() {
/*  88 */     switch (this) {
/*     */       case DOWN:
/*  90 */         return EAST;
/*     */       case EAST:
/*  92 */         return NORTH;
/*     */       case NORTH:
/*  94 */         return SOUTH;
/*     */       case SOUTH:
/*  96 */         return UP;
/*     */       case UP:
/*  98 */         return WEST;
/*     */       case WEST:
/* 100 */         return DOWN;
/*     */     } 
/* 102 */     return DOWN;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumPipePart opposite() {
/* 107 */     if (this == CENTER) {
/* 108 */       return CENTER;
/*     */     }
/* 110 */     return fromFacing(this.face.func_176734_d());
/*     */   }
/*     */   
/*     */   public static EnumPipePart readFromNBT(NBTBase base) {
/* 114 */     if (base == null) {
/* 115 */       return CENTER;
/*     */     }
/* 117 */     if (base instanceof NBTTagString) {
/* 118 */       NBTTagString nbtString = (NBTTagString)base;
/* 119 */       String string = nbtString.func_150285_a_();
/* 120 */       return nameMap.getOrDefault(string, CENTER);
/*     */     } 
/* 122 */     byte ord = ((NBTPrimitive)base).func_150290_f();
/* 123 */     if (ord < 0 || ord > 6) {
/* 124 */       return CENTER;
/*     */     }
/* 126 */     return values()[ord];
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTBase writeToNBT() {
/* 131 */     return (NBTBase)new NBTTagString(name());
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\EnumPipePart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
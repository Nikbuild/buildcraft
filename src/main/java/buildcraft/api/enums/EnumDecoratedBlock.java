/*    */ package buildcraft.api.enums;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ 
/*    */ public enum EnumDecoratedBlock
/*    */   implements IStringSerializable {
/*  8 */   DESTROY(0),
/*  9 */   BLUEPRINT(10),
/* 10 */   TEMPLATE(10),
/* 11 */   PAPER(10),
/* 12 */   LEATHER(10),
/* 13 */   LASER_BACK(0); public static final EnumDecoratedBlock[] VALUES;
/*    */   static {
/* 15 */     VALUES = values();
/*    */   }
/*    */   public final int lightValue;
/*    */   
/*    */   EnumDecoratedBlock(int lightValue) {
/* 20 */     this.lightValue = lightValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_176610_l() {
/* 25 */     return name().toLowerCase(Locale.ROOT);
/*    */   }
/*    */   
/*    */   public static EnumDecoratedBlock fromMeta(int meta) {
/* 29 */     if (meta < 0 || meta >= VALUES.length) {
/* 30 */       return DESTROY;
/*    */     }
/* 32 */     return VALUES[meta];
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\enums\EnumDecoratedBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
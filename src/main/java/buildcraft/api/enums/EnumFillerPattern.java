/*    */ package buildcraft.api.enums;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ 
/*    */ @Deprecated
/*    */ public enum EnumFillerPattern
/*    */   implements IStringSerializable {
/*  9 */   NONE,
/* 10 */   BOX,
/* 11 */   CLEAR,
/* 12 */   CYLINDER,
/* 13 */   FILL,
/* 14 */   FLATTEN,
/* 15 */   FRAME,
/* 16 */   HORIZON,
/* 17 */   PYRAMID,
/* 18 */   STAIRS;
/*    */ 
/*    */   
/*    */   public String toString() {
/* 22 */     return func_176610_l();
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_176610_l() {
/* 27 */     return name().toLowerCase(Locale.ROOT);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\enums\EnumFillerPattern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
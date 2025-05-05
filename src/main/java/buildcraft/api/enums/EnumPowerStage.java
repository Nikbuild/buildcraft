/*    */ package buildcraft.api.enums;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ 
/*    */ public enum EnumPowerStage
/*    */   implements IStringSerializable {
/*  8 */   BLUE,
/*  9 */   GREEN,
/* 10 */   YELLOW,
/* 11 */   RED,
/* 12 */   OVERHEAT,
/* 13 */   BLACK;
/*    */   public static final EnumPowerStage[] VALUES;
/*    */   
/*    */   EnumPowerStage() {
/* 17 */     this.modelName = name().toLowerCase(Locale.ROOT);
/*    */   }
/*    */   public String getModelName() {
/* 20 */     return this.modelName;
/*    */   }
/*    */   private final String modelName;
/*    */   
/*    */   public String func_176610_l() {
/* 25 */     return getModelName();
/*    */   }
/*    */   
/*    */   static {
/*    */     VALUES = values();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\enums\EnumPowerStage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
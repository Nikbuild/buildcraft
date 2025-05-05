/*    */ package buildcraft.api.enums;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ 
/*    */ public enum EnumOptionalSnapshotType
/*    */   implements IStringSerializable {
/*  8 */   NONE(null),
/*  9 */   TEMPLATE(EnumSnapshotType.TEMPLATE),
/* 10 */   BLUEPRINT(EnumSnapshotType.BLUEPRINT);
/*    */   
/*    */   public final EnumSnapshotType type;
/*    */   
/*    */   EnumOptionalSnapshotType(EnumSnapshotType type) {
/* 15 */     this.type = type;
/*    */   }
/*    */   
/*    */   public static EnumOptionalSnapshotType fromNullable(EnumSnapshotType type) {
/* 19 */     if (type == null) {
/* 20 */       return NONE;
/*    */     }
/* 22 */     switch (type) {
/*    */       case TEMPLATE:
/* 24 */         return TEMPLATE;
/*    */       case BLUEPRINT:
/* 26 */         return BLUEPRINT;
/*    */     } 
/* 28 */     return NONE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_176610_l() {
/* 34 */     return name().toLowerCase(Locale.ROOT);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\enums\EnumOptionalSnapshotType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
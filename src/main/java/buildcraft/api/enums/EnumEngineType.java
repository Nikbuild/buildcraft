/*    */ package buildcraft.api.enums;
/*    */ import buildcraft.api.core.IEngineType;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ 
/*    */ public enum EnumEngineType implements IStringSerializable, IEngineType {
/*    */   public final String unlocalizedTag;
/*    */   public final String resourceLocation;
/*  8 */   WOOD("core", "wood"),
/*  9 */   STONE("energy", "stone"),
/* 10 */   IRON("energy", "iron"),
/* 11 */   CREATIVE("energy", "creative");
/*    */   
/*    */   public static final EnumEngineType[] VALUES;
/*    */   
/*    */   static {
/* 16 */     VALUES = values();
/*    */   }
/*    */   EnumEngineType(String mod, String loc) {
/* 19 */     this.unlocalizedTag = loc;
/* 20 */     this.resourceLocation = "buildcraft" + mod + ":blocks/engine/inv/" + loc;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getItemModelLocation() {
/* 25 */     return this.resourceLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_176610_l() {
/* 30 */     return this.unlocalizedTag;
/*    */   }
/*    */   
/*    */   public static EnumEngineType fromMeta(int meta) {
/* 34 */     if (meta < 0 || meta >= VALUES.length) {
/* 35 */       meta = 0;
/*    */     }
/* 37 */     return VALUES[meta];
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\enums\EnumEngineType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
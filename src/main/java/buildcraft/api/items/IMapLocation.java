/*    */ package buildcraft.api.items;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public interface IMapLocation extends INamedItem {
/*    */   BlockPos getPoint(@Nonnull ItemStack paramItemStack);
/*    */   
/*    */   IBox getBox(@Nonnull ItemStack paramItemStack);
/*    */   
/*    */   IZone getZone(@Nonnull ItemStack paramItemStack);
/*    */   
/*    */   List<BlockPos> getPath(@Nonnull ItemStack paramItemStack);
/*    */   
/*    */   EnumFacing getPointSide(@Nonnull ItemStack paramItemStack);
/*    */   
/*    */   public enum MapLocationType {
/* 17 */     CLEAN,
/* 18 */     SPOT,
/* 19 */     AREA,
/* 20 */     PATH,
/* 21 */     ZONE,
/*    */     
/* 23 */     PATH_REPEATING;
/*    */     MapLocationType() {
/* 25 */       this.meta = ordinal();
/*    */     } public final int meta;
/*    */     public static MapLocationType getFromStack(@Nonnull ItemStack stack) {
/* 28 */       int dam = stack.func_77952_i();
/* 29 */       if (dam < 0 || dam >= (values()).length) {
/* 30 */         return CLEAN;
/*    */       }
/* 32 */       return values()[dam];
/*    */     }
/*    */     
/*    */     public void setToStack(@Nonnull ItemStack stack) {
/* 36 */       stack.func_77964_b(this.meta);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\items\IMapLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
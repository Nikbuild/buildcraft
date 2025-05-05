/*    */ package buildcraft.api.enums;
/*    */ 
/*    */ import buildcraft.api.BCItems;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ 
/*    */ public enum EnumRedstoneChipset
/*    */   implements IStringSerializable
/*    */ {
/* 12 */   RED,
/* 13 */   IRON,
/* 14 */   GOLD,
/* 15 */   QUARTZ,
/* 16 */   DIAMOND;
/*    */   EnumRedstoneChipset() {
/* 18 */     this.name = name().toLowerCase(Locale.ROOT);
/*    */   }
/*    */   public ItemStack getStack(int stackSize) {
/* 21 */     Item chipset = BCItems.SILICON_REDSTONE_CHIPSET;
/* 22 */     if (chipset == null) {
/* 23 */       return ItemStack.field_190927_a;
/*    */     }
/*    */     
/* 26 */     return new ItemStack(chipset, stackSize, ordinal());
/*    */   }
/*    */   private final String name;
/*    */   public ItemStack getStack() {
/* 30 */     return getStack(1);
/*    */   }
/*    */   
/*    */   public static EnumRedstoneChipset fromStack(ItemStack stack) {
/* 34 */     if (stack == null) {
/* 35 */       return RED;
/*    */     }
/* 37 */     return fromOrdinal(stack.func_77960_j());
/*    */   }
/*    */   
/*    */   public static EnumRedstoneChipset fromOrdinal(int ordinal) {
/* 41 */     if (ordinal < 0 || ordinal >= (values()).length) {
/* 42 */       return RED;
/*    */     }
/* 44 */     return values()[ordinal];
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_176610_l() {
/* 49 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\enums\EnumRedstoneChipset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
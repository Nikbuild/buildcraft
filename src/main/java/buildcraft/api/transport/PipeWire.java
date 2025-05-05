/*    */ package buildcraft.api.transport;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum PipeWire
/*    */ {
/*    */   public static final PipeWire[] VALUES;
/*    */   public static Item item;
/* 18 */   RED, BLUE, GREEN, YELLOW;
/*    */   static {
/* 20 */     VALUES = values();
/*    */   }
/*    */   public PipeWire reverse() {
/* 23 */     switch (this) {
/*    */       case RED:
/* 25 */         return YELLOW;
/*    */       case BLUE:
/* 27 */         return GREEN;
/*    */       case GREEN:
/* 29 */         return BLUE;
/*    */     } 
/* 31 */     return RED;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTag() {
/* 36 */     return name().toLowerCase(Locale.ENGLISH) + "PipeWire";
/*    */   }
/*    */   
/*    */   public String getColor() {
/* 40 */     String name = toString().toLowerCase(Locale.ENGLISH);
/* 41 */     char first = Character.toUpperCase(name.charAt(0));
/* 42 */     return first + name.substring(1);
/*    */   }
/*    */   
/*    */   public ItemStack getStack() {
/* 46 */     return getStack(1);
/*    */   }
/*    */   
/*    */   public ItemStack getStack(int qty) {
/* 50 */     if (item == null) {
/* 51 */       return null;
/*    */     }
/* 53 */     return new ItemStack(item, qty, ordinal());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPipeWire(ItemStack stack) {
/* 58 */     if (stack == null)
/* 59 */       return false; 
/* 60 */     if (stack.func_77973_b() != item) {
/* 61 */       return false;
/*    */     }
/* 63 */     return (stack.func_77960_j() == ordinal());
/*    */   }
/*    */ 
/*    */   
/*    */   public static PipeWire fromOrdinal(int ordinal) {
/* 68 */     if (ordinal < 0 || ordinal >= VALUES.length) {
/* 69 */       return RED;
/*    */     }
/* 71 */     return VALUES[ordinal];
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\transport\PipeWire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
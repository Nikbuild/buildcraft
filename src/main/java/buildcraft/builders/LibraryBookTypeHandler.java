/*    */ package buildcraft.builders;
/*    */ import buildcraft.api.library.LibraryTypeHandler;
/*    */ import buildcraft.api.library.LibraryTypeHandlerNBT;
/*    */ import buildcraft.core.lib.utils.NBTUtils;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class LibraryBookTypeHandler extends LibraryTypeHandlerNBT {
/*    */   public LibraryBookTypeHandler() {
/* 12 */     super("book");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHandler(ItemStack stack, LibraryTypeHandler.HandlerType type) {
/* 17 */     if (type == LibraryTypeHandler.HandlerType.STORE) {
/* 18 */       return (stack.func_77973_b() == Items.field_151164_bB);
/*    */     }
/* 20 */     return (stack.func_77973_b() == Items.field_151099_bA || stack.func_77973_b() == Items.field_151164_bB);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTextColor() {
/* 26 */     return 6834180;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName(ItemStack stack) {
/* 31 */     String s = NBTUtils.getItemData(stack).func_74779_i("title");
/* 32 */     return (s != null) ? s : "";
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack load(ItemStack stack, NBTTagCompound compound) {
/* 37 */     ItemStack out = new ItemStack(Items.field_151164_bB);
/* 38 */     NBTTagCompound outNBT = new NBTTagCompound();
/* 39 */     outNBT.func_74778_a("title", compound.func_74779_i("title"));
/* 40 */     outNBT.func_74778_a("author", compound.func_74779_i("author"));
/* 41 */     outNBT.func_74782_a("pages", (NBTBase)compound.func_150295_c("pages", 8));
/* 42 */     out.func_77982_d(outNBT);
/* 43 */     return out;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean store(ItemStack stack, NBTTagCompound compound) {
/* 48 */     NBTTagCompound inNBT = NBTUtils.getItemData(stack);
/* 49 */     compound.func_74778_a("title", inNBT.func_74779_i("title"));
/* 50 */     compound.func_74778_a("author", inNBT.func_74779_i("author"));
/* 51 */     compound.func_74782_a("pages", (NBTBase)inNBT.func_150295_c("pages", 8));
/* 52 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\LibraryBookTypeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
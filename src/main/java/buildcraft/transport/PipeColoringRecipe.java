/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.core.lib.utils.ColorUtils;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class PipeColoringRecipe
/*    */   implements IRecipe {
/*    */   private ItemStack getResult(InventoryCrafting crafting) {
/* 13 */     ItemStack oneColorPipeStack = null;
/* 14 */     ItemStack pipeStack = null;
/*    */     
/* 16 */     boolean hasDifferentPipes = false;
/*    */     
/* 18 */     boolean isBleach = false;
/* 19 */     ItemStack dye = null;
/*    */     
/* 21 */     for (int i = 0; i < 9; i++) {
/* 22 */       ItemStack stack = crafting.func_70301_a(i);
/* 23 */       if (stack != null && stack.func_77973_b() != null && stack.field_77994_a != 0)
/*    */       {
/*    */ 
/*    */         
/* 27 */         if (stack.func_77973_b() instanceof ItemPipe) {
/* 28 */           if (pipeStack == null) {
/* 29 */             pipeStack = new ItemStack(stack.func_77973_b(), 1, 0);
/* 30 */             oneColorPipeStack = new ItemStack(stack.func_77973_b(), 1, stack.func_77960_j());
/*    */           }
/* 32 */           else if (stack.func_77973_b() == pipeStack.func_77973_b()) {
/* 33 */             pipeStack.field_77994_a++;
/* 34 */             if (oneColorPipeStack.func_77960_j() == oneColorPipeStack.func_77960_j()) {
/* 35 */               oneColorPipeStack.field_77994_a++;
/*    */             }
/*    */           } else {
/* 38 */             hasDifferentPipes = true;
/*    */           }
/*    */         
/* 41 */         } else if (stack.func_77973_b() == Items.field_151131_as) {
/* 42 */           isBleach = true;
/* 43 */         } else if (ColorUtils.isDye(stack)) {
/* 44 */           dye = stack;
/*    */         } 
/*    */       }
/*    */     } 
/* 48 */     if (isBleach && dye != null)
/* 49 */       return null; 
/* 50 */     if (pipeStack != null && (isBleach || (dye != null && pipeStack.field_77994_a == 8)) && !hasDifferentPipes) {
/* 51 */       ItemStack result = pipeStack;
/* 52 */       if (dye != null) {
/* 53 */         result.func_77964_b(ColorUtils.getColorIDFromDye(dye) + 1);
/*    */       }
/* 55 */       return result;
/*    */     } 
/*    */     
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_77569_a(InventoryCrafting crafting, World world) {
/* 63 */     return (getResult(crafting) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_77572_b(InventoryCrafting crafting) {
/* 68 */     return getResult(crafting);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77570_a() {
/* 73 */     return 10;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_77571_b() {
/* 78 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeColoringRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.core.builders;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import java.util.List;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class BuilderItemMetaPair
/*    */ {
/*    */   public Item item;
/*    */   public int meta;
/* 14 */   public int position = 0;
/*    */   
/*    */   public BuilderItemMetaPair(ItemStack stack) {
/* 17 */     if (stack != null) {
/* 18 */       this.item = stack.func_77973_b();
/* 19 */       this.meta = stack.func_77960_j();
/*    */     } else {
/* 21 */       this.item = Item.func_150898_a(Blocks.field_150350_a);
/* 22 */       this.meta = 0;
/*    */     } 
/*    */   }
/*    */   
/*    */   public BuilderItemMetaPair(IBuilderContext context, BuildingSlotBlock block) {
/* 27 */     this(findStack(context, block));
/*    */   }
/*    */   
/*    */   private static ItemStack findStack(IBuilderContext context, BuildingSlotBlock block) {
/* 31 */     List<ItemStack> s = block.getRequirements(context);
/* 32 */     return (s.size() > 0) ? s.get(0) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 37 */     if (o instanceof BuilderItemMetaPair) {
/* 38 */       BuilderItemMetaPair imp = (BuilderItemMetaPair)o;
/* 39 */       return (imp.item == this.item && imp.meta == this.meta);
/*    */     } 
/*    */     
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 47 */     return Item.func_150891_b(this.item) * 17 + this.meta;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\BuilderItemMetaPair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
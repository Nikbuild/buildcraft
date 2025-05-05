/*    */ package buildcraft.core.list;
/*    */ 
/*    */ import buildcraft.api.lists.ListMatchHandler;
/*    */ import buildcraft.core.proxy.CoreProxy;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.common.DimensionManager;
/*    */ 
/*    */ public class ListMatchHandlerArmor
/*    */   extends ListMatchHandler {
/*    */   private int getArmorTypeID(ItemStack stack) {
/* 13 */     EntityPlayer player = CoreProxy.proxy.getClientPlayer();
/* 14 */     if (player == null) {
/* 15 */       player = CoreProxy.proxy.getBuildCraftPlayer(DimensionManager.getWorld(0)).get();
/*    */     }
/* 17 */     int atID = 0;
/*    */     
/* 19 */     for (int i = 0; i <= 3; i++) {
/* 20 */       if (stack.func_77973_b().isValidArmor(stack, i, (Entity)player)) {
/* 21 */         atID |= 1 << i;
/*    */       }
/*    */     } 
/*    */     
/* 25 */     return atID;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ListMatchHandler.Type type, ItemStack stack, ItemStack target, boolean precise) {
/* 30 */     if (type == ListMatchHandler.Type.TYPE) {
/* 31 */       int armorTypeIDSource = getArmorTypeID(stack);
/* 32 */       if (armorTypeIDSource > 0) {
/* 33 */         int armorTypeIDTarget = getArmorTypeID(target);
/* 34 */         if (precise) {
/* 35 */           return (armorTypeIDSource == armorTypeIDTarget);
/*    */         }
/* 37 */         return ((armorTypeIDSource & armorTypeIDTarget) != 0);
/*    */       } 
/*    */     } 
/*    */     
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidSource(ListMatchHandler.Type type, ItemStack stack) {
/* 46 */     return (getArmorTypeID(stack) > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\list\ListMatchHandlerArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
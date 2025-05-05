/*    */ package buildcraft.api.template;
/*    */ 
/*    */ import buildcraft.api.core.EnumHandlerPriority;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ITemplateRegistry
/*    */ {
/*    */   default void addHandler(ITemplateHandler handler) {
/* 19 */     addHandler(handler, EnumHandlerPriority.NORMAL);
/*    */   }
/*    */   
/*    */   void addHandler(ITemplateHandler paramITemplateHandler, EnumHandlerPriority paramEnumHandlerPriority);
/*    */   
/*    */   boolean handle(World paramWorld, BlockPos paramBlockPos, EntityPlayer paramEntityPlayer, ItemStack paramItemStack);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\template\ITemplateRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
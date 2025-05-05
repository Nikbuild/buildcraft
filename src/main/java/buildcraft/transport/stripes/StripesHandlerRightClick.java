/*    */ package buildcraft.transport.stripes;
/*    */ 
/*    */ import buildcraft.api.transport.IStripesActivator;
/*    */ import buildcraft.api.transport.IStripesHandler;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemPotion;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ public class StripesHandlerRightClick
/*    */   implements IStripesHandler
/*    */ {
/* 19 */   public static final List<Item> items = new ArrayList<Item>();
/*    */ 
/*    */   
/*    */   public IStripesHandler.StripesHandlerType getType() {
/* 23 */     return IStripesHandler.StripesHandlerType.ITEM_USE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldHandle(ItemStack stack) {
/* 28 */     return ((stack.func_77973_b() == Items.field_151068_bn && ItemPotion.func_77831_g(stack.func_77960_j())) || items
/* 29 */       .contains(stack.func_77973_b()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
/* 36 */     ItemStack remainingStack = stack.func_77973_b().func_77659_a(stack, world, player);
/* 37 */     activator.sendItem(remainingStack, direction.getOpposite());
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\StripesHandlerRightClick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
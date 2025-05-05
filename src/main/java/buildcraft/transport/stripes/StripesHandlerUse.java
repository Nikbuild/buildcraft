/*    */ package buildcraft.transport.stripes;
/*    */ 
/*    */ import buildcraft.api.core.Position;
/*    */ import buildcraft.api.transport.IStripesActivator;
/*    */ import buildcraft.api.transport.IStripesHandler;
/*    */ import buildcraft.core.lib.utils.BlockUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ public class StripesHandlerUse
/*    */   implements IStripesHandler
/*    */ {
/* 20 */   public static final List<Item> items = new ArrayList<Item>();
/*    */ 
/*    */   
/*    */   public IStripesHandler.StripesHandlerType getType() {
/* 24 */     return IStripesHandler.StripesHandlerType.ITEM_USE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldHandle(ItemStack stack) {
/* 29 */     return items.contains(stack.func_77973_b());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
/* 36 */     Position target = new Position(x, y, z, direction);
/* 37 */     target.moveForwards(1.0D);
/*    */     
/* 39 */     if (BlockUtils.useItemOnBlock(world, player, stack, MathHelper.func_76128_c(target.x), 
/* 40 */         MathHelper.func_76128_c(target.y), MathHelper.func_76128_c(target.z), direction
/* 41 */         .getOpposite())) {
/* 42 */       if (stack.field_77994_a > 0) {
/* 43 */         activator.sendItem(stack, direction.getOpposite());
/*    */       }
/* 45 */       return true;
/*    */     } 
/* 47 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\StripesHandlerUse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
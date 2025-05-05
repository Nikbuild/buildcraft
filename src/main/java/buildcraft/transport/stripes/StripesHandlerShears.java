/*    */ package buildcraft.transport.stripes;
/*    */ 
/*    */ import buildcraft.api.transport.IStripesActivator;
/*    */ import buildcraft.api.transport.IStripesHandler;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.IShearable;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StripesHandlerShears
/*    */   implements IStripesHandler
/*    */ {
/*    */   public IStripesHandler.StripesHandlerType getType() {
/* 23 */     return IStripesHandler.StripesHandlerType.ITEM_USE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldHandle(ItemStack stack) {
/* 28 */     return stack.func_77973_b() instanceof net.minecraft.item.ItemShears;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
/* 35 */     Block block = world.func_147439_a(x, y, z);
/*    */     
/* 37 */     if (block instanceof IShearable) {
/* 38 */       IShearable shearableBlock = (IShearable)block;
/* 39 */       if (shearableBlock.isShearable(stack, (IBlockAccess)world, x, y, z)) {
/* 40 */         world.func_72908_a(x, y, z, Block.field_149779_h.func_150495_a(), 1.0F, 1.0F);
/* 41 */         List<ItemStack> drops = shearableBlock.onSheared(stack, (IBlockAccess)world, x, y, z, 
/* 42 */             EnchantmentHelper.func_77506_a(Enchantment.field_77346_s.field_77352_x, stack));
/* 43 */         world.func_147468_f(x, y, z);
/* 44 */         if (stack.func_96631_a(1, player.func_70681_au())) {
/* 45 */           stack.field_77994_a--;
/*    */         }
/* 47 */         if (stack.field_77994_a > 0) {
/* 48 */           activator.sendItem(stack, direction.getOpposite());
/*    */         }
/* 50 */         for (ItemStack dropStack : drops) {
/* 51 */           activator.sendItem(dropStack, direction.getOpposite());
/*    */         }
/* 53 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\StripesHandlerShears.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
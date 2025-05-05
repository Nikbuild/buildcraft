/*    */ package buildcraft.transport.stripes;
/*    */ 
/*    */ import buildcraft.api.transport.IStripesActivator;
/*    */ import buildcraft.api.transport.IStripesHandler;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.projectile.EntityArrow;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ public class StripesHandlerArrow
/*    */   implements IStripesHandler
/*    */ {
/*    */   public IStripesHandler.StripesHandlerType getType() {
/* 18 */     return IStripesHandler.StripesHandlerType.ITEM_USE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldHandle(ItemStack stack) {
/* 23 */     return (stack.func_77973_b() == Items.field_151032_g);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
/* 31 */     EntityArrow entityArrow = new EntityArrow(world, (EntityLivingBase)player, 0.0F);
/* 32 */     entityArrow.func_70107_b(x + 0.5D, y + 0.5D, z + 0.5D);
/* 33 */     entityArrow.func_70239_b(3.0D);
/* 34 */     entityArrow.func_70240_a(1);
/* 35 */     entityArrow.field_70159_w = direction.offsetX * 1.8D + world.field_73012_v.nextGaussian() * 0.007499999832361937D;
/* 36 */     entityArrow.field_70181_x = direction.offsetY * 1.8D + world.field_73012_v.nextGaussian() * 0.007499999832361937D;
/* 37 */     entityArrow.field_70179_y = direction.offsetZ * 1.8D + world.field_73012_v.nextGaussian() * 0.007499999832361937D;
/* 38 */     world.func_72838_d((Entity)entityArrow);
/*    */     
/* 40 */     stack.field_77994_a--;
/* 41 */     if (stack.field_77994_a > 0) {
/* 42 */       activator.sendItem(stack, direction.getOpposite());
/*    */     }
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\StripesHandlerArrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
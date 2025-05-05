/*    */ package buildcraft.transport.stripes;
/*    */ 
/*    */ import buildcraft.api.transport.IStripesActivator;
/*    */ import buildcraft.api.transport.IStripesHandler;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StripesHandlerEntityInteract
/*    */   implements IStripesHandler
/*    */ {
/*    */   public IStripesHandler.StripesHandlerType getType() {
/* 30 */     return IStripesHandler.StripesHandlerType.ITEM_USE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldHandle(ItemStack stack) {
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
/* 43 */     AxisAlignedBB box = AxisAlignedBB.func_72330_a(x, y, z, (x + 1), (y + 1), (z + 1));
/* 44 */     List<?> entities = world.func_72839_b(null, box);
/* 45 */     if (entities.size() <= 0) {
/* 46 */       return false;
/*    */     }
/*    */     
/* 49 */     List<EntityLivingBase> livingEntities = new LinkedList<EntityLivingBase>();
/* 50 */     for (Object entityObj : entities) {
/* 51 */       if (entityObj instanceof EntityLivingBase) {
/* 52 */         livingEntities.add((EntityLivingBase)entityObj);
/*    */       }
/*    */     } 
/*    */     
/* 56 */     player.func_70062_b(0, stack);
/*    */     
/* 58 */     boolean successful = false;
/* 59 */     Collections.shuffle(livingEntities);
/* 60 */     while (livingEntities.size() > 0) {
/* 61 */       EntityLivingBase entity = livingEntities.remove(0);
/*    */       
/* 63 */       if (!player.func_70998_m((Entity)entity)) {
/*    */         continue;
/*    */       }
/* 66 */       successful = true;
/* 67 */       dropItemsExcept(stack, player, activator, direction);
/*    */     } 
/* 69 */     if (stack.field_77994_a > 0 && successful) {
/* 70 */       activator.sendItem(stack, direction.getOpposite());
/*    */     }
/*    */     
/* 73 */     player.func_70062_b(0, null);
/*    */     
/* 75 */     return successful;
/*    */   }
/*    */   
/*    */   private void dropItemsExcept(ItemStack stack, EntityPlayer player, IStripesActivator activator, ForgeDirection direction) {
/* 79 */     for (int i = 0; i < player.field_71071_by.func_70302_i_(); i++) {
/* 80 */       ItemStack invStack = player.field_71071_by.func_70301_a(i);
/* 81 */       if (invStack != null && invStack != stack) {
/* 82 */         player.field_71071_by.func_70299_a(i, null);
/* 83 */         activator.sendItem(invStack, direction.getOpposite());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\StripesHandlerEntityInteract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
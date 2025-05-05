/*    */ package buildcraft.transport.stripes;
/*    */ 
/*    */ import buildcraft.api.transport.IStripesActivator;
/*    */ import buildcraft.api.transport.IStripesHandler;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.item.EntityMinecart;
/*    */ import net.minecraft.entity.item.EntityMinecartContainer;
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
/*    */ 
/*    */ public class StripesHandlerMinecartDestroy
/*    */   implements IStripesHandler
/*    */ {
/*    */   public IStripesHandler.StripesHandlerType getType() {
/* 31 */     return IStripesHandler.StripesHandlerType.BLOCK_BREAK;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldHandle(ItemStack stack) {
/* 36 */     return true;
/*    */   }
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
/* 49 */     List<EntityMinecart> minecarts = new LinkedList<EntityMinecart>();
/* 50 */     for (Object entityObj : entities) {
/* 51 */       if (entityObj instanceof EntityMinecart) {
/* 52 */         minecarts.add((EntityMinecart)entityObj);
/*    */       }
/*    */     } 
/*    */     
/* 56 */     if (minecarts.size() > 0) {
/* 57 */       Collections.shuffle(minecarts);
/* 58 */       EntityMinecart cart = minecarts.get(0);
/* 59 */       if (cart instanceof EntityMinecartContainer) {
/*    */         
/* 61 */         EntityMinecartContainer container = (EntityMinecartContainer)cart;
/* 62 */         for (int i = 0; i < container.func_70302_i_(); i++) {
/* 63 */           ItemStack s = container.func_70301_a(i);
/* 64 */           if (s != null) {
/* 65 */             container.func_70299_a(i, null);
/*    */             
/* 67 */             if (container.func_70301_a(i) == null) {
/* 68 */               activator.sendItem(s, direction.getOpposite());
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 78 */       cart.func_70106_y();
/* 79 */       activator.sendItem(cart.getCartItem(), direction.getOpposite());
/* 80 */       return true;
/*    */     } 
/*    */     
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\StripesHandlerMinecartDestroy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
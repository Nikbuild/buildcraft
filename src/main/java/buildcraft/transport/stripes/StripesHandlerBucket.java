/*    */ package buildcraft.transport.stripes;
/*    */ 
/*    */ import buildcraft.api.transport.IStripesActivator;
/*    */ import buildcraft.api.transport.IStripesHandler;
/*    */ import buildcraft.core.lib.utils.BlockUtils;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemBucket;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class StripesHandlerBucket
/*    */   implements IStripesHandler
/*    */ {
/* 20 */   private static final ItemStack emptyBucket = new ItemStack(Items.field_151133_ar, 1);
/*    */   
/*    */   private ItemStack getFilledBucket(FluidStack fluidStack, Block underblock) {
/* 23 */     if (underblock == Blocks.field_150353_l)
/* 24 */       return new ItemStack(Items.field_151129_at, 1); 
/* 25 */     if (underblock == Blocks.field_150355_j) {
/* 26 */       return new ItemStack(Items.field_151131_as, 1);
/*    */     }
/* 28 */     return FluidContainerRegistry.fillFluidContainer(fluidStack, emptyBucket);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IStripesHandler.StripesHandlerType getType() {
/* 34 */     return IStripesHandler.StripesHandlerType.ITEM_USE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldHandle(ItemStack stack) {
/* 39 */     return stack.func_77973_b() instanceof ItemBucket;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
/* 46 */     if (world.func_147437_c(x, y, z) && (
/* 47 */       (ItemBucket)stack.func_77973_b()).func_77875_a(world, x, (direction.ordinal() < 2) ? y : (y - 1), z)) {
/* 48 */       activator.sendItem(emptyBucket, direction.getOpposite());
/* 49 */       stack.field_77994_a--;
/* 50 */       if (stack.field_77994_a > 0) {
/* 51 */         activator.sendItem(stack, direction.getOpposite());
/*    */       }
/*    */       
/* 54 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 58 */     if (!FluidContainerRegistry.isEmptyContainer(stack)) {
/* 59 */       activator.sendItem(stack, direction.getOpposite());
/* 60 */       return true;
/*    */     } 
/*    */     
/* 63 */     Block targetBlock = world.func_147439_a(x, y, z);
/* 64 */     FluidStack fluidStack = BlockUtils.drainBlock(targetBlock, world, x, y, z, true);
/*    */     
/* 66 */     if (fluidStack == null) {
/* 67 */       targetBlock = world.func_147439_a(x, y - 1, z);
/* 68 */       fluidStack = BlockUtils.drainBlock(targetBlock, world, x, y - 1, z, true);
/*    */     } 
/*    */     
/* 71 */     ItemStack filledBucket = getFilledBucket(fluidStack, targetBlock);
/*    */     
/* 73 */     if (fluidStack == null || filledBucket == null) {
/* 74 */       activator.sendItem(stack, direction.getOpposite());
/* 75 */       return true;
/*    */     } 
/*    */     
/* 78 */     activator.sendItem(filledBucket, direction.getOpposite());
/* 79 */     stack.field_77994_a--;
/* 80 */     if (stack.field_77994_a > 0) {
/* 81 */       activator.sendItem(stack, direction.getOpposite());
/*    */     }
/*    */     
/* 84 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\StripesHandlerBucket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
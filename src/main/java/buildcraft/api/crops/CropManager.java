/*    */ package buildcraft.api.crops;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public final class CropManager
/*    */ {
/* 15 */   private static List<ICropHandler> handlers = new ArrayList<>();
/*    */ 
/*    */   
/*    */   private static ICropHandler defaultHandler;
/*    */ 
/*    */ 
/*    */   
/*    */   public static void registerHandler(ICropHandler cropHandler) {
/* 23 */     handlers.add(cropHandler);
/*    */   }
/*    */   
/*    */   public static void setDefaultHandler(ICropHandler cropHandler) {
/* 27 */     defaultHandler = cropHandler;
/*    */   }
/*    */   
/*    */   public static ICropHandler getDefaultHandler() {
/* 31 */     return defaultHandler;
/*    */   }
/*    */   
/*    */   public static boolean isSeed(ItemStack stack) {
/* 35 */     for (ICropHandler cropHandler : handlers) {
/* 36 */       if (cropHandler.isSeed(stack)) {
/* 37 */         return true;
/*    */       }
/*    */     } 
/* 40 */     return defaultHandler.isSeed(stack);
/*    */   }
/*    */   
/*    */   public static boolean canSustainPlant(World world, ItemStack seed, BlockPos pos) {
/* 44 */     for (ICropHandler cropHandler : handlers) {
/* 45 */       if (cropHandler.isSeed(seed) && cropHandler.canSustainPlant(world, seed, pos)) {
/* 46 */         return true;
/*    */       }
/*    */     } 
/* 49 */     return (defaultHandler.isSeed(seed) && defaultHandler.canSustainPlant(world, seed, pos));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean plantCrop(World world, EntityPlayer player, ItemStack seed, BlockPos pos) {
/* 56 */     for (ICropHandler cropHandler : handlers) {
/* 57 */       if (cropHandler.isSeed(seed) && cropHandler.canSustainPlant(world, seed, pos) && cropHandler.plantCrop(world, player, seed, pos)) {
/* 58 */         return true;
/*    */       }
/*    */     } 
/* 61 */     if (defaultHandler.isSeed(seed) && defaultHandler.canSustainPlant(world, seed, pos)) {
/* 62 */       return defaultHandler.plantCrop(world, player, seed, pos);
/*    */     }
/* 64 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean isMature(IBlockAccess blockAccess, IBlockState state, BlockPos pos) {
/* 68 */     for (ICropHandler cropHandler : handlers) {
/* 69 */       if (cropHandler.isMature(blockAccess, state, pos)) {
/* 70 */         return true;
/*    */       }
/*    */     } 
/* 73 */     return defaultHandler.isMature(blockAccess, state, pos);
/*    */   }
/*    */   
/*    */   public static boolean harvestCrop(World world, BlockPos pos, NonNullList<ItemStack> drops) {
/* 77 */     IBlockState state = world.func_180495_p(pos);
/* 78 */     for (ICropHandler cropHandler : handlers) {
/* 79 */       if (cropHandler.isMature((IBlockAccess)world, state, pos)) {
/* 80 */         return cropHandler.harvestCrop(world, pos, drops);
/*    */       }
/*    */     } 
/* 83 */     return (defaultHandler.isMature((IBlockAccess)world, state, pos) && defaultHandler.harvestCrop(world, pos, drops));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\crops\CropManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
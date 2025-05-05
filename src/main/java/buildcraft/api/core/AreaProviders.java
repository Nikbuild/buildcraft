/*    */ package buildcraft.api.core;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class AreaProviders
/*    */ {
/* 10 */   public static final List<IAreaProviderGetter> providers = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static List<IAreaProvider> getAreaProviders(World world, BlockPos at) {
/* 18 */     List<IAreaProvider> list = new ArrayList<>();
/* 19 */     for (IAreaProviderGetter getter : providers) {
/* 20 */       list.addAll(getter.getAreaProviders(world, at));
/*    */     }
/* 22 */     return list;
/*    */   }
/*    */   
/*    */   public static interface IAreaProviderGetter {
/*    */     List<IAreaProvider> getAreaProviders(World param1World, BlockPos param1BlockPos);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\core\AreaProviders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.energy;
/*    */ 
/*    */ import cpw.mods.fml.common.eventhandler.Event;
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.entity.player.FillBucketEvent;
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
/*    */ public final class BucketHandler
/*    */ {
/* 26 */   public static BucketHandler INSTANCE = new BucketHandler();
/* 27 */   public Map<Block, Item> buckets = new HashMap<Block, Item>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onBucketFill(FillBucketEvent event) {
/* 34 */     ItemStack result = fillCustomBucket(event.world, event.target);
/*    */     
/* 36 */     if (result == null) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     event.result = result;
/* 41 */     event.setResult(Event.Result.ALLOW);
/*    */   }
/*    */   
/*    */   private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
/* 45 */     Block block = world.func_147439_a(pos.field_72311_b, pos.field_72312_c, pos.field_72309_d);
/*    */     
/* 47 */     Item bucket = this.buckets.get(block);
/*    */     
/* 49 */     if (bucket != null && world.func_72805_g(pos.field_72311_b, pos.field_72312_c, pos.field_72309_d) == 0) {
/* 50 */       world.func_147468_f(pos.field_72311_b, pos.field_72312_c, pos.field_72309_d);
/* 51 */       return new ItemStack(bucket);
/*    */     } 
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\BucketHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
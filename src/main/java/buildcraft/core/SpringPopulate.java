/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import cpw.mods.fml.common.eventhandler.Event;
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.terraingen.PopulateChunkEvent;
/*    */ import net.minecraftforge.event.terraingen.TerrainGen;
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
/*    */ public class SpringPopulate
/*    */ {
/*    */   @SubscribeEvent
/*    */   public void populate(PopulateChunkEvent.Post event) {
/* 28 */     boolean doGen = TerrainGen.populate(event.chunkProvider, event.world, event.rand, event.chunkX, event.chunkZ, event.hasVillageGenerated, PopulateChunkEvent.Populate.EventType.CUSTOM);
/*    */     
/* 30 */     if (!doGen || !BlockSpring.EnumSpring.WATER.canGen) {
/* 31 */       event.setResult(Event.Result.ALLOW);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 36 */     int worldX = event.chunkX << 4;
/* 37 */     int worldZ = event.chunkZ << 4;
/*    */     
/* 39 */     doPopulate(event.world, event.rand, worldX, worldZ);
/*    */   }
/*    */   
/*    */   private void doPopulate(World world, Random random, int x, int z) {
/* 43 */     int dimId = world.field_73011_w.field_76574_g;
/*    */     
/* 45 */     if (dimId == -1 || dimId == 1) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 50 */     if (random.nextFloat() > 0.025F) {
/*    */       return;
/*    */     }
/*    */     
/* 54 */     int posX = x + random.nextInt(16);
/* 55 */     int posZ = z + random.nextInt(16);
/*    */     
/* 57 */     for (int i = 0; i < 5; ) {
/* 58 */       Block candidate = world.func_147439_a(posX, i, posZ);
/*    */       
/* 60 */       if (candidate != Blocks.field_150357_h) {
/*    */         i++;
/*    */         
/*    */         continue;
/*    */       } 
/* 65 */       int y = (i > 0) ? i : (i - 1);
/*    */       
/* 67 */       world.func_147449_b(posX, y + 1, posZ, BuildCraftCore.springBlock);
/*    */       
/* 69 */       for (int j = y + 2; j < world.func_72800_K() && 
/* 70 */         !world.func_147437_c(posX, j, posZ); j++)
/*    */       {
/*    */         
/* 73 */         world.func_147449_b(posX, j, posZ, Blocks.field_150355_j);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\SpringPopulate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
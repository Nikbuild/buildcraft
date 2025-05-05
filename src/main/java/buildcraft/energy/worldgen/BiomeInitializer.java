/*    */ package buildcraft.energy.worldgen;
/*    */ 
/*    */ import buildcraft.BuildCraftEnergy;
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.event.terraingen.WorldTypeEvent;
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
/*    */ 
/*    */ 
/*    */ public class BiomeInitializer
/*    */ {
/*    */   @SubscribeEvent
/*    */   public void initBiomes(WorldTypeEvent.InitBiomeGens event) {
/* 24 */     if (BuildCraftEnergy.biomeOilDesert != null) {
/* 25 */       for (int i = 0; i < event.newBiomeGens.length; i++) {
/* 26 */         event.newBiomeGens[i] = new GenLayerAddOilDesert(event.seed, 1500L, event.newBiomeGens[i]);
/*    */       }
/*    */     }
/* 29 */     if (BuildCraftEnergy.biomeOilOcean != null)
/* 30 */       for (int i = 0; i < event.newBiomeGens.length; i++)
/* 31 */         event.newBiomeGens[i] = new GenLayerAddOilOcean(event.seed, 1500L, event.newBiomeGens[i]);  
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\worldgen\BiomeInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
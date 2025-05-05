/*    */ package buildcraft.energy;
/*    */ 
/*    */ import buildcraft.api.core.BCLog;
/*    */ import buildcraft.api.fuels.ICoolant;
/*    */ import buildcraft.core.IMCHandler;
/*    */ import buildcraft.energy.fuels.CoolantManager;
/*    */ import buildcraft.energy.worldgen.OilPopulate;
/*    */ import cpw.mods.fml.common.event.FMLInterModComms;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IMCHandlerEnergy
/*    */   extends IMCHandler
/*    */ {
/*    */   public void processIMCEvent(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage m) {
/* 20 */     if (m.key.equals("oil-lake-biome")) {
/* 21 */       processOilLakeBiomeIMC(event, m);
/* 22 */     } else if (m.key.equals("oil-gen-exclude")) {
/* 23 */       processOilGenExcludeIMC(event, m);
/* 24 */     } else if (m.key.equals("add-coolant")) {
/* 25 */       processCoolantAddIMC(event, m);
/* 26 */     } else if (m.key.equals("remove-coolant")) {
/* 27 */       processCoolantRemoveIMC(event, m);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void processOilLakeBiomeIMC(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage m) {
/*    */     try {
/* 33 */       String biomeID = m.getStringValue().trim();
/* 34 */       int id = Integer.valueOf(biomeID).intValue();
/* 35 */       if (id >= (BiomeGenBase.func_150565_n()).length) {
/* 36 */         throw new IllegalArgumentException("Biome ID must be less than " + (BiomeGenBase.func_150565_n()).length);
/*    */       }
/* 38 */       OilPopulate.INSTANCE.surfaceDepositBiomes.add(Integer.valueOf(id));
/* 39 */     } catch (Exception ex) {
/* 40 */       BCLog.logger.warn(String.format("Received an invalid oil-lake-biome request %s from mod %s", new Object[] { m.getStringValue(), m.getSender() }));
/*    */     } 
/* 42 */     BCLog.logger.info(String.format("Received a successful oil-lake-biome request %s from mod %s", new Object[] { m.getStringValue(), m.getSender() }));
/*    */   }
/*    */   
/*    */   public static void processOilGenExcludeIMC(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage m) {
/*    */     try {
/* 47 */       String biomeID = m.getStringValue().trim();
/* 48 */       int id = Integer.valueOf(biomeID).intValue();
/* 49 */       if (id >= (BiomeGenBase.func_150565_n()).length) {
/* 50 */         throw new IllegalArgumentException("Biome ID must be less than " + (BiomeGenBase.func_150565_n()).length);
/*    */       }
/* 52 */       OilPopulate.INSTANCE.excludedBiomes.add(Integer.valueOf(id));
/* 53 */     } catch (Exception ex) {
/* 54 */       BCLog.logger.warn(String.format("Received an invalid oil-gen-exclude request %s from mod %s", new Object[] { m.getStringValue(), m.getSender() }));
/*    */     } 
/* 56 */     BCLog.logger.info(String.format("Received a successful oil-gen-exclude request %s from mod %s", new Object[] { m.getStringValue(), m.getSender() }));
/*    */   }
/*    */   
/*    */   public static void processCoolantAddIMC(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage m) {
/* 60 */     boolean failed = false;
/* 61 */     if (!m.isNBTMessage()) {
/* 62 */       failed = true;
/*    */     } else {
/* 64 */       NBTTagCompound tag = m.getNBTValue();
/* 65 */       if (!tag.func_74764_b("coolant") || !tag.func_150297_b("degrees", 3)) {
/* 66 */         failed = true;
/*    */       } else {
/* 68 */         Fluid coolant = FluidRegistry.getFluid(tag.func_74779_i("coolant"));
/* 69 */         if (coolant != null) {
/* 70 */           CoolantManager.INSTANCE.addCoolant(coolant, tag.func_74762_e("degrees"));
/*    */         } else {
/* 72 */           failed = true;
/*    */         } 
/*    */       } 
/*    */     } 
/* 76 */     if (failed) {
/* 77 */       BCLog.logger.warn("Received invalid coolant IMC message from mod %s!", new Object[] { m.getSender() });
/*    */     }
/*    */   }
/*    */   
/*    */   public static void processCoolantRemoveIMC(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage m) {
/* 82 */     boolean failed = false;
/* 83 */     if (m.isStringMessage()) {
/* 84 */       ICoolant coolant = CoolantManager.INSTANCE.getCoolant(FluidRegistry.getFluid(m.getStringValue()));
/* 85 */       if (coolant != null) {
/* 86 */         CoolantManager.INSTANCE.getCoolants().remove(coolant);
/*    */       } else {
/* 88 */         failed = true;
/*    */       } 
/*    */     } else {
/* 91 */       failed = true;
/*    */     } 
/* 93 */     if (failed)
/* 94 */       BCLog.logger.warn("Received invalid coolant IMC message from mod %s!", new Object[] { m.getSender() }); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\IMCHandlerEnergy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.core.BCLog;
/*    */ import buildcraft.api.gates.GateExpansions;
/*    */ import buildcraft.api.gates.IGateExpansion;
/*    */ import buildcraft.core.IMCHandler;
/*    */ import com.google.common.base.Splitter;
/*    */ import com.google.common.base.Strings;
/*    */ import com.google.common.collect.Iterables;
/*    */ import com.google.common.primitives.Ints;
/*    */ import cpw.mods.fml.common.event.FMLInterModComms;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IMCHandlerTransport
/*    */   extends IMCHandler
/*    */ {
/*    */   public void processIMCEvent(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage m) {
/* 24 */     if (m.key.equals("add-facade")) {
/* 25 */       processAddFacadeIMC(event, m);
/* 26 */     } else if (m.key.equals("blacklist-facade")) {
/* 27 */       processBlacklistFacadeIMC(event, m);
/* 28 */     } else if (m.key.equals("add-gate-expansion-recipe")) {
/* 29 */       processGateExpansionRecipeAddIMC(event, m);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void processGateExpansionRecipeAddIMC(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage msg) {
/* 34 */     boolean failed = false;
/* 35 */     if (!msg.isNBTMessage()) {
/* 36 */       failed = true;
/*    */     } else {
/* 38 */       NBTTagCompound recipe = msg.getNBTValue();
/* 39 */       if (!recipe.func_74764_b("id") || !recipe.func_74764_b("expansion") || !recipe.func_74764_b("input")) {
/* 40 */         failed = true;
/*    */       } else {
/* 42 */         IGateExpansion exp = GateExpansions.getExpansion(recipe.func_74779_i("expansion"));
/* 43 */         ItemStack is = ItemStack.func_77949_a(recipe.func_74775_l("input"));
/* 44 */         if (exp == null || is == null) {
/* 45 */           failed = true;
/*    */         } else {
/* 47 */           GateExpansions.registerExpansion(exp, is);
/*    */         } 
/*    */       } 
/*    */     } 
/* 51 */     if (failed) {
/* 52 */       BCLog.logger.warn("Received invalid gate expansion recipe IMC message from mod %s!", new Object[] { msg.getSender() });
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static void processAddFacadeIMC(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage m) {
/*    */     try {
/* 59 */       if (m.isStringMessage()) {
/* 60 */         Splitter splitter = Splitter.on("@").trimResults();
/*    */         
/* 62 */         String[] array = (String[])Iterables.toArray(splitter.split(m.getStringValue()), String.class);
/* 63 */         if (array.length != 2) {
/* 64 */           BCLog.logger.info(String.format("Received an invalid add-facade request %s from mod %s", new Object[] { m.getStringValue(), m.getSender() }));
/*    */         } else {
/* 66 */           String blockName = array[0];
/* 67 */           Integer metaId = Ints.tryParse(array[1]);
/*    */           
/* 69 */           if (Strings.isNullOrEmpty(blockName) || metaId == null) {
/* 70 */             BCLog.logger.info(String.format("Received an invalid add-facade request %s from mod %s", new Object[] { m.getStringValue(), m.getSender() }));
/*    */           } else {
/* 72 */             Block block = (Block)Block.field_149771_c.func_82594_a(blockName);
/* 73 */             BuildCraftTransport.facadeItem.addFacade(new ItemStack(block, 1, metaId.intValue()));
/*    */           } 
/*    */         } 
/* 76 */       } else if (m.isItemStackMessage()) {
/* 77 */         ItemStack modItemStack = m.getItemStackValue();
/* 78 */         BuildCraftTransport.facadeItem.addFacade(modItemStack);
/*    */       } 
/* 80 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public static void processBlacklistFacadeIMC(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage message) {
/*    */     try {
/* 86 */       if (message.isItemStackMessage()) {
/* 87 */         ItemStack modItemStack = message.getItemStackValue();
/*    */         
/* 89 */         Block block = Block.func_149634_a(modItemStack.func_77973_b());
/* 90 */         if (block != null) {
/* 91 */           String blockName = Block.field_149771_c.func_148750_c(block);
/* 92 */           ItemFacade.blacklistFacade(blockName);
/*    */         } 
/*    */       } else {
/* 95 */         BCLog.logger.info(String.format("Invalid blacklist-facade message from mod %s. Send an ItemStackMessage instead.", new Object[] { message.getSender() }));
/*    */       } 
/* 97 */     } catch (Throwable throwable) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\IMCHandlerTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
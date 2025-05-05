/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.gates.GateExpansions;
/*     */ import buildcraft.api.gates.IGateExpansion;
/*     */ import buildcraft.api.recipes.BuildcraftRecipeRegistry;
/*     */ import buildcraft.api.recipes.IIntegrationRecipe;
/*     */ import buildcraft.api.transport.PipeWire;
/*     */ import buildcraft.core.lib.utils.ColorUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import buildcraft.silicon.ItemRedstoneChipset;
/*     */ import buildcraft.transport.gates.GateDefinition;
/*     */ import buildcraft.transport.gates.GateExpansionPulsar;
/*     */ import buildcraft.transport.gates.GateExpansionRedstoneFader;
/*     */ import buildcraft.transport.gates.GateExpansionTimer;
/*     */ import buildcraft.transport.gates.ItemGate;
/*     */ import buildcraft.transport.recipes.AdvancedFacadeRecipe;
/*     */ import buildcraft.transport.recipes.GateExpansionRecipe;
/*     */ import cpw.mods.fml.common.Optional.Method;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TransportSiliconRecipes
/*     */ {
/*     */   @Method(modid = "BuildCraft|Silicon")
/*     */   public static void loadSiliconRecipes() {
/*  35 */     GameRegistry.addShapelessRecipe(new ItemStack(BuildCraftTransport.gateCopier, 1), new Object[] { new ItemStack(BuildCraftCore.wrenchItem), ItemRedstoneChipset.Chipset.RED.getStack(1) });
/*     */     
/*  37 */     if (Utils.isRegistered(BuildCraftTransport.lensItem)) {
/*     */       
/*  39 */       for (int i = 0; i < 16; i++) {
/*  40 */         BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:lens:" + i, 10000, new ItemStack(BuildCraftTransport.lensItem, 2, i), new Object[] {
/*  41 */               ColorUtils.getOreDictionaryName(15 - i), "blockGlass" });
/*  42 */         BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:filter:" + i, 10000, new ItemStack(BuildCraftTransport.lensItem, 2, i + 16), new Object[] {
/*  43 */               ColorUtils.getOreDictionaryName(15 - i), "blockGlass", Blocks.field_150411_aY
/*     */             });
/*     */       } 
/*  46 */       BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:lens:16", 10000, new ItemStack(BuildCraftTransport.lensItem, 2, 32), new Object[] { "blockGlass" });
/*     */       
/*  48 */       BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:filter:16", 10000, new ItemStack(BuildCraftTransport.lensItem, 2, 33), new Object[] { "blockGlass", Blocks.field_150411_aY });
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  53 */     if (Utils.isRegistered(PipeWire.item)) {
/*  54 */       BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:redWire", 5000, PipeWire.RED.getStack(8), new Object[] { "dyeRed", "dustRedstone", "ingotIron" });
/*     */       
/*  56 */       BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:blueWire", 5000, PipeWire.BLUE.getStack(8), new Object[] { "dyeBlue", "dustRedstone", "ingotIron" });
/*     */       
/*  58 */       BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:greenWire", 5000, PipeWire.GREEN.getStack(8), new Object[] { "dyeGreen", "dustRedstone", "ingotIron" });
/*     */       
/*  60 */       BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:yellowWire", 5000, PipeWire.YELLOW.getStack(8), new Object[] { "dyeYellow", "dustRedstone", "ingotIron" });
/*     */ 
/*     */       
/*  63 */       if (Utils.isRegistered(ItemRedstoneChipset.Chipset.RED.getStack()) && Utils.isRegistered(BuildCraftTransport.pipeGate)) {
/*     */         
/*  65 */         BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:simpleGate", Math.round(100000.0F * BuildCraftTransport.gateCostMultiplier), 
/*  66 */             ItemGate.makeGateItem(GateDefinition.GateMaterial.REDSTONE, GateDefinition.GateLogic.AND), new Object[] { ItemRedstoneChipset.Chipset.RED.getStack(), PipeWire.RED
/*  67 */               .getStack() });
/*     */         
/*  69 */         addGateRecipe("Iron", Math.round(200000.0F * BuildCraftTransport.gateCostMultiplier), GateDefinition.GateMaterial.IRON, ItemRedstoneChipset.Chipset.IRON, new PipeWire[] { PipeWire.RED, PipeWire.BLUE });
/*  70 */         addGateRecipe("Gold", Math.round(400000.0F * BuildCraftTransport.gateCostMultiplier), GateDefinition.GateMaterial.GOLD, ItemRedstoneChipset.Chipset.GOLD, new PipeWire[] { PipeWire.RED, PipeWire.BLUE, PipeWire.GREEN });
/*  71 */         addGateRecipe("Quartz", Math.round(600000.0F * BuildCraftTransport.gateCostMultiplier), GateDefinition.GateMaterial.QUARTZ, ItemRedstoneChipset.Chipset.QUARTZ, new PipeWire[] { PipeWire.RED, PipeWire.BLUE, PipeWire.GREEN });
/*  72 */         addGateRecipe("Diamond", Math.round(800000.0F * BuildCraftTransport.gateCostMultiplier), GateDefinition.GateMaterial.DIAMOND, ItemRedstoneChipset.Chipset.DIAMOND, new PipeWire[] { PipeWire.RED, PipeWire.BLUE, PipeWire.GREEN, PipeWire.YELLOW });
/*     */         
/*  74 */         addGateRecipe("Emerald", Math.round(1200000.0F * BuildCraftTransport.gateCostMultiplier), GateDefinition.GateMaterial.EMERALD, ItemRedstoneChipset.Chipset.EMERALD, new PipeWire[] { PipeWire.RED, PipeWire.BLUE, PipeWire.GREEN, PipeWire.YELLOW });
/*     */ 
/*     */         
/*  77 */         BuildcraftRecipeRegistry.integrationTable.addRecipe((IIntegrationRecipe)new GateExpansionRecipe());
/*  78 */         BuildcraftRecipeRegistry.integrationTable.addRecipe((IIntegrationRecipe)new AdvancedFacadeRecipe());
/*     */ 
/*     */         
/*  81 */         GateExpansions.registerExpansion((IGateExpansion)GateExpansionPulsar.INSTANCE, ItemRedstoneChipset.Chipset.PULSATING.getStack());
/*  82 */         GateExpansions.registerExpansion((IGateExpansion)GateExpansionTimer.INSTANCE, ItemRedstoneChipset.Chipset.QUARTZ.getStack());
/*  83 */         GateExpansions.registerExpansion((IGateExpansion)GateExpansionRedstoneFader.INSTANCE, ItemRedstoneChipset.Chipset.COMP.getStack());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Method(modid = "BuildCraft|Silicon")
/*     */   private static void addGateRecipe(String materialName, int energyCost, GateDefinition.GateMaterial material, ItemRedstoneChipset.Chipset chipset, PipeWire... pipeWire) {
/*  91 */     List<ItemStack> temp = new ArrayList<ItemStack>();
/*  92 */     temp.add(chipset.getStack());
/*  93 */     for (PipeWire wire : pipeWire) {
/*  94 */       temp.add(wire.getStack());
/*     */     }
/*  96 */     Object[] inputs = temp.toArray();
/*  97 */     BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:andGate" + materialName, energyCost, 
/*  98 */         ItemGate.makeGateItem(material, GateDefinition.GateLogic.AND), inputs);
/*  99 */     BuildcraftRecipeRegistry.assemblyTable.addRecipe("buildcraft:orGate" + materialName, energyCost, 
/* 100 */         ItemGate.makeGateItem(material, GateDefinition.GateLogic.OR), inputs);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\TransportSiliconRecipes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
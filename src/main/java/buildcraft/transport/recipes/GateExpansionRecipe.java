/*     */ package buildcraft.transport.recipes;
/*     */ 
/*     */ import buildcraft.api.gates.GateExpansions;
/*     */ import buildcraft.api.gates.IGateExpansion;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.recipes.IntegrationRecipeBC;
/*     */ import buildcraft.silicon.ItemRedstoneChipset;
/*     */ import buildcraft.transport.gates.GateDefinition;
/*     */ import buildcraft.transport.gates.ItemGate;
/*     */ import com.google.common.collect.BiMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GateExpansionRecipe
/*     */   extends IntegrationRecipeBC
/*     */ {
/*  28 */   private static final BiMap<IGateExpansion, ItemStack> recipes = (BiMap<IGateExpansion, ItemStack>)GateExpansions.getRecipesForPostInit();
/*     */   
/*     */   public GateExpansionRecipe() {
/*  31 */     super(25000);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack input) {
/*  36 */     return input.func_77973_b() instanceof ItemGate;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidExpansion(ItemStack input, ItemStack expansion) {
/*  41 */     if (StackHelper.isMatchingItem(ItemRedstoneChipset.Chipset.RED.getStack(), expansion, true, true)) {
/*  42 */       return true;
/*     */     }
/*  44 */     for (ItemStack s : recipes.values()) {
/*  45 */       if (StackHelper.isMatchingItem(s, expansion, true, true)) {
/*  46 */         return true;
/*     */       }
/*     */     } 
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> generateExampleInput() {
/*  54 */     return Collections.unmodifiableList(ItemGate.getAllGates());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> generateExampleOutput() {
/*  59 */     ArrayList<ItemStack> list = new ArrayList<ItemStack>();
/*  60 */     ArrayList<IGateExpansion> exps = new ArrayList<IGateExpansion>();
/*  61 */     int combinations = recipes.size();
/*  62 */     for (IGateExpansion exp : recipes.keySet()) {
/*  63 */       exps.add(exp);
/*     */     }
/*  65 */     for (int i = 0; i < 1 << combinations; i++) {
/*  66 */       for (GateDefinition.GateLogic l : GateDefinition.GateLogic.VALUES) {
/*  67 */         for (GateDefinition.GateMaterial m : GateDefinition.GateMaterial.VALUES) {
/*  68 */           ItemStack s = ItemGate.makeGateItem(m, l);
/*  69 */           for (int j = 0; j < combinations; j++) {
/*  70 */             if ((i >> j & 0x1) != 0) {
/*  71 */               ItemGate.addGateExpansion(s, exps.get(j));
/*     */             }
/*     */           } 
/*  74 */           list.add(s);
/*     */         } 
/*     */       } 
/*     */     } 
/*  78 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<List<ItemStack>> generateExampleExpansions() {
/*  83 */     ArrayList<List<ItemStack>> list = new ArrayList<List<ItemStack>>();
/*  84 */     ArrayList<ItemStack> list2 = new ArrayList<ItemStack>();
/*  85 */     list2.addAll(recipes.values());
/*  86 */     list.add(list2);
/*  87 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack craft(ItemStack input, List<ItemStack> expansions, boolean preview) {
/*  92 */     ItemStack output = input.func_77946_l();
/*  93 */     output.field_77994_a = 1;
/*  94 */     int expansionsAdded = 0;
/*     */     
/*  96 */     for (ItemStack chipset : expansions) {
/*  97 */       if (StackHelper.isMatchingItem(ItemRedstoneChipset.Chipset.RED.getStack(), chipset, true, true)) {
/*  98 */         ItemGate.setLogic(output, (ItemGate.getLogic(output) == GateDefinition.GateLogic.AND) ? GateDefinition.GateLogic.OR : GateDefinition.GateLogic.AND);
/*  99 */         expansionsAdded++;
/*     */         continue;
/*     */       } 
/* 102 */       label24: for (ItemStack expansion : recipes.values()) {
/* 103 */         if (StackHelper.isMatchingItem(chipset, expansion, true, true) && !ItemGate.hasGateExpansion(output, (IGateExpansion)recipes.inverse().get(expansion))) {
/* 104 */           if (!preview) {
/* 105 */             chipset.field_77994_a--;
/*     */             
/* 107 */             ItemGate.addGateExpansion(output, (IGateExpansion)recipes.inverse().get(expansion));
/* 108 */             expansionsAdded++; continue;
/*     */           } 
/*     */           break label24;
/*     */         } 
/*     */       } 
/*     */     } 
/* 114 */     if (expansionsAdded > 0) {
/* 115 */       return output;
/*     */     }
/* 117 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\recipes\GateExpansionRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
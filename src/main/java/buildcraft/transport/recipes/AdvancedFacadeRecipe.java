/*     */ package buildcraft.transport.recipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.JavaTools;
/*     */ import buildcraft.api.facades.FacadeType;
/*     */ import buildcraft.api.facades.IFacadeItem;
/*     */ import buildcraft.api.transport.PipeWire;
/*     */ import buildcraft.core.recipes.IntegrationRecipeBC;
/*     */ import buildcraft.transport.ItemFacade;
/*     */ import java.util.ArrayList;
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
/*     */ public class AdvancedFacadeRecipe
/*     */   extends IntegrationRecipeBC
/*     */ {
/*     */   public AdvancedFacadeRecipe() {
/*  27 */     super(25000, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> generateExampleInput() {
/*  32 */     return ItemFacade.allFacades;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> generateExampleOutput() {
/*  37 */     return ItemFacade.allFacades;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<List<ItemStack>> generateExampleExpansions() {
/*  42 */     List<List<ItemStack>> list = new ArrayList<List<ItemStack>>();
/*  43 */     list.add(ItemFacade.allFacades);
/*  44 */     List<ItemStack> pipeWires = new ArrayList<ItemStack>();
/*  45 */     for (PipeWire wire : PipeWire.values()) {
/*  46 */       pipeWires.add(wire.getStack());
/*     */     }
/*  48 */     list.add(pipeWires);
/*  49 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack input) {
/*  54 */     return input.func_77973_b() instanceof ItemFacade;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidExpansion(ItemStack input, ItemStack expansion) {
/*  59 */     return ((expansion.func_77973_b() instanceof ItemFacade && ((IFacadeItem)expansion
/*  60 */       .func_77973_b()).getFacadeType(expansion) == FacadeType.Basic) || expansion
/*  61 */       .func_77973_b() == BuildCraftTransport.plugItem || expansion
/*  62 */       .func_77973_b() == BuildCraftTransport.pipeWire);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack craft(ItemStack input, List<ItemStack> expansions, boolean preview) {
/*  67 */     PipeWire wire = null;
/*  68 */     ItemStack facade = null;
/*     */     
/*  70 */     for (ItemStack stack : expansions) {
/*  71 */       if (wire == null && stack.func_77973_b() instanceof buildcraft.transport.ItemPipeWire) {
/*  72 */         wire = PipeWire.fromOrdinal(stack.func_77960_j());
/*  73 */         if (!preview)
/*  74 */           stack.field_77994_a--;  continue;
/*     */       } 
/*  76 */       if (facade == null && (stack.func_77973_b() instanceof ItemFacade || stack.func_77973_b() == BuildCraftTransport.pipeWire)) {
/*  77 */         facade = stack;
/*  78 */         if (!preview) {
/*  79 */           stack.field_77994_a--;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     if (wire != null && facade != null) {
/*  85 */       ItemFacade.FacadeState additionalState, states[] = ItemFacade.getFacadeStates(input);
/*     */ 
/*     */       
/*  88 */       if (facade.func_77973_b() == BuildCraftTransport.plugItem) {
/*  89 */         additionalState = ItemFacade.FacadeState.createTransparent(wire);
/*     */       } else {
/*  91 */         additionalState = ItemFacade.getFacadeStates(facade)[0];
/*  92 */         additionalState = new ItemFacade.FacadeState(additionalState.block, additionalState.metadata, wire, additionalState.hollow);
/*     */       } 
/*     */ 
/*     */       
/*  96 */       for (int i = 0; i < states.length; i++) {
/*  97 */         if ((states[i]).wire == wire) {
/*  98 */           states[i] = additionalState;
/*  99 */           return ItemFacade.getFacade(states);
/*     */         } 
/*     */       } 
/*     */       
/* 103 */       return ItemFacade.getFacade((ItemFacade.FacadeState[])JavaTools.concat((Object[])states, (Object[])new ItemFacade.FacadeState[] { additionalState }));
/*     */     } 
/*     */     
/* 106 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\recipes\AdvancedFacadeRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
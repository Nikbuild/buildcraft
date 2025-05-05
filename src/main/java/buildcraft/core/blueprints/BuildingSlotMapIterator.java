/*     */ package buildcraft.core.blueprints;
/*     */ 
/*     */ import buildcraft.core.builders.BuilderItemMetaPair;
/*     */ import buildcraft.core.builders.BuildingSlotBlock;
/*     */ import buildcraft.core.builders.TileAbstractBuilder;
/*     */ import buildcraft.core.lib.fluids.Tank;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.WorldSettings;
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
/*     */ public class BuildingSlotMapIterator
/*     */ {
/*  26 */   public static int MAX_PER_ITEM = 512;
/*     */   private final BptBuilderBlueprint builderBlueprint;
/*     */   private final Map<BuilderItemMetaPair, List<BuildingSlotBlock>> slotMap;
/*  29 */   private final Set<BuilderItemMetaPair> availablePairs = new HashSet<BuilderItemMetaPair>();
/*     */   
/*     */   private final int[] buildStageOccurences;
/*     */   
/*     */   private final boolean isCreative;
/*     */   
/*     */   private Iterator<BuilderItemMetaPair> keyIterator;
/*     */   
/*     */   public BuildingSlotMapIterator(BptBuilderBlueprint builderBlueprint, TileAbstractBuilder builder) {
/*  38 */     this.builderBlueprint = builderBlueprint;
/*  39 */     this.slotMap = builderBlueprint.buildList;
/*  40 */     this.buildStageOccurences = builderBlueprint.buildStageOccurences;
/*  41 */     this
/*  42 */       .isCreative = (builder == null || builder.func_145831_w().func_72912_H().func_76077_q() == WorldSettings.GameType.CREATIVE);
/*     */     
/*  44 */     reset();
/*     */   }
/*     */   private BuilderItemMetaPair currentKey; private List<BuildingSlotBlock> slots; private int slotPos; private int slotFound;
/*     */   public void refresh(TileAbstractBuilder builder) {
/*  48 */     if (!this.isCreative) {
/*  49 */       this.availablePairs.clear();
/*  50 */       this.availablePairs.add(new BuilderItemMetaPair(null));
/*     */       
/*  52 */       if (builder != null) {
/*  53 */         for (int i = 0; i < builder.func_70302_i_(); i++) {
/*  54 */           ItemStack stack = builder.func_70301_a(i);
/*  55 */           if (stack != null) {
/*  56 */             this.availablePairs.add(new BuilderItemMetaPair(stack));
/*     */           }
/*     */         } 
/*  59 */         for (Tank t : builder.getFluidTanks()) {
/*  60 */           if (t.getFluid() != null && t.getFluid().getFluid().getBlock() != null) {
/*  61 */             this.availablePairs.add(new BuilderItemMetaPair(new ItemStack(t.getFluid().getFluid().getBlock())));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void skipKey() {
/*  69 */     findNextKey();
/*     */   }
/*     */   
/*     */   private void findNextKey() {
/*  73 */     this.slotPos = -1;
/*  74 */     this.slotFound = 0;
/*  75 */     this.slots = null;
/*  76 */     while (this.keyIterator.hasNext()) {
/*  77 */       this.currentKey = this.keyIterator.next();
/*  78 */       if (this.isCreative || this.availablePairs.contains(this.currentKey)) {
/*  79 */         this.slots = this.slotMap.get(this.currentKey);
/*  80 */         this.slotPos = this.currentKey.position - 1;
/*     */         return;
/*     */       } 
/*     */     } 
/*  84 */     this.currentKey = null;
/*  85 */     this.keyIterator = this.slotMap.keySet().iterator();
/*     */   }
/*     */   
/*     */   public BuildingSlotBlock next() {
/*  89 */     if (this.slots == null) {
/*  90 */       findNextKey();
/*     */     }
/*  92 */     while (this.slots != null) {
/*  93 */       this.slotPos++;
/*  94 */       while (this.slotFound < MAX_PER_ITEM && this.slotPos < this.slots.size()) {
/*  95 */         BuildingSlotBlock b = this.slots.get(this.slotPos);
/*  96 */         if (b != null) {
/*  97 */           this.slotFound++;
/*  98 */           this.currentKey.position = this.slotPos + 1;
/*  99 */           return b;
/*     */         } 
/* 101 */         this.slotPos++;
/*     */       } 
/* 103 */       if (this.slotFound >= MAX_PER_ITEM) {
/* 104 */         this.currentKey.position = this.slotPos;
/* 105 */       } else if (this.slotPos >= this.slots.size()) {
/* 106 */         this.currentKey.position = 0;
/*     */       } 
/* 108 */       findNextKey();
/*     */     } 
/* 110 */     return null;
/*     */   }
/*     */   
/*     */   public void remove() {
/* 114 */     BuildingSlotBlock slot = this.slots.get(this.slotPos);
/* 115 */     this.slots.set(this.slotPos, null);
/*     */     
/* 117 */     this.builderBlueprint.onRemoveBuildingSlotBlock(slot);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 121 */     this.keyIterator = this.slotMap.keySet().iterator();
/* 122 */     this.currentKey = null;
/* 123 */     this.slots = null;
/* 124 */     findNextKey();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\BuildingSlotMapIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
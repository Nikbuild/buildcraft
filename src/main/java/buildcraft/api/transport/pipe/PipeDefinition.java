/*     */ package buildcraft.api.transport.pipe;
/*     */ 
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.ModContainer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PipeDefinition
/*     */ {
/*     */   public final ResourceLocation identifier;
/*     */   public final IPipeCreator logicConstructor;
/*     */   public final IPipeLoader logicLoader;
/*     */   public final PipeFlowType flowType;
/*     */   
/*     */   public PipeDefinition(PipeDefinitionBuilder builder) {
/*  18 */     this.identifier = builder.identifier;
/*  19 */     this.textures = new String[builder.textureSuffixes.length];
/*  20 */     for (int i = 0; i < this.textures.length; i++) {
/*  21 */       this.textures[i] = builder.texturePrefix + builder.textureSuffixes[i];
/*     */     }
/*  23 */     this.logicConstructor = builder.logicConstructor;
/*  24 */     this.logicLoader = builder.logicLoader;
/*  25 */     this.flowType = builder.flowType;
/*  26 */     this.itemTextureTop = builder.itemTextureTop;
/*  27 */     this.itemTextureCenter = builder.itemTextureCenter;
/*  28 */     this.itemTextureBottom = builder.itemTextureBottom;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String[] textures;
/*     */   
/*     */   public final int itemTextureTop;
/*     */   
/*     */   public final int itemTextureCenter;
/*     */   
/*     */   public final int itemTextureBottom;
/*     */   
/*     */   public static class PipeDefinitionBuilder
/*     */   {
/*     */     public ResourceLocation identifier;
/*     */     public String texturePrefix;
/*  44 */     public String[] textureSuffixes = new String[] { "" };
/*     */     public PipeDefinition.IPipeCreator logicConstructor;
/*     */     public PipeDefinition.IPipeLoader logicLoader;
/*     */     public PipeFlowType flowType;
/*  48 */     public int itemTextureTop = 0;
/*  49 */     public int itemTextureCenter = 0;
/*  50 */     public int itemTextureBottom = 0;
/*     */     
/*     */     public PipeDefinitionBuilder() {}
/*     */     
/*     */     public PipeDefinitionBuilder(ResourceLocation identifier, PipeDefinition.IPipeCreator logicConstructor, PipeDefinition.IPipeLoader logicLoader, PipeFlowType flowType) {
/*  55 */       this.identifier = identifier;
/*  56 */       this.logicConstructor = logicConstructor;
/*  57 */       this.logicLoader = logicLoader;
/*  58 */       this.flowType = flowType;
/*     */     }
/*     */     
/*     */     public PipeDefinitionBuilder idTexPrefix(String both) {
/*  62 */       return id(both).texPrefix(both);
/*     */     }
/*     */     
/*     */     public PipeDefinitionBuilder idTex(String both) {
/*  66 */       return id(both).tex(both, new String[0]);
/*     */     }
/*     */     
/*     */     private static String getActiveModId() {
/*  70 */       ModContainer mod = Loader.instance().activeModContainer();
/*  71 */       if (mod == null) {
/*  72 */         throw new IllegalStateException("Cannot interact with PipeDefinition outside of an actively scoped mod!");
/*     */       }
/*  74 */       return mod.getModId();
/*     */     }
/*     */     
/*     */     public PipeDefinitionBuilder id(String post) {
/*  78 */       this.identifier = new ResourceLocation(getActiveModId(), post);
/*  79 */       return this;
/*     */     }
/*     */     
/*     */     public PipeDefinitionBuilder tex(String prefix, String... suffixes) {
/*  83 */       return texPrefix(prefix).texSuffixes(suffixes);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PipeDefinitionBuilder texPrefix(String prefix) {
/*  91 */       return texPrefixDirect(getActiveModId() + ":pipes/" + prefix);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PipeDefinitionBuilder texPrefixDirect(String prefix) {
/*  99 */       this.texturePrefix = prefix;
/* 100 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PipeDefinitionBuilder texSuffixes(String... suffixes) {
/* 108 */       if (suffixes == null || suffixes.length == 0) {
/* 109 */         this.textureSuffixes = new String[] { "" };
/*     */       } else {
/* 111 */         this.textureSuffixes = suffixes;
/*     */       } 
/* 113 */       return this;
/*     */     }
/*     */     
/*     */     public PipeDefinitionBuilder itemTex(int all) {
/* 117 */       this.itemTextureTop = all;
/* 118 */       this.itemTextureCenter = all;
/* 119 */       this.itemTextureBottom = all;
/* 120 */       return this;
/*     */     }
/*     */     
/*     */     public PipeDefinitionBuilder itemTex(int top, int center, int bottom) {
/* 124 */       this.itemTextureTop = top;
/* 125 */       this.itemTextureCenter = center;
/* 126 */       this.itemTextureBottom = bottom;
/* 127 */       return this;
/*     */     }
/*     */     
/*     */     public PipeDefinitionBuilder logic(PipeDefinition.IPipeCreator creator, PipeDefinition.IPipeLoader loader) {
/* 131 */       this.logicConstructor = creator;
/* 132 */       this.logicLoader = loader;
/* 133 */       return this;
/*     */     }
/*     */     
/*     */     public PipeDefinitionBuilder flowItem() {
/* 137 */       return flow(PipeApi.flowItems);
/*     */     }
/*     */     
/*     */     public PipeDefinitionBuilder flowFluid() {
/* 141 */       return flow(PipeApi.flowFluids);
/*     */     }
/*     */     
/*     */     public PipeDefinitionBuilder flowPower() {
/* 145 */       return flow(PipeApi.flowPower);
/*     */     }
/*     */     
/*     */     public PipeDefinitionBuilder flow(PipeFlowType flow) {
/* 149 */       this.flowType = flow;
/* 150 */       return this;
/*     */     }
/*     */     
/*     */     public PipeDefinition define() {
/* 154 */       PipeDefinition def = new PipeDefinition(this);
/* 155 */       PipeApi.pipeRegistry.registerPipe(def);
/* 156 */       return def;
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface IPipeLoader {
/*     */     PipeBehaviour loadBehaviour(IPipe param1IPipe, NBTTagCompound param1NBTTagCompound);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface IPipeCreator {
/*     */     PipeBehaviour createBehaviour(IPipe param1IPipe);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
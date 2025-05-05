/*     */ package buildcraft.builders.blueprints;
/*     */ 
/*     */ import buildcraft.BuildCraftBuilders;
/*     */ import buildcraft.api.blueprints.IBuilderContext;
/*     */ import buildcraft.api.blueprints.Translation;
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.builders.ItemBlueprint;
/*     */ import buildcraft.builders.TileArchitect;
/*     */ import buildcraft.builders.TileBuilder;
/*     */ import buildcraft.builders.TileConstructionMarker;
/*     */ import buildcraft.core.blueprints.Blueprint;
/*     */ import buildcraft.core.blueprints.BlueprintBase;
/*     */ import buildcraft.core.blueprints.BptContext;
/*     */ import buildcraft.core.blueprints.Template;
/*     */ import buildcraft.core.lib.utils.BlockScanner;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecursiveBlueprintReader
/*     */ {
/*     */   private static final int SCANNER_ITERATION = 100;
/*     */   public TileArchitect architect;
/*     */   private BlockScanner blockScanner;
/*     */   private BlueprintBase writingBlueprint;
/*     */   private BptContext writingContext;
/*  42 */   private int subIndex = 0;
/*     */   private RecursiveBlueprintReader currentSubReader;
/*  44 */   private float computingTime = 0.0F;
/*     */   
/*     */   private boolean done = false;
/*     */   
/*     */   private BlueprintBase parentBlueprint;
/*     */   
/*     */   public RecursiveBlueprintReader(TileArchitect iArchitect) {
/*  51 */     this.architect = iArchitect;
/*  52 */     ItemStack stack = this.architect.func_70301_a(0);
/*     */     
/*  54 */     if (stack != null && stack.func_77973_b() instanceof ItemBlueprint && this.architect.box.isInitialized()) {
/*  55 */       this.blockScanner = new BlockScanner(this.architect.box, this.architect.func_145831_w(), 100);
/*     */       
/*  57 */       if (stack.func_77973_b() instanceof buildcraft.builders.ItemBlueprintStandard) {
/*  58 */         this.writingBlueprint = (BlueprintBase)new Blueprint(this.architect.box.sizeX(), this.architect.box.sizeY(), this.architect.box.sizeZ());
/*  59 */       } else if (stack.func_77973_b() instanceof buildcraft.builders.ItemBlueprintTemplate) {
/*  60 */         this.writingBlueprint = (BlueprintBase)new Template(this.architect.box.sizeX(), this.architect.box.sizeY(), this.architect.box.sizeZ());
/*     */       } 
/*     */       
/*  63 */       this.writingContext = this.writingBlueprint.getContext(this.architect.func_145831_w(), this.architect.box);
/*  64 */       this.writingContext.readConfiguration = this.architect.readConfiguration;
/*     */       
/*  66 */       this.writingBlueprint.id.name = this.architect.name;
/*  67 */       this.writingBlueprint.author = this.architect.currentAuthorName;
/*  68 */       this.writingBlueprint.anchorX = this.architect.field_145851_c - this.architect.box.xMin;
/*  69 */       this.writingBlueprint.anchorY = this.architect.field_145848_d - this.architect.box.yMin;
/*  70 */       this.writingBlueprint.anchorZ = this.architect.field_145849_e - this.architect.box.zMin;
/*     */     } else {
/*  72 */       this.done = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected RecursiveBlueprintReader(TileArchitect iArchitect, BlueprintBase iParentBlueprint) {
/*  77 */     this.parentBlueprint = iParentBlueprint;
/*  78 */     this.architect = iArchitect;
/*     */     
/*  80 */     if (this.architect.box.isInitialized()) {
/*  81 */       this.blockScanner = new BlockScanner(this.architect.box, this.architect.func_145831_w(), 100);
/*     */       
/*  83 */       if (this.parentBlueprint instanceof Blueprint) {
/*  84 */         this.writingBlueprint = (BlueprintBase)new Blueprint(this.architect.box.sizeX(), this.architect.box.sizeY(), this.architect.box.sizeZ());
/*  85 */       } else if (this.parentBlueprint instanceof Template) {
/*  86 */         this.writingBlueprint = (BlueprintBase)new Template(this.architect.box.sizeX(), this.architect.box.sizeY(), this.architect.box.sizeZ());
/*     */       } 
/*     */       
/*  89 */       this.writingContext = this.writingBlueprint.getContext(this.architect.func_145831_w(), this.architect.box);
/*  90 */       this.writingContext.readConfiguration = this.architect.readConfiguration;
/*     */       
/*  92 */       this.writingBlueprint.id.name = this.architect.name;
/*  93 */       this.writingBlueprint.author = this.architect.currentAuthorName;
/*  94 */       this.writingBlueprint.anchorX = this.architect.field_145851_c - this.architect.box.xMin;
/*  95 */       this.writingBlueprint.anchorY = this.architect.field_145848_d - this.architect.box.yMin;
/*  96 */       this.writingBlueprint.anchorZ = this.architect.field_145849_e - this.architect.box.zMin;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void iterate() {
/* 101 */     if (this.done)
/*     */       return; 
/* 103 */     if (this.currentSubReader == null && this.subIndex < this.architect.subBlueprints.size()) {
/* 104 */       BlockIndex subBlock = this.architect.subBlueprints.get(this.subIndex);
/*     */       
/* 106 */       TileEntity subTile = this.architect.func_145831_w().func_147438_o(subBlock.x, subBlock.y, subBlock.z);
/*     */ 
/*     */       
/* 109 */       if (subTile instanceof TileArchitect) {
/* 110 */         TileArchitect subArchitect = (TileArchitect)subTile;
/* 111 */         this.currentSubReader = new RecursiveBlueprintReader(subArchitect, this.writingBlueprint);
/* 112 */       } else if (subTile instanceof TileConstructionMarker || subTile instanceof TileBuilder) {
/* 113 */         BlueprintBase blueprint = null;
/* 114 */         ForgeDirection orientation = ForgeDirection.EAST;
/*     */         
/* 116 */         if (subTile instanceof TileConstructionMarker) {
/* 117 */           TileConstructionMarker marker = (TileConstructionMarker)subTile;
/* 118 */           blueprint = ItemBlueprint.loadBlueprint(marker.itemBlueprint);
/* 119 */           orientation = marker.direction;
/* 120 */         } else if (subTile instanceof TileBuilder) {
/* 121 */           TileBuilder builder = (TileBuilder)subTile;
/* 122 */           blueprint = ItemBlueprint.loadBlueprint(builder.func_70301_a(0));
/*     */           
/* 124 */           orientation = ForgeDirection.values()[this.architect.func_145831_w().func_72805_g(subBlock.x, subBlock.y, subBlock.z)].getOpposite();
/*     */         } 
/*     */         
/* 127 */         if (blueprint != null) {
/* 128 */           this.writingBlueprint.addSubBlueprint(blueprint, subTile.field_145851_c - 
/*     */               
/* 130 */               (this.architect.getBox()).xMin, subTile.field_145848_d - 
/* 131 */               (this.architect.getBox()).yMin, subTile.field_145849_e - 
/* 132 */               (this.architect.getBox()).zMin, orientation);
/*     */         }
/*     */ 
/*     */         
/* 136 */         this.subIndex++;
/*     */       } else {
/* 138 */         this.subIndex++;
/*     */       } 
/* 140 */     } else if (this.currentSubReader != null) {
/* 141 */       this.currentSubReader.iterate();
/*     */       
/* 143 */       if (this.currentSubReader.isDone()) {
/* 144 */         this.writingBlueprint
/* 145 */           .addSubBlueprint(this.currentSubReader.getBlueprint(), this.currentSubReader.architect.field_145851_c - 
/* 146 */             (this.architect.getBox()).xMin, this.currentSubReader.architect.field_145848_d - 
/* 147 */             (this.architect.getBox()).yMin, this.currentSubReader.architect.field_145849_e - 
/* 148 */             (this.architect.getBox()).zMin, 
/* 149 */             ForgeDirection.values()[this.currentSubReader.architect
/* 150 */               .func_145831_w().func_72805_g(this.currentSubReader.architect.field_145851_c, this.currentSubReader.architect.field_145848_d, this.currentSubReader.architect.field_145849_e)]
/*     */ 
/*     */             
/* 153 */             .getOpposite());
/*     */         
/* 155 */         this.currentSubReader = null;
/* 156 */         this.subIndex++;
/*     */       } 
/* 158 */     } else if (this.blockScanner != null && this.blockScanner.blocksLeft() != 0) {
/* 159 */       for (BlockIndex index : this.blockScanner) {
/* 160 */         this.writingBlueprint.readFromWorld((IBuilderContext)this.writingContext, (TileEntity)this.architect, index.x, index.y, index.z);
/*     */       }
/*     */ 
/*     */       
/* 164 */       this
/* 165 */         .computingTime = 1.0F - this.blockScanner.blocksLeft() / this.blockScanner.totalBlocks();
/*     */       
/* 167 */       if (this.blockScanner.blocksLeft() == 0) {
/* 168 */         this.writingBlueprint.readEntitiesFromWorld((IBuilderContext)this.writingContext, (TileEntity)this.architect);
/*     */         
/* 170 */         Translation transform = new Translation();
/*     */         
/* 172 */         transform.x = -(this.writingContext.surroundingBox().pMin()).x;
/* 173 */         transform.y = -(this.writingContext.surroundingBox().pMin()).y;
/* 174 */         transform.z = -(this.writingContext.surroundingBox().pMin()).z;
/*     */         
/* 176 */         this.writingBlueprint.translateToBlueprint(transform);
/*     */ 
/*     */         
/* 179 */         ForgeDirection o = ForgeDirection.values()[this.architect.func_145831_w().func_72805_g(this.architect.field_145851_c, this.architect.field_145848_d, this.architect.field_145849_e)].getOpposite();
/*     */         
/* 181 */         this.writingBlueprint.rotate = this.architect.readConfiguration.rotate;
/* 182 */         this.writingBlueprint.excavate = this.architect.readConfiguration.excavate;
/*     */         
/* 184 */         if (this.writingBlueprint.rotate && 
/* 185 */           o != ForgeDirection.EAST)
/*     */         {
/* 187 */           if (o == ForgeDirection.SOUTH) {
/* 188 */             this.writingBlueprint.rotateLeft(this.writingContext);
/* 189 */             this.writingBlueprint.rotateLeft(this.writingContext);
/* 190 */             this.writingBlueprint.rotateLeft(this.writingContext);
/* 191 */           } else if (o == ForgeDirection.WEST) {
/* 192 */             this.writingBlueprint.rotateLeft(this.writingContext);
/* 193 */             this.writingBlueprint.rotateLeft(this.writingContext);
/* 194 */           } else if (o == ForgeDirection.NORTH) {
/* 195 */             this.writingBlueprint.rotateLeft(this.writingContext);
/*     */           } 
/*     */         }
/*     */       } 
/* 199 */     } else if (this.blockScanner != null) {
/* 200 */       createBlueprint();
/*     */       
/* 202 */       this.done = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private BlueprintBase getBlueprint() {
/* 207 */     return this.writingBlueprint;
/*     */   }
/*     */   
/*     */   public void createBlueprint() {
/* 211 */     this.writingBlueprint.id.name = this.architect.name;
/* 212 */     this.writingBlueprint.author = this.architect.currentAuthorName;
/* 213 */     NBTTagCompound nbt = this.writingBlueprint.getNBT();
/* 214 */     BuildCraftBuilders.serverDB.add(this.writingBlueprint.id, nbt);
/*     */     
/* 216 */     if (this.parentBlueprint == null) {
/* 217 */       this.architect.storeBlueprintStack(this.writingBlueprint.getStack());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isDone() {
/* 222 */     return this.done;
/*     */   }
/*     */   
/*     */   public float getComputingProgressScaled() {
/* 226 */     float sections = (this.architect.subBlueprints.size() + 1);
/*     */     
/* 228 */     float processed = this.subIndex;
/*     */     
/* 230 */     if (this.currentSubReader != null) {
/* 231 */       processed += this.currentSubReader.getComputingProgressScaled();
/*     */     }
/*     */     
/* 234 */     processed += this.computingTime;
/*     */     
/* 236 */     return processed / sections;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\blueprints\RecursiveBlueprintReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
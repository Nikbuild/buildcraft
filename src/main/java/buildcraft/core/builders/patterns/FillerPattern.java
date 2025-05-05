/*     */ package buildcraft.core.builders.patterns;
/*     */ 
/*     */ import buildcraft.api.blueprints.SchematicBlockBase;
/*     */ import buildcraft.api.blueprints.SchematicMask;
/*     */ import buildcraft.api.filler.IFillerPattern;
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.core.Box;
/*     */ import buildcraft.core.blueprints.Blueprint;
/*     */ import buildcraft.core.blueprints.BlueprintBase;
/*     */ import buildcraft.core.blueprints.BptBuilderTemplate;
/*     */ import buildcraft.core.blueprints.SchematicRegistry;
/*     */ import buildcraft.core.blueprints.Template;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
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
/*     */ public abstract class FillerPattern
/*     */   implements IFillerPattern
/*     */ {
/*  36 */   public static final Map<String, FillerPattern> patterns = new TreeMap<String, FillerPattern>();
/*     */   
/*     */   private final String tag;
/*     */   
/*     */   public FillerPattern(String tag) {
/*  41 */     this.tag = tag;
/*  42 */     patterns.put(getUniqueTag(), this);
/*     */   }
/*     */   private IIcon icon; private IIcon blockIcon;
/*     */   
/*     */   public String getDescription() {
/*  47 */     return StringUtils.localize("fillerpattern." + this.tag);
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int index) {
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatement rotateLeft() {
/*  57 */     return (IStatement)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUniqueTag() {
/*  62 */     return "buildcraft:" + this.tag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerIcons(IIconRegister iconRegister) {
/*  67 */     if (!(iconRegister instanceof TextureMap) || ((TextureMap)iconRegister).func_130086_a() == 1) {
/*  68 */       this.icon = iconRegister.func_94245_a("buildcraftcore:fillerPatterns/" + this.tag);
/*     */     }
/*     */     
/*  71 */     if (Loader.isModLoaded("BuildCraft|Builders") && (
/*  72 */       !(iconRegister instanceof TextureMap) || ((TextureMap)iconRegister).func_130086_a() == 0)) {
/*  73 */       this.blockIcon = iconRegister.func_94245_a("buildcraftbuilders:fillerBlockIcons/" + this.tag);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getIcon() {
/*  80 */     return this.icon;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon getBlockOverlay() {
/*  85 */     return this.blockIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  90 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int minParameters() {
/*  95 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     return "Pattern: " + getUniqueTag();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fill(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, Template template) {
/* 109 */     for (int y = yMin; y <= yMax; y++) {
/* 110 */       for (int x = xMin; x <= xMax; x++) {
/* 111 */         for (int z = zMin; z <= zMax; z++) {
/* 112 */           if (isValid(x, y, z, (BlueprintBase)template)) {
/* 113 */             template.put(x, y, z, (SchematicBlockBase)new SchematicMask(true));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void empty(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, Template template) {
/* 124 */     for (int y = yMax; y >= yMin; y--) {
/* 125 */       for (int x = xMin; x <= xMax; x++) {
/* 126 */         for (int z = zMin; z <= zMax; z++) {
/* 127 */           if (isValid(x, y, z, (BlueprintBase)template)) {
/* 128 */             template.put(x, y, z, null);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void flatten(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, Template template) {
/* 139 */     for (int x = xMin; x <= xMax; x++) {
/* 140 */       for (int z = zMin; z <= zMax; z++) {
/* 141 */         for (int y = yMax; y >= yMin; y--) {
/* 142 */           if (isValid(x, y, z, (BlueprintBase)template)) {
/* 143 */             template.put(x, y, z, (SchematicBlockBase)new SchematicMask(true));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract Template getTemplate(Box paramBox, World paramWorld, IStatementParameter[] paramArrayOfIStatementParameter);
/*     */   
/*     */   public Blueprint getBlueprint(Box box, World world, IStatementParameter[] parameters, Block block, int meta) {
/* 153 */     Blueprint result = new Blueprint(box.sizeX(), box.sizeY(), box.sizeZ());
/*     */     
/*     */     try {
/* 156 */       Template tmpl = getTemplate(box, world, parameters);
/*     */       
/* 158 */       for (int x = 0; x < box.sizeX(); x++) {
/* 159 */         for (int y = 0; y < box.sizeY(); y++) {
/* 160 */           for (int z = 0; z < box.sizeZ(); z++) {
/* 161 */             if (tmpl.get(x, y, z) != null) {
/* 162 */               result.put(x, y, z, (SchematicBlockBase)SchematicRegistry.INSTANCE
/* 163 */                   .createSchematicBlock(block, meta));
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 169 */     } catch (Exception e) {
/* 170 */       e.printStackTrace();
/* 171 */       return null;
/*     */     } 
/*     */     
/* 174 */     return result;
/*     */   }
/*     */   
/*     */   public BptBuilderTemplate getTemplateBuilder(Box box, World world, IStatementParameter[] parameters) {
/* 178 */     return new BptBuilderTemplate((BlueprintBase)getTemplate(box, world, parameters), world, box.xMin, box.yMin, box.zMin);
/*     */   }
/*     */   
/*     */   private static boolean isValid(int x, int y, int z, BlueprintBase bpt) {
/* 182 */     return (x >= 0 && y >= 0 && z >= 0 && x < bpt.sizeX && y < bpt.sizeY && z < bpt.sizeZ);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\FillerPattern.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
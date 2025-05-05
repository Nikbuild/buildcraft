/*     */ package buildcraft.core.blueprints;
/*     */ 
/*     */ import buildcraft.api.blueprints.ISchematicRegistry;
/*     */ import buildcraft.api.blueprints.Schematic;
/*     */ import buildcraft.api.blueprints.SchematicBlock;
/*     */ import buildcraft.api.blueprints.SchematicEntity;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.JavaTools;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraftforge.common.config.Configuration;
/*     */ import net.minecraftforge.common.config.Property;
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
/*     */ public final class SchematicRegistry
/*     */   implements ISchematicRegistry
/*     */ {
/*  31 */   public static SchematicRegistry INSTANCE = new SchematicRegistry();
/*  32 */   private static final HashMap<Class<? extends Schematic>, Constructor<?>> emptyConstructorMap = new HashMap<Class<? extends Schematic>, Constructor<?>>();
/*     */   
/*  34 */   public final HashMap<String, SchematicConstructor> schematicBlocks = new HashMap<String, SchematicConstructor>();
/*     */ 
/*     */   
/*  37 */   public final HashMap<Class<? extends Entity>, SchematicConstructor> schematicEntities = new HashMap<Class<? extends Entity>, SchematicConstructor>();
/*     */   
/*  39 */   private final HashSet<String> modsForbidden = new HashSet<String>();
/*  40 */   private final HashSet<String> blocksForbidden = new HashSet<String>();
/*     */ 
/*     */   
/*     */   public class SchematicConstructor
/*     */   {
/*     */     public final Class<? extends Schematic> clazz;
/*     */     
/*     */     public final Object[] params;
/*     */     
/*     */     private final Constructor<?> constructor;
/*     */     
/*     */     SchematicConstructor(Class<? extends Schematic> clazz, Object[] params) throws IllegalArgumentException {
/*  52 */       this.clazz = clazz;
/*  53 */       this.params = params;
/*  54 */       this.constructor = findConstructor();
/*     */     }
/*     */     
/*     */     public Schematic newInstance() throws IllegalAccessException, InvocationTargetException, InstantiationException {
/*  58 */       return (Schematic)this.constructor.newInstance(this.params);
/*     */     }
/*     */     
/*     */     private Constructor<?> findConstructor() throws IllegalArgumentException {
/*  62 */       if (this.params.length == 0 && SchematicRegistry.emptyConstructorMap.containsKey(this.clazz)) {
/*  63 */         return (Constructor)SchematicRegistry.emptyConstructorMap.get(this.clazz);
/*     */       }
/*     */       
/*  66 */       for (Constructor<?> c : this.clazz.getConstructors()) {
/*  67 */         if (c != null) {
/*     */ 
/*     */           
/*  70 */           Class<?>[] typesSignature = c.getParameterTypes();
/*  71 */           if (typesSignature.length == this.params.length)
/*     */           
/*     */           { 
/*     */             
/*  75 */             boolean valid = true;
/*  76 */             for (int i = 0; i < this.params.length; i++) {
/*  77 */               if (this.params[i] != null) {
/*     */ 
/*     */ 
/*     */                 
/*  81 */                 Class<?> paramClass = this.params[i].getClass();
/*  82 */                 if (!typesSignature[i].isAssignableFrom(paramClass) && (typesSignature[i] != int.class || paramClass != Integer.class) && (typesSignature[i] != double.class || paramClass != Double.class) && (typesSignature[i] != boolean.class || paramClass != Boolean.class)) {
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*  87 */                   valid = false; break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*  91 */             if (valid)
/*     */             
/*     */             { 
/*  94 */               if (this.params.length == 0) {
/*  95 */                 SchematicRegistry.emptyConstructorMap.put(this.clazz, c);
/*     */               }
/*  97 */               return c; }  } 
/*     */         } 
/*  99 */       }  throw new IllegalArgumentException("Builder: Could not find matching constructor for class " + this.clazz);
/*     */     }
/*     */   }
/*     */   
/*     */   public void registerSchematicBlock(Block block, Class<? extends Schematic> clazz, Object... params) {
/* 104 */     for (int i = 0; i < 16; i++) {
/* 105 */       registerSchematicBlock(block, i, clazz, params);
/*     */     }
/*     */   }
/*     */   
/*     */   public void registerSchematicBlock(Block block, int meta, Class<? extends Schematic> clazz, Object... params) {
/* 110 */     if (block == null || Block.field_149771_c.func_148750_c(block) == null || "null".equals(Block.field_149771_c.func_148750_c(block))) {
/* 111 */       BCLog.logger.warn("Builder: Mod tried to register block '" + ((block != null) ? block.getClass().getName() : "null") + "' schematic with a null name! Ignoring.");
/*     */       return;
/*     */     } 
/* 114 */     if (this.schematicBlocks.containsKey(toStringKey(block, meta))) {
/* 115 */       throw new RuntimeException("Builder: Block " + Block.field_149771_c.func_148750_c(block) + " is already associated with a schematic.");
/*     */     }
/*     */     
/* 118 */     this.schematicBlocks.put(toStringKey(block, meta), new SchematicConstructor(clazz, params));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerSchematicEntity(Class<? extends Entity> entityClass, Class<? extends SchematicEntity> schematicClass, Object... params) {
/* 124 */     if (this.schematicEntities.containsKey(entityClass)) {
/* 125 */       throw new RuntimeException("Builder: Entity " + entityClass.getName() + " is already associated with a schematic.");
/*     */     }
/* 127 */     this.schematicEntities.put(entityClass, new SchematicConstructor((Class)schematicClass, params));
/*     */   }
/*     */   
/*     */   public SchematicBlock createSchematicBlock(Block block, int metadata) {
/* 131 */     if (block == null || metadata < 0 || metadata >= 16) {
/* 132 */       return null;
/*     */     }
/*     */     
/* 135 */     SchematicConstructor c = this.schematicBlocks.get(toStringKey(block, metadata));
/*     */     
/* 137 */     if (c == null) {
/* 138 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 142 */       SchematicBlock s = (SchematicBlock)c.newInstance();
/* 143 */       s.block = block;
/* 144 */       return s;
/* 145 */     } catch (IllegalAccessException e) {
/* 146 */       e.printStackTrace();
/* 147 */     } catch (IllegalArgumentException e) {
/* 148 */       e.printStackTrace();
/* 149 */     } catch (InvocationTargetException e) {
/* 150 */       e.printStackTrace();
/* 151 */     } catch (InstantiationException e) {
/* 152 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 155 */     return null;
/*     */   }
/*     */   
/*     */   public SchematicEntity createSchematicEntity(Class<? extends Entity> entityClass) {
/* 159 */     if (!this.schematicEntities.containsKey(entityClass)) {
/* 160 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 164 */       SchematicConstructor c = this.schematicEntities.get(entityClass);
/* 165 */       SchematicEntity s = (SchematicEntity)c.newInstance();
/* 166 */       s.entity = entityClass;
/* 167 */       return s;
/* 168 */     } catch (IllegalAccessException e) {
/* 169 */       e.printStackTrace();
/* 170 */     } catch (IllegalArgumentException e) {
/* 171 */       e.printStackTrace();
/* 172 */     } catch (InvocationTargetException e) {
/* 173 */       e.printStackTrace();
/* 174 */     } catch (InstantiationException e) {
/* 175 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 178 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isSupported(Block block, int metadata) {
/* 182 */     return this.schematicBlocks.containsKey(toStringKey(block, metadata));
/*     */   }
/*     */   
/*     */   public boolean isAllowedForBuilding(Block block, int metadata) {
/* 186 */     String name = Block.field_149771_c.func_148750_c(block);
/* 187 */     return (isSupported(block, metadata) && !this.blocksForbidden.contains(name) && !this.modsForbidden.contains(name.split(":", 2)[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readConfiguration(Configuration conf) {
/* 192 */     Property excludedMods = conf.get("blueprints", "excludedMods", new String[0], "mods that should be excluded from the builder.").setLanguageKey("config.blueprints.excludedMods").setRequiresMcRestart(true);
/*     */     
/* 194 */     Property excludedBlocks = conf.get("blueprints", "excludedBlocks", new String[0], "blocks that should be excluded from the builder.").setLanguageKey("config.blueprints.excludedBlocks").setRequiresMcRestart(true);
/*     */     
/* 196 */     for (String id : excludedMods.getStringList()) {
/* 197 */       String strippedId = JavaTools.stripSurroundingQuotes(id.trim());
/*     */       
/* 199 */       if (strippedId.length() > 0) {
/* 200 */         this.modsForbidden.add(strippedId);
/*     */       }
/*     */     } 
/*     */     
/* 204 */     for (String id : excludedBlocks.getStringList()) {
/* 205 */       String strippedId = JavaTools.stripSurroundingQuotes(id.trim());
/*     */       
/* 207 */       if (strippedId.length() > 0) {
/* 208 */         this.blocksForbidden.add(strippedId);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String toStringKey(Block block, int meta) {
/* 214 */     return Block.field_149771_c.func_148750_c(block) + ":" + meta;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\SchematicRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package buildcraft.api.schematics;
/*    */ 
/*    */ import buildcraft.api.core.BuildCraftAPI;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.TreeSet;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicBlockFactoryRegistry
/*    */ {
/* 20 */   private static final Set<SchematicBlockFactory<?>> FACTORIES = new TreeSet<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <S extends ISchematicBlock> void registerFactory(String name, int priority, Predicate<SchematicBlockContext> predicate, Supplier<S> supplier) {
/* 26 */     FACTORIES.add(new SchematicBlockFactory(
/* 27 */           BuildCraftAPI.nameToResourceLocation(name), priority, predicate, supplier));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <S extends ISchematicBlock> void registerFactory(String name, int priority, List<Block> blocks, Supplier<S> supplier) {
/* 38 */     registerFactory(name, priority, context -> blocks.contains(context.block), supplier);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static List<SchematicBlockFactory<?>> getFactories() {
/* 47 */     return (List<SchematicBlockFactory<?>>)ImmutableList.copyOf(FACTORIES);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static <S extends ISchematicBlock> SchematicBlockFactory<S> getFactoryByInstance(S instance) {
/* 53 */     return (SchematicBlockFactory<S>)FACTORIES.stream()
/* 54 */       .filter(schematicBlockFactory -> (schematicBlockFactory.clazz == instance.getClass()))
/* 55 */       .findFirst()
/* 56 */       .orElseThrow(() -> new IllegalStateException("Didn't find a factory for " + instance.getClass()));
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static SchematicBlockFactory<?> getFactoryByName(ResourceLocation name) {
/* 61 */     return FACTORIES.stream()
/* 62 */       .filter(schematicBlockFactory -> schematicBlockFactory.name.equals(name))
/* 63 */       .findFirst()
/* 64 */       .orElse(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\schematics\SchematicBlockFactoryRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
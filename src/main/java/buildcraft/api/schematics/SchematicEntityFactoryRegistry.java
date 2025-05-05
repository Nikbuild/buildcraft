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
/*    */ import net.minecraft.entity.EntityList;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicEntityFactoryRegistry
/*    */ {
/* 20 */   private static final Set<SchematicEntityFactory<?>> FACTORIES = new TreeSet<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <S extends ISchematicEntity> void registerFactory(String name, int priority, Predicate<SchematicEntityContext> predicate, Supplier<S> supplier) {
/* 26 */     FACTORIES.add(new SchematicEntityFactory(
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
/*    */   public static <S extends ISchematicEntity> void registerFactory(String name, int priority, List<ResourceLocation> entities, Supplier<S> supplier) {
/* 38 */     registerFactory(name, priority, context -> entities.contains(EntityList.func_191301_a(context.entity)), supplier);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static List<SchematicEntityFactory<?>> getFactories() {
/* 47 */     return (List<SchematicEntityFactory<?>>)ImmutableList.copyOf(FACTORIES);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static <S extends ISchematicEntity> SchematicEntityFactory<S> getFactoryByInstance(S instance) {
/* 53 */     return (SchematicEntityFactory<S>)FACTORIES.stream()
/* 54 */       .filter(schematicEntityFactory -> (schematicEntityFactory.clazz == instance.getClass()))
/* 55 */       .findFirst()
/* 56 */       .orElseThrow(() -> new IllegalStateException("Didn't find a factory for " + instance.getClass()));
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static SchematicEntityFactory<?> getFactoryByName(ResourceLocation name) {
/* 61 */     return FACTORIES.stream()
/* 62 */       .filter(schematicEntityFactory -> schematicEntityFactory.name.equals(name))
/* 63 */       .findFirst()
/* 64 */       .orElse(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\schematics\SchematicEntityFactoryRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
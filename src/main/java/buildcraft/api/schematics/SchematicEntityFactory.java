/*    */ package buildcraft.api.schematics;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicEntityFactory<S extends ISchematicEntity>
/*    */   implements Comparable<SchematicEntityFactory<?>>
/*    */ {
/*    */   @Nonnull
/*    */   public final ResourceLocation name;
/*    */   public final int priority;
/*    */   @Nonnull
/*    */   public final Predicate<SchematicEntityContext> predicate;
/*    */   @Nonnull
/*    */   public final Supplier<S> supplier;
/*    */   @Nonnull
/*    */   public final Class<S> clazz;
/*    */   
/*    */   public SchematicEntityFactory(@Nonnull ResourceLocation name, int priority, @Nonnull Predicate<SchematicEntityContext> predicate, @Nonnull Supplier<S> supplier) {
/* 26 */     this.name = name;
/* 27 */     this.priority = priority;
/* 28 */     this.predicate = predicate;
/* 29 */     this.supplier = supplier;
/* 30 */     this.clazz = (Class)((ISchematicEntity)supplier.get()).getClass();
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(@Nonnull SchematicEntityFactory o) {
/* 35 */     return (this.priority != o.priority) ? 
/* 36 */       Integer.compare(this.priority, o.priority) : this.name
/* 37 */       .toString().compareTo(o.name.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\schematics\SchematicEntityFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
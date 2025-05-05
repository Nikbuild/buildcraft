/*    */ package buildcraft.api.blueprints;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SchematicFactory<S extends Schematic>
/*    */ {
/* 17 */   private static final HashMap<String, SchematicFactory<?>> factories = new HashMap<String, SchematicFactory<?>>();
/*    */   
/* 19 */   private static final HashMap<Class<? extends Schematic>, SchematicFactory<?>> schematicToFactory = new HashMap<Class<? extends Schematic>, SchematicFactory<?>>();
/*    */ 
/*    */   
/*    */   protected abstract S loadSchematicFromWorldNBT(NBTTagCompound paramNBTTagCompound, MappingRegistry paramMappingRegistry) throws MappingNotFoundException;
/*    */   
/*    */   public void saveSchematicToWorldNBT(NBTTagCompound nbt, S object, MappingRegistry registry) {
/* 25 */     nbt.func_74778_a("factoryID", getClass().getCanonicalName());
/*    */   }
/*    */ 
/*    */   
/*    */   public static Schematic createSchematicFromWorldNBT(NBTTagCompound nbt, MappingRegistry registry) throws MappingNotFoundException {
/* 30 */     String factoryName = nbt.func_74779_i("factoryID");
/*    */     
/* 32 */     if (factories.containsKey(factoryName)) {
/* 33 */       return ((SchematicFactory<Schematic>)factories.get(factoryName)).loadSchematicFromWorldNBT(nbt, registry);
/*    */     }
/* 35 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerSchematicFactory(Class<? extends Schematic> clas, SchematicFactory<?> factory) {
/* 40 */     schematicToFactory.put(clas, factory);
/* 41 */     factories.put(factory.getClass().getCanonicalName(), factory);
/*    */   }
/*    */   
/*    */   public static SchematicFactory getFactory(Class<? extends Schematic> clas) {
/* 45 */     Class<? super Schematic> superClass = clas.getSuperclass();
/*    */     
/* 47 */     if (schematicToFactory.containsKey(clas))
/* 48 */       return schematicToFactory.get(clas); 
/* 49 */     if (superClass != null) {
/* 50 */       return getFactory((Class)superClass);
/*    */     }
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\blueprints\SchematicFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
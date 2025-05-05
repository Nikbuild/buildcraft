/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.MappingNotFoundException;
/*    */ import buildcraft.api.blueprints.MappingRegistry;
/*    */ import buildcraft.api.blueprints.Schematic;
/*    */ import buildcraft.api.blueprints.SchematicEntity;
/*    */ import buildcraft.api.blueprints.SchematicFactory;
/*    */ import buildcraft.core.blueprints.SchematicRegistry;
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
/*    */ public class SchematicFactoryEntity
/*    */   extends SchematicFactory<SchematicEntity>
/*    */ {
/*    */   protected SchematicEntity loadSchematicFromWorldNBT(NBTTagCompound nbt, MappingRegistry registry) throws MappingNotFoundException {
/* 24 */     int entityId = nbt.func_74762_e("entityId");
/* 25 */     SchematicEntity s = SchematicRegistry.INSTANCE.createSchematicEntity(registry.getEntityForId(entityId));
/*    */     
/* 27 */     if (s != null) {
/* 28 */       s.readSchematicFromNBT(nbt, registry);
/*    */     } else {
/* 30 */       return null;
/*    */     } 
/*    */     
/* 33 */     return s;
/*    */   }
/*    */ 
/*    */   
/*    */   public void saveSchematicToWorldNBT(NBTTagCompound nbt, SchematicEntity object, MappingRegistry registry) {
/* 38 */     super.saveSchematicToWorldNBT(nbt, (Schematic)object, registry);
/*    */     
/* 40 */     nbt.func_74768_a("entityId", registry.getIdForEntity(object.entity));
/* 41 */     object.writeSchematicToNBT(nbt, registry);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicFactoryEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
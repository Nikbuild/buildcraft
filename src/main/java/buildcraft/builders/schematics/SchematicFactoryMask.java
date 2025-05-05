/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.MappingNotFoundException;
/*    */ import buildcraft.api.blueprints.MappingRegistry;
/*    */ import buildcraft.api.blueprints.Schematic;
/*    */ import buildcraft.api.blueprints.SchematicFactory;
/*    */ import buildcraft.api.blueprints.SchematicMask;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicFactoryMask
/*    */   extends SchematicFactory<SchematicMask>
/*    */ {
/*    */   protected SchematicMask loadSchematicFromWorldNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 21 */     SchematicMask s = new SchematicMask();
/* 22 */     s.readSchematicFromNBT(nbt, registry);
/*    */     
/* 24 */     return s;
/*    */   }
/*    */ 
/*    */   
/*    */   public void saveSchematicToWorldNBT(NBTTagCompound nbt, SchematicMask object, MappingRegistry registry) {
/* 29 */     super.saveSchematicToWorldNBT(nbt, (Schematic)object, registry);
/*    */     
/* 31 */     object.writeSchematicToNBT(nbt, registry);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicFactoryMask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
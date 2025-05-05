/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.MappingNotFoundException;
/*    */ import buildcraft.api.blueprints.MappingRegistry;
/*    */ import buildcraft.api.blueprints.Schematic;
/*    */ import buildcraft.api.blueprints.SchematicBlock;
/*    */ import buildcraft.api.blueprints.SchematicFactory;
/*    */ import buildcraft.core.blueprints.SchematicRegistry;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
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
/*    */ public class SchematicFactoryBlock
/*    */   extends SchematicFactory<SchematicBlock>
/*    */ {
/*    */   protected SchematicBlock loadSchematicFromWorldNBT(NBTTagCompound nbt, MappingRegistry registry) throws MappingNotFoundException {
/* 26 */     int blockId = nbt.func_74762_e("blockId");
/* 27 */     Block b = registry.getBlockForId(blockId);
/*    */     
/* 29 */     if (b == Blocks.field_150350_a) {
/* 30 */       SchematicBlock schematicBlock = new SchematicBlock();
/* 31 */       schematicBlock.meta = 0;
/* 32 */       schematicBlock.block = Blocks.field_150350_a;
/*    */       
/* 34 */       return schematicBlock;
/*    */     } 
/* 36 */     SchematicBlock s = SchematicRegistry.INSTANCE.createSchematicBlock(b, nbt.func_74762_e("blockMeta"));
/*    */     
/* 38 */     if (s != null) {
/* 39 */       s.readSchematicFromNBT(nbt, registry);
/* 40 */       return s;
/*    */     } 
/*    */ 
/*    */     
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void saveSchematicToWorldNBT(NBTTagCompound nbt, SchematicBlock object, MappingRegistry registry) {
/* 49 */     super.saveSchematicToWorldNBT(nbt, (Schematic)object, registry);
/*    */     
/* 51 */     nbt.func_74768_a("blockId", registry.getIdForBlock(object.block));
/* 52 */     object.writeSchematicToNBT(nbt, registry);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicFactoryBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
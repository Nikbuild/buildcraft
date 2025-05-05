/*    */ package buildcraft.core.builders;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.MappingNotFoundException;
/*    */ import buildcraft.api.blueprints.MappingRegistry;
/*    */ import buildcraft.api.blueprints.Schematic;
/*    */ import buildcraft.api.blueprints.SchematicEntity;
/*    */ import buildcraft.api.blueprints.SchematicFactory;
/*    */ import buildcraft.api.core.Position;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuildingSlotEntity
/*    */   extends BuildingSlot
/*    */ {
/*    */   public SchematicEntity schematic;
/*    */   public int sequenceNumber;
/*    */   
/*    */   public boolean writeToWorld(IBuilderContext context) {
/* 38 */     this.schematic.writeToWorld(context);
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Position getDestination() {
/* 44 */     NBTTagList nbttaglist = this.schematic.entityNBT.func_150295_c("Pos", 6);
/*    */     
/* 46 */     Position pos = new Position(nbttaglist.func_150309_d(0), nbttaglist.func_150309_d(1), nbttaglist.func_150309_d(2));
/*    */     
/* 48 */     return pos;
/*    */   }
/*    */ 
/*    */   
/*    */   public LinkedList<ItemStack> getRequirements(IBuilderContext context) {
/* 53 */     LinkedList<ItemStack> results = new LinkedList<ItemStack>();
/*    */     
/* 55 */     Collections.addAll(results, this.schematic.storedRequirements);
/*    */     
/* 57 */     return results;
/*    */   }
/*    */ 
/*    */   
/*    */   public SchematicEntity getSchematic() {
/* 62 */     return this.schematic;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context) {
/* 67 */     return this.schematic.isAlreadyBuilt(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 72 */     NBTTagCompound schematicNBT = new NBTTagCompound();
/* 73 */     SchematicFactory.getFactory(this.schematic.getClass())
/* 74 */       .saveSchematicToWorldNBT(schematicNBT, (Schematic)this.schematic, registry);
/* 75 */     nbt.func_74782_a("schematic", (NBTBase)schematicNBT);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound nbt, MappingRegistry registry) throws MappingNotFoundException {
/* 80 */     this
/* 81 */       .schematic = (SchematicEntity)SchematicFactory.createSchematicFromWorldNBT(nbt.func_74775_l("schematic"), registry);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyRequirement() {
/* 86 */     return this.schematic.getEnergyRequirement(this.stackConsumed);
/*    */   }
/*    */ 
/*    */   
/*    */   public int buildTime() {
/* 91 */     return this.schematic.buildTime();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\BuildingSlotEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
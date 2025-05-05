/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicEntity;
/*    */ import buildcraft.api.blueprints.Translation;
/*    */ import buildcraft.api.core.Position;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
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
/*    */ public class SchematicMinecart
/*    */   extends SchematicEntity
/*    */ {
/*    */   private Item baseItem;
/*    */   
/*    */   public SchematicMinecart(Item baseItem) {
/* 27 */     this.baseItem = baseItem;
/*    */   }
/*    */ 
/*    */   
/*    */   public void translateToBlueprint(Translation transform) {
/* 32 */     super.translateToBlueprint(transform);
/*    */     
/* 34 */     NBTTagList nbttaglist = this.entityNBT.func_150295_c("Pos", 6);
/*    */     
/* 36 */     Position pos = new Position(nbttaglist.func_150309_d(0), nbttaglist.func_150309_d(1), nbttaglist.func_150309_d(2));
/* 37 */     pos.x -= 0.5D;
/* 38 */     pos.z -= 0.5D;
/* 39 */     this.entityNBT.func_74782_a("Pos", (NBTBase)newDoubleNBTList(new double[] { pos.x, pos.y, pos.z }));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void translateToWorld(Translation transform) {
/* 45 */     super.translateToWorld(transform);
/*    */     
/* 47 */     NBTTagList nbttaglist = this.entityNBT.func_150295_c("Pos", 6);
/*    */     
/* 49 */     Position pos = new Position(nbttaglist.func_150309_d(0), nbttaglist.func_150309_d(1), nbttaglist.func_150309_d(2));
/* 50 */     pos.x += 0.5D;
/* 51 */     pos.z += 0.5D;
/* 52 */     this.entityNBT.func_74782_a("Pos", (NBTBase)newDoubleNBTList(new double[] { pos.x, pos.y, pos.z }));
/*    */   }
/*    */ 
/*    */   
/*    */   public void readFromWorld(IBuilderContext context, Entity entity) {
/* 57 */     super.readFromWorld(context, entity);
/*    */     
/* 59 */     this.storedRequirements = new ItemStack[1];
/* 60 */     this.storedRequirements[0] = new ItemStack(this.baseItem);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context) {
/* 65 */     NBTTagList nbttaglist = this.entityNBT.func_150295_c("Pos", 6);
/*    */     
/* 67 */     Position newPosition = new Position(nbttaglist.func_150309_d(0), nbttaglist.func_150309_d(1), nbttaglist.func_150309_d(2));
/*    */     
/* 69 */     for (Object o : (context.world()).field_72996_f) {
/* 70 */       Entity e = (Entity)o;
/*    */       
/* 72 */       Position existingPositon = new Position(e.field_70165_t, e.field_70163_u, e.field_70161_v);
/*    */       
/* 74 */       if (e instanceof net.minecraft.entity.item.EntityMinecart && 
/* 75 */         existingPositon.isClose(newPosition, 0.1F)) {
/* 76 */         return true;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 81 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicMinecart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
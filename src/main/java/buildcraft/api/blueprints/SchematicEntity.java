/*     */ package buildcraft.api.blueprints;
/*     */ 
/*     */ import buildcraft.api.core.Position;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagDouble;
/*     */ import net.minecraft.nbt.NBTTagFloat;
/*     */ import net.minecraft.nbt.NBTTagList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SchematicEntity
/*     */   extends Schematic
/*     */ {
/*     */   public Class<? extends Entity> entity;
/*  36 */   public NBTTagCompound entityNBT = new NBTTagCompound();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public ItemStack[] storedRequirements = new ItemStack[0];
/*  44 */   public BuildingPermission defaultPermission = BuildingPermission.ALL;
/*     */ 
/*     */   
/*     */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/*  48 */     Collections.addAll(requirements, this.storedRequirements);
/*     */   }
/*     */   
/*     */   public void writeToWorld(IBuilderContext context) {
/*  52 */     Entity e = EntityList.func_75615_a(this.entityNBT, context.world());
/*  53 */     context.world().func_72838_d(e);
/*     */   }
/*     */   
/*     */   public void readFromWorld(IBuilderContext context, Entity entity) {
/*  57 */     entity.func_70039_c(this.entityNBT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateToBlueprint(Translation transform) {
/*  62 */     NBTTagList nbttaglist = this.entityNBT.func_150295_c("Pos", 6);
/*     */     
/*  64 */     Position pos = new Position(nbttaglist.func_150309_d(0), nbttaglist.func_150309_d(1), nbttaglist.func_150309_d(2));
/*  65 */     pos = transform.translate(pos);
/*     */     
/*  67 */     this.entityNBT.func_74782_a("Pos", (NBTBase)
/*  68 */         newDoubleNBTList(new double[] { pos.x, pos.y, pos.z }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateToWorld(Translation transform) {
/*  73 */     NBTTagList nbttaglist = this.entityNBT.func_150295_c("Pos", 6);
/*     */     
/*  75 */     Position pos = new Position(nbttaglist.func_150309_d(0), nbttaglist.func_150309_d(1), nbttaglist.func_150309_d(2));
/*  76 */     pos = transform.translate(pos);
/*     */     
/*  78 */     this.entityNBT.func_74782_a("Pos", (NBTBase)
/*  79 */         newDoubleNBTList(new double[] { pos.x, pos.y, pos.z }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void idsToBlueprint(MappingRegistry registry) {
/*  84 */     registry.scanAndTranslateStacksToRegistry(this.entityNBT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void idsToWorld(MappingRegistry registry) {
/*     */     try {
/*  90 */       registry.scanAndTranslateStacksToWorld(this.entityNBT);
/*  91 */     } catch (MappingNotFoundException e) {
/*  92 */       this.entityNBT = new NBTTagCompound();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotateLeft(IBuilderContext context) {
/*  98 */     NBTTagList nbttaglist = this.entityNBT.func_150295_c("Pos", 6);
/*     */     
/* 100 */     Position pos = new Position(nbttaglist.func_150309_d(0), nbttaglist.func_150309_d(1), nbttaglist.func_150309_d(2));
/* 101 */     pos = context.rotatePositionLeft(pos);
/* 102 */     this.entityNBT.func_74782_a("Pos", (NBTBase)
/* 103 */         newDoubleNBTList(new double[] { pos.x, pos.y, pos.z }));
/*     */     
/* 105 */     nbttaglist = this.entityNBT.func_150295_c("Rotation", 5);
/* 106 */     float yaw = nbttaglist.func_150308_e(0);
/* 107 */     yaw += 90.0F;
/* 108 */     this.entityNBT.func_74782_a("Rotation", (NBTBase)
/*     */         
/* 110 */         newFloatNBTList(new float[] {
/* 111 */             yaw, nbttaglist.func_150308_e(1)
/*     */           }));
/*     */   }
/*     */   
/*     */   public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 116 */     super.writeSchematicToNBT(nbt, registry);
/*     */     
/* 118 */     nbt.func_74768_a("entityId", registry.getIdForEntity(this.entity));
/* 119 */     nbt.func_74782_a("entity", (NBTBase)this.entityNBT);
/*     */     
/* 121 */     NBTTagList rq = new NBTTagList();
/*     */     
/* 123 */     for (ItemStack stack : this.storedRequirements) {
/* 124 */       NBTTagCompound sub = new NBTTagCompound();
/* 125 */       stack.func_77955_b(stack.func_77955_b(sub));
/* 126 */       sub.func_74768_a("id", registry.getIdForItem(stack.func_77973_b()));
/* 127 */       rq.func_74742_a((NBTBase)sub);
/*     */     } 
/*     */     
/* 130 */     nbt.func_74782_a("rq", (NBTBase)rq);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 135 */     super.readSchematicFromNBT(nbt, registry);
/*     */     
/* 137 */     this.entityNBT = nbt.func_74775_l("entity");
/*     */     
/* 139 */     NBTTagList rq = nbt.func_150295_c("rq", 10);
/*     */ 
/*     */     
/* 142 */     ArrayList<ItemStack> rqs = new ArrayList<ItemStack>();
/*     */     
/* 144 */     for (int i = 0; i < rq.func_74745_c(); i++) {
/*     */       try {
/* 146 */         NBTTagCompound sub = rq.func_150305_b(i);
/*     */         
/* 148 */         if (sub.func_74762_e("id") >= 0) {
/*     */           
/* 150 */           sub.func_74768_a("id", Item.field_150901_e
/* 151 */               .func_148757_b(registry.getItemForId(sub
/* 152 */                   .func_74762_e("id"))));
/*     */           
/* 154 */           rqs.add(ItemStack.func_77949_a(sub));
/*     */         } else {
/* 156 */           this.defaultPermission = BuildingPermission.CREATIVE_ONLY;
/*     */         } 
/* 158 */       } catch (Throwable t) {
/* 159 */         t.printStackTrace();
/* 160 */         this.defaultPermission = BuildingPermission.CREATIVE_ONLY;
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     this.storedRequirements = rqs.<ItemStack>toArray(new ItemStack[rqs.size()]);
/*     */   }
/*     */   
/*     */   protected NBTTagList newDoubleNBTList(double... par1ArrayOfDouble) {
/* 168 */     NBTTagList nbttaglist = new NBTTagList();
/* 169 */     double[] adouble = par1ArrayOfDouble;
/* 170 */     int i = par1ArrayOfDouble.length;
/*     */     
/* 172 */     for (int j = 0; j < i; j++) {
/* 173 */       double d1 = adouble[j];
/* 174 */       nbttaglist.func_74742_a((NBTBase)new NBTTagDouble(d1));
/*     */     } 
/*     */     
/* 177 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   protected NBTTagList newFloatNBTList(float... par1ArrayOfFloat) {
/* 181 */     NBTTagList nbttaglist = new NBTTagList();
/* 182 */     float[] afloat = par1ArrayOfFloat;
/* 183 */     int i = par1ArrayOfFloat.length;
/*     */     
/* 185 */     for (int j = 0; j < i; j++) {
/* 186 */       float f1 = afloat[j];
/* 187 */       nbttaglist.func_74742_a((NBTBase)new NBTTagFloat(f1));
/*     */     } 
/*     */     
/* 190 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   public boolean isAlreadyBuilt(IBuilderContext context) {
/* 194 */     NBTTagList nbttaglist = this.entityNBT.func_150295_c("Pos", 6);
/*     */     
/* 196 */     Position newPosition = new Position(nbttaglist.func_150309_d(0), nbttaglist.func_150309_d(1), nbttaglist.func_150309_d(2));
/*     */     
/* 198 */     for (Object o : (context.world()).field_72996_f) {
/* 199 */       Entity e = (Entity)o;
/*     */       
/* 201 */       Position existingPositon = new Position(e.field_70165_t, e.field_70163_u, e.field_70161_v);
/*     */       
/* 203 */       if (existingPositon.isClose(newPosition, 0.1F)) {
/* 204 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 208 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int buildTime() {
/* 213 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public BuildingPermission getBuildingPermission() {
/* 218 */     return this.defaultPermission;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\blueprints\SchematicEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
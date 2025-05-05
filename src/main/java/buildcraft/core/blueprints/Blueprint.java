/*     */ package buildcraft.core.blueprints;
/*     */ 
/*     */ import buildcraft.api.blueprints.BuildingPermission;
/*     */ import buildcraft.api.blueprints.IBuilderContext;
/*     */ import buildcraft.api.blueprints.MappingNotFoundException;
/*     */ import buildcraft.api.blueprints.SchematicBlock;
/*     */ import buildcraft.api.blueprints.SchematicBlockBase;
/*     */ import buildcraft.api.blueprints.SchematicEntity;
/*     */ import buildcraft.api.blueprints.Translation;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ public class Blueprint
/*     */   extends BlueprintBase
/*     */ {
/*  34 */   public LinkedList<SchematicEntity> entities = new LinkedList<SchematicEntity>();
/*     */ 
/*     */ 
/*     */   
/*     */   public Blueprint() {
/*  39 */     this.id.extension = "bpt";
/*     */   }
/*     */   
/*     */   public Blueprint(int sizeX, int sizeY, int sizeZ) {
/*  43 */     super(sizeX, sizeY, sizeZ);
/*     */     
/*  45 */     this.id.extension = "bpt";
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotateLeft(BptContext context) {
/*  50 */     for (SchematicEntity e : this.entities) {
/*  51 */       e.rotateLeft(context);
/*     */     }
/*     */     
/*  54 */     super.rotateLeft(context);
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateToBlueprint(Translation transform) {
/*  59 */     super.translateToBlueprint(transform);
/*     */     
/*  61 */     for (SchematicEntity e : this.entities) {
/*  62 */       e.translateToBlueprint(transform);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateToWorld(Translation transform) {
/*  68 */     super.translateToWorld(transform);
/*     */     
/*  70 */     for (SchematicEntity e : this.entities) {
/*  71 */       e.translateToWorld(transform);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromWorld(IBuilderContext context, TileEntity anchorTile, int x, int y, int z) {
/*  77 */     BptContext bptContext = (BptContext)context;
/*  78 */     Block block = anchorTile.func_145831_w().func_147439_a(x, y, z);
/*  79 */     int meta = anchorTile.func_145831_w().func_72805_g(x, y, z);
/*     */     
/*  81 */     if (context.world().func_147437_c(x, y, z)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     SchematicBlock slot = SchematicRegistry.INSTANCE.createSchematicBlock(block, meta);
/*     */     
/*  90 */     if (slot == null) {
/*     */       return;
/*     */     }
/*     */     
/*  94 */     int posX = (int)(x - (context.surroundingBox().pMin()).x);
/*  95 */     int posY = (int)(y - (context.surroundingBox().pMin()).y);
/*  96 */     int posZ = (int)(z - (context.surroundingBox().pMin()).z);
/*     */     
/*  98 */     slot.block = block;
/*  99 */     slot.meta = meta;
/*     */     
/* 101 */     if (!SchematicRegistry.INSTANCE.isSupported(block, meta)) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 106 */       slot.initializeFromObjectAt(context, x, y, z);
/* 107 */       slot.storeRequirements(context, x, y, z);
/* 108 */       put(posX, posY, posZ, (SchematicBlockBase)slot);
/* 109 */     } catch (Throwable t) {
/*     */       
/* 111 */       t.printStackTrace();
/* 112 */       BCLog.logger.throwing(t);
/*     */     } 
/*     */     
/* 115 */     switch (slot.getBuildingPermission()) {
/*     */ 
/*     */       
/*     */       case CREATIVE_ONLY:
/* 119 */         if (bptContext.readConfiguration.allowCreative) {
/* 120 */           if (this.buildingPermission == BuildingPermission.ALL)
/* 121 */             this.buildingPermission = BuildingPermission.CREATIVE_ONLY; 
/*     */           break;
/*     */         } 
/* 124 */         put(posX, posY, posZ, null);
/*     */         break;
/*     */       
/*     */       case NONE:
/* 128 */         this.buildingPermission = BuildingPermission.NONE;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readEntitiesFromWorld(IBuilderContext context, TileEntity anchorTile) {
/* 135 */     BptContext bptContext = (BptContext)context;
/* 136 */     Translation transform = new Translation();
/*     */     
/* 138 */     transform.x = -(context.surroundingBox().pMin()).x;
/* 139 */     transform.y = -(context.surroundingBox().pMin()).y;
/* 140 */     transform.z = -(context.surroundingBox().pMin()).z;
/*     */     
/* 142 */     for (Object o : (context.world()).field_72996_f) {
/* 143 */       Entity e = (Entity)o;
/*     */       
/* 145 */       if (context.surroundingBox().contains(e.field_70165_t, e.field_70163_u, e.field_70161_v)) {
/* 146 */         SchematicEntity s = SchematicRegistry.INSTANCE.createSchematicEntity((Class)e.getClass());
/*     */         
/* 148 */         if (s != null) {
/* 149 */           s.readFromWorld(context, e);
/* 150 */           switch (s.getBuildingPermission()) {
/*     */             case ALL:
/* 152 */               this.entities.add(s);
/*     */             
/*     */             case CREATIVE_ONLY:
/* 155 */               if (bptContext.readConfiguration.allowCreative) {
/* 156 */                 if (this.buildingPermission == BuildingPermission.ALL) {
/* 157 */                   this.buildingPermission = BuildingPermission.CREATIVE_ONLY;
/*     */                 }
/* 159 */                 this.entities.add(s);
/*     */               } 
/*     */             
/*     */             case NONE:
/* 163 */               this.buildingPermission = BuildingPermission.NONE;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveContents(NBTTagCompound nbt) {
/* 174 */     NBTTagList nbtContents = new NBTTagList();
/*     */     
/* 176 */     for (int x = 0; x < this.sizeX; x++) {
/* 177 */       for (int y = 0; y < this.sizeY; y++) {
/* 178 */         for (int z = 0; z < this.sizeZ; z++) {
/* 179 */           SchematicBlockBase schematic = get(x, y, z);
/* 180 */           NBTTagCompound cpt = new NBTTagCompound();
/*     */           
/* 182 */           if (schematic != null) {
/* 183 */             schematic.idsToBlueprint(this.mapping);
/* 184 */             schematic.writeSchematicToNBT(cpt, this.mapping);
/*     */           } 
/*     */           
/* 187 */           nbtContents.func_74742_a((NBTBase)cpt);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     nbt.func_74782_a("contents", (NBTBase)nbtContents);
/*     */     
/* 194 */     NBTTagList entitiesNBT = new NBTTagList();
/*     */     
/* 196 */     for (SchematicEntity s : this.entities) {
/* 197 */       NBTTagCompound subNBT = new NBTTagCompound();
/* 198 */       s.idsToBlueprint(this.mapping);
/* 199 */       s.writeSchematicToNBT(subNBT, this.mapping);
/* 200 */       entitiesNBT.func_74742_a((NBTBase)subNBT);
/*     */     } 
/*     */     
/* 203 */     nbt.func_74782_a("entities", (NBTBase)entitiesNBT);
/*     */     
/* 205 */     NBTTagCompound contextNBT = new NBTTagCompound();
/* 206 */     this.mapping.write(contextNBT);
/* 207 */     nbt.func_74782_a("idMapping", (NBTBase)contextNBT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadContents(NBTTagCompound nbt) throws BptError {
/* 212 */     this.mapping.read(nbt.func_74775_l("idMapping"));
/*     */     
/* 214 */     NBTTagList nbtContents = nbt.func_150295_c("contents", 10);
/*     */ 
/*     */     
/* 217 */     int index = 0;
/*     */     
/* 219 */     for (int x = 0; x < this.sizeX; x++) {
/* 220 */       for (int y = 0; y < this.sizeY; y++) {
/* 221 */         for (int z = 0; z < this.sizeZ; z++) {
/* 222 */           NBTTagCompound cpt = nbtContents.func_150305_b(index);
/* 223 */           index++;
/*     */           
/* 225 */           if (cpt.func_74764_b("blockId")) {
/*     */             Block block;
/*     */             
/*     */             try {
/* 229 */               block = this.mapping.getBlockForId(cpt.func_74762_e("blockId"));
/* 230 */             } catch (MappingNotFoundException e) {
/* 231 */               block = null;
/* 232 */               this.isComplete = false;
/*     */             } 
/*     */             
/* 235 */             if (block != null) {
/* 236 */               int meta = cpt.func_74762_e("blockMeta");
/* 237 */               SchematicBlock schematicBlock = SchematicRegistry.INSTANCE.createSchematicBlock(block, meta);
/* 238 */               if (schematicBlock != null) {
/* 239 */                 schematicBlock.readSchematicFromNBT(cpt, this.mapping);
/*     */                 
/* 241 */                 if (!schematicBlock.doNotUse()) {
/* 242 */                   schematicBlock.idsToWorld(this.mapping);
/*     */                   
/* 244 */                   switch (schematicBlock.getBuildingPermission()) {
/*     */ 
/*     */                     
/*     */                     case CREATIVE_ONLY:
/* 248 */                       if (this.buildingPermission == BuildingPermission.ALL) {
/* 249 */                         this.buildingPermission = BuildingPermission.CREATIVE_ONLY;
/*     */                       }
/*     */                       break;
/*     */                     case NONE:
/* 253 */                       this.buildingPermission = BuildingPermission.NONE;
/*     */                       break;
/*     */                   } 
/*     */                 } else {
/* 257 */                   schematicBlock = null;
/* 258 */                   this.isComplete = false;
/*     */                 } 
/*     */               } 
/* 261 */               put(x, y, z, (SchematicBlockBase)schematicBlock);
/*     */             } else {
/* 263 */               put(x, y, z, null);
/* 264 */               this.isComplete = false;
/*     */             } 
/*     */           } else {
/* 267 */             put(x, y, z, null);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 273 */     NBTTagList entitiesNBT = nbt.func_150295_c("entities", 10);
/*     */ 
/*     */     
/* 276 */     for (int i = 0; i < entitiesNBT.func_74745_c(); i++) {
/* 277 */       NBTTagCompound cpt = entitiesNBT.func_150305_b(i);
/*     */       
/* 279 */       if (cpt.func_74764_b("entityId")) {
/*     */         Class<? extends Entity> entity;
/*     */         
/*     */         try {
/* 283 */           entity = this.mapping.getEntityForId(cpt.func_74762_e("entityId"));
/* 284 */         } catch (MappingNotFoundException e) {
/* 285 */           entity = null;
/* 286 */           this.isComplete = false;
/*     */         } 
/*     */         
/* 289 */         if (entity != null) {
/* 290 */           SchematicEntity s = SchematicRegistry.INSTANCE.createSchematicEntity(entity);
/* 291 */           s.readSchematicFromNBT(cpt, this.mapping);
/* 292 */           s.idsToWorld(this.mapping);
/* 293 */           this.entities.add(s);
/*     */         } else {
/* 295 */           this.isComplete = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getStack() {
/* 303 */     Item item = (Item)Item.field_150901_e.func_82594_a("BuildCraft|Builders:blueprintItem");
/* 304 */     ItemStack stack = new ItemStack(item, 1);
/* 305 */     NBTTagCompound nbt = NBTUtils.getItemData(stack);
/* 306 */     this.id.write(nbt);
/* 307 */     nbt.func_74778_a("author", this.author);
/* 308 */     nbt.func_74778_a("name", this.id.name);
/* 309 */     nbt.func_74774_a("permission", (byte)this.buildingPermission.ordinal());
/* 310 */     nbt.func_74757_a("isComplete", this.isComplete);
/*     */     
/* 312 */     return stack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\Blueprint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
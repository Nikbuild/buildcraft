/*     */ package buildcraft.api.blueprints;
/*     */ 
/*     */ import buildcraft.api.core.BCLog;
/*     */ import com.google.common.collect.ArrayListMultimap;
/*     */ import com.google.common.collect.ListMultimap;
/*     */ import cpw.mods.fml.common.FMLModContainer;
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import cpw.mods.fml.common.ModContainer;
/*     */ import cpw.mods.fml.common.event.FMLEvent;
/*     */ import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import org.apache.logging.log4j.Level;
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
/*     */ public class MappingRegistry
/*     */ {
/*  36 */   public HashMap<Block, Integer> blockToId = new HashMap<Block, Integer>();
/*  37 */   public ArrayList<Block> idToBlock = new ArrayList<Block>();
/*     */   
/*  39 */   public HashMap<Item, Integer> itemToId = new HashMap<Item, Integer>();
/*  40 */   public ArrayList<Item> idToItem = new ArrayList<Item>();
/*     */   
/*  42 */   public HashMap<Class<? extends Entity>, Integer> entityToId = new HashMap<Class<? extends Entity>, Integer>();
/*  43 */   public ArrayList<Class<? extends Entity>> idToEntity = new ArrayList<Class<? extends Entity>>();
/*     */   
/*     */   private void registerItem(Item item) {
/*  46 */     if (!this.itemToId.containsKey(item)) {
/*  47 */       this.idToItem.add(item);
/*  48 */       this.itemToId.put(item, Integer.valueOf(this.idToItem.size() - 1));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerBlock(Block block) {
/*  53 */     if (!this.blockToId.containsKey(block)) {
/*  54 */       this.idToBlock.add(block);
/*  55 */       this.blockToId.put(block, Integer.valueOf(this.idToBlock.size() - 1));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerEntity(Class<? extends Entity> entityClass) {
/*  60 */     if (!this.entityToId.containsKey(entityClass)) {
/*  61 */       this.idToEntity.add(entityClass);
/*  62 */       this.entityToId.put(entityClass, Integer.valueOf(this.idToEntity.size() - 1));
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item getItemForId(int id) throws MappingNotFoundException {
/*  67 */     if (id >= this.idToItem.size()) {
/*  68 */       throw new MappingNotFoundException("no item mapping at position " + id);
/*     */     }
/*     */     
/*  71 */     Item result = this.idToItem.get(id);
/*     */     
/*  73 */     if (result == null) {
/*  74 */       throw new MappingNotFoundException("no item mapping at position " + id);
/*     */     }
/*  76 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIdForItem(Item item) {
/*  81 */     if (!this.itemToId.containsKey(item)) {
/*  82 */       registerItem(item);
/*     */     }
/*     */     
/*  85 */     return ((Integer)this.itemToId.get(item)).intValue();
/*     */   }
/*     */   
/*     */   public int itemIdToRegistry(int id) {
/*  89 */     Item item = Item.func_150899_d(id);
/*     */     
/*  91 */     return getIdForItem(item);
/*     */   }
/*     */   
/*     */   public int itemIdToWorld(int id) throws MappingNotFoundException {
/*  95 */     Item item = getItemForId(id);
/*     */     
/*  97 */     return Item.func_150891_b(item);
/*     */   }
/*     */   
/*     */   public Block getBlockForId(int id) throws MappingNotFoundException {
/* 101 */     if (id >= this.idToBlock.size()) {
/* 102 */       throw new MappingNotFoundException("no block mapping at position " + id);
/*     */     }
/*     */     
/* 105 */     Block result = this.idToBlock.get(id);
/*     */     
/* 107 */     if (result == null) {
/* 108 */       throw new MappingNotFoundException("no block mapping at position " + id);
/*     */     }
/* 110 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIdForBlock(Block block) {
/* 115 */     if (!this.blockToId.containsKey(block)) {
/* 116 */       registerBlock(block);
/*     */     }
/*     */     
/* 119 */     return ((Integer)this.blockToId.get(block)).intValue();
/*     */   }
/*     */   
/*     */   public int blockIdToRegistry(int id) {
/* 123 */     Block block = Block.func_149729_e(id);
/*     */     
/* 125 */     return getIdForBlock(block);
/*     */   }
/*     */   
/*     */   public int blockIdToWorld(int id) throws MappingNotFoundException {
/* 129 */     Block block = getBlockForId(id);
/*     */     
/* 131 */     return Block.func_149682_b(block);
/*     */   }
/*     */   
/*     */   public Class<? extends Entity> getEntityForId(int id) throws MappingNotFoundException {
/* 135 */     if (id >= this.idToEntity.size()) {
/* 136 */       throw new MappingNotFoundException("no entity mapping at position " + id);
/*     */     }
/*     */     
/* 139 */     Class<? extends Entity> result = this.idToEntity.get(id);
/*     */     
/* 141 */     if (result == null) {
/* 142 */       throw new MappingNotFoundException("no entity mapping at position " + id);
/*     */     }
/* 144 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIdForEntity(Class<? extends Entity> entity) {
/* 149 */     if (!this.entityToId.containsKey(entity)) {
/* 150 */       registerEntity(entity);
/*     */     }
/*     */     
/* 153 */     return ((Integer)this.entityToId.get(entity)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stackToRegistry(NBTTagCompound nbt) {
/* 161 */     Item item = Item.func_150899_d(nbt.func_74765_d("id"));
/* 162 */     nbt.func_74777_a("id", (short)getIdForItem(item));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stackToWorld(NBTTagCompound nbt) throws MappingNotFoundException {
/* 170 */     Item item = getItemForId(nbt.func_74765_d("id"));
/* 171 */     nbt.func_74777_a("id", (short)Item.func_150891_b(item));
/*     */   }
/*     */   
/*     */   private boolean isStackLayout(NBTTagCompound nbt) {
/* 175 */     return (nbt.func_74764_b("id") && nbt
/* 176 */       .func_74764_b("Count") && nbt
/* 177 */       .func_74764_b("Damage") && nbt
/* 178 */       .func_74781_a("id") instanceof net.minecraft.nbt.NBTTagShort && nbt
/* 179 */       .func_74781_a("Count") instanceof net.minecraft.nbt.NBTTagByte && nbt
/* 180 */       .func_74781_a("Damage") instanceof net.minecraft.nbt.NBTTagShort);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void scanAndTranslateStacksToRegistry(NBTTagCompound nbt) {
/* 186 */     if (isStackLayout(nbt)) {
/* 187 */       stackToRegistry(nbt);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 192 */     for (Object keyO : nbt.func_150296_c()) {
/* 193 */       String key = (String)keyO;
/*     */       
/* 195 */       if (nbt.func_74781_a(key) instanceof NBTTagCompound) {
/* 196 */         scanAndTranslateStacksToRegistry(nbt.func_74775_l(key));
/*     */       }
/*     */       
/* 199 */       if (nbt.func_74781_a(key) instanceof NBTTagList) {
/* 200 */         NBTTagList list = (NBTTagList)nbt.func_74781_a(key);
/*     */         
/* 202 */         if (list.func_150303_d() == 10) {
/* 203 */           for (int i = 0; i < list.func_74745_c(); i++) {
/* 204 */             scanAndTranslateStacksToRegistry(list.func_150305_b(i));
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void scanAndTranslateStacksToWorld(NBTTagCompound nbt) throws MappingNotFoundException {
/* 214 */     if (isStackLayout(nbt)) {
/* 215 */       stackToWorld(nbt);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 220 */     for (Object keyO : new HashSet(nbt.func_150296_c())) {
/* 221 */       String key = (String)keyO;
/*     */       
/* 223 */       if (nbt.func_74781_a(key) instanceof NBTTagCompound) {
/*     */         try {
/* 225 */           scanAndTranslateStacksToWorld(nbt.func_74775_l(key));
/* 226 */         } catch (MappingNotFoundException e) {
/* 227 */           nbt.func_82580_o(key);
/*     */         } 
/*     */       }
/*     */       
/* 231 */       if (nbt.func_74781_a(key) instanceof NBTTagList) {
/* 232 */         NBTTagList list = (NBTTagList)nbt.func_74781_a(key);
/*     */         
/* 234 */         if (list.func_150303_d() == 10) {
/* 235 */           for (int i = list.func_74745_c() - 1; i >= 0; i--) {
/*     */             try {
/* 237 */               scanAndTranslateStacksToWorld(list.func_150305_b(i));
/* 238 */             } catch (MappingNotFoundException e) {
/* 239 */               list.func_74744_a(i);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write(NBTTagCompound nbt) {
/* 248 */     NBTTagList blocksMapping = new NBTTagList();
/*     */     
/* 250 */     for (Block b : this.idToBlock) {
/* 251 */       NBTTagCompound sub = new NBTTagCompound();
/* 252 */       if (b != null) {
/* 253 */         String name = Block.field_149771_c.func_148750_c(b);
/* 254 */         if (name == null || name.length() == 0) {
/* 255 */           BCLog.logger.error("Block " + b.func_149739_a() + " (" + b.getClass().getName() + ") has an empty registry name! This is a bug!");
/*     */         } else {
/* 257 */           sub.func_74778_a("name", name);
/*     */         } 
/*     */       } 
/* 260 */       blocksMapping.func_74742_a((NBTBase)sub);
/*     */     } 
/*     */     
/* 263 */     nbt.func_74782_a("blocksMapping", (NBTBase)blocksMapping);
/*     */     
/* 265 */     NBTTagList itemsMapping = new NBTTagList();
/*     */     
/* 267 */     for (Item i : this.idToItem) {
/* 268 */       NBTTagCompound sub = new NBTTagCompound();
/* 269 */       if (i != null) {
/* 270 */         String name = Item.field_150901_e.func_148750_c(i);
/* 271 */         if (name == null || name.length() == 0) {
/* 272 */           BCLog.logger.error("Item " + i.func_77658_a() + " (" + i.getClass().getName() + ") has an empty registry name! This is a bug!");
/*     */         } else {
/* 274 */           sub.func_74778_a("name", name);
/*     */         } 
/*     */       } 
/* 277 */       itemsMapping.func_74742_a((NBTBase)sub);
/*     */     } 
/*     */     
/* 280 */     nbt.func_74782_a("itemsMapping", (NBTBase)itemsMapping);
/*     */     
/* 282 */     NBTTagList entitiesMapping = new NBTTagList();
/*     */     
/* 284 */     for (Class<? extends Entity> e : this.idToEntity) {
/* 285 */       NBTTagCompound sub = new NBTTagCompound();
/* 286 */       sub.func_74778_a("name", e.getCanonicalName());
/* 287 */       entitiesMapping.func_74742_a((NBTBase)sub);
/*     */     } 
/*     */     
/* 290 */     nbt.func_74782_a("entitiesMapping", (NBTBase)entitiesMapping);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object getMissingMappingFromFML(boolean isBlock, String name, int i) {
/* 299 */     String modName = name.split(":")[0];
/* 300 */     if (Loader.isModLoaded(modName)) {
/*     */       try {
/* 302 */         FMLMissingMappingsEvent.MissingMapping mapping = new FMLMissingMappingsEvent.MissingMapping((isBlock ? 1 : 32) + name, i);
/*     */ 
/*     */ 
/*     */         
/* 306 */         ArrayListMultimap arrayListMultimap = ArrayListMultimap.create();
/* 307 */         arrayListMultimap.put(modName, mapping);
/* 308 */         FMLMissingMappingsEvent event = new FMLMissingMappingsEvent((ListMultimap)arrayListMultimap);
/* 309 */         for (ModContainer container : Loader.instance().getModList()) {
/* 310 */           if (container instanceof FMLModContainer) {
/* 311 */             event.applyModContainer(container);
/* 312 */             ((FMLModContainer)container).handleModStateEvent((FMLEvent)event);
/* 313 */             if (mapping.getAction() != FMLMissingMappingsEvent.Action.DEFAULT) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/* 318 */         if (mapping.getAction() == FMLMissingMappingsEvent.Action.REMAP) {
/* 319 */           return mapping.getTarget();
/*     */         }
/* 321 */       } catch (Exception e) {
/* 322 */         e.printStackTrace();
/*     */       } 
/*     */     }
/* 325 */     return null;
/*     */   }
/*     */   
/*     */   public void read(NBTTagCompound nbt) {
/* 329 */     NBTTagList blocksMapping = nbt.func_150295_c("blocksMapping", 10);
/*     */ 
/*     */     
/* 332 */     for (int i = 0; i < blocksMapping.func_74745_c(); i++) {
/* 333 */       NBTTagCompound sub = blocksMapping.func_150305_b(i);
/* 334 */       if (!sub.func_74764_b("name")) {
/*     */         
/* 336 */         this.idToBlock.add(null);
/* 337 */         BCLog.logger.log(Level.WARN, "Can't load a block - corrupt blueprint!");
/*     */       } else {
/*     */         
/* 340 */         String name = sub.func_74779_i("name");
/* 341 */         Block b = null;
/*     */         
/* 343 */         if (!Block.field_149771_c.func_148741_d(name) && name.contains(":")) {
/* 344 */           b = (Block)getMissingMappingFromFML(true, name, i);
/* 345 */           if (b != null) {
/* 346 */             BCLog.logger.info("Remapped " + name + " to " + Block.field_149771_c.func_148750_c(b));
/*     */           }
/*     */         } 
/*     */         
/* 350 */         if (b == null && Block.field_149771_c.func_148741_d(name)) {
/* 351 */           b = (Block)Block.field_149771_c.func_82594_a(name);
/*     */         }
/*     */         
/* 354 */         if (b != null) {
/* 355 */           registerBlock(b);
/*     */         } else {
/*     */           
/* 358 */           this.idToBlock.add(null);
/* 359 */           BCLog.logger.log(Level.WARN, "Can't load block " + name);
/*     */         } 
/*     */       } 
/*     */     } 
/* 363 */     NBTTagList itemsMapping = nbt.func_150295_c("itemsMapping", 10);
/*     */ 
/*     */     
/* 366 */     for (int j = 0; j < itemsMapping.func_74745_c(); j++) {
/* 367 */       NBTTagCompound sub = itemsMapping.func_150305_b(j);
/* 368 */       if (!sub.func_74764_b("name")) {
/*     */         
/* 370 */         this.idToItem.add(null);
/* 371 */         BCLog.logger.log(Level.WARN, "Can't load an item - corrupt blueprint!");
/*     */       }
/*     */       else {
/*     */         
/* 375 */         String name = sub.func_74779_i("name");
/* 376 */         Item item = null;
/*     */         
/* 378 */         if (!Item.field_150901_e.func_148741_d(name) && name.contains(":")) {
/* 379 */           item = (Item)getMissingMappingFromFML(false, name, j);
/* 380 */           if (item != null) {
/* 381 */             BCLog.logger.info("Remapped " + name + " to " + Item.field_150901_e.func_148750_c(item));
/*     */           }
/*     */         } 
/*     */         
/* 385 */         if (item == null && Item.field_150901_e.func_148741_d(name)) {
/* 386 */           item = (Item)Item.field_150901_e.func_82594_a(name);
/*     */         }
/*     */         
/* 389 */         if (item != null) {
/* 390 */           registerItem(item);
/*     */         } else {
/*     */           
/* 393 */           this.idToItem.add(null);
/* 394 */           BCLog.logger.log(Level.WARN, "Can't load item " + name);
/*     */         } 
/*     */       } 
/*     */     } 
/* 398 */     NBTTagList entitiesMapping = nbt.func_150295_c("entitiesMapping", 10);
/*     */ 
/*     */     
/* 401 */     for (int k = 0; k < entitiesMapping.func_74745_c(); k++) {
/* 402 */       NBTTagCompound sub = entitiesMapping.func_150305_b(k);
/* 403 */       String name = sub.func_74779_i("name");
/* 404 */       Class<? extends Entity> e = null;
/*     */       
/*     */       try {
/* 407 */         e = (Class)Class.forName(name);
/* 408 */       } catch (ClassNotFoundException e1) {
/* 409 */         e1.printStackTrace();
/*     */       } 
/*     */       
/* 412 */       if (e != null) {
/* 413 */         registerEntity(e);
/*     */       } else {
/*     */         
/* 416 */         this.idToEntity.add(null);
/* 417 */         BCLog.logger.log(Level.WARN, "Can't load entity " + name);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\blueprints\MappingRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
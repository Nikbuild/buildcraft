/*     */ package buildcraft.core.blueprints;
/*     */ 
/*     */ import buildcraft.api.blueprints.BuildingPermission;
/*     */ import buildcraft.api.blueprints.IBuilderContext;
/*     */ import buildcraft.api.blueprints.MappingRegistry;
/*     */ import buildcraft.api.blueprints.SchematicBlockBase;
/*     */ import buildcraft.api.blueprints.Translation;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.core.Box;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
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
/*     */ public abstract class BlueprintBase
/*     */ {
/*  35 */   public ArrayList<NBTTagCompound> subBlueprintsNBT = new ArrayList<NBTTagCompound>();
/*     */   public int anchorX;
/*     */   public int anchorY;
/*     */   public int anchorZ;
/*  39 */   public LibraryId id = new LibraryId(); public int sizeX; public int sizeY; public int sizeZ;
/*     */   public String author;
/*     */   public boolean rotate = true;
/*     */   public boolean excavate = true;
/*  43 */   public BuildingPermission buildingPermission = BuildingPermission.ALL;
/*     */   
/*     */   public boolean isComplete = true;
/*  46 */   protected MappingRegistry mapping = new MappingRegistry();
/*     */   
/*     */   protected SchematicBlockBase[] contents;
/*     */   private NBTTagCompound nbt;
/*  50 */   private ForgeDirection mainDir = ForgeDirection.EAST;
/*     */ 
/*     */   
/*     */   public BlueprintBase() {}
/*     */   
/*     */   public BlueprintBase(int sizeX, int sizeY, int sizeZ) {
/*  56 */     this.contents = new SchematicBlockBase[sizeX * sizeY * sizeZ];
/*     */     
/*  58 */     this.sizeX = sizeX;
/*  59 */     this.sizeY = sizeY;
/*  60 */     this.sizeZ = sizeZ;
/*     */     
/*  62 */     this.anchorX = 0;
/*  63 */     this.anchorY = 0;
/*  64 */     this.anchorZ = 0;
/*     */   }
/*     */   
/*     */   private int toArrayPos(int x, int y, int z) {
/*  68 */     return (y * this.sizeZ + z) * this.sizeX + x;
/*     */   }
/*     */   
/*     */   public SchematicBlockBase get(int x, int y, int z) {
/*  72 */     return this.contents[(y * this.sizeZ + z) * this.sizeX + x];
/*     */   }
/*     */   
/*     */   public void put(int x, int y, int z, SchematicBlockBase s) {
/*  76 */     this.contents[(y * this.sizeZ + z) * this.sizeX + x] = s;
/*     */   }
/*     */   
/*     */   public void translateToBlueprint(Translation transform) {
/*  80 */     for (SchematicBlockBase content : this.contents) {
/*  81 */       if (content != null) {
/*  82 */         content.translateToBlueprint(transform);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void translateToWorld(Translation transform) {
/*  88 */     for (SchematicBlockBase content : this.contents) {
/*  89 */       if (content != null) {
/*  90 */         content.translateToWorld(transform);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rotateLeft(BptContext context) {
/*  96 */     SchematicBlockBase[] newContents = new SchematicBlockBase[this.sizeZ * this.sizeY * this.sizeX];
/*     */     
/*  98 */     for (int x = 0; x < this.sizeZ; x++) {
/*  99 */       for (int y = 0; y < this.sizeY; y++) {
/* 100 */         for (int z = 0; z < this.sizeX; z++) {
/* 101 */           int pos = (y * this.sizeX + z) * this.sizeZ + x;
/* 102 */           newContents[pos] = this.contents[toArrayPos(z, y, this.sizeZ - 1 - x)];
/*     */           
/* 104 */           if (newContents[pos] != null) {
/*     */             try {
/* 106 */               newContents[pos].rotateLeft(context);
/* 107 */             } catch (Throwable t) {
/*     */               
/* 109 */               t.printStackTrace();
/* 110 */               BCLog.logger.throwing(t);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 119 */     int newAnchorX = this.sizeZ - 1 - this.anchorZ;
/* 120 */     int newAnchorY = this.anchorY;
/* 121 */     int newAnchorZ = this.anchorX;
/*     */     
/* 123 */     for (NBTTagCompound sub : this.subBlueprintsNBT) {
/* 124 */       ForgeDirection dir = ForgeDirection.values()[sub.func_74771_c("dir")];
/*     */       
/* 126 */       dir = dir.getRotation(ForgeDirection.UP);
/*     */       
/* 128 */       Position pos = new Position(sub.func_74762_e("x"), sub.func_74762_e("y"), sub.func_74762_e("z"));
/* 129 */       Position np = context.rotatePositionLeft(pos);
/*     */       
/* 131 */       sub.func_74768_a("x", (int)np.x);
/* 132 */       sub.func_74768_a("z", (int)np.z);
/* 133 */       sub.func_74774_a("dir", (byte)dir.ordinal());
/*     */     } 
/*     */     
/* 136 */     context.rotateLeft();
/*     */     
/* 138 */     this.anchorX = newAnchorX;
/* 139 */     this.anchorY = newAnchorY;
/* 140 */     this.anchorZ = newAnchorZ;
/*     */     
/* 142 */     this.contents = newContents;
/* 143 */     int tmp = this.sizeX;
/* 144 */     this.sizeX = this.sizeZ;
/* 145 */     this.sizeZ = tmp;
/*     */     
/* 147 */     this.mainDir = this.mainDir.getRotation(ForgeDirection.UP);
/*     */   }
/*     */   
/*     */   private void writeToNBTInternal(NBTTagCompound nbt) {
/* 151 */     nbt.func_74778_a("version", "7.1.26");
/*     */     
/* 153 */     if (this instanceof Template) {
/* 154 */       nbt.func_74778_a("kind", "template");
/*     */     } else {
/* 156 */       nbt.func_74778_a("kind", "blueprint");
/*     */     } 
/*     */     
/* 159 */     nbt.func_74768_a("sizeX", this.sizeX);
/* 160 */     nbt.func_74768_a("sizeY", this.sizeY);
/* 161 */     nbt.func_74768_a("sizeZ", this.sizeZ);
/* 162 */     nbt.func_74768_a("anchorX", this.anchorX);
/* 163 */     nbt.func_74768_a("anchorY", this.anchorY);
/* 164 */     nbt.func_74768_a("anchorZ", this.anchorZ);
/* 165 */     nbt.func_74757_a("rotate", this.rotate);
/* 166 */     nbt.func_74757_a("excavate", this.excavate);
/*     */     
/* 168 */     if (this.author != null) {
/* 169 */       nbt.func_74778_a("author", this.author);
/*     */     }
/*     */     
/* 172 */     saveContents(nbt);
/*     */     
/* 174 */     NBTTagList subBptList = new NBTTagList();
/*     */     
/* 176 */     for (NBTTagCompound subBpt : this.subBlueprintsNBT) {
/* 177 */       subBptList.func_74742_a((NBTBase)subBpt);
/*     */     }
/*     */     
/* 180 */     nbt.func_74782_a("subBpt", (NBTBase)subBptList);
/*     */   }
/*     */   public static BlueprintBase loadBluePrint(NBTTagCompound nbt) {
/*     */     BlueprintBase bpt;
/* 184 */     String kind = nbt.func_74779_i("kind");
/*     */ 
/*     */ 
/*     */     
/* 188 */     if ("template".equals(kind)) {
/* 189 */       bpt = new Template();
/*     */     } else {
/* 191 */       bpt = new Blueprint();
/*     */     } 
/*     */     
/* 194 */     bpt.readFromNBT(nbt);
/*     */     
/* 196 */     return bpt;
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 200 */     this.sizeX = nbt.func_74762_e("sizeX");
/* 201 */     this.sizeY = nbt.func_74762_e("sizeY");
/* 202 */     this.sizeZ = nbt.func_74762_e("sizeZ");
/* 203 */     this.anchorX = nbt.func_74762_e("anchorX");
/* 204 */     this.anchorY = nbt.func_74762_e("anchorY");
/* 205 */     this.anchorZ = nbt.func_74762_e("anchorZ");
/*     */     
/* 207 */     this.author = nbt.func_74779_i("author");
/*     */     
/* 209 */     if (nbt.func_74764_b("rotate")) {
/* 210 */       this.rotate = nbt.func_74767_n("rotate");
/*     */     } else {
/* 212 */       this.rotate = true;
/*     */     } 
/*     */     
/* 215 */     if (nbt.func_74764_b("excavate")) {
/* 216 */       this.excavate = nbt.func_74767_n("excavate");
/*     */     } else {
/* 218 */       this.excavate = true;
/*     */     } 
/*     */     
/* 221 */     this.contents = new SchematicBlockBase[this.sizeX * this.sizeY * this.sizeZ];
/*     */     
/*     */     try {
/* 224 */       loadContents(nbt);
/* 225 */     } catch (BptError e) {
/* 226 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 229 */     if (nbt.func_74764_b("subBpt")) {
/* 230 */       NBTTagList subBptList = nbt.func_150295_c("subBpt", 10);
/*     */       
/* 232 */       for (int i = 0; i < subBptList.func_74745_c(); i++) {
/* 233 */         this.subBlueprintsNBT.add(subBptList.func_150305_b(i));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Box getBoxForPos(int x, int y, int z) {
/* 239 */     int xMin = x - this.anchorX;
/* 240 */     int yMin = y - this.anchorY;
/* 241 */     int zMin = z - this.anchorZ;
/* 242 */     int xMax = x + this.sizeX - this.anchorX - 1;
/* 243 */     int yMax = y + this.sizeY - this.anchorY - 1;
/* 244 */     int zMax = z + this.sizeZ - this.anchorZ - 1;
/*     */     
/* 246 */     Box res = new Box();
/* 247 */     res.initialize(xMin, yMin, zMin, xMax, yMax, zMax);
/* 248 */     res.reorder();
/*     */     
/* 250 */     return res;
/*     */   }
/*     */   
/*     */   public BptContext getContext(World world, Box box) {
/* 254 */     return new BptContext(world, box, this.mapping);
/*     */   }
/*     */   
/*     */   public void addSubBlueprint(BlueprintBase subBpt, int x, int y, int z, ForgeDirection dir) {
/* 258 */     NBTTagCompound subNBT = new NBTTagCompound();
/*     */     
/* 260 */     subNBT.func_74768_a("x", x);
/* 261 */     subNBT.func_74768_a("y", y);
/* 262 */     subNBT.func_74768_a("z", z);
/* 263 */     subNBT.func_74774_a("dir", (byte)dir.ordinal());
/* 264 */     subNBT.func_74782_a("bpt", (NBTBase)subBpt.getNBT());
/*     */     
/* 266 */     this.subBlueprintsNBT.add(subNBT);
/*     */   }
/*     */   
/*     */   public NBTTagCompound getNBT() {
/* 270 */     if (this.nbt == null) {
/* 271 */       this.nbt = new NBTTagCompound();
/* 272 */       writeToNBTInternal(this.nbt);
/*     */     } 
/* 274 */     return this.nbt;
/*     */   }
/*     */   
/*     */   public BlueprintBase adjustToWorld(World world, int x, int y, int z, ForgeDirection o) {
/* 278 */     if (this.buildingPermission == BuildingPermission.NONE || (this.buildingPermission == BuildingPermission.CREATIVE_ONLY && world
/*     */       
/* 280 */       .func_72912_H().func_76077_q() != WorldSettings.GameType.CREATIVE)) {
/* 281 */       return null;
/*     */     }
/*     */     
/* 284 */     BptContext context = getContext(world, getBoxForPos(x, y, z));
/*     */     
/* 286 */     if (this.rotate && 
/* 287 */       o != ForgeDirection.EAST)
/*     */     {
/* 289 */       if (o == ForgeDirection.SOUTH) {
/* 290 */         rotateLeft(context);
/* 291 */       } else if (o == ForgeDirection.WEST) {
/* 292 */         rotateLeft(context);
/* 293 */         rotateLeft(context);
/* 294 */       } else if (o == ForgeDirection.NORTH) {
/* 295 */         rotateLeft(context);
/* 296 */         rotateLeft(context);
/* 297 */         rotateLeft(context);
/*     */       } 
/*     */     }
/*     */     
/* 301 */     Translation transform = new Translation();
/*     */     
/* 303 */     transform.x = (x - this.anchorX);
/* 304 */     transform.y = (y - this.anchorY);
/* 305 */     transform.z = (z - this.anchorZ);
/*     */     
/* 307 */     translateToWorld(transform);
/*     */     
/* 309 */     return this;
/*     */   }
/*     */   
/*     */   public abstract void loadContents(NBTTagCompound paramNBTTagCompound) throws BptError;
/*     */   
/*     */   public abstract void saveContents(NBTTagCompound paramNBTTagCompound);
/*     */   
/*     */   public abstract void readFromWorld(IBuilderContext paramIBuilderContext, TileEntity paramTileEntity, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   public abstract ItemStack getStack();
/*     */   
/*     */   public void readEntitiesFromWorld(IBuilderContext context, TileEntity anchorTile) {}
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\BlueprintBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
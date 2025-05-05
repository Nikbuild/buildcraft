/*     */ package buildcraft.core.builders;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.blueprints.IBuilderContext;
/*     */ import buildcraft.api.blueprints.MappingNotFoundException;
/*     */ import buildcraft.api.blueprints.MappingRegistry;
/*     */ import buildcraft.api.core.ISerializable;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.core.StackAtPosition;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.MathHelper;
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
/*     */ public class BuildingItem
/*     */   implements IBuildingItem, ISerializable
/*     */ {
/*  38 */   public static int ITEMS_SPACE = 2;
/*     */   public Position origin;
/*     */   public Position destination;
/*  41 */   public LinkedList<StackAtPosition> stacksToDisplay = new LinkedList<StackAtPosition>();
/*     */   
/*     */   public boolean isDone = false;
/*     */   
/*     */   public BuildingSlot slotToBuild;
/*     */   
/*     */   public IBuilderContext context;
/*     */   private long previousUpdate;
/*  49 */   private float lifetimeDisplay = 0.0F;
/*  50 */   private float maxLifetime = 0.0F;
/*     */   
/*     */   private boolean initialized = false;
/*     */   private double vx;
/*  54 */   private float lifetime = 0.0F; private double vy; private double vz; private double maxHeight;
/*     */   
/*     */   public void initialize() {
/*  57 */     if (!this.initialized) {
/*  58 */       double dx = this.destination.x - this.origin.x;
/*  59 */       double dy = this.destination.y - this.origin.y;
/*  60 */       double dz = this.destination.z - this.origin.z;
/*     */       
/*  62 */       double size = Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */       
/*  64 */       this.maxLifetime = (float)size * 4.0F;
/*     */ 
/*     */ 
/*     */       
/*  68 */       this.maxHeight = size / 2.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  74 */       Position middle = new Position();
/*  75 */       middle.x = (this.destination.x + this.origin.x) / 2.0D;
/*  76 */       middle.y = (this.destination.y + this.origin.y) / 2.0D;
/*  77 */       middle.z = (this.destination.z + this.origin.z) / 2.0D;
/*     */       
/*  79 */       Position top = new Position();
/*  80 */       top.x = middle.x;
/*  81 */       middle.y += this.maxHeight;
/*  82 */       top.z = middle.z;
/*     */       
/*  84 */       Position originToTop = new Position();
/*  85 */       top.x -= this.origin.x;
/*  86 */       top.y -= this.origin.y;
/*  87 */       top.z -= this.origin.z;
/*     */       
/*  89 */       Position destinationToTop = new Position();
/*  90 */       this.destination.x -= this.origin.x;
/*  91 */       this.destination.y -= this.origin.y;
/*  92 */       this.destination.z -= this.origin.z;
/*     */       
/*  94 */       double d1 = Math.sqrt(originToTop.x * originToTop.x + originToTop.y * originToTop.y + originToTop.z * originToTop.z);
/*     */ 
/*     */       
/*  97 */       double d2 = Math.sqrt(destinationToTop.x * destinationToTop.x + destinationToTop.y * destinationToTop.y + destinationToTop.z * destinationToTop.z);
/*     */ 
/*     */       
/* 100 */       d1 = d1 / size * this.maxLifetime;
/* 101 */       d2 = d2 / size * this.maxLifetime;
/*     */       
/* 103 */       this.maxLifetime = (float)d1 + (float)d2;
/*     */       
/* 105 */       this.vx = dx / this.maxLifetime;
/* 106 */       this.vy = dy / this.maxLifetime;
/* 107 */       this.vz = dz / this.maxLifetime;
/*     */       
/* 109 */       if (this.stacksToDisplay.size() == 0) {
/* 110 */         StackAtPosition sPos = new StackAtPosition();
/* 111 */         sPos.stack = new ItemStack((Block)BuildCraftCore.buildToolBlock);
/* 112 */         this.stacksToDisplay.add(sPos);
/*     */       } 
/*     */       
/* 115 */       this.initialized = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Position getDisplayPosition(float time) {
/* 120 */     Position result = new Position();
/*     */     
/* 122 */     this.origin.x += this.vx * time;
/* 123 */     result.y = this.origin.y + this.vy * time + MathHelper.func_76126_a(time / this.maxLifetime * 3.1415927F) * this.maxHeight;
/* 124 */     this.origin.z += this.vz * time;
/*     */     
/* 126 */     return result;
/*     */   }
/*     */   
/*     */   public void update() {
/* 130 */     if (this.isDone) {
/*     */       return;
/*     */     }
/*     */     
/* 134 */     initialize();
/*     */     
/* 136 */     this.lifetime++;
/*     */     
/* 138 */     if (this.lifetime > this.maxLifetime + (this.stacksToDisplay.size() * ITEMS_SPACE) - 1.0F) {
/* 139 */       this.isDone = true;
/* 140 */       build();
/*     */     } 
/*     */     
/* 143 */     this.lifetimeDisplay = this.lifetime;
/* 144 */     this.previousUpdate = (new Date()).getTime();
/*     */     
/* 146 */     if (this.slotToBuild != null && this.lifetime > this.maxLifetime) {
/* 147 */       this.slotToBuild.writeCompleted(this.context, ((this.lifetime - this.maxLifetime) / (this.stacksToDisplay
/* 148 */           .size() * ITEMS_SPACE)));
/*     */     }
/*     */   }
/*     */   
/*     */   public void displayUpdate() {
/* 153 */     initialize();
/*     */     
/* 155 */     float tickDuration = 50.0F;
/* 156 */     long currentUpdate = (new Date()).getTime();
/* 157 */     float timeSpan = (float)(currentUpdate - this.previousUpdate);
/* 158 */     this.previousUpdate = currentUpdate;
/*     */     
/* 160 */     float displayPortion = timeSpan / tickDuration;
/*     */     
/* 162 */     if ((this.lifetimeDisplay - this.lifetime) <= 1.0D) {
/* 163 */       this.lifetimeDisplay = (float)(this.lifetimeDisplay + 1.0D * displayPortion);
/*     */     }
/*     */   }
/*     */   
/*     */   private void build() {
/* 168 */     if (this.slotToBuild != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 175 */       int destX = (int)Math.floor(this.destination.x);
/* 176 */       int destY = (int)Math.floor(this.destination.y);
/* 177 */       int destZ = (int)Math.floor(this.destination.z);
/* 178 */       Block oldBlock = this.context.world().func_147439_a(destX, destY, destZ);
/* 179 */       int oldMeta = this.context.world().func_72805_g(destX, destY, destZ);
/*     */       
/* 181 */       if (this.slotToBuild.writeToWorld(this.context)) {
/* 182 */         this.context.world().func_72889_a(null, 2001, destX, destY, destZ, 
/*     */             
/* 184 */             Block.func_149682_b(oldBlock) + (oldMeta << 12));
/* 185 */       } else if (this.slotToBuild.stackConsumed != null) {
/* 186 */         for (ItemStack s : this.slotToBuild.stackConsumed) {
/* 187 */           if (s != null && (!(s.func_77973_b() instanceof net.minecraft.item.ItemBlock) || !(Block.func_149634_a(s.func_77973_b()) instanceof buildcraft.core.BlockBuildTool))) {
/* 188 */             InvUtils.dropItems(this.context.world(), s, destX, destY, destZ);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public LinkedList<StackAtPosition> getStacks() {
/* 196 */     int d = 0;
/*     */     
/* 198 */     for (StackAtPosition s : this.stacksToDisplay) {
/* 199 */       float stackLife = this.lifetimeDisplay - d;
/*     */       
/* 201 */       if (stackLife <= this.maxLifetime && stackLife > 0.0F) {
/* 202 */         s.pos = getDisplayPosition(stackLife);
/* 203 */         s.display = true;
/*     */       } else {
/* 205 */         s.display = false;
/*     */       } 
/*     */       
/* 208 */       d += ITEMS_SPACE;
/*     */     } 
/*     */     
/* 211 */     return this.stacksToDisplay;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/* 216 */     return this.isDone;
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 220 */     NBTTagCompound originNBT = new NBTTagCompound();
/* 221 */     this.origin.writeToNBT(originNBT);
/* 222 */     nbt.func_74782_a("origin", (NBTBase)originNBT);
/*     */     
/* 224 */     NBTTagCompound destinationNBT = new NBTTagCompound();
/* 225 */     this.destination.writeToNBT(destinationNBT);
/* 226 */     nbt.func_74782_a("destination", (NBTBase)destinationNBT);
/*     */     
/* 228 */     nbt.func_74776_a("lifetime", this.lifetime);
/*     */     
/* 230 */     NBTTagList items = new NBTTagList();
/*     */     
/* 232 */     for (StackAtPosition s : this.stacksToDisplay) {
/* 233 */       NBTTagCompound cpt = new NBTTagCompound();
/* 234 */       s.stack.func_77955_b(cpt);
/* 235 */       items.func_74742_a((NBTBase)cpt);
/*     */     } 
/*     */     
/* 238 */     nbt.func_74782_a("items", (NBTBase)items);
/*     */     
/* 240 */     MappingRegistry registry = new MappingRegistry();
/*     */     
/* 242 */     NBTTagCompound slotNBT = new NBTTagCompound();
/* 243 */     NBTTagCompound registryNBT = new NBTTagCompound();
/*     */     
/* 245 */     this.slotToBuild.writeToNBT(slotNBT, registry);
/* 246 */     registry.write(registryNBT);
/*     */     
/* 248 */     nbt.func_74782_a("registry", (NBTBase)registryNBT);
/*     */     
/* 250 */     if (this.slotToBuild instanceof BuildingSlotBlock) {
/* 251 */       nbt.func_74774_a("slotKind", (byte)0);
/*     */     } else {
/* 253 */       nbt.func_74774_a("slotKind", (byte)1);
/*     */     } 
/*     */     
/* 256 */     nbt.func_74782_a("slotToBuild", (NBTBase)slotNBT);
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) throws MappingNotFoundException {
/* 260 */     this.origin = new Position(nbt.func_74775_l("origin"));
/* 261 */     this.destination = new Position(nbt.func_74775_l("destination"));
/* 262 */     this.lifetime = nbt.func_74760_g("lifetime");
/*     */     
/* 264 */     NBTTagList items = nbt.func_150295_c("items", 10);
/*     */ 
/*     */     
/* 267 */     for (int i = 0; i < items.func_74745_c(); i++) {
/* 268 */       StackAtPosition sPos = new StackAtPosition();
/* 269 */       sPos.stack = ItemStack.func_77949_a(items
/* 270 */           .func_150305_b(i));
/* 271 */       this.stacksToDisplay.add(sPos);
/*     */     } 
/*     */     
/* 274 */     MappingRegistry registry = new MappingRegistry();
/* 275 */     registry.read(nbt.func_74775_l("registry"));
/*     */     
/* 277 */     if (nbt.func_74771_c("slotKind") == 0) {
/* 278 */       this.slotToBuild = new BuildingSlotBlock();
/*     */     } else {
/* 280 */       this.slotToBuild = new BuildingSlotEntity();
/*     */     } 
/*     */     
/* 283 */     this.slotToBuild.readFromNBT(nbt.func_74775_l("slotToBuild"), registry);
/*     */   }
/*     */   
/*     */   public void setStacksToDisplay(List<ItemStack> stacks) {
/* 287 */     if (stacks != null) {
/* 288 */       for (ItemStack s : stacks) {
/* 289 */         for (int i = 0; i < s.field_77994_a; i++) {
/* 290 */           StackAtPosition sPos = new StackAtPosition();
/* 291 */           sPos.stack = s.func_77946_l();
/* 292 */           sPos.stack.field_77994_a = 1;
/* 293 */           this.stacksToDisplay.add(sPos);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 301 */     this.origin = new Position();
/* 302 */     this.destination = new Position();
/* 303 */     this.origin.readData(stream);
/* 304 */     this.destination.readData(stream);
/* 305 */     this.lifetime = stream.readFloat();
/* 306 */     this.stacksToDisplay.clear();
/* 307 */     int size = stream.readUnsignedShort();
/* 308 */     for (int i = 0; i < size; i++) {
/* 309 */       StackAtPosition e = new StackAtPosition();
/* 310 */       e.readData(stream);
/* 311 */       this.stacksToDisplay.add(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 317 */     this.origin.writeData(stream);
/* 318 */     this.destination.writeData(stream);
/* 319 */     stream.writeFloat(this.lifetime);
/* 320 */     stream.writeShort(this.stacksToDisplay.size());
/* 321 */     for (StackAtPosition s : this.stacksToDisplay) {
/* 322 */       s.writeData(stream);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 328 */     return 131 * this.origin.hashCode() + this.destination.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\BuildingItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
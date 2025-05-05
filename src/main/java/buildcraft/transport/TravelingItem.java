/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import com.google.common.collect.MapMaker;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.MathHelper;
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
/*     */ public class TravelingItem
/*     */ {
/*  34 */   public static final TravelingItemCache serverCache = new TravelingItemCache();
/*  35 */   public static final TravelingItemCache clientCache = new TravelingItemCache();
/*  36 */   public static final InsertionHandler DEFAULT_INSERTION_HANDLER = new InsertionHandler();
/*  37 */   private static int maxId = 0;
/*     */   
/*  39 */   public final EnumSet<ForgeDirection> blacklist = EnumSet.noneOf(ForgeDirection.class);
/*     */   
/*     */   public double xCoord;
/*     */   
/*     */   public double yCoord;
/*     */   public double zCoord;
/*  45 */   public ForgeDirection input = ForgeDirection.UNKNOWN; public final int id; public boolean toCenter = true; public EnumColor color;
/*  46 */   public ForgeDirection output = ForgeDirection.UNKNOWN;
/*     */   
/*     */   public int displayList;
/*     */   
/*     */   public boolean hasDisplayList;
/*  51 */   protected float speed = 0.01F;
/*     */   
/*     */   protected ItemStack itemStack;
/*     */   protected TileEntity container;
/*     */   protected NBTTagCompound extraData;
/*  56 */   protected InsertionHandler insertionHandler = DEFAULT_INSERTION_HANDLER;
/*     */ 
/*     */   
/*     */   protected TravelingItem(int id) {
/*  60 */     this.id = id;
/*     */   }
/*     */   
/*     */   public static TravelingItem make(int id) {
/*  64 */     TravelingItem item = new TravelingItem(id);
/*  65 */     getCache().cache(item);
/*  66 */     return item;
/*     */   }
/*     */   
/*     */   public static TravelingItem make() {
/*  70 */     return make((maxId < 32767) ? ++maxId : (maxId = -32768));
/*     */   }
/*     */   
/*     */   public static TravelingItem make(double x, double y, double z, ItemStack stack) {
/*  74 */     TravelingItem item = make();
/*  75 */     item.xCoord = x;
/*  76 */     item.yCoord = y;
/*  77 */     item.zCoord = z;
/*  78 */     item.itemStack = stack.func_77946_l();
/*  79 */     return item;
/*     */   }
/*     */   
/*     */   public static TravelingItem make(NBTTagCompound nbt) {
/*  83 */     TravelingItem item = make();
/*  84 */     item.readFromNBT(nbt);
/*  85 */     return item;
/*     */   }
/*     */   
/*     */   public static TravelingItemCache getCache() {
/*  89 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
/*  90 */       return clientCache;
/*     */     }
/*  92 */     return serverCache;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(double x, double y, double z) {
/*  97 */     this.xCoord = x;
/*  98 */     this.yCoord = y;
/*  99 */     this.zCoord = z;
/*     */   }
/*     */   
/*     */   public void movePosition(double x, double y, double z) {
/* 103 */     this.xCoord += x;
/* 104 */     this.yCoord += y;
/* 105 */     this.zCoord += z;
/*     */   }
/*     */   
/*     */   public float getSpeed() {
/* 109 */     return this.speed;
/*     */   }
/*     */   
/*     */   public void setSpeed(float speed) {
/* 113 */     this.speed = speed;
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack() {
/* 117 */     return this.itemStack;
/*     */   }
/*     */   
/*     */   public void setItemStack(ItemStack item) {
/* 121 */     this.itemStack = item;
/*     */   }
/*     */   
/*     */   public TileEntity getContainer() {
/* 125 */     return this.container;
/*     */   }
/*     */   
/*     */   public void setContainer(TileEntity container) {
/* 129 */     this.container = container;
/*     */   }
/*     */   
/*     */   public NBTTagCompound getExtraData() {
/* 133 */     if (this.extraData == null) {
/* 134 */       this.extraData = new NBTTagCompound();
/*     */     }
/* 136 */     return this.extraData;
/*     */   }
/*     */   
/*     */   public boolean hasExtraData() {
/* 140 */     return (this.extraData != null);
/*     */   }
/*     */   
/*     */   public void setInsertionHandler(InsertionHandler handler) {
/* 144 */     if (handler == null) {
/*     */       return;
/*     */     }
/* 147 */     this.insertionHandler = handler;
/*     */   }
/*     */   
/*     */   public InsertionHandler getInsertionHandler() {
/* 151 */     return this.insertionHandler;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 155 */     this.toCenter = true;
/* 156 */     this.blacklist.clear();
/* 157 */     this.input = ForgeDirection.UNKNOWN;
/* 158 */     this.output = ForgeDirection.UNKNOWN;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound data) {
/* 163 */     setPosition(data.func_74769_h("x"), data.func_74769_h("y"), data.func_74769_h("z"));
/*     */     
/* 165 */     setSpeed(data.func_74760_g("speed"));
/* 166 */     setItemStack(ItemStack.func_77949_a(data.func_74775_l("Item")));
/*     */     
/* 168 */     this.toCenter = data.func_74767_n("toCenter");
/* 169 */     this.input = ForgeDirection.getOrientation(data.func_74771_c("input"));
/* 170 */     this.output = ForgeDirection.getOrientation(data.func_74771_c("output"));
/*     */     
/* 172 */     byte c = data.func_74771_c("color");
/* 173 */     if (c != -1) {
/* 174 */       this.color = EnumColor.fromId(c);
/*     */     }
/*     */     
/* 177 */     if (data.func_74764_b("extraData")) {
/* 178 */       this.extraData = data.func_74775_l("extraData");
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound data) {
/* 183 */     data.func_74780_a("x", this.xCoord);
/* 184 */     data.func_74780_a("y", this.yCoord);
/* 185 */     data.func_74780_a("z", this.zCoord);
/* 186 */     data.func_74776_a("speed", getSpeed());
/* 187 */     NBTTagCompound itemStackTag = new NBTTagCompound();
/* 188 */     getItemStack().func_77955_b(itemStackTag);
/* 189 */     data.func_74782_a("Item", (NBTBase)itemStackTag);
/*     */     
/* 191 */     data.func_74757_a("toCenter", this.toCenter);
/* 192 */     data.func_74774_a("input", (byte)this.input.ordinal());
/* 193 */     data.func_74774_a("output", (byte)this.output.ordinal());
/*     */     
/* 195 */     data.func_74774_a("color", (this.color != null) ? (byte)this.color.ordinal() : -1);
/*     */     
/* 197 */     if (this.extraData != null) {
/* 198 */       data.func_74782_a("extraData", (NBTBase)this.extraData);
/*     */     }
/*     */   }
/*     */   
/*     */   public EntityItem toEntityItem() {
/* 203 */     if (this.container != null && !(this.container.func_145831_w()).field_72995_K) {
/* 204 */       if ((getItemStack()).field_77994_a <= 0) {
/* 205 */         return null;
/*     */       }
/*     */       
/* 208 */       Position motion = new Position(0.0D, 0.0D, 0.0D, this.output);
/* 209 */       motion.moveForwards(0.1D + (getSpeed() * 2.0F));
/*     */       
/* 211 */       EntityItem entity = new EntityItem(this.container.func_145831_w(), this.xCoord, this.yCoord, this.zCoord, getItemStack());
/* 212 */       entity.lifespan = BuildCraftCore.itemLifespan * 20;
/* 213 */       entity.field_145804_b = 10;
/*     */       
/* 215 */       float f3 = 0.0F + (this.container.func_145831_w()).field_73012_v.nextFloat() * 0.04F - 0.02F;
/* 216 */       entity.field_70159_w = ((float)(this.container.func_145831_w()).field_73012_v.nextGaussian() * f3) + motion.x;
/* 217 */       entity.field_70181_x = ((float)(this.container.func_145831_w()).field_73012_v.nextGaussian() * f3) + motion.y;
/* 218 */       entity.field_70179_y = ((float)(this.container.func_145831_w()).field_73012_v.nextGaussian() * f3) + motion.z;
/* 219 */       return entity;
/*     */     } 
/* 221 */     return null;
/*     */   }
/*     */   
/*     */   public float getEntityBrightness(float f) {
/* 225 */     int i = MathHelper.func_76128_c(this.xCoord);
/* 226 */     int j = MathHelper.func_76128_c(this.zCoord);
/* 227 */     if (this.container != null && this.container.func_145831_w().func_72899_e(i, 64, j)) {
/* 228 */       double d = 0.66D;
/* 229 */       int k = MathHelper.func_76128_c(this.yCoord + d);
/* 230 */       return this.container.func_145831_w().func_72801_o(i, k, j);
/*     */     } 
/* 232 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCorrupted() {
/* 237 */     return (this.itemStack == null || this.itemStack.field_77994_a <= 0 || this.itemStack.func_77973_b() == null);
/*     */   }
/*     */   
/*     */   public boolean canBeGroupedWith(TravelingItem otherItem) {
/* 241 */     if (otherItem == this) {
/* 242 */       return false;
/*     */     }
/* 244 */     if (this.toCenter != otherItem.toCenter) {
/* 245 */       return false;
/*     */     }
/* 247 */     if (this.output != otherItem.output) {
/* 248 */       return false;
/*     */     }
/* 250 */     if (this.color != otherItem.color) {
/* 251 */       return false;
/*     */     }
/* 253 */     if (hasExtraData() || otherItem.hasExtraData()) {
/* 254 */       return false;
/*     */     }
/* 256 */     if (this.insertionHandler != DEFAULT_INSERTION_HANDLER) {
/* 257 */       return false;
/*     */     }
/* 259 */     if (!this.blacklist.equals(otherItem.blacklist)) {
/* 260 */       return false;
/*     */     }
/* 262 */     if (otherItem.isCorrupted()) {
/* 263 */       return false;
/*     */     }
/* 265 */     return StackHelper.canStacksMerge(this.itemStack, otherItem.itemStack);
/*     */   }
/*     */   
/*     */   public boolean tryMergeInto(TravelingItem otherItem) {
/* 269 */     if (!canBeGroupedWith(otherItem)) {
/* 270 */       return false;
/*     */     }
/* 272 */     if (StackHelper.mergeStacks(this.itemStack, otherItem.itemStack, false) == this.itemStack.field_77994_a) {
/* 273 */       StackHelper.mergeStacks(this.itemStack, otherItem.itemStack, true);
/* 274 */       this.itemStack.field_77994_a = 0;
/* 275 */       return true;
/*     */     } 
/* 277 */     return false;
/*     */   }
/*     */   
/*     */   public boolean ignoreWeight() {
/* 281 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 286 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 291 */     if (obj == null) {
/* 292 */       return false;
/*     */     }
/* 294 */     if (getClass() != obj.getClass()) {
/* 295 */       return false;
/*     */     }
/* 297 */     TravelingItem other = (TravelingItem)obj;
/* 298 */     if (this.id != other.id) {
/* 299 */       return false;
/*     */     }
/* 301 */     return true;
/*     */   }
/*     */   
/*     */   public void cleanup() {
/* 305 */     if (this.hasDisplayList) {
/* 306 */       TransportProxy.proxy.clearDisplayList(this.displayList);
/* 307 */       this.hasDisplayList = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 313 */     return "TravelingItem: " + this.id;
/*     */   }
/*     */   
/*     */   public static class InsertionHandler {
/*     */     public boolean canInsertItem(TravelingItem item, IInventory inv) {
/* 318 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class TravelingItemCache {
/* 323 */     private final Map<Integer, TravelingItem> itemCache = (new MapMaker()).weakValues().makeMap();
/*     */     
/*     */     public void cache(TravelingItem item) {
/* 326 */       this.itemCache.put(Integer.valueOf(item.id), item);
/*     */     }
/*     */     
/*     */     public TravelingItem get(int id) {
/* 330 */       return this.itemCache.get(Integer.valueOf(id));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\TravelingItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package buildcraft.core;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.IAreaProvider;
/*     */ import buildcraft.api.core.IBox;
/*     */ import buildcraft.api.core.IPathProvider;
/*     */ import buildcraft.api.core.IZone;
/*     */ import buildcraft.api.items.IMapLocation;
/*     */ import buildcraft.core.lib.items.ItemBuildCraft;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.robotics.ZonePlan;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
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
/*     */ public class ItemMapLocation
/*     */   extends ItemBuildCraft
/*     */   implements IMapLocation
/*     */ {
/*     */   public ItemMapLocation() {
/*  41 */     super(BCCreativeTab.get("main"));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemStackLimit(ItemStack stack) {
/*  46 */     return NBTUtils.getItemData(stack).func_74764_b("kind") ? 1 : 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
/*  51 */     NBTTagCompound cpt = NBTUtils.getItemData(stack);
/*     */     
/*  53 */     if (cpt.func_74764_b("name")) {
/*  54 */       String name = cpt.func_74779_i("name");
/*  55 */       if (name.length() > 0) {
/*  56 */         list.add(name);
/*     */       }
/*     */     } 
/*     */     
/*  60 */     if (cpt.func_74764_b("kind")) {
/*  61 */       int x; NBTTagList pathNBT; int y; BlockIndex first; int z; int i; ForgeDirection side; int xLength; int j; int yLength; int k; int zLength; switch (cpt.func_74771_c("kind")) {
/*     */         case 0:
/*  63 */           x = cpt.func_74762_e("x");
/*  64 */           y = cpt.func_74762_e("y");
/*  65 */           z = cpt.func_74762_e("z");
/*  66 */           side = ForgeDirection.values()[cpt.func_74771_c("side")];
/*     */           
/*  68 */           list.add(StringUtils.localize("{" + x + ", " + y + ", " + z + ", " + side + "}"));
/*     */           break;
/*     */         
/*     */         case 1:
/*  72 */           x = cpt.func_74762_e("xMin");
/*  73 */           y = cpt.func_74762_e("yMin");
/*  74 */           z = cpt.func_74762_e("zMin");
/*  75 */           xLength = cpt.func_74762_e("xMax") - x + 1;
/*  76 */           yLength = cpt.func_74762_e("yMax") - y + 1;
/*  77 */           zLength = cpt.func_74762_e("zMax") - z + 1;
/*     */           
/*  79 */           list.add(StringUtils.localize("{" + x + ", " + y + ", " + z + "} + {" + xLength + " x " + yLength + " x " + zLength + "}"));
/*     */           break;
/*     */         
/*     */         case 2:
/*  83 */           pathNBT = cpt.func_150295_c("path", 10);
/*  84 */           first = new BlockIndex(pathNBT.func_150305_b(0));
/*     */           
/*  86 */           i = first.x;
/*  87 */           j = first.y;
/*  88 */           k = first.z;
/*     */           
/*  90 */           list.add(StringUtils.localize("{" + i + ", " + j + ", " + k + "} + " + pathNBT.func_74745_c() + " elements"));
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_77650_f(ItemStack stack) {
/* 102 */     NBTTagCompound cpt = NBTUtils.getItemData(stack);
/*     */     
/* 104 */     if (!cpt.func_74764_b("kind")) {
/* 105 */       return this.icons[0];
/*     */     }
/* 107 */     return func_77617_a(cpt.func_74771_c("kind") + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getIconNames() {
/* 113 */     return new String[] { "mapLocation/clean", "mapLocation/spot", "mapLocation/area", "mapLocation/path", "mapLocation/zone" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister par1IconRegister) {
/* 120 */     super.func_94581_a(par1IconRegister);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack stack, EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
/* 126 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 127 */     NBTTagCompound cpt = NBTUtils.getItemData(stack);
/*     */     
/* 129 */     if (tile instanceof IPathProvider) {
/* 130 */       cpt.func_74774_a("kind", (byte)2);
/*     */       
/* 132 */       NBTTagList pathNBT = new NBTTagList();
/*     */       
/* 134 */       for (BlockIndex index : ((IPathProvider)tile).getPath()) {
/* 135 */         NBTTagCompound nbt = new NBTTagCompound();
/* 136 */         index.writeTo(nbt);
/* 137 */         pathNBT.func_74742_a((NBTBase)nbt);
/*     */       } 
/*     */       
/* 140 */       cpt.func_74782_a("path", (NBTBase)pathNBT);
/* 141 */     } else if (tile instanceof IAreaProvider) {
/* 142 */       cpt.func_74774_a("kind", (byte)1);
/*     */       
/* 144 */       IAreaProvider areaTile = (IAreaProvider)tile;
/*     */       
/* 146 */       cpt.func_74768_a("xMin", areaTile.xMin());
/* 147 */       cpt.func_74768_a("yMin", areaTile.yMin());
/* 148 */       cpt.func_74768_a("zMin", areaTile.zMin());
/* 149 */       cpt.func_74768_a("xMax", areaTile.xMax());
/* 150 */       cpt.func_74768_a("yMax", areaTile.yMax());
/* 151 */       cpt.func_74768_a("zMax", areaTile.zMax());
/*     */     } else {
/*     */       
/* 154 */       cpt.func_74774_a("kind", (byte)0);
/*     */       
/* 156 */       cpt.func_74774_a("side", (byte)side);
/* 157 */       cpt.func_74768_a("x", x);
/* 158 */       cpt.func_74768_a("y", y);
/* 159 */       cpt.func_74768_a("z", z);
/*     */     } 
/*     */     
/* 162 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBox getBox(ItemStack item) {
/* 167 */     NBTTagCompound cpt = NBTUtils.getItemData(item);
/*     */     
/* 169 */     if (cpt.func_74764_b("kind") && cpt.func_74771_c("kind") == 1) {
/* 170 */       int xMin = cpt.func_74762_e("xMin");
/* 171 */       int yMin = cpt.func_74762_e("yMin");
/* 172 */       int zMin = cpt.func_74762_e("zMin");
/* 173 */       int xMax = cpt.func_74762_e("xMax");
/* 174 */       int yMax = cpt.func_74762_e("yMax");
/* 175 */       int zMax = cpt.func_74762_e("zMax");
/*     */       
/* 177 */       return new Box(xMin, yMin, zMin, xMax, yMax, zMax);
/* 178 */     }  if (cpt.func_74764_b("kind") && cpt.func_74771_c("kind") == 0) {
/* 179 */       return getPointBox(item);
/*     */     }
/* 181 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBox getPointBox(ItemStack item) {
/* 186 */     NBTTagCompound cpt = NBTUtils.getItemData(item);
/*     */     
/* 188 */     if (cpt.func_74764_b("kind") && cpt.func_74771_c("kind") == 0) {
/* 189 */       int x = cpt.func_74762_e("x");
/* 190 */       int y = cpt.func_74762_e("y");
/* 191 */       int z = cpt.func_74762_e("z");
/*     */       
/* 193 */       return new Box(x, y, z, x, y, z);
/*     */     } 
/* 195 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ForgeDirection getPointSide(ItemStack item) {
/* 201 */     NBTTagCompound cpt = NBTUtils.getItemData(item);
/*     */     
/* 203 */     if (cpt.func_74764_b("kind") && cpt.func_74771_c("kind") == 0) {
/* 204 */       return ForgeDirection.values()[cpt.func_74771_c("side")];
/*     */     }
/* 206 */     return ForgeDirection.UNKNOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockIndex getPoint(ItemStack item) {
/* 212 */     NBTTagCompound cpt = NBTUtils.getItemData(item);
/*     */     
/* 214 */     if (cpt.func_74764_b("kind") && cpt.func_74771_c("kind") == 0) {
/* 215 */       return new BlockIndex(cpt.func_74762_e("x"), cpt.func_74762_e("y"), cpt.func_74762_e("z"));
/*     */     }
/* 217 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IZone getZone(ItemStack item) {
/* 223 */     NBTTagCompound cpt = NBTUtils.getItemData(item);
/*     */     
/* 225 */     if (cpt.func_74764_b("kind") && cpt.func_74771_c("kind") == 3) {
/* 226 */       ZonePlan plan = new ZonePlan();
/* 227 */       plan.readFromNBT(cpt);
/*     */       
/* 229 */       return (IZone)plan;
/* 230 */     }  if (cpt.func_74764_b("kind") && cpt.func_74771_c("kind") == 1)
/* 231 */       return (IZone)getBox(item); 
/* 232 */     if (cpt.func_74764_b("kind") && cpt.func_74771_c("kind") == 0) {
/* 233 */       return (IZone)getPointBox(item);
/*     */     }
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BlockIndex> getPath(ItemStack item) {
/* 241 */     NBTTagCompound cpt = NBTUtils.getItemData(item);
/*     */     
/* 243 */     if (cpt.func_74764_b("kind") && cpt.func_74771_c("kind") == 2) {
/* 244 */       List<BlockIndex> indexList = new ArrayList<BlockIndex>();
/* 245 */       NBTTagList pathNBT = cpt.func_150295_c("path", 10);
/* 246 */       for (int i = 0; i < pathNBT.func_74745_c(); i++) {
/* 247 */         indexList.add(new BlockIndex(pathNBT.func_150305_b(i)));
/*     */       }
/* 249 */       return indexList;
/* 250 */     }  if (cpt.func_74764_b("kind") && cpt.func_74771_c("kind") == 0) {
/* 251 */       List<BlockIndex> indexList = new ArrayList<BlockIndex>();
/* 252 */       indexList.add(new BlockIndex(cpt.func_74762_e("x"), cpt.func_74762_e("y"), cpt.func_74762_e("z")));
/* 253 */       return indexList;
/*     */     } 
/* 255 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setZone(ItemStack item, ZonePlan plan) {
/* 260 */     NBTTagCompound cpt = NBTUtils.getItemData(item);
/*     */     
/* 262 */     cpt.func_74774_a("kind", (byte)3);
/* 263 */     plan.writeToNBT(cpt);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName(ItemStack item) {
/* 268 */     return NBTUtils.getItemData(item).func_74779_i("name");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setName(ItemStack item, String name) {
/* 273 */     NBTTagCompound cpt = NBTUtils.getItemData(item);
/* 274 */     cpt.func_74778_a("name", name);
/*     */     
/* 276 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMapLocation.MapLocationType getType(ItemStack stack) {
/* 281 */     NBTTagCompound cpt = NBTUtils.getItemData(stack);
/* 282 */     return IMapLocation.MapLocationType.values()[cpt.func_74771_c("kind")];
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\ItemMapLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
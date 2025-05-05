/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.JavaTools;
/*     */ import buildcraft.api.facades.FacadeType;
/*     */ import buildcraft.api.facades.IFacadeItem;
/*     */ import buildcraft.api.recipes.BuildcraftRecipeRegistry;
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.api.transport.PipeWire;
/*     */ import buildcraft.api.transport.pluggable.IPipePluggableItem;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import buildcraft.core.BCCreativeTab;
/*     */ import buildcraft.core.lib.items.ItemBuildCraft;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import com.google.common.base.Strings;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
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
/*     */ 
/*     */ public class ItemFacade
/*     */   extends ItemBuildCraft
/*     */   implements IFacadeItem, IPipePluggableItem
/*     */ {
/*     */   public static class FacadeState
/*     */   {
/*     */     public final Block block;
/*     */     public final int metadata;
/*     */     public final boolean transparent;
/*     */     public final boolean hollow;
/*     */     public final PipeWire wire;
/*     */     
/*     */     public FacadeState(Block block, int metadata, PipeWire wire) {
/*  62 */       this.block = block;
/*  63 */       this.metadata = metadata;
/*  64 */       this.wire = wire;
/*  65 */       this.transparent = false;
/*  66 */       this.hollow = false;
/*     */     }
/*     */     
/*     */     public FacadeState(Block block, int metadata, PipeWire wire, boolean hollow) {
/*  70 */       this.block = block;
/*  71 */       this.metadata = metadata;
/*  72 */       this.wire = wire;
/*  73 */       this.transparent = false;
/*  74 */       this.hollow = hollow;
/*     */     }
/*     */     
/*     */     public FacadeState(NBTTagCompound nbt) {
/*  78 */       this.block = nbt.func_74764_b("block") ? (Block)Block.field_149771_c.func_82594_a(nbt.func_74779_i("block")) : null;
/*  79 */       this.metadata = nbt.func_74771_c("metadata");
/*  80 */       this.wire = nbt.func_74764_b("wire") ? PipeWire.fromOrdinal(nbt.func_74771_c("wire")) : null;
/*  81 */       this.transparent = (nbt.func_74764_b("transparent") && nbt.func_74767_n("transparent"));
/*  82 */       this.hollow = (nbt.func_74764_b("hollow") && nbt.func_74767_n("hollow"));
/*     */     }
/*     */     
/*     */     private FacadeState(PipeWire wire) {
/*  86 */       this.block = null;
/*  87 */       this.metadata = 0;
/*  88 */       this.wire = wire;
/*  89 */       this.transparent = true;
/*  90 */       this.hollow = false;
/*     */     }
/*     */     
/*     */     public static FacadeState create(Block block, int metadata) {
/*  94 */       return create(block, metadata, null);
/*     */     }
/*     */     
/*     */     public static FacadeState create(Block block, int metadata, PipeWire wire) {
/*  98 */       return new FacadeState(block, metadata, wire);
/*     */     }
/*     */     
/*     */     public static FacadeState createTransparent(PipeWire wire) {
/* 102 */       return new FacadeState(wire);
/*     */     }
/*     */     
/*     */     public void writeToNBT(NBTTagCompound nbt) {
/* 106 */       if (this.block != null) {
/* 107 */         nbt.func_74778_a("block", Block.field_149771_c.func_148750_c(this.block));
/*     */       }
/* 109 */       nbt.func_74774_a("metadata", (byte)this.metadata);
/* 110 */       if (this.wire != null) {
/* 111 */         nbt.func_74774_a("wire", (byte)this.wire.ordinal());
/*     */       }
/* 113 */       nbt.func_74757_a("transparent", this.transparent);
/* 114 */       nbt.func_74757_a("hollow", this.hollow);
/*     */     }
/*     */     
/*     */     public static NBTTagList writeArray(FacadeState[] states) {
/* 118 */       if (states == null) {
/* 119 */         return null;
/*     */       }
/* 121 */       NBTTagList list = new NBTTagList();
/* 122 */       for (FacadeState state : states) {
/* 123 */         NBTTagCompound stateNBT = new NBTTagCompound();
/* 124 */         state.writeToNBT(stateNBT);
/* 125 */         list.func_74742_a((NBTBase)stateNBT);
/*     */       } 
/* 127 */       return list;
/*     */     }
/*     */     
/*     */     public static FacadeState[] readArray(NBTTagList list) {
/* 131 */       if (list == null) {
/* 132 */         return null;
/*     */       }
/* 134 */       int length = list.func_74745_c();
/* 135 */       FacadeState[] states = new FacadeState[length];
/* 136 */       for (int i = 0; i < length; i++) {
/* 137 */         states[i] = new FacadeState(list.func_150305_b(i));
/*     */       }
/* 139 */       return states;
/*     */     }
/*     */   }
/*     */   
/* 143 */   public static final ArrayList<ItemStack> allFacades = new ArrayList<ItemStack>();
/* 144 */   public static final ArrayList<ItemStack> allHollowFacades = new ArrayList<ItemStack>();
/* 145 */   public static final ArrayList<String> allFacadeIDs = new ArrayList<String>();
/* 146 */   public static final ArrayList<String> blacklistedFacades = new ArrayList<String>();
/*     */   
/* 148 */   private static final Block NULL_BLOCK = null;
/* 149 */   private static final ItemStack NO_MATCH = new ItemStack(NULL_BLOCK, 0, 0);
/*     */   
/* 151 */   private static final Block[] PREVIEW_FACADES = new Block[] { Blocks.field_150344_f, Blocks.field_150417_aV, Blocks.field_150359_w };
/*     */ 
/*     */   
/* 154 */   private static int RANDOM_FACADE_ID = -1;
/*     */   
/*     */   public ItemFacade() {
/* 157 */     super(BCCreativeTab.isPresent("facades") ? (CreativeTabs)BCCreativeTab.get("facades") : (CreativeTabs)BCCreativeTab.get("main"));
/*     */     
/* 159 */     func_77627_a(true);
/* 160 */     func_77656_e(0);
/*     */   }
/*     */   public String func_77653_i(ItemStack itemstack) {
/*     */     FacadeState[] states;
/*     */     String displayName;
/* 165 */     switch (getFacadeType(itemstack)) {
/*     */       case Basic:
/* 167 */         states = getFacadeStates(itemstack);
/* 168 */         displayName = (states.length > 0) ? getFacadeStateDisplayName(states[0]) : "CORRUPT";
/* 169 */         return super.func_77653_i(itemstack) + ": " + displayName;
/*     */       case Phased:
/* 171 */         return StringUtils.localize("item.FacadePhased.name");
/*     */     } 
/* 173 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack itemstack) {
/* 179 */     return "item.Facade";
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean debug) {
/* 184 */     for (FacadeState state : getFacadeStates(stack)) {
/* 185 */       if (state != null && !state.transparent && state.block != null && Item.func_150898_a(state.block) != null) {
/* 186 */         Item.func_150898_a(state.block).func_77624_a(new ItemStack(state.block, 1, state.metadata), player, list, debug);
/*     */       }
/*     */     } 
/* 189 */     if (getFacadeType(stack) == FacadeType.Phased) {
/* 190 */       String stateString = StringUtils.localize("item.FacadePhased.state");
/* 191 */       FacadeState defaultState = null;
/* 192 */       for (FacadeState state : getFacadeStates(stack)) {
/* 193 */         if (state.wire == null) {
/* 194 */           defaultState = state;
/*     */         } else {
/*     */           
/* 197 */           list.add(String.format(stateString, new Object[] { state.wire.getColor(), getFacadeStateDisplayName(state) }));
/*     */         } 
/* 199 */       }  if (defaultState != null) {
/* 200 */         list.add(1, String.format(StringUtils.localize("item.FacadePhased.state_default"), new Object[] { getFacadeStateDisplayName(defaultState) }));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getFacadeStateDisplayName(FacadeState state) {
/* 206 */     if (state.block == null) {
/* 207 */       return StringUtils.localize("item.FacadePhased.state_transparent");
/*     */     }
/* 209 */     int meta = state.metadata;
/* 210 */     if (state.block.func_149645_b() == 31) {
/* 211 */       meta &= 0x3;
/* 212 */     } else if (state.block.func_149645_b() == 39 && meta > 2) {
/* 213 */       meta = 2;
/*     */     } 
/* 215 */     String s = CoreProxy.proxy.getItemDisplayName(new ItemStack(state.block, 1, meta));
/* 216 */     if (state.hollow) {
/* 217 */       s = s + " (" + StringUtils.localize("item.Facade.state_hollow") + ")";
/*     */     }
/* 219 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item item, CreativeTabs par2CreativeTabs, List<ItemStack> itemList) {
/* 226 */     if (BuildCraftTransport.showAllFacadesCreative) {
/* 227 */       for (ItemStack stack : allFacades) {
/* 228 */         itemList.add(stack);
/*     */       }
/* 230 */       for (ItemStack stack : allHollowFacades) {
/* 231 */         itemList.add(stack);
/*     */       }
/*     */     } else {
/* 234 */       List<ItemStack> hollowFacades = new ArrayList<ItemStack>();
/* 235 */       for (Block b : PREVIEW_FACADES) {
/* 236 */         if (isBlockValidForFacade(b) && !isBlockBlacklisted(b)) {
/* 237 */           ItemStack facade = getFacadeForBlock(b, 0);
/* 238 */           itemList.add(facade);
/* 239 */           FacadeState state = getFacadeStates(facade)[0];
/* 240 */           hollowFacades.add(getFacade(new FacadeState[] { new FacadeState(state.block, state.metadata, state.wire, true) }));
/*     */         } 
/*     */       } 
/* 243 */       if (RANDOM_FACADE_ID < 0) {
/* 244 */         RANDOM_FACADE_ID = BuildCraftCore.random.nextInt(allFacades.size());
/*     */       }
/* 246 */       itemList.add(allFacades.get(RANDOM_FACADE_ID));
/* 247 */       itemList.addAll(hollowFacades);
/* 248 */       itemList.add(allHollowFacades.get(RANDOM_FACADE_ID));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void initialize() {
/* 253 */     for (Object o : Block.field_149771_c) {
/* 254 */       Block b = (Block)o;
/*     */       
/* 256 */       if (!isBlockValidForFacade(b)) {
/*     */         continue;
/*     */       }
/*     */       
/* 260 */       Item item = Item.func_150898_a(b);
/*     */       
/* 262 */       if (item == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 266 */       if (isBlockBlacklisted(b)) {
/*     */         continue;
/*     */       }
/*     */       
/* 270 */       registerValidFacades(b, item);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerValidFacades(Block block, Item item) {
/* 275 */     ArrayList<ItemStack> stacks = new ArrayList<ItemStack>(16);
/*     */     try {
/* 277 */       if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
/* 278 */         for (CreativeTabs ct : item.getCreativeTabs()) {
/* 279 */           block.func_149666_a(item, ct, stacks);
/*     */         }
/*     */       } else {
/* 282 */         for (int i = 0; i < 16; i++) {
/* 283 */           stacks.add(new ItemStack(item, 1, i));
/*     */         }
/*     */       } 
/* 286 */     } catch (Exception e) {
/* 287 */       e.printStackTrace();
/*     */     } 
/* 289 */     for (ItemStack stack : stacks) {
/*     */       try {
/* 291 */         int i = stack.func_77960_j();
/*     */         
/* 293 */         if (block.hasTileEntity(i)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 301 */           if (stack.func_82833_r() == null || Strings.isNullOrEmpty(stack.func_77977_a())) {
/*     */             continue;
/*     */           }
/* 304 */         } catch (Throwable t) {
/*     */           continue;
/*     */         } 
/*     */         
/* 308 */         addFacade(stack);
/* 309 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*     */       
/* 311 */       } catch (Throwable t) {
/* 312 */         t.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isBlockBlacklisted(Block block) {
/* 318 */     String blockName = Block.field_149771_c.func_148750_c(block);
/*     */     
/* 320 */     if (blockName == null) {
/* 321 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 325 */     for (String blacklistedBlock : blacklistedFacades) {
/* 326 */       if (blockName.equals(blacklistedBlock)) {
/* 327 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 332 */     for (String blacklistedBlock : BuildCraftTransport.facadeBlacklist) {
/* 333 */       if (blockName.equals(JavaTools.stripSurroundingQuotes(blacklistedBlock))) {
/* 334 */         return true ^ BuildCraftTransport.facadeTreatBlacklistAsWhitelist;
/*     */       }
/*     */     } 
/*     */     
/* 338 */     return false ^ BuildCraftTransport.facadeTreatBlacklistAsWhitelist;
/*     */   }
/*     */   
/*     */   private static boolean isBlockValidForFacade(Block block) {
/*     */     try {
/* 343 */       if (block.func_149704_x() != 0.0D || block.func_149665_z() != 0.0D || block.func_149706_B() != 0.0D) {
/* 344 */         return false;
/*     */       }
/*     */       
/* 347 */       if (block.func_149753_y() != 1.0D || block.func_149669_A() != 1.0D || block.func_149693_C() != 1.0D) {
/* 348 */         return false;
/*     */       }
/*     */       
/* 351 */       if (block instanceof buildcraft.core.BlockSpring || block instanceof BlockGenericPipe) {
/* 352 */         return false;
/*     */       }
/*     */       
/* 355 */       return true;
/* 356 */     } catch (Throwable ignored) {
/* 357 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static FacadeState[] getFacadeStates(ItemStack stack) {
/* 362 */     if (!stack.func_77942_o()) {
/* 363 */       return new FacadeState[0];
/*     */     }
/* 365 */     NBTTagCompound nbt = stack.func_77978_p();
/* 366 */     nbt = migrate(stack, nbt);
/* 367 */     if (!nbt.func_74764_b("states")) {
/* 368 */       return new FacadeState[0];
/*     */     }
/* 370 */     return FacadeState.readArray(nbt.func_150295_c("states", 10));
/*     */   }
/*     */   private static NBTTagCompound migrate(ItemStack stack, NBTTagCompound nbt) {
/*     */     int metadataAlt;
/* 374 */     Block block = null, blockAlt = null;
/* 375 */     int metadata = 0;
/* 376 */     PipeWire wire = null;
/* 377 */     if (nbt.func_74764_b("id")) {
/* 378 */       block = (Block)Block.field_149771_c.func_148754_a(nbt.func_74762_e("id"));
/* 379 */     } else if (nbt.func_74764_b("name")) {
/* 380 */       block = (Block)Block.field_149771_c.func_82594_a(nbt.func_74779_i("name"));
/*     */     } 
/* 382 */     if (nbt.func_74764_b("name_alt")) {
/* 383 */       blockAlt = (Block)Block.field_149771_c.func_82594_a(nbt.func_74779_i("name_alt"));
/*     */     }
/* 385 */     if (nbt.func_74764_b("meta")) {
/* 386 */       metadata = nbt.func_74762_e("meta");
/*     */     }
/* 388 */     if (nbt.func_74764_b("meta_alt")) {
/* 389 */       metadataAlt = nbt.func_74762_e("meta_alt");
/*     */     } else {
/* 391 */       metadataAlt = stack.func_77960_j() & 0xF;
/*     */     } 
/* 393 */     if (nbt.func_74764_b("wire")) {
/* 394 */       wire = PipeWire.fromOrdinal(nbt.func_74762_e("wire"));
/*     */     }
/* 396 */     if (block != null) {
/*     */       
/* 398 */       FacadeState states[], mainState = FacadeState.create(block, metadata);
/* 399 */       if (blockAlt != null && wire != null) {
/* 400 */         FacadeState altState = FacadeState.create(blockAlt, metadataAlt, wire);
/* 401 */         states = new FacadeState[] { mainState, altState };
/*     */       } else {
/* 403 */         states = new FacadeState[] { mainState };
/*     */       } 
/* 405 */       NBTTagCompound newNbt = getFacade(states).func_77978_p();
/* 406 */       stack.func_77982_d(newNbt);
/* 407 */       return newNbt;
/*     */     } 
/* 409 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public Block[] getBlocksForFacade(ItemStack stack) {
/* 414 */     FacadeState[] states = getFacadeStates(stack);
/* 415 */     Block[] blocks = new Block[states.length];
/* 416 */     for (int i = 0; i < states.length; i++) {
/* 417 */       blocks[i] = (states[i]).block;
/*     */     }
/* 419 */     return blocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getMetaValuesForFacade(ItemStack stack) {
/* 424 */     FacadeState[] states = getFacadeStates(stack);
/* 425 */     int[] meta = new int[states.length];
/* 426 */     for (int i = 0; i < states.length; i++) {
/* 427 */       meta[i] = (states[i]).metadata;
/*     */     }
/* 429 */     return meta;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FacadeType getFacadeType(ItemStack stack) {
/* 435 */     if (!stack.func_77942_o()) {
/* 436 */       return FacadeType.Basic;
/*     */     }
/* 438 */     NBTTagCompound nbt = stack.func_77978_p();
/* 439 */     if (!nbt.func_74764_b("type")) {
/* 440 */       return FacadeType.Basic;
/*     */     }
/* 442 */     return FacadeType.fromOrdinal(nbt.func_74762_e("type"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
/* 448 */     return true;
/*     */   }
/*     */   
/*     */   public void addFacade(ItemStack itemStack) {
/* 452 */     if (itemStack.field_77994_a == 0) {
/* 453 */       itemStack.field_77994_a = 1;
/*     */     }
/*     */     
/* 456 */     Block block = Block.func_149634_a(itemStack.func_77973_b());
/* 457 */     if (block == null) {
/*     */       return;
/*     */     }
/* 460 */     if (!block.func_149688_o().func_76230_c()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 465 */     String recipeId = "buildcraft:facade{" + Block.field_149771_c.func_148750_c(block) + "#" + itemStack.func_77960_j() + "}";
/*     */     
/* 467 */     ItemStack facade = getFacadeForBlock(block, itemStack.func_77960_j());
/*     */     
/* 469 */     if (!allFacadeIDs.contains(recipeId)) {
/* 470 */       allFacadeIDs.add(recipeId);
/* 471 */       allFacades.add(facade);
/*     */       
/* 473 */       ItemStack facade6 = facade.func_77946_l();
/* 474 */       facade6.field_77994_a = 6;
/*     */       
/* 476 */       FacadeState state = getFacadeStates(facade6)[0];
/* 477 */       ItemStack facadeHollow = getFacade(new FacadeState[] { new FacadeState(state.block, state.metadata, state.wire, true) });
/*     */       
/* 479 */       allHollowFacades.add(facadeHollow);
/*     */       
/* 481 */       ItemStack facade6Hollow = facadeHollow.func_77946_l();
/* 482 */       facade6Hollow.field_77994_a = 6;
/*     */ 
/*     */       
/* 485 */       if (Loader.isModLoaded("BuildCraft|Silicon") && !BuildCraftTransport.facadeForceNonLaserRecipe) {
/* 486 */         BuildcraftRecipeRegistry.assemblyTable.addRecipe(recipeId, 8000, facade6, new Object[] { new ItemStack(BuildCraftTransport.pipeStructureCobblestone, 3), itemStack });
/*     */ 
/*     */         
/* 489 */         BuildcraftRecipeRegistry.assemblyTable.addRecipe(recipeId + ":hollow", 8000, facade6Hollow, new Object[] { new ItemStack(BuildCraftTransport.pipeStructureCobblestone, 3), itemStack });
/*     */ 
/*     */         
/* 492 */         BuildcraftRecipeRegistry.assemblyTable.addRecipe(recipeId + ":toHollow", 160, facadeHollow, new Object[] { facade });
/* 493 */         BuildcraftRecipeRegistry.assemblyTable.addRecipe(recipeId + ":fromHollow", 160, facade, new Object[] { facadeHollow });
/*     */       } else {
/* 495 */         GameRegistry.addShapedRecipe(facade6, new Object[] { "t ", "ts", "t ", Character.valueOf('t'), itemStack, Character.valueOf('s'), BuildCraftTransport.pipeStructureCobblestone });
/* 496 */         GameRegistry.addShapedRecipe(facade6Hollow, new Object[] { "t ", " s", "t ", Character.valueOf('t'), itemStack, Character.valueOf('s'), BuildCraftTransport.pipeStructureCobblestone });
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void blacklistFacade(String blockName) {
/* 502 */     if (!blacklistedFacades.contains(blockName)) {
/* 503 */       blacklistedFacades.add(blockName);
/*     */     }
/*     */   }
/*     */   
/*     */   public class FacadeRecipe
/*     */     implements IRecipe
/*     */   {
/*     */     public boolean func_77569_a(InventoryCrafting inventorycrafting, World world) {
/* 511 */       Object[] facade = getFacadeBlockFromCraftingGrid(inventorycrafting);
/*     */       
/* 513 */       return (facade != null && facade[0] != null && ((Block[])facade[0]).length == 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_77572_b(InventoryCrafting inventorycrafting) {
/* 518 */       Object[] facade = getFacadeBlockFromCraftingGrid(inventorycrafting);
/* 519 */       if (facade == null || ((Block[])facade[0]).length != 1) {
/* 520 */         return null;
/*     */       }
/*     */       
/* 523 */       Block block = ((Block[])facade[0])[0];
/* 524 */       ItemStack originalFacade = (ItemStack)facade[1];
/*     */       
/* 526 */       if (block == null) {
/* 527 */         return null;
/*     */       }
/*     */       
/* 530 */       return getNextFacadeItemStack(block, originalFacade);
/*     */     }
/*     */     
/*     */     private Object[] getFacadeBlockFromCraftingGrid(InventoryCrafting inventorycrafting) {
/* 534 */       ItemStack slotmatch = null;
/* 535 */       int countOfItems = 0;
/* 536 */       for (int i = 0; i < inventorycrafting.func_70302_i_(); i++) {
/* 537 */         ItemStack slot = inventorycrafting.func_70301_a(i);
/*     */         
/* 539 */         if (slot != null && slot.func_77973_b() == ItemFacade.this && slotmatch == null) {
/* 540 */           slotmatch = slot;
/* 541 */           countOfItems++;
/* 542 */         } else if (slot != null) {
/* 543 */           slotmatch = ItemFacade.NO_MATCH;
/*     */         } 
/*     */         
/* 546 */         if (countOfItems > 1) {
/* 547 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 551 */       if (slotmatch != null && slotmatch != ItemFacade.NO_MATCH) {
/* 552 */         return new Object[] { this.this$0.getBlocksForFacade(slotmatch), slotmatch };
/*     */       }
/*     */       
/* 555 */       return null;
/*     */     }
/*     */     
/*     */     private ItemStack getNextFacadeItemStack(Block block, ItemStack originalFacade) {
/* 559 */       int blockMeta = ItemFacade.this.getMetaValuesForFacade(originalFacade)[0];
/* 560 */       int stackMeta = blockMeta;
/*     */       
/* 562 */       switch (block.func_149645_b()) {
/*     */         case 31:
/* 564 */           if ((blockMeta & 0xC) == 0) {
/*     */             
/* 566 */             stackMeta = blockMeta & 0x3 | 0x4; break;
/* 567 */           }  if ((blockMeta & 0x8) == 0) {
/*     */             
/* 569 */             stackMeta = blockMeta & 0x3 | 0x8; break;
/* 570 */           }  if ((blockMeta & 0x4) == 0) {
/* 571 */             stackMeta = blockMeta & 0x3;
/*     */           }
/*     */           break;
/*     */         case 39:
/* 575 */           if (blockMeta >= 2 && blockMeta < 4) {
/* 576 */             stackMeta = blockMeta + 1; break;
/* 577 */           }  if (blockMeta == 4) {
/* 578 */             stackMeta = 2;
/*     */           }
/*     */           break;
/*     */       } 
/*     */       
/* 583 */       return ItemFacade.this.getFacadeForBlock(block, stackMeta);
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_77570_a() {
/* 588 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_77571_b() {
/* 593 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister par1IconRegister) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_94901_k() {
/* 606 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getFacadeForBlock(Block block, int metadata) {
/* 611 */     return getFacade(new FacadeState[] { FacadeState.create(block, metadata) });
/*     */   }
/*     */   
/*     */   public static ItemStack getAdvancedFacade(PipeWire wire, Block block, int metadata, Block blockAlt, int metaDataAlt) {
/* 615 */     return getFacade(new FacadeState[] { FacadeState.create(block, metadata), FacadeState.create(blockAlt, metaDataAlt, wire) });
/*     */   }
/*     */   
/*     */   public static ItemStack getFacade(FacadeState... states) {
/* 619 */     if (states == null || states.length == 0) {
/* 620 */       return null;
/*     */     }
/* 622 */     boolean basic = (states.length == 1 && (states[0]).wire == null);
/*     */     
/* 624 */     ItemStack stack = new ItemStack((Item)BuildCraftTransport.facadeItem, 1, 0);
/*     */     
/* 626 */     NBTTagCompound nbt = new NBTTagCompound();
/* 627 */     nbt.func_74774_a("type", (byte)(basic ? FacadeType.Basic : FacadeType.Phased).ordinal());
/* 628 */     nbt.func_74782_a("states", (NBTBase)FacadeState.writeArray(states));
/*     */     
/* 630 */     stack.func_77982_d(nbt);
/* 631 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public PipePluggable createPipePluggable(IPipe pipe, ForgeDirection side, ItemStack stack) {
/* 636 */     return new FacadePluggable(getFacadeStates(stack));
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\ItemFacade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
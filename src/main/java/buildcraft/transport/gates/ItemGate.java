/*     */ package buildcraft.transport.gates;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.gates.GateExpansions;
/*     */ import buildcraft.api.gates.IGateExpansion;
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.api.transport.pluggable.IPipePluggableItem;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import buildcraft.core.BCCreativeTab;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.lib.items.ItemBuildCraft;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.transport.Gate;
/*     */ import buildcraft.transport.Pipe;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
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
/*     */ public class ItemGate
/*     */   extends ItemBuildCraft
/*     */   implements IPipePluggableItem
/*     */ {
/*     */   protected static final String NBT_TAG_MAT = "mat";
/*     */   protected static final String NBT_TAG_LOGIC = "logic";
/*     */   protected static final String NBT_TAG_EX = "ex";
/*     */   private static ArrayList<ItemStack> allGates;
/*     */   
/*     */   public ItemGate() {
/*  56 */     func_77627_a(false);
/*  57 */     func_77656_e(0);
/*  58 */     setPassSneakClick(true);
/*  59 */     func_77637_a((CreativeTabs)BCCreativeTab.getIfPresent("gates"));
/*     */   }
/*     */   
/*     */   private static NBTTagCompound getNBT(ItemStack stack) {
/*  63 */     if (stack == null || !(stack.func_77973_b() instanceof ItemGate)) {
/*  64 */       return null;
/*     */     }
/*  66 */     return InvUtils.getItemData(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setMaterial(ItemStack stack, GateDefinition.GateMaterial material) {
/*  71 */     NBTTagCompound nbt = InvUtils.getItemData(stack);
/*  72 */     nbt.func_74774_a("mat", (byte)material.ordinal());
/*     */   }
/*     */   
/*     */   public static GateDefinition.GateMaterial getMaterial(ItemStack stack) {
/*  76 */     NBTTagCompound nbt = getNBT(stack);
/*     */     
/*  78 */     if (nbt == null) {
/*  79 */       return GateDefinition.GateMaterial.REDSTONE;
/*     */     }
/*  81 */     return GateDefinition.GateMaterial.fromOrdinal(nbt.func_74771_c("mat"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static GateDefinition.GateLogic getLogic(ItemStack stack) {
/*  86 */     NBTTagCompound nbt = getNBT(stack);
/*     */     
/*  88 */     if (nbt == null) {
/*  89 */       return GateDefinition.GateLogic.AND;
/*     */     }
/*  91 */     return GateDefinition.GateLogic.fromOrdinal(nbt.func_74771_c("logic"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setLogic(ItemStack stack, GateDefinition.GateLogic logic) {
/*  96 */     NBTTagCompound nbt = InvUtils.getItemData(stack);
/*  97 */     nbt.func_74774_a("logic", (byte)logic.ordinal());
/*     */   }
/*     */   
/*     */   public static void addGateExpansion(ItemStack stack, IGateExpansion expansion) {
/* 101 */     NBTTagCompound nbt = getNBT(stack);
/*     */     
/* 103 */     if (nbt == null) {
/*     */       return;
/*     */     }
/*     */     
/* 107 */     NBTTagList expansionList = nbt.func_150295_c("ex", 8);
/* 108 */     expansionList.func_74742_a((NBTBase)new NBTTagString(expansion.getUniqueIdentifier()));
/* 109 */     nbt.func_74782_a("ex", (NBTBase)expansionList);
/*     */   }
/*     */   
/*     */   public static boolean hasGateExpansion(ItemStack stack, IGateExpansion expansion) {
/* 113 */     NBTTagCompound nbt = getNBT(stack);
/*     */     
/* 115 */     if (nbt == null) {
/* 116 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 120 */       NBTTagList expansionList = nbt.func_150295_c("ex", 8);
/*     */       
/* 122 */       for (int i = 0; i < expansionList.func_74745_c(); i++) {
/* 123 */         String ex = expansionList.func_150307_f(i);
/*     */         
/* 125 */         if (ex.equals(expansion.getUniqueIdentifier())) {
/* 126 */           return true;
/*     */         }
/*     */       } 
/* 129 */     } catch (RuntimeException runtimeException) {}
/*     */ 
/*     */     
/* 132 */     return false;
/*     */   }
/*     */   
/*     */   public static Set<IGateExpansion> getInstalledExpansions(ItemStack stack) {
/* 136 */     Set<IGateExpansion> expansions = new HashSet<IGateExpansion>();
/* 137 */     NBTTagCompound nbt = getNBT(stack);
/*     */     
/* 139 */     if (nbt == null) {
/* 140 */       return expansions;
/*     */     }
/*     */     
/*     */     try {
/* 144 */       NBTTagList expansionList = nbt.func_150295_c("ex", 8);
/* 145 */       for (int i = 0; i < expansionList.func_74745_c(); i++) {
/* 146 */         String exTag = expansionList.func_150307_f(i);
/* 147 */         IGateExpansion ex = GateExpansions.getExpansion(exTag);
/* 148 */         if (ex != null) {
/* 149 */           expansions.add(ex);
/*     */         }
/*     */       } 
/* 152 */     } catch (RuntimeException runtimeException) {}
/*     */ 
/*     */     
/* 155 */     return expansions;
/*     */   }
/*     */   
/*     */   public static ItemStack makeGateItem(GateDefinition.GateMaterial material, GateDefinition.GateLogic logic) {
/* 159 */     ItemStack stack = new ItemStack(BuildCraftTransport.pipeGate);
/* 160 */     NBTTagCompound nbt = InvUtils.getItemData(stack);
/* 161 */     nbt.func_74774_a("mat", (byte)material.ordinal());
/* 162 */     nbt.func_74774_a("logic", (byte)logic.ordinal());
/*     */     
/* 164 */     return stack;
/*     */   }
/*     */   
/*     */   public static ItemStack makeGateItem(Gate gate) {
/* 168 */     ItemStack stack = new ItemStack(BuildCraftTransport.pipeGate);
/* 169 */     NBTTagCompound nbt = InvUtils.getItemData(stack);
/* 170 */     nbt.func_74774_a("mat", (byte)gate.material.ordinal());
/* 171 */     nbt.func_74774_a("logic", (byte)gate.logic.ordinal());
/*     */     
/* 173 */     for (IGateExpansion expansion : gate.expansions.keySet()) {
/* 174 */       addGateExpansion(stack, expansion);
/*     */     }
/*     */     
/* 177 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack stack) {
/* 182 */     return GateDefinition.getLocalizedName(getMaterial(stack), getLogic(stack));
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77653_i(ItemStack stack) {
/* 187 */     return ("" + StatCollector.func_74838_a(func_77657_g(stack))).trim();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> itemList) {
/* 194 */     for (GateDefinition.GateMaterial material : GateDefinition.GateMaterial.VALUES) {
/* 195 */       for (GateDefinition.GateLogic logic : GateDefinition.GateLogic.VALUES) {
/* 196 */         if (material != GateDefinition.GateMaterial.REDSTONE || logic != GateDefinition.GateLogic.OR) {
/*     */ 
/*     */ 
/*     */           
/* 200 */           itemList.add(makeGateItem(material, logic));
/*     */           
/* 202 */           for (IGateExpansion exp : GateExpansions.getExpansions()) {
/* 203 */             ItemStack stackExpansion = makeGateItem(material, logic);
/* 204 */             addGateExpansion(stackExpansion, exp);
/* 205 */             itemList.add(stackExpansion);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean adv) {
/* 213 */     super.func_77624_a(stack, player, list, adv);
/*     */     
/* 215 */     list.add(StringUtils.localize("tip.gate.wires"));
/* 216 */     list.add(StringUtils.localize("tip.gate.wires." + getMaterial(stack).getTag()));
/* 217 */     Set<IGateExpansion> expansions = getInstalledExpansions(stack);
/*     */     
/* 219 */     if (!expansions.isEmpty()) {
/* 220 */       list.add(StringUtils.localize("tip.gate.expansions"));
/*     */       
/* 222 */       for (IGateExpansion expansion : expansions) {
/* 223 */         list.add(expansion.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_77650_f(ItemStack stack) {
/* 230 */     return getLogic(stack).getIconItem();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister iconRegister) {
/* 236 */     for (GateDefinition.GateMaterial material : GateDefinition.GateMaterial.VALUES) {
/* 237 */       material.registerItemIcon(iconRegister);
/*     */     }
/*     */     
/* 240 */     for (GateDefinition.GateLogic logic : GateDefinition.GateLogic.VALUES) {
/* 241 */       logic.registerItemIcon(iconRegister);
/*     */     }
/*     */     
/* 244 */     for (IGateExpansion expansion : GateExpansions.getExpansions()) {
/* 245 */       expansion.registerItemOverlay(iconRegister);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public PipePluggable createPipePluggable(IPipe pipe, ForgeDirection side, ItemStack stack) {
/* 251 */     Pipe<?> realPipe = (Pipe)pipe;
/*     */     
/* 253 */     return new GatePluggable(GateFactory.makeGate(realPipe, stack, side));
/*     */   }
/*     */   
/*     */   public static ArrayList<ItemStack> getAllGates() {
/* 257 */     if (allGates == null) {
/* 258 */       allGates = new ArrayList<ItemStack>();
/* 259 */       for (GateDefinition.GateMaterial m : GateDefinition.GateMaterial.VALUES) {
/* 260 */         for (GateDefinition.GateLogic l : GateDefinition.GateLogic.VALUES) {
/* 261 */           if (m != GateDefinition.GateMaterial.REDSTONE || l != GateDefinition.GateLogic.OR)
/*     */           {
/*     */ 
/*     */             
/* 265 */             allGates.add(makeGateItem(m, l)); } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 269 */     return allGates;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gates\ItemGate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
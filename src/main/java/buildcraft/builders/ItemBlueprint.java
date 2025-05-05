/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.BuildCraftBuilders;
/*     */ import buildcraft.api.blueprints.BuildingPermission;
/*     */ import buildcraft.api.items.IBlueprintItem;
/*     */ import buildcraft.core.BCCreativeTab;
/*     */ import buildcraft.core.blueprints.Blueprint;
/*     */ import buildcraft.core.blueprints.BlueprintBase;
/*     */ import buildcraft.core.blueprints.LibraryId;
/*     */ import buildcraft.core.blueprints.Template;
/*     */ import buildcraft.core.lib.items.ItemBuildCraft;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ItemBlueprint
/*     */   extends ItemBuildCraft
/*     */   implements IBlueprintItem
/*     */ {
/*     */   public ItemBlueprint() {
/*  32 */     super((CreativeTabs)BCCreativeTab.get("main"));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName(ItemStack stack) {
/*  37 */     return NBTUtils.getItemData(stack).func_74779_i("name");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setName(ItemStack stack, String name) {
/*  42 */     NBTUtils.getItemData(stack).func_74778_a("name", name);
/*  43 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
/*  48 */     if (NBTUtils.getItemData(stack).func_74764_b("name")) {
/*  49 */       String name = NBTUtils.getItemData(stack).func_74779_i("name");
/*     */       
/*  51 */       if ("".equals(name)) {
/*  52 */         list.add(StringUtils.localize("item.blueprint.unnamed"));
/*     */       } else {
/*  54 */         list.add(name);
/*     */       } 
/*     */       
/*  57 */       list.add(StringUtils.localize("item.blueprint.author") + " " + 
/*     */           
/*  59 */           NBTUtils.getItemData(stack).func_74779_i("author"));
/*     */     } else {
/*  61 */       list.add(StringUtils.localize("item.blueprint.blank"));
/*     */     } 
/*     */     
/*  64 */     if (NBTUtils.getItemData(stack).func_74764_b("permission")) {
/*  65 */       BuildingPermission p = BuildingPermission.values()[NBTUtils.getItemData(stack).func_74771_c("permission")];
/*     */       
/*  67 */       if (p == BuildingPermission.CREATIVE_ONLY) {
/*  68 */         list.add(StringUtils.localize("item.blueprint.creative_only"));
/*  69 */       } else if (p == BuildingPermission.NONE) {
/*  70 */         list.add(StringUtils.localize("item.blueprint.no_build"));
/*     */       } 
/*     */     } 
/*     */     
/*  74 */     if (NBTUtils.getItemData(stack).func_74764_b("isComplete")) {
/*  75 */       boolean isComplete = NBTUtils.getItemData(stack).func_74767_n("isComplete");
/*     */       
/*  77 */       if (!isComplete) {
/*  78 */         list.add(StringUtils.localize("item.blueprint.incomplete"));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemStackLimit(ItemStack stack) {
/*  85 */     return NBTUtils.getItemData(stack).func_74764_b("name") ? 1 : 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String getIconType();
/*     */   
/*     */   public String[] getIconNames() {
/*  92 */     return new String[] { getIconType() + "/clean", getIconType() + "/used" };
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_77650_f(ItemStack stack) {
/*  97 */     if (!NBTUtils.getItemData(stack).func_74764_b("name")) {
/*  98 */       this.field_77791_bV = this.icons[0];
/*     */     } else {
/* 100 */       this.field_77791_bV = this.icons[1];
/*     */     } 
/*     */     
/* 103 */     return this.field_77791_bV;
/*     */   }
/*     */   
/*     */   public static boolean isContentReadable(ItemStack stack) {
/* 107 */     return (getId(stack) != null);
/*     */   }
/*     */   
/*     */   public static LibraryId getId(ItemStack stack) {
/* 111 */     NBTTagCompound nbt = NBTUtils.getItemData(stack);
/* 112 */     if (nbt == null) {
/* 113 */       return null;
/*     */     }
/* 115 */     LibraryId id = new LibraryId();
/* 116 */     id.read(nbt);
/*     */     
/* 118 */     if (BuildCraftBuilders.serverDB.exists(id)) {
/* 119 */       return id;
/*     */     }
/* 121 */     return null;
/*     */   }
/*     */   
/*     */   public static BlueprintBase loadBlueprint(ItemStack stack) {
/*     */     Blueprint blueprint;
/* 126 */     if (stack == null || stack.func_77973_b() == null || !(stack.func_77973_b() instanceof IBlueprintItem)) {
/* 127 */       return null;
/*     */     }
/*     */     
/* 130 */     LibraryId id = getId(stack);
/* 131 */     if (id == null) {
/* 132 */       return null;
/*     */     }
/*     */     
/* 135 */     NBTTagCompound nbt = BuildCraftBuilders.serverDB.load(id);
/*     */     
/* 137 */     if (((IBlueprintItem)stack.func_77973_b()).getType(stack) == IBlueprintItem.Type.TEMPLATE) {
/* 138 */       Template template = new Template();
/*     */     } else {
/* 140 */       blueprint = new Blueprint();
/*     */     } 
/* 142 */     blueprint.readFromNBT(nbt);
/* 143 */     ((BlueprintBase)blueprint).id = id;
/* 144 */     return (BlueprintBase)blueprint;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\ItemBlueprint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
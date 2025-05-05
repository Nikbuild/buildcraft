/*     */ package buildcraft.core;
/*     */ import buildcraft.core.lib.items.ItemBlockBuildCraft;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import java.io.File;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.config.Configuration;
/*     */ import net.minecraftforge.oredict.ShapelessOreRecipe;
/*     */ 
/*     */ public final class BCRegistry {
/*  16 */   public static final BCRegistry INSTANCE = new BCRegistry();
/*     */ 
/*     */   
/*     */   private Configuration regCfg;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled(String category, String name) {
/*  24 */     return this.regCfg.get(category, name, true).getBoolean();
/*     */   }
/*     */   
/*     */   public void setRegistryConfig(File f) {
/*  28 */     this.regCfg = new Configuration(f);
/*     */   }
/*     */   
/*     */   public boolean registerBlock(Block block, boolean forced) {
/*  32 */     return registerBlock(block, (Class)ItemBlockBuildCraft.class, forced);
/*     */   }
/*     */   
/*     */   public boolean isBlockEnabled(String name) {
/*  36 */     return this.regCfg.get("blocks", name, true).getBoolean();
/*     */   }
/*     */   
/*     */   public boolean isBlockEnabled(Block block) {
/*  40 */     String name = block.func_149739_a().replace("tile.", "");
/*  41 */     return isBlockEnabled(name);
/*     */   }
/*     */   
/*     */   public boolean registerBlock(Block block, Class<? extends ItemBlock> item, boolean forced) {
/*  45 */     String name = block.func_149739_a().replace("tile.", "");
/*  46 */     if (forced || isBlockEnabled(name)) {
/*  47 */       GameRegistry.registerBlock(block, item, name);
/*  48 */       return true;
/*     */     } 
/*  50 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isItemEnabled(String name) {
/*  54 */     return this.regCfg.get("items", name, true).getBoolean();
/*     */   }
/*     */   
/*     */   public boolean isItemEnabled(Item item) {
/*  58 */     String name = item.func_77658_a().replace("item.", "");
/*  59 */     return isItemEnabled(name);
/*     */   }
/*     */   
/*     */   public boolean registerItem(Item item, boolean forced) {
/*  63 */     String name = item.func_77658_a().replace("item.", "");
/*  64 */     if (forced || isItemEnabled(name)) {
/*  65 */       GameRegistry.registerItem(item, name);
/*  66 */       return true;
/*     */     } 
/*  68 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isInvalidRecipeElement(Object o) {
/*  72 */     if (o == null) {
/*  73 */       return true;
/*     */     }
/*  75 */     if (o instanceof Block && !Utils.isRegistered((Block)o)) {
/*  76 */       return true;
/*     */     }
/*  78 */     if (o instanceof Item && !Utils.isRegistered((Item)o)) {
/*  79 */       return true;
/*     */     }
/*  81 */     if (o instanceof ItemStack && !Utils.isRegistered((ItemStack)o)) {
/*  82 */       return true;
/*     */     }
/*     */     
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerTileEntity(Class<? extends TileEntity> clas, String ident) {
/*  90 */     GameRegistry.registerTileEntity(CompatHooks.INSTANCE.getTile(clas), ident);
/*     */   }
/*     */   
/*     */   public void addCraftingRecipe(ItemStack result, Object... recipe) {
/*  94 */     if (isInvalidRecipeElement(result)) {
/*     */       return;
/*     */     }
/*     */     
/*  98 */     for (Object o : recipe) {
/*  99 */       if (isInvalidRecipeElement(o)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 104 */     GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(result, recipe));
/*     */   }
/*     */   
/*     */   public void addShapelessRecipe(ItemStack result, Object... recipe) {
/* 108 */     if (isInvalidRecipeElement(result)) {
/*     */       return;
/*     */     }
/*     */     
/* 112 */     for (Object o : recipe) {
/* 113 */       if (isInvalidRecipeElement(o)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 118 */     GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(result, recipe));
/*     */   }
/*     */   
/*     */   public void save() {
/* 122 */     this.regCfg.save();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\BCRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
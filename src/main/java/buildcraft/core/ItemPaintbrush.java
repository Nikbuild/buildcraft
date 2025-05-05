/*     */ package buildcraft.core;
/*     */ 
/*     */ import buildcraft.api.blocks.IColorRemovable;
/*     */ import buildcraft.api.core.EnumColor;
/*     */ import buildcraft.core.lib.items.ItemBuildCraft;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemPaintbrush
/*     */   extends ItemBuildCraft
/*     */ {
/*     */   public ItemPaintbrush() {
/*  37 */     func_77664_n();
/*  38 */     func_77625_d(1);
/*  39 */     func_77656_e(63);
/*     */   }
/*     */   
/*     */   private int getColor(ItemStack stack) {
/*  43 */     if (!stack.func_77942_o()) {
/*  44 */       return -1;
/*     */     }
/*  46 */     NBTTagCompound compound = NBTUtils.getItemData(stack);
/*  47 */     return compound.func_74764_b("color") ? compound.func_74771_c("color") : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamage(ItemStack stack, int damage) {
/*  52 */     if (damage > func_77612_l()) {
/*  53 */       stack.func_77982_d(null);
/*  54 */       super.setDamage(stack, 0);
/*     */     } else {
/*  56 */       super.setDamage(stack, damage);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getIconNames() {
/*  62 */     String[] names = new String[17];
/*  63 */     names[0] = "paintbrush/clean";
/*  64 */     for (int i = 0; i < 16; i++) {
/*  65 */       names[1 + i] = "paintbrush/" + EnumColor.fromId(i).name().toLowerCase(Locale.ENGLISH);
/*     */     }
/*  67 */     return names;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister par1IconRegister) {
/*  73 */     super.func_94581_a(par1IconRegister);
/*     */     
/*  75 */     IIcon[] brushColors = new IIcon[16];
/*  76 */     System.arraycopy(this.icons, 1, brushColors, 0, 16);
/*  77 */     EnumColor.setIconArray(brushColors);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77650_f(ItemStack stack) {
/*  83 */     this.field_77791_bV = this.icons[(getColor(stack) + 1) % this.icons.length];
/*  84 */     return this.field_77791_bV;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77653_i(ItemStack stack) {
/*  89 */     String base = super.func_77653_i(stack);
/*  90 */     int dye = getColor(stack);
/*  91 */     if (dye >= 0) {
/*  92 */       return base + " (" + EnumColor.fromId(dye).getLocalizedName() + ")";
/*     */     }
/*  94 */     return base;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/* 100 */     int dye = getColor(stack);
/* 101 */     Block block = world.func_147439_a(x, y, z);
/*     */     
/* 103 */     if (block == null) {
/* 104 */       return false;
/*     */     }
/*     */     
/* 107 */     if (dye >= 0) {
/* 108 */       if (block.recolourBlock(world, x, y, z, ForgeDirection.getOrientation(side), 15 - dye)) {
/* 109 */         player.func_71038_i();
/* 110 */         setDamage(stack, getDamage(stack) + 1);
/* 111 */         return !world.field_72995_K;
/*     */       }
/*     */     
/*     */     }
/* 115 */     else if (block instanceof IColorRemovable && (
/* 116 */       (IColorRemovable)block).removeColorFromBlock(world, x, y, z, ForgeDirection.getOrientation(side))) {
/* 117 */       player.func_71038_i();
/* 118 */       return !world.field_72995_K;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> itemList) {
/* 129 */     itemList.add(new ItemStack((Item)this));
/* 130 */     for (int i = 0; i < 16; i++) {
/* 131 */       ItemStack stack = new ItemStack((Item)this);
/* 132 */       NBTUtils.getItemData(stack).func_74774_a("color", (byte)i);
/* 133 */       itemList.add(stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
/* 139 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\ItemPaintbrush.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
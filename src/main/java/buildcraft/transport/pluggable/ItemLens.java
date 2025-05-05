/*     */ package buildcraft.transport.pluggable;
/*     */ 
/*     */ import buildcraft.api.transport.IPipe;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.api.transport.pluggable.IPipePluggableItem;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import buildcraft.core.lib.items.ItemBuildCraft;
/*     */ import buildcraft.core.lib.utils.ColorUtils;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ public class ItemLens
/*     */   extends ItemBuildCraft
/*     */   implements IPipePluggableItem
/*     */ {
/*     */   public ItemLens() {
/*  35 */     func_77627_a(true);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77618_c(int meta, int pass) {
/*  41 */     switch (meta) {
/*     */       case 32:
/*  43 */         return (pass == 0) ? this.icons[3] : this.icons[0];
/*     */       case 33:
/*  45 */         return (pass == 0) ? this.icons[3] : this.icons[2];
/*     */     } 
/*  47 */     return this.icons[(meta >= 16) ? (1 + (pass & 0x1)) : (1 - (pass & 0x1))];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_77623_v() {
/*  54 */     return true;
/*     */   }
/*     */   
/*     */   public int getDye(ItemStack stack) {
/*  58 */     return 15 - (stack.func_77960_j() & 0xF);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_82790_a(ItemStack stack, int pass) {
/*  64 */     if (stack.func_77960_j() >= 32) {
/*  65 */       return 16777215;
/*     */     }
/*  67 */     return (pass == 0) ? ColorUtils.getRGBColor(getDye(stack)) : 16777215;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77653_i(ItemStack itemstack) {
/*  72 */     if (itemstack.func_77960_j() >= 32) {
/*  73 */       return StringUtils.localize((itemstack.func_77960_j() == 33) ? "item.Filter.name" : "item.Lens.name") + " (" + StringUtils.localize("color.clear") + ")";
/*     */     }
/*  75 */     return StringUtils.localize((itemstack.func_77960_j() >= 16) ? "item.Filter.name" : "item.Lens.name") + " (" + StringUtils.localize("color." + ColorUtils.getName(getDye(itemstack))) + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getIconNames() {
/*  86 */     return new String[] { "lens/lensFrame", "lens/transparent", "lens/filterFrame", "lens/clear" };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> itemList) {
/*  94 */     for (int i = 0; i < 34; i++) {
/*  95 */       itemList.add(new ItemStack(item, 1, i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public PipePluggable createPipePluggable(IPipe pipe, ForgeDirection side, ItemStack stack) {
/* 101 */     if (pipe.getTile().getPipeType() == IPipeTile.PipeType.ITEM) {
/* 102 */       return new LensPluggable(stack);
/*     */     }
/* 104 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pluggable\ItemLens.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
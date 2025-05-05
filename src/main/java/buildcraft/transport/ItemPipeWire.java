/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.api.transport.PipeWire;
/*    */ import buildcraft.core.lib.items.ItemBuildCraft;
/*    */ import cpw.mods.fml.common.registry.GameRegistry;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemPipeWire
/*    */   extends ItemBuildCraft
/*    */ {
/*    */   private IIcon[] icons;
/*    */   
/*    */   public ItemPipeWire() {
/* 32 */     func_77627_a(true);
/* 33 */     func_77656_e(0);
/* 34 */     setPassSneakClick(true);
/* 35 */     func_77655_b("pipeWire");
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon func_77617_a(int damage) {
/* 40 */     return this.icons[damage % this.icons.length];
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack stack) {
/* 45 */     return "item." + PipeWire.fromOrdinal(stack.func_77960_j()).getTag();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> itemList) {
/* 52 */     for (PipeWire pipeWire : PipeWire.VALUES) {
/* 53 */       itemList.add(pipeWire.getStack());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister par1IconRegister) {
/* 60 */     this.icons = new IIcon[PipeWire.VALUES.length];
/* 61 */     for (PipeWire pipeWire : PipeWire.VALUES) {
/* 62 */       this.icons[pipeWire.ordinal()] = par1IconRegister.func_94245_a("buildcrafttransport:pipeWire/" + pipeWire.getColor().toLowerCase());
/*    */     }
/*    */   }
/*    */   
/*    */   public void registerItemStacks() {
/* 67 */     for (PipeWire pipeWire : PipeWire.VALUES)
/* 68 */       GameRegistry.registerCustomItemStack(pipeWire.getTag(), pipeWire.getStack()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\ItemPipeWire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
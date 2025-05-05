/*    */ package buildcraft.api.facades;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraftforge.fml.common.event.FMLInterModComms;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FacadeAPI
/*    */ {
/*    */   public static final String IMC_MOD_TARGET = "buildcrafttransport";
/*    */   public static final String IMC_FACADE_DISABLE = "facade_disable_block";
/*    */   public static final String IMC_FACADE_CUSTOM = "facade_custom_map_block_item";
/*    */   public static final String NBT_CUSTOM_BLOCK_REG_KEY = "block_registry_name";
/*    */   public static final String NBT_CUSTOM_BLOCK_META = "block_meta";
/*    */   public static final String NBT_CUSTOM_ITEM_STACK = "item_stack";
/*    */   public static IFacadeItem facadeItem;
/*    */   public static IFacadeRegistry registry;
/*    */   
/*    */   public static void disableBlock(Block block) {
/* 26 */     FMLInterModComms.sendMessage("buildcrafttransport", "facade_disable_block", block.getRegistryName());
/*    */   }
/*    */   
/*    */   public static void mapStateToStack(IBlockState state, ItemStack stack) {
/* 30 */     NBTTagCompound nbt = new NBTTagCompound();
/* 31 */     nbt.func_74778_a("block_registry_name", state.func_177230_c().getRegistryName().toString());
/* 32 */     nbt.func_74768_a("block_meta", state.func_177230_c().func_176201_c(state));
/* 33 */     nbt.func_74782_a("item_stack", (NBTBase)stack.serializeNBT());
/* 34 */     FMLInterModComms.sendMessage("buildcrafttransport", "facade_custom_map_block_item", nbt);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\facades\FacadeAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
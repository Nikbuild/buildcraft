/*    */ package buildcraft.api.blueprints;
/*    */ 
/*    */ import buildcraft.api.core.BuildCraftAPI;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
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
/*    */ 
/*    */ public class SchematicMask
/*    */   extends SchematicBlockBase
/*    */ {
/*    */   public boolean isConcrete = true;
/*    */   
/*    */   public SchematicMask() {}
/*    */   
/*    */   public SchematicMask(boolean isConcrete) {
/* 32 */     this.isConcrete = isConcrete;
/*    */   }
/*    */ 
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 37 */     if (this.isConcrete) {
/* 38 */       if (stacks.size() == 0 || !BuildCraftAPI.isSoftBlock(context.world(), x, y, z)) {
/*    */         return;
/*    */       }
/* 41 */       ItemStack stack = stacks.getFirst();
/* 42 */       EntityPlayer player = BuildCraftAPI.proxy.getBuildCraftPlayer((WorldServer)context.world()).get();
/*    */ 
/*    */ 
/*    */       
/* 46 */       context.world().func_147465_d(x, y, z, Blocks.field_150350_a, 0, 3);
/*    */ 
/*    */       
/* 49 */       ForgeDirection dir = ForgeDirection.DOWN;
/* 50 */       while (dir != ForgeDirection.UNKNOWN && BuildCraftAPI.isSoftBlock(context.world(), x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
/* 51 */         dir = ForgeDirection.getOrientation(dir.ordinal() + 1);
/*    */       }
/*    */       
/* 54 */       stack.func_77943_a(player, context.world(), x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite().ordinal(), 0.0F, 0.0F, 0.0F);
/*    */     } else {
/*    */       
/* 57 */       context.world().func_147465_d(x, y, z, Blocks.field_150350_a, 0, 3);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/* 63 */     if (this.isConcrete) {
/* 64 */       return !BuildCraftAPI.getWorldProperty("replaceable").get(context.world(), x, y, z);
/*    */     }
/* 66 */     return BuildCraftAPI.getWorldProperty("replaceable").get(context.world(), x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 72 */     nbt.func_74757_a("isConcrete", this.isConcrete);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 77 */     this.isConcrete = nbt.func_74767_n("isConcrete");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\blueprints\SchematicMask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
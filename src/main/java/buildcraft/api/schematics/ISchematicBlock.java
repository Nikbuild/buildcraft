/*    */ package buildcraft.api.schematics;
/*    */ 
/*    */ import buildcraft.api.core.InvalidInputDataException;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.Rotation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ISchematicBlock
/*    */ {
/*    */   void init(SchematicBlockContext paramSchematicBlockContext);
/*    */   
/*    */   default boolean isAir() {
/* 23 */     return false;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   default Set<BlockPos> getRequiredBlockOffsets() {
/* 28 */     return Collections.emptySet();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   default List<ItemStack> computeRequiredItems() {
/* 33 */     return Collections.emptyList();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   default List<FluidStack> computeRequiredFluids() {
/* 38 */     return Collections.emptyList();
/*    */   }
/*    */   
/*    */   ISchematicBlock getRotated(Rotation paramRotation);
/*    */   
/*    */   boolean canBuild(World paramWorld, BlockPos paramBlockPos);
/*    */   
/*    */   default boolean isReadyToBuild(World world, BlockPos blockPos) {
/* 46 */     return true;
/*    */   }
/*    */   
/*    */   boolean build(World paramWorld, BlockPos paramBlockPos);
/*    */   
/*    */   boolean buildWithoutChecks(World paramWorld, BlockPos paramBlockPos);
/*    */   
/*    */   boolean isBuilt(World paramWorld, BlockPos paramBlockPos);
/*    */   
/*    */   NBTTagCompound serializeNBT();
/*    */   
/*    */   void deserializeNBT(NBTTagCompound paramNBTTagCompound) throws InvalidInputDataException;
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\schematics\ISchematicBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
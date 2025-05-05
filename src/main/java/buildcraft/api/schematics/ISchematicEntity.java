/*    */ package buildcraft.api.schematics;
/*    */ 
/*    */ import buildcraft.api.core.InvalidInputDataException;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.Rotation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ISchematicEntity
/*    */ {
/*    */   void init(SchematicEntityContext paramSchematicEntityContext);
/*    */   
/*    */   Vec3d getPos();
/*    */   
/*    */   @Nonnull
/*    */   default List<ItemStack> computeRequiredItems() {
/* 27 */     return Collections.emptyList();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   default List<FluidStack> computeRequiredFluids() {
/* 32 */     return Collections.emptyList();
/*    */   }
/*    */   
/*    */   ISchematicEntity getRotated(Rotation paramRotation);
/*    */   
/*    */   Entity build(World paramWorld, BlockPos paramBlockPos);
/*    */   
/*    */   Entity buildWithoutChecks(World paramWorld, BlockPos paramBlockPos);
/*    */   
/*    */   NBTTagCompound serializeNBT();
/*    */   
/*    */   void deserializeNBT(NBTTagCompound paramNBTTagCompound) throws InvalidInputDataException;
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\schematics\ISchematicEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
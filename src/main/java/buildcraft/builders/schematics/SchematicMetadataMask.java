/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.MappingRegistry;
/*    */ import buildcraft.api.blueprints.SchematicBlock;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicMetadataMask
/*    */   extends SchematicBlock
/*    */ {
/*    */   private final int mask;
/*    */   
/*    */   public SchematicMetadataMask(int mask) {
/* 22 */     this.mask = mask;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
/* 27 */     super.initializeFromObjectAt(context, x, y, z);
/* 28 */     this.meta &= this.mask;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 33 */     super.readSchematicFromNBT(nbt, registry);
/* 34 */     this.meta &= this.mask;
/*    */   }
/*    */ 
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {
/* 39 */     if (this.block != null) {
/* 40 */       this.storedRequirements = new ItemStack[1];
/* 41 */       this.storedRequirements[0] = new ItemStack(this.block, 1, this.meta & this.mask);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/* 47 */     return (this.block == context.world().func_147439_a(x, y, z) && (this.meta & this.mask) == (context.world().func_72805_g(x, y, z) & this.mask));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicMetadataMask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
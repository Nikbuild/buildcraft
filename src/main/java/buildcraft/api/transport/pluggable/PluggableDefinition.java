/*    */ package buildcraft.api.transport.pluggable;
/*    */ 
/*    */ import buildcraft.api.transport.pipe.IPipeHolder;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PluggableDefinition
/*    */ {
/*    */   public final ResourceLocation identifier;
/*    */   public final IPluggableNetLoader loader;
/*    */   public final IPluggableNbtReader reader;
/*    */   @Nullable
/*    */   public final IPluggableCreator creator;
/*    */   
/*    */   public PluggableDefinition(ResourceLocation identifier, IPluggableNbtReader reader, IPluggableNetLoader loader) {
/* 22 */     this.identifier = identifier;
/* 23 */     this.reader = reader;
/* 24 */     this.loader = loader;
/* 25 */     this.creator = null;
/*    */   }
/*    */   
/*    */   public PluggableDefinition(ResourceLocation identifier, @Nullable IPluggableCreator creator) {
/* 29 */     this.identifier = identifier;
/* 30 */     this.reader = creator;
/* 31 */     this.loader = creator;
/* 32 */     this.creator = creator;
/*    */   }
/*    */   
/*    */   public PipePluggable readFromNbt(IPipeHolder holder, EnumFacing side, NBTTagCompound nbt) {
/* 36 */     return this.reader.readFromNbt(this, holder, side, nbt);
/*    */   }
/*    */   
/*    */   public PipePluggable loadFromBuffer(IPipeHolder holder, EnumFacing side, PacketBuffer buffer) {
/* 40 */     return this.loader.loadFromBuffer(this, holder, side, buffer);
/*    */   }
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
/*    */   @FunctionalInterface
/*    */   public static interface IPluggableCreator
/*    */     extends IPluggableNbtReader, IPluggableNetLoader
/*    */   {
/*    */     default PipePluggable loadFromBuffer(PluggableDefinition definition, IPipeHolder holder, EnumFacing side, PacketBuffer buffer) {
/* 59 */       return createSimplePluggable(definition, holder, side);
/*    */     }
/*    */ 
/*    */     
/*    */     default PipePluggable readFromNbt(PluggableDefinition definition, IPipeHolder holder, EnumFacing side, NBTTagCompound nbt) {
/* 64 */       return createSimplePluggable(definition, holder, side);
/*    */     }
/*    */     
/*    */     PipePluggable createSimplePluggable(PluggableDefinition param1PluggableDefinition, IPipeHolder param1IPipeHolder, EnumFacing param1EnumFacing);
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface IPluggableNetLoader {
/*    */     PipePluggable loadFromBuffer(PluggableDefinition param1PluggableDefinition, IPipeHolder param1IPipeHolder, EnumFacing param1EnumFacing, PacketBuffer param1PacketBuffer);
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface IPluggableNbtReader {
/*    */     PipePluggable readFromNbt(PluggableDefinition param1PluggableDefinition, IPipeHolder param1IPipeHolder, EnumFacing param1EnumFacing, NBTTagCompound param1NBTTagCompound);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pluggable\PluggableDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
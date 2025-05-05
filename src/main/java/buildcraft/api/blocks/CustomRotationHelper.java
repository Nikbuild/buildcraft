/*    */ package buildcraft.api.blocks;
/*    */ 
/*    */ import buildcraft.api.core.BCDebugging;
/*    */ import buildcraft.api.core.BCLog;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum CustomRotationHelper
/*    */ {
/* 20 */   INSTANCE;
/*    */   private final Map<Block, List<ICustomRotationHandler>> handlers;
/*    */   private static final boolean DEBUG;
/*    */   
/*    */   CustomRotationHelper()
/*    */   {
/* 26 */     this.handlers = Maps.newIdentityHashMap();
/*    */   }
/*    */   public void registerHandlerForAll(Class<? extends Block> blockClass, ICustomRotationHandler handler) {
/* 29 */     for (Block block : Block.field_149771_c) {
/* 30 */       Class<? extends Block> foundClass = (Class)block.getClass();
/* 31 */       if (blockClass.isAssignableFrom(foundClass)) {
/* 32 */         if (DEBUG) {
/* 33 */           BCLog.logger.info("[api.rotation] Found an assignable block " + block.getRegistryName() + " (" + foundClass + ") for " + blockClass);
/*    */         }
/* 35 */         registerHandlerInternal(block, handler);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void registerHandler(Block block, ICustomRotationHandler handler) {
/* 41 */     if (registerHandlerInternal(block, handler)) {
/* 42 */       if (DEBUG) {
/* 43 */         BCLog.logger.info("[api.rotation] Setting a rotation handler for block " + block.getRegistryName());
/*    */       }
/* 45 */     } else if (DEBUG) {
/* 46 */       BCLog.logger.info("[api.rotation] Adding another rotation handler for block " + block.getRegistryName());
/*    */     } 
/*    */   } static {
/*    */     DEBUG = BCDebugging.shouldDebugLog("api.rotation");
/*    */   } private boolean registerHandlerInternal(Block block, ICustomRotationHandler handler) {
/* 51 */     if (!this.handlers.containsKey(block)) {
/* 52 */       List<ICustomRotationHandler> forBlock = Lists.newArrayList();
/* 53 */       forBlock.add(handler);
/* 54 */       this.handlers.put(block, forBlock);
/* 55 */       return true;
/*    */     } 
/* 57 */     ((List<ICustomRotationHandler>)this.handlers.get(block)).add(handler);
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumActionResult attemptRotateBlock(World world, BlockPos pos, IBlockState state, EnumFacing sideWrenched) {
/* 63 */     Block block = state.func_177230_c();
/* 64 */     if (block instanceof ICustomRotationHandler) {
/* 65 */       return ((ICustomRotationHandler)block).attemptRotation(world, pos, state, sideWrenched);
/*    */     }
/* 67 */     if (!this.handlers.containsKey(block)) return EnumActionResult.PASS; 
/* 68 */     for (ICustomRotationHandler handler : this.handlers.get(block)) {
/* 69 */       EnumActionResult result = handler.attemptRotation(world, pos, state, sideWrenched);
/* 70 */       if (result != EnumActionResult.PASS) {
/* 71 */         return result;
/*    */       }
/*    */     } 
/* 74 */     return EnumActionResult.PASS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\blocks\CustomRotationHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
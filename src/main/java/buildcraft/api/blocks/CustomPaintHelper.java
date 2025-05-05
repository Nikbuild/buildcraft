/*     */ package buildcraft.api.blocks;
/*     */ 
/*     */ import buildcraft.api.core.BCDebugging;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum CustomPaintHelper
/*     */ {
/*  26 */   INSTANCE;
/*     */   private final List<ICustomPaintHandler> allHandlers;
/*     */   private final Map<Block, List<ICustomPaintHandler>> handlers;
/*     */   private static final boolean DEBUG;
/*     */   
/*     */   CustomPaintHelper() {
/*  32 */     this.handlers = Maps.newIdentityHashMap();
/*  33 */     this.allHandlers = Lists.newArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerHandlerForAll(ICustomPaintHandler handler) {
/*  38 */     if (DEBUG) {
/*  39 */       BCLog.logger.info("[api.painting] Adding a paint handler for ALL blocks (" + handler.getClass() + ")");
/*     */     }
/*  41 */     this.allHandlers.add(handler);
/*     */   } static {
/*     */     DEBUG = BCDebugging.shouldDebugLog("api.painting");
/*     */   }
/*     */   public void registerHandlerForAll(Class<? extends Block> blockClass, ICustomPaintHandler handler) {
/*  46 */     for (Block block : Block.field_149771_c) {
/*  47 */       Class<? extends Block> foundClass = (Class)block.getClass();
/*  48 */       if (blockClass.isAssignableFrom(foundClass)) {
/*  49 */         if (DEBUG) {
/*  50 */           BCLog.logger.info("[api.painting] Found an assignable block " + block.getRegistryName() + " (" + foundClass + ") for " + blockClass);
/*     */         }
/*  52 */         registerHandlerInternal(block, handler);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerHandler(Block block, ICustomPaintHandler handler) {
/*  58 */     if (registerHandlerInternal(block, handler)) {
/*  59 */       if (DEBUG) {
/*  60 */         BCLog.logger.info("[api.painting] Setting a paint handler for block " + block.getRegistryName() + "(" + handler.getClass() + ")");
/*     */       }
/*  62 */     } else if (DEBUG) {
/*  63 */       BCLog.logger.info("[api.painting] Adding another paint handler for block " + block.getRegistryName() + "(" + handler.getClass() + ")");
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean registerHandlerInternal(Block block, ICustomPaintHandler handler) {
/*  68 */     if (!this.handlers.containsKey(block)) {
/*  69 */       List<ICustomPaintHandler> forBlock = Lists.newArrayList();
/*  70 */       forBlock.add(handler);
/*  71 */       this.handlers.put(block, forBlock);
/*  72 */       return true;
/*     */     } 
/*  74 */     ((List<ICustomPaintHandler>)this.handlers.get(block)).add(handler);
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult attemptPaintBlock(World world, BlockPos pos, IBlockState state, Vec3d hitPos, @Nullable EnumFacing hitSide, @Nullable EnumDyeColor paint) {
/*  81 */     Block block = state.func_177230_c();
/*  82 */     if (block instanceof ICustomPaintHandler) {
/*  83 */       return ((ICustomPaintHandler)block).attemptPaint(world, pos, state, hitPos, hitSide, paint);
/*     */     }
/*  85 */     List<ICustomPaintHandler> custom = this.handlers.get(block);
/*  86 */     if (custom == null || custom.isEmpty()) {
/*  87 */       return defaultAttemptPaint(world, pos, state, hitPos, hitSide, paint);
/*     */     }
/*  89 */     for (ICustomPaintHandler handler : custom) {
/*  90 */       EnumActionResult result = handler.attemptPaint(world, pos, state, hitPos, hitSide, paint);
/*  91 */       if (result != EnumActionResult.PASS) {
/*  92 */         return result;
/*     */       }
/*     */     } 
/*  95 */     return defaultAttemptPaint(world, pos, state, hitPos, hitSide, paint);
/*     */   }
/*     */   
/*     */   private EnumActionResult defaultAttemptPaint(World world, BlockPos pos, IBlockState state, Vec3d hitPos, EnumFacing hitSide, @Nullable EnumDyeColor paint) {
/*  99 */     for (ICustomPaintHandler handler : this.allHandlers) {
/* 100 */       EnumActionResult result = handler.attemptPaint(world, pos, state, hitPos, hitSide, paint);
/* 101 */       if (result != EnumActionResult.PASS) {
/* 102 */         return result;
/*     */       }
/*     */     } 
/* 105 */     if (paint == null) {
/* 106 */       return EnumActionResult.FAIL;
/*     */     }
/* 108 */     Block b = state.func_177230_c();
/* 109 */     if (b.recolorBlock(world, pos, hitSide, paint)) {
/* 110 */       return EnumActionResult.SUCCESS;
/*     */     }
/* 112 */     return EnumActionResult.FAIL;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\blocks\CustomPaintHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
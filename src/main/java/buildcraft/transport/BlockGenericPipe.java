/*      */ package buildcraft.transport;
/*      */ 
/*      */ import buildcraft.BuildCraftTransport;
/*      */ import buildcraft.api.blocks.IColorRemovable;
/*      */ import buildcraft.api.core.BCLog;
/*      */ import buildcraft.api.core.BlockIndex;
/*      */ import buildcraft.api.transport.IPipe;
/*      */ import buildcraft.api.transport.IPipeTile;
/*      */ import buildcraft.api.transport.PipeWire;
/*      */ import buildcraft.api.transport.pluggable.IPipePluggableItem;
/*      */ import buildcraft.api.transport.pluggable.PipePluggable;
/*      */ import buildcraft.core.BCCreativeTab;
/*      */ import buildcraft.core.lib.block.BlockBuildCraft;
/*      */ import buildcraft.core.lib.utils.MatrixTranformations;
/*      */ import buildcraft.core.lib.utils.Utils;
/*      */ import buildcraft.core.proxy.CoreProxy;
/*      */ import buildcraft.transport.render.PipeRendererWorld;
/*      */ import cpw.mods.fml.common.eventhandler.Event;
/*      */ import cpw.mods.fml.common.registry.GameRegistry;
/*      */ import cpw.mods.fml.relauncher.Side;
/*      */ import cpw.mods.fml.relauncher.SideOnly;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.particle.EffectRenderer;
/*      */ import net.minecraft.client.particle.EntityDiggingFX;
/*      */ import net.minecraft.client.particle.EntityFX;
/*      */ import net.minecraft.client.renderer.texture.IIconRegister;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.IIcon;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraftforge.common.MinecraftForge;
/*      */ import net.minecraftforge.common.util.BlockSnapshot;
/*      */ import net.minecraftforge.common.util.ForgeDirection;
/*      */ import net.minecraftforge.event.world.BlockEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BlockGenericPipe
/*      */   extends BlockBuildCraft
/*      */   implements IColorRemovable
/*      */ {
/*   72 */   public static Map<Item, Class<? extends Pipe<?>>> pipes = new HashMap<Item, Class<? extends Pipe<?>>>();
/*   73 */   public static Map<BlockIndex, Pipe<?>> pipeRemoved = new HashMap<BlockIndex, Pipe<?>>();
/*      */   
/*   75 */   private static long lastRemovedDate = -1L;
/*      */   
/*   77 */   private static final ForgeDirection[] DIR_VALUES = ForgeDirection.values();
/*      */   
/*      */   public enum Part {
/*   80 */     Pipe,
/*   81 */     Pluggable;
/*      */   }
/*      */   
/*      */   public static class RaytraceResult {
/*      */     public final BlockGenericPipe.Part hitPart;
/*      */     public final MovingObjectPosition movingObjectPosition;
/*      */     public final AxisAlignedBB boundingBox;
/*      */     public final ForgeDirection sideHit;
/*      */     
/*      */     RaytraceResult(BlockGenericPipe.Part hitPart, MovingObjectPosition movingObjectPosition, AxisAlignedBB boundingBox, ForgeDirection side) {
/*   91 */       this.hitPart = hitPart;
/*   92 */       this.movingObjectPosition = movingObjectPosition;
/*   93 */       this.boundingBox = boundingBox;
/*   94 */       this.sideHit = side;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*   99 */       return String.format("RayTraceResult: %s, %s", new Object[] { (this.hitPart == null) ? "null" : this.hitPart.name(), (this.boundingBox == null) ? "null" : this.boundingBox.toString() });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockGenericPipe() {
/*  105 */     super(Material.field_151592_s);
/*  106 */     func_149647_a(null);
/*      */   }
/*      */ 
/*      */   
/*      */   public float func_149712_f(World world, int x, int y, int z) {
/*  111 */     return BuildCraftTransport.pipeDurability;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int func_149645_b() {
/*  117 */     return TransportProxy.pipeModel;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canRenderInPass(int pass) {
/*  122 */     PipeRendererWorld.renderPass = pass;
/*  123 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_149701_w() {
/*  128 */     return 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_149662_c() {
/*  133 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_149686_d() {
/*  138 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
/*  143 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/*  148 */     TileEntity tile = world.func_147438_o(x, y, z);
/*      */     
/*  150 */     if (tile instanceof ISolidSideTile) {
/*  151 */       return ((ISolidSideTile)tile).isSolidOnSide(side);
/*      */     }
/*      */     
/*  154 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_149721_r() {
/*  159 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_149743_a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity) {
/*  165 */     func_149676_a(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
/*  166 */     super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */     
/*  168 */     TileEntity tile1 = world.func_147438_o(i, j, k);
/*  169 */     if (tile1 instanceof TileGenericPipe) {
/*  170 */       TileGenericPipe tileG = (TileGenericPipe)tile1;
/*      */       
/*  172 */       if (tileG.isPipeConnected(ForgeDirection.WEST)) {
/*  173 */         func_149676_a(0.0F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
/*  174 */         super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */       } 
/*      */       
/*  177 */       if (tileG.isPipeConnected(ForgeDirection.EAST)) {
/*  178 */         func_149676_a(0.25F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
/*  179 */         super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */       } 
/*      */       
/*  182 */       if (tileG.isPipeConnected(ForgeDirection.DOWN)) {
/*  183 */         func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 0.75F, 0.75F);
/*  184 */         super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */       } 
/*      */       
/*  187 */       if (tileG.isPipeConnected(ForgeDirection.UP)) {
/*  188 */         func_149676_a(0.25F, 0.25F, 0.25F, 0.75F, 1.0F, 0.75F);
/*  189 */         super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */       } 
/*      */       
/*  192 */       if (tileG.isPipeConnected(ForgeDirection.NORTH)) {
/*  193 */         func_149676_a(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.75F);
/*  194 */         super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */       } 
/*      */       
/*  197 */       if (tileG.isPipeConnected(ForgeDirection.SOUTH)) {
/*  198 */         func_149676_a(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 1.0F);
/*  199 */         super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */       } 
/*      */       
/*  202 */       float facadeThickness = 0.125F;
/*      */       
/*  204 */       if (tileG.hasEnabledFacade(ForgeDirection.EAST)) {
/*  205 */         func_149676_a(1.0F - facadeThickness, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  206 */         super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */       } 
/*      */       
/*  209 */       if (tileG.hasEnabledFacade(ForgeDirection.WEST)) {
/*  210 */         func_149676_a(0.0F, 0.0F, 0.0F, facadeThickness, 1.0F, 1.0F);
/*  211 */         super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */       } 
/*      */       
/*  214 */       if (tileG.hasEnabledFacade(ForgeDirection.UP)) {
/*  215 */         func_149676_a(0.0F, 1.0F - facadeThickness, 0.0F, 1.0F, 1.0F, 1.0F);
/*  216 */         super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */       } 
/*      */       
/*  219 */       if (tileG.hasEnabledFacade(ForgeDirection.DOWN)) {
/*  220 */         func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, facadeThickness, 1.0F);
/*  221 */         super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */       } 
/*      */       
/*  224 */       if (tileG.hasEnabledFacade(ForgeDirection.SOUTH)) {
/*  225 */         func_149676_a(0.0F, 0.0F, 1.0F - facadeThickness, 1.0F, 1.0F, 1.0F);
/*  226 */         super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */       } 
/*      */       
/*  229 */       if (tileG.hasEnabledFacade(ForgeDirection.NORTH)) {
/*  230 */         func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, facadeThickness);
/*  231 */         super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, par7Entity);
/*      */       } 
/*      */     } 
/*  234 */     func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public AxisAlignedBB func_149633_g(World world, int x, int y, int z) {
/*  240 */     RaytraceResult rayTraceResult = doRayTrace(world, x, y, z, (EntityPlayer)(Minecraft.func_71410_x()).field_71439_g);
/*      */     
/*  242 */     if (rayTraceResult != null && rayTraceResult.boundingBox != null) {
/*  243 */       float scale; AxisAlignedBB box = rayTraceResult.boundingBox;
/*  244 */       switch (rayTraceResult.hitPart) {
/*      */         case Pluggable:
/*  246 */           scale = 0.001F;
/*  247 */           box = box.func_72314_b(scale, scale, scale);
/*      */           break;
/*      */         
/*      */         case Pipe:
/*  251 */           scale = 0.08F;
/*  252 */           box = box.func_72314_b(scale, scale, scale);
/*      */           break;
/*      */       } 
/*      */       
/*  256 */       return box.func_72325_c(x, y, z);
/*      */     } 
/*  258 */     return super.func_149633_g(world, x, y, z).func_72314_b(-0.8500000238418579D, -0.8500000238418579D, -0.8500000238418579D);
/*      */   }
/*      */ 
/*      */   
/*      */   public MovingObjectPosition func_149731_a(World world, int x, int y, int z, Vec3 origin, Vec3 direction) {
/*  263 */     RaytraceResult raytraceResult = doRayTrace(world, x, y, z, origin, direction);
/*      */     
/*  265 */     if (raytraceResult == null) {
/*  266 */       return null;
/*      */     }
/*  268 */     return raytraceResult.movingObjectPosition;
/*      */   }
/*      */ 
/*      */   
/*      */   public RaytraceResult doRayTrace(World world, int x, int y, int z, EntityPlayer player) {
/*  273 */     double reachDistance = 5.0D;
/*      */     
/*  275 */     if (player instanceof EntityPlayerMP) {
/*  276 */       reachDistance = ((EntityPlayerMP)player).field_71134_c.getBlockReachDistance();
/*      */     }
/*      */     
/*  279 */     double eyeHeight = world.field_72995_K ? (player.func_70047_e() - player.getDefaultEyeHeight()) : player.func_70047_e();
/*  280 */     Vec3 lookVec = player.func_70040_Z();
/*  281 */     Vec3 origin = Vec3.func_72443_a(player.field_70165_t, player.field_70163_u + eyeHeight, player.field_70161_v);
/*  282 */     Vec3 direction = origin.func_72441_c(lookVec.field_72450_a * reachDistance, lookVec.field_72448_b * reachDistance, lookVec.field_72449_c * reachDistance);
/*      */     
/*  284 */     return doRayTrace(world, x, y, z, origin, direction);
/*      */   }
/*      */   private RaytraceResult doRayTrace(World world, int x, int y, int z, Vec3 origin, Vec3 direction) {
/*      */     Part hitPart;
/*  288 */     Pipe<?> pipe = getPipe((IBlockAccess)world, x, y, z);
/*      */     
/*  290 */     if (!isValid(pipe)) {
/*  291 */       return null;
/*      */     }
/*      */     
/*  294 */     TileGenericPipe tileG = pipe.container;
/*      */     
/*  296 */     if (tileG == null) {
/*  297 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  304 */     MovingObjectPosition[] hits = new MovingObjectPosition[31];
/*  305 */     AxisAlignedBB[] boxes = new AxisAlignedBB[31];
/*  306 */     ForgeDirection[] sideHit = new ForgeDirection[31];
/*  307 */     Arrays.fill((Object[])sideHit, ForgeDirection.UNKNOWN);
/*      */ 
/*      */ 
/*      */     
/*  311 */     for (ForgeDirection side : DIR_VALUES) {
/*  312 */       if (side == ForgeDirection.UNKNOWN || tileG.isPipeConnected(side)) {
/*  313 */         AxisAlignedBB bb = getPipeBoundingBox(side);
/*  314 */         setBlockBounds(bb);
/*  315 */         boxes[side.ordinal()] = bb;
/*  316 */         hits[side.ordinal()] = super.func_149731_a(world, x, y, z, origin, direction);
/*  317 */         sideHit[side.ordinal()] = side;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  323 */     for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/*  324 */       if (tileG.getPipePluggable(side) != null) {
/*  325 */         AxisAlignedBB bb = tileG.getPipePluggable(side).getBoundingBox(side);
/*  326 */         setBlockBounds(bb);
/*  327 */         boxes[7 + side.ordinal()] = bb;
/*  328 */         hits[7 + side.ordinal()] = super.func_149731_a(world, x, y, z, origin, direction);
/*  329 */         sideHit[7 + side.ordinal()] = side;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  337 */     double minLengthSquared = Double.POSITIVE_INFINITY;
/*  338 */     int minIndex = -1;
/*      */     
/*  340 */     for (int i = 0; i < hits.length; i++) {
/*  341 */       MovingObjectPosition hit = hits[i];
/*  342 */       if (hit != null) {
/*      */ 
/*      */ 
/*      */         
/*  346 */         double lengthSquared = hit.field_72307_f.func_72436_e(origin);
/*      */         
/*  348 */         if (lengthSquared < minLengthSquared) {
/*  349 */           minLengthSquared = lengthSquared;
/*  350 */           minIndex = i;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  356 */     func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/*  358 */     if (minIndex == -1) {
/*  359 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  363 */     if (minIndex < 7) {
/*  364 */       hitPart = Part.Pipe;
/*      */     } else {
/*  366 */       hitPart = Part.Pluggable;
/*      */     } 
/*      */     
/*  369 */     return new RaytraceResult(hitPart, hits[minIndex], boxes[minIndex], sideHit[minIndex]);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setBlockBounds(AxisAlignedBB bb) {
/*  374 */     func_149676_a((float)bb.field_72340_a, (float)bb.field_72338_b, (float)bb.field_72339_c, (float)bb.field_72336_d, (float)bb.field_72337_e, (float)bb.field_72334_f);
/*      */   }
/*      */   
/*      */   private AxisAlignedBB getPipeBoundingBox(ForgeDirection side) {
/*  378 */     float min = 0.25F;
/*  379 */     float max = 0.75F;
/*      */     
/*  381 */     if (side == ForgeDirection.UNKNOWN) {
/*  382 */       return AxisAlignedBB.func_72330_a(min, min, min, max, max, max);
/*      */     }
/*      */     
/*  385 */     float[][] bounds = new float[3][2];
/*      */     
/*  387 */     bounds[0][0] = min;
/*  388 */     bounds[0][1] = max;
/*      */     
/*  390 */     bounds[1][0] = 0.0F;
/*  391 */     bounds[1][1] = min;
/*      */     
/*  393 */     bounds[2][0] = min;
/*  394 */     bounds[2][1] = max;
/*      */     
/*  396 */     MatrixTranformations.transform(bounds, side);
/*  397 */     return AxisAlignedBB.func_72330_a(bounds[0][0], bounds[1][0], bounds[2][0], bounds[0][1], bounds[1][1], bounds[2][1]);
/*      */   }
/*      */   
/*      */   public static void removePipe(Pipe<?> pipe) {
/*  401 */     if (!isValid(pipe)) {
/*      */       return;
/*      */     }
/*      */     
/*  405 */     World world = pipe.container.func_145831_w();
/*      */     
/*  407 */     if (world == null) {
/*      */       return;
/*      */     }
/*      */     
/*  411 */     int x = pipe.container.field_145851_c;
/*  412 */     int y = pipe.container.field_145848_d;
/*  413 */     int z = pipe.container.field_145849_e;
/*      */     
/*  415 */     if (lastRemovedDate != world.func_82737_E()) {
/*  416 */       lastRemovedDate = world.func_82737_E();
/*  417 */       pipeRemoved.clear();
/*      */     } 
/*      */     
/*  420 */     pipeRemoved.put(new BlockIndex(x, y, z), pipe);
/*  421 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*  422 */       Pipe<?> tpipe = getPipe((IBlockAccess)world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
/*  423 */       if (tpipe != null) {
/*  424 */         tpipe.scheduleWireUpdate();
/*      */       }
/*      */     } 
/*  427 */     world.func_147475_p(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_149749_a(World world, int x, int y, int z, Block block, int par6) {
/*  432 */     Utils.preDestroyBlock(world, x, y, z);
/*  433 */     removePipe(getPipe((IBlockAccess)world, x, y, z));
/*  434 */     super.func_149749_a(world, x, y, z, block, par6);
/*      */   }
/*      */ 
/*      */   
/*      */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
/*  439 */     if (world.field_72995_K) {
/*  440 */       return null;
/*      */     }
/*      */     
/*  443 */     ArrayList<ItemStack> list = new ArrayList<ItemStack>();
/*  444 */     Pipe<?> pipe = getPipe((IBlockAccess)world, x, y, z);
/*      */     
/*  446 */     if (pipe == null) {
/*  447 */       pipe = pipeRemoved.get(new BlockIndex(x, y, z));
/*      */     }
/*      */     
/*  450 */     if (pipe != null && 
/*  451 */       pipe.item != null) {
/*  452 */       list.add(new ItemStack(pipe.item, 1, pipe.container.getItemMetadata()));
/*  453 */       list.addAll(pipe.computeItemDrop());
/*  454 */       list.addAll(pipe.getDroppedItems());
/*      */     } 
/*      */     
/*  457 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public TileEntity func_149915_a(World world, int metadata) {
/*  462 */     return new TileGenericPipe();
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_149690_a(World world, int i, int j, int k, int l, float f, int dmg) {
/*  467 */     if (world.field_72995_K) {
/*      */       return;
/*      */     }
/*  470 */     Pipe<?> pipe = getPipe((IBlockAccess)world, i, j, k);
/*      */     
/*  472 */     if (pipe == null) {
/*  473 */       pipe = pipeRemoved.get(new BlockIndex(i, j, k));
/*      */     }
/*      */     
/*  476 */     if (pipe != null) {
/*  477 */       Item k1 = pipe.item;
/*      */       
/*  479 */       if (k1 != null) {
/*  480 */         pipe.dropContents();
/*  481 */         for (ItemStack is : pipe.computeItemDrop()) {
/*  482 */           func_149642_a(world, i, j, k, is);
/*      */         }
/*  484 */         func_149642_a(world, i, j, k, new ItemStack(k1, 1, pipe.container.getItemMetadata()));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Item func_149650_a(int meta, Random rand, int dmg) {
/*  492 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
/*  498 */     EntityPlayer clientPlayer = CoreProxy.proxy.getClientPlayer();
/*  499 */     if (clientPlayer != null) {
/*  500 */       return getPickBlock(target, world, x, y, z, clientPlayer);
/*      */     }
/*  502 */     return new ItemStack((getPipe((IBlockAccess)world, x, y, z)).item, 1, (getPipe((IBlockAccess)world, x, y, z)).container.getItemMetadata());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
/*  508 */     RaytraceResult rayTraceResult = doRayTrace(world, x, y, z, player);
/*      */     
/*  510 */     if (rayTraceResult != null && rayTraceResult.boundingBox != null) {
/*  511 */       Pipe<?> pipe; PipePluggable pluggable; ItemStack[] drops; switch (rayTraceResult.hitPart) {
/*      */         case Pluggable:
/*  513 */           pipe = getPipe((IBlockAccess)world, x, y, z);
/*  514 */           pluggable = pipe.container.getPipePluggable(rayTraceResult.sideHit);
/*  515 */           drops = pluggable.getDropItems(pipe.container);
/*  516 */           if (drops != null && drops.length > 0) {
/*  517 */             return drops[0];
/*      */           }
/*      */         
/*      */         case Pipe:
/*  521 */           return new ItemStack((getPipe((IBlockAccess)world, x, y, z)).item, 1, (getPipe((IBlockAccess)world, x, y, z)).container.getItemMetadata());
/*      */       } 
/*      */     } 
/*  524 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_149695_a(World world, int x, int y, int z, Block block) {
/*  530 */     super.func_149695_a(world, x, y, z, block);
/*      */     
/*  532 */     Pipe<?> pipe = getPipe((IBlockAccess)world, x, y, z);
/*      */     
/*  534 */     if (isValid(pipe)) {
/*  535 */       pipe.container.scheduleNeighborChange();
/*  536 */       pipe.container.redstoneInput = 0;
/*      */       
/*  538 */       for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
/*  539 */         ForgeDirection d = ForgeDirection.getOrientation(i);
/*  540 */         pipe.container.redstoneInputSide[i] = getRedstoneInputToPipe(world, x, y, z, d);
/*  541 */         if (pipe.container.redstoneInput < pipe.container.redstoneInputSide[i]) {
/*  542 */           pipe.container.redstoneInput = pipe.container.redstoneInputSide[i];
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onNeighborChange(IBlockAccess world, int x, int y, int z, int nx, int ny, int nz) {
/*  550 */     int ox = nx - x;
/*  551 */     int oy = ny - y;
/*  552 */     int oz = nz - z;
/*      */     
/*  554 */     TileEntity tile = world.func_147438_o(x, y, z);
/*      */     
/*  556 */     if (tile instanceof TileGenericPipe) {
/*  557 */       for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
/*  558 */         if (d.offsetX == ox && d.offsetY == oy && d.offsetZ == oz) {
/*  559 */           ((TileGenericPipe)tile).scheduleNeighborChange(d);
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private int getRedstoneInputToPipe(World world, int x, int y, int z, ForgeDirection d) {
/*  568 */     int i = d.ordinal();
/*  569 */     int input = world.func_72879_k(x + d.offsetX, y + d.offsetY, z + d.offsetZ, i);
/*  570 */     if (input == 0) {
/*  571 */       input = world.func_72878_l(x + d.offsetX, y + d.offsetY, z + d.offsetZ, i);
/*  572 */       if (input == 0 && d != ForgeDirection.DOWN) {
/*  573 */         Block block = world.func_147439_a(x + d.offsetX, y + d.offsetY, z + d.offsetZ);
/*  574 */         if (block instanceof net.minecraft.block.BlockRedstoneWire) {
/*  575 */           return world.func_72805_g(x + d.offsetX, y + d.offsetY, z + d.offsetZ);
/*      */         }
/*      */       } 
/*      */     } 
/*  579 */     return input;
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_149660_a(World world, int x, int y, int z, int side, float par6, float par7, float par8, int meta) {
/*  584 */     super.func_149660_a(world, x, y, z, side, par6, par7, par8, meta);
/*  585 */     Pipe<?> pipe = getPipe((IBlockAccess)world, x, y, z);
/*      */     
/*  587 */     if (isValid(pipe)) {
/*  588 */       pipe.onBlockPlaced();
/*      */     }
/*      */     
/*  591 */     return meta;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_149689_a(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
/*  596 */     super.func_149689_a(world, x, y, z, placer, stack);
/*  597 */     Pipe<?> pipe = getPipe((IBlockAccess)world, x, y, z);
/*      */     
/*  599 */     if (isValid(pipe)) {
/*  600 */       pipe.onBlockPlacedBy(placer);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
/*  606 */     if (super.func_149727_a(world, x, y, z, player, side, xOffset, yOffset, zOffset)) {
/*  607 */       return true;
/*      */     }
/*      */     
/*  610 */     world.func_147459_d(x, y, z, (Block)BuildCraftTransport.genericPipeBlock);
/*      */     
/*  612 */     Pipe<?> pipe = getPipe((IBlockAccess)world, x, y, z);
/*      */     
/*  614 */     if (isValid(pipe)) {
/*  615 */       ItemStack currentItem = player.func_71045_bC();
/*      */ 
/*      */ 
/*      */       
/*  619 */       if (player.func_70093_af() && currentItem == null) {
/*  620 */         if (stripEquipment(world, x, y, z, player, pipe, ForgeDirection.getOrientation(side))) {
/*  621 */           return true;
/*      */         }
/*  623 */       } else if (currentItem != null) {
/*      */         
/*  625 */         if (currentItem.func_77973_b() == Items.field_151155_ap)
/*      */         {
/*  627 */           return false; } 
/*  628 */         if (currentItem.func_77973_b() instanceof ItemPipe)
/*  629 */           return false; 
/*  630 */         if (currentItem.func_77973_b() instanceof ItemGateCopier)
/*  631 */           return false; 
/*  632 */         if (currentItem.func_77973_b() instanceof buildcraft.api.tools.IToolWrench) {
/*      */ 
/*      */           
/*  635 */           RaytraceResult raytraceResult = doRayTrace(world, x, y, z, player);
/*  636 */           if (raytraceResult != null) {
/*  637 */             ForgeDirection hitSide = (raytraceResult.hitPart == Part.Pipe) ? raytraceResult.sideHit : ForgeDirection.UNKNOWN;
/*  638 */             return pipe.blockActivated(player, hitSide);
/*      */           } 
/*  640 */           return pipe.blockActivated(player, ForgeDirection.UNKNOWN);
/*      */         } 
/*  642 */         if (currentItem.func_77973_b() instanceof buildcraft.api.items.IMapLocation)
/*      */         {
/*  644 */           return false; } 
/*  645 */         if (PipeWire.RED.isPipeWire(currentItem)) {
/*  646 */           if (addOrStripWire(player, pipe, PipeWire.RED)) {
/*  647 */             return true;
/*      */           }
/*  649 */         } else if (PipeWire.BLUE.isPipeWire(currentItem)) {
/*  650 */           if (addOrStripWire(player, pipe, PipeWire.BLUE)) {
/*  651 */             return true;
/*      */           }
/*  653 */         } else if (PipeWire.GREEN.isPipeWire(currentItem)) {
/*  654 */           if (addOrStripWire(player, pipe, PipeWire.GREEN)) {
/*  655 */             return true;
/*      */           }
/*  657 */         } else if (PipeWire.YELLOW.isPipeWire(currentItem)) {
/*  658 */           if (addOrStripWire(player, pipe, PipeWire.YELLOW))
/*  659 */             return true; 
/*      */         } else {
/*  661 */           if (currentItem.func_77973_b() == Items.field_151131_as) {
/*  662 */             if (!world.field_72995_K) {
/*  663 */               pipe.container.setPipeColor(-1);
/*      */             }
/*  665 */             return true;
/*  666 */           }  if (currentItem.func_77973_b() instanceof IPipePluggableItem && 
/*  667 */             addOrStripPipePluggable(world, x, y, z, currentItem, player, ForgeDirection.getOrientation(side), pipe)) {
/*  668 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*  672 */       Gate clickedGate = null;
/*      */       
/*  674 */       RaytraceResult rayTraceResult = doRayTrace(world, x, y, z, player);
/*      */       
/*  676 */       if (rayTraceResult != null && rayTraceResult.hitPart == Part.Pluggable && pipe.container
/*  677 */         .getPipePluggable(rayTraceResult.sideHit) instanceof buildcraft.transport.gates.GatePluggable) {
/*  678 */         clickedGate = pipe.gates[rayTraceResult.sideHit.ordinal()];
/*      */       }
/*      */       
/*  681 */       if (clickedGate != null) {
/*  682 */         clickedGate.openGui(player);
/*  683 */         return true;
/*      */       } 
/*  685 */       if (pipe.blockActivated(player, ForgeDirection.getOrientation(side))) {
/*  686 */         return true;
/*      */       }
/*      */       
/*  689 */       if (rayTraceResult != null) {
/*  690 */         ForgeDirection hitSide = (rayTraceResult.hitPart == Part.Pipe) ? rayTraceResult.sideHit : ForgeDirection.UNKNOWN;
/*  691 */         return pipe.blockActivated(player, hitSide);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  696 */     return false;
/*      */   }
/*      */   
/*      */   private boolean addOrStripPipePluggable(World world, int x, int y, int z, ItemStack stack, EntityPlayer player, ForgeDirection side, Pipe<?> pipe) {
/*  700 */     RaytraceResult rayTraceResult = doRayTrace(world, x, y, z, player);
/*      */     
/*  702 */     ForgeDirection placementSide = (rayTraceResult != null && rayTraceResult.sideHit != ForgeDirection.UNKNOWN) ? rayTraceResult.sideHit : side;
/*      */     
/*  704 */     IPipePluggableItem pluggableItem = (IPipePluggableItem)stack.func_77973_b();
/*  705 */     PipePluggable pluggable = pluggableItem.createPipePluggable(pipe, placementSide, stack);
/*      */     
/*  707 */     if (pluggable == null) {
/*  708 */       return false;
/*      */     }
/*      */     
/*  711 */     if (player.func_70093_af() && 
/*  712 */       pipe.container.hasPipePluggable(side) && rayTraceResult != null && rayTraceResult.hitPart == Part.Pluggable && pluggable
/*  713 */       .getClass().isInstance(pipe.container.getPipePluggable(side))) {
/*  714 */       return pipe.container.setPluggable(side, (PipePluggable)null, player);
/*      */     }
/*      */ 
/*      */     
/*  718 */     if (rayTraceResult != null && rayTraceResult.hitPart == Part.Pipe && 
/*  719 */       !pipe.container.hasPipePluggable(placementSide)) {
/*  720 */       if (pipe.container.setPluggable(placementSide, pluggable, player)) {
/*  721 */         if (!player.field_71075_bZ.field_75098_d) {
/*  722 */           stack.field_77994_a--;
/*      */         }
/*      */         
/*  725 */         return true;
/*      */       } 
/*  727 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  731 */     return false;
/*      */   }
/*      */   
/*      */   private boolean addOrStripWire(EntityPlayer player, Pipe<?> pipe, PipeWire color) {
/*  735 */     if (addWire(pipe, color)) {
/*  736 */       if (!player.field_71075_bZ.field_75098_d) {
/*  737 */         player.func_71045_bC().func_77979_a(1);
/*      */       }
/*  739 */       return true;
/*      */     } 
/*  741 */     return (player.func_70093_af() && stripWire(pipe, color, player));
/*      */   }
/*      */   
/*      */   private boolean addWire(Pipe<?> pipe, PipeWire color) {
/*  745 */     if (!pipe.wireSet[color.ordinal()]) {
/*  746 */       pipe.wireSet[color.ordinal()] = true;
/*  747 */       pipe.wireSignalStrength[color.ordinal()] = 0;
/*      */       
/*  749 */       pipe.updateSignalState();
/*      */       
/*  751 */       pipe.container.scheduleRenderUpdate();
/*  752 */       return true;
/*      */     } 
/*  754 */     return false;
/*      */   }
/*      */   
/*      */   private boolean stripWire(Pipe<?> pipe, PipeWire color, EntityPlayer player) {
/*  758 */     if (pipe.wireSet[color.ordinal()]) {
/*  759 */       if (!(pipe.container.func_145831_w()).field_72995_K) {
/*  760 */         dropWire(color, pipe, player);
/*      */       }
/*      */       
/*  763 */       pipe.wireSignalStrength[color.ordinal()] = 0;
/*  764 */       pipe.wireSet[color.ordinal()] = false;
/*      */       
/*  766 */       if (!(pipe.container.func_145831_w()).field_72995_K) {
/*  767 */         pipe.propagateSignalState(color, 0);
/*      */         
/*  769 */         if (isFullyDefined(pipe)) {
/*  770 */           pipe.resolveActions();
/*      */         }
/*      */       } 
/*      */       
/*  774 */       pipe.container.scheduleRenderUpdate();
/*      */       
/*  776 */       return true;
/*      */     } 
/*  778 */     return false;
/*      */   }
/*      */   
/*      */   private boolean stripEquipment(World world, int x, int y, int z, EntityPlayer player, Pipe<?> pipe, ForgeDirection side) {
/*  782 */     if (!world.field_72995_K) {
/*      */       
/*  784 */       ForgeDirection nSide = side;
/*      */       
/*  786 */       RaytraceResult rayTraceResult = doRayTrace(world, x, y, z, player);
/*  787 */       if (rayTraceResult != null && rayTraceResult.hitPart != Part.Pipe) {
/*  788 */         nSide = rayTraceResult.sideHit;
/*      */       }
/*      */       
/*  791 */       if (pipe.container.hasPipePluggable(nSide)) {
/*  792 */         return pipe.container.setPluggable(nSide, (PipePluggable)null, player);
/*      */       }
/*      */ 
/*      */       
/*  796 */       for (PipeWire color : PipeWire.values()) {
/*  797 */         if (stripWire(pipe, color, player)) {
/*  798 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  803 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void dropWire(PipeWire pipeWire, Pipe<?> pipe, EntityPlayer player) {
/*  812 */     Utils.dropTryIntoPlayerInventory(pipe.container.getWorld(), pipe.container.x(), pipe.container
/*  813 */         .y(), pipe.container.z(), pipeWire.getStack(), player);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
/*  819 */     super.func_149670_a(world, i, j, k, entity);
/*      */     
/*  821 */     Pipe<?> pipe = getPipe((IBlockAccess)world, i, j, k);
/*      */     
/*  823 */     if (isValid(pipe)) {
/*  824 */       pipe.onEntityCollidedWithBlock(entity);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
/*  830 */     Pipe<?> pipe = getPipe(world, x, y, z);
/*      */     
/*  832 */     if (isValid(pipe)) {
/*  833 */       return pipe.canConnectRedstone();
/*      */     }
/*  835 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int func_149748_c(IBlockAccess iblockaccess, int x, int y, int z, int l) {
/*  841 */     Pipe<?> pipe = getPipe(iblockaccess, x, y, z);
/*      */     
/*  843 */     if (isValid(pipe)) {
/*  844 */       return pipe.isPoweringTo(l);
/*      */     }
/*  846 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean func_149744_f() {
/*  852 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_149709_b(IBlockAccess world, int i, int j, int k, int l) {
/*  857 */     Pipe<?> pipe = getPipe(world, i, j, k);
/*      */     
/*  859 */     if (isValid(pipe)) {
/*  860 */       return pipe.isIndirectlyPoweringTo(l);
/*      */     }
/*  862 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_149734_b(World world, int i, int j, int k, Random random) {
/*  869 */     Pipe<?> pipe = getPipe((IBlockAccess)world, i, j, k);
/*      */     
/*  871 */     if (isValid(pipe)) {
/*  872 */       pipe.randomDisplayTick(random);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static ItemPipe registerPipe(Class<? extends Pipe<?>> clas, BCCreativeTab creativeTab) {
/*  878 */     ItemPipe item = new ItemPipe(creativeTab);
/*  879 */     item.func_77655_b("buildcraftPipe." + clas.getSimpleName().toLowerCase(Locale.ENGLISH));
/*  880 */     GameRegistry.registerItem((Item)item, item.func_77658_a());
/*      */     
/*  882 */     pipes.put(item, clas);
/*      */     
/*  884 */     Pipe<?> dummyPipe = createPipe((Item)item);
/*  885 */     if (dummyPipe != null) {
/*  886 */       item.setPipeIconIndex(dummyPipe.getIconIndexForItem());
/*  887 */       TransportProxy.proxy.setIconProviderFromPipe(item, dummyPipe);
/*      */     } 
/*      */     
/*  890 */     return item;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Pipe<?> createPipe(Item key) {
/*      */     try {
/*  896 */       Class<? extends Pipe> pipe = (Class<? extends Pipe>)pipes.get(key);
/*  897 */       if (pipe != null) {
/*  898 */         return pipe.getConstructor(new Class[] { Item.class }).newInstance(new Object[] { key });
/*      */       }
/*  900 */       BCLog.logger.warn("Detected pipe with unknown key (" + key + "). Did you remove a buildcraft addon?");
/*      */     
/*      */     }
/*  903 */     catch (Throwable t) {
/*  904 */       t.printStackTrace();
/*  905 */       BCLog.logger.warn("Failed to create pipe with (" + key + "). No valid constructor found. Possibly a item ID conflit.");
/*      */     } 
/*      */     
/*  908 */     return null;
/*      */   }
/*      */   
/*      */   public static boolean placePipe(Pipe<?> pipe, World world, int i, int j, int k, Block block, int meta, EntityPlayer player, ForgeDirection side) {
/*  912 */     if (world.field_72995_K) {
/*  913 */       return true;
/*      */     }
/*      */     
/*  916 */     if (player != null) {
/*  917 */       Block placedAgainst = world.func_147439_a(i + (side.getOpposite()).offsetX, j + (side.getOpposite()).offsetY, k + (side.getOpposite()).offsetZ);
/*  918 */       BlockEvent.PlaceEvent placeEvent = new BlockEvent.PlaceEvent(new BlockSnapshot(world, i, j, k, block, meta), placedAgainst, player);
/*      */ 
/*      */       
/*  921 */       MinecraftForge.EVENT_BUS.post((Event)placeEvent);
/*  922 */       if (placeEvent.isCanceled()) {
/*  923 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  927 */     boolean placed = world.func_147465_d(i, j, k, block, meta, 3);
/*      */     
/*  929 */     if (placed) {
/*  930 */       TileEntity tile = world.func_147438_o(i, j, k);
/*  931 */       if (tile instanceof TileGenericPipe) {
/*  932 */         TileGenericPipe tilePipe = (TileGenericPipe)tile;
/*  933 */         tilePipe.initialize(pipe);
/*  934 */         tilePipe.sendUpdateToClient();
/*      */       } 
/*      */     } 
/*      */     
/*  938 */     return placed;
/*      */   }
/*      */   
/*      */   public static Pipe<?> getPipe(IBlockAccess blockAccess, int i, int j, int k) {
/*  942 */     TileEntity tile = blockAccess.func_147438_o(i, j, k);
/*      */     
/*  944 */     if (tile instanceof IPipeTile && !tile.func_145837_r()) {
/*  945 */       IPipe pipe = ((IPipeTile)tile).getPipe();
/*  946 */       if (pipe instanceof Pipe) {
/*  947 */         return (Pipe)pipe;
/*      */       }
/*      */     } 
/*  950 */     return null;
/*      */   }
/*      */   
/*      */   public static boolean isFullyDefined(Pipe<?> pipe) {
/*  954 */     return (pipe != null && pipe.transport != null && pipe.container != null);
/*      */   }
/*      */   
/*      */   public static boolean isValid(Pipe<?> pipe) {
/*  958 */     return isFullyDefined(pipe);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void func_149651_a(IIconRegister iconRegister) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
/*  981 */     int x = target.field_72311_b;
/*  982 */     int y = target.field_72312_c;
/*  983 */     int z = target.field_72309_d;
/*      */     
/*  985 */     Pipe<?> pipe = getPipe((IBlockAccess)worldObj, x, y, z);
/*  986 */     if (pipe == null) {
/*  987 */       return false;
/*      */     }
/*      */     
/*  990 */     IIcon icon = pipe.getIconProvider().getIcon(pipe.getIconIndexForItem());
/*      */     
/*  992 */     int sideHit = target.field_72310_e;
/*      */     
/*  994 */     BlockGenericPipe blockGenericPipe = BuildCraftTransport.genericPipeBlock;
/*  995 */     float b = 0.1F;
/*  996 */     double px = x + this.rand.nextDouble() * (blockGenericPipe.func_149753_y() - blockGenericPipe.func_149704_x() - (b * 2.0F)) + b + blockGenericPipe.func_149704_x();
/*  997 */     double py = y + this.rand.nextDouble() * (blockGenericPipe.func_149669_A() - blockGenericPipe.func_149665_z() - (b * 2.0F)) + b + blockGenericPipe.func_149665_z();
/*  998 */     double pz = z + this.rand.nextDouble() * (blockGenericPipe.func_149693_C() - blockGenericPipe.func_149706_B() - (b * 2.0F)) + b + blockGenericPipe.func_149706_B();
/*      */     
/* 1000 */     if (sideHit == 0) {
/* 1001 */       py = y + blockGenericPipe.func_149665_z() - b;
/*      */     }
/*      */     
/* 1004 */     if (sideHit == 1) {
/* 1005 */       py = y + blockGenericPipe.func_149669_A() + b;
/*      */     }
/*      */     
/* 1008 */     if (sideHit == 2) {
/* 1009 */       pz = z + blockGenericPipe.func_149706_B() - b;
/*      */     }
/*      */     
/* 1012 */     if (sideHit == 3) {
/* 1013 */       pz = z + blockGenericPipe.func_149693_C() + b;
/*      */     }
/*      */     
/* 1016 */     if (sideHit == 4) {
/* 1017 */       px = x + blockGenericPipe.func_149704_x() - b;
/*      */     }
/*      */     
/* 1020 */     if (sideHit == 5) {
/* 1021 */       px = x + blockGenericPipe.func_149753_y() + b;
/*      */     }
/*      */     
/* 1024 */     EntityDiggingFX fx = new EntityDiggingFX(worldObj, px, py, pz, 0.0D, 0.0D, 0.0D, (Block)blockGenericPipe, sideHit, worldObj.func_72805_g(x, y, z));
/* 1025 */     fx.func_110125_a(icon);
/* 1026 */     effectRenderer.func_78873_a(fx.func_70596_a(x, y, z).func_70543_e(0.2F).func_70541_f(0.6F));
/* 1027 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public boolean addDestroyEffects(World worldObj, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
/* 1047 */     Pipe<?> pipe = getPipe((IBlockAccess)worldObj, x, y, z);
/* 1048 */     if (pipe == null) {
/* 1049 */       return false;
/*      */     }
/*      */     
/* 1052 */     IIcon icon = pipe.getIconProvider().getIcon(pipe.getIconIndexForItem());
/*      */     
/* 1054 */     byte its = 4;
/* 1055 */     for (int i = 0; i < its; i++) {
/* 1056 */       for (int j = 0; j < its; j++) {
/* 1057 */         for (int k = 0; k < its; k++) {
/* 1058 */           double px = x + (i + 0.5D) / its;
/* 1059 */           double py = y + (j + 0.5D) / its;
/* 1060 */           double pz = z + (k + 0.5D) / its;
/* 1061 */           int random = this.rand.nextInt(6);
/* 1062 */           EntityDiggingFX fx = new EntityDiggingFX(worldObj, px, py, pz, px - x - 0.5D, py - y - 0.5D, pz - z - 0.5D, (Block)BuildCraftTransport.genericPipeBlock, random, meta);
/* 1063 */           fx.func_110125_a(icon);
/* 1064 */           effectRenderer.func_78873_a((EntityFX)fx.func_70596_a(x, y, z));
/*      */         } 
/*      */       } 
/*      */     } 
/* 1068 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
/* 1073 */     TileGenericPipe pipeTile = (TileGenericPipe)world.func_147438_o(x, y, z);
/* 1074 */     if (!pipeTile.hasBlockingPluggable(side)) {
/* 1075 */       return pipeTile.setPipeColor(colour);
/*      */     }
/*      */     
/* 1078 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeColorFromBlock(World world, int x, int y, int z, ForgeDirection side) {
/* 1083 */     TileGenericPipe pipeTile = (TileGenericPipe)world.func_147438_o(x, y, z);
/* 1084 */     if (!pipeTile.hasBlockingPluggable(side)) {
/* 1085 */       return pipeTile.setPipeColor(-1);
/*      */     }
/*      */     
/* 1088 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
/* 1093 */     Pipe<?> pipe = getPipe(world, i, j, k);
/* 1094 */     if (pipe != null) {
/* 1095 */       return pipe.getIconProvider().getIcon(pipe.getIconIndexForItem());
/*      */     }
/*      */     
/* 1098 */     return PipeIconProvider.TYPE.PipeItemsStone.getIcon();
/*      */   }
/*      */ 
/*      */   
/*      */   public IIcon func_149691_a(int side, int meta) {
/* 1103 */     return PipeIconProvider.TYPE.PipeItemsStone.getIcon();
/*      */   }
/*      */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\BlockGenericPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
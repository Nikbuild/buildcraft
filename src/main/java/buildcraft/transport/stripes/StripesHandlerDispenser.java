/*     */ package buildcraft.transport.stripes;
/*     */ 
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.api.transport.IStripesActivator;
/*     */ import buildcraft.api.transport.IStripesHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ public class StripesHandlerDispenser
/*     */   implements IStripesHandler
/*     */ {
/*  22 */   public static final List<Object> items = new ArrayList();
/*     */   
/*     */   public class Source
/*     */     implements IBlockSource {
/*     */     private final World world;
/*     */     private final int x;
/*     */     
/*     */     public Source(World world, int x, int y, int z, ForgeDirection side) {
/*  30 */       this.world = world;
/*  31 */       this.x = x;
/*  32 */       this.y = y;
/*  33 */       this.z = z;
/*  34 */       this.side = side;
/*     */     }
/*     */     private final int y; private final int z; private final ForgeDirection side;
/*     */     
/*     */     public double func_82615_a() {
/*  39 */       return this.x + 0.5D;
/*     */     }
/*     */ 
/*     */     
/*     */     public double func_82617_b() {
/*  44 */       return this.y + 0.5D;
/*     */     }
/*     */ 
/*     */     
/*     */     public double func_82616_c() {
/*  49 */       return this.z + 0.5D;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_82623_d() {
/*  54 */       return this.x;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_82622_e() {
/*  59 */       return this.y;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_82621_f() {
/*  64 */       return this.z;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_82620_h() {
/*  69 */       return this.side.ordinal();
/*     */     }
/*     */ 
/*     */     
/*     */     public TileEntity func_150835_j() {
/*  74 */       return this.world.func_147438_o(this.x, this.y, this.z);
/*     */     }
/*     */ 
/*     */     
/*     */     public World func_82618_k() {
/*  79 */       return this.world;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public IStripesHandler.StripesHandlerType getType() {
/*  85 */     return IStripesHandler.StripesHandlerType.ITEM_USE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldHandle(ItemStack stack) {
/*  90 */     if (items.contains(stack.func_77973_b())) {
/*  91 */       return true;
/*     */     }
/*     */     
/*  94 */     Class<?> c = stack.func_77973_b().getClass();
/*  95 */     while (c != Item.class) {
/*  96 */       if (items.contains(c)) {
/*  97 */         return true;
/*     */       }
/*  99 */       c = c.getSuperclass();
/*     */     } 
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
/* 106 */     Position origin = new Position(x, y, z, direction);
/* 107 */     origin.moveBackwards(1.0D);
/*     */     
/* 109 */     IBlockSource source = new Source(world, (int)origin.x, (int)origin.y, (int)origin.z, direction);
/* 110 */     IBehaviorDispenseItem behaviour = (IBehaviorDispenseItem)BlockDispenser.field_149943_a.func_82594_a(stack.func_77973_b());
/* 111 */     if (behaviour != null) {
/* 112 */       ItemStack output = behaviour.func_82482_a(source, stack.func_77946_l());
/* 113 */       if (output.field_77994_a > 0) {
/* 114 */         activator.sendItem(output, direction.getOpposite());
/*     */       }
/* 116 */       return true;
/*     */     } 
/* 118 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\StripesHandlerDispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */